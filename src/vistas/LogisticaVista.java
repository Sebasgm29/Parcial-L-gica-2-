package vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;                                  // NUEVO

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;                         // NUEVO
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import controladores.LogisticaControlador;              // NUEVO
import modelos.Envio;                                   // NUEVO

public class LogisticaVista extends JFrame {

    public String[] encabezados = new String[] { "Código", "Cliente", "Peso", "Distancia", "Tipo", "Costo" };

    private JTable tblEnvios;
    private JPanel pnlEditarEnvio;

    private JTextField txtNumero, txtCliente, txtPeso, txtDistancia;
    private JComboBox cmbTipoEnvio;

    private LogisticaControlador controlador;           // NUEVO

    public LogisticaVista() {
        setSize(600, 400);
        setTitle("Operador Logísitico");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JToolBar tbLogistica = new JToolBar();

        JButton btnAgregarEnvio = new JButton();
        btnAgregarEnvio.setIcon(new ImageIcon(getClass().getResource("/iconos/AgregarEnvio.png")));
        btnAgregarEnvio.setToolTipText("Agregar Envío");
        btnAgregarEnvio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                agregarEnvio();
            }
        });
        tbLogistica.add(btnAgregarEnvio);

        JButton btnQuitarEnvio = new JButton();
        btnQuitarEnvio.setIcon(new ImageIcon(getClass().getResource("/iconos/QuitarEnvio.png")));
        btnQuitarEnvio.setToolTipText("Quitar Envío");
        btnQuitarEnvio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quitarEnvio();
            }
        });
        tbLogistica.add(btnQuitarEnvio);

        // Contenedor principal de ENVIOS con BoxLayout (vertical)
        JPanel pnlEnvios = new JPanel();
        pnlEnvios.setLayout(new BoxLayout(pnlEnvios, BoxLayout.Y_AXIS));

        // Panel 1 (oculto por defecto)
        pnlEditarEnvio = new JPanel();
        pnlEditarEnvio.setPreferredSize(new Dimension(pnlEditarEnvio.getWidth(), 250)); // Altura fija de 100px
        pnlEditarEnvio.setLayout(null);

        JLabel lblNumero = new JLabel("Número");
        lblNumero.setBounds(10, 10, 100, 25);
        pnlEditarEnvio.add(lblNumero);

        txtNumero = new JTextField();
        txtNumero.setBounds(110, 10, 100, 25);
        pnlEditarEnvio.add(txtNumero);

        JLabel lblCliente = new JLabel("Cliente");
        lblCliente.setBounds(10, 40, 100, 25);
        pnlEditarEnvio.add(lblCliente);

        txtCliente = new JTextField();
        txtCliente.setBounds(110, 40, 100, 25);
        pnlEditarEnvio.add(txtCliente);

        JLabel lblPeso = new JLabel("Peso");
        lblPeso.setBounds(10, 70, 100, 25);
        pnlEditarEnvio.add(lblPeso);

        txtPeso = new JTextField();
        txtPeso.setBounds(110, 70, 100, 25);
        pnlEditarEnvio.add(txtPeso);

        JLabel lblTipo = new JLabel("Tipo");
        lblTipo.setBounds(220, 10, 100, 25);
        pnlEditarEnvio.add(lblTipo);

        cmbTipoEnvio = new JComboBox();
        cmbTipoEnvio.setBounds(320, 10, 100, 25);
        String[] opciones = new String[] { "Terrestre", "Aéreo", "Marítimo" };
        DefaultComboBoxModel mdlTipoEnvio = new DefaultComboBoxModel(opciones);
        cmbTipoEnvio.setModel(mdlTipoEnvio);
        pnlEditarEnvio.add(cmbTipoEnvio);

        JLabel lblDistancia = new JLabel("Distancia en Km");
        lblDistancia.setBounds(220, 40, 100, 25);
        pnlEditarEnvio.add(lblDistancia);

        txtDistancia = new JTextField();
        txtDistancia.setBounds(320, 40, 100, 25);
        pnlEditarEnvio.add(txtDistancia);

        JButton btnGuardarEnvio = new JButton("Guardar");
        btnGuardarEnvio.setBounds(220, 70, 100, 25);
        btnGuardarEnvio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                guardarEnvio();
            }
        });
        pnlEditarEnvio.add(btnGuardarEnvio);

        JButton btnCancelarEnvio = new JButton("Cancelar");
        btnCancelarEnvio.setBounds(320, 70, 100, 25);
        btnCancelarEnvio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelarEnvio();
            }
        });
        pnlEditarEnvio.add(btnCancelarEnvio);

        pnlEditarEnvio.setVisible(false); // Se oculta al inicio

        // Panel 2 (siempre visible)
        tblEnvios = new JTable();
        JScrollPane spListaEnvios = new JScrollPane(tblEnvios);

        DefaultTableModel dtm = new DefaultTableModel(null, encabezados);
        tblEnvios.setModel(dtm);

        // Agregar componentes
        pnlEnvios.add(pnlEditarEnvio);
        pnlEnvios.add(spListaEnvios);

        // JScrollPane para permitir desplazamiento si es necesario
        JScrollPane spEnvios = new JScrollPane(pnlEnvios);
        spEnvios.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(tbLogistica, BorderLayout.NORTH);
        getContentPane().add(pnlEnvios, BorderLayout.CENTER);
    }

    public void agregarEnvio() {
        pnlEditarEnvio.setVisible(true);

    }

    public void quitarEnvio() {
        controlador.quitar();                           // NUEVO
    }

    public void guardarEnvio() {
        // El panel se oculta solo si el controlador guarda bien (ver ocultarFormulario)
        controlador.guardar();                          // NUEVO
    }

    public void cancelarEnvio() {
        pnlEditarEnvio.setVisible(false);
        limpiarFormulario();                            // NUEVO
    }

    // ===== AGREGADO PARA MVC (no modifica lo anterior) =====

    public void setControlador(LogisticaControlador controlador) {
        this.controlador = controlador;
    }

    public String getNumero() { return txtNumero.getText(); }
    public String getCliente() { return txtCliente.getText(); }
    public String getPeso() { return txtPeso.getText(); }
    public String getDistancia() { return txtDistancia.getText(); }
    public String getTipo() { return (String) cmbTipoEnvio.getSelectedItem(); }
    public int getFilaSeleccionada() { return tblEnvios.getSelectedRow(); }

    public void ocultarFormulario() {
        pnlEditarEnvio.setVisible(false);
    }

    public void limpiarFormulario() {
        txtNumero.setText("");
        txtCliente.setText("");
        txtPeso.setText("");
        txtDistancia.setText("");
        cmbTipoEnvio.setSelectedIndex(0);
    }

    public void mostrarEnvios(List<Envio> envios) {
        DefaultTableModel dtm = (DefaultTableModel) tblEnvios.getModel();
        dtm.setRowCount(0);
        for (Envio e : envios) {
            // calcularTarifa() y getTipo() se ejecutan según la subclase real (polimorfismo)
            dtm.addRow(new Object[] {
                e.getCodigo(), e.getCliente(), e.getPeso(),
                                e.getDistancia(), e.getTipo(), String.format(java.util.Locale.US, "%.1f", e.calcularTarifa())
            });
        }
    }

    public void mostrarMensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }

}