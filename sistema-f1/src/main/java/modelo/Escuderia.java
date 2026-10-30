package modelo;

public class Escuderia {
    private int idEscuderia;
    private String nombre;
    private int idProveedorMotor;

    public Escuderia() {
    }

    public Escuderia(int idEscuderia, String nombre, int idProveedorMotor) {
        this.idEscuderia = idEscuderia;
        this.nombre = nombre;
        this.idProveedorMotor = idProveedorMotor;
    }

    public int getIdEscuderia() {
        return idEscuderia;
    }

    public void setIdEscuderia(int idEscuderia) {
        this.idEscuderia = idEscuderia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdProveedorMotor() {
        return idProveedorMotor;
    }

    public void setIdProveedorMotor(int idProveedorMotor) {
        this.idProveedorMotor = idProveedorMotor;
    }
}