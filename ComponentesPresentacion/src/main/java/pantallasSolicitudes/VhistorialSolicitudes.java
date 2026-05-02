package pantallasSolicitudes;

import DTOS.VentaDTO;
import coordinadores.CoordinadorEstados;
import coordinadores.CoordinadorPresentacion;
import coordinadores.ICoordinadorEstados;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import observadores.IObservador;
import utilEstilos.Constantes;
import utilEstilos.UtilBuild;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilPanel;

/**
 * Pantalla que muestra el historial de solicitudes realizadas.
 * 
 * @author Aaron
 */
public class VhistorialSolicitudes extends JFrame implements IObservador{

    JPanel panelPrincipal;

    //Se usa en más de un método
    private JPanel contenedorListaPiezas;
    private JPanel contenedorListaDetalles;

    //Coordinadores
    private ICoordinadorPresentacion coordinadorPresentacion;
    private ICoordinadorNegocio coordinadorNegocio;
    private ICoordinadorEstados coordinadorEstados;

    //Mapa que contiene los campos de búsqueda para recuperarlos después
    Map<String, JTextField> mapaCampos = new HashMap<>();

    //Botón de buscar como atributo para usarlo en más de un método
    JButton botonBuscar = UtilBoton.crearBoton("Buscar");

    //Arreglo de constantes ya definidas para los campos de texto y así no pelearnos con strings sueltos
    private String[] campos = {Constantes.PIEZA_NOMBRE, Constantes.PIEZA_CATEGORIA, Constantes.PIEZA_MARCA, Constantes.PIEZA_PRECIOMAX};

    //Siempre actualizado desde el CoordiandorEstados
    private double totalCarrito = CoordinadorEstados.singleton().totalCarritoVenta();

    //Se usa en más de un método
    private JLabel labelTotal = new JLabel("Total: $ " + totalCarrito);

    /**
     * Constructor donde se ensambla toda el frame
     *
     * @param coordinadorPresentacion que navegará entre pantallas
     * @param coordinadorNegocio para lógica de procesos
     * @param coordinadorEstados
     */
    public VhistorialSolicitudes(ICoordinadorPresentacion coordinadorPresentacion, ICoordinadorNegocio coordinadorNegocio, ICoordinadorEstados coordinadorEstados) {
        this.coordinadorPresentacion = coordinadorPresentacion;
        this.coordinadorNegocio = coordinadorNegocio;
        this.coordinadorEstados = coordinadorEstados;

        //Configuración general
        UtilSwing.configurarFrame("Historial de solicitudes", this);

        //Añade el panel posterior
        add(UtilBuild.crearNavegacion(this, coordinadorPresentacion), BorderLayout.NORTH);

        //Crea el panel principal que contiene lo importante
        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Constantes.COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        //Configura el gbc
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 10, 0, 10);

        //Agrega a dicho panel
        //g.gridx = 0; g.weightx = 0.10; g.weighty = 1.0; contenido.add(crearPanelBusqueda(), g);
        //Agrega los paneles
        JPanel panelSeccionCentral = crearSeccionCentral(coordinadorNegocio.consultarVentas());
        g.gridx = 1;
        g.weightx = 0.30;
        contenido.add(panelSeccionCentral, g);
        
        //Añade al frame
        add(contenido, BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    /**
     * Crea la sección central, donde aparecen las tarjetas de todas las ventas
     *
     * @param piezas en una lista
     *
     * @return el panel listo
     */
    private JPanel crearSeccionCentral(java.util.List<VentaDTO> ventas) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        //Encabezado
        JLabel titulo = new JLabel("Solicitudes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        p.add(titulo, BorderLayout.NORTH);

        //Llena el atributo del contenedor
        contenedorListaPiezas = new JPanel();
        contenedorListaPiezas.setLayout(new BoxLayout(contenedorListaPiezas, BoxLayout.Y_AXIS));
        contenedorListaPiezas.setBackground(Color.WHITE);

        //Dibuja un campo por cada pieza
        dibujarTarjetasVentas(ventas);

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
    
    /**
     * Crea la barra inferior para continuar y regresar
     */
    private JPanel crearPanelInferior() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(10, 30, 20, 30));

        //Crea el botón de regreso
        JButton botonRegresar = UtilBoton.crearBotonRegresar();
        botonRegresar.addActionListener(e -> coordinadorPresentacion.mostrarVentanaInicio());

        //Creaa el botón de continuar y le agrega navegación
        JButton botonContinuar = UtilBoton.crearBoton("Continuar");
        botonContinuar.setPreferredSize(new Dimension(200, 50));
        botonContinuar.addActionListener(e -> {

            //Verifica que el carrito no esté vacío
            if (coordinadorEstados.getCarritoVenta().isEmpty()) {
                UtilSwing.dialogoAlerta(this, "El carrito está vacío");
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
     * Dibuja y habita cada tarjeta que le corresponde una pieza en específico
     * Crea la tarjeta de UtilPanel Extrae los datos de la pieza Configura cómo
     * se plasma la información Crea el BotonAlmacenador mostrarInfo Esa
     * información del DTO se manda a un diálogo
     *
     * @param pieza específica
     */
    private void dibujarTarjetasVentas(java.util.List<VentaDTO> ventas) {
        for (VentaDTO venta : ventas) {
            JPanel tarjeta = UtilPanel.dibujarTarjeta();
            tarjeta.setLayout(new BorderLayout(20, 0));

            double supertotal = venta.getTotal();
            String fechaHora = venta.getFechaHora();
            String folio = venta.getFolio();
            int cantidadDetalles = venta.getDetalles().size();

            // Panel de información básica
            JPanel panelInfoBasica = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            panelInfoBasica.setOpaque(false);

            String desc = "<html><body style='width: 120px'>"
                    + "<font color='white' size='3'><b>" + folio + "</b> (" + cantidadDetalles + " elementos)</font><br>"
                    + "<font color='white' size='2'> " + fechaHora + "</font></body></html>";
            panelInfoBasica.add(new JLabel(desc));

            // Sección derecha (Precio y Botón)
            JPanel panelMostrarInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
            panelMostrarInfo.setOpaque(false);

            JLabel lblP = new JLabel("$" + supertotal);
            lblP.setForeground(new Color(50, 255, 100));
            lblP.setFont(new Font("Segoe UI", Font.BOLD, 22));

            Color colorBoton = new Color(50, 255, 100);
            UtilBoton.BotonAlmacenador botonInfo = new UtilBoton.BotonAlmacenador("Detalles", venta);
            botonInfo.setBackground(colorBoton);
            UtilBoton.asignarHoverBoton(botonInfo, colorBoton.darker());
            
            panelMostrarInfo.add(lblP);
            panelMostrarInfo.add(botonInfo);

            // FUNCIONALIDAD DEL BOTÓN: Detectar si es Venta o Solicitud
            botonInfo.addActionListener(e -> {
                if (venta instanceof DTOS.SolicitudDTO) {
                    // Si es una solicitud, abrimos nuestro nuevo diálogo modal
                    // Pasamos 'this' como Frame padre
                    DetalleSolicitud ds = new DetalleSolicitud(this, (DTOS.SolicitudDTO) venta, null); 
                    ds.setVisible(true);
                } else {
                    // Si es una venta normal, usamos el flujo que ya tenías
                    coordinadorPresentacion.abrirDetalleVenta(venta);
                }
            });

            tarjeta.add(panelInfoBasica, BorderLayout.WEST);
            tarjeta.add(panelMostrarInfo, BorderLayout.EAST);

            contenedorListaPiezas.add(tarjeta);
            contenedorListaPiezas.add(Box.createVerticalStrut(15));
        }
    }

    /**
     * En orden: 1. Obtiene el nuevo total del CoordinadorEstados 2. Actualiza
     * el label con ese nuevo total 3. Vacía la lista de detalles del frame 4.
     * Recalcula y redibuja el panel
     */
    @Override
    public void observar() {
        totalCarrito = coordinadorEstados.totalCarritoVenta();
        labelTotal.setText("Total: $ " + totalCarrito);
        contenedorListaDetalles.removeAll();
        contenedorListaDetalles.revalidate();
        contenedorListaDetalles.repaint();
    }
}