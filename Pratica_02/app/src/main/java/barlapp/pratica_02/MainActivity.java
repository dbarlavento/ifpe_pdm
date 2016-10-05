package barlapp.pratica_02;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		Toolbar mainToolBar = (Toolbar) findViewById(R.id.main_toolbar);
		setSupportActionBar(mainToolBar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.acao_configurar:
				Toast.makeText( this, "Configura o sistema", Toast.LENGTH_SHORT ).show();
				return true;

			case R.id.acao_pesquisar:
				Toast.makeText( this, "Pesquisar Frutas", Toast.LENGTH_SHORT ).show();
				return true;

			default:
				return super.onOptionsItemSelected(item);

		}
	}
}