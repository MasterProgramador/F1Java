package vista;

import servicio.EscuderiaServicio;

import javax.swing.*;
import java.awt.*;

public class DialogoEscuderia extends JDialog {
    private JTextField txtNombre;
    private JComboBox<Integer> cmbProveedorMotor;
    private JButton btnGuardar, btnCancelar;
    private EscuderiaServicio escuderiaServicio;
    private boolean operacionExitosa = false;
    private Integer idEscuderiaModificar = null;

    public DialogoEscuderia(JFrame parent) {
        super(parent, "Registrar Escudería", true);
        escuderiaServicio = new EscuderiaServicio();
        configurarVentana();
    }

    public DialogoEscuderia(JFrame parent, int id, String nombreActual) {
        super(parent, "Modificar Escudería", true);
        escuderiaServicio = new EscuderiaServicio();
        this.idEscuderiaModificar = id;
        configurarVentana();
        txtNombre.setText(nombreActual);
    }

    private void configurarVentana() {
        setSize(320, 240);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("Nombre de la Escudería:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Proveedor de Motor (ID):"));
        cmbProveedorMotor = new JComboBox<>(new Integer[]{1, 2});
        panelForm.add(cmbProveedorMotor);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton(idEscuderiaModificar == null ? "Guardar" : "Actualizar");
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

        int idProveedorMotor = (Integer) cmbProveedorMotor.getSelectedItem();
        boolean exito;

        if (idEscuderiaModificar == null) {
            // Llamamos al servicio pasando el nombre y el ID del motor seleccionado
            exito = escuderiaServicio.registrarEscuderia(nombre, idProveedorMotor);
        } else {
            // Llamamos al servicio pasando el ID, el nombre y el ID del motor
            exito = escuderiaServicio.actualizarEscuderia(idEscuderiaModificar, nombre, idProveedorMotor);
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