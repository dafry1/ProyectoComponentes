package utilClases;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 *
 * @author Andre
 */
public class CampoTextoRedondeado extends JTextField {
    public CampoTextoRedondeado(int tamaño) {
        super(tamaño);
        //Evita que dibuje el rectánglo completo
        setOpaque(false);
        //Padding
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setFont(new Font("Segoe UI", Font.PLAIN, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        //Suavizado de bordes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Relleno redondeado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        //Dibuja todo y libera memoria
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        //Suavizado de bordes y color
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);

        //Dibuja el borde
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        g2.dispose();
    }
}