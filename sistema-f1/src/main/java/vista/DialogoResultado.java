package vista;

import servicio.ResultadoServicio;

import javax.swing.*;
import java.awt.*;

public class DialogoResultado extends JDialog {
    private JTextField txtIdGp, txtCodigoPiloto, txtIdAuto, txtPosicion, txtVueltas, txtPuntos;
    private JButton btnGuardar, btnCancelar;
    private ResultadoServicio servicio;
    private boolean operacionExitosa = false;
    private boolean esModificacion = false;

    public DialogoResultado(JFrame parent) {
        super(parent, "Registrar Resultado", true);
        servicio = new ResultadoServicio();
        configurarVentana();
    }

    public DialogoResultado(JFrame parent, int idGp, String codigoPiloto, int idAuto, int posicion, int vueltas, int puntos) {
        super(parent, "Modificar Resultado", true);
        servicio = new ResultadoServicio();
        this.esModificacion = true;
        configurarVentana();

        txtIdGp.setText(String.valueOf(idGp));
        txtCodigoPiloto.setText(codigoPiloto);
        txtIdAuto.setText(String.valueOf(idAuto));
        txtPosicion.setText(String.valueOf(posicion));
        txtVueltas.setText(String.valueOf(vueltas));
        txtPuntos.setText(String.valueOf(puntos));

        // Las claves primarias no deben editarse al modificar
        txtIdGp.setEditable(false);
        txtCodigoPiloto.setEditable(false);
    }

    private void configurarVentana() {
        setSize(360, 320);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("ID Gran Premio:"));
        txtIdGp = new JTextField();
        panelForm.add(txtIdGp);

        panelForm.add(new JLabel("Código Piloto (3 letras):"));
        txtCodigoPiloto = new JTextField();
        panelForm.add(txtCodigoPiloto);

        panelForm.add(new JLabel("ID Auto:"));
        txtIdAuto = new JTextField();
        panelForm.add(txtIdAuto);

        panelForm.add(new JLabel("Posición Final:"));
        txtPosicion = new JTextField();
        panelForm.add(txtPosicion);

        panelForm.add(new JLabel("Vueltas Completadas:"));
        txtVueltas = new JTextField();
        panelForm.add(txtVueltas);

        panelForm.add(new JLabel("Puntos Obtenidos:"));
        txtPuntos = new JTextField();
        panelForm.add(txtPuntos);

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
            int idAuto = Integer.parseInt(txtIdAuto.getText().trim());
            int posicion = Integer.parseInt(txtPosicion.getText().trim());
            int vueltas = Integer.parseInt(txtVueltas.getText().trim());
            int puntos = Integer.parseInt(txtPuntos.getText().trim());

            boolean exito;
            if (!esModificacion) {
                exito = servicio.registrarResultado(idGp, codigoPiloto, idAuto, posicion, vueltas, puntos);
            } else {
                exito = servicio.actualizarResultado(idGp, codigoPiloto, idAuto, posicion, vueltas, puntos);
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Operación realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                operacionExitosa = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Asegúrese de ingresar valores numéricos válidos en los campos correspondientes.", "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean isOperacionExitosa() {
        return operacionExitosa;
    }
}