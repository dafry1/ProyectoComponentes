package pantallasSolicitudes;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import coordinadores.ICoordinadorEstados;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import excepciones.PresentacionException;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import observadores.IObservador;
import utilEstilos.Constantes;
import utilEstilos.UtilFormato;
import utilPresentacion.FachadaUtil;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilBoton.BotonAlmacenador;
import utilPresentacion.UtilPanel;

/**
 * Pantalla para iniciar una solicitud de piezas (Carrito de solicitud)
 * Adaptada al estilo de ViniciarVenta pero enfocada en el proceso de solicitud.
 * * @author DANIEL
 */
public class ViniciarSolicitud extends JFrame implements IObservador {

    private JPanel contenedorListaPiezas = new JPanel();
    private JPanel contenedorListaDetalles = new JPanel();
    private JScrollPane scrollPiezas = new JScrollPane();
    private JScrollPane scrollDetalles = new JScrollPane();

    private ICoordinadorPresentacion coordinadorPresentacion;
    private ICoordinadorNegocio coordinadorNegocio;
    private ICoordinadorEstados coordinadorEstados;

    private Map<String, JButton> mapaBotonesFiltros = new HashMap<>();
    private JButton botonBuscar = FachadaUtil.crearBoton("Buscar");
    private String[] campos = {Constantes.PIEZA_NOMBRE, Constantes.PIEZA_CATEGORIA, Constantes.PIEZA_MARCA};

    private double totalSolicitud = 0;
    private JLabel labelTotal = new JLabel("Total Solicitud: $ 0.0");
    private List<PiezaDTO> piezasMostrar = new ArrayList<>();

    public ViniciarSolicitud(ICoordinadorPresentacion coordinadorPresentacion, ICoordinadorNegocio coordinadorNegocio, ICoordinadorEstados coordinadorEstados) {
        this.coordinadorPresentacion = coordinadorPresentacion;
        this.coordinadorNegocio = coordinadorNegocio;
        this.coordinadorEstados = coordinadorEstados;

        // Configuración estética del Frame
        FachadaUtil.configurarFrame("Iniciar Solicitud de Piezas", this);
        add(FachadaUtil.crearNavegacion(this, coordinadorPresentacion), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Constantes.COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Inicializar contenedores de listas
        contenedorListaPiezas.setLayout(new BoxLayout(contenedorListaPiezas, BoxLayout.Y_AXIS));
        contenedorListaDetalles.setLayout(new BoxLayout(contenedorListaDetalles, BoxLayout.Y_AXIS));
        
        // Consultar piezas iniciales
        piezasMostrar = coordinadorNegocio.consultarPiezas();

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 10, 0, 10);

        // Panel Izquierdo: Filtros de Búsqueda
        g.gridx = 0; g.weightx = 0.15; g.weighty = 1.0; 
        contenido.add(crearPanelBusqueda(), g);

        // Panel Central: Catálogo de Piezas (Usa tarjetas azules)
        g.gridx = 1; g.weightx = 0.45; 
        contenido.add(crearSeccionCentral(), g);

        // Panel Derecho: Carrito de la Solicitud
        g.gridx = 2; g.weightx = 0.30; 
        contenido.add(crearPanelCarrito(), g);

        add(contenido, BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearSeccionCentral() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel titulo = new JLabel("Piezas Disponibles", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        p.add(titulo, BorderLayout.NORTH);

        dibujarTarjetasPiezas(piezasMostrar);
        scrollPiezas.setViewportView(contenedorListaPiezas);
        scrollPiezas.setBorder(null);
        scrollPiezas.getVerticalScrollBar().setUnitIncrement(16);
        p.add(scrollPiezas, BorderLayout.CENTER);

        return p;
    }

    private JPanel crearPanelCarrito() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel titulo = new JLabel("Resumen Solicitud", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        p.add(titulo, BorderLayout.NORTH);

        dibujarTarjetasCarrito();
        scrollDetalles.setViewportView(contenedorListaDetalles);
        scrollDetalles.setBorder(null);
        scrollDetalles.getVerticalScrollBar().setUnitIncrement(16);
        p.add(scrollDetalles, BorderLayout.CENTER);

        labelTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelTotal.setHorizontalAlignment(SwingConstants.CENTER);
        labelTotal.setBorder(new EmptyBorder(10, 0, 0, 0));
        p.add(labelTotal, BorderLayout.SOUTH);

        return p;
    }

    private JPanel crearPanelBusqueda() {
        JPanel p = FachadaUtil.crearPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridx = 0; gbc.weightx = 1.0;

        JLabel titulo = new JLabel("Búsqueda", SwingConstants.CENTER);
        gbc.gridy = 0; p.add(titulo, gbc);

        JTextField campoBuscar = FachadaUtil.crearCampoTexto();
        gbc.gridy = 1; p.add(campoBuscar, gbc);

        int orden = 2;
        for (String campo : campos) {
            JButton btnFiltro = FachadaUtil.crearBoton(campo);
            btnFiltro.addActionListener(e -> {
                String filtro = campoBuscar.getText();
                if (filtro.isBlank()) {
                    FachadaUtil.dialogoAlerta(this, "Ingrese un término para buscar");
                    return;
                }
                inyectarLogicaFiltradoBoton(filtro, campo);
            });
            gbc.gridy = orden++; p.add(btnFiltro, gbc);
        }

        JButton btnRestablecer = FachadaUtil.crearBoton("Restablecer");
        btnRestablecer.addActionListener(e -> {
            piezasMostrar = coordinadorNegocio.consultarPiezas();
            reconsultarPiezasFiltro();
        });
        gbc.gridy = orden++; p.add(btnRestablecer, gbc);

        return p;
    }

    private void inyectarLogicaFiltradoBoton(String filtro, String stringCampo) {
        switch (stringCampo) {
            case Constantes.PIEZA_NOMBRE -> piezasMostrar = coordinadorNegocio.filtrarPorNombre(filtro);
            case Constantes.PIEZA_CATEGORIA -> piezasMostrar = coordinadorNegocio.filtrarPorCategoria(filtro);
            case Constantes.PIEZA_MARCA -> piezasMostrar = coordinadorNegocio.filtrarPorMarca(filtro);   
            default -> throw new PresentacionException("Categoría inválida");
        }
        reconsultarPiezasFiltro();
    }

    private void dibujarTarjetasPiezas(List<PiezaDTO> piezas) {
        contenedorListaPiezas.removeAll();
        for (PiezaDTO pieza : piezas) {
            JPanel tarjeta = UtilPanel.dibujarTarjeta();
            tarjeta.setBackground(new Color(30, 90, 230)); // Azul vibrante
            tarjeta.setLayout(new BorderLayout(20, 0));

            // Info (Izquierda)
            String desc = "<html><font color='white' size='4'><b>" + pieza.getNombre() + "</b></font><br>" +
                          "<font color='white' size='2'>" + pieza.getMarcaPieza() + " (" + pieza.getModeloPieza() + ")</font></html>";
            JLabel lblInfo = new JLabel(desc);
            lblInfo.setBorder(new EmptyBorder(10, 15, 10, 0));

            // Acciones (Derecha)
            JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
            panelAcciones.setOpaque(false);

            JLabel lblPrecio = new JLabel("$" + pieza.getCostoPieza());
            lblPrecio.setForeground(new Color(50, 255, 100)); // Verde neón
            lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 20));

            JButton btnInfo = FachadaUtil.crearBoton("Info");
            btnInfo.setBackground(new Color(50, 255, 100));
            btnInfo.addActionListener(e -> coordinadorPresentacion.abrirInfoPieza(this, pieza));

            panelAcciones.add(lblPrecio);
            panelAcciones.add(btnInfo);

            tarjeta.add(lblInfo, BorderLayout.WEST);
            tarjeta.add(panelAcciones, BorderLayout.EAST);
            contenedorListaPiezas.add(tarjeta);
            contenedorListaPiezas.add(Box.createVerticalStrut(10));
        }
    }

    private void dibujarTarjetasCarrito() {
        
        //Declara variables
        String nombre;
        int cantidad;
        double costo;
        double subtotal;
        
        for (DetallesVentaDTO detalle: coordinadorEstados.getCarritoVenta()) {
            JPanel tarjeta = UtilPanel.dibujarTarjeta();
            
            //Asigna valores
            nombre = detalle.getPieza().getNombre();
            cantidad = detalle.getCantidad();
            costo = detalle.getCosto();
            subtotal = detalle.getSubtotal();
            
            //Crea el panel de información básica
            JPanel panelInfoBasica = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            panelInfoBasica.setOpaque(false);
            
            //Parte de ícono y descripción
            String desc = "<html><body style='width: 65%'>" +
                          "<font color='white' size='3'><b>"+nombre+"</b> ("+cantidad+")</font><br>" +
                          "<font color='white' size='2'>$ "+costo+"</font></body></html>";
            panelInfoBasica.add(new JLabel(desc));
            
            //Sección para mostrar información adicional: el precio y el botón de detalles
            JPanel panelMostrarInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
            panelMostrarInfo.setOpaque(false);
            panelMostrarInfo.setPreferredSize(new Dimension(160, 50));
            
            //Label de precio
            JLabel lblP = new JLabel("$" + subtotal); 
            lblP.setForeground(new Color(50, 255, 100));
            lblP.setFont(new Font("Segoe UI", Font.BOLD, 18));
            panelMostrarInfo.add(lblP);
            
            //Crea un botón de información adicional
            Color colorBoton = new Color(50, 255, 100);
            UtilBoton.BotonAlmacenador botonInfo = new BotonAlmacenador("Info", detalle);
            botonInfo.setBackground(colorBoton);
            UtilBoton.asignarHoverBoton(botonInfo, colorBoton.darker());
            panelMostrarInfo.add(botonInfo);
            
            //Agrega funcionalidad al botón de mostrarInfo
            botonInfo.addActionListener(e -> {
                coordinadorPresentacion.abrirInfoDetalle(ViniciarSolicitud.this, detalle);
            });
            
            //Agrega al panel principal
            tarjeta.add(panelInfoBasica, BorderLayout.WEST);
            tarjeta.add(panelMostrarInfo, BorderLayout.EAST);

            //Agrega al panel
            contenedorListaDetalles.add(tarjeta);
            contenedorListaDetalles.add(Box.createVerticalStrut(15));
        }
    }

    private JPanel crearPanelInferior() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(10, 30, 20, 30));

        JButton btnRegresar = FachadaUtil.crearBotonRegresar();
        btnRegresar.addActionListener(e -> coordinadorPresentacion.mostrarVentanaInicio());

        JButton btnContinuar = FachadaUtil.crearBoton("Siguiente");
        btnContinuar.setPreferredSize(new Dimension(200, 50));
        btnContinuar.addActionListener(e -> {
            if (coordinadorEstados.carritoVentaVacio()) {
                FachadaUtil.dialogoAlerta(this, "La solicitud está vacía");
                return;
            }
            coordinadorPresentacion.abrirResumenSolicitud(); 
        });

        p.add(btnRegresar, BorderLayout.WEST);
        p.add(btnContinuar, BorderLayout.EAST);
        return p;
    }

    private void reconsultarPiezasFiltro() {
        dibujarTarjetasPiezas(piezasMostrar);
        contenedorListaPiezas.revalidate();
        contenedorListaPiezas.repaint();
    }

    @Override
    public void observar() {
        totalSolicitud = coordinadorEstados.totalCarritoVenta();
        labelTotal.setText("Total Solicitud: $ " + totalSolicitud);
        dibujarTarjetasCarrito();
        contenedorListaDetalles.revalidate();
        contenedorListaDetalles.repaint();
    }
}