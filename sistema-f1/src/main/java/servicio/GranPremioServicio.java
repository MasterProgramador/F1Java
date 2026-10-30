package servicio;

import dao.GranPremioDAO;
import java.time.LocalDate;
import java.util.List;

public class GranPremioServicio {

    private GranPremioDAO granPremioDAO = new GranPremioDAO();

    public List<String> obtenerGrandesPremios() {
        return granPremioDAO.listarGrandesPremios();
    }

    public boolean registrarGranPremio(String nombre, LocalDate fecha, int idTemporada, int idCircuito) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("Error: El nombre del Gran Premio no puede estar vacío.");
            return false;
        }

        if (fecha == null) {
            System.err.println("Error: La fecha del Gran Premio es obligatoria.");
            return false;
        }

        return granPremioDAO.insertarGranPremio(nombre.trim(), fecha, idTemporada, idCircuito);
    }

    public boolean actualizarGranPremio(int idGranPremio, String nombre, LocalDate fecha, int idTemporada, int idCircuito) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("Error: El nombre del Gran Premio no puede estar vacío.");
            return false;
        }

        if (fecha == null) {
            System.err.println("Error: La fecha del Gran Premio es obligatoria.");
            return false;
        }

        return granPremioDAO.actualizarGranPremio(idGranPremio, nombre.trim(), fecha, idTemporada, idCircuito);
    }

    public boolean eliminarGranPremio(int idGranPremio) {
        return granPremioDAO.eliminarGranPremio(idGranPremio);
    }
    
    public List<Object[]> obtenerGrandesPremiosDetallados() {
        return granPremioDAO.listarGrandesPremiosDetallados();
    }
}