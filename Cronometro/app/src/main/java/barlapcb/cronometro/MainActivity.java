package barlapcb.cronometro;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Chronometer cronometro;
    Button bt_iniciar;
    Button bt_parar;
    Button bt_limpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cronometro = (Chronometer) findViewById(R.id.cronometro);

        bt_iniciar = (Button) findViewById(R.id.bt_iniciar);
        bt_parar = (Button) findViewById(R.id.bt_parar);
        bt_limpar = (Button) findViewById(R.id.bt_limpar);

        bt_iniciar.setOnClickListener(this);
        bt_parar.setOnClickListener(this);
        bt_limpar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_iniciar:
                cronometro.start();
                break;
            case R.id.bt_parar:
                cronometro.stop();
                break;
            case R.id.bt_limpar:
                cronometro.setBase(SystemClock.elapsedRealtime());
                break;
        }
    }
}
