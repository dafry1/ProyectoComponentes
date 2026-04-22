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
import observadores.IObservador;
import utilEstilos.UtilBuild;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton.BotonAlmacenador;

/**
 * Pantalla donde se despliega el carrito justo antes de 
 * procesar la venta
 * 
 * @author Andre
 */
public class PantallaResumen extends JFrame implements IObservador {

    //Se usa en más de un método
    private JPanel contenedorListaDetalles;
    
    //CoordinadorPresentacion que mueve pantallas
    private ICoordinadorPresentacion coordinador;
    
    //Siempre actualizado desde el CoordiandorEstados
    private double totalCarrito = CoordinadorEstados.singleton().totalCarritoVenta();
    
    //Se usa en más de un método
    private JLabel labelTotal = new JLabel("Total: $ " + totalCarrito);
    
    /**
     * Constructor donde se ensambla toda el frame
     * 
     * @param coordinador que navegará entre pantallas
     */
    public PantallaResumen(ICoordinadorPresentacion coordinador) {
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
        
        //Agrega los paneles
        g.gridx = 1; g.weightx = 0.30; contenido.add(crearPanelCarrito(), g);
        
        //Añade al frame
        add(contenido, BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
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
        JButton botonRegresar = UtilBoton.crearBotonRegresar();
        botonRegresar.addActionListener(e -> coordinador.mostrarVentanaInicio());

        //Creaa el botón de continuar y le agrega navegación
        JButton botonContinuar = UtilBoton.crearBoton("Continuar");
        botonContinuar.setPreferredSize(new Dimension(200, 50));
        botonContinuar.addActionListener(e -> {
            
            //Obtiene el carrito del CoordinadorEstados
            List<DetallesVentaDTO> carrito = CoordinadorEstados.singleton().getCarritoVenta();
            
            //Verifica que el carrito no esté vacío
            if (carrito.isEmpty()) {
                UtilSwing.dialogoAlerta(this, "El carrito está vacío");
                return;
            }
            
            //Procesa la venta
            //CoordinadorNegocio.getInstance().procesarVenta(carrito, PantallaResumen.this);
        });

        //Agrega los botones al panel
        p.add(botonRegresar, BorderLayout.WEST); 
        p.add(botonContinuar, BorderLayout.EAST);
        return p;
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
        
        for (DetallesVentaDTO detalle: CoordinadorEstados.singleton().getCarritoVenta()) {
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
            String desc = "<html><body style='width: 120px'>" +
                          "<font color='white' size='3'><b>"+nombre+"</b> ("+cantidad+")</font><br>" +
                          "<font color='white' size='2'>$ "+costo+"</font></body></html>";
            panelInfoBasica.add(new JLabel(desc));
            
            //Sección para mostrar información adicional: el precio y el botón de detalles
            JPanel panelMostrarInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
            panelMostrarInfo.setOpaque(false);
            
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
                UtilSwing.dialogoAviso(PantallaResumen.this, "Aún en proceso..."); //-> FIXME: TEMPORAL
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
     * En orden: 
     * 1. Obtiene el nuevo total del CoordinadorEstados
     * 2. Actualiza el label con ese nuevo total
     * 3. Vacía la lista de detalles del frame
     * 4. Recalcula y redibuja el panel
     */
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