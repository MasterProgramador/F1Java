package vista;

import modelo.Piloto;
import servicio.PilotoServicio;

import javax.swing.*;
import java.awt.*;

public class DialogoModificarPiloto extends JDialog {
    private JTextField txtCodigo, txtNombre, txtNacionalidad;
    private JButton btnActualizar, btnCancelar;
    private PilotoServicio pilotoServicio;
    private boolean modificadoExitoso = false;

    public DialogoModificarPiloto(JFrame parent, String codigoActual, String nombreActual, String nacionalidadActual) {
        super(parent, "Modificar Piloto", true);
        pilotoServicio = new PilotoServicio();

        setSize(350, 250);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents(codigoActual, nombreActual, nacionalidadActual);
    }

    private void initComponents(String codigo, String nombre, String nacionalidad) {
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(6, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("Código (No editable):"));
        txtCodigo = new JTextField(codigo);
        txtCodigo.setEditable(false); // El código PK no debe modificarse
        panelForm.add(txtCodigo);

        panelForm.add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField(nombre);
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Nacionalidad:"));
        txtNacionalidad = new JTextField(nacionalidad);
        panelForm.add(txtNacionalidad);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnActualizar = new JButton("Actualizar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> actualizarPiloto());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void actualizarPiloto() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String nacionalidad = txtNacionalidad.getText().trim();

        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacíos.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Piloto piloto = new Piloto();
        piloto.setCodigo(codigo);
        piloto.setNombre(nombre);
        piloto.setNacionalidad(nacionalidad);

        boolean exito = pilotoServicio.actualizarPiloto(piloto);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Piloto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            modificadoExitoso = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el piloto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isModificadoExitoso() {
        return modificadoExitoso;
    }
}