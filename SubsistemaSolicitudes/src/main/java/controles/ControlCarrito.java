package controles;

import DTOS.DetallesVentaDTO;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementación de un control que recuerda en memoria
 * 
 * @author Andre
 */
public class ControlCarrito {
    private static final System.Logger LOG = System.getLogger(ControlCarrito.class.getName());
    private static String DETALLE_VACIO = "El detalle está vacío";
    
    //Carritos actuales protegidos mediante los métodos
    private List<DetallesVentaDTO> carrito = new ArrayList<>();
    
    /**
     * Regresa una lista inmutable del carrito
     * 
     * @return lista de los detalles
     */
    public List<DetallesVentaDTO> getCarritoSolicitud() {
        return Collections.unmodifiableList(carrito);
    }

    /**
     * Agrega un detalle al carrito de ventas
     * 
     * @param detalle a agregar
     */
    public void agregarCarritoSolicitud(DetallesVentaDTO detalle) {
        if (detalle != null) {
            carrito.add(detalle);
        } else {
            LOG.log(System.Logger.Level.ERROR, () -> ">> " + DETALLE_VACIO);
            throw new NegocioException(DETALLE_VACIO);
        }
    }

    /**
     * Elimina un detalle del carrito de ventas
     * 
     * @param detalle a eliminar
     */
    public void eliminarCarritoSolicitud(DetallesVentaDTO detalle) {
        if (detalle != null) {
            carrito.remove(detalle);
        } else {
            LOG.log(System.Logger.Level.ERROR, DETALLE_VACIO);
            throw new NegocioException(DETALLE_VACIO);
        }
    }

    /**
     * Calcula el total del carrito
     * 
     * @return la suma del subtotal de todos los detalles
     */
    public double totalCarritoSolicitud() {
        return carrito.stream().mapToDouble(DetallesVentaDTO::getSubtotal).sum();
    }

    /**
     * Determina si el carrito está vacío
     * 
     * @return true o false
     */
    public boolean carritoSolicitudVacio() {
        return carrito.isEmpty();
    }

    /** Encapsula la lógica de limpiar el carrito */
    public void limpiarCarritoSolicitud() {
        carrito.clear();
    }
}