package dao;

import conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GranPremioDAO {

    public List<String> listarGrandesPremios() {
        List<String> listaGPs = new ArrayList<>();
        String sql = "SELECT nombre FROM GRAN_PREMIO";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listaGPs.add(rs.getString("nombre"));
            }
            System.out.println("Grandes Premios consultados.");

        } catch (SQLException e) {
            System.err.println("Error al listar los Grandes Premios: " + e.getMessage());
        }

        return listaGPs;
    }

    public boolean insertarGranPremio(String nombre, LocalDate fecha, int idTemporada, int idCircuito) {
        String sql = "INSERT INTO GRAN_PREMIO (nombre, fecha, id_temporada, id_circuito) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setDate(2, java.sql.Date.valueOf(fecha));
            pstmt.setInt(3, idTemporada);
            pstmt.setInt(4, idCircuito);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar el Gran Premio: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarGranPremio(int idGranPremio, String nombre, LocalDate fecha, int idTemporada, int idCircuito) {
        String sql = "UPDATE GRAN_PREMIO SET nombre = ?, fecha = ?, id_temporada = ?, id_circuito = ? WHERE id_gp = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setDate(2, java.sql.Date.valueOf(fecha));
            pstmt.setInt(3, idTemporada);
            pstmt.setInt(4, idCircuito);
            pstmt.setInt(5, idGranPremio);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el Gran Premio: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarGranPremio(int idGranPremio) {
        String sql = "DELETE FROM GRAN_PREMIO WHERE id_gp = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, idGranPremio);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el Gran Premio: " + e.getMessage());
            return false;
        }
    }
    
    public List<Object[]> listarGrandesPremiosDetallados() {
        List<Object[]> listaGPs = new ArrayList<>();
        String sql = "SELECT id_gp, nombre, fecha FROM GRAN_PREMIO";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_gp");
                String nombre = rs.getString("nombre");
                java.sql.Date fechaSql = rs.getDate("fecha");
                String fechaStr = (fechaSql != null) ? fechaSql.toString() : "";
                
                listaGPs.add(new Object[]{id, nombre, fechaStr});
            }
            System.out.println("Grandes Premios detallados consultados.");

        } catch (SQLException e) {
            System.err.println("Error al listar los Grandes Premios: " + e.getMessage());
        }

        return listaGPs;
    }
}