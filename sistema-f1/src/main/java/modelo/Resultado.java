package modelo;

public class Resultado {
    private int idGp;
    private String codigoPiloto;
    private int idAuto;
    private int posicion;
    private int vueltasCompletadas;
    private int puntos;

    public Resultado() {
    }

    public Resultado(int idGp, String codigoPiloto, int idAuto, int posicion, int vueltasCompletadas, int puntos) {
        this.idGp = idGp;
        this.codigoPiloto = codigoPiloto;
        this.idAuto = idAuto;
        this.posicion = posicion;
        this.vueltasCompletadas = vueltasCompletadas;
        this.puntos = puntos;
    }

    public int getIdGp() {
        return idGp;
    }

    public void setIdGp(int idGp) {
        this.idGp = idGp;
    }

    public String getCodigoPiloto() {
        return codigoPiloto;
    }

    public void setCodigoPiloto(String codigoPiloto) {
        this.codigoPiloto = codigoPiloto;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getVueltasCompletadas() {
        return vueltasCompletadas;
    }

    public void setVueltasCompletadas(int vueltasCompletadas) {
        this.vueltasCompletadas = vueltasCompletadas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}