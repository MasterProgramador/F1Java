package servicio;

import dao.ResultadoDAO;
import modelo.Resultado;

import java.util.List;

public class ResultadoServicio {
    private ResultadoDAO resultadoDAO = new ResultadoDAO();

    public List<Resultado> obtenerResultados() {
        return resultadoDAO.listarResultados();
    }

    public boolean registrarResultado(int idGp, String codigoPiloto, int idAuto, int posicion, int vueltas, int puntos) {
        if (codigoPiloto == null || codigoPiloto.trim().isEmpty()) return false;
        return resultadoDAO.insertarResultado(idGp, codigoPiloto.trim().toUpperCase(), idAuto, posicion, vueltas, puntos);
    }

    public boolean actualizarResultado(int idGp, String codigoPiloto, int idAuto, int posicion, int vueltas, int puntos) {
        if (codigoPiloto == null || codigoPiloto.trim().isEmpty()) return false;
        return resultadoDAO.actualizarResultado(idGp, codigoPiloto.trim().toUpperCase(), idAuto, posicion, vueltas, puntos);
    }

    public boolean eliminarResultado(int idGp, String codigoPiloto) {
        return resultadoDAO.eliminarResultado(idGp, codigoPiloto);
    }
}