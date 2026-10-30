package dao;

import conexion.ConexionBD;
import modelo.ProveedorMotor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorMotorDAO {

    public List<ProveedorMotor> listarProveedoresMotor() {
        List<ProveedorMotor> lista = new ArrayList<>();
        String sql = "SELECT p.id_proveedor, p.nombre, pm.n_chasis " +
                     "FROM PROVEEDOR p " +
                     "INNER JOIN P_MOTOR pm ON p.id_proveedor = pm.id_proveedor";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProveedorMotor pm = new ProveedorMotor(
                    rs.getInt("id_proveedor"),
                    rs.getString("nombre"),
                    rs.getString("n_chasis")
                );
                lista.add(pm);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar proveedores de motor: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarProveedorMotor(String nombre, String nChasis) {
        String sqlProveedor = "INSERT INTO PROVEEDOR (nombre) VALUES (?)";
        String sqlPMotor = "INSERT INTO P_MOTOR (id_proveedor, n_chasis) VALUES (?, ?)";

        try (Connection con = ConexionBD.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psP = con.prepareStatement(sqlProveedor, Statement.RETURN_GENERATED_KEYS)) {
                psP.setString(1, nombre);
                psP.executeUpdate();

                ResultSet rs = psP.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    try (PreparedStatement psM = con.prepareStatement(sqlPMotor)) {
                        psM.setInt(1, idGenerado);
                        psM.setString(2, nChasis);
                        psM.executeUpdate();
                    }
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                System.err.println("Error al insertar proveedor de motor: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarProveedorMotor(int idProveedor, String nombre, String nChasis) {
        String sqlProveedor = "UPDATE PROVEEDOR SET nombre = ? WHERE id_proveedor = ?";
        String sqlPMotor = "UPDATE P_MOTOR SET n_chasis = ? WHERE id_proveedor = ?";

        try (Connection con = ConexionBD.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psP = con.prepareStatement(sqlProveedor);
                 PreparedStatement psM = con.prepareStatement(sqlPMotor)) {
                
                psP.setString(1, nombre);
                psP.setInt(2, idProveedor);
                psP.executeUpdate();

                psM.setString(1, nChasis);
                psM.setInt(2, idProveedor);
                psM.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                System.err.println("Error al actualizar proveedor de motor: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProveedorMotor(int idProveedor) {
        String sqlPMotor = "DELETE FROM P_MOTOR WHERE id_proveedor = ?";
        String sqlProveedor = "DELETE FROM PROVEEDOR WHERE id_proveedor = ?";

        try (Connection con = ConexionBD.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psM = con.prepareStatement(sqlPMotor);
                 PreparedStatement psP = con.prepareStatement(sqlProveedor)) {
                
                psM.setInt(1, idProveedor);
                psM.executeUpdate();

                psP.setInt(1, idProveedor);
                psP.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                System.err.println("Error al eliminar proveedor de motor: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }
}