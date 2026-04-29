package pantallasVentas;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.VentaDTO;
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
 * Pantalla que muestra el resumen del carrito
 *
 * @author Andre
 */
public class PantallaResumen extends JFrame implements IObservador {

    private JPanel contenedorListaDetalles;

    // Coordinadores
    private ICoordinadorPresentacion coordinadorPresentacion;
    private ICoordinadorNegocio coordinadorNegocio;
    private ICoordinadorEstados coordinadorEstados;
    private IEnsambladorDTO ensambladorDTO;

    private double totalCarrito;
    private JLabel labelTotal;

    public PantallaResumen(ICoordinadorPresentacion coordinadorPresentacion, ICoordinadorNegocio coordinadorNegocio, ICoordinadorEstados coordinadorEstados, IEnsambladorDTO ensambladorDTO) {
        this.coordinadorPresentacion = coordinadorPresentacion;
        this.coordinadorNegocio = coordinadorNegocio;
        this.coordinadorEstados = coordinadorEstados;
        this.ensambladorDTO = ensambladorDTO;
        this.totalCarrito = coordinadorEstados.totalCarritoVenta();
        this.labelTotal = new JLabel("Total: $ " + totalCarrito);

        // Configuración general
        UtilSwing.configurarFrame("Resumen de Venta", this);

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

        JLabel titulo = new JLabel("Resumen del Carrito", SwingConstants.CENTER);
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

        JButton botonContinuar = UtilBoton.crearBoton("Finalizar Venta");
        botonContinuar.setPreferredSize(new Dimension(220, 50));
        botonContinuar.addActionListener(e -> {
            if (coordinadorEstados.getCarritoVenta().isEmpty()) {
                UtilSwing.dialogoAlerta(this, "El carrito está vacío");
                return;
            }
            //confirmarVenta();
            coordinadorPresentacion.abrirInfoCliente(this, ensambladorDTO);
        });

        p.add(botonRegresar, BorderLayout.WEST);
        p.add(botonContinuar, BorderLayout.EAST);
        return p;
    }

    private void dibujarTarjetasCarrito() {
        for (DetallesVentaDTO detalle : coordinadorEstados.getCarritoVenta()) {
            JPanel tarjeta = UtilPanel.dibujarTarjeta();
            tarjeta.setLayout(new BorderLayout(10, 10));

            String nombre = detalle.getPieza().getNombre();
            int cantidad = detalle.getCantidad();
            double costo = detalle.getCosto();
            double subtotal = detalle.getSubtotal();

            JPanel panelInfoBasica = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panelInfoBasica.setOpaque(false);

            String desc = "<html><body style='width: 150px'>"
                    + "<font color='white' size='4'><b>" + nombre + "</b></font><br>"
                    + "<font color='white' size='3'>Cant: " + cantidad + " x $" + costo + "</font></body></html>";
            panelInfoBasica.add(new JLabel(desc));

            JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
            panelDerecho.setOpaque(false);

            JLabel lblSubtotal = new JLabel("$" + subtotal);
            lblSubtotal.setForeground(new Color(50, 255, 100));
            lblSubtotal.setFont(new Font("Segoe UI", Font.BOLD, 20));

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
        totalCarrito = coordinadorEstados.totalCarritoVenta();
        labelTotal.setText("Total: $ " + totalCarrito);
        contenedorListaDetalles.removeAll();
        dibujarTarjetasCarrito();
        contenedorListaDetalles.revalidate();
        contenedorListaDetalles.repaint();
    }
}
