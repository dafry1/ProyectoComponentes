/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pantallas;

import java.awt.Color;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author DANIEL
 */
public class VinicioSesion extends JFrame {

    public VinicioSesion() {
        setTitle("Technoware - Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(new Color(247, 247, 247));
        setLayout(new GridBagLayout()); 

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false); 
    

        RoundedPanel banner = new RoundedPanel(50, new Color(0, 95, 255));
        banner.setPreferredSize(new Dimension(600, 220));
        banner.setMaximumSize(new Dimension(600, 220));
        banner.setLayout(new GridBagLayout());
        
        JLabel lblLogo = new JLabel("Technoware");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 72)); // Fuente moderna
        lblLogo.setForeground(Color.WHITE);
        banner.add(lblLogo);

        JPanel formBox = new JPanel();
        formBox.setLayout(new BoxLayout(formBox, BoxLayout.Y_AXIS));
        formBox.setBackground(Color.WHITE);
        formBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        formBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(40, 50, 40, 50)
        ));
        formBox.setMaximumSize(new Dimension(550, 300));

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        Color labelColor = new Color(80, 80, 80);

        JLabel lblUser = new JLabel("Usuario");
        lblUser.setFont(labelFont);
        lblUser.setForeground(labelColor);
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField txtUser = new JTextField("");
        txtUser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 220, 224)),
                new EmptyBorder(5, 10, 5, 10)));

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(labelFont);
        lblPass.setForeground(labelColor);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPasswordField txtPass = new JPasswordField("");
        txtPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 220, 224)),
                new EmptyBorder(5, 10, 5, 10)));

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(0, 95, 255));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        formBox.add(lblUser);
        formBox.add(Box.createVerticalStrut(5));
        formBox.add(txtUser);
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(lblPass);
        formBox.add(Box.createVerticalStrut(5));
        formBox.add(txtPass);
        formBox.add(Box.createVerticalStrut(30));
        formBox.add(btnIngresar);

        container.add(banner);
        container.add(Box.createVerticalStrut(30));
        container.add(formBox);

        add(container);
    }

    class RoundedPanel extends JPanel {
        private int radius;
        private Color bgColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.radius = radius;
            this.bgColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
        }
    }
}
