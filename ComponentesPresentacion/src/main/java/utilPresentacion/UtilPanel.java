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
    
    //Panel con estilo personalizado
    private static class PanelRedondeado extends JPanel {
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
    
    
    
    /**
     * Dibuja un panel con estilo propio
     * 
     * @return un panel
     */
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
}