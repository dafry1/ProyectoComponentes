package coordinadores;

import java.util.function.Supplier;
import javax.swing.JDialog;
import javax.swing.JFrame;
import observadores.IObservador;
import pantallas.*;

/**
 * Clase que coordina el flujo entre pantallas También guarda el tipo de client
 * que ingresó: la lógica es mostrar u ocultar componentes gráficos
 */
public class Coordinador implements IObservador {

    private JFrame ventanaActual;
    private boolean administrador = false;

    public Coordinador() {
    }

    /**
     * Muestra la pantalla de Iniciar Venta.
     */
    public void mostrarVentanaVenta() {
        abrirNuevaVentana(() -> new ViniciarVenta(this));
    }

    /**
     * Muestra la pantalla de Iniciar Solicitud.
     */
    public void mostrarVentanaSolicitud() {
        abrirNuevaVentana(() -> new ViniciarSolicitud(this));
    }

    /**
     * Muestra la pantalla de Inicio (Dashboard principal).
     */
    public void mostrarVentanaInicio() {
        abrirNuevaVentana(() -> new Vinicio(this));
    }
    
    /**
     * Muestra la pantalla de Inicio (Dashboard principal).
     */
    public void mostrarVentanaInicioSesion() {
        abrirNuevaVentana(() -> new VinicioSesion(this));
    }
    
    /**
     * Muestra la pantalla de Historial de ventas.
     */
    public void mostrarHistorialVentas() {
        abrirNuevaVentana(() -> new VhistorialVentas(this));
    }
    
    /**
     * Muestra la pantalla de Historial de solicitudes.
     */
    public void mostrarHistorialSolicitudes() {
        abrirNuevaVentana(() -> new VhistorialSolicitudes(this));
    }

    /**
     * Método privado para centralizar la lógica de cerrar la anterior y abrir
     * la nueva.
     */
    private void abrirNuevaVentana(Supplier<JFrame> creadorVentana) {
        if (ventanaActual != null) {
            ventanaActual.dispose();
        }

        ventanaActual = creadorVentana.get();
        ventanaActual.setLocationRelativeTo(null); // Centrar
        ventanaActual.setVisible(true);
        ventanaActual.toFront();
    }

    @Override
    public void navegar(JFrame actual, Supplier<JFrame> siguiente) {
        // Reutilizamos la lógica de cerrar y abrir
        abrirNuevaVentana(siguiente);
    }

    @Override
    public void abrirDialogo(Supplier<? extends JDialog> formulario) {
        // Los diálogos no cierran la ventana principal, se muestran encima
        JDialog dialogo = formulario.get();
        dialogo.setLocationRelativeTo(ventanaActual);
        dialogo.setVisible(true);
    }

    @Override
    public void establecerAdministrador() {
        this.administrador = true;
    }

    @Override
    public boolean esAdministrador() {
        return this.administrador;
    }

    @Override
    public void notificarCambio() {
        // Aquí puedes refrescar tablas o datos si es necesario
        System.out.println("Cambio notificado en el sistema.");
    }

    // Método para arrancar el sistema
    public void arrancar() {
        mostrarVentanaVenta();
    }
}
