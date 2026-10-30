package modelo;

import java.time.LocalDate;

public class GranPremio {
    private int idGp;
    private String nombre;
    private LocalDate fecha;
    private int idTemporada;
    private int idCircuito;

    public GranPremio() {
    }

    public GranPremio(int idGp, String nombre, LocalDate fecha, int idTemporada, int idCircuito) {
        this.idGp = idGp;
        this.nombre = nombre;
        this.fecha = fecha;
        this.idTemporada = idTemporada;
        this.idCircuito = idCircuito;
    }

    public int getIdGp() {
        return idGp;
    }

    public void setIdGp(int idGp) {
        this.idGp = idGp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getIdTemporada() {
        return idTemporada;
    }

    public void setIdTemporada(int idTemporada) {
        this.idTemporada = idTemporada;
    }

    public int getIdCircuito() {
        return idCircuito;
    }

    public void setIdCircuito(int idCircuito) {
        this.idCircuito = idCircuito;
    }
}