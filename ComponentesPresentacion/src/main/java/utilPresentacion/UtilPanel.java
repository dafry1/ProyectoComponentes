package utilPresentacion;
import coordinadores.CoordinadorPresentacion;
import coordinadores.ICoordinadorPresentacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import utilEstilos.Constantes;

/**
 * Clase que centraliza la lógica para crear el panel de navegación superior
 * En lugar de construirse cada vez, solo se llama a esta clase
 * @author Andre
 */
public class UtilPanel {
    
    public static class PanelRedondeado extends JPanel {
        private int r; 
        private Color c;
        public PanelRedondeado(int radio, Color color) { 
            this.r = radio; 
            this.c = color; 
            setOpaque(false); 
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), r, r));
        }
    }
    public static JPanel crearPanel() {
        return new PanelRedondeado(40, new Color(220, 220, 220));
    }
    
    
    
    /**
     * Crea un panel diseñado para plasmar la información
     * de un objeto en específicio, dependiendo del DTO
     * que albergue
     * 
     * @return la tarjeta vacía para configurarla a gusto
     */
    public static JPanel dibujarTarjeta() {
        JPanel panel = crearPanel();
        panel.setBackground(Constantes.COLOR_BOTONES);
        panel.setOpaque(true);
        panel.setMaximumSize(new Dimension(650, 100)); 
        panel.setPreferredSize(new Dimension(600, 100));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 20, 5, 20));
        return panel;
    }
    
    
    
    /**
     * Crea el panel superior de navegación con lógica incluida
     * 
     * @param frame donde es desplegado
     * @param coordinador que se encarga de navegar
     * 
     * @return el panel superior totalmente navegable
     */
    public static JPanel crearNavegacion(JFrame frame, ICoordinadorPresentacion coordinador) {
        
        //Creación y configuración
        JPanel nav = new JPanel(new GridLayout(1, 5));
        nav.setPreferredSize(new Dimension(0, 65));
        nav.setBackground(new Color(0, 95, 255));
        
        //Arreglo con los apartados
        String[] nombres = {"Inicio", "Iniciar venta", "Iniciar solicitud", "Historial de ventas", "Historial de solicitudes"};
        
        //Crea un botón por cada apartado anterior y lo configura
        for (String texto: nombres) {
            JButton btn = new JButton(texto);
            btn.setForeground(Constantes.COLOR_TEXTO_BOTONES);
            btn.setBackground(Constantes.COLOR_BOTONES);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            //Dependiendo de qué campo sea presionado, creará una respectiva pantalla
            btn.addActionListener(e -> {
                switch (texto) {
                    case "Inicio" -> coordinador.mostrarVentanaInicio();
                    case "Iniciar venta" -> coordinador.mostrarVentanaVenta();
                    case "Historial de ventas" -> {}
                    case "Historial de solicitudes" -> coordinador.mostrarHistorialSolicitudes();
                    default -> JOptionPane.showMessageDialog(frame, "Módulo en desarrollo");
                }
            });
            nav.add(btn);
        }
        return nav;
    }
}