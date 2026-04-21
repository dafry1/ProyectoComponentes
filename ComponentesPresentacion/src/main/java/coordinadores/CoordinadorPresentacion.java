package coordinadores;

import DTOS.EmpleadoDTO;
import bo.EmpleadoBO;
import pantallasPrincipales.VinicioSesion;
import pantallasPrincipales.Vinicio;
import pantallasVentas.VhistorialSolicitudes;
import pantallasVentas.ViniciarVenta;
import pantallasVentas.ViniciarSolicitud;
import pantallasVentas.VhistorialVentas;
import java.util.function.Supplier;
import javax.swing.JDialog;
import javax.swing.JFrame;
import observadores.IObservador;

/**
 * Clase que coordina el flujo entre pantallas También guarda el tipo de client
 * que ingresó: la lógica es mostrar u ocultar componentes gráficos
 */
public class CoordinadorPresentacion implements ICoordinadorPresentacion {

    private JFrame ventanaActual;
    private boolean administrador = false;
    public CoordinadorPresentacion() {
    }

    /**
     * Muestra la pantalla de Iniciar Venta.
     */
    @Override
    public void mostrarVentanaVenta() {
        abrirNuevaVentana(() -> new ViniciarVenta(this));
    }

    /**
     * Muestra la pantalla de Iniciar Solicitud.
     */
    @Override
    public void mostrarVentanaSolicitud() {
        abrirNuevaVentana(() -> new ViniciarSolicitud(this));
    }

    /**
     * Muestra la pantalla de Inicio (Dashboard principal).
     */
    @Override
    public void mostrarVentanaInicio() {
        abrirNuevaVentana(() -> new Vinicio(this));
    }

    /**
     * Muestra la pantalla de Inicio (Dashboard principal).
     */
    @Override
    public void mostrarVentanaInicioSesion() {
        abrirNuevaVentana(() -> new VinicioSesion(this, CoordinadorNegocio.getInstance(),CoordinadorEstados.singleton()));
    }

    /**
     * Muestra la pantalla de Historial de ventas.
     */
    @Override
    public void mostrarHistorialVentas() {
        abrirNuevaVentana(() -> new VhistorialVentas(this));
    }

    /**
     * Muestra la pantalla de Historial de solicitudes.
     */
    @Override
    public void mostrarHistorialSolicitudes() {
        abrirNuevaVentana(() -> new VhistorialSolicitudes(this));
    }

    /**
     * Método privado para centralizar la lógica de cerrar la anterior y abrir
     * la nueva.
     */
    @Override
    public void abrirNuevaVentana(Supplier<JFrame> creadorVentana) {
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
        dialogo.pack();
        dialogo.setLocationRelativeTo(ventanaActual);
        dialogo.setVisible(true);
    }

    // Método para arrancar el sistema
    @Override
    public void arrancar() {
        mostrarVentanaVenta();
    }


}
