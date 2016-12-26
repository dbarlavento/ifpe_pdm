package barlapcb.frutagratis.entidades;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 05/12/2016.
 */

public class Arvore {
    private String chave;

    private String fruta;
    private String estadoFrutos;
    private String facilidadeColheita;

    //Posição da árvore
    private String latitude;
    private String longitude;

    public Arvore(){}

    public Arvore(double latitude, double longitude) {
        this.latitude = String.valueOf(latitude);
        this.longitude = String.valueOf(longitude);
    }

    public Arvore(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Arvore(String chave, String fruta, String estadoFrutos, String facilidadeColheita,
                  String latitude, String longitude) {
        this.chave = chave;
        this.fruta = fruta;
        this.estadoFrutos = estadoFrutos;
        this.facilidadeColheita = facilidadeColheita;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude ) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Exclude
    public LatLng getPosicao() {
        return new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arvore arvore = (Arvore) o;

        if (!latitude.equals(arvore.latitude)) return false;
        return longitude.equals(arvore.longitude);

    }

    @Override
    public int hashCode() {
        int result = latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        return result;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("chave", chave);
        result.put("fruta", fruta);
        result.put("estadoFrutos", estadoFrutos);
        result.put("facilidadeColheita", facilidadeColheita);
        result.put("latitude", latitude);
        result.put("longitude", longitude);

        return result;
    }
}
