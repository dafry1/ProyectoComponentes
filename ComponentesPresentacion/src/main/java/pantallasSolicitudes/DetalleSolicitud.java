package pantallasSolicitudes;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.SolicitudDTO;
import ensambladores.IEnsambladorDTO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * Muestra el detalle completo de una Solicitud, incluyendo historial de envío y dirección.
 * @author DANIEL
 */
public class DetalleSolicitud extends JDialog {

    public DetalleSolicitud(Frame padre, SolicitudDTO solicitud, IEnsambladorDTO ensambladorDTO) {
        super(padre, "Detalle de Solicitud - " + solicitud.getFolio(), true);

        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Panel Principal con scroll general por si el historial es largo
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(new EmptyBorder(20, 25, 20, 25));

        // --- ENCABEZADO: FOLIO Y ESTADO ACTUAL ---
        JLabel lblFolio = new JLabel("Folio: " + solicitud.getFolio());
        lblFolio.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblFolio.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Badge de estado dinámico
        JLabel lblEstadoActual = new JLabel(" " + solicitud.getEstado().toUpperCase() + " ");
        lblEstadoActual.setOpaque(true);
        lblEstadoActual.setBackground(new Color(41, 128, 185));
        lblEstadoActual.setForeground(Color.WHITE);
        lblEstadoActual.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEstadoActual.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- SECCIÓN: HISTORIAL DE ENVÍO ---
        JPanel panelHistorial = new JPanel();
        panelHistorial.setLayout(new BoxLayout(panelHistorial, BoxLayout.Y_AXIS));
        panelHistorial.setBackground(new Color(245, 247, 250));
        panelHistorial.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Estatus del Envío", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 12)));

        // Aquí simulamos o iteramos el historial si tu DTO tiene una lista de eventos
        // Si no la tiene, mostramos la fecha de pedido y entrega estimada como hitos
        String htmlHistorial = "<html><div style='padding: 5px;'>"
                + "• <b>Pedido recibido:</b> " + solicitud.getFechaEntrega() + "<br>"
                + "• <b>Procesando:</b> En almacén principal<br>"
                + "<font color='#d35400'>• <b>Entrega estimada:</b> " + solicitud.getFechaEntregaEstimada() + "</font>"
                + "</div></html>";
        JLabel lblHistorialTexto = new JLabel(htmlHistorial);
        panelHistorial.add(lblHistorialTexto);
        panelHistorial.setMaximumSize(new Dimension(400, 80));

        // --- SECCIÓN: DIRECCIÓN DE ENTREGA ---
        JPanel panelDireccion = new JPanel(new BorderLayout());
        panelDireccion.setBackground(Color.WHITE);
        panelDireccion.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        String iconoCasa = "🏠 ";
        String dirHtml = (solicitud.getDireccion() != null) 
                ? solicitud.getDireccion().toString() 
                : "No se proporcionó dirección (Recogida en sucursal)";
        
        JLabel lblDireccion = new JLabel("<html><b>Dirección de Envío:</b><br>" + iconoCasa + dirHtml + "</html>");
        lblDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panelDireccion.add(lblDireccion, BorderLayout.CENTER);

        // --- SECCIÓN: LISTA DE PRODUCTOS ---
        JPanel contenedorPiezas = new JPanel();
        contenedorPiezas.setLayout(new BoxLayout(contenedorPiezas, BoxLayout.Y_AXIS));
        contenedorPiezas.setBackground(Color.WHITE);

        for (DetallesVentaDTO detalle : solicitud.getDetalles()) {
            JPanel itemPieza = crearTarjetaPieza(detalle);
            contenedorPiezas.add(itemPieza);
            contenedorPiezas.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        JScrollPane scrollPane = new JScrollPane(contenedorPiezas);
        scrollPane.setPreferredSize(new Dimension(420, 250));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Artículos solicitados"));

        // --- TOTAL Y BOTÓN ---
        JLabel lblTotal = new JLabel("Total: $" + solicitud.getTotal());
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTotal.setForeground(new Color(39, 174, 96));
        lblTotal.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnCerrar = new JButton("Entendido");
        btnCerrar.setPreferredSize(new Dimension(120, 40));
        btnCerrar.setBackground(new Color(41, 128, 185));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(e -> this.dispose());

        // Ensamblado
        panelPrincipal.add(lblFolio);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
        panelPrincipal.add(lblEstadoActual);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        panelPrincipal.add(panelHistorial);
        panelPrincipal.add(panelDireccion);
        panelPrincipal.add(scrollPane);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(lblTotal);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        panelPrincipal.add(btnCerrar);

        this.add(panelPrincipal);
        this.pack();
        this.setLocationRelativeTo(padre);
    }

    private JPanel crearTarjetaPieza(DetallesVentaDTO detalle) {
        PiezaDTO pieza = detalle.getPieza();
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(252, 252, 252));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                new EmptyBorder(8, 10, 8, 10)));
        
        String texto = "<html><b>" + pieza.getNombre() + "</b><br>"
                + "<font color='gray'>" + pieza.getMarcaPieza() + " | Cant: " + detalle.getCantidad() + "</font></html>";
        
        JLabel info = new JLabel(texto);
        JLabel subtotal = new JLabel("$" + detalle.getSubtotal());
        subtotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        subtotal.setForeground(new Color(44, 62, 80));

        p.add(info, BorderLayout.CENTER);
        p.add(subtotal, BorderLayout.EAST);
        
        p.setMaximumSize(new Dimension(400, 60));
        return p;
    }
}