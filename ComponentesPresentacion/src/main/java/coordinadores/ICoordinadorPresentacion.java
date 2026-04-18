package coordinadores;
import java.util.function.Supplier;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Interfaz de un coordiandor que navega entre frames
 * 
 * @author Andre
 */
public interface ICoordinadorPresentacion {
    
    /**
     * Muestra la pantalla de Iniciar Venta.
     */
    void mostrarVentanaVenta();

    /**
     * Muestra la pantalla de Iniciar Solicitud.
     */
    void mostrarVentanaSolicitud();
    /**
     * Muestra la pantalla de Inicio.
     */
    void mostrarVentanaInicio();
    
    /**
     * Muestra la pantalla de Inicio.
     */
    void mostrarVentanaInicioSesion();
    
    /**
     * Muestra la pantalla de Historial de ventas.
     */
    void mostrarHistorialVentas();
    
    /**
     * Muestra la pantalla de Historial de solicitudes.
     */
    void mostrarHistorialSolicitudes();

    /**
     * Método privado para centralizar la lógica de cerrar la anterior y abrir
     * la nueva.
     */
    void abrirNuevaVentana(Supplier<JFrame> creadorVentana);
    
    /**
     * Abre un diálogo
     */
    void abrirDialogo(Supplier<? extends JDialog> formulario);
    

    void navegar(JFrame actual, Supplier<JFrame> siguiente);
    
    void arrancar();
}