package barlapcb.frutagratis;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import barlapcb.frutagratis.entidades.Arvore;

public class NovaArvoreActivity extends AppCompatActivity {

    private Spinner frutas, producao, acesso;

    private ArrayAdapter<CharSequence> adapterFrutas;
    private ArrayAdapter<CharSequence> adapterProducao;
    private ArrayAdapter<CharSequence> adapterAcesso;

    private Intent intent;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_arvore);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_cadastrar_arvore);
        setSupportActionBar(toolBar);
        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);
        iniSpinnerSelFrutas();
        iniSpinnerSelEstadoProducao();
        iniSpinnerSelAcesso();

        intent = getIntent();
    }

    private void iniSpinnerSelFrutas() {
        frutas = (Spinner) findViewById( R.id.sel_frutas );
        adapterFrutas = ArrayAdapter.createFromResource(this, R.array.futas_array,
                android.R.layout.simple_spinner_item);
        adapterFrutas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frutas.setAdapter(adapterFrutas);
    }

    private void iniSpinnerSelEstadoProducao() {
        producao = (Spinner) findViewById( R.id.sel_estado_producao );
        adapterProducao = ArrayAdapter.createFromResource(this, R.array.estado_de_producao,
                android.R.layout.simple_spinner_item);
        adapterProducao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        producao.setAdapter(adapterProducao);
    }

    private void iniSpinnerSelAcesso() {
        acesso = (Spinner) findViewById( R.id.sel_facilidade_de_colheita );
        adapterAcesso = ArrayAdapter.createFromResource(this, R.array.facilidade_de_colheita,
                android.R.layout.simple_spinner_item);
        adapterAcesso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acesso.setAdapter(adapterAcesso);
    }

    public void voltarMapa(View view) {
        finish();
    }

    public void persistirArvore(View view) {
        Arvore arvore = new Arvore(intent.getStringExtra(MapActivity.LATITUDE_ARVORE),
                intent.getStringExtra(MapActivity.LONGITUDE_ARVORE));

        arvore.setFruta(frutas.getSelectedItem().toString());
        arvore.setEstadoFrutos(producao.getSelectedItem().toString());
        arvore.setFacilidadeColheita(acesso.getSelectedItem().toString());

        database = FirebaseDatabase.getInstance();
        DatabaseReference dadosArvore = database.getReference("main").child("arvores");
        arvore.setChave(dadosArvore.push().getKey());

        Map<String, Object> valoresArvore = arvore.toMap();

        Map<String, Object> updateArvore = new HashMap<>();
        updateArvore.put(arvore.getChave(), valoresArvore);

        dadosArvore.updateChildren(updateArvore);

        Toast.makeText(this, "Árvore adicionada com sucesso", Toast.LENGTH_LONG).show();

        finish();
    }
}
