package vista;

import servicio.GranPremioServicio;

import javax.swing.*;
import java.awt.*;

public class DialogoGranPremio extends JDialog {
    private JTextField txtNombre;
    private JTextField txtFecha;
    private JButton btnGuardar, btnCancelar;
    private GranPremioServicio granPremioServicio;
    private boolean operacionExitosa = false;
    private Integer idGpModificar = null;

    public DialogoGranPremio(JFrame parent) {
        super(parent, "Registrar Gran Premio", true);
        granPremioServicio = new GranPremioServicio();
        configurarVentana();
    }

    public DialogoGranPremio(JFrame parent, int id, String nombreActual, String fechaActual) {
        super(parent, "Modificar Gran Premio", true);
        granPremioServicio = new GranPremioServicio();
        this.idGpModificar = id;
        configurarVentana();
        txtNombre.setText(nombreActual);
        txtFecha.setText(fechaActual);
    }

    private void configurarVentana() {
        setSize(320, 220);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("Nombre del Gran Premio:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Fecha (YYYY-MM-DD):"));
        txtFecha = new JTextField();
        panelForm.add(txtFecha);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton(idGpModificar == null ? "Guardar" : "Actualizar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarOActualizar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarOActualizar() {
        String nombre = txtNombre.getText().trim();
        String fechaStr = txtFecha.getText().trim();

        if (nombre.isEmpty() || fechaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito;
        java.time.LocalDate fecha = java.time.LocalDate.parse(fechaStr);

        if (idGpModificar == null) {
            exito = granPremioServicio.registrarGranPremio(nombre, fecha, 1, 1);
        } else {
            // Aseguramos que el ID se pase como int primitivo (int)idGpModificar
            exito = granPremioServicio.actualizarGranPremio((int) idGpModificar, nombre, fecha, 1, 1);
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