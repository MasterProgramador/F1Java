package servicio;

import dao.PilotoDAO;
import modelo.Piloto;
import java.util.List;

public class PilotoServicio {

    private PilotoDAO pilotoDAO = new PilotoDAO();

    public List<Piloto> obtenerPilotos() {
        return pilotoDAO.listarPilotos();
    }
    
    public boolean registrarPiloto(Piloto piloto) {
        if (piloto.getCodigo() == null || piloto.getCodigo().length() != 3) {
            System.err.println("Error: El código del piloto debe tener exactamente 3 caracteres (Ej: VER, LEC).");
            return false;
        }

        if (piloto.getNombre() == null || piloto.getNombre().trim().isEmpty()) {
            System.err.println("Error: El nombre del piloto no puede estar vacío.");
            return false;
        }

        return pilotoDAO.insertarPiloto(piloto);
    }
    
    public boolean actualizarPiloto(Piloto piloto) {
        if (piloto.getNombre() == null || piloto.getNombre().trim().isEmpty()) {
            System.err.println("Error: El nombre no puede estar vacío.");
            return false;
        }
        return pilotoDAO.actualizarPiloto(piloto);
    }

    public boolean eliminarPiloto(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }
        return pilotoDAO.eliminarPiloto(codigo);
    }
}