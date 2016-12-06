package barlapcb.frutagratis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/* Esta classe possui diversas gambiarras que devem ser corrigidas */
public class InitActivity extends AppCompatActivity {

    private FirebaseAuth authUsuario;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        authUsuario = FirebaseAuth.getInstance();
        mAuthListener = getmAuthListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        authUsuario.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            authUsuario.removeAuthStateListener(mAuthListener);
        }
    }

    private FirebaseAuth.AuthStateListener getmAuthListener() {
        return new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent( InitActivity.this, MapActivity.class );
                    startActivity( intent );
                } else {
                    Toast.makeText(InitActivity.this, "Você está desconectado",
                            Toast.LENGTH_SHORT).show();
                }
            }

        };
    }

    private boolean validarLogin(String e, String s) {
        return true;
    }

    /* Realiza login no sistema */
    public void entrar( View view ) {

        EditText in_email = (EditText) findViewById(R.id.login_in_email);
        EditText in_senha = (EditText) findViewById(R.id.login_in_senha);

        email = in_email.getText().toString();
        senha = in_senha.getText().toString();

        if( validarEmail(email) && validarSenha(senha)) {
            authUsuario.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(InitActivity.this, "E-mail ou senha incorretos",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(InitActivity.this, "E-mail ou senha incorretos",
                    Toast.LENGTH_SHORT).show();
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

    private boolean validarSenha(String senha) {
        return senha.length() >= 6;
    }

    private boolean validarEmail(String email) {
        return email.contains(".com") && email.contains("@");
    }
}
