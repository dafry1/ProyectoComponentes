package utilPresentacion;
import DTOS.DTO;
import utilEstilos.Constantes;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Utilerías para la lógica relacionada a los botones
 * Centraliza el estilo y comportamiento para evitar la redundancia de código
 * @author Andre
 */
public class UtilBoton {

    /**
     * Clase anidada de un botón redondeado
     * Encapsula el dibujo técnico del componente
     */
    private static class BotonPersonalizado extends JButton {
        public BotonPersonalizado(String label) {
            super(label);
            setContentAreaFilled(false); 
            setOpaque(false);
            setBorderPainted(false);
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            //Activa el antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            
            //Redondea el botón
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g2);
            g2.dispose();
        }
    }
    
    
    /**
     * Clase anidada de un botón que puede guardar un DTO en 
     * particular para así pasársela a un Diálogo por ejemplo
     * que despliegue esos detalles. Se vale de la interfaz
     * IDTO para establecer correctamente el contrato, entonces
     * así cada botón lleva consigo una "mochila" que solo es invocada
     */
    public static class BotonAlmacenador extends BotonPersonalizado {
        DTO dto;
        public BotonAlmacenador(String texto, DTO dto) {
            super(texto);
            this.dto = dto;
        }
        public DTO getDTO() {return dto;}
        public void setDTO(DTO dto) {this.dto = dto;}
    }
    
    public static JButton crearBotonAlmacenador(String texto, DTO dto) {
        return new BotonAlmacenador(texto, dto);
    }
    
    /**
     * De un botón almacenador extrae el DTO que
     * contenga para ser usado. Utiliza un casteo
     * forzado, pero es su sacrificio para que las
     * pantallas desconozcan sobre la clase del
     * botón almacenador
     * 
     * @param boton
     * @return 
     */
    public static DTO obtenerDTOBoton(JButton boton) {
        if (boton instanceof UtilBoton.BotonAlmacenador) {
            return ((UtilBoton.BotonAlmacenador) boton).getDTO();
        }
        return null;
    }
    
    /**
     * Fábrica de un botón estilizado
     * 
     * @param texto Contenido del botón
     * @return JButton con el estilo visual de Technoware
     */
    public static JButton crearBoton(String texto) {
        JButton boton = new BotonPersonalizado(texto);
        
        //Colores
        boton.setFont(Constantes.FUENTE);
        boton.setBackground(Constantes.COLOR_BOTONES);
        boton.setForeground(Constantes.COLOR_TEXTO_BOTONES);
        
        //Borde
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        //Hover
        asignarHoverBoton(boton, Constantes.COLOR_BOTON_HOVER);
        return boton;
    }
    
    
    
    /**
     * Fábrica de un botón estilizado que te regresa, se ve como
     * una flecha volando
     * 
     * @return el botón diseñado, no contempla funcionalidad
     */
    public static JButton crearBotonRegresar() {
        JButton boton = new JButton("←"); 
        boton.setFont(new Font("Arial", Font.BOLD, 40));
        boton.setContentAreaFilled(false); 
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    
    
    /**
     * Asigna el comportamiento visual cuando el mouse entra o sale del componente.
     */
    public static void asignarHoverBoton(JButton boton, Color colorHover) {
        Color colorOriginal = boton.getBackground();
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorHover.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorOriginal);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorHover.darker().darker());
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorHover);
            }
        }); 
    }

    /**
     * Botón con lógica de salida del sistema integrada.
     */
    public static JButton crearBotonSalir() {
        JButton boton = crearBoton("Salir");
        boton.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(boton, 
                "¿Desea salir del sistema?", "Salir del sistema", 
                JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        return boton;
    }
}