package vista;

import modelo.Piloto;
import servicio.PilotoServicio;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DialogoRegistrarPiloto extends JDialog {
    private JTextField txtCodigo, txtNombre, txtNacionalidad, txtFechaNacimiento;
    private JComboBox<Integer> cmbProveedorIndumentaria;
    private JButton btnGuardar, btnCancelar;
    private PilotoServicio pilotoServicio;
    private boolean guardadoExitoso = false;

    public DialogoRegistrarPiloto(JFrame parent) {
        super(parent, "Registrar Nuevo Piloto", true);
        pilotoServicio = new PilotoServicio();
        
        setSize(350, 370);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        JPanel panelForm = new JPanel(new GridLayout(10, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        panelForm.add(new JLabel("Código (Exactamente 3 letras, ej: VER):"));
        txtCodigo = new JTextField();
        panelForm.add(txtCodigo);

        panelForm.add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Nacionalidad:"));
        txtNacionalidad = new JTextField();
        panelForm.add(txtNacionalidad);

        panelForm.add(new JLabel("Fecha de Nacimiento (AAAA-MM-DD):"));
        txtFechaNacimiento = new JTextField();
        panelForm.add(txtFechaNacimiento);

        panelForm.add(new JLabel("Proveedor de Indumentaria (ID):"));
        cmbProveedorIndumentaria = new JComboBox<>(new Integer[]{3,4});
        panelForm.add(cmbProveedorIndumentaria);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarPiloto());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarPiloto() {
        String codigo = txtCodigo.getText().trim().toUpperCase();
        String nombre = txtNombre.getText().trim();
        String nacionalidad = txtNacionalidad.getText().trim();
        String fechaStr = txtFechaNacimiento.getText().trim();

        if (codigo.length() != 3) {
            JOptionPane.showMessageDialog(this, "El código del piloto debe tener exactamente 3 caracteres.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (nombre.isEmpty() || nacionalidad.isEmpty() || fechaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios, incluyendo la fecha.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        LocalDate fechaNacimiento = null;
        try {
            if (!fechaStr.isEmpty()) {
                fechaNacimiento = LocalDate.parse(fechaStr);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use AAAA-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idProveedor = (Integer) cmbProveedorIndumentaria.getSelectedItem();

        Piloto nuevoPiloto = new Piloto();
        nuevoPiloto.setCodigo(codigo);
        nuevoPiloto.setNombre(nombre);
        nuevoPiloto.setNacionalidad(nacionalidad);
        nuevoPiloto.setFechaNacimiento(fechaNacimiento);
        nuevoPiloto.setIdProveedorIndumentaria(idProveedor);
        
        boolean exito = pilotoServicio.registrarPiloto(nuevoPiloto);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Piloto registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            guardadoExitoso = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el piloto. Verifique si el código ya existe.", "Error en Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }
}