package pantallasVentas;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import coordinadores.CoordinadorEstados;
import coordinadores.CoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utilEstilos.Constantes;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilGeneral;
import utilPresentacion.UtilPanel;
import java.util.List;
import java.util.ArrayList;
import observadores.IObservador;
import utilEstilos.UtilBuild;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton.BotonAlmacenador;

/**
 * Pantalla donde se eligen piezas para el carrito
 * 
 * @author DANIEL
 */
public class ViniciarVenta extends JFrame implements IObservador {

    //Se usa en más de un método
    private JPanel contenedorListaPiezas;
    private JPanel contenedorListaDetalles;
    
    //CoordinadorPresentacion que mueve pantallas
    private ICoordinadorPresentacion coordinador;
    
    //Mapa que contiene los campos de búsqueda para recuperarlos después
    Map<String, JTextField> mapaCampos = new HashMap<>();
    
    //Botón de buscar como atributo para usarlo en más de un método
    JButton botonBuscar = UtilBoton.crearBoton("Buscar");
    
    //Arreglo de constantes ya definidas para los campos de texto y así no pelearnos con strings sueltos
    private String[] campos = {Constantes.PIEZA_NOMBRE, Constantes.PIEZA_CATEGORIA, Constantes.PIEZA_MARCA, Constantes.PIEZA_PRECIOMAX};
    
    //Siempre actualizado desde el CoordiandorEstados
    private List<DetallesVentaDTO> carrito = CoordinadorEstados.singleton().getCarritoVenta();
    private double totalCarrito = CoordinadorEstados.singleton().totalCarritoVenta();
    
    //Se usa en más de un método
    private JLabel labelTotal = new JLabel("Total: $ " + totalCarrito);
    
    /**
     * Constructor donde se ensambla toda el frame
     * 
     * @param coordinador que navegará entre pantallas
     */
    public ViniciarVenta(ICoordinadorPresentacion coordinador) {
        this.coordinador = coordinador;
        
        //Configuración general
        UtilSwing.configurarFrame("Iniciar venta", this);

        //Añade el panel posterior
        add(UtilBuild.crearNavegacion(this, coordinador), BorderLayout.NORTH);

        //Crea el panel principal que contiene lo importante
        JPanel contenido = new JPanel(new GridBagLayout()); 
        contenido.setBackground(Constantes.COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        //Configura el gbc
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 10, 0, 10);

        //Agrega a dicho panel
        g.gridx = 0; g.weightx = 0.25; g.weighty = 1.0; contenido.add(crearPanelBusqueda(), g);
        
        //Agrega los paneles
        JPanel panelSeccionCentral = crearSeccionCentral(CoordinadorNegocio.singleton().consultarPiezas());
        g.gridx = 1; g.weightx = 0.40; contenido.add(panelSeccionCentral, g);
        
        boolean PROBANDO = true;
        if(PROBANDO) {
            JPanel panelSeccionDerecha = crearSeccionDerecha();
            g.gridx = 2; g.weightx = 0.35; contenido.add(panelSeccionDerecha, g);
        }


        //Añade al frame
        add(contenido, BorderLayout.CENTER);
        add(crearBarraInferior(), BorderLayout.SOUTH);
    }
    
    
    /**
     * Crea la sección central, donde aparecen las tarjetas de todas las piezas
     * 
     * @param piezas
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

        //Llena el atributo del contenedor
        contenedorListaPiezas = new JPanel();
        contenedorListaPiezas.setLayout(new BoxLayout(contenedorListaPiezas, BoxLayout.Y_AXIS));
        contenedorListaPiezas.setBackground(Color.WHITE);
        
        //Dibuja un campo por cada pieza
        dibujarTarjetasPiezas(piezas);
        
        //Crea y configura un scroll por si son varios
        JScrollPane scroll = new JScrollPane(contenedorListaPiezas);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        p.add(scroll, BorderLayout.CENTER);
        
        //Regresa el panel
        return p;
    }
    
    
    
    private JPanel crearSeccionDerecha () {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        //Encabezado
        JLabel titulo = new JLabel("Piezas", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        p.add(titulo, BorderLayout.NORTH);
        
        //Llena el atributo del contenedor de detalles
        contenedorListaDetalles = new JPanel();
        contenedorListaDetalles.setLayout(new BoxLayout(contenedorListaDetalles, BoxLayout.Y_AXIS));
        contenedorListaDetalles.setBackground(Color.WHITE);

        //Campo de los detalles
        dibujarTarjetasCarrito();

        //Crea un scroll para la lista de detalles
        JScrollPane scrollD = new JScrollPane(contenedorListaDetalles);
        scrollD.setBorder(null);
        scrollD.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollD.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollD.getVerticalScrollBar().setUnitIncrement(16);
        p.add(scrollD, BorderLayout.CENTER);
        
        // AGREGAR EL TOTAL AQUÍ
        labelTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelTotal.setHorizontalAlignment(SwingConstants.CENTER);
        labelTotal.setBorder(new EmptyBorder(10, 0, 0, 0));
        p.add(labelTotal, BorderLayout.SOUTH);
        
        return p;
    }
    
    
    
    private JPanel crearBarraInferior() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(10, 30, 20, 30));
        
        //Crea el botón de regreso
        JButton botonRegresar = UtilBoton.crearBotonRegresar();
        botonRegresar.addActionListener(e -> coordinador.mostrarVentanaInicio());

        //Creaa el botón de continuar y le agrega navegación
        JButton botonContinuar = UtilBoton.crearBoton("Continuar");
        botonContinuar.setPreferredSize(new Dimension(200, 50));
        botonContinuar.addActionListener(e -> {
            UtilSwing.dialogoAviso(this, "Procesando venta...");
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
        JPanel p = UtilPanel.crearPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        //Encabezado del panel 
        JLabel titulo = new JLabel("Buscar", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        gbc.gridy = 0; gbc.weighty = 0.1;
        p.add(titulo, gbc);
        
        //Va guardando la posición que tiene cada elemento verticalmente
        int ordenGbc = 1;
        
        /**
         * Empieza a iterar sobre el arreglo de campos
         * Por cada iteración, crea el label de indicaciones y el campo para ingresar información
         * Al final, lo agrega al campo para que no quede aislado
         */
        for (String stringCampo: campos) {
            
            //Crea un label con indicaciones del campo respectivo
            JLabel label = new JLabel(stringCampo);
            label.setFont(Constantes.FUENTE);
            gbc.gridy = ordenGbc++;
            gbc.insets = new Insets(5, 20, 0, 20);
            p.add(label, gbc);
            
            //Crea el campo de búsqueda
            gbc.gridy = ordenGbc++;
            gbc.insets = new Insets(3, 20, 10, 20);
            JTextField campoBuscar = UtilGeneral.crearCampoTexto();
            p.add(campoBuscar, gbc);
            
            //Lo agrega al mapa para poder rescatarlo
            mapaCampos.put(stringCampo, campoBuscar);
        }
        
        //Configura el botón de búsqueda
        botonBuscar.setPreferredSize(new Dimension(0, 45));
        gbc.gridy = ordenGbc++; 
        gbc.weighty = 0.1; 
        gbc.insets = new Insets(10, 20, 30, 20);
        p.add(botonBuscar, gbc);
        return p;
    }
    
    
    //Crea el panel del carrito
    private JPanel crearPanelCarrito() {
        labelTotal.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        
        JLabel t = new JLabel("Carrito"); t.setFont(new Font("Segoe UI", Font.BOLD, 20));

        p.add(t); 
        p.add(Box.createVerticalStrut(10)); 
        p.add(labelTotal);
        return p;
    }
    
    
    
    
    private void dibujarTarjetasCarrito() {
        
        //Declara variables
        String nombre;
        int cantidad;
        double costo;
        double subtotal;
        
        for (DetallesVentaDTO detalle: CoordinadorEstados.singleton().getCarritoVenta()) {
            JPanel tarjeta = UtilPanel.dibujarTarjeta();
            
            //Asigna valores
            nombre = detalle.getPieza().getNombre();
            cantidad = detalle.getCantidad();
            costo = detalle.getCosto();
            subtotal = detalle.getSubtotal();
            
             //Crea el panel de información básica
            JPanel panelInfoBasica = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
            panelInfoBasica.setOpaque(false);
            JLabel ico = new JLabel("▣"); ico.setFont(new Font("Arial", Font.BOLD, 40));
            String desc = "<html><font color='white' size='4'><b>"+nombre+" (" + cantidad + ")</b></font><br><font color='white'>$ "+costo+"</font></html>";
            panelInfoBasica.add(ico); 
            panelInfoBasica.add(new JLabel(desc));
            
            //Sección para mostrar información adicional: el precio y el botón de detalles
            JPanel panelMostrarInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
            panelMostrarInfo.setOpaque(false);
            JLabel lblP = new JLabel("$" + subtotal); 
            lblP.setForeground(new Color(50, 255, 100));
            lblP.setFont(new Font("Segoe UI", Font.BOLD, 22));
            
            //Crea un botón de información adicional
            Color colorBoton = new Color(50, 255, 100);
            UtilBoton.BotonAlmacenador botonInfo = new BotonAlmacenador("Más información", detalle);
            botonInfo.setBackground(colorBoton);
            UtilBoton.asignarHoverBoton(botonInfo, colorBoton.darker());
            panelMostrarInfo.add(lblP); 
            panelMostrarInfo.add(botonInfo);
            
            //Agrega funcionalidad al botón de mostrarInfo
            botonInfo.addActionListener(e -> {
                UtilSwing.dialogoAviso(ViniciarVenta.this, "Aún en proceso..."); //-> FIXME: TEMPORAL
                //coordinador.abrirDialogo(() -> new InfoDetalle(ViniciarVenta.this, botonInfo.getDTO())); //-> FIXME: LUEGO SE MANDA A UNA CLASE EN ESPECÍFICO
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
        double precio;
        
        //Por cada pieza de la lista...
        for (PiezaDTO pieza: piezas) {
            JPanel tarjeta = UtilPanel.dibujarTarjeta();
        
            //Asigna valores
            nombre = pieza.getNombre();
            marca = pieza.getMarcaPieza();
            precio = pieza.getCostoPieza();
            
            //Crea el panel de información básica
            JPanel panelInfoBasica = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
            panelInfoBasica.setOpaque(false);
            JLabel ico = new JLabel("▣"); ico.setFont(new Font("Arial", Font.BOLD, 40));
            String desc = "<html><font color='white' size='4'><b>"+nombre+"</b></font><br><font color='white'>"+marca+"</font></html>";
            panelInfoBasica.add(ico); 
            panelInfoBasica.add(new JLabel(desc));

            //Sección para mostrar información adicional: el precio y el botón de detalles
            JPanel panelMostrarInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
            panelMostrarInfo.setOpaque(false);
            JLabel lblP = new JLabel("$" + precio); 
            lblP.setForeground(new Color(50, 255, 100));
            lblP.setFont(new Font("Segoe UI", Font.BOLD, 22));

            //Crea un botón de información adicional
            Color colorBoton = new Color(50, 255, 100);
            UtilBoton.BotonAlmacenador botonInfo = new BotonAlmacenador("Más información", pieza);
            botonInfo.setBackground(colorBoton);
            UtilBoton.asignarHoverBoton(botonInfo, colorBoton.darker());
            panelMostrarInfo.add(lblP); 
            panelMostrarInfo.add(botonInfo);

            //Agrega funcionalidad al botón de mostrarInfo
            botonInfo.addActionListener(e -> {
                coordinador.abrirDialogo(() -> new infoPieza(ViniciarVenta.this, botonInfo.getDTO()));
            });

            //Agrega al panel principal
            tarjeta.add(panelInfoBasica, BorderLayout.WEST);
            tarjeta.add(panelMostrarInfo, BorderLayout.EAST);

            //Agrega al panel
            contenedorListaPiezas.add(tarjeta);
            contenedorListaPiezas.add(Box.createVerticalStrut(15));
        }
    }

    
    
    /** Actualiza el label que indica el total del carrito directamente del coordinador */
    @Override
    public void observar() {
        totalCarrito = CoordinadorEstados.singleton().totalCarritoVenta();
        labelTotal.setText("Total: $ " + totalCarrito);
        contenedorListaDetalles.removeAll();
        dibujarTarjetasCarrito();
        contenedorListaDetalles.revalidate();
        contenedorListaDetalles.repaint();
    }
}