package fachadas;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import controles.ControlCarrito;
import controles.ControlCatalogo;
import fabricas.FabricaBO;
import interfaces.IFabricaBO;
import java.util.List;
import interfaces.IFachadaSolicitudes;

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
    private final ControlCatalogo controlCatalogo = new ControlCatalogo(fabricaBO.fabricarPieza());
    //private final ControlVentas controlVentas = new ControlVentas(fabricaBO);
    private final ControlCarrito controlCarrito = new ControlCarrito();
    
    /** Constructor vacío */
    public FachadaSolicitudes() {}
    
    /**
     * Consulta las piezas del sistema
     * 
     * @return lista de tipo PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        return controlCatalogo.consultarPiezas();
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

    /** Regresa el total */
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
}