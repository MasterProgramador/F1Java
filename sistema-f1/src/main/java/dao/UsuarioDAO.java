package dao;

import conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean validarLogin(String nombreUsuario, String contrasenaHash) {
        String sql = "SELECT * FROM USUARIO WHERE nombre_usuario = ? AND contrasena = ?";
        
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, contrasenaHash);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Login exitoso para el usuario: " + nombreUsuario);
                    return true;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al validar el login: " + e.getMessage());
        }
        
        System.out.println("Credenciales incorrectas.");
        return false;
    }
}