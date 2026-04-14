/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import coordinadores.Coordinador;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author DANIEL
 */
public class Vinicio extends JFrame {
    private Coordinador coordinador;

    public Vinicio(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
    }


    private void initComponents() {
        setTitle("Technoware - Panel de Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        add(crearNavegacion(), BorderLayout.NORTH);


        JPanel gridContenedor = new JPanel(new GridLayout(2, 2, 25, 25));
        gridContenedor.setBackground(Color.WHITE);
        gridContenedor.setBorder(new EmptyBorder(30, 30, 30, 30));

        gridContenedor.add(new TarjetaCarrusel("Piezas más vendidas (día)"));
        gridContenedor.add(new TarjetaCarrusel("Piezas más vendidas (semana)"));
        gridContenedor.add(new TarjetaCarrusel("Piezas más vendidas (mes)"));
        gridContenedor.add(new TarjetaCarrusel("Piezas más vendidas (todo el tiempo)"));

        add(gridContenedor, BorderLayout.CENTER);
    }

    private JPanel crearNavegacion() {
        JPanel nav = new JPanel(new GridLayout(1, 5));
        nav.setPreferredSize(new Dimension(0, 65));
        nav.setBackground(new Color(0, 95, 255));

        String[] botones = {"Inicio", "Iniciar venta", "Iniciar solicitud", "Historial de ventas", "Historial de solicitudes"};

        for (String texto : botones) {
            JButton btn = new JButton(texto);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(0, 95, 255));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));


            btn.addActionListener(e -> {
                switch (texto) {
                    case "Inicio":

                        coordinador.mostrarVentanaInicio();
                        break;
                    case "Iniciar venta":
                        coordinador.mostrarVentanaVenta();
                        break;
                    case "Iniciar solicitud":
                        coordinador.mostrarVentanaSolicitud();
                        break;
                    case "Historial de ventas":
                        coordinador.mostrarHistorialVentas();
                        break;
                    case "Historial de solicitudes":
                        coordinador.mostrarHistorialSolicitudes();
                        break;
                }
            });

            nav.add(btn);
        }
        return nav;
    }

    class TarjetaCarrusel extends PanelRedondeado {

        private CardLayout navegadorCartas;
        private JPanel panelCartas;
        private JPanel panelPuntos;
        private int indiceActual = 0;
        private final int TOTAL_PRODUCTOS = 3;

        public TarjetaCarrusel(String titulo) {
            super(45, new Color(0, 95, 255));
            setLayout(new BorderLayout());

            JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
            lblTitulo.setForeground(Color.WHITE);
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
            lblTitulo.setBorder(new EmptyBorder(15, 0, 0, 0));
            add(lblTitulo, BorderLayout.NORTH);

            navegadorCartas = new CardLayout();
            panelCartas = new JPanel(navegadorCartas);
            panelCartas.setOpaque(false);

            panelCartas.add(generarContenidoProducto("Intel i5 - 1102", "Procesador", "$2,000"), "p0");
            panelCartas.add(generarContenidoProducto("AMD Ryzen 9", "Procesador", "$5,200"), "p1");
            panelCartas.add(generarContenidoProducto("Nvidia RTX 4070", "GPU", "$12,000"), "p2");

            add(panelCartas, BorderLayout.CENTER);

            add(crearBotonAccion("<", true), BorderLayout.WEST);
            add(crearBotonAccion(">", false), BorderLayout.EAST);

            panelPuntos = crearIndicadores();
            add(panelPuntos, BorderLayout.SOUTH);
            actualizarPuntos();
        }

        private void actualizarPuntos() {
            Component[] puntos = panelPuntos.getComponents();
            for (int i = 0; i < puntos.length; i++) {
                if (i == indiceActual) {
                    puntos[i].setForeground(new Color(50, 255, 100));
                } else {
                    puntos[i].setForeground(Color.LIGHT_GRAY);
                }
            }
        }

        private JPanel generarContenidoProducto(String nombre, String tipo, String precio) {
            JPanel p = new JPanel(new GridBagLayout());
            p.setOpaque(false);
            GridBagConstraints g = new GridBagConstraints();

            JLabel ico = new JLabel("▣");
            ico.setFont(new Font("Monospaced", Font.BOLD, 70));
            ico.setForeground(Color.BLACK);
            g.gridx = 0;
            g.gridy = 0;
            p.add(ico, g);

            PanelRedondeado infoBox = new PanelRedondeado(25, new Color(0, 45, 190));
            infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
            infoBox.setBorder(new EmptyBorder(15, 20, 15, 20));

            JLabel n = new JLabel(nombre);
            n.setFont(new Font("Serif", Font.BOLD, 24));
            n.setForeground(Color.WHITE);

            JLabel t = new JLabel(tipo);
            t.setFont(new Font("Serif", Font.ITALIC, 19));
            t.setForeground(new Color(200, 200, 200));

            JLabel pr = new JLabel(precio);
            pr.setFont(new Font("SansSerif", Font.BOLD, 22));
            pr.setForeground(new Color(0, 255, 100));

            infoBox.add(n);
            infoBox.add(t);
            infoBox.add(pr);

            g.gridx = 1;
            g.insets = new Insets(0, 25, 0, 0);
            p.add(infoBox, g);

            return p;
        }

        private JButton crearBotonAccion(String simbolo, boolean esAtras) {
            JButton b = new JButton(simbolo);
            b.setFont(new Font("Arial", Font.BOLD, 35));
            b.setForeground(Color.WHITE);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));

            b.addActionListener(e -> {
                if (esAtras) {
                    indiceActual = (indiceActual > 0) ? indiceActual - 1 : TOTAL_PRODUCTOS - 1;
                    navegadorCartas.previous(panelCartas);
                } else {
                    indiceActual = (indiceActual < TOTAL_PRODUCTOS - 1) ? indiceActual + 1 : 0;
                    navegadorCartas.next(panelCartas);
                }
                actualizarPuntos();
            });
            return b;
        }

        private JPanel crearIndicadores() {
            JPanel p = new JPanel();
            p.setOpaque(false);
            for (int i = 0; i < TOTAL_PRODUCTOS; i++) {
                JLabel dot = new JLabel("•");
                dot.setFont(new Font("Arial", Font.BOLD, 25));
                p.add(dot);
            }
            p.setBorder(new EmptyBorder(0, 0, 10, 0));
            return p;
        }
    } 
    
    class PanelRedondeado extends JPanel {
        private int r;
        private Color c;

        public PanelRedondeado(int radio, Color color) {
            this.r = radio;
            this.c = color;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), r, r));
        }
    } 
}