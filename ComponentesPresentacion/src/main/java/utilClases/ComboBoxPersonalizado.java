package utilClases;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import utilPresentacion.Constantes;

/**
 *
 * @author Andre
 */
public class ComboBoxPersonalizado extends BasicComboBoxUI {
    
    @Override
    protected JButton createArrowButton() {
        JButton botonFlecha = new JButton() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(comboBox.getForeground());
                int[] xPoints = {getWidth() / 2 - 5, getWidth() / 2 + 5, getWidth() / 2};
                int[] yPoints = {getHeight() / 2 - 2, getHeight() / 2 - 2, getHeight() / 2 + 4};
                g2.fillPolygon(xPoints, yPoints, 3);
                g2.dispose();
            }
        };
        botonFlecha.setContentAreaFilled(false);
        botonFlecha.setBorderPainted(false);
        botonFlecha.setOpaque(false);
        return botonFlecha;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(c.getBackground());
        g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
        g2.dispose();
        
        hasFocus = false;
        
        int width = c.getWidth();
        int height = c.getHeight();
        Insets insets = c.getInsets();
        int arrowWidth = 0;
        
        if (arrowButton != null) {
            arrowWidth = arrowButton.getWidth();
        }
        
        Rectangle bounds = new Rectangle(
            insets.left, 
            insets.top, 
            width - (insets.left + insets.right + arrowWidth), 
            height - (insets.top + insets.bottom)
        );
        
        paintCurrentValue(g, bounds, false);
    }

    @Override
    protected ComboPopup createPopup() {
        BasicComboPopup popup = new BasicComboPopup(comboBox) {
            @Override
            protected JScrollPane createScroller() {
                JScrollPane scroller = super.createScroller();
                scroller.setBorder(BorderFactory.createLineBorder(comboBox.getBackground(), 1));
                scroller.getVerticalScrollBar().setUnitIncrement(10);
                return scroller;
            }

            @Override
            protected void configurePopup() {
                super.configurePopup();
                setBorder(BorderFactory.createEmptyBorder(2, 0, 5, 0));
                setBackground(Constantes.COLOR_BOTONES);
            }
        };

        Component m = popup.getTopLevelAncestor();
        if (m instanceof JWindow) {
            JWindow ventanaFlotante = (JWindow) m;
            ventanaFlotante.setBackground(new Color(0, 0, 0, 0)); 
        }

        return popup;
    }
}