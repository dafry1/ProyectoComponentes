package pantallasVentas;

import coordinadores.CoordinadorPresentacion;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla que muestra el historial de ventas realizadas.
 * 
 * @author Aaron
 */
public class VhistorialVentas extends JFrame {

    private JPanel contenedorListaVentas;
    private CoordinadorPresentacion coordinador;

    public VhistorialVentas(CoordinadorPresentacion coordinador) {
        this.coordinador = coordinador;
        setTitle("Technoware - Historial de Ventas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 850);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Barra de navegación superior (Azul)
        add(crearNavegacion(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 10, 0, 10);

        // Lado Izquierdo: Panel de Búsqueda (Gris redondeado)
        g.gridx = 0; g.weightx = 0.30; g.weighty = 1.0;
        contenido.add(crearPanelBusqueda(), g);

        // Lado Derecho: Lista de Ventas (Panel Azul)
        g.gridx = 1; g.weightx = 0.70;
        contenido.add(crearSeccionVentas(), g);

        add(contenido, BorderLayout.CENTER);
        
        // Flecha de regresar abajo a la izquierda
        add(crearBarraInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearNavegacion() {
        JPanel nav = new JPanel(new GridLayout(1, 5));
        nav.setPreferredSize(new Dimension(0, 65));
        nav.setBackground(new Color(0, 95, 255));
        
        String[] nombres = {"Inicio", "Iniciar venta", "Iniciar solicitud", "Historial de ventas", "Historial de solicitudes"};
        
        for (String texto : nombres) {
            JButton btn = new JButton(texto);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(0, 95, 255));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> {
                switch (texto) {
                    case "Inicio" -> coordinador.mostrarVentanaInicio();
                    case "Iniciar venta" -> coordinador.mostrarVentanaVenta();
                    case "Historial de ventas" -> {}
                    case "Historial de solicitudes" -> coordinador.mostrarHistorialSolicitudes();
                    default -> JOptionPane.showMessageDialog(this, "Módulo en desarrollo");
                }
            });
            nav.add(btn);
        }
        return nav;
    }

    private JPanel crearSeccionVentas() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel titulo = new JLabel("Ventas realizadas", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        p.add(titulo, BorderLayout.NORTH);

        // Este es el "Fondo Azul" de tu imagen
        PanelRedondeado panelAzul = new PanelRedondeado(40, new Color(0, 95, 255));
        panelAzul.setLayout(new BorderLayout());
        panelAzul.setBorder(new EmptyBorder(20, 20, 20, 20));

        contenedorListaVentas = new JPanel();
        contenedorListaVentas.setLayout(new BoxLayout(contenedorListaVentas, BoxLayout.Y_AXIS));
        contenedorListaVentas.setOpaque(false);

        // Ejemplo de carga de datos
        for(int i=0; i<5; i++) {
            agregarVentaALista("Aaron Burciaga", "6442288812", i + " piezas");
        }

        JScrollPane scroll = new JScrollPane(contenedorListaVentas);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        panelAzul.add(scroll, BorderLayout.CENTER);

        p.add(panelAzul, BorderLayout.CENTER);
        return p;
    }

    private void agregarVentaALista(String cliente, String tel, String cantidad) {
        contenedorListaVentas.add(new CardVenta(cliente, tel, cantidad));
        contenedorListaVentas.add(Box.createVerticalStrut(15));
    }

    private JPanel crearPanelBusqueda() {
        PanelRedondeado p = new PanelRedondeado(40, new Color(225, 225, 225));
        p.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 25, 10, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Buscar", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridy = 0; p.add(titulo, gbc);

        gbc.gridy = 1; p.add(new JTextField("Folio..."), gbc);
        gbc.gridy = 2; p.add(new JTextField("Cliente..."), gbc);
        gbc.gridy = 3; p.add(new JTextField("Fecha..."), gbc);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(0, 95, 255));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setPreferredSize(new Dimension(0, 45));
        gbc.gridy = 4; gbc.insets = new Insets(30, 25, 10, 25);
        p.add(btnBuscar, gbc);

        return p;
    }

    private JPanel crearBarraInferior() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 10));
        p.setOpaque(false);
        JButton btnBack = new JButton("←");
        btnBack.setFont(new Font("Arial", Font.BOLD, 35));
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> coordinador.mostrarVentanaInicio());
        p.add(btnBack);
        return p;
    }

    // --- CLASES INTERNAS DE DISEÑO ---

    class CardVenta extends JPanel {
        public CardVenta(String cliente, String tel, String piezas) {
            setOpaque(false);
            setLayout(new BorderLayout());
            setMaximumSize(new Dimension(800, 800)); // Para que no se estire de más
            setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE)); // Línea divisoria blanca

            // Info izquierda: Icono + Datos
            JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
            info.setOpaque(false);
            JLabel icono = new JLabel("👤"); icono.setFont(new Font("Arial", Font.PLAIN, 40));
            icono.setForeground(Color.BLACK);
            
            String html = "<html><font color='white' size='4'><b>"+cliente+"</b></font><br>"
                        + "<font color='white'>"+tel+"</font></html>";
            info.add(icono);
            info.add(new JLabel(html));

            // Info centro: Piezas
            JLabel lblPiezas = new JLabel(piezas);
            lblPiezas.setForeground(Color.WHITE);
            lblPiezas.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            // Botón derecho: Ver
            JButton btnVer = new JButton("Ver");
            btnVer.setBackground(new Color(80, 255, 80)); // Verde brillante como tu imagen
            btnVer.setFont(new Font("Segoe UI", Font.BOLD, 14));

            add(info, BorderLayout.WEST);
            add(lblPiezas, BorderLayout.CENTER);
            add(btnVer, BorderLayout.EAST);
        }
    }

    class PanelRedondeado extends JPanel {
        private int r; private Color c;
        public PanelRedondeado(int radio, Color color) { this.r = radio; this.c = color; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), r, r));
        }
    }
}