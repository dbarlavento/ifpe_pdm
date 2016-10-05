package main.pratica_02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.acao_configurar:
                abrirConfigurar();
                return true;

            case R.id.acao_pesquisar:
                abrirPesquisar();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void abrirConfigurar() {
        Toast.makeText(MainActivity.this, "Abrir configurções", Toast.LENGTH_LONG).show();
    }

    private void abrirPesquisar() {
        Toast.makeText(MainActivity.this, "Abrir pesquisa", Toast.LENGTH_LONG).show();
    }
}
