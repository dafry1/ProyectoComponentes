package fachadas;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.SolicitudDTO;
import DTOS.VentaDTO;
import controles.ControlCarritoSolicitudes;
import controles.ControlCatalogoBodega;
import controles.ControlSolicitudes;
import excepciones.InfraestructuraException;
import excepciones.NegocioException;
import fabricas.FabricaBO;
import fabricas.IFabricaBO;
import java.time.LocalDate;
import java.util.List;

/**
 * Fachada que encapsula y coordina los métodos de los controles para
 * procesos enteros de negocio. Por ejemplo, para procesar una venta,
 * llama a ControlVentas para registrarla y a ControlCarrito para
 * vaciar los productos seleccionados en la sesión
 * 
 * @author Andre
 */
public class FachadaSolicitudes implements IFachadaSolicitudes {

    //Fábrica de BO que inyecta a los controles
    private final IFabricaBO fabricaBO = FabricaBO.singleton();
    
    //Controles
    private final ControlCatalogoBodega controlCatalogo = new ControlCatalogoBodega();
    private final ControlSolicitudes controlSolicitudes = new ControlSolicitudes(fabricaBO.fabricarPieza(), fabricaBO.fabricarSolicitud());
    private final ControlCarritoSolicitudes controlCarrito = new ControlCarritoSolicitudes();
    
    /** Constructor vacío */
    public FachadaSolicitudes() {}
    
    /**
     * Consulta las piezas del sistema
     * 
     * @return lista de tipo PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        try {
            return controlCatalogo.consultarBodega();
        } catch (InfraestructuraException e) {
            throw new NegocioException(e.getMessage());
        }
    }
    
    @Override
    public PiezaDTO consultarPieza(Long id) {
        try {
            return controlCatalogo.consultarPieza(id);
        } catch (InfraestructuraException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorNombre(String nombre) {
        try {
            return controlCatalogo.filtrarPorNombre(nombre);
        } catch (InfraestructuraException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorCategoria(String categoria) {
        try {
            return controlCatalogo.filtrarPorCategoria(categoria);
        } catch (InfraestructuraException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorMarca(String marca) {
        try {
            return controlCatalogo.filtrarPorMarca(marca);
        } catch (InfraestructuraException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo) {
        try {
            return controlCatalogo.filtrarPorPrecioMax(precioMaximo);
        } catch (InfraestructuraException e) {
            throw new NegocioException(e.getMessage());
        }
    }
    
    /**
     * Regresa el carrito
     * 
     * @return lista de detalles
     */
    @Override
    public List<DetallesVentaDTO> getCarritoSolicitud() {
        return controlCarrito.getCarritoSolicitud();
    }

    /**
     * Agrega un detalle
     * 
     * @param detalle 
     */
    @Override
    public void agregarCarritoSolicitud(DetallesVentaDTO detalle) {
        controlCarrito.agregarCarritoSolicitud(detalle);
    }

    /**
     * Elimina un detalle
     * 
     * @param detalle 
     */
    @Override
    public void eliminarCarritoSolicitud(DetallesVentaDTO detalle) {
        controlCarrito.eliminarCarritoSolicitud(detalle);
    }

    /** Regresa el total
     * @return  */
    @Override
    public double totalCarritoSolicitud() {
        return controlCarrito.totalCarritoSolicitud();
    }

    /**
     * Determina si está vacío el carrito
     * 
     * @return true o false
     */
    @Override
    public boolean carritoSolicitudVacio() {
        return controlCarrito.carritoSolicitudVacio();
    }

    //Vacía el carrito
    @Override
    public void limpiarCarritoSolicitud() {
        controlCarrito.limpiarCarritoSolicitud();
    }

    @Override
    public SolicitudDTO procesarSolicitud(SolicitudDTO solicitud) {
        return controlSolicitudes.procesarSolicitud(solicitud);
    }

    @Override
    public List<SolicitudDTO> consultarHistorialSolicitudes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SolicitudDTO> filtrarSolicitudesPorFecha(LocalDate fecha) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SolicitudDTO> filtrarSolicitudesPorMontoMinimo(double minimo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public List<SolicitudDTO> consultarSolicitudes(){
        return controlSolicitudes.consultarSolicitudes();
    }
}