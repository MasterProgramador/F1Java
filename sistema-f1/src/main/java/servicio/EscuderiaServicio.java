package servicio;

import dao.EscuderiaDAO;
import modelo.Escuderia;

import java.util.List;

public class EscuderiaServicio {
    private EscuderiaDAO escuderiaDAO;

    public EscuderiaServicio() {
        this.escuderiaDAO = new EscuderiaDAO();
    }

    public List<Escuderia> obtenerEscuderias() {
        return escuderiaDAO.listarEscuderias();
    }

    public boolean registrarEscuderia(String nombre, int idProveedorMotor) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("Error: El nombre de la escudería no puede estar vacío.");
            return false;
        }
        return escuderiaDAO.insertarEscuderia(nombre.trim(), idProveedorMotor);
    }

    public boolean actualizarEscuderia(int idEscuderia, String nombre, int idProveedorMotor) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("Error: El nombre no puede estar vacío.");
            return false;
        }
        return escuderiaDAO.actualizarEscuderia(idEscuderia, nombre.trim(), idProveedorMotor);
    }

    public boolean eliminarEscuderia(int id) {
        return escuderiaDAO.eliminarEscuderia(id);
    }
}