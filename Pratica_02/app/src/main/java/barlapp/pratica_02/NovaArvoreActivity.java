package barlapp.pratica_02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NovaArvoreActivity extends AppCompatActivity {

	private Spinner frutas;
	private ArrayAdapter<CharSequence> adapter;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_nova_arvore );
		Toolbar mainToolBar = (Toolbar) findViewById(R.id.main_toolbar);
		setSupportActionBar(mainToolBar);
		iniSpinnerFrutas();
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.menu_map, menu );
		return true;
	}

	private void iniSpinnerFrutas() {
		frutas = (Spinner) findViewById( R.id.sel_frutas );
		adapter = ArrayAdapter.createFromResource(this, R.array.futas_array,
			  android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		frutas.setAdapter(adapter);
	}
}
