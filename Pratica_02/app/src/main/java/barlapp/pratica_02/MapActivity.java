package barlapp.pratica_02;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class MapActivity extends AppCompatActivity {

	LocationManager locationManager;

	private void setInitLocation() {
		if ( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		locationManager.requestSingleUpdate( LocationManager.GPS_PROVIDER, new LocationListener() {

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

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_map );
		Toolbar mainToolBar = (Toolbar) findViewById(R.id.main_toolbar);
		setSupportActionBar(mainToolBar);
		locationManager = ( LocationManager ) getSystemService( Context.LOCATION_SERVICE );
		setInitLocation();
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.menu_map, menu );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.acao_nova_arvore:
				novaArvore();
				return true;

			case R.id.acao_configurar:
				Toast.makeText( this, "Configura o sistema", Toast.LENGTH_SHORT ).show();
				return true;

			case R.id.acao_sair:
				Toast.makeText( this, "Retornar a tela de login", Toast.LENGTH_SHORT ).show();
				return true;

			case R.id.acao_pesquisar:
				Toast.makeText( this, "Pesquisar Frutas", Toast.LENGTH_SHORT ).show();
				return true;

			default:
				return super.onOptionsItemSelected(item);

		}
	}

	/* Realiza login no sistema */
	public void novaArvore( View view ) {
		Intent intent = new Intent( this, NovaArvoreActivity.class );
		startActivity( intent );

	}

}
