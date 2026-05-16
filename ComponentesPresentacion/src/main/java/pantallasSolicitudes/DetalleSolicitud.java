package pantallasSolicitudes;

import DTOS.DetallesSolicitudDTO;
import DTOS.PiezaDTO;
import DTOS.SolicitudDTO;
import ensambladores.IEnsambladorDTO;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class DetalleSolicitud extends JDialog {

    public DetalleSolicitud(Frame padre, SolicitudDTO solicitud, IEnsambladorDTO ensambladorDTO) {
        super(padre, "Detalle de Solicitud - " + solicitud.getFolio(), true);

        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(new EmptyBorder(25, 30, 25, 30));

        // --- ENCABEZADO ---
        JLabel lblFolio = new JLabel("Folio: " + solicitud.getFolio());
        lblFolio.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblFolio.setAlignmentX(Component.CENTER_ALIGNMENT);

        // CORRECCIÓN: Validación preventiva para evitar el NullPointerException si el estado viene nulo
        String estadoTexto = (solicitud.getEstado() != null) ? solicitud.getEstado().toUpperCase() : "PENDIENTE";
        JLabel lblEstadoActual = new JLabel(" " + estadoTexto + " ");
        
        lblEstadoActual.setOpaque(true);
        lblEstadoActual.setBackground(new Color(41, 128, 185));
        lblEstadoActual.setForeground(Color.WHITE);
        lblEstadoActual.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEstadoActual.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEstadoActual.setMaximumSize(new Dimension(140, 25)); // Incrementado ligeramente el ancho por si el texto es largo

        // --- HISTORIAL DE ENVÍO ---
        JPanel panelHistorial = new JPanel(new BorderLayout());
        panelHistorial.setBackground(new Color(245, 247, 250));
        panelHistorial.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                "Estatus del Envío", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12)));

        String fechaEnvio = (solicitud.getFechaHora() != null) ? solicitud.getFechaHora() : "No disponible";
        String fechaEstimada = (solicitud.getFechaEntregaEstimada() != null) ? solicitud.getFechaEntregaEstimada() : "De 3 a 5 dias";

        String htmlHistorial = "<html><div style='padding: 10px;'>"
                + "• <b>Pedido recibido:</b> " + fechaEnvio + "<br>"
                + "• <b>Procesando:</b> En almacén principal<br>"
                + "<font color='#d35400'>• <b>Entrega estimada:</b> " + fechaEstimada + "</font>"
                + "</div></html>";

        JLabel lblHistorialTexto = new JLabel(htmlHistorial);
        panelHistorial.add(lblHistorialTexto, BorderLayout.CENTER);
        panelHistorial.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelHistorial.setPreferredSize(new Dimension(450, 100));

        // --- DIRECCIÓN DE ENTREGA ---
        String dirHtml = (solicitud.getDireccion() != null) ? solicitud.getDireccion() : "Recogida en sucursal";
        JLabel lblDireccion = new JLabel("<html><div style='width: 350px;'><b>Dirección de Envío:</b><br>🏠 " + dirHtml + "</div></html>");
        lblDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDireccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDireccion.setBorder(new EmptyBorder(15, 0, 15, 0));

        // --- LISTA DE PRODUCTOS ---
        JPanel contenedorPiezas = new JPanel();
        contenedorPiezas.setLayout(new BoxLayout(contenedorPiezas, BoxLayout.Y_AXIS));
        contenedorPiezas.setBackground(Color.WHITE);

        if (solicitud.getDetalles() != null) {
            for (DetallesSolicitudDTO detalle : solicitud.getDetalles()) {
                contenedorPiezas.add(crearTarjetaPieza(detalle));
                contenedorPiezas.add(Box.createRigidArea(new Dimension(0, 8)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(contenedorPiezas);
        scrollPane.setPreferredSize(new Dimension(450, 250));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Artículos solicitados"));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // --- TOTAL (Solo muestra el valor precalculado) ---
        JLabel lblTotal = new JLabel("Total: $" + String.format("%.2f", solicitud.getTotal()));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTotal.setForeground(new Color(39, 174, 96));
        lblTotal.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- BOTÓN CERRAR ---
        JButton btnCerrar = new JButton("Entendido");
        btnCerrar.setMaximumSize(new Dimension(150, 40));
        btnCerrar.setPreferredSize(new Dimension(150, 40));
        btnCerrar.setBackground(new Color(41, 128, 185));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(e -> this.dispose());

        panelPrincipal.add(lblFolio);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 8)));
        panelPrincipal.add(lblEstadoActual);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(panelHistorial);
        panelPrincipal.add(lblDireccion);
        panelPrincipal.add(scrollPane);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(lblTotal);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(btnCerrar);

        this.add(panelPrincipal);
        this.pack();
        this.setLocationRelativeTo(padre);
    }

    private JPanel crearTarjetaPieza(DetallesSolicitudDTO detalle) {
        PiezaDTO pieza = detalle.getPieza();
        JPanel p = new JPanel(new BorderLayout(15, 0));
        p.setBackground(new Color(250, 250, 250));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(235, 235, 235)),
                new EmptyBorder(10, 15, 10, 15)));

        String nombrePieza = (pieza != null) ? pieza.getNombre() : "Pieza Desconocida";
        String marca = (pieza != null) ? pieza.getMarcaPieza() : "Genérica";

        String texto = "<html><b>" + nombrePieza + "</b><br>"
                + "<font color='gray'>" + marca + " | Cant: " + detalle.getCantidad() + "</font></html>";

        JLabel info = new JLabel(texto);

        JLabel subtotal = new JLabel("$" + String.format("%.2f", detalle.getSubtotal()));
        subtotal.setFont(new Font("Segoe UI", Font.BOLD, 15));
        subtotal.setForeground(new Color(44, 62, 80));

        p.add(info, BorderLayout.CENTER);
        p.add(subtotal, BorderLayout.EAST);

        p.setMaximumSize(new Dimension(420, 65));
        return p;
    }
}