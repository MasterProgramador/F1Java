package dao;

import conexion.ConexionBD;
import modelo.Circuito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CircuitoDAO {

    public List<Circuito> listarCircuitos() {
        List<Circuito> lista = new ArrayList<>();
        // Ajustado a las columnas reales de la base de datos
        String sql = "SELECT id_circuito, nombre, pais FROM CIRCUITO";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Circuito c = new Circuito(
                    rs.getInt("id_circuito"),
                    rs.getString("nombre"),
                    rs.getString("pais")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar circuitos: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarCircuito(String nombre, String pais) {
        String sql = "INSERT INTO CIRCUITO (nombre, pais, longitud, vueltas_programadas) VALUES (?, ?, 5.0, 50)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, pais);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar circuito: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCircuito(int idCircuito, String nombre, String pais) {
        String sql = "UPDATE CIRCUITO SET nombre = ?, pais = ? WHERE id_circuito = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, pais);
            ps.setInt(3, idCircuito);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar circuito: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCircuito(int idCircuito) {
        String sql = "DELETE FROM CIRCUITO WHERE id_circuito = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCircuito);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar circuito: " + e.getMessage());
            return false;
        }
    }
}