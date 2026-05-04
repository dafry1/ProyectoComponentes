package pantallasSolicitudes;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import coordinadores.CoordinadorEstados;
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
    private String[] campos = {Constantes.PIEZA_NOMBRE, Constantes.PIEZA_CATEGORIA, Constantes.PIEZA_MARCA, Constantes.PIEZA_PRECIOMAX};

    private double totalCarrito = CoordinadorEstados.singleton().totalCarritoSolicitud();
    
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
        piezasMostrar = coordinadorNegocio.consultarPiezasBodega();

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

    /**
     * Crea el panel izquiero de búsqueda
     * Utiliza un arreglo para crear los campos de busqueda
     * 
     * @return 
     */
    private JPanel crearPanelBusqueda() {
        
        //Configura el panel
        JPanel p = FachadaUtil.crearPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        
        //Encabezado del panel 
        JLabel titulo = new JLabel("Buscar", SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 10, 20);
        p.add(titulo, gbc);
        
        //Campo de búsqueda
        JTextField campoBuscar = FachadaUtil.crearCampoTexto();
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 20, 15, 20);
        p.add(campoBuscar, gbc);
        
        //Va guardando la posición que tiene cada elemento verticalmente
        int ordenGbc = 2;
        
        /**
         * Empieza a iterar sobre el arreglo de campos
         * Por cada iteración, crea el label de indicaciones y el campo para ingresar información
         * Al final, lo agrega al campo para que no quede aislado
         */
        for (String stringCampo: campos) {
            
            //Se va sumando el orden vertical, así que solo se debe poner gbc
            gbc.gridy = ordenGbc++;
            gbc.insets = new Insets(3, 10, 3, 10);
            
            //Crea un botón de filtrado
            JButton botonFiltro = FachadaUtil.crearBoton(stringCampo);
            botonFiltro.addActionListener(e -> {
                
                //Verifica que el string sea válido primero
                String filtro = campoBuscar.getText();
                if (filtro.isBlank()) {
                    FachadaUtil.dialogoAlerta(ViniciarSolicitud.this, "Campo vacío");
                    return;
                }
                inyectarLogicaFiltradoBoton(filtro, stringCampo);
            });
            p.add(botonFiltro, gbc);
            
            //Agrega al mapa de botones para recuperarlo después 
            mapaBotonesFiltros.put(stringCampo, botonFiltro);
        }
        
        //Botón encargado de restablecer los filtros y buscar todas las piezas
        JButton eliminarFiltros = FachadaUtil.crearBoton("Restablecer filtros");
        eliminarFiltros.addActionListener(e -> {
            piezasMostrar = coordinadorNegocio.consultarPiezasBodega();
            reconsultarPiezasFiltro();
        });
        gbc.gridy = ordenGbc++;
        gbc.insets = new Insets(15, 20, 5, 20);
        p.add(eliminarFiltros, gbc);
        
        //Configura el botón de búsqueda
        botonBuscar.setPreferredSize(new Dimension(03, 45));
        gbc.gridy = ordenGbc++; 
        //gbc.weighty = 0.1; 
        gbc.insets = new Insets(10, 20, 30, 20);
        p.add(botonBuscar, gbc);
        return p;
    }

    private void inyectarLogicaFiltradoBoton(String filtro, String stringCampo) {
        switch (stringCampo) {
            case Constantes.PIEZA_NOMBRE -> piezasMostrar = coordinadorNegocio.filtrarPorNombre(filtro);
            case Constantes.PIEZA_CATEGORIA -> piezasMostrar = coordinadorNegocio.filtrarPorCategoria(filtro);
            case Constantes.PIEZA_MARCA -> piezasMostrar = coordinadorNegocio.filtrarPorMarca(filtro);   
            case Constantes.PIEZA_PRECIOMAX -> {
                piezasMostrar = UtilFormato.numeroEnteroPositivo(filtro) 
                    ? coordinadorNegocio.filtrarPorPrecioMax(Double.parseDouble(filtro)) 
                    : piezasMostrar;
                if (!UtilFormato.numeroEnteroPositivo(filtro)) {
                    FachadaUtil.dialogoAlerta(ViniciarSolicitud.this, "Ingrese un número entero positivo");
                    return;
                }
            }
            default -> throw new PresentacionException("Categoría inválida para filtrar piezas");
        }
        reconsultarPiezasFiltro();
    }

    /**
     * Dibuja y habita cada tarjeta que le corresponde una pieza en específico
     * Crea la tarjeta de UtilPanel
     * Extrae los datos de la pieza
     * Configura cómo se plasma la información
     * Crea el BotonAlmacenador mostrarInfo
     * Esa información del DTO se manda a un diálogo
     * 
     * @param pieza específica
     */
    private void dibujarTarjetasPiezas(List<PiezaDTO> piezas) {
        
        //Declara variables
        String nombre;
        String marca;
        String modelo;
        double precio;
        String categoria;
        
        //Por cada pieza de la lista...
        for (PiezaDTO pieza: piezas) {
            JPanel tarjeta = UtilPanel.dibujarTarjeta();
            tarjeta.setLayout(new BorderLayout(20, 0));
        
            //Asigna valores
            nombre = pieza.getNombre();
            marca = pieza.getMarcaPieza();
            precio = pieza.getCostoPieza();
            modelo = pieza.getModeloPieza();
            categoria = pieza.getCategoria();
            
            //Crea el panel de información básica
            JPanel panelInfoBasica = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            panelInfoBasica.setOpaque(false);
            
            //Parte de ícono y descripción
            String desc = "<html><body style='width: 120px'>" +
                          "<font color='white' size='3'><b>["+categoria+"] "+nombre+"</b> ("+modelo+")</font><br>" +
                          "<font color='white' size='2'>$ "+marca+"</font></body></html>";
            panelInfoBasica.add(new JLabel(desc));

            //Sección para mostrar información adicional: el precio y el botón de detalles
            JPanel panelMostrarInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
            panelMostrarInfo.setOpaque(false);
            
            //Label de precio
            JLabel lblP = new JLabel("$" + precio); 
            lblP.setForeground(new Color(50, 255, 100));
            lblP.setFont(new Font("Segoe UI", Font.BOLD, 22));

            //Crea un botón de información adicional
            Color colorBoton = new Color(50, 255, 100);
            UtilBoton.BotonAlmacenador botonInfo = new BotonAlmacenador("Info", pieza);
            botonInfo.setBackground(colorBoton);
            UtilBoton.asignarHoverBoton(botonInfo, colorBoton.darker());
            panelMostrarInfo.add(lblP); 
            panelMostrarInfo.add(botonInfo);

            //Agrega funcionalidad al botón de mostrarInfo
            botonInfo.addActionListener(e -> {
                coordinadorPresentacion.abrirInfoPiezaBodega(this, pieza);
            });

            //Agrega al panel principal
            tarjeta.add(panelInfoBasica, BorderLayout.WEST);
            tarjeta.add(panelMostrarInfo, BorderLayout.EAST);

            //Agrega al panel
            contenedorListaPiezas.add(tarjeta);
            contenedorListaPiezas.add(Box.createVerticalStrut(15));
        }
    }

    private void dibujarTarjetasCarrito() {
        
        //Declara variables
        String nombre;
        int cantidad;
        double costo;
        double subtotal;
        
        for (DetallesVentaDTO detalle: coordinadorEstados.getCarritoSolicitud()) {
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
                coordinadorPresentacion.abrirInfoDetalleSolicitud(ViniciarSolicitud.this, detalle);
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

    /** Redibuja el panel del catálogo de piezas */
    private void reconsultarPiezasFiltro() {
        contenedorListaPiezas.removeAll();
        dibujarTarjetasPiezas(piezasMostrar);
        contenedorListaPiezas.revalidate();
        contenedorListaPiezas.repaint();
        scrollPiezas.revalidate();
        scrollPiezas.repaint();
    }

    @Override
    public void observar() {
        totalCarrito = coordinadorEstados.totalCarritoSolicitud();
        labelTotal.setText("Total: $ " + totalCarrito);
        contenedorListaDetalles.removeAll();
        dibujarTarjetasCarrito();
        contenedorListaDetalles.revalidate();
        scrollDetalles.revalidate();
        contenedorListaDetalles.repaint();
        scrollDetalles.repaint();
        labelTotal.revalidate();
        labelTotal.repaint();
    }
}