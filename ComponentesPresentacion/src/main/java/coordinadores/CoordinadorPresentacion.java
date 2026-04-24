package coordinadores;

import DTOS.DTO;
import DTOS.PiezaDTO;
import ensambladores.IEnsambladorDTO;
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
import pantallasVentas.InfoPieza;
import pantallasVentas.PantallaResumen;

/**
 * Clase que coordina el flujo entre pantallas También guarda el tipo de client
 * que ingresó: la lógica es mostrar u ocultar componentes gráficos
 */
public class CoordinadorPresentacion implements ICoordinadorPresentacion {

    private JFrame ventanaActual;
    private boolean administrador = false;
    
    private ICoordinadorNegocio coordinadorNegocio;
    private ICoordinadorEstados coordinadorEstados;
    
    private IEnsambladorDTO ensambladorDTO;
    
    /** Constructor */
    public CoordinadorPresentacion(ICoordinadorNegocio coordinadorNegocio, ICoordinadorEstados coordinadorEstados, IEnsambladorDTO ensambladorDTO) {
        this.coordinadorNegocio = coordinadorNegocio;
        this.coordinadorEstados = coordinadorEstados;
        this.ensambladorDTO  = ensambladorDTO;
    }

    /**
     * Muestra la pantalla de Iniciar Venta.
     */
    @Override
    public void mostrarVentanaVenta() {
        abrirNuevaVentana(() -> new ViniciarVenta(this, coordinadorNegocio, coordinadorEstados));
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
        abrirNuevaVentana(() -> new Vinicio(this, coordinadorNegocio));
    }

    /**
     * Muestra la pantalla de Inicio (Dashboard principal).
     */
    @Override
    public void mostrarVentanaInicioSesion() {
        abrirNuevaVentana(() -> new VinicioSesion(this, coordinadorNegocio, CoordinadorEstados.singleton()));
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
    private void abrirNuevaVentana(Supplier<JFrame> creadorVentana) {
        
        //Anterior
        JFrame ventanaVieja = ventanaActual;
        if (ventanaActual != null) {
            ventanaActual.dispose();
        }

        ventanaActual = creadorVentana.get();
        ventanaActual.setLocationRelativeTo(null);
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

    
    @Override
    public void abrirResumenVenta() {
        abrirNuevaVentana(() -> new PantallaResumen(this, coordinadorNegocio, coordinadorEstados));
    }

    @Override
    public void abrirInfoPieza(IObservador observador, PiezaDTO pieza) {
        abrirDialogo(() -> new InfoPieza(coordinadorEstados, observador, pieza, ensambladorDTO));
    }

    @Override
    public void abrirInfoDetalle() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
