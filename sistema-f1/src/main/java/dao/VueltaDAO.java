package dao;

import conexion.ConexionBD;
import modelo.Vuelta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VueltaDAO {

    public List<Vuelta> listarVueltas() {
        List<Vuelta> lista = new ArrayList<>();
        String sql = "SELECT id_gp, codigo_piloto, nro_vuelta, tiempo FROM VUELTA";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vuelta v = new Vuelta(
                    rs.getInt("id_gp"),
                    rs.getString("codigo_piloto"),
                    rs.getInt("nro_vuelta"),
                    rs.getString("tiempo")
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar vueltas: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarVuelta(int idGp, String codigoPiloto, int nroVuelta, String tiempo) {
        String sql = "INSERT INTO VUELTA (id_gp, codigo_piloto, nro_vuelta, tiempo) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGp);
            ps.setString(2, codigoPiloto);
            ps.setInt(3, nroVuelta);
            ps.setString(4, tiempo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar vuelta: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarVuelta(int idGp, String codigoPiloto, int nroVuelta, String nuevoTiempo) {
        String sql = "UPDATE VUELTA SET tiempo = ? WHERE id_gp = ? AND codigo_piloto = ? AND nro_vuelta = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoTiempo);
            ps.setInt(2, idGp);
            ps.setString(3, codigoPiloto);
            ps.setInt(4, nroVuelta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar vuelta: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarVuelta(int idGp, String codigoPiloto, int nroVuelta) {
        String sql = "DELETE FROM VUELTA WHERE id_gp = ? AND codigo_piloto = ? AND nro_vuelta = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGp);
            ps.setString(2, codigoPiloto);
            ps.setInt(3, nroVuelta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar vuelta: " + e.getMessage());
            return false;
        }
    }
}