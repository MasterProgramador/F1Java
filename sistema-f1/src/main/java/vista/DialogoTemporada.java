package vista;

import servicio.TemporadaServicio;

import javax.swing.*;
import java.awt.*;

public class DialogoTemporada extends JDialog {
    private JTextField txtAnio;
    private JButton btnGuardar, btnCancelar;
    private TemporadaServicio servicio;
    private boolean operacionExitosa = false;
    private Integer idModificar = null;

    public DialogoTemporada(JFrame parent) {
        super(parent, "Registrar Temporada", true);
        servicio = new TemporadaServicio();
        configurarVentana();
    }

    public DialogoTemporada(JFrame parent, int id, int anio) {
        super(parent, "Modificar Temporada", true);
        servicio = new TemporadaServicio();
        this.idModificar = id;
        configurarVentana();
        txtAnio.setText(String.valueOf(anio));
    }

    private void configurarVentana() {
        setSize(300, 180);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("Año de la Temporada:"));
        txtAnio = new JTextField();
        panelForm.add(txtAnio);

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
        try {
            int anio = Integer.parseInt(txtAnio.getText().trim());

            boolean exito;
            if (idModificar == null) {
                exito = servicio.registrarTemporada(anio);
            } else {
                exito = servicio.actualizarTemporada(idModificar, anio);
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                operacionExitosa = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos (el año puede estar repetido).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un año válido en número entero.", "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean isOperacionExitosa() {
        return operacionExitosa;
    }
}