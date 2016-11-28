package barlapp.pratica_02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
	}

	/* Realiza login no sistema */
	public void entrar( View view ) {
		Intent intent = new Intent( this, MapActivity.class );
		startActivity( intent );
	}
}
