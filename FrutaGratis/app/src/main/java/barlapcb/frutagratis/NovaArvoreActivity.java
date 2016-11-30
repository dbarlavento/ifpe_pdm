package barlapcb.frutagratis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NovaArvoreActivity extends AppCompatActivity {

    private Spinner frutas, producao, acesso;
    private ArrayAdapter<CharSequence> adapterFrutas;
    private ArrayAdapter<CharSequence> adapterProducao;
    private ArrayAdapter<CharSequence> adapterAcesso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_arvore);
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
}
