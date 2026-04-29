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
    public List<DetallesVentaDTO> getCarritoVenta() {
        return Collections.unmodifiableList(carrito);
    }

    /**
     * Agrega un detalle al carrito de ventas
     * 
     * @param detalle a agregar
     */
    public void agregarCarritoVenta(DetallesVentaDTO detalle) {
        if (detalle == null) {
            LOG.log(System.Logger.Level.ERROR, () -> ">> " + DETALLE_VACIO);
            throw new NegocioException(DETALLE_VACIO);
        }
        carrito.add(detalle);
    }

    /**
     * Elimina un detalle del carrito de ventas
     * 
     * @param detalle a eliminar
     */
    public void eliminarCarritoVenta(DetallesVentaDTO detalle) {
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
    public double totalCarritoVenta() {
        return carrito.stream().mapToDouble(DetallesVentaDTO::getSubtotal).sum();
    }

    /**
     * Determina si el carrito está vacío
     * 
     * @return true o false
     */
    public boolean carritoVentaVacio() {
        return carrito.isEmpty();
    }

    /** Encapsula la lógica de limpiar el carrito */
    public void limpiarCarritoVenta() {
        carrito.clear();
    }
    
    /**
     * Calcula el stock disponible de cierta pieza aún en medio proceso de la
     * venta. Sirve para validaciones rápidas y lógica de experiencia de
     * usuario. Aunque existan 20 piezas en la BD, si ya elegiste 10, y quieres
     * otras 15, no podrás elegirlas
     *
     * @param id de la pieza a calcular stock antes de la venta
     *
     * @return cantidad de stock de dicha pieza
     */
    public int calcularStockAntesVenta(Long id) {
        return carrito.stream()
                .filter(d -> d.getPieza().getId().equals(id))
                .mapToInt(DetallesVentaDTO::getCantidad).sum();
    }  
    
    /**
     * Determina si esa pieza ya fue agregada al carrito
     * 
     * @param id
     * @return 
     */
    public boolean existePiezaCarrito(Long id) {
        if (id == null) {
            LOG.log(System.Logger.Level.ERROR, () -> ">> " + DETALLE_VACIO);
            throw new NegocioException(DETALLE_VACIO);
        }
        for (DetallesVentaDTO detalle: carrito) {
            if (detalle.getPieza().equals(id)) {
                return true;
            }
        }
        return false;
    }
}