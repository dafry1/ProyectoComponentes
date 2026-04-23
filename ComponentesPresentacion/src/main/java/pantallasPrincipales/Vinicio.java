package pantallasPrincipales;

import DTOS.PiezaDTO;
import coordinadores.CoordinadorNegocio;
import coordinadores.CoordinadorPresentacion;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import utilEstilos.UtilBuild;
import utilPresentacion.UtilPanel;

/**
 *
 * @author DANIEL
 */
public class Vinicio extends JFrame {
    private ICoordinadorPresentacion coordinadorPresentacion;
    private ICoordinadorNegocio coordinadorNegocio;

    public Vinicio(ICoordinadorPresentacion coordinadorPresentacion, ICoordinadorNegocio coordinadorNegocio) {
        this.coordinadorPresentacion = coordinadorPresentacion;
        this.coordinadorNegocio = coordinadorNegocio;
        initComponents();
    }


    private void initComponents() {
        setTitle("Technoware - Panel de Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        add(UtilBuild.crearNavegacion(this, coordinadorPresentacion), BorderLayout.NORTH);


        JPanel gridContenedor = new JPanel(new GridLayout(2, 2, 25, 25));
        gridContenedor.setBackground(Color.WHITE);
        gridContenedor.setBorder(new EmptyBorder(30, 30, 30, 30));

        gridContenedor.add(new TarjetaCarrusel("Piezas más vendidas (día)"));
        gridContenedor.add(new TarjetaCarrusel("Piezas más vendidas (semana)"));
        gridContenedor.add(new TarjetaCarrusel("Piezas más vendidas (mes)"));
        gridContenedor.add(new TarjetaCarrusel("Piezas más vendidas (todo el tiempo)"));

        add(gridContenedor, BorderLayout.CENTER);
    }
    
    

    class TarjetaCarrusel extends PanelRedondeado {

        private CardLayout navegadorCartas;
        private JPanel panelCartas;
        private JPanel panelPuntos;
        private int indiceActual = 0;
        private int TOTAL_PRODUCTOS;

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

            for (PiezaDTO pieza: coordinadorNegocio.consultarPiezas()) {
                panelCartas.add(generarContenidoProducto(pieza));
            }
            
            /*
            if (titulo.equals("Piezas más vendidas (día)")) {
                for (PiezaDTO pieza : CoordinadorNegocio.getInstance().consultarTopDiaPiezas()) {
                    panelCartas.add(generarContenidoProducto(pieza));
                }
                TOTAL_PRODUCTOS = CoordinadorNegocio.getInstance().consultarTopDiaPiezas().size();
            }
            if (titulo.equals("Piezas más vendidas (semana)")) {
                for (PiezaDTO pieza : CoordinadorNegocio.getInstance().consultarTopSemanaPiezas()) {
                    panelCartas.add(generarContenidoProducto(pieza));
                }
                TOTAL_PRODUCTOS = CoordinadorNegocio.getInstance().consultarTopSemanaPiezas().size();
            }
            if (titulo.equals("Piezas más vendidas (mes)")) {
                for (PiezaDTO pieza : CoordinadorNegocio.getInstance().consultarTopMesPiezas()) {
                    panelCartas.add(generarContenidoProducto(pieza));
                }
                TOTAL_PRODUCTOS = CoordinadorNegocio.getInstance().consultarTopMesPiezas().size();
            }
            if (titulo.equals("Piezas más vendidas (todo el tiempo)")) {
                for (PiezaDTO pieza : CoordinadorNegocio.getInstance().consultarTopTodoPiezas()) {
                    panelCartas.add(generarContenidoProducto(pieza));
                }
                TOTAL_PRODUCTOS = CoordinadorNegocio.getInstance().consultarTopTodoPiezas().size();
            }
             */

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

        private JPanel generarContenidoProducto(PiezaDTO pieza) {
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

            JLabel n = new JLabel(pieza.getNombre());
            n.setFont(new Font("Serif", Font.BOLD, 24));
            n.setForeground(Color.WHITE);

            JLabel t = new JLabel(pieza.getCategoria());
            t.setFont(new Font("Serif", Font.ITALIC, 19));
            t.setForeground(new Color(200, 200, 200));

            JLabel pr = new JLabel(String.valueOf(pieza.getCostoPieza()));
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