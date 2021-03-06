package barlapcb.frutagratis;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MapActivity extends AppCompatActivity {
    private LocationManager locationManeger;
    private LocationProvider locationProvider;

    private TextView latitude;
    private TextView longitude;

    public final static String LATITUDE_ARVORE = "latitudeArvore";
    public final static String LONGITUDE_ARVORE = "longitudeArvore";

    private final LocationListener locationListenerPosicaoUsuario = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude.setText( "Latitude: " + location.getLatitude() );
            longitude.setText( "Longitude: " + location.getLongitude() );
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_map);
        setSupportActionBar(toolBar);

        latitude = ( TextView ) findViewById( R.id.latitude );
        longitude = ( TextView ) findViewById( R.id.longitude );
    }

    @Override
    protected void onStart() {
        super.onStart();

        locationManeger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationProvider = locationManeger.getProvider(LocationManager.GPS_PROVIDER);

        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if(locationProvider != null) {
            locationManeger.requestLocationUpdates(
                    locationProvider.getName(), 10000, 1, locationListenerPosicaoUsuario);
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

    public void sair(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void novaArvore(View view) {
        Intent intent = new Intent(this, NovaArvoreActivity.class);

        String arLatitude = latitude.getText().toString();
        String arLongitude = longitude.getText().toString();

        if( arLatitude.length() > 1 && arLongitude.length() > 1) {
            intent.putExtra(LATITUDE_ARVORE, arLatitude);
            intent.putExtra(LONGITUDE_ARVORE, arLongitude);
            startActivity(intent);
        } else {
            Toast.makeText(this, "O GPS está definindo sua localização, aguarde alguns instantes",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void getLocal(View view) {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManeger.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {

            @Override
            public void onLocationChanged( Location location ) {
                TextView latitude = ( TextView ) findViewById( R.id.latitude );
                TextView longitude = ( TextView ) findViewById( R.id.longitude );

                if ( location != null ) {
                    latitude.setText("Latitude: " + location.getLatitude());
                    longitude.setText("Longitude: " + location.getLongitude());
                } else {
                    latitude.setText( "Latitude: Não localizado" );
                    longitude.setText( "Longitude: Não localizado" );
                }

            }

            @Override
            public void onStatusChanged( String provider, int status, Bundle extras ) {

            }

            @Override
            public void onProviderEnabled( String provider ) {

            }

            @Override
            public void onProviderDisabled( String provider ) {

            }
        }, null );
    }
}
