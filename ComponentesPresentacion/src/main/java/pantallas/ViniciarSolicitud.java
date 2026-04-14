/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author DANIEL
 */
public class ViniciarSolicitud extends JFrame { 

    private JPanel contenedorListaPiezas;

    public ViniciarSolicitud() {
        setTitle("Technoware - Iniciar Solicitud");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 850);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        add(crearNavegacion(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 10, 0, 10);

        g.gridx = 0; g.weightx = 0.25; g.weighty = 1.0;
        contenido.add(crearPanelBusqueda(), g);

        g.gridx = 1; g.weightx = 0.65;
        contenido.add(crearSeccionCentral(), g);

        g.gridx = 2; g.weightx = 0.10;
        contenido.add(crearPanelCarrito(), g);

        add(contenido, BorderLayout.CENTER);
        
        add(crearBarraInferior(), BorderLayout.SOUTH);
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
            nav.add(btn);
        }
        return nav;
    }

    private JPanel crearPanelBusqueda() {
        PanelRedondeado p = new PanelRedondeado(40, new Color(220, 220, 220));
        p.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Buscar", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        gbc.gridy = 0; gbc.weighty = 0.1;
        p.add(titulo, gbc);

        gbc.weighty = 0.0;
        gbc.gridy = 1; p.add(crearCampoBuscador("Nombre de pieza..."), gbc);
        gbc.gridy = 2; p.add(crearCampoBuscador("Categoría..."), gbc);
        gbc.gridy = 3; p.add(crearCampoBuscador("Marca..."), gbc);
        gbc.gridy = 4; p.add(crearCampoBuscador("Precio máximo..."), gbc);

        gbc.gridy = 5; gbc.weighty = 1.0;
        p.add(Box.createGlue(), gbc);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(0, 95, 255));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBuscar.setPreferredSize(new Dimension(0, 45)); 
        btnBuscar.setFocusPainted(false);
        gbc.gridy = 6; gbc.weighty = 0.1; gbc.insets = new Insets(10, 20, 30, 20);
        p.add(btnBuscar, gbc);

        return p;
    }

    private JTextField crearCampoBuscador(String placeholder) {
        JTextField txt = new JTextField(placeholder);
        txt.setPreferredSize(new Dimension(200, 45)); 
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(20, Color.GRAY),
                new EmptyBorder(0, 15, 0, 15)));
        return txt;
    }

    private JPanel crearSeccionCentral() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel titulo = new JLabel("Piezas Disponibles", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        p.add(titulo, BorderLayout.NORTH);

        contenedorListaPiezas = new JPanel();
        contenedorListaPiezas.setLayout(new BoxLayout(contenedorListaPiezas, BoxLayout.Y_AXIS));
        contenedorListaPiezas.setBackground(Color.WHITE);

        agregarPiezaALista("Intel Core i9-13900K", "Intel", "$11,500");
        agregarPiezaALista("NVIDIA GeForce RTX 4070 Ti", "ASUS", "$16,800");
        agregarPiezaALista("Samsung 980 Pro 1TB NVMe", "Samsung", "$1,950");
        agregarPiezaALista("Corsair Vengeance 32GB DDR5", "Corsair", "$2,400");
        agregarPiezaALista("ROG Strix Z790-E Gaming", "ASUS", "$9,200");
        agregarPiezaALista("EVGA SuperNOVA 850G GT", "EVGA", "$2,850");
        agregarPiezaALista("Liquid Cooler LT720 360mm", "DeepCool", "$3,100");
        agregarPiezaALista("Case H9 Elite White", "NZXT", "$4,500");

        JScrollPane scroll = new JScrollPane(contenedorListaPiezas);
        scroll.setBorder(null); 
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(20); // Un poco más rápido para mejor UX
        
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    private void agregarPiezaALista(String nombre, String marca, String precio) {
        contenedorListaPiezas.add(new CardPieza(nombre, marca, precio));
        contenedorListaPiezas.add(Box.createVerticalStrut(15));
    }

    private JPanel crearPanelCarrito() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        JLabel t = new JLabel("Solicitud"); t.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JLabel tot = new JLabel("Total: $ 0"); tot.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        p.add(t); p.add(Box.createVerticalStrut(10)); p.add(tot);
        return p;
    }

    private JPanel crearBarraInferior() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(10, 30, 20, 30));
        
        JButton btnBack = new JButton("←"); 
        btnBack.setFont(new Font("Arial", Font.BOLD, 40));
        btnBack.setContentAreaFilled(false); 
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnNext = new JButton("Continuar");
        btnNext.setBackground(new Color(0, 95, 255)); 
        btnNext.setForeground(Color.WHITE);
        btnNext.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        btnNext.setPreferredSize(new Dimension(200, 50));
        btnNext.setFocusPainted(false);

        p.add(btnBack, BorderLayout.WEST); 
        p.add(btnNext, BorderLayout.EAST);
        return p;
    }

    class CardPieza extends PanelRedondeado {
        public CardPieza(String nombre, String marca, String precio) {
            super(30, new Color(0, 95, 255));
            setMaximumSize(new Dimension(650, 100)); 
            setPreferredSize(new Dimension(600, 100));
            setAlignmentX(Component.CENTER_ALIGNMENT);
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(5, 20, 5, 20));

            JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
            info.setOpaque(false);
            JLabel ico = new JLabel("▣"); 
            ico.setFont(new Font("Arial", Font.BOLD, 40));
            String desc = "<html><font color='white' size='4'><b>"+nombre+"</b></font><br><font color='white'>"+marca+"</font></html>";
            info.add(ico); 
            info.add(new JLabel(desc));

            JPanel precioBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
            precioBtn.setOpaque(false);
            JLabel lblP = new JLabel(precio); 
            lblP.setForeground(new Color(50, 255, 100));
            lblP.setFont(new Font("Segoe UI", Font.BOLD, 22));
            
            JButton btnI = new JButton("Mostrar Info"); 
            btnI.setBackground(new Color(50, 255, 100));
            btnI.setFocusPainted(false);
            
            precioBtn.add(lblP); 
            precioBtn.add(btnI);

            add(info, BorderLayout.WEST);
            add(precioBtn, BorderLayout.EAST);
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

    class RoundedBorder implements javax.swing.border.Border {
        private int radius; private Color color;
        public RoundedBorder(int radius, Color color) { this.radius = radius; this.color = color; }
        public Insets getBorderInsets(Component c) { return new Insets(5, 15, 5, 15); }
        public boolean isBorderOpaque() { return true; }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(color);
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
}