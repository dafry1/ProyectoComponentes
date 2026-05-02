package fachadas;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import java.util.List;

/**
 * Contrato para la fachada que unifica los métodos
 * del subsistema
 * 
 * @author Andre
 */
public interface IFachadaSolicitudes {
    
    /**
     * Consulta todas las piezas del sistema
     * 
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarPiezas();
    
    /**
     * Regresa la lista inmutable de ventas
     * 
     * @return 
     */
    List<DetallesVentaDTO> getCarritoSolicitud();

    /**
     * Agrega un detalle al carrito de ventas
     * 
     * @param detalle a agregar
     */
    void agregarCarritoSolicitud(DetallesVentaDTO detalle);

    /**
     * Elimina un detalle del carrito de ventas
     * 
     * @param detalle a eliminar
     */
    void eliminarCarritoSolicitud(DetallesVentaDTO detalle);
    
    /**
     * Calcula el total del carrito
     * 
     * @return la suma del subtotal de todos los detalles
     */
    double totalCarritoSolicitud();
    
    /**
     * Determina si el carrito está vacío
     * 
     * @return true o false
     */
    public boolean carritoSolicitudVacio();

    /** Encapsula la lógica de limpiar el carrito */
    void limpiarCarritoSolicitud();
}