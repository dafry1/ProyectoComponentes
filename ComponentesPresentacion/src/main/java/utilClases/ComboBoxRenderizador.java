package utilClases;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import utilPresentacion.Constantes;

/**
 *
 * @author Andre
 */
public class ComboBoxRenderizador extends DefaultListCellRenderer {
    public ComboBoxRenderizador() {
        setOpaque(false);
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, false);
        
        setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); 

        if (index == -1) {
            setBackground(Constantes.COLOR_BOTONES);
            setForeground(Constantes.COLOR_TEXTO_BOTONES);
            return this;
        }

        if (isSelected) {
            setBackground(Constantes.COLOR_BOTON_HOVER);
            setForeground(Constantes.COLOR_TEXTO_BOTONES);
        } else {
            setBackground(Constantes.COLOR_BOTONES);
            setForeground(Constantes.COLOR_TEXTO_BOTONES);
        }
        return this;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }
}