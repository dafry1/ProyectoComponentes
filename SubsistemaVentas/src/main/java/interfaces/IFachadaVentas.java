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
    
    /**
     * Consulta todas las piezas del sistema
     * 
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarPiezas();
    
    /**
     * Actualiza el stock de una pieza en específico
     * 
     * @param detalle de la pieza para restarle stock 
     */
    void actualizarStock(DetallesVentaDTO detalle);
    
    /**
     * Crea una venta en el sistma
     * 
     * @param cliente que compró las piezas
     * @param detalles de la venta
     * 
     * @return la ventaDTO
     */
    VentaDTO registrarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles);
    
    /**
     * Utiliza el método actualizarStock iterando sobre la lista
     * de detalles para actualizar todos las piezas
     * 
     * @param detalles de la venta y el stock se debe actualizar
     */
    void actualizarStockTrasVenta(List<DetallesVentaDTO> detalles);
    
    /**
     * Orquesta la lógica de procesar una venta (registro de
     * la venta, actualización de stock)
     * 
     * @param cliente que compró las piezas
     * @param detalles de la venta
     * 
     * @return la venta registrada
     */
    VentaDTO procesarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles);
}