package barlapcb.frutagratis.entidades;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 03/12/2016.
 */

public class Usuario {

    private String chave;

    private String nome;
    private String sobrenome;
    private String email;

    public Usuario(){}

    public Usuario( String nome, String sobrenome, String email ) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (!chave.equals(usuario.chave)) return false;
        return email.equals(usuario.email);

    }

    @Override
    public int hashCode() {
        int result = chave.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", chave);
        result.put("nome", nome);
        result.put("sobrenome", sobrenome);
        result.put("email", email);

        return result;
    }
}
