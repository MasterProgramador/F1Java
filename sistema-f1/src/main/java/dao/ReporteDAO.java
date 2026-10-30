package dao;

import conexion.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    public List<Object[]> obtenerClasificacionPilotos() {
        List<Object[]> lista = new ArrayList<>();
        // Sumamos los puntos de cada piloto ordenados de mayor a menor
        String sql = "SELECT p.codigo, p.nombre, SUM(r.puntos) AS total_puntos, MIN(r.posicion) AS mejor_posicion " +
                     "FROM RESULTADO r " +
                     "INNER JOIN PILOTO p ON r.codigo_piloto = p.codigo " +
                     "GROUP BY p.codigo, p.nombre " +
                     "ORDER BY total_puntos DESC, mejor_posicion ASC";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int posicionTabla = 1;
            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                int puntos = rs.getInt("total_puntos");
                
                lista.add(new Object[]{posicionTabla++, codigo, nombre, puntos});
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clasificación de pilotos: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Object[]> obtenerRankingGrandesPremios() {
        List<Object[]> lista = new ArrayList<>();
        
        // Consulta para obtener el GP, el piloto con el tiempo mínimo de vuelta y su auto
        String sql = "SELECT gp.id_gp, gp.nombre AS nombre_gp, c.nombre AS circuito, " +
                     "v.codigo_piloto, p.nombre AS nombre_piloto, v.tiempo, a.modelo AS modelo_auto " +
                     "FROM GRAN_PREMIO gp " +
                     "INNER JOIN CIRCUITO c ON gp.id_circuito = c.id_circuito " +
                     "INNER JOIN VUELTA v ON gp.id_gp = v.id_gp " +
                     "INNER JOIN PILOTO p ON v.codigo_piloto = p.codigo " +
                     "LEFT JOIN RESULTADO r ON gp.id_gp = r.id_gp AND v.codigo_piloto = r.codigo_piloto " +
                     "LEFT JOIN AUTO a ON r.id_auto = a.id_auto " +
                     "WHERE v.tiempo = (SELECT MIN(v2.tiempo) FROM VUELTA v2 WHERE v2.id_gp = gp.id_gp) " +
                     "GROUP BY gp.id_gp";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idGp = rs.getInt("id_gp");
                String nombreGp = rs.getString("nombre_gp");
                String circuito = rs.getString("circuito");
                String piloto = rs.getString("nombre_piloto") + " (" + rs.getString("codigo_piloto") + ")";
                String tiempo = rs.getString("tiempo");
                String auto = rs.getString("modelo_auto") != null ? rs.getString("modelo_auto") : "No asignado";

                lista.add(new Object[]{idGp, nombreGp, circuito, piloto, tiempo, auto});
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ranking de Grandes Premios: " + e.getMessage());
        }
        return lista;
    }
}