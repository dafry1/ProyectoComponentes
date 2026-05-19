package pantallasVentas;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import coordinadores.ICoordinadorPresentacion;
import ensambladores.IEnsambladorDTO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import observadores.IObservador;
import utilPresentacion.Util;

public class DetalleVenta extends JDialog implements IObservador {
    ICoordinadorPresentacion coordinadorPresentacion;
    IObservador observador;
    
    public DetalleVenta(ICoordinadorPresentacion cp, Frame padre, VentaDTO venta, IEnsambladorDTO ensambladorDTO, IObservador observador) {
        super(padre, "Detalle de Venta", true);
        
        coordinadorPresentacion = cp;
        this.observador = observador;
        
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Panel Principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(new Color(238, 238, 238));
        panelPrincipal.setBorder(new EmptyBorder(30, 40, 30, 40));

        // --- ENCABEZADO ---
        JLabel lblFolio = new JLabel("Folio: " + venta.getFolio());
        lblFolio.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblFolio.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblFecha = new JLabel(venta.getFechaHora());
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblFecha.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Nombre completo de ambos participantes
        String empleadoNom = venta.getEmpleado().nombreCompleto();
        String clienteNom = venta.getCliente().nombreCompleto();
        
        String htmlGente = "<html><center>Atendido por: <b>" + empleadoNom + "</b><br>"
                + "Cliente: <b>" + clienteNom + "</b></center></html>";
        
        JLabel lblGente = new JLabel(htmlGente);
        lblGente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblGente.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- SECCIÓN DETALLES (Contenedor de tarjetas) ---
        JPanel contenedorPiezas = new JPanel();
        contenedorPiezas.setLayout(new BoxLayout(contenedorPiezas, BoxLayout.Y_AXIS));
        contenedorPiezas.setBackground(Color.WHITE); // Fondo blanco para que resalten las piezas

        for (DetallesVentaDTO detalle : venta.getDetalles()) {
            PiezaDTO pieza = detalle.getPieza();
            
            String htmlPieza = "<html><center><div style='width: 300px; padding: 5px;'>"
                    + "<p><font size='5'>Nombre: <b>" + pieza.getNombre() + "</b></font></p>"
                    + "<p>Marca: " + pieza.getMarcaPieza() + " | Modelo: " + pieza.getModeloPieza() + "</p>"
                    + "<p style='margin-top: 5px;'>Cantidad: <b>" + detalle.getCantidad() + "</b></p>"
                    + "<p>Precio unit: $" + detalle.getCosto() + "</p>"
                    + "<p><font size='5' color='#27ae60'>Subtotal: <b>$" + detalle.getSubtotal() + "</b></font></p>"
                    + "<p>__________________________________________</p>"
                    + "</div></center></html>";
            
            JLabel lblPieza = new JLabel(htmlPieza);
            lblPieza.setAlignmentX(Component.CENTER_ALIGNMENT);
            contenedorPiezas.add(lblPieza);
            contenedorPiezas.add(Box.createRigidArea(new Dimension(0, 10)));
        }

      // --- SCROLL PANEL CONFIGURADO ---
        JScrollPane scrollPane = new JScrollPane(contenedorPiezas);
        scrollPane.setPreferredSize(new Dimension(420, 350)); 
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); 
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenedorPiezas.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

        // --- TOTAL FINAL ---
        JLabel lblTotal = new JLabel("Total Final: $" + venta.getTotal());
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTotal.setForeground(new Color(44, 62, 80));
        lblTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTotal.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Ensamblado
        panelPrincipal.add(lblFolio);
        panelPrincipal.add(lblFecha);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        panelPrincipal.add(lblGente);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(scrollPane);
        panelPrincipal.add(lblTotal);
        
        //Si la venta aún no está facturada, da acceso al botón correspondiente
        if (!venta.isFacturada()) {
            JButton botonFacturar = Util.crearBoton("Facturar");
            botonFacturar.addActionListener(e -> coordinadorPresentacion.abrirIngresarRFC(DetalleVenta.this));
            panelPrincipal.add(botonFacturar);
        }
        
        this.add(panelPrincipal);
        this.pack();
        this.setLocationRelativeTo(padre);
    }

    /**
     * Es notificado si se encuentra un contribuyente, y a
     * su vez notifica al historial de ventas para que navegue
     * a la pantalla para facturar
     */
    @Override
    public void observar() {
        if (observador != null) observador.observar();
    }
}