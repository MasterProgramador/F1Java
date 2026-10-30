package servicio;

import dao.VueltaDAO;
import modelo.Vuelta;

import java.util.List;

public class VueltaServicio {
    private VueltaDAO vueltaDAO = new VueltaDAO();

    public List<Vuelta> obtenerVueltas() {
        return vueltaDAO.listarVueltas();
    }

    public boolean registrarVuelta(int idGp, String codigoPiloto, int nroVuelta, String tiempo) {
        if (codigoPiloto == null || codigoPiloto.trim().isEmpty()) return false;
        return vueltaDAO.insertarVuelta(idGp, codigoPiloto.trim().toUpperCase(), nroVuelta, tiempo.trim());
    }

    public boolean actualizarVuelta(int idGp, String codigoPiloto, int nroVuelta, String tiempo) {
        if (codigoPiloto == null || codigoPiloto.trim().isEmpty()) return false;
        return vueltaDAO.actualizarVuelta(idGp, codigoPiloto.trim().toUpperCase(), nroVuelta, tiempo.trim());
    }

    public boolean eliminarVuelta(int idGp, String codigoPiloto, int nroVuelta) {
        return vueltaDAO.eliminarVuelta(idGp, codigoPiloto.trim().toUpperCase(), nroVuelta);
    }
}