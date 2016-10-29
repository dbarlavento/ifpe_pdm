package barlapcb.cronometro;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final private long INTERVALO = 10;

    long valorInicial;

    CountDownTimer cronometroDec;

    boolean eDecrescente;

    TextView minutos;
    TextView segundos;
    TextView milisegundos;

    Button btIniciar;
    Button btParar;
    Button btLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valorInicial = 30000;

        minutos = (TextView) findViewById(R.id.dp_minutos);
        segundos = (TextView) findViewById(R.id.dp_segundos);
        milisegundos = (TextView) findViewById(R.id.dp_msegundos);

        btIniciar = (Button) findViewById(R.id.bt_iniciar);
        btParar = (Button) findViewById(R.id.bt_parar);
        btLimpar = (Button) findViewById(R.id.bt_limpar);

        btIniciar.setOnClickListener(this);
        btParar.setOnClickListener(this);
        btLimpar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_iniciar:
                iniciar();
                break;
            case R.id.bt_parar:
                parar();
                break;
            case R.id.bt_limpar:
                limpar();
                break;
        }
    }

    private void iniciar() {
        atualizarTexto(valorInicial);
        cronometroDec = new CountDownTimer(valorInicial, INTERVALO) {

            @Override
            public void onTick(long millisUntilFinished) {
                atualizarTexto(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                limpar();
            }
        }.start();
    }

    private void parar() {

    }

    private void configurar() {

    }

    private void limpar() {

    }

    public void atualizarTexto(long tempoRestante) {
        int mSec = (int) tempoRestante % 1000;
        int seg = (int) (tempoRestante / 1000);
        int min = seg / 60;

        if (mSec > 99) {
            milisegundos.setText(Integer.toString(mSec));
        } else if (mSec > 9) {
            milisegundos.setText("0" + Integer.toString(mSec));
        } else {
            milisegundos.setText("00" + Integer.toString(mSec));
        }

        if (seg < 10) {
            segundos.setText("0" + Integer.toString(seg));
        } else {
            segundos.setText(Integer.toString(seg % 60));
        }

        if (min < 10) {
            minutos.setText("0" + Integer.toString(min));
        } else {
            minutos.setText(Integer.toString(min));
        }
    }
}
