package pantallasSolicitudes;

import DTOS.DetallesVentaDTO;
import coordinadores.ICoordinadorEstados;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import ensambladores.IEnsambladorDTO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utilEstilos.Constantes;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilPanel;
import java.util.List;
import observadores.IObservador;
import utilEstilos.UtilBuild;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton.BotonAlmacenador;

/**
 * Pantalla que muestra el resumen del carrito antes de generar la Solicitud
 * * @author Andre (Adaptado para Solicitudes)
 */
public class PantallaResumenSolicitud extends JFrame implements IObservador {

    private JPanel contenedorListaDetalles;

    // Coordinadores
    private ICoordinadorPresentacion coordinadorPresentacion;
    private ICoordinadorNegocio coordinadorNegocio;
    private ICoordinadorEstados coordinadorEstados;
    private IEnsambladorDTO ensambladorDTO;

    private double totalSolicitud;
    private JLabel labelTotal;

    public PantallaResumenSolicitud(ICoordinadorPresentacion cp, ICoordinadorNegocio cn, ICoordinadorEstados ce, IEnsambladorDTO ens) {
        this.coordinadorPresentacion = cp;
        this.coordinadorNegocio = cn;
        this.coordinadorEstados = ce;
        this.ensambladorDTO = ens;
        this.totalSolicitud = coordinadorEstados.totalCarritoSolicitud();
        this.labelTotal = new JLabel("Total Solicitud: $ " + totalSolicitud);

        // Configuración general: Título adaptado
        UtilSwing.configurarFrame("Resumen de Solicitud de Pedido", this);

        // Panel de navegación superior
        add(UtilBuild.crearNavegacion(this, coordinadorPresentacion), BorderLayout.NORTH);

        // Panel principal de contenido
        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Constantes.COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(10, 10, 10, 10);

        g.gridx = 0;
        g.weightx = 1.0;
        g.weighty = 1.0;
        contenido.add(crearPanelCarrito(), g);

        add(contenido, BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelCarrito() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel titulo = new JLabel("Artículos en Solicitud", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        p.add(titulo, BorderLayout.NORTH);

        contenedorListaDetalles = new JPanel();
        contenedorListaDetalles.setLayout(new BoxLayout(contenedorListaDetalles, BoxLayout.Y_AXIS));
        contenedorListaDetalles.setBackground(Color.WHITE);

        dibujarTarjetasCarrito();

        JScrollPane scrollD = new JScrollPane(contenedorListaDetalles);
        scrollD.setBorder(null);
        scrollD.getVerticalScrollBar().setUnitIncrement(16);
        p.add(scrollD, BorderLayout.CENTER);

        labelTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTotal.setHorizontalAlignment(SwingConstants.CENTER);
        labelTotal.setBorder(new EmptyBorder(15, 0, 0, 0));
        p.add(labelTotal, BorderLayout.SOUTH);

        return p;
    }

    private JPanel crearPanelInferior() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(15, 30, 20, 30));

        JButton botonRegresar = UtilBoton.crearBotonRegresar();
        botonRegresar.addActionListener(e -> coordinadorPresentacion.mostrarVentanaInicio());

        // Cambio de texto: De "Finalizar Venta" a "Datos de Envío"
        JButton botonContinuar = UtilBoton.crearBoton("Datos de Envío");
        botonContinuar.setPreferredSize(new Dimension(220, 50));
        botonContinuar.addActionListener(e -> {
            if (coordinadorEstados.getCarritoSolicitud().isEmpty()) {
                UtilSwing.dialogoAlerta(this, "No hay artículos para solicitar");
                return;
            }
            
            InfoClienteSolicitud info = new InfoClienteSolicitud(
                coordinadorPresentacion, 
                coordinadorNegocio, 
                coordinadorEstados, 
                this
            );
            info.setVisible(true);
        });

        p.add(botonRegresar, BorderLayout.WEST);
        p.add(botonContinuar, BorderLayout.EAST);
        return p;
    }

    private void dibujarTarjetasCarrito() {
        for (DetallesVentaDTO detalle : coordinadorEstados.getCarritoSolicitud()) {
            JPanel tarjeta = UtilPanel.dibujarTarjeta();
            tarjeta.setLayout(new BorderLayout(10, 10));
            
            // Color representativo para solicitudes (Azul opcional, o mantener estilo)
            tarjeta.setBackground(new Color(41, 128, 185)); 

            String nombre = detalle.getPieza().getNombre();
            int cantidad = detalle.getCantidad();
            double costo = detalle.getCosto();
            double subtotal = detalle.getSubtotal();

            JPanel panelInfoBasica = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panelInfoBasica.setOpaque(false);

            String desc = "<html><body style='width: 200px'>"
                    + "<font color='white' size='4'><b>" + nombre + "</b></font><br>"
                    + "<font color='white' size='3'>Cantidad: " + cantidad + " unid.</font><br>"
                    + "<font color='white' size='3'>Precio unit: $" + costo + "</font></body></html>";
            panelInfoBasica.add(new JLabel(desc));

            JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
            panelDerecho.setOpaque(false);

            JLabel lblSubtotal = new JLabel("$" + subtotal);
            lblSubtotal.setForeground(new Color(50, 255, 100)); // Verde neón para el dinero
            lblSubtotal.setFont(new Font("Segoe UI", Font.BOLD, 22));

            BotonAlmacenador botonInfo = new BotonAlmacenador("Detalles", detalle);
            botonInfo.addActionListener(e -> {
                coordinadorPresentacion.abrirInfoDetalle(this, detalle);
            });

            panelDerecho.add(lblSubtotal);
            panelDerecho.add(botonInfo);

            tarjeta.add(panelInfoBasica, BorderLayout.WEST);
            tarjeta.add(panelDerecho, BorderLayout.EAST);

            contenedorListaDetalles.add(tarjeta);
            contenedorListaDetalles.add(Box.createVerticalStrut(10));
        }
    }

    @Override
    public void observar() {
        totalSolicitud = coordinadorEstados.totalCarritoSolicitud();
        labelTotal.setText("Total Solicitud: $ " + totalSolicitud);
        contenedorListaDetalles.removeAll();
        dibujarTarjetasCarrito();
        contenedorListaDetalles.revalidate();
        contenedorListaDetalles.repaint();
    }
}