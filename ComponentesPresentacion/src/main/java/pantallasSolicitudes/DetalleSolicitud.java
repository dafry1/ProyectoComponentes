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

        // Panel Principal con BoxLayout vertical
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(new EmptyBorder(25, 30, 25, 30));

        // --- ENCABEZADO: FOLIO ---
        JLabel lblFolio = new JLabel("Folio: " + solicitud.getFolio());
        lblFolio.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblFolio.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Badge de estado (Alineado al centro)
        JLabel lblEstadoActual = new JLabel(" " + solicitud.getEstado().toUpperCase() + " ");
        lblEstadoActual.setOpaque(true);
        lblEstadoActual.setBackground(new Color(41, 128, 185));
        lblEstadoActual.setForeground(Color.WHITE);
        lblEstadoActual.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEstadoActual.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEstadoActual.setMaximumSize(new Dimension(120, 25)); // Tamaño fijo para el badge

        // --- SECCIÓN: HISTORIAL DE ENVÍO ---
        JPanel panelHistorial = new JPanel();
        panelHistorial.setLayout(new BorderLayout());
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
        panelHistorial.setMaximumSize(new Dimension(450, 100)); // Ancho máximo consistente

        // --- SECCIÓN: DIRECCIÓN DE ENTREGA ---
        String dirHtml = (solicitud.getDireccion() != null)
                ? solicitud.getDireccion()
                : "No se proporcionó dirección (Recogida en sucursal)";

        JLabel lblDireccion = new JLabel("<html><div style='width: 350px;'><b>Dirección de Envío:</b><br>🏠 " + dirHtml + "</div></html>");
        lblDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDireccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDireccion.setBorder(new EmptyBorder(15, 0, 15, 0));

        // --- SECCIÓN: LISTA DE PRODUCTOS ---
        JPanel contenedorPiezas = new JPanel();
        contenedorPiezas.setLayout(new BoxLayout(contenedorPiezas, BoxLayout.Y_AXIS));
        contenedorPiezas.setBackground(Color.WHITE);

        for (DetallesSolicitudDTO detalle : solicitud.getDetalles()) {
            contenedorPiezas.add(crearTarjetaPieza(detalle));
            contenedorPiezas.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        JScrollPane scrollPane = new JScrollPane(contenedorPiezas);
        scrollPane.setPreferredSize(new Dimension(450, 200));
        scrollPane.setMaximumSize(new Dimension(450, 200));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Artículos solicitados"));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // --- TOTAL ---
        double supertotal = solicitud.getTotal();
        if (supertotal <= 0 && solicitud.getDetalles() != null) {
            for (DTOS.DetallesSolicitudDTO d : solicitud.getDetalles()) {
                supertotal += d.getSubtotal();
            }
        }

        // Si el total llega en 0, asegúrate de sumarlo en el DTO antes de abrir esta ventana
        JLabel lblTotal = new JLabel("$" + String.format("%.2f", supertotal));
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

        // Ensamblado Final con espaciadores rígidos para mantener la estructura
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
        this.setSize(new Dimension(520, 700)); // Tamaño forzado para evitar recortes
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

        // CORRECCIÓN: Si el subtotal es 0, calculamos precio * cantidad
        double monto = (detalle.getSubtotal() > 0) ? detalle.getSubtotal() : (pieza.getCostoPieza() * detalle.getCantidad());
        JLabel subtotal = new JLabel("$" + monto);
        subtotal.setFont(new Font("Segoe UI", Font.BOLD, 15));
        subtotal.setForeground(new Color(44, 62, 80));

        p.add(info, BorderLayout.CENTER);
        p.add(subtotal, BorderLayout.EAST);

        p.setMaximumSize(new Dimension(420, 65));
        return p;
    }
}
