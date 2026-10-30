package vista;

import servicio.ProveedorMotorServicio;

import javax.swing.*;
import java.awt.*;

public class DialogoProveedorMotor extends JDialog {
    private JTextField txtNombre, txtNChasis;
    private JButton btnGuardar, btnCancelar;
    private ProveedorMotorServicio servicio;
    private boolean operacionExitosa = false;
    private Integer idModificar = null;

    public DialogoProveedorMotor(JFrame parent) {
        super(parent, "Registrar Proveedor de Motor", true);
        servicio = new ProveedorMotorServicio();
        configurarVentana();
    }

    public DialogoProveedorMotor(JFrame parent, int id, String nombre, String nChasis) {
        super(parent, "Modificar Proveedor de Motor", true);
        servicio = new ProveedorMotorServicio();
        this.idModificar = id;
        configurarVentana();

        txtNombre.setText(nombre);
        txtNChasis.setText(nChasis);
    }

    private void configurarVentana() {
        setSize(340, 200);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("Nombre del Proveedor:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("N° de Chasis:"));
        txtNChasis = new JTextField();
        panelForm.add(txtNChasis);

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
        String nChasis = txtNChasis.getText().trim();

        if (nombre.isEmpty() || nChasis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito;
        if (idModificar == null) {
            exito = servicio.registrarProveedorMotor(nombre, nChasis);
        } else {
            exito = servicio.actualizarProveedorMotor(idModificar, nombre, nChasis);
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