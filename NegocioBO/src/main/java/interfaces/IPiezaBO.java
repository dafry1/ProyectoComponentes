package interfaces;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import java.util.List;

/**
 * Interfaz que establece el contrato para los métodos 
 * de PiezaBO
 * 
 * @author Andre
 */
public interface IPiezaBO {
    
    /**
     * Consulta todas las piezas registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<PiezaDTO> consultarPiezas();
    
    /**
     * Consulta las piezas más vendidas al día registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<PiezaDTO> consultarTopDiaPiezas();
    
    /**
     * Consulta las piezas más vendidas a la semana registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<PiezaDTO> consultarTopSemanaPiezas();
    
    /**
     * Consulta las piezas más vendidas al mes registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<PiezaDTO> consultarTopMesPiezas();
    
    /**
     * Consulta las piezas más vendidas en todo el tiempo registradas en
     * el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<PiezaDTO> consultarTopTodoPiezas();
    
    /**
     * Actualiza el stock de una pieza en específica basándose
     * en un DetallesVentaDTO en específico
     * 
     * @param detalle de la pieza a actualizar el stock
     */
    void actualizarStock(DetallesVentaDTO detalle); 
    
    /**
     * Utiliza el método actualizarStock iterando sobre la lista
     * de detalles para actualizar todos las piezas
     * 
     * @param detalles de la venta y el stock se debe actualizar
     */
    void actualizarStockTrasVenta(List<DetallesVentaDTO> detalles);
}