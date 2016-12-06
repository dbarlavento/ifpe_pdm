package barlapcb.frutagratis.entidades;

/**
 * Created by Daniel on 05/12/2016.
 */

public class Arvore {

    private String fruta;

    private String estadoFrutos;

    private String facilidadeColheita;

    //Posição da árvore
    private double posLatitude;
    private double posLongitude;

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

    public double getPosLatitude() {
        return posLatitude;
    }

    public void setPosLatitude(double posLatitude) {
        this.posLatitude = posLatitude;
    }

    public double getPosLongitude() {
        return posLongitude;
    }

    public void setPosLongitude(double posLongitude) {
        this.posLongitude = posLongitude;
    }
}
