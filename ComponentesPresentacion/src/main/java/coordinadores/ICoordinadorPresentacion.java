package coordinadores;
import DTOS.DTO;
import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import DTOS.SolicitudDTO;
import DTOS.VentaDTO;
import ensambladores.IEnsambladorDTO;
import java.awt.Frame;
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
    
    
    void abrirInfoPieza(IObservador observador, PiezaDTO pieza);    
    
    void navegar(JFrame actual, Supplier<JFrame> siguiente);
    
    void arrancar();
        
    void abrirInfoDetalle(IObservador observador, DetallesVentaDTO detalle);
        
    void abrirDetalleVenta(VentaDTO venta);
    
    void abrirInfoCliente( IObservador observador, IEnsambladorDTO ensambladorDTO);
    
    void abrirInfoClienteSolicitud(IObservador observador);
    
    void abrirDetalleSolicitud(SolicitudDTO solicitud);
    
    void abrirResumenSolicitud();
    
    void abrirInfoPiezaBodega(IObservador observador, PiezaDTO pieza);
    
    void abrirInfoDetalleSolicitud(IObservador observador, DetallesVentaDTO detalle);
}