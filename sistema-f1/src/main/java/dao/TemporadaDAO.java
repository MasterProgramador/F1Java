package dao;

import conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TemporadaDAO {

    public List<Object[]> listarTemporadasDetalladas() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id_temporada, anio FROM TEMPORADA";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_temporada");
                int anio = rs.getInt("anio");
                lista.add(new Object[]{id, anio});
            }
        } catch (SQLException e) {
            System.err.println("Error al listar temporadas: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarTemporada(int anio) {
        String sql = "INSERT INTO TEMPORADA (anio) VALUES (?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, anio);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar temporada: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarTemporada(int idTemporada, int anio) {
        String sql = "UPDATE TEMPORADA SET anio = ? WHERE id_temporada = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, anio);
            ps.setInt(2, idTemporada);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar temporada: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarTemporada(int idTemporada) {
        String sql = "DELETE FROM TEMPORADA WHERE id_temporada = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTemporada);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar temporada: " + e.getMessage());
            return false;
        }
    }
}