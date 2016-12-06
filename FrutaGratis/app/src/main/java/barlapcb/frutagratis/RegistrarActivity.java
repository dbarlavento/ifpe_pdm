package barlapcb.frutagratis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/* Esta classe possui diversas gambiarras que devem ser corrigidas */

public class RegistrarActivity extends AppCompatActivity {

    private FirebaseAuth authUsuario;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_registrar);
        setSupportActionBar(toolBar);

        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void persistirUsuario(View view) {

        EditText in_email = (EditText) findViewById(R.id.regis_in_email);
        EditText in_senha = (EditText) findViewById(R.id.regis_in_senha);

        String email = in_email.getText().toString();
        String senha = in_senha.getText().toString();


        if(! validarEmail(email)) {
            in_email.setError("Email inválido");
            Toast.makeText(RegistrarActivity.this, "E-mail inválido!",
                    Toast.LENGTH_SHORT).show();
        }

        if(! validarSenha(senha)) {
            in_senha.setError("A senha deve ter no mínimo 6 caracteres");
            Toast.makeText(RegistrarActivity.this, "A senha deve ter no mínimo 6 caracteres!",
                    Toast.LENGTH_SHORT).show();
        }

        if( validarEmail(email) && validarSenha(senha) ) {
            authUsuario.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegistrarActivity.this, "E-mail já está cadastrado",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private FirebaseAuth.AuthStateListener getmAuthListener() {
        return new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent( RegistrarActivity.this, MapActivity.class );
                    startActivity( intent );
                    finish();
                } else {
                    Toast.makeText(RegistrarActivity.this, "msg!!!!!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }

        };
    }

    private boolean validarSenha(String senha) {
        return senha.length() >= 6;
    }

    private boolean validarEmail(String email) {
        return email.contains(".com") && email.contains("@");
    }

}
