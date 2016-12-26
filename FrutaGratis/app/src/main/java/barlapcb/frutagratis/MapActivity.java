package barlapcb.frutagratis;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private LocationManager locationManeger;
    private LocationProvider gpsLocationProvider;
    private LocationProvider netLocationProvider;

    Location ultimaLocalizacao;

    private double latitude;
    private double longitude;

    private boolean flProcurandoLocal = false;

    public final static String LATITUDE_ARVORE = "latitudeArvore";
    public final static String LONGITUDE_ARVORE = "longitudeArvore";

    private GoogleMap mMap;
    private Marker tokenUsuario;

    private final LocationListener locationListenerPosicaoUsuario = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            LatLng localUsuario = new LatLng(location.getLatitude(), location.getLongitude());

            tokenUsuario.setPosition(localUsuario);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(localUsuario, 16));
            CameraUpdateFactory.zoomTo(50);
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

    ValueEventListener arvoresListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

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
            latitude = ultimaLocalizacao.getLatitude();
            longitude = ultimaLocalizacao.getLongitude();
        } else {
            Toast.makeText(this, "Seu GPS está desabilitado",
                    Toast.LENGTH_SHORT).show();
        }
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
                position(new LatLng(latitude,longitude))
        );
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
