package modelo;

public class ProveedorMotor {
    private int idProveedorMotor;
    private String nombre;
    private String nChasis;

    public ProveedorMotor() {}

    public ProveedorMotor(int idProveedorMotor, String nombre, String nChasis) {
        this.idProveedorMotor = idProveedorMotor;
        this.nombre = nombre;
        this.nChasis = nChasis;
    }

    public int getIdProveedorMotor() {
        return idProveedorMotor;
    }

    public void setIdProveedorMotor(int idProveedorMotor) {
        this.idProveedorMotor = idProveedorMotor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getnChasis() {
        return nChasis;
    }

    public void setnChasis(String nChasis) {
        this.nChasis = nChasis;
    }
}