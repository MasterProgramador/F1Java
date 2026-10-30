package vista;

import servicio.ProveedorIndumentariaServicio;

import javax.swing.*;
import java.awt.*;

public class DialogoProveedorIndumentaria extends JDialog {
    private JTextField txtNombre;
    private JButton btnGuardar, btnCancelar;
    private ProveedorIndumentariaServicio servicio;
    private boolean operacionExitosa = false;
    private Integer idModificar = null;

    public DialogoProveedorIndumentaria(JFrame parent) {
        super(parent, "Registrar Proveedor de Indumentaria", true);
        servicio = new ProveedorIndumentariaServicio();
        configurarVentana();
    }

    public DialogoProveedorIndumentaria(JFrame parent, int id, String nombre) {
        super(parent, "Modificar Proveedor de Indumentaria", true);
        servicio = new ProveedorIndumentariaServicio();
        this.idModificar = id;
        configurarVentana();

        txtNombre.setText(nombre);
    }

    private void configurarVentana() {
        setSize(300, 180);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(3, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("Nombre del Proveedor:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton(idModificar == null ? "Guardar" : "Actualizar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarOActualizar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarOActualizar() {
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito;
        if (idModificar == null) {
            exito = servicio.registrarProveedorIndumentaria(nombre);
        } else {
            exito = servicio.actualizarProveedorIndumentaria(idModificar, nombre);
        }

        if (exito) {
            JOptionPane.showMessageDialog(this, "Operación realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            operacionExitosa = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isOperacionExitosa() {
        return operacionExitosa;
    }
}