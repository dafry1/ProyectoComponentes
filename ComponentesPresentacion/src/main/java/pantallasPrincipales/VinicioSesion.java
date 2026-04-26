/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallasPrincipales;

import DTOS.EmpleadoDTO;
import coordinadores.CoordinadorEstados;
import coordinadores.CoordinadorNegocio;
import coordinadores.CoordinadorPresentacion;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import java.awt.Color;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilGeneral;

/**
 *
 * @author DANIEL
 */
public class VinicioSesion extends JFrame {

    public VinicioSesion(ICoordinadorPresentacion coordinador, ICoordinadorNegocio coordinadorNegocio, CoordinadorEstados coordinadorEstados) {
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
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 72));
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

        JTextField txtUser = UtilGeneral.crearCampoTexto();

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(labelFont);
        lblPass.setForeground(labelColor);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField txtPass = new JPasswordField("");
        txtPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 220, 224)),
                new EmptyBorder(5, 10, 5, 10)));

        JButton btnIngresar = UtilBoton.crearBoton("Ingresar");
        
        
        /**
         * btnIngresar.setBackground(new Color(0, 95, 255));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 16)); 
         * 
         * 
         */
        
        
        
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnIngresar.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Campos vacíos", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                CoordinadorEstados.singleton().verificarEmpleado(user, pass);
                EmpleadoDTO empleado = coordinadorNegocio.iniciarSesion(user, pass);

                
            if (empleado != null) {
                coordinadorEstados.singleton().establecerSesion(empleado);

                String nombreEmpleado = empleado.getNombres() + " " + empleado.getApellidoPaterno() + " " + empleado.getApellidoMaterno();
                
                JOptionPane.showMessageDialog(this, "Bienvenido " + nombreEmpleado + " (" + empleado.getNombreUsuario() + ")");
                coordinador.mostrarVentanaInicio();
                this.dispose();

                } else {
                    JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
                    txtPass.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    

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
