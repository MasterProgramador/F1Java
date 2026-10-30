package vista;

import modelo.Circuito;
import modelo.Escuderia;
import modelo.GranPremio;
import modelo.Piloto;
import modelo.Auto;
import servicio.CircuitoServicio;
import servicio.EscuderiaServicio;
import servicio.GranPremioServicio;
import servicio.PilotoServicio;
import servicio.TemporadaServicio;
import servicio.AutoServicio;
import modelo.Resultado;
import servicio.ResultadoServicio;
import modelo.Vuelta;
import servicio.VueltaServicio;
import modelo.ProveedorMotor;
import servicio.ProveedorMotorServicio;
import modelo.ProveedorIndumentaria;
import servicio.ProveedorIndumentariaServicio;
import servicio.ReporteServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private JTabbedPane tabbedPane;
    
    private JTable tablaPilotos;
    private DefaultTableModel modeloTablaPilotos;
    private PilotoServicio pilotoServicio;

    private JTable tablaEscuderias;
    private DefaultTableModel modeloTablaEscuderias;
    private EscuderiaServicio escuderiaServicio;

    private JTable tablaGrandesPremios;
    private DefaultTableModel modeloTablaGrandesPremios;
    private GranPremioServicio granPremioServicio;
    
    private JTable tablaCircuitos;
    private DefaultTableModel modeloTablaCircuitos;
    private CircuitoServicio circuitoServicio;
    
    private JTable tablaTemporadas;
    private DefaultTableModel modeloTablaTemporadas;
    private TemporadaServicio temporadaServicio;
    
    private JTable tablaAutos;
    private DefaultTableModel modeloTablaAutos;
    private AutoServicio autoServicio;
    
    private JTable tablaResultados;
    private DefaultTableModel modeloTablaResultados;
    private ResultadoServicio resultadoServicio = new ResultadoServicio();
    
    private JTable tablaVueltas;
    private DefaultTableModel modeloTablaVueltas;
    private VueltaServicio vueltaServicio = new VueltaServicio();
    
    private JTable tablaProvMotor;
    private DefaultTableModel modeloTablaProvMotor;
    private ProveedorMotorServicio provMotorServicio = new ProveedorMotorServicio();
    
    private JTable tablaProvIndumentaria;
    private DefaultTableModel modeloTablaProvIndumentaria;
    private ProveedorIndumentariaServicio provIndumentariaServicio = new ProveedorIndumentariaServicio();
    
    private JTable tablaClasificacion;
    private DefaultTableModel modeloTablaClasificacion;
    private ReporteServicio reporteServicio = new ReporteServicio();
    
    private JTable tablaRankingGp;
    private DefaultTableModel modeloTablaRankingGp;

    public VentanaPrincipal() {
        pilotoServicio = new PilotoServicio();
        escuderiaServicio = new EscuderiaServicio();
        granPremioServicio = new GranPremioServicio();
        circuitoServicio = new CircuitoServicio();
        temporadaServicio = new TemporadaServicio();
        autoServicio = new AutoServicio();

        setTitle("F1 Management - Panel Principal");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // --- PESTAÑA PILOTOS ---
        JPanel panelPilotos = new JPanel(new BorderLayout());
        modeloTablaPilotos = new DefaultTableModel(new String[]{"Código", "Nombre", "Nacionalidad"}, 0);
        tablaPilotos = new JTable(modeloTablaPilotos);
        panelPilotos.add(new JScrollPane(tablaPilotos), BorderLayout.CENTER);

        JPanel panelSurPilotos = new JPanel();
        JButton btnNuevoPiloto = new JButton("Nuevo Piloto");
        JButton btnModificarPiloto = new JButton("Modificar");
        JButton btnEliminarPiloto = new JButton("Eliminar");
        JButton btnActualizarPilotos = new JButton("Actualizar Tabla");

        panelSurPilotos.add(btnNuevoPiloto);
        panelSurPilotos.add(btnModificarPiloto);
        panelSurPilotos.add(btnEliminarPiloto);
        panelSurPilotos.add(btnActualizarPilotos);
        
        panelPilotos.add(panelSurPilotos, BorderLayout.SOUTH);

        btnNuevoPiloto.addActionListener(e -> {
            DialogoRegistrarPiloto dialogo = new DialogoRegistrarPiloto(this);
            dialogo.setVisible(true);
            if (dialogo.isGuardadoExitoso()) {
                cargarDatos();
            }
        });

        btnModificarPiloto.addActionListener(e -> {
            int filaSeleccionada = tablaPilotos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un piloto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String codigo = modeloTablaPilotos.getValueAt(filaSeleccionada, 0).toString();
            String nombre = modeloTablaPilotos.getValueAt(filaSeleccionada, 1).toString();
            String nacionalidad = modeloTablaPilotos.getValueAt(filaSeleccionada, 2).toString();

            DialogoModificarPiloto dialogo = new DialogoModificarPiloto(this, codigo, nombre, nacionalidad);
            dialogo.setVisible(true);
            if (dialogo.isModificadoExitoso()) {
                cargarDatos();
            }
        });
        
        btnEliminarPiloto.addActionListener(e -> {
            int filaSeleccionada = tablaPilotos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un piloto de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String codigo = modeloTablaPilotos.getValueAt(filaSeleccionada, 0).toString();
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                    "¿Está seguro de eliminar al piloto con código " + codigo + "?", 
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean exito = pilotoServicio.eliminarPiloto(codigo);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Piloto eliminado con éxito.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el piloto. Es posible que tenga registros asociados.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnActualizarPilotos.addActionListener(e -> cargarDatos());

        // --- PESTAÑA ESCUDERÍAS ---
        JPanel panelEscuderias = new JPanel(new BorderLayout());
        modeloTablaEscuderias = new DefaultTableModel(new String[]{"ID", "Escudería"}, 0);
        tablaEscuderias = new JTable(modeloTablaEscuderias);
        panelEscuderias.add(new JScrollPane(tablaEscuderias), BorderLayout.CENTER);

        JPanel panelSurEscuderias = new JPanel();
        JButton btnNuevaEscuderia = new JButton("Nueva Escudería");
        JButton btnModificarEscuderia = new JButton("Modificar");
        JButton btnEliminarEscuderia = new JButton("Eliminar");
        JButton btnActualizarEscuderias = new JButton("Actualizar Tabla");

        panelSurEscuderias.add(btnNuevaEscuderia);
        panelSurEscuderias.add(btnModificarEscuderia);
        panelSurEscuderias.add(btnEliminarEscuderia);
        panelSurEscuderias.add(btnActualizarEscuderias);
        panelEscuderias.add(panelSurEscuderias, BorderLayout.SOUTH);

        btnNuevaEscuderia.addActionListener(e -> {
            DialogoEscuderia dialogo = new DialogoEscuderia(this);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnModificarEscuderia.addActionListener(e -> {
            int fila = tablaEscuderias.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una escudería de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaEscuderias.getValueAt(fila, 0).toString());
            String nombre = modeloTablaEscuderias.getValueAt(fila, 1).toString();

            DialogoEscuderia dialogo = new DialogoEscuderia(this, id, nombre);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnEliminarEscuderia.addActionListener(e -> {
            int fila = tablaEscuderias.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una escudería para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaEscuderias.getValueAt(fila, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta escudería?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (escuderiaServicio.eliminarEscuderia(id)) {
                    JOptionPane.showMessageDialog(this, "Escudería eliminada con éxito.");
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar (puede tener autos o datos asociados).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnActualizarEscuderias.addActionListener(e -> cargarDatos());

        // --- PESTAÑA GRANDES PREMIOS ---
        JPanel panelGp = new JPanel(new BorderLayout());
        modeloTablaGrandesPremios = new DefaultTableModel(new String[]{"ID", "Gran Premio", "Fecha"}, 0);
        tablaGrandesPremios = new JTable(modeloTablaGrandesPremios);
        panelGp.add(new JScrollPane(tablaGrandesPremios), BorderLayout.CENTER);

        JPanel panelSurGp = new JPanel();
        JButton btnNuevoGp = new JButton("Nuevo GP");
        JButton btnModificarGp = new JButton("Modificar");
        JButton btnEliminarGp = new JButton("Eliminar");
        JButton btnActualizarGp = new JButton("Actualizar Tabla");

        panelSurGp.add(btnNuevoGp);
        panelSurGp.add(btnModificarGp);
        panelSurGp.add(btnEliminarGp);
        panelSurGp.add(btnActualizarGp);
        panelGp.add(panelSurGp, BorderLayout.SOUTH);

        btnNuevoGp.addActionListener(e -> {
            DialogoGranPremio dialogo = new DialogoGranPremio(this);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnModificarGp.addActionListener(e -> {
            int fila = tablaGrandesPremios.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un Gran Premio de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaGrandesPremios.getValueAt(fila, 0).toString());
            String nombre = modeloTablaGrandesPremios.getValueAt(fila, 1).toString();
            String fecha = modeloTablaGrandesPremios.getValueAt(fila, 2).toString();

            DialogoGranPremio dialogo = new DialogoGranPremio(this, id, nombre, fecha);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnEliminarGp.addActionListener(e -> {
            int fila = tablaGrandesPremios.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un Gran Premio para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaGrandesPremios.getValueAt(fila, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este Gran Premio?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (granPremioServicio.eliminarGranPremio(id)) {
                    JOptionPane.showMessageDialog(this, "Gran Premio eliminado con éxito.");
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el Gran Premio.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnActualizarGp.addActionListener(e -> cargarDatos());
        
        // --- PESTAÑA CIRCUITOS ---
        JPanel panelCircuitos = new JPanel(new BorderLayout());
        modeloTablaCircuitos = new DefaultTableModel(new String[]{"ID", "Nombre", "Pais"}, 0);
        tablaCircuitos = new JTable(modeloTablaCircuitos);
        panelCircuitos.add(new JScrollPane(tablaCircuitos), BorderLayout.CENTER);

        JPanel panelSurCircuito = new JPanel();
        JButton btnNuevoCircuito = new JButton("Nuevo Circuito");
        JButton btnModificarCircuito = new JButton("Modificar");
        JButton btnEliminarCircuito = new JButton("Eliminar");
        JButton btnActualizarCircuitos = new JButton("Actualizar Tabla");

        panelSurCircuito.add(btnNuevoCircuito);
        panelSurCircuito.add(btnModificarCircuito);
        panelSurCircuito.add(btnEliminarCircuito);
        panelSurCircuito.add(btnActualizarCircuitos);
        panelCircuitos.add(panelSurCircuito, BorderLayout.SOUTH);

        btnNuevoCircuito.addActionListener(e -> {
            DialogoCircuito dialogo = new DialogoCircuito(this);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnModificarCircuito.addActionListener(e -> {
            int fila = tablaCircuitos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un circuito de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaCircuitos.getValueAt(fila, 0).toString());
            String nombre = modeloTablaCircuitos.getValueAt(fila, 1).toString();
            String pais = modeloTablaCircuitos.getValueAt(fila, 2).toString();

            DialogoCircuito dialogo = new DialogoCircuito(this, id, nombre, pais);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnEliminarCircuito.addActionListener(e -> {
            int fila = tablaCircuitos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un circuito para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaCircuitos.getValueAt(fila, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este circuito?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (circuitoServicio.eliminarCircuito(id)) {
                    JOptionPane.showMessageDialog(this, "Circuito eliminado con éxito.");
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el circuito.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnActualizarCircuitos.addActionListener(e -> cargarDatos());
        
     // --- PESTAÑA TEMPORADAS ---
        JPanel panelTemporadas = new JPanel(new BorderLayout());
        modeloTablaTemporadas = new DefaultTableModel(new String[]{"ID", "Año"}, 0);
        tablaTemporadas = new JTable(modeloTablaTemporadas);
        panelTemporadas.add(new JScrollPane(tablaTemporadas), BorderLayout.CENTER);

        JPanel panelSurTemporadas = new JPanel();
        JButton btnNuevaTemporada = new JButton("Nueva Temporada");
        JButton btnModificarTemporada = new JButton("Modificar");
        JButton btnEliminarTemporada = new JButton("Eliminar");
        JButton btnActualizarTemporadas = new JButton("Actualizar Tabla");

        panelSurTemporadas.add(btnNuevaTemporada);
        panelSurTemporadas.add(btnModificarTemporada);
        panelSurTemporadas.add(btnEliminarTemporada);
        panelSurTemporadas.add(btnActualizarTemporadas);
        panelTemporadas.add(panelSurTemporadas, BorderLayout.SOUTH);

        btnNuevaTemporada.addActionListener(e -> {
            DialogoTemporada dialogo = new DialogoTemporada(this);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnModificarTemporada.addActionListener(e -> {
            int fila = tablaTemporadas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una temporada de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaTemporadas.getValueAt(fila, 0).toString());
            int anio = Integer.parseInt(modeloTablaTemporadas.getValueAt(fila, 1).toString());

            DialogoTemporada dialogo = new DialogoTemporada(this, id, anio);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnEliminarTemporada.addActionListener(e -> {
            int fila = tablaTemporadas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una temporada para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaTemporadas.getValueAt(fila, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta temporada?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (temporadaServicio.eliminarTemporada(id)) {
                    JOptionPane.showMessageDialog(this, "Temporada eliminada con éxito.");
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar (puede tener Grandes Premios asociados).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnActualizarTemporadas.addActionListener(e -> cargarDatos());
        
     // --- PESTAÑA AUTOS ---
        JPanel panelAutos = new JPanel(new BorderLayout());
        modeloTablaAutos = new DefaultTableModel(new String[]{"ID", "Modelo", "Año", "ID Escudería"}, 0);
        tablaAutos = new JTable(modeloTablaAutos);
        panelAutos.add(new JScrollPane(tablaAutos), BorderLayout.CENTER);

        JPanel panelSurAutos = new JPanel();
        JButton btnNuevoAuto = new JButton("Nuevo Auto");
        JButton btnModificarAuto = new JButton("Modificar");
        JButton btnEliminarAuto = new JButton("Eliminar");
        JButton btnActualizarAutos = new JButton("Actualizar Tabla");

        panelSurAutos.add(btnNuevoAuto);
        panelSurAutos.add(btnModificarAuto);
        panelSurAutos.add(btnEliminarAuto);
        panelSurAutos.add(btnActualizarAutos);
        panelAutos.add(panelSurAutos, BorderLayout.SOUTH);

        btnNuevoAuto.addActionListener(e -> {
            DialogoAuto dialogo = new DialogoAuto(this);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnModificarAuto.addActionListener(e -> {
            int fila = tablaAutos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un auto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaAutos.getValueAt(fila, 0).toString());
            String modelo = modeloTablaAutos.getValueAt(fila, 1).toString();
            int anio = Integer.parseInt(modeloTablaAutos.getValueAt(fila, 2).toString());
            int idEscuderia = Integer.parseInt(modeloTablaAutos.getValueAt(fila, 3).toString());

            DialogoAuto dialogo = new DialogoAuto(this, id, modelo, anio, idEscuderia);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnEliminarAuto.addActionListener(e -> {
            int fila = tablaAutos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un auto para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaAutos.getValueAt(fila, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este auto?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (autoServicio.eliminarAuto(id)) {
                    JOptionPane.showMessageDialog(this, "Auto eliminado con éxito.");
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar (puede tener registros o resultados asociados).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnActualizarAutos.addActionListener(e -> cargarDatos());
        
     // --- PESTAÑA RESULTADOS ---
        JPanel panelResultados = new JPanel(new BorderLayout());
        modeloTablaResultados = new DefaultTableModel(new String[]{"ID GP", "Piloto", "ID Auto", "Posición", "Vueltas", "Puntos"}, 0);
        tablaResultados = new JTable(modeloTablaResultados);
        panelResultados.add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        JPanel panelSurResultados = new JPanel();
        JButton btnNuevoResultado = new JButton("Nuevo Resultado");
        JButton btnModificarResultado = new JButton("Modificar");
        JButton btnEliminarResultado = new JButton("Eliminar");
        JButton btnActualizarResultados = new JButton("Actualizar Tabla");

        panelSurResultados.add(btnNuevoResultado);
        panelSurResultados.add(btnModificarResultado);
        panelSurResultados.add(btnEliminarResultado);
        panelSurResultados.add(btnActualizarResultados);
        panelResultados.add(panelSurResultados, BorderLayout.SOUTH);

        btnNuevoResultado.addActionListener(e -> {
            DialogoResultado dialogo = new DialogoResultado(this);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnModificarResultado.addActionListener(e -> {
            int fila = tablaResultados.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un resultado de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idGp = Integer.parseInt(modeloTablaResultados.getValueAt(fila, 0).toString());
            String codigoPiloto = modeloTablaResultados.getValueAt(fila, 1).toString();
            int idAuto = Integer.parseInt(modeloTablaResultados.getValueAt(fila, 2).toString());
            int posicion = Integer.parseInt(modeloTablaResultados.getValueAt(fila, 3).toString());
            int vueltas = Integer.parseInt(modeloTablaResultados.getValueAt(fila, 4).toString());
            int puntos = Integer.parseInt(modeloTablaResultados.getValueAt(fila, 5).toString());

            DialogoResultado dialogo = new DialogoResultado(this, idGp, codigoPiloto, idAuto, posicion, vueltas, puntos);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnEliminarResultado.addActionListener(e -> {
            int fila = tablaResultados.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un resultado para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idGp = Integer.parseInt(modeloTablaResultados.getValueAt(fila, 0).toString());
            String codigoPiloto = modeloTablaResultados.getValueAt(fila, 1).toString();

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el resultado del piloto " + codigoPiloto + " en el GP " + idGp + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (resultadoServicio.eliminarResultado(idGp, codigoPiloto)) {
                    JOptionPane.showMessageDialog(this, "Resultado eliminado con éxito.");
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el resultado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnActualizarResultados.addActionListener(e -> cargarDatos());
        
     // --- PESTAÑA VUELTAS ---
        JPanel panelVueltas = new JPanel(new BorderLayout());
        modeloTablaVueltas = new DefaultTableModel(new String[]{"ID GP", "Piloto", "N° Vuelta", "Tiempo"}, 0);
        tablaVueltas = new JTable(modeloTablaVueltas);
        panelVueltas.add(new JScrollPane(tablaVueltas), BorderLayout.CENTER);

        JPanel panelSurVueltas = new JPanel();
        JButton btnNuevaVuelta = new JButton("Nueva Vuelta");
        JButton btnModificarVuelta = new JButton("Modificar");
        JButton btnEliminarVuelta = new JButton("Eliminar");
        JButton btnActualizarVueltas = new JButton("Actualizar Tabla");

        panelSurVueltas.add(btnNuevaVuelta);
        panelSurVueltas.add(btnModificarVuelta);
        panelSurVueltas.add(btnEliminarVuelta);
        panelSurVueltas.add(btnActualizarVueltas);
        panelVueltas.add(panelSurVueltas, BorderLayout.SOUTH);

        btnNuevaVuelta.addActionListener(e -> {
            DialogoVuelta dialogo = new DialogoVuelta(this);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnModificarVuelta.addActionListener(e -> {
            int fila = tablaVueltas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una vuelta de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idGp = Integer.parseInt(modeloTablaVueltas.getValueAt(fila, 0).toString());
            String codigoPiloto = modeloTablaVueltas.getValueAt(fila, 1).toString();
            int numeroVuelta = Integer.parseInt(modeloTablaVueltas.getValueAt(fila, 2).toString());
            String tiempo = modeloTablaVueltas.getValueAt(fila, 3).toString();

            DialogoVuelta dialogo = new DialogoVuelta(this, idGp, codigoPiloto, numeroVuelta, tiempo);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnEliminarVuelta.addActionListener(e -> {
            int fila = tablaVueltas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una vuelta para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idGp = Integer.parseInt(modeloTablaVueltas.getValueAt(fila, 0).toString());
            String codigoPiloto = modeloTablaVueltas.getValueAt(fila, 1).toString();
            int numeroVuelta = Integer.parseInt(modeloTablaVueltas.getValueAt(fila, 2).toString());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar esta vuelta?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (vueltaServicio.eliminarVuelta(idGp, codigoPiloto, numeroVuelta)) {
                    JOptionPane.showMessageDialog(this, "Vuelta eliminada con éxito.");
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar la vuelta.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnActualizarVueltas.addActionListener(e -> cargarDatos());
        
     // --- PESTAÑA PROVEEDORES DE MOTOR ---
        JPanel panelProvMotor = new JPanel(new BorderLayout());
        modeloTablaProvMotor = new DefaultTableModel(new String[]{"ID", "Nombre", "N° Chasis"}, 0);
        tablaProvMotor = new JTable(modeloTablaProvMotor);
        panelProvMotor.add(new JScrollPane(tablaProvMotor), BorderLayout.CENTER);

        JPanel panelSurProvMotor = new JPanel();
        JButton btnNuevoProvMotor = new JButton("Nuevo Proveedor");
        JButton btnModificarProvMotor = new JButton("Modificar");
        JButton btnEliminarProvMotor = new JButton("Eliminar");
        JButton btnActualizarProvMotor = new JButton("Actualizar Tabla");

        panelSurProvMotor.add(btnNuevoProvMotor);
        panelSurProvMotor.add(btnModificarProvMotor);
        panelSurProvMotor.add(btnEliminarProvMotor);
        panelSurProvMotor.add(btnActualizarProvMotor);
        panelProvMotor.add(panelSurProvMotor, BorderLayout.SOUTH);

        btnNuevoProvMotor.addActionListener(e -> {
            DialogoProveedorMotor dialogo = new DialogoProveedorMotor(this);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnModificarProvMotor.addActionListener(e -> {
            int fila = tablaProvMotor.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaProvMotor.getValueAt(fila, 0).toString());
            String nombre = modeloTablaProvMotor.getValueAt(fila, 1).toString();
            String nChasis = modeloTablaProvMotor.getValueAt(fila, 2).toString();

            DialogoProveedorMotor dialogo = new DialogoProveedorMotor(this, id, nombre, nChasis);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnEliminarProvMotor.addActionListener(e -> {
            int fila = tablaProvMotor.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaProvMotor.getValueAt(fila, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este proveedor de motor?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (provMotorServicio.eliminarProveedorMotor(id)) {
                    JOptionPane.showMessageDialog(this, "Proveedor de motor eliminado con éxito.");
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar (puede tener escuderías asociadas).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnActualizarProvMotor.addActionListener(e -> cargarDatos());
        
     // --- PESTAÑA PROVEEDORES DE INDUMENTARIA ---
        JPanel panelProvIndumentaria = new JPanel(new BorderLayout());
        modeloTablaProvIndumentaria = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tablaProvIndumentaria = new JTable(modeloTablaProvIndumentaria);
        panelProvIndumentaria.add(new JScrollPane(tablaProvIndumentaria), BorderLayout.CENTER);

        JPanel panelSurProvIndumentaria = new JPanel();
        JButton btnNuevoProvInd = new JButton("Nuevo Proveedor");
        JButton btnModificarProvInd = new JButton("Modificar");
        JButton btnEliminarProvInd = new JButton("Eliminar");
        JButton btnActualizarProvInd = new JButton("Actualizar Tabla");

        panelSurProvIndumentaria.add(btnNuevoProvInd);
        panelSurProvIndumentaria.add(btnModificarProvInd);
        panelSurProvIndumentaria.add(btnEliminarProvInd);
        panelSurProvIndumentaria.add(btnActualizarProvInd);
        panelProvIndumentaria.add(panelSurProvIndumentaria, BorderLayout.SOUTH);

        btnNuevoProvInd.addActionListener(e -> {
            DialogoProveedorIndumentaria dialogo = new DialogoProveedorIndumentaria(this);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnModificarProvInd.addActionListener(e -> {
            int fila = tablaProvIndumentaria.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaProvIndumentaria.getValueAt(fila, 0).toString());
            String nombre = modeloTablaProvIndumentaria.getValueAt(fila, 1).toString();

            DialogoProveedorIndumentaria dialogo = new DialogoProveedorIndumentaria(this, id, nombre);
            dialogo.setVisible(true);
            if (dialogo.isOperacionExitosa()) {
                cargarDatos();
            }
        });

        btnEliminarProvInd.addActionListener(e -> {
            int fila = tablaProvIndumentaria.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = Integer.parseInt(modeloTablaProvIndumentaria.getValueAt(fila, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este proveedor de indumentaria?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (provIndumentariaServicio.eliminarProveedorIndumentaria(id)) {
                    JOptionPane.showMessageDialog(this, "Proveedor de indumentaria eliminado con éxito.");
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar (puede tener pilotos asociados).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnActualizarProvInd.addActionListener(e -> cargarDatos());
        
     // --- PESTAÑA CLASIFICACIÓN / REPORTES ---
        JPanel panelReportes = new JPanel(new BorderLayout());
        modeloTablaClasificacion = new DefaultTableModel(new String[]{"Pos° Campeonato", "Código Piloto", "Piloto", "Puntos Totales"}, 0);
        tablaClasificacion = new JTable(modeloTablaClasificacion);
        panelReportes.add(new JScrollPane(tablaClasificacion), BorderLayout.CENTER);

        JPanel panelSurReportes = new JPanel();
        JButton btnActualizarReporte = new JButton("Actualizar Clasificación");
        panelSurReportes.add(btnActualizarReporte);
        panelReportes.add(panelSurReportes, BorderLayout.SOUTH);

        btnActualizarReporte.addActionListener(e -> cargarDatos());
        
     // --- PESTAÑA RANKING GRANDES PREMIOS ---
        JPanel panelRankingGp = new JPanel(new BorderLayout());
        modeloTablaRankingGp = new DefaultTableModel(new String[]{"ID GP", "Gran Premio", "Circuito", "Piloto Más Rápido", "Vuelta Rápida", "Auto"}, 0);
        tablaRankingGp = new JTable(modeloTablaRankingGp);
        panelRankingGp.add(new JScrollPane(tablaRankingGp), BorderLayout.CENTER);

        JPanel panelSurRanking = new JPanel();
        JButton btnActualizarRanking = new JButton("Actualizar Ranking");
        panelSurRanking.add(btnActualizarRanking);
        panelRankingGp.add(panelSurRanking, BorderLayout.SOUTH);

        btnActualizarRanking.addActionListener(e -> cargarDatos());

        tabbedPane.addTab("Ranking GPs (Vuelta Rápida)", panelRankingGp);

        // Agregar la pestaña al JTabbedPane
        tabbedPane.addTab("Clasificación Pilotos", panelReportes);

        tabbedPane.addTab("Proveedores Indumentaria", panelProvIndumentaria);

        tabbedPane.addTab("Proveedores Motor", panelProvMotor);

        // Agregar al JTabbedPane
        tabbedPane.addTab("Vueltas", panelVueltas);

        // Agregar la pestaña al JTabbedPane
        tabbedPane.addTab("Resultados", panelResultados);

        // Agregar al JTabbedPane
        tabbedPane.addTab("Autos", panelAutos);

        // Agregar al JTabbedPane
        tabbedPane.addTab("Temporadas", panelTemporadas);

        // Agregamos las pestañas al panel principal
        tabbedPane.addTab("Pilotos", panelPilotos);
        tabbedPane.addTab("Escuderías", panelEscuderias);
        tabbedPane.addTab("Grandes Premios", panelGp);
        tabbedPane.addTab("Circuitos", panelCircuitos);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        // Cargar Pilotos
        modeloTablaPilotos.setRowCount(0);
        List<Piloto> pilotos = pilotoServicio.obtenerPilotos();
        for (Piloto p : pilotos) {
            modeloTablaPilotos.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getNacionalidad()});
        }

        // Cargar Escuderías
        modeloTablaEscuderias.setRowCount(0);
        List<Escuderia> escuderias = escuderiaServicio.obtenerEscuderias();
        for (Escuderia esc : escuderias) {
            modeloTablaEscuderias.addRow(new Object[]{esc.getIdEscuderia(), esc.getNombre()});
        }

        // Cargar Grandes Premios
        modeloTablaGrandesPremios.setRowCount(0);
        try {
            List<Object[]> gps = granPremioServicio.obtenerGrandesPremiosDetallados();
            if (gps != null) {
                for (Object[] gp : gps) {
                    modeloTablaGrandesPremios.addRow(gp);
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso al cargar grandes premios: " + e.getMessage());
        }
        
        // Cargar Circuitos
        modeloTablaCircuitos.setRowCount(0);
        try {
            List<Circuito> circuitos = circuitoServicio.obtenerCircuitos();
            if (circuitos != null) {
                for (Circuito c : circuitos) {
                    modeloTablaCircuitos.addRow(new Object[]{c.getIdCircuito(), c.getNombre(), c.getPais()});
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso al cargar circuitos: " + e.getMessage());
        }
        
        // Cargar Temporadas
        modeloTablaTemporadas.setRowCount(0);
        try {
            List<Object[]> temporadas = temporadaServicio.obtenerTemporadasDetalladas();
            if (temporadas != null) {
                for (Object[] t : temporadas) {
                    modeloTablaTemporadas.addRow(t);
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso al cargar temporadas: " + e.getMessage());
        }
        
        // Cargar Autos
        modeloTablaAutos.setRowCount(0);
        try {
            List<Auto> autos = autoServicio.obtenerAutos();
            if (autos != null) {
                for (Auto a : autos) {
                    modeloTablaAutos.addRow(new Object[]{a.getIdAuto(), a.getModelo(), a.getAnioFabricacion(), a.getIdEscuderia()});
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso al cargar autos: " + e.getMessage());
        }
        
        // Cargar Resultados
        modeloTablaResultados.setRowCount(0);
        try {
            List<Resultado> resultados = resultadoServicio.obtenerResultados();
            if (resultados != null) {
                for (Resultado r : resultados) {
                    modeloTablaResultados.addRow(new Object[]{
                        r.getIdGp(),
                        r.getCodigoPiloto(),
                        r.getIdAuto(),
                        r.getPosicion(),
                        r.getVueltasCompletadas(),
                        r.getPuntos()
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso al cargar resultados: " + e.getMessage());
        }
        
        // Cargar Vueltas
        modeloTablaVueltas.setRowCount(0);
        try {
            List<Vuelta> vueltas = vueltaServicio.obtenerVueltas();
            if (vueltas != null) {
                for (Vuelta v : vueltas) {
                    modeloTablaVueltas.addRow(new Object[]{
                        v.getIdGp(),
                        v.getCodigoPiloto(),
                        v.getNroVuelta(),
                        v.getTiempo()
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso al cargar vueltas: " + e.getMessage());
        }
        
        // Cargar Proveedores de Motor
        modeloTablaProvMotor.setRowCount(0);
        try {
            List<ProveedorMotor> lista = provMotorServicio.obtenerProveedoresMotor();
            if (lista != null) {
                for (ProveedorMotor pm : lista) {
                    modeloTablaProvMotor.addRow(new Object[]{
                        pm.getIdProveedorMotor(),
                        pm.getNombre(),
                        pm.getnChasis()
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso al cargar proveedores de motor: " + e.getMessage());
        }	
        
        // Cargar Proveedores de Indumentaria
        modeloTablaProvIndumentaria.setRowCount(0);
        try {
            List<ProveedorIndumentaria> lista = provIndumentariaServicio.obtenerProveedoresIndumentaria();
            if (lista != null) {
                for (ProveedorIndumentaria pi : lista) {
                    modeloTablaProvIndumentaria.addRow(new Object[]{
                        pi.getIdProveedorIndumentaria(),
                        pi.getNombre()
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso al cargar proveedores de indumentaria: " + e.getMessage());
        }
        
        // Cargar Clasificación de Pilotos
        modeloTablaClasificacion.setRowCount(0);
        try {
            List<Object[]> clasificacion = reporteServicio.obtenerClasificacionPilotos();
            if (clasificacion != null) {
                for (Object[] fila : clasificacion) {
                    modeloTablaClasificacion.addRow(fila);
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso al cargar clasificación: " + e.getMessage());
        }
        
        // Cargar Ranking de Grandes Premios (Ahora dentro del método correctamente)
        modeloTablaRankingGp.setRowCount(0);
        try {
            List<Object[]> ranking = reporteServicio.obtenerRankingGrandesPremios();
            if (ranking != null) {
                for (Object[] fila : ranking) {
                    modeloTablaRankingGp.addRow(fila);
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso al cargar ranking de GPs: " + e.getMessage());
        }
    }
}