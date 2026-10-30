package vista;

import servicio.AutoServicio;

import javax.swing.*;
import java.awt.*;

public class DialogoAuto extends JDialog {
    private JTextField txtModelo, txtAnio, txtIdEscuderia;
    private JButton btnGuardar, btnCancelar;
    private AutoServicio servicio;
    private boolean operacionExitosa = false;
    private Integer idModificar = null;

    public DialogoAuto(JFrame parent) {
        super(parent, "Registrar Auto", true);
        servicio = new AutoServicio();
        configurarVentana();
    }

    public DialogoAuto(JFrame parent, int id, String modelo, int anio, int idEscuderia) {
        super(parent, "Modificar Auto", true);
        servicio = new AutoServicio();
        this.idModificar = id;
        configurarVentana();
        txtModelo.setText(modelo);
        txtAnio.setText(String.valueOf(anio));
        txtIdEscuderia.setText(String.valueOf(idEscuderia));
    }

    private void configurarVentana() {
        setSize(320, 240);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(6, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("Modelo del Auto:"));
        txtModelo = new JTextField();
        panelForm.add(txtModelo);

        panelForm.add(new JLabel("Año de Fabricación:"));
        txtAnio = new JTextField();
        panelForm.add(txtAnio);

        panelForm.add(new JLabel("ID Escudería:"));
        txtIdEscuderia = new JTextField();
        panelForm.add(txtIdEscuderia);

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
            String modelo = txtModelo.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());
            int idEscuderia = Integer.parseInt(txtIdIdEscuderiaValidado()); // aux

            boolean exito;
            if (idModificar == null) {
                exito = servicio.registrarAuto(modelo, anio, idEscuderia);
            } else {
                exito = servicio.actualizarAuto(idModificar, modelo, anio, idEscuderia);
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                operacionExitosa = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique que el año y el ID de escudería sean números enteros válidos.", "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String txtIdIdEscuderiaValidado() {
        return txtIdEscuderia.getText().trim();
    }

    public boolean isOperacionExitosa() {
        return operacionExitosa;
    }
}