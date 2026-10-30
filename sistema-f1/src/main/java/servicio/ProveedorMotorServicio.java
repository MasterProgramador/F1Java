package servicio;

import dao.ProveedorMotorDAO;
import modelo.ProveedorMotor;

import java.util.List;

public class ProveedorMotorServicio {
    private ProveedorMotorDAO proveedorMotorDAO = new ProveedorMotorDAO();

    public List<ProveedorMotor> obtenerProveedoresMotor() {
        return proveedorMotorDAO.listarProveedoresMotor();
    }

    public boolean registrarProveedorMotor(String nombre, String nChasis) {
        if (nombre == null || nombre.trim().isEmpty() || nChasis == null || nChasis.trim().isEmpty()) {
            return false;
        }
        return proveedorMotorDAO.insertarProveedorMotor(nombre.trim(), nChasis.trim());
    }

    public boolean actualizarProveedorMotor(int idProveedor, String nombre, String nChasis) {
        if (nombre == null || nombre.trim().isEmpty() || nChasis == null || nChasis.trim().isEmpty()) {
            return false;
        }
        return proveedorMotorDAO.actualizarProveedorMotor(idProveedor, nombre.trim(), nChasis.trim());
    }

    public boolean eliminarProveedorMotor(int idProveedor) {
        return proveedorMotorDAO.eliminarProveedorMotor(idProveedor);
    }
}