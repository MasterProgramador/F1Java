package modelo;

import java.time.LocalDate;

public class Piloto {
    private String codigo;
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private int idProveedorIndumentaria;

    public Piloto() {
    }

    public Piloto(String codigo, String nombre, String nacionalidad, LocalDate fechaNacimiento, int idProveedorIndumentaria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.idProveedorIndumentaria = idProveedorIndumentaria;
    } //tener 2 constructores facilita crear el objeto

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdProveedorIndumentaria() {
        return idProveedorIndumentaria;
    }

    public void setIdProveedorIndumentaria(int idProveedorIndumentaria) {
        this.idProveedorIndumentaria = idProveedorIndumentaria;
    }
}