package barlapcb.frutagratis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
    }

    /* Realiza login no sistema */
    public void entrar( View view ) {

    }

    /* Registra um novo usuário */
    public void registrarNovoUsuario(View view) {
        Intent intent = new Intent( this, RegistrarActivity.class );
        startActivity( intent );
    }
}
