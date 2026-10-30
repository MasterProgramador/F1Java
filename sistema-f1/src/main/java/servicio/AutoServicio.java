package servicio;

import dao.AutoDAO;
import modelo.Auto;
import java.util.List;

public class AutoServicio {
    private AutoDAO autoDAO = new AutoDAO();

    public List<Auto> obtenerAutos() {
        return autoDAO.listarAutos();
    }

    public boolean registrarAuto(String modelo, int anioFabricacion, int idEscuderia) {
        if (modelo == null || modelo.trim().isEmpty()) return false;
        return autoDAO.insertarAuto(modelo.trim(), anioFabricacion, idEscuderia);
    }

    public boolean actualizarAuto(int idAuto, String modelo, int anioFabricacion, int idEscuderia) {
        if (modelo == null || modelo.trim().isEmpty()) return false;
        return autoDAO.actualizarAuto(idAuto, modelo.trim(), anioFabricacion, idEscuderia);
    }

    public boolean eliminarAuto(int idAuto) {
        return autoDAO.eliminarAuto(idAuto);
    }
}