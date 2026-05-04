package pantallasVentas;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import coordinadores.CoordinadorEstados;
import coordinadores.ICoordinadorEstados;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import excepciones.PresentacionException;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utilEstilos.Constantes;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilPanel;
import java.util.List;
import observadores.IObservador;
import utilEstilos.UtilFormato;
import utilPresentacion.FachadaUtil;
import utilPresentacion.UtilBoton.BotonAlmacenador;

/**
 * Pantalla donde se eligen piezas para el carrito
 * 
 * @author Andre
 */
public class ViniciarVenta extends JFrame implements IObservador {
    JPanel panelPrincipal;
    
    //Se usa en más de un método
    private JPanel contenedorListaPiezas = new JPanel();
    private JPanel contenedorListaDetalles = new JPanel();
    
    JScrollPane scrollDetalles = new JScrollPane();
    JScrollPane scrollPiezas = new JScrollPane();
    
    //Coordinadores
    private ICoordinadorPresentacion coordinadorPresentacion;
    private ICoordinadorNegocio coordinadorNegocio;
    private ICoordinadorEstados coordinadorEstados;
    
    //Mapa que contiene los campos de búsqueda para recuperarlos después
    Map<String, JButton> mapaBotonesFiltros = new HashMap<>();
    
    //Botón de buscar como atributo para usarlo en más de un método
    JButton botonBuscar = FachadaUtil.crearBoton("Buscar");
    
    //Arreglo de constantes ya definidas para los campos de texto y así no pelearnos con strings sueltos
    private String[] campos = {Constantes.PIEZA_NOMBRE, Constantes.PIEZA_CATEGORIA, Constantes.PIEZA_MARCA, Constantes.PIEZA_PRECIOMAX};
    
    //Siempre actualizado desde el CoordiandorEstados
    private double totalCarrito = CoordinadorEstados.singleton().totalCarritoVenta();
    
    //Se usa en más de un método
    private JLabel labelTotal = new JLabel("Total: $ " + totalCarrito);
    
    private List<PiezaDTO> piezasMostrar = new ArrayList<>();
    
    
    
    
    /**
     * Constructor donde se ensambla toda el frame
     * 
     * @param coordinadorPresentacion que navegará entre pantallas
     * @param coordinadorNegocio para lógica de procesos
     * @param coordinadorEstados
     */
    public ViniciarVenta(ICoordinadorPresentacion coordinadorPresentacion, ICoordinadorNegocio coordinadorNegocio, ICoordinadorEstados coordinadorEstados) {
        this.coordinadorPresentacion = coordinadorPresentacion;
        this.coordinadorNegocio = coordinadorNegocio;
        this.coordinadorEstados = coordinadorEstados;
        
        //Configuración general
        FachadaUtil.configurarFrame("Iniciar venta", this);

        //Añade el panel posterior
        add(FachadaUtil.crearNavegacion(this, coordinadorPresentacion), BorderLayout.NORTH);

        //Crea el panel principal que contiene lo importante
        JPanel contenido = new JPanel(new GridBagLayout()); 
        contenido.setBackground(Constantes.COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        contenedorListaPiezas.setLayout(new BoxLayout(contenedorListaPiezas, BoxLayout.Y_AXIS));
        contenedorListaDetalles.setLayout(new BoxLayout(contenedorListaDetalles, BoxLayout.Y_AXIS));
        scrollPiezas.setViewportView(contenedorListaPiezas);
        scrollDetalles.setViewportView(contenedorListaDetalles);
        
        //Actualiza las listas
        piezasMostrar = coordinadorNegocio.consultarPiezas();
        
        //Configura el gbc
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 10, 0, 10);

        //Agrega a dicho panel
        g.gridx = 0; g.weightx = 0.10; g.weighty = 1.0; contenido.add(crearPanelBusqueda(), g);
        
        //Agrega los paneles
        JPanel panelSeccionCentral = crearSeccionCentral(piezasMostrar);
        g.gridx = 1; g.weightx = 0.30; contenido.add(panelSeccionCentral, g);
        g.gridx = 2; g.weightx = 0.30; contenido.add(crearPanelCarrito(), g);
        
        //Añade al frame
        add(contenido, BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }
    
    /**
     * Crea la sección central, donde aparecen las tarjetas de todas las piezas
     * 
     * @param piezas en una lista
     * 
     * @return el panel listo
     */
    private JPanel crearSeccionCentral(List<PiezaDTO> piezas) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        //Encabezado
        JLabel titulo = new JLabel("Piezas", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        p.add(titulo, BorderLayout.NORTH);

        contenedorListaPiezas.removeAll(); 
        dibujarTarjetasPiezas(piezas);
        scrollPiezas.setBorder(null);
        scrollPiezas.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPiezas.getVerticalScrollBar().setUnitIncrement(16);
        p.add(scrollPiezas, BorderLayout.CENTER);
        
        //Regresa el panel
        return p;
    }
    
    /**
     * Crea el panel derecho que contiene gráficamente a
     * los elementos del carrito
     * 
     * @return panel del del carrito
     */
    private JPanel crearPanelCarrito () {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        //Encabezado
        JLabel titulo = new JLabel("Carrito", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        p.add(titulo, BorderLayout.NORTH);
        
        //Llena el atributo del contenedor de detalles
        contenedorListaDetalles = new JPanel();
        contenedorListaDetalles.setLayout(new BoxLayout(contenedorListaDetalles, BoxLayout.Y_AXIS));
        contenedorListaDetalles.setBackground(Color.WHITE);

        /**
         * Cuando el botón filtrar sea cliqueado, se dispara
         * el filtro de las piezas YA traídas de la BD por
         * 
         * 
         */
        botonBuscar.addActionListener(e -> {
            reconsultarPiezasFiltro();
        });
        p.add(botonBuscar);
        
        //Campo de los detalles
        dibujarTarjetasCarrito();

        //Crea un scroll para la lista de detalles
        scrollDetalles = new JScrollPane(contenedorListaDetalles);
        scrollDetalles.setBorder(null);
        scrollDetalles.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDetalles.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDetalles.getVerticalScrollBar().setUnitIncrement(16);
        p.add(scrollDetalles, BorderLayout.CENTER);
        
        //Total
        labelTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelTotal.setHorizontalAlignment(SwingConstants.CENTER);
        labelTotal.setBorder(new EmptyBorder(10, 0, 0, 0));
        p.add(labelTotal, BorderLayout.SOUTH);
        return p;
    }
    
    /**  Crea la barra inferior para continuar y regresar */
    private JPanel crearPanelInferior() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(10, 30, 20, 30));
        
        //Crea el botón de regreso
        JButton botonRegresar = FachadaUtil.crearBotonRegresar();
        botonRegresar.addActionListener(e -> coordinadorPresentacion.mostrarVentanaInicio());

        //Creaa el botón de continuar y le agrega navegación
        JButton botonContinuar = FachadaUtil.crearBoton("Continuar");
        botonContinuar.setPreferredSize(new Dimension(200, 50));
        botonContinuar.addActionListener(e -> {
            
            //Verifica que el carrito no esté vacío
            if (coordinadorEstados.carritoVentaVacio()) {
                FachadaUtil.dialogoAlerta(this, "El carrito está vacío");
                return;
            }
            coordinadorPresentacion.abrirResumenVenta();
        });

        //Agrega los botones al panel
        p.add(botonRegresar, BorderLayout.WEST); 
        p.add(botonContinuar, BorderLayout.EAST);
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
                    FachadaUtil.dialogoAlerta(ViniciarVenta.this, "Campo vacío");
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
            piezasMostrar = coordinadorNegocio.consultarPiezas();
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
    
    /**
     * Asigna la naturaleza de filtrado del botón según el 
     * campo correspondiente
     * 
     * @param filtro
     * @param stringCampo 
     */
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
                    FachadaUtil.dialogoAlerta(ViniciarVenta.this, "Ingrese un número entero positivo");
                    return;
                }
            }
            default -> throw new PresentacionException("Categoría inválida para filtrar piezas");
        }
        reconsultarPiezasFiltro();
    }
    
    /**
     * En un bucle for por cada detalle del carrito crea
     * una tarjeta nueva con información específica y un
     * botón almacenador para un diálogo de info
     */
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
                coordinadorPresentacion.abrirInfoDetalle(ViniciarVenta.this, detalle);
            });
            
            //Agrega al panel principal
            tarjeta.add(panelInfoBasica, BorderLayout.WEST);
            tarjeta.add(panelMostrarInfo, BorderLayout.EAST);

            //Agrega al panel
            contenedorListaDetalles.add(tarjeta);
            contenedorListaDetalles.add(Box.createVerticalStrut(15));
        }
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
                coordinadorPresentacion.abrirInfoPieza(this, pieza);
            });

            //Agrega al panel principal
            tarjeta.add(panelInfoBasica, BorderLayout.WEST);
            tarjeta.add(panelMostrarInfo, BorderLayout.EAST);

            //Agrega al panel
            contenedorListaPiezas.add(tarjeta);
            contenedorListaPiezas.add(Box.createVerticalStrut(15));
        }
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
    
    /** Actualiza el total y los detalles directamente del carrito así como redibuja todo */
    @Override
    public void observar() {
        totalCarrito = coordinadorEstados.totalCarritoVenta();
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