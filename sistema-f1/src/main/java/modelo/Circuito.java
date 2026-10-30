package modelo;

public class Circuito {
    private int idCircuito;
    private String nombre;
    private String pais;

    public Circuito() {}

    public Circuito(int idCircuito, String nombre, String pais) {
        this.idCircuito = idCircuito;
        this.nombre = nombre;
        this.pais = pais;
    }

    public int getIdCircuito() {
        return idCircuito;
    }

    public void setIdCircuito(int idCircuito) {
        this.idCircuito = idCircuito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}