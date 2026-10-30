package servicio;

import dao.TemporadaDAO;
import java.util.List;

public class TemporadaServicio {
    private TemporadaDAO temporadaDAO = new TemporadaDAO();

    public List<Object[]> obtenerTemporadasDetalladas() {
        return temporadaDAO.listarTemporadasDetalladas();
    }

    public boolean registrarTemporada(int anio) {
        if (anio < 1950) return false;
        return temporadaDAO.insertarTemporada(anio);
    }

    public boolean actualizarTemporada(int idTemporada, int anio) {
        if (anio < 1950) return false;
        return temporadaDAO.actualizarTemporada(idTemporada, anio);
    }

    public boolean eliminarTemporada(int idTemporada) {
        return temporadaDAO.eliminarTemporada(idTemporada);
    }
}