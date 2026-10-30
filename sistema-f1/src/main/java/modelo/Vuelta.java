package modelo;

public class Vuelta {
    private int idGp;
    private String codigoPiloto;
    private int nroVuelta;
    private String tiempo;

    public Vuelta() {}

    public Vuelta(int idGp, String codigoPiloto, int nroVuelta, String tiempo) {
        this.idGp = idGp;
        this.codigoPiloto = codigoPiloto;
        this.nroVuelta = nroVuelta;
        this.tiempo = tiempo;
    }

    public int getIdGp() { return idGp; }
    public void setIdGp(int idGp) { this.idGp = idGp; }

    public String getCodigoPiloto() { return codigoPiloto; }
    public void setCodigoPiloto(String codigoPiloto) { this.codigoPiloto = codigoPiloto; }

    public int getNroVuelta() { return nroVuelta; }
    public void setNroVuelta(int nroVuelta) { this.nroVuelta = nroVuelta; }

    public String getTiempo() { return tiempo; }
    public void setTiempo(String tiempo) { this.tiempo = tiempo; }
}