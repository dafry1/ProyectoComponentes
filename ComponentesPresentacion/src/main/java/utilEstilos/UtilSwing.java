package utilEstilos;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pantallasVentas.PantallaResumen;

/**
 * Configura todo lo relacionado a configuraciones visuales de las
 * pantallas en sí. Tamaño, posición, diálogos, etcétera
 * 
 * @author Andre
 */
public class UtilSwing {
    private static String ETIQUETA = Constantes.ETIQUETA_NEGOCIO;
    
    
     /**
     * Configura el frame de donde se llame
     *
     * @param titulo de la ventana
     * @param frame a configurar
     */
    public static void configurarFrame(String titulo, JFrame frame) {
        frame.setTitle(ETIQUETA + titulo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Constantes.COLOR_FONDO);
        frame.setLayout(new BorderLayout()); //-> Este atrbituo puede cambiarse según se necesite
    }
    
    
    
    /**
     * Configura el diálogo
     * 
     * @param dialogo 
     */
    public static void configurarDialogoInicio(JDialog dialogo, String titulo) {
        dialogo.setTitle(ETIQUETA + titulo);
        dialogo.setResizable(true);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    
    
    /**
     * En orden de instrucciones:
     * -Ajusta el tamaño del contenido
     * -Centra respecto al menú principal
     * -Lo hace visible
     * 
     * @param dialogo
     */
    public static void configurarDialogoFinal(JDialog dialogo) {
        dialogo.pack();
        dialogo.setLocationRelativeTo(null);
        dialogo.setVisible(true);
    }
    
    
    
    /**
     * Centraliza la estética de un diálogo de aviso
     * 
     * @param pantalla donde va a aparecer
     * @param mensaje a mostrar
     */
    public static void dialogoAviso(Component pantalla, String mensaje) {
        JOptionPane.showMessageDialog(pantalla, mensaje);
    }
    
    
    
    /**
     * Centraliza la estética de un diálogo de alerta
     * 
     * @param pantalla donde va a aparecer
     * @param mensaje a mostrar
     */
    public static void dialogoAlerta(Component pantalla, String mensaje) {
        JOptionPane.showMessageDialog(pantalla, mensaje, "Alerta", JOptionPane.WARNING_MESSAGE);
    }
    
    
    
    /**
     * Centraliza la estética y funcionalidad de un diálogo de confirmación. Mediante
     * un Runnable, que es como una función anónima, permite manejar fácilmente
     * acciones deseadas al confirmar la acción. Esto evita tener que usar condicionales
     * en donde se implemente, leyéndose de forma más fácil y natural
     * 
     * @param componente donde aparecerá (diálogo, frame)
     * @param mensaje
     * @param funcion a ejecutar una vez confirmado
     * 
     */
    public static void dialogoConfirmacion(Component componente, String mensaje, Runnable funcion) {
        //Define las opciones manualmente
        Object[] opciones = {"Confirmar", "Cancelar"};

        //Crea el diálogo de opciones con diferentes parámetros
        //Se hace manualmente para insertar las opciones
        int seleccion = JOptionPane.showOptionDialog(
                componente, 
                mensaje, 
                "Confirmar", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null,      
                opciones, //Los botones son las opciones
                opciones[0] //Resalta por defecto el primero
        );

        //Ejecuta la acción si se confirma
        if (seleccion == JOptionPane.YES_OPTION) {
            funcion.run();
        }
    }

    public static void dialogoError(Component pantalla, String mensaje) {
        JOptionPane.showMessageDialog(pantalla, mensaje, "Error", JOptionPane.ERROR_MESSAGE);    
    }
}