package coordinadores;

import DTOS.DTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import ensambladores.IEnsambladorDTO;
import pantallasPrincipales.VinicioSesion;
import pantallasPrincipales.Vinicio;
import pantallasSolicitudes.VhistorialSolicitudes;
import pantallasVentas.ViniciarVenta;
import pantallasVentas.ViniciarSolicitud;
import pantallasVentas.VhistorialVentas;
import java.util.function.Supplier;
import javax.swing.JDialog;
import javax.swing.JFrame;
import observadores.IObservador;
import pantallasVentas.DetalleVenta;
import pantallasVentas.InfoCliente;
import pantallasVentas.InfoDetalle;
import pantallasVentas.InfoPieza;
import pantallasVentas.PantallaResumen;

/**
 * Clase que coordina el flujo entre pantallas, haciendo que
 * desconozcan sobre el resto de frames, solo preocupándose por
 * pasar parámetros en específicio. Este coordinador ya inecta todo
 * lo necesario: otros coordinadores o ensambladores
 */
public class CoordinadorPresentacion implements ICoordinadorPresentacion {

    //Guarda el frame actual para usarlo en varios lugares
    private JFrame ventanaActual;
    
    //Auxiliares listos para inyectar en las pantallas necesarias
    private ICoordinadorNegocio coordinadorNegocio;
    private ICoordinadorEstados coordinadorEstados;
    private IEnsambladorDTO ensambladorDTO;
    
    /**
     * Este constructor se usa en el método main, para inyectar una instancia de 
     * las interfaces e irlas pasando a las pantallas que las ocupen
     * 
     * @param coordinadorNegocio
     * @param coordinadorEstados
     * @param ensambladorDTO 
     */
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
        abrirNuevaVentana(() -> new VhistorialVentas(this, coordinadorNegocio, coordinadorEstados));
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
     * la nueva. Primero abre la ventana a navegar y luego cierra la anterior,
     * evitando ese efecto como de parpadeo
     */
    private void abrirNuevaVentana(Supplier<JFrame> creadorVentana) {
        
        //Guarda el frame anterior
        JFrame ventanaAnterior = ventanaActual;
        
        //Crea la nueva ventana
        ventanaActual = creadorVentana.get();
        
        //La nueva ventana copia propiedades de la anterior
        if (ventanaAnterior != null) {
            ventanaActual.setBounds(ventanaAnterior.getBounds());
            ventanaActual.setExtendedState(ventanaAnterior.getExtendedState());
        } else {
            ventanaActual.setLocationRelativeTo(null);
        }
        
        //Muestra la ventana actual
        ventanaActual.setVisible(true);
        ventanaActual.toFront();

        //Fuerza a sincronicar gráficos
        java.awt.Toolkit.getDefaultToolkit().sync();
        
        //Cierra la ventana anterior
        if (ventanaAnterior != null) {
            ventanaAnterior.dispose();
        }
    }

    @Override
    public void navegar(JFrame actual, Supplier<JFrame> siguiente) {
        // Reutilizamos la lógica de cerrar y abrir
        abrirNuevaVentana(siguiente);
    }

    @Override
    public void abrirDialogo(Supplier<? extends JDialog> formulario) {
        JDialog dialogo = formulario.get();
        dialogo.setVisible(true);
    }

    // Método para arrancar el sistema
    @Override
    public void arrancar() {
        mostrarVentanaVenta();
    }

    
    @Override
    public void abrirResumenVenta() {
        abrirNuevaVentana(() -> new PantallaResumen(this, coordinadorNegocio, coordinadorEstados, ensambladorDTO));
    }

    @Override
    public void abrirInfoPieza(IObservador observador, PiezaDTO pieza) {
        abrirDialogo(() -> new InfoPieza(coordinadorEstados, observador, pieza, ensambladorDTO));
    }
    
    @Override
    public void abrirInfoCliente(IObservador observador, IEnsambladorDTO ensambladorDTO) {
        abrirDialogo(() -> new InfoCliente(this, coordinadorNegocio, coordinadorEstados, observador, ensambladorDTO));
        
    }
    
@Override
    public void abrirDetalleVenta(VentaDTO venta) {
        abrirDialogo(() -> new DetalleVenta(ventanaActual, venta, ensambladorDTO));
    }

    
    @Override
    public void abrirInfoDetalle(IObservador observador, DetallesVentaDTO detalle) {
        abrirDialogo(() -> new InfoDetalle(coordinadorNegocio, coordinadorEstados, observador, detalle));
    }
}