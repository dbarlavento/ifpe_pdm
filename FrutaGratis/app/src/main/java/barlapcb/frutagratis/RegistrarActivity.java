package barlapcb.frutagratis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import barlapcb.frutagratis.entidades.Usuario;

/*
 * Bug 26/12/2016: quando um novo usuário tenta se registrar com um e-mail já cadastrado e em seguida faz uma
 * tentativa bem sucedida os dados do usuário não estão sendo persistidos no banco, apenas a autenticação
 * está sendo persistida.
 *
 * Atualização 26/12/2016: O método de persistencia deve ser generalizado para todos os objetos.
 * Atualização 26/12/2016: Os métodos de validação devem ser todos unificados em um método validarUsuário.
 */

public class RegistrarActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    private FirebaseAuth authUsuario;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Usuario usuario;

    public static boolean cadastrado = true;

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

        EditText in_nome = (EditText) findViewById(R.id.in_primeiro_nome);
        EditText in_sobrenome = (EditText) findViewById(R.id.in_sobrenome);
        EditText in_email = (EditText) findViewById(R.id.regis_in_email);
        EditText in_senha = (EditText) findViewById(R.id.regis_in_senha);

        String pNome = in_nome.getText().toString();
        String subNome = in_sobrenome.getText().toString();
        String email = in_email.getText().toString();
        String senha = in_senha.getText().toString();

        if(! validarNomes(pNome)) {
            in_nome.setError("Nome inválido");
            Toast.makeText(RegistrarActivity.this, "Nome inválido!",
                    Toast.LENGTH_SHORT).show();
        }

        if(! validarNomes(subNome)) {
            in_sobrenome.setError("Sobrenome inválido");
            Toast.makeText(RegistrarActivity.this, "Sobrenome inválido!",
                    Toast.LENGTH_SHORT).show();
        }

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

        if( validarEmail(email) && validarSenha(senha)
                && validarNomes(pNome) && validarNomes(subNome) ) {
            authUsuario.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                RegistrarActivity.cadastrado = false;
                                Toast.makeText(RegistrarActivity.this, "Este e-mail já está cadastrado",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                RegistrarActivity.cadastrado = true;
                            }
                        }
                    });

            if(cadastrado) {
                usuario = new Usuario(pNome, subNome, email);
            }
        }
    }

    private FirebaseAuth.AuthStateListener getmAuthListener() {
        return new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    cadastrarUsuario();
                    Intent intent = new Intent( RegistrarActivity.this, MapActivity.class );
                    startActivity( intent );
                    finish();
                }
            }

        };
    }

    private boolean validarNomes(String nome) {
        return !(nome.isEmpty());
    }

    private boolean validarSenha(String senha) {
        return senha.length() >= 6;
    }

    private boolean validarEmail(String email) {
        return email.contains(".com") && email.contains("@");
    }

    private void cadastrarUsuario() {
        if(cadastrado) {
            database = FirebaseDatabase.getInstance();
            DatabaseReference dadosUsuario = database.getReference("main").child("usuarios");
            usuario.setChave(dadosUsuario.push().getKey());

            Map<String, Object> valoresUsuario = usuario.toMap();

            Map<String, Object> updateUsuario = new HashMap<>();
            updateUsuario.put(usuario.getChave(), valoresUsuario);

            dadosUsuario.updateChildren(updateUsuario);
        }
    }

}
