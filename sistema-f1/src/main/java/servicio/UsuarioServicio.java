package servicio;

import dao.UsuarioDAO;
import org.apache.commons.codec.digest.DigestUtils; //importacion de xml

public class UsuarioServicio {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean autenticar(String nombreUsuario, String passwordPlana) {
        if (nombreUsuario == null || nombreUsuario.isEmpty() || passwordPlana == null || passwordPlana.isEmpty()) {
            return false;
        }

        String passwordHash = DigestUtils.sha256Hex(passwordPlana); //se cifra la contraseña en texto plano

        return usuarioDAO.validarLogin(nombreUsuario, passwordHash);
    }
}