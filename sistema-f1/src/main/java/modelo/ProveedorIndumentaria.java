package modelo;

public class ProveedorIndumentaria {
    private int idProveedorIndumentaria;
    private String nombre;

    public ProveedorIndumentaria() {}

    public ProveedorIndumentaria(int idProveedorIndumentaria, String nombre) {
        this.idProveedorIndumentaria = idProveedorIndumentaria;
        this.nombre = nombre;
    }

    public int getIdProveedorIndumentaria() {
        return idProveedorIndumentaria;
    }

    public void setIdProveedorIndumentaria(int idProveedorIndumentaria) {
        this.idProveedorIndumentaria = idProveedorIndumentaria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}