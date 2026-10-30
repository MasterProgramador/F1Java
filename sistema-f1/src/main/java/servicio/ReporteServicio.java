package servicio;

import dao.ReporteDAO;
import java.util.List;

public class ReporteServicio {
    private ReporteDAO reporteDAO = new ReporteDAO();

    public List<Object[]> obtenerClasificacionPilotos() {
        return reporteDAO.obtenerClasificacionPilotos();
    }

    public List<Object[]> obtenerRankingGrandesPremios() {
        return reporteDAO.obtenerRankingGrandesPremios();
    }
}