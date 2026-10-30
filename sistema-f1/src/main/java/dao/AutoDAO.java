package dao;

import conexion.ConexionBD;
import modelo.Auto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoDAO {

    public List<Auto> listarAutos() {
        List<Auto> lista = new ArrayList<>();
        String sql = "SELECT id_auto, modelo, anio_fabricacion, id_escuderia FROM AUTO";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Auto a = new Auto(
                    rs.getInt("id_auto"),
                    rs.getString("modelo"),
                    rs.getInt("anio_fabricacion"),
                    rs.getInt("id_escuderia")
                );
                lista.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar autos: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarAuto(String modelo, int anioFabricacion, int idEscuderia) {
        String sql = "INSERT INTO AUTO (modelo, anio_fabricacion, id_escuderia) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, modelo);
            ps.setInt(2, anioFabricacion);
            ps.setInt(3, idEscuderia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar auto: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarAuto(int idAuto, String modelo, int anioFabricacion, int idEscuderia) {
        String sql = "UPDATE AUTO SET modelo = ?, anio_fabricacion = ?, id_escuderia = ? WHERE id_auto = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, modelo);
            ps.setInt(2, anioFabricacion);
            ps.setInt(3, idEscuderia);
            ps.setInt(4, idAuto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar auto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarAuto(int idAuto) {
        String sql = "DELETE FROM AUTO WHERE id_auto = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAuto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar auto: " + e.getMessage());
            return false;
        }
    }
}