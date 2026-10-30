package dao;

import conexion.ConexionBD;
import modelo.ProveedorIndumentaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorIndumentariaDAO {

    public List<ProveedorIndumentaria> listarProveedoresIndumentaria() {
        List<ProveedorIndumentaria> lista = new ArrayList<>();
        String sql = "SELECT p.id_proveedor, p.nombre " +
                     "FROM PROVEEDOR p " +
                     "INNER JOIN P_INDUMENTARIA pi ON p.id_proveedor = pi.id_proveedor";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProveedorIndumentaria pi = new ProveedorIndumentaria(
                    rs.getInt("id_proveedor"),
                    rs.getString("nombre")
                );
                lista.add(pi);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar proveedores de indumentaria: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarProveedorIndumentaria(String nombre) {
        String sqlProveedor = "INSERT INTO PROVEEDOR (nombre) VALUES (?)";
        String sqlPIndumentaria = "INSERT INTO P_INDUMENTARIA (id_proveedor) VALUES (?)";

        try (Connection con = ConexionBD.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psP = con.prepareStatement(sqlProveedor, Statement.RETURN_GENERATED_KEYS)) {
                psP.setString(1, nombre);
                psP.executeUpdate();

                ResultSet rs = psP.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    try (PreparedStatement psI = con.prepareStatement(sqlPIndumentaria)) {
                        psI.setInt(1, idGenerado);
                        psI.executeUpdate();
                    }
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                System.err.println("Error al insertar proveedor de indumentaria: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarProveedorIndumentaria(int idProveedor, String nombre) {
        String sql = "UPDATE PROVEEDOR SET nombre = ? WHERE id_proveedor = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idProveedor);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor de indumentaria: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProveedorIndumentaria(int idProveedor) {
        String sqlPIndumentaria = "DELETE FROM P_INDUMENTARIA WHERE id_proveedor = ?";
        String sqlProveedor = "DELETE FROM PROVEEDOR WHERE id_proveedor = ?";

        try (Connection con = ConexionBD.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psI = con.prepareStatement(sqlPIndumentaria);
                 PreparedStatement psP = con.prepareStatement(sqlProveedor)) {
                
                psI.setInt(1, idProveedor);
                psI.executeUpdate();

                psP.setInt(1, idProveedor);
                psP.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                System.err.println("Error al eliminar proveedor de indumentaria: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }
}