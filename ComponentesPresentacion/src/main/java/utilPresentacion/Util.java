package utilPresentacion;

import utilEstilos.UtilPanel;
import utilEstilos.UtilGeneral;
import utilEstilos.UtilBoton;
import DTOS.DTO;
import coordinadores.ICoordinadorPresentacion;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utilEstilos.UtilBuild;
import utilEstilos.UtilSwing;

/**
 * Fachada de utilerías para que las pantallas solo
 * importen lo que realmente importa, eliminando el
 * ruido de las clases anidadas o el abuso de imports
 * 
 * @author Andre
 */
public class Util {
    private Util(){}
    
    
    /**
     * Fábrica de un label que ya carga consigo mismo la fuente del
     * programa, lo que lo aleja un poco de un simple envoltorio
     * innecesario. Anticipa decisiones más complejas en un
     * futuro
     * 
     * @param texto del label
     * 
     * @return el label estilizado
     */
    public static JLabel crearLabel(String texto) {
        return UtilGeneral.crearLabel(texto);
    }
    
    public static JButton crearBoton(String texto) {
        return UtilBoton.crearBoton(texto);
    }
    
    public static void asignarHoverBoton(JButton boton, Color colorHover) {
        UtilBoton.asignarHoverBoton(boton, colorHover);
    }
    
    public static JButton crearBotonSalir() {
        return UtilBoton.crearBotonSalir();
    }
    
    public static JPanel crearPanel() {
        return UtilPanel.crearPanel();
    }
    
    public static JPanel dibujarTarjeta() {
        return UtilPanel.dibujarTarjeta();
    }
    
    public static JTextField crearCampoTexto() {
        return UtilGeneral.crearCampoTexto();
    }
    
    public static JComboBox crearComboBox(String[] elementos) {
        return UtilGeneral.crearComboBox(elementos);
    }
    
    public static JPanel crearNavegacion(JFrame frame, ICoordinadorPresentacion coordinador) {
        return UtilBuild.crearNavegacion(frame, coordinador);
    }
    
    public static void configurarFrame(String titulo, JFrame frame) {
        UtilSwing.configurarFrame(titulo, frame);
    }
    
    public static void configurarDialogoInicio(JDialog dialogo, String titulo) {
        UtilSwing.configurarDialogoInicio(dialogo, titulo);
    }
    
    public static void configurarDialogoFinal(JDialog dialogo) {
        UtilSwing.configurarDialogoFinal(dialogo);
    }
    
    public static void dialogoAviso(Component pantalla, String mensaje) {
        UtilSwing.dialogoAviso(pantalla, mensaje);
    }
    
    public static void dialogoAlerta(Component pantalla, String mensaje) {
        UtilSwing.dialogoAlerta(pantalla, mensaje);
    }
    
    public static void dialogoConfirmacion(Component componente, String mensaje, Runnable funcion) {
        UtilSwing.dialogoConfirmacion(componente, mensaje, funcion);
    }

    public static void dialogoError(Component pantalla, String mensaje) {
        UtilSwing.dialogoError(pantalla, mensaje);    
    }
    
    public static JButton crearBotonAlmacenador(String texto, DTO dto) {
        return UtilBoton.crearBotonAlmacenador(texto, dto);
    }
    
    public static DTO obtenerDTOBoton(JButton boton) {
        return UtilBoton.obtenerDTOBoton(boton);
    }
    
    public static JButton crearBotonRegresar() {
        return UtilBoton.crearBotonRegresar();
    }
}