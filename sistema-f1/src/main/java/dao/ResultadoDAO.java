package dao;

import conexion.ConexionBD;
import modelo.Resultado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultadoDAO {

    public List<Resultado> listarResultados() {
        List<Resultado> lista = new ArrayList<>();
        String sql = "SELECT id_gp, codigo_piloto, id_auto, posicion, vueltas_completadas, puntos FROM RESULTADO";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Resultado r = new Resultado(
                    rs.getInt("id_gp"),
                    rs.getString("codigo_piloto"),
                    rs.getInt("id_auto"),
                    rs.getInt("posicion"),
                    rs.getInt("vueltas_completadas"),
                    rs.getInt("puntos")
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar resultados: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarResultado(int idGp, String codigoPiloto, int idAuto, int posicion, int vueltas, int puntos) {
        String sql = "INSERT INTO RESULTADO (id_gp, codigo_piloto, id_auto, posicion, vueltas_completadas, puntos) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGp);
            ps.setString(2, codigoPiloto);
            ps.setInt(3, idAuto);
            ps.setInt(4, posicion);
            ps.setInt(5, vueltas);
            ps.setInt(6, puntos);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar resultado: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarResultado(int idGp, String codigoPiloto, int idAuto, int posicion, int vueltas, int puntos) {
        String sql = "UPDATE RESULTADO SET id_auto = ?, posicion = ?, vueltas_completadas = ?, puntos = ? WHERE id_gp = ? AND codigo_piloto = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAuto);
            ps.setInt(2, posicion);
            ps.setInt(3, vueltas);
            ps.setInt(4, puntos);
            ps.setInt(5, idGp);
            ps.setString(6, codigoPiloto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar resultado: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarResultado(int idGp, String codigoPiloto) {
        String sql = "DELETE FROM RESULTADO WHERE id_gp = ? AND codigo_piloto = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGp);
            ps.setString(2, codigoPiloto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar resultado: " + e.getMessage());
            return false;
        }
    }
}