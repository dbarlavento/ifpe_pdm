package barlapcb.frutagratis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_map);
        setSupportActionBar(toolBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_map, menu );
        return true;
    }

    public void sair(MenuItem item) {
        finish();
    }

    public void novaArvore( View view ) {
        Intent intent = new Intent( this, NovaArvoreActivity.class );
        startActivity( intent );
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
