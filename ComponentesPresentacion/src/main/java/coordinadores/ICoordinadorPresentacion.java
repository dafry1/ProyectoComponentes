package coordinadores;
import DTOS.DTO;
import DTOS.EmpleadoDTO;
import java.util.function.Supplier;
import javax.swing.JDialog;
import javax.swing.JFrame;
import observadores.IObservador;

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
     * Abre un diálogo
     */
    void abrirDialogo(Supplier<? extends JDialog> formulario);
    
    /** Abre la ventana del resumen de una venta (carrito) */
    void abrirResumenVenta();
    
    
    public void abrirInfoPieza(IObservador observador, DTO dto);
    
    void abrirInfoDetalle();
    
    
    void navegar(JFrame actual, Supplier<JFrame> siguiente);
    
    void arrancar();
    
}