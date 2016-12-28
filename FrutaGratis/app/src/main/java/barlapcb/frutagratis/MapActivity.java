package barlapcb.frutagratis;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import barlapcb.frutagratis.entidades.Arvore;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private LocationManager locationManeger;
    private LocationProvider gpsLocationProvider;
    private LocationProvider netLocationProvider;

    Location ultimaLocalizacao;

    private double latitude;
    private double longitude;
    private LatLng localUsuario;

    private boolean flProcurandoLocal = false;

    public final static String LATITUDE_ARVORE = "latitudeArvore";
    public final static String LONGITUDE_ARVORE = "longitudeArvore";

    private GoogleMap mMap;
    private Marker tokenUsuario;

    private FirebaseDatabase banco;
    private DatabaseReference refArvores;
    private static String filtroFruta = "";

    private final LocationListener locationListenerPosicaoUsuario = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            localUsuario = new LatLng(location.getLatitude(), location.getLongitude());
            tokenUsuario.setPosition(localUsuario);
            mMap.animateCamera(CameraUpdateFactory.newLatLng(localUsuario));
            flProcurandoLocal = true;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MapActivity.this, "GPS habilitado",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            flProcurandoLocal = false;
            Toast.makeText(MapActivity.this, "GPS desabilitado",
                    Toast.LENGTH_LONG).show();
        }
    };

    ChildEventListener arvoresListener = new ChildEventListener() {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Arvore ar = dataSnapshot.getValue(Arvore.class);

            if (ar != null && filtroFruta.isEmpty()) {
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_arvore))
                        .position(ar.getPosicao())
                        .title(ar.getFruta())
                        .snippet("Estado das frutas: " + ar.getEstadoFrutos())
                );
            } else {
                if( ar.getFruta().equals(filtroFruta) ) {
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marca_arvore))
                            .position(ar.getPosicao())
                            .title(ar.getFruta())
                            .snippet("Estado das frutas: " + ar.getEstadoFrutos())
                    );
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_map);
        setSupportActionBar(toolBar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        locationManeger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsLocationProvider = locationManeger.getProvider(LocationManager.GPS_PROVIDER);
        netLocationProvider = locationManeger.getProvider(LocationManager.NETWORK_PROVIDER);

        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (gpsLocationProvider != null) {
            locationManeger.requestLocationUpdates(
                    gpsLocationProvider.getName(), 10000, 1, locationListenerPosicaoUsuario);
            ultimaLocalizacao = locationManeger.getLastKnownLocation(gpsLocationProvider.getName());
            if (ultimaLocalizacao == null) {
                latitude = 0;
                longitude = 0;
            } else {
                latitude = ultimaLocalizacao.getLatitude();
                longitude = ultimaLocalizacao.getLongitude();
            }
            localUsuario = new LatLng(latitude, longitude);
        } else {
            Toast.makeText(this, "Seu GPS está desabilitado",
                    Toast.LENGTH_SHORT).show();
        }

        banco = FirebaseDatabase.getInstance();
        refArvores = banco.getReference("main").child("arvores");
        refArvores.addChildEventListener(arvoresListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        tokenUsuario = mMap.addMarker(new MarkerOptions().flat(true).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.ic_navigation_black_18dp)).
                position(localUsuario)
        );

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(localUsuario, 16));
    }

    public void filtrar(MenuItem item) {
        EditText pesquisa = (EditText) findViewById(R.id.in_pesquisa);

        mMap.clear();
        tokenUsuario = mMap.addMarker(new MarkerOptions().flat(true).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.ic_navigation_black_18dp)).
                position(localUsuario)
        );

        if( filtroFruta.isEmpty() ) {
            filtroFruta = pesquisa.getText().toString();
            item.setIcon(R.drawable.ic_clear_white_48dp);
            refArvores.removeEventListener(arvoresListener);
            refArvores.addChildEventListener(arvoresListener);
        } else {
            filtroFruta = "";
            item.setIcon(R.drawable.ic_search_white_48dp);
            refArvores.removeEventListener(arvoresListener);
            refArvores.addChildEventListener(arvoresListener);
        }
    }

    public void sair(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void novaArvore(View view) {
        Intent intent = new Intent(this, NovaArvoreActivity.class);

        if (flProcurandoLocal) {
            intent.putExtra(LATITUDE_ARVORE, String.valueOf(latitude));
            intent.putExtra(LONGITUDE_ARVORE, String.valueOf(longitude));
            startActivity(intent);
        } else {
            Toast.makeText(this, "O GPS está definindo sua localização, aguarde ...",
                    Toast.LENGTH_LONG).show();
        }
    }
}
