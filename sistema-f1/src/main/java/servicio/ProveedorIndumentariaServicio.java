package servicio;

import dao.ProveedorIndumentariaDAO;
import modelo.ProveedorIndumentaria;

import java.util.List;

public class ProveedorIndumentariaServicio {
    private ProveedorIndumentariaDAO dao = new ProveedorIndumentariaDAO();

    public List<ProveedorIndumentaria> obtenerProveedoresIndumentaria() {
        return dao.listarProveedoresIndumentaria();
    }

    public boolean registrarProveedorIndumentaria(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        return dao.insertarProveedorIndumentaria(nombre.trim());
    }

    public boolean actualizarProveedorIndumentaria(int idProveedor, String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        return dao.actualizarProveedorIndumentaria(idProveedor, nombre.trim());
    }

    public boolean eliminarProveedorIndumentaria(int idProveedor) {
        return dao.eliminarProveedorIndumentaria(idProveedor);
    }
}