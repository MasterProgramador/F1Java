package vista;

import com.formdev.flatlaf.FlatLightLaf;
import servicio.UsuarioServicio;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private UsuarioServicio usuarioServicio;

    public VentanaLogin() {
        usuarioServicio = new UsuarioServicio();
        setTitle("F1 Management - Iniciar Sesión");
        setSize(350, 230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel central con los campos de texto
        JPanel panelFormulario = new JPanel(new GridLayout(4, 1, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelFormulario.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelFormulario.add(txtUsuario);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelFormulario.add(txtPassword);

        add(panelFormulario, BorderLayout.CENTER);

        // Panel inferior con el botón de acceso
        JPanel panelBoton = new JPanel();
        panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        btnIngresar = new JButton("Ingresar");
        panelBoton.add(btnIngresar);
        add(panelBoton, BorderLayout.SOUTH);

        // Evento del botón de login
        btnIngresar.addActionListener(e -> validarAcceso());
    }

    private void validarAcceso() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        boolean autenticado = usuarioServicio.autenticar(usuario, password);

        if (autenticado) {
            JOptionPane.showMessageDialog(this, "¡Login exitoso! Bienvenido, " + usuario, "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);
            
            // Abrir la ventana principal y cerrar el login
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
            ventanaPrincipal.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }

    // Método main para arrancar la aplicación aplicando FlatLaf
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo inicializar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new VentanaLogin().setVisible(true);
        });
    }
}