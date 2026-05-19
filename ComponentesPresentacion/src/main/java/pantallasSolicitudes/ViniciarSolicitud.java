package pantallasSolicitudes;

import DTOS.DetallesSolicitudDTO;
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
import utilPresentacion.Constantes;
import utilEstilos.UtilFormato;
import utilPresentacion.FachadaUtil;
import utilEstilos.UtilBoton;
import utilEstilos.UtilBoton.BotonAlmacenador;
import utilEstilos.UtilPanel;

/**
 * Pantalla para iniciar una solicitud de piezas (Carrito de solicitud)
 * Adaptada al estilo de ViniciarVenta pero enfocada en el proceso de solicitud.
 * 
 * @author DANIEL
 */
public class ViniciarSolicitud extends JFrame implements IObservador {

    private final JPanel contenedorListaPiezas = new JPanel();
    private final JPanel contenedorListaDetalles = new JPanel();
    private final JScrollPane scrollPiezas = new JScrollPane();
    private final JScrollPane scrollDetalles = new JScrollPane();

    private final ICoordinadorPresentacion coordinadorPresentacion;
    private final ICoordinadorNegocio coordinadorNegocio;
    private final ICoordinadorEstados coordinadorEstados;

    private final Map<String, JButton> mapaBotonesFiltros = new HashMap<>();
    private final String[] campos = {Constantes.PIEZA_NOMBRE, Constantes.PIEZA_CATEGORIA, Constantes.PIEZA_MARCA, Constantes.PIEZA_PRECIOMAX};

    private double totalCarrito;
    private final JLabel labelTotal = new JLabel("Total Solicitud: $ 0.0");
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
        piezasMostrar = coordinadorNegocio.consultarPiezasBodega();

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 10, 0, 10);

        // Panel Izquierdo: Filtros de Búsqueda
        g.gridx = 0; g.weightx = 0.15; g.weighty = 1.0; 
        contenido.add(crearPanelBusqueda(), g);

        // Panel Central: Catálogo de Piezas
        g.gridx = 1; g.weightx = 0.45; 
        contenido.add(crearSeccionCentral(), g);

        // Panel Derecho: Carrito de la Solicitud
        g.gridx = 2; g.weightx = 0.30; 
        contenido.add(crearPanelCarrito(), g);

        add(contenido, BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
        
        // Forzar sincronización del estado inicial en caliente
        observar();
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
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        
        JLabel titulo = new JLabel("Buscar", SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 10, 20);
        p.add(titulo, gbc);
        
        JTextField campoBuscar = FachadaUtil.crearCampoTexto();
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 20, 15, 20);
        p.add(campoBuscar, gbc);
        
        int ordenGbc = 2;
        
        for (String stringCampo : campos) {
            gbc.gridy = ordenGbc++;
            gbc.insets = new Insets(3, 10, 3, 10);
            
            JButton botonFiltro = FachadaUtil.crearBoton(stringCampo);
            botonFiltro.addActionListener(e -> {
                String filtro = campoBuscar.getText().trim();
                if (filtro.isBlank()) {
                    FachadaUtil.dialogoAlerta(ViniciarSolicitud.this, "Campo vacío");
                    return;
                }
                inyectarLogicaFiltradoBoton(filtro, stringCampo);
            });
            p.add(botonFiltro, gbc);
            mapaBotonesFiltros.put(stringCampo, botonFiltro);
        }
        
        JButton eliminarFiltros = FachadaUtil.crearBoton("Restablecer filtros");
        eliminarFiltros.addActionListener(e -> {
            campoBuscar.setText("");
            piezasMostrar = coordinadorNegocio.consultarPiezasBodega();
            reconsultarPiezasFiltro();
        });
        gbc.gridy = ordenGbc++;
        gbc.insets = new Insets(15, 20, 5, 20);
        p.add(eliminarFiltros, gbc);
        
        return p;
    }

    private void inyectarLogicaFiltradoBoton(String filtro, String stringCampo) {
        switch (stringCampo) {
            case Constantes.PIEZA_NOMBRE -> piezasMostrar = coordinadorNegocio.filtrarPorNombreSoli(filtro);
            case Constantes.PIEZA_CATEGORIA -> piezasMostrar = coordinadorNegocio.filtrarPorCategoriaSoli(filtro);
            case Constantes.PIEZA_MARCA -> piezasMostrar = coordinadorNegocio.filtrarPorMarcaSoli(filtro);   
            case Constantes.PIEZA_MODELO -> piezasMostrar = coordinadorNegocio.filtrarPorModeloSoli(filtro);
            case Constantes.PIEZA_PRECIOMAX -> {
                if (!UtilFormato.numeroEnteroPositivo(filtro)) {
                    FachadaUtil.dialogoAlerta(ViniciarSolicitud.this, "Ingrese un número entero positivo");
                    return;
                }
                piezasMostrar = coordinadorNegocio.filtrarPorPrecioMaxSoli(Double.parseDouble(filtro));
            }
            default -> throw new PresentacionException("Categoría inválida para filtrar piezas");
        }
        reconsultarPiezasFiltro();
    }

    private void dibujarTarjetasPiezas(List<PiezaDTO> piezas) {
        // FIX: Evita que se dupliquen visualmente las tarjetas en el re-render
        contenedorListaPiezas.removeAll();
        
        for (PiezaDTO pieza : piezas) {
            JPanel tarjeta = UtilPanel.dibujarTarjeta();
            tarjeta.setLayout(new BorderLayout(20, 0));
        
            String nombre = pieza.getNombre();
            String marca = pieza.getMarcaPieza();
            double precio = pieza.getCostoPieza();
            String modelo = pieza.getModeloPieza();
            String categoria = pieza.getCategoria();
            
            JPanel panelInfoBasica = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            panelInfoBasica.setOpaque(false);
            
            String desc = "<html><body style='width: 120px'>" +
                          "<font color='white' size='3'><b>["+categoria+"] "+nombre+"</b> ("+modelo+")</font><br>" +
                          "<font color='white' size='2'>"+marca+"</font></body></html>";
            panelInfoBasica.add(new JLabel(desc));

            JPanel panelMostrarInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
            panelMostrarInfo.setOpaque(false);
            
            JLabel lblP = new JLabel("$" + precio); 
            lblP.setForeground(new Color(50, 255, 100));
            lblP.setFont(new Font("Segoe UI", Font.BOLD, 22));

            Color colorBoton = new Color(50, 255, 100);
            BotonAlmacenador botonInfo = new BotonAlmacenador("Info", pieza);
            botonInfo.setBackground(colorBoton);
            UtilBoton.asignarHoverBoton(botonInfo, colorBoton.darker());
            
            panelMostrarInfo.add(lblP); 
            panelMostrarInfo.add(botonInfo);

            botonInfo.addActionListener(e -> {
                // Se envía el contexto ('this') como observador legítimo para los cambios
                coordinadorPresentacion.abrirInfoPiezaBodega(this, pieza);
            });

            tarjeta.add(panelInfoBasica, BorderLayout.WEST);
            tarjeta.add(panelMostrarInfo, BorderLayout.EAST);

            contenedorListaPiezas.add(tarjeta);
            contenedorListaPiezas.add(Box.createVerticalStrut(15));
        }
    }

    private void dibujarTarjetasCarrito() {
        contenedorListaDetalles.removeAll();
        
        for (DetallesSolicitudDTO detalle : coordinadorEstados.getCarritoSolicitud()) {
            JPanel tarjeta = UtilPanel.dibujarTarjeta();
            tarjeta.setLayout(new BorderLayout(20, 0));
            
            String nombre = detalle.getPieza().getNombre();
            int cantidad = detalle.getCantidad();
            double costo = detalle.getCosto();
            double subtotal = detalle.getSubtotal();
            
            JPanel panelInfoBasica = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            panelInfoBasica.setOpaque(false);
            
            String desc = "<html><body style='width: 120px'>" +
                          "<font color='white' size='3'><b>"+nombre+"</b> ("+cantidad+")</font><br>" +
                          "<font color='white' size='2'>$ "+costo+" c/u</font></body></html>";
            panelInfoBasica.add(new JLabel(desc));
            
            JPanel panelMostrarInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
            panelMostrarInfo.setOpaque(false);
            panelMostrarInfo.setPreferredSize(new Dimension(160, 50));
            
            JLabel lblP = new JLabel("$" + subtotal); 
            lblP.setForeground(new Color(50, 255, 100));
            lblP.setFont(new Font("Segoe UI", Font.BOLD, 18));
            panelMostrarInfo.add(lblP);
            
            Color colorBoton = new Color(50, 255, 100);
            BotonAlmacenador botonInfo = new BotonAlmacenador("Info", detalle);
            botonInfo.setBackground(colorBoton);
            UtilBoton.asignarHoverBoton(botonInfo, colorBoton.darker());
            panelMostrarInfo.add(botonInfo);
            
            botonInfo.addActionListener(e -> {
                coordinadorPresentacion.abrirInfoDetalleSolicitud(this, detalle);
            });
            
            tarjeta.add(panelInfoBasica, BorderLayout.WEST);
            tarjeta.add(panelMostrarInfo, BorderLayout.EAST);

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
            if (coordinadorEstados.carritoSolicitudVacio()) {
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
        scrollPiezas.revalidate();
        scrollPiezas.repaint();
    }
    
    @Override
    public void observar() {
        totalCarrito = coordinadorEstados.totalCarritoSolicitud();
        labelTotal.setText("Total Solicitud: $ " + totalCarrito);
        dibujarTarjetasCarrito();
        contenedorListaDetalles.revalidate();
        contenedorListaDetalles.repaint();
        scrollDetalles.revalidate();
        scrollDetalles.repaint();
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }
}