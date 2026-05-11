package controles;

import DTOS.DetallesVentaDTO;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación de un control que recuerda en memoria
 * 
 * @author Andre
 */
public class ControlCarritoSolicitudes {
    private static final System.Logger LOG = System.getLogger(ControlCarritoSolicitudes.class.getName());
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
    
    /**
     * Agrupa detalles de la misma pieza para evitar duplicación
     * innecesaria, ayudándose de un mapa temporal del juego
     * <String, DetallesVentaDTO>, o aplicado a negocio, <idPieza, detalle>
     * y así agrupar correctamente qué id de pieza tiene un detalle. Va
     * agregando a ese mapa o actualizando cantidad recorriendo el
     * carrito. Al final limpia el carrito y actualiza con los datos
     * frescos del mapa auxiliar
     */
    public void agruparDetalles() {
        
        //Mapa de juego idPieza, detalle para saber qué piezas tienen qué detalles
        Map<String, DetallesVentaDTO> mapaDetalles = new HashMap<>();
        
        //Por cada detalle del carrito...
        for (DetallesVentaDTO detalle: carrito) {
            String idPieza = detalle.getPieza().getId();
            
            //Si no existe un detalle con esa pieza, solo lo agrega
            if (!mapaDetalles.containsKey(idPieza)) {
                mapaDetalles.put(idPieza, detalle);
            } 
            
            //Si ya existe, busca el detalle en el mapa con ese ID y lo suma al detalle de la iteración
            else {
                DetallesVentaDTO detalleExistente = mapaDetalles.get(idPieza);
                int cantidadActualizada = detalleExistente.getCantidad() + detalle.getCantidad();
                detalleExistente.setCantidad(cantidadActualizada);
            }
        }
        
        //Limpia el carrito y le manda los valores del mapa, o sea, sin el juego de llave
        carrito.clear();
        carrito.addAll(mapaDetalles.values());
    }
}