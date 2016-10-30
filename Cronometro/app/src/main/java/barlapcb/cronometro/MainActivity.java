package barlapcb.cronometro;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int INTERVALO_CRONOMETRO = 10;
    private final int INTERVALO_REGRESSIVA = 980;

    int valorRegressiva;
    long valorConfigurado;
    long valorInicial;
    long valorParado;

    CountDownTimer contRegressiva;
    CountDownTimer cronometroDec;

    boolean comRegressiva;
    boolean eDecrescente;

    TextView minutos;
    TextView segundos;
    TextView milisegundos;

    Button btIniciar;
    Button btParar;
    Button btLimpar;

    ToneGenerator toneG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolBar);

        comRegressiva = true;

        valorRegressiva = 5000;
        valorConfigurado = 30000;
        valorInicial = 0;
        valorParado = 0;

        minutos = (TextView) findViewById(R.id.dp_minutos);
        segundos = (TextView) findViewById(R.id.dp_segundos);
        milisegundos = (TextView) findViewById(R.id.dp_msegundos);

        btIniciar = (Button) findViewById(R.id.bt_iniciar);
        btParar = (Button) findViewById(R.id.bt_parar);
        btLimpar = (Button) findViewById(R.id.bt_limpar);

        btIniciar.setOnClickListener(this);
        btParar.setOnClickListener(this);
        btLimpar.setOnClickListener(this);

        toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
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
        if (valorParado > 0) {
            valorInicial = valorParado;
        } else {
            valorInicial = valorConfigurado;
        }

        atualizarTexto(valorInicial);

        cronometroDec = new CountDownTimer(valorInicial, INTERVALO_CRONOMETRO) {
            @Override
            public void onTick(long millisUntilFinished) {
                atualizarTexto(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                toneG.startTone(ToneGenerator.TONE_DTMF_0, 200);
                limpar();
            }
        };

        if (comRegressiva) {
            contRegressiva = new CountDownTimer(valorRegressiva, INTERVALO_REGRESSIVA) {
                @Override
                public void onTick(long millisUntilFinished) {
                    toneG.startTone(ToneGenerator.TONE_DTMF_1, 100);
                }

                @Override
                public void onFinish() {
                    toneG.startTone(ToneGenerator.TONE_DTMF_0, 200);
                    cronometroDec.start();
                }
            }.start();
        } else {
            cronometroDec.start();
        }
    }

    private void parar() {
        cronometroDec.cancel();
    }

    public void configurar(View view) {
        Intent intent = new Intent(this, ConfiguracaoActivity.class);
        startActivity(intent);
    }

    private void limpar() {
        valorParado = 0;
        milisegundos.setText("000");
        segundos.setText("00");
        minutos.setText("00");
    }

    private void atualizarTexto(long tempoRestante) {
        valorParado = tempoRestante;
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
