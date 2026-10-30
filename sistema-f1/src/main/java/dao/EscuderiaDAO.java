package dao;

import conexion.ConexionBD;
import modelo.Escuderia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EscuderiaDAO {

	public List<Escuderia> listarEscuderias() {
	    List<Escuderia> listaEscuderias = new ArrayList<>();
	    String sql = "SELECT id_escuderia, nombre, id_proveedor_motor FROM ESCUDERIA";

	    try (Connection conexion = ConexionBD.obtenerConexion();
	         PreparedStatement pstmt = conexion.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            int id = rs.getInt("id_escuderia");
	            String nombre = rs.getString("nombre");
	            int idMotor = rs.getInt("id_proveedor_motor");
	            
	            listaEscuderias.add(new Escuderia(id, nombre, idMotor));
	        }
	        System.out.println("Escuderias consultadas.");

	    } catch (SQLException e) {
	        System.err.println("Error al listar escuderías: " + e.getMessage());
	    }

	    return listaEscuderias;
	}
    
    public boolean insertarEscuderia(String nombre, int idProveedorMotor) {
        String sql = "INSERT INTO ESCUDERIA (nombre, id_proveedor_motor) VALUES (?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idProveedorMotor);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar escudería: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEscuderia(int id, String nuevoNombre, int idProveedorMotor) {
        String sql = "UPDATE ESCUDERIA SET nombre = ?, id_proveedor_motor = ? WHERE id_escuderia = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setInt(2, idProveedorMotor);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar escudería: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarEscuderia(int id) {
        String sql = "DELETE FROM ESCUDERIA WHERE id_escuderia = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar escudería: " + e.getMessage());
            return false;
        }
    }
}