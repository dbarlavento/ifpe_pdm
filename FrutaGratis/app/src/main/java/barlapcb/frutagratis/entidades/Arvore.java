package barlapcb.frutagratis.entidades;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Daniel on 05/12/2016.
 */

public class Arvore {
    private String chave;

    private String fruta;
    private String estadoFrutos;
    private String facilidadeColheita;

    //Posição da árvore
    private String posLatitude;
    private String posLongitude;

    public Arvore(){}

    public Arvore(String posLatitude, String posLongitude) {
        this.posLatitude = posLatitude;
        this.posLongitude = posLongitude;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getFruta() {
        return fruta;
    }

    public void setFruta(String fruta) {
        this.fruta = fruta;
    }

    public String getEstadoFrutos() {
        return estadoFrutos;
    }

    public void setEstadoFrutos(String estadoFrutos) {
        this.estadoFrutos = estadoFrutos;
    }

    public String getFacilidadeColheita() {
        return facilidadeColheita;
    }

    public void setFacilidadeColheita(String facilidadeColheita) {
        this.facilidadeColheita = facilidadeColheita;
    }

    public String getPosLatitude() {
        return posLatitude;
    }

    public void setPosLatitude( String latitude ) {
        this.posLatitude = latitude;
    }

    public String getPosLongitude() {
        return posLongitude;
    }

    public void setPosLongitude(String longitude) {
        this.posLongitude = longitude;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arvore arvore = (Arvore) o;

        if (!posLatitude.equals(arvore.posLatitude)) return false;
        return posLongitude.equals(arvore.posLongitude);

    }

    @Override
    public int hashCode() {
        int result = posLatitude.hashCode();
        result = 31 * result + posLongitude.hashCode();
        return result;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("chave", chave);
        result.put("fruta", fruta);
        result.put("estadoFrutos", estadoFrutos);
        result.put("facilidadeColheita", facilidadeColheita);
        result.put("latitude", posLatitude);
        result.put("longitude", posLongitude);

        return result;
    }

}
