package utilEstilos;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Configura todo lo relacionado a configuraciones visuales de las
 * pantallas en sí. Tamaño, posición, diálogos, etcétera
 * 
 * @author Andre
 */
public class UtilSwing {
    
     /**
     * Configura el frame de donde se llame
     *
     * @param texto de la ventana más la etiqueta del sistema "TechnoWare"
     * @param frame
     */
    public static void configurarFrame(String texto, JFrame frame) {
        frame.setTitle("Technoware - " + texto);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 850);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Constantes.COLOR_FONDO);
        frame.setLayout(new BorderLayout());
    }
    
    /**
     * En orden de instrucciones:
     * -Acomoda en layout
     * -Bloquea la pantalla de fondo
     * -Si se puede ajustar el tamaño o no
     * 
     * @param dialogo
     * @param esReajustable 
     */
    public static void configurarDialogoInicio(JDialog dialogo, boolean esReajustable) {
        dialogo.setLayout(new BorderLayout());
        dialogo.setModal(true);
        dialogo.setResizable(esReajustable);
    }
    
    /**
     * En orden de instrucciones:
     * -Ajusta el tamaño del contenido
     * -Centra respecto al menú principal
     * @param dialogo
     */
    public static void configurarDialogoFinal(JDialog dialogo) {
        dialogo.pack();
        dialogo.setLocationRelativeTo(null);
        dialogo.setVisible(true);
    }
    
    /** Centraliza la estética de un diálogo de aviso */
    public static void dialogoAviso(Component componente, String mensaje) {
        JOptionPane.showMessageDialog(componente, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
    
    /** Centraliza la estética de un diálogo de confirmación */
    public static int dialogoSiNo(Component componente, String mensaje) {
        return JOptionPane.showConfirmDialog(componente, mensaje, "Confirmar", JOptionPane.YES_NO_OPTION);
    }
}