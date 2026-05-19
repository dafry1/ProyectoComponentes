package pantallasFactura;

import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import java.awt.*;
import javax.swing.*;
import utilPresentacion.FachadaUtil;

/**
 * AÚN EN PROCESITO
 * 
 * Pantalla que recibe datos (y modifica de igual forma)
 * los datos del contribuyente que desea facturar la venta
 * 
 * @author Andre
 */
public class IngresarDatosContribuyente extends JFrame {

    //Coordinadores 
    private final ICoordinadorPresentacion coordinadorPresentacion;
    private final ICoordinadorNegocio coordinadorNegocio;
    
    //Componentes del formulario
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JComboBox<String> comboFormaPago;
    private JComboBox<String> comboRegimen;
    private JComboBox<String> comboUsoCfdi;
    
    // Botones de navegación inferior
    private JButton btnAtras;
    private JButton btnContinuar;
    
    // Etiquetas del encabezado manipulables
    private JLabel lblShcp;
    private JLabel lblSat;
    private JLabel lblTitulo;

    public IngresarDatosContribuyente(ICoordinadorPresentacion coordinadorPresentacion, ICoordinadorNegocio coordinadorNegocio) {
        FachadaUtil.configurarFrame("Ingresar datos fiscales", this);
        this.coordinadorPresentacion = coordinadorPresentacion;
        this.coordinadorNegocio = coordinadorNegocio;
        
        //Crea el panel principal
        JPanel panelPrincipal = FachadaUtil.crearPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        //Encabezado
        JPanel panelEncabezado = construirEncabezado();
        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);

        //Formulario central
        JPanel panelCentroContenedor = new JPanel(new GridBagLayout());
        panelCentroContenedor.setOpaque(false);

        //Panel tal cual para el formulario
        JPanel panelFormulario = FachadaUtil.crearPanel();
        panelFormulario.setLayout(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(35, 45, 35, 45));
        inicializarCamposFormulario();
        acomodarComponentesEnTarjeta(panelFormulario);
        
        //Navegación inferior
        JPanel panelFooter = construirPiePagina();
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);

        //Ensambla todo al frame
        panelCentroContenedor.add(panelFormulario);
        panelPrincipal.add(panelCentroContenedor, BorderLayout.CENTER);
        this.add(panelPrincipal);
    }
    
    private void iniciarGraficos() {
        FachadaUtil.configurarFrame("Factura electrónica", this);
        
    }

    private JPanel construirEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        // Sub-panel izquierdo para los logotipos (SHCP y SAT)
        JPanel panelLogos = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelLogos.setOpaque(false);
        
        // JLabels normales planos y fácilmente modificables externamente o desde atributos
        lblShcp = new JLabel("SHCP");
        lblShcp.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblShcp.setForeground(new Color(106, 27, 41)); // Color guinda institucional
        
        lblSat = new JLabel("SAT");
        lblSat.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblSat.setForeground(Color.BLACK);
        
        panelLogos.add(lblShcp);
        panelLogos.add(lblSat);

        // Título Derecho
        lblTitulo = new JLabel("FACTURA ELECTRÓNICA");
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        lblTitulo.setForeground(Color.BLACK);

        header.add(panelLogos, BorderLayout.WEST);
        header.add(lblTitulo, BorderLayout.EAST);

        // Línea divisoria gris tenue debajo del header
        JPanel lineaDivisoria = new JPanel();
        lineaDivisoria.setBackground(new Color(230, 230, 230));
        lineaDivisoria.setPreferredSize(new Dimension(100, 3));
        
        JPanel headerCompleto = new JPanel(new BorderLayout());
        headerCompleto.add(header, BorderLayout.CENTER);
        headerCompleto.add(lineaDivisoria, BorderLayout.SOUTH);

        return headerCompleto;
    }

    private void inicializarCamposFormulario() {
        // Fila 1
        txtCorreo = FachadaUtil.crearCampoTexto();
        txtCorreo.setPreferredSize(new Dimension(220, 40));
        
        txtTelefono = FachadaUtil.crearCampoTexto();
        txtTelefono.setPreferredSize(new Dimension(220, 40));

        // Fila 2 (Utilizan las dimensiones estilizadas automáticas fijadas por la factoría)
        String[] formasPago = {"Efectivo", "Tarjeta de Crédito", "Tarjeta de Débito", "Transferencia"};
        comboFormaPago = FachadaUtil.crearComboBox(formasPago);
        comboFormaPago.setPreferredSize(new Dimension(140, 40));

        String[] regimenes = {"Sueldos y Salarios", "RESICO", "Actividad Empresarial"};
        comboRegimen = FachadaUtil.crearComboBox(regimenes);
        comboRegimen.setPreferredSize(new Dimension(140, 40));

        String[] usosCfdi = {"G03 - Gastos en general", "P01 - Por definir"};
        comboUsoCfdi = FachadaUtil.crearComboBox(usosCfdi);
        comboUsoCfdi.setPreferredSize(new Dimension(140, 40));
    }

    private void acomodarComponentesEnTarjeta(JPanel tarjeta) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 15, 8, 15); // Separaciones internas uniformes

        // --- FILA 0: Etiquetas Superiores ---
        gbc.gridy = 0;
        
        gbc.gridx = 0; gbc.gridwidth = 2; // Ocupa espacio doble para alinearse con los inputs de abajo
        tarjeta.add(crearEtiquetaFormulario("Correo electrónico"), gbc);
        
        gbc.gridx = 2; gbc.gridwidth = 2;
        tarjeta.add(crearEtiquetaFormulario("Teléfono"), gbc);

        // --- FILA 1: Campos de Texto ---
        gbc.gridy = 1; gbc.gridwidth = 2;
        
        gbc.gridx = 0;
        tarjeta.add(txtCorreo, gbc);
        
        gbc.gridx = 2;
        tarjeta.add(txtTelefono, gbc);

        // Espaciador vertical notable entre bloques de datos
        gbc.gridy = 2;
        tarjeta.add(Box.createVerticalStrut(20), gbc);

        // --- FILA 3: Etiquetas Inferiores (Fila de Combos) ---
        gbc.gridy = 3; gbc.gridwidth = 1; // Reseteamos ancho de celdas a 1
        
        gbc.gridx = 0;
        tarjeta.add(crearEtiquetaFormulario("Forma de pago*"), gbc);
        
        gbc.gridx = 1;
        tarjeta.add(crearEtiquetaFormulario("Régimen fiscal*"), gbc);
        
        gbc.gridx = 2;
        tarjeta.add(crearEtiquetaFormulario("Uso del CFDI"), gbc);

        // --- FILA 4: ComboBoxes Estilizados ---
        gbc.gridy = 4;
        
        gbc.gridx = 0;
        tarjeta.add(comboFormaPago, gbc);
        
        gbc.gridx = 1;
        tarjeta.add(comboRegimen, gbc);
        
        gbc.gridx = 2;
        tarjeta.add(comboUsoCfdi, gbc);
    }

    private JPanel construirPiePagina() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));

        // Botón Regresar (Atrás) alineado a la izquierda
        btnAtras = FachadaUtil.crearBotonRegresar();
        btnAtras.setText("Atrás");
        btnAtras.setPreferredSize(new Dimension(160, 45));

        // Botón Continuar alineado a la derecha
        btnContinuar = FachadaUtil.crearBoton("Continuar");
        btnContinuar.setPreferredSize(new Dimension(160, 45));
        
        // Acciones
        btnAtras.addActionListener(e -> {
            // coordinador.regresar();
        });
        
        btnContinuar.addActionListener(e -> {
            FachadaUtil.dialogoAviso(this, "Procesando datos del CFDI...");
        });

        footer.add(btnAtras, BorderLayout.WEST);
        footer.add(btnContinuar, BorderLayout.EAST);

        return footer;
    }

    private JLabel crearEtiquetaFormulario(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }
}