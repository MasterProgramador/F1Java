package servicio;

import dao.CircuitoDAO;
import modelo.Circuito;

import java.util.List;

public class CircuitoServicio {
    private CircuitoDAO circuitoDAO = new CircuitoDAO();

    public List<Circuito> obtenerCircuitos() {
        return circuitoDAO.listarCircuitos();
    }

    public boolean registrarCircuito(String nombre, String pais) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        return circuitoDAO.insertarCircuito(nombre.trim(), pais.trim());
    }

    public boolean actualizarCircuito(int idCircuito, String nombre, String pais) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        return circuitoDAO.actualizarCircuito(idCircuito, nombre.trim(), pais.trim());
    }

    public boolean eliminarCircuito(int idCircuito) {
        return circuitoDAO.eliminarCircuito(idCircuito);
    }
}