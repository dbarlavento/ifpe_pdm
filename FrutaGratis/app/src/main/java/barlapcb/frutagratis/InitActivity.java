package barlapcb.frutagratis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InitActivity extends AppCompatActivity {

    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
    }

    private boolean validarLogin(String e, String s) {
        return true;
    }

    /* Realiza login no sistema */
    public void entrar( View view ) {
        if( validarLogin(email, senha) ) {
            Intent intent = new Intent( this, MapActivity.class );
            startActivity( intent );
        } else {
            Toast.makeText( this, "E-mail ou senha incorretos", Toast.LENGTH_LONG ).show();
        }
    }

    /* Registra um novo usuário */
    public void registrarNovoUsuario(View view) {
        Intent intent = new Intent( this, RegistrarActivity.class );
        startActivity( intent );
    }

    public void recuperarSenha(View view) {
        Intent intent = new Intent( this, RecupSenhaActivity.class );
        startActivity( intent );
    }
}
