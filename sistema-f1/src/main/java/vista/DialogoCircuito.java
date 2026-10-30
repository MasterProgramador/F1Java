package vista;

import servicio.CircuitoServicio;

import javax.swing.*;
import java.awt.*;

public class DialogoCircuito extends JDialog {
    private JTextField txtNombre, txtPais;
    private JButton btnGuardar, btnCancelar;
    private CircuitoServicio servicio;
    private boolean operacionExitosa = false;
    private Integer idModificar = null;

    public DialogoCircuito(JFrame parent) {
        super(parent, "Registrar Circuito", true);
        servicio = new CircuitoServicio();
        configurarVentana();
    }

    public DialogoCircuito(JFrame parent, int id, String nombre, String pais) {
        super(parent, "Modificar Circuito", true);
        servicio = new CircuitoServicio();
        this.idModificar = id;
        configurarVentana();
        txtNombre.setText(nombre);
        txtPais.setText(pais);
    }

    private void configurarVentana() {
        setSize(320, 200);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("Nombre del Circuito:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("País:"));
        txtPais = new JTextField();
        panelForm.add(txtPais);

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
        String pais = txtPais.getText().trim();

        if (nombre.isEmpty() || pais.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito;
        if (idModificar == null) {
            exito = servicio.registrarCircuito(nombre, pais);
        } else {
            exito = servicio.actualizarCircuito(idModificar, nombre, pais);
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