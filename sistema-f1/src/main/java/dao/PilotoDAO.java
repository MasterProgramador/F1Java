package dao;

import conexion.ConexionBD;
import modelo.Piloto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PilotoDAO {

    public List<Piloto> listarPilotos() {
        List<Piloto> listaPilotos = new ArrayList<>();
        String sql = "SELECT codigo, nombre, nacionalidad, fecha_nacimiento, id_proveedor_indumentaria FROM PILOTO";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Piloto piloto = new Piloto();
                piloto.setCodigo(rs.getString("codigo"));
                piloto.setNombre(rs.getString("nombre"));
                piloto.setNacionalidad(rs.getString("nacionalidad"));
                
                LocalDate fechaNac = rs.getDate("fecha_nacimiento").toLocalDate();
                piloto.setFechaNacimiento(fechaNac);
                
                piloto.setIdProveedorIndumentaria(rs.getInt("id_proveedor_indumentaria"));

                listaPilotos.add(piloto);
            }
            System.out.println("Pilotos consultados.");

        } catch (SQLException e) {
            System.err.println("Error al listar los pilotos: " + e.getMessage());
        }

        return listaPilotos;
    }

    public boolean insertarPiloto(Piloto piloto) {
        String sql = "INSERT INTO PILOTO (codigo, nombre, nacionalidad, fecha_nacimiento, id_proveedor_indumentaria) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, piloto.getCodigo());
            pstmt.setString(2, piloto.getNombre());
            pstmt.setString(3, piloto.getNacionalidad());
            if (piloto.getFechaNacimiento() != null) {
                pstmt.setDate(4, java.sql.Date.valueOf(piloto.getFechaNacimiento()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }
            pstmt.setInt(5, piloto.getIdProveedorIndumentaria());

            pstmt.executeUpdate();
            System.out.println("Piloto insertado exitosamente.");
            return true;

        } catch (SQLException e) {
            System.err.println("Error al insertar el piloto: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizarPiloto(Piloto piloto) {
        String sql = "UPDATE PILOTO SET nombre = ?, nacionalidad = ? WHERE codigo = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, piloto.getNombre());
            ps.setString(2, piloto.getNacionalidad());
            ps.setString(3, piloto.getCodigo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar piloto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPiloto(String codigo) {
        String sql = "DELETE FROM PILOTO WHERE codigo = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar piloto (puede tener datos relacionados): " + e.getMessage());
            return false;
        }
    }
}