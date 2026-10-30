package vista;

import servicio.VueltaServicio;

import javax.swing.*;
import java.awt.*;

public class DialogoVuelta extends JDialog {
    private JTextField txtIdGp, txtCodigoPiloto, txtNumeroVuelta, txtTiempo;
    private JButton btnGuardar, btnCancelar;
    private VueltaServicio servicio;
    private boolean operacionExitosa = false;
    private boolean esModificacion = false;

    public DialogoVuelta(JFrame parent) {
        super(parent, "Registrar Vuelta", true);
        servicio = new VueltaServicio();
        configurarVentana();
    }

    public DialogoVuelta(JFrame parent, int idGp, String codigoPiloto, int numeroVuelta, String tiempo) {
        super(parent, "Modificar Vuelta", true);
        servicio = new VueltaServicio();
        this.esModificacion = true;
        configurarVentana();

        txtIdGp.setText(String.valueOf(idGp));
        txtCodigoPiloto.setText(codigoPiloto);
        txtNumeroVuelta.setText(String.valueOf(numeroVuelta));
        txtTiempo.setText(tiempo);

        // Claves primarias bloqueadas al modificar
        txtIdGp.setEditable(false);
        txtCodigoPiloto.setEditable(false);
        txtNumeroVuelta.setEditable(false);
    }

    private void configurarVentana() {
        setSize(320, 260);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("ID Gran Premio:"));
        txtIdGp = new JTextField();
        panelForm.add(txtIdGp);

        panelForm.add(new JLabel("Código Piloto:"));
        txtCodigoPiloto = new JTextField();
        panelForm.add(txtCodigoPiloto);

        panelForm.add(new JLabel("Número de Vuelta:"));
        txtNumeroVuelta = new JTextField();
        panelForm.add(txtNumeroVuelta);

        panelForm.add(new JLabel("Tiempo (ej. 1:30.250):"));
        txtTiempo = new JTextField();
        panelForm.add(txtTiempo);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton(esModificacion ? "Actualizar" : "Guardar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarOActualizar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarOActualizar() {
        try {
            int idGp = Integer.parseInt(txtIdGp.getText().trim());
            String codigoPiloto = txtCodigoPiloto.getText().trim();
            int numeroVuelta = Integer.parseInt(txtNumeroVuelta.getText().trim());
            String tiempo = txtTiempo.getText().trim();

            if (tiempo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo de tiempo no puede estar vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean exito;
            if (!esModificacion) {
                exito = servicio.registrarVuelta(idGp, codigoPiloto, numeroVuelta, tiempo);
            } else {
                exito = servicio.actualizarVuelta(idGp, codigoPiloto, numeroVuelta, tiempo);
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                operacionExitosa = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor verifique que los campos numéricos sean correctos.", "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean isOperacionExitosa() {
        return operacionExitosa;
    }
}