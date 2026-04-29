package interfaces;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import java.util.List;

/**
 * Contrato para la fachada que unifica los métodos
 * del subsistema
 * 
 * @author Andre
 */
public interface IFachadaVentas {
    PiezaDTO consultarPieza(Long id);
    
    /**
     * Determina si esa pieza ya fue agregada al carrito
     * 
     * @param id
     * @return 
     */
    boolean existePiezaCarrito(Long id);
    
    /**
     * Consulta todas las piezas del sistema
     * 
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarPiezas();
    
    /**
     * Consulta las piezas más vendidas en el día del sistema
     *
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarTopDiaPiezas();

    /**
     * Consulta las piezas más vendidas en la semana del sistema
     *
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarTopSemanaPiezas();

    /**
     * Consulta las piezas más vendidas en el mes del sistema
     *
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarTopMesPiezas();

    /**
     * Consulta las piezas más vendidas en todo el tiempo del sistema
     *
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarTopTodoPiezas();
    
    /**
     * Consulta todas las ventas del sistema
     *
     * @return lista de VentaDTO
     */
    List<VentaDTO> consultarVentas();
    
    /**
     * Orquesta la lógica de procesar una venta (registro de
     * la venta, actualización de stock)
     * 
     * @param venta a procesar
     * 
     * @return la venta registrada
     */
    VentaDTO procesarVenta(VentaDTO venta);
    
    /**
     * Regresa la lista inmutable de ventas
     * 
     * @return 
     */
    List<DetallesVentaDTO> getCarritoVenta();

    /**
     * Agrega un detalle al carrito de ventas
     * 
     * @param detalle a agregar
     */
    void agregarCarritoVenta(DetallesVentaDTO detalle);

    /**
     * Elimina un detalle del carrito de ventas
     * 
     * @param detalle a eliminar
     */
    void eliminarCarritoVenta(DetallesVentaDTO detalle);
    
    /**
     * Calcula el total del carrito
     * 
     * @return la suma del subtotal de todos los detalles
     */
    double totalCarritoVenta();
    
    /**
     * Determina si el carrito está vacío
     * 
     * @return true o false
     */
    public boolean carritoVentaVacio();

    /** Encapsula la lógica de limpiar el carrito */
    void limpiarCarritoVenta();
    
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
    int calcularStockAntesVenta(Long id);
}