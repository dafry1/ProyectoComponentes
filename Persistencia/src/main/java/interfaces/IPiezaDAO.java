package interfaces;

import dominio.DetallesVenta;
import dominio.Pieza;
import java.util.List;

/**
 * Establece el contrato de métodos relacionados a 
 * una pieza en la BD
 * 
 * @author Andre
 */
public interface IPiezaDAO {
    /**
     * Consulta todas las piezas registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<Pieza> consultarPiezas();
    
    /**
     * Consulta las piezas más vendidas al día registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<Pieza> consultarTopDiaPiezas();
    
    /**
     * Consulta las piezas más vendidas a la semana registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<Pieza> consultarTopSemanaPiezas();
    
    /**
     * Consulta las piezas más vendidas al mes registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<Pieza> consultarTopMesPiezas();
    
    /**
     * Consulta las piezas más vendidas en todo el tiempo registradas en
     * el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<Pieza> consultarTopTodoPiezas();
    
    /**
     * Actualiza el stock de una pieza en específica basándose
     * en un DetallesVentaDTO en específico
     * 
     * @param detalle de la pieza a actualizar el stock
     */
    void actualizarStock(DetallesVenta detalle); 
    
    /**
     * Utiliza el método actualizarStock iterando sobre la lista
     * de detalles para actualizar todos las piezas
     * 
     * @param detalles de la venta y el stock se debe actualizar
     */
    void actualizarStockTrasVenta(List<DetallesVenta> detalles);
}
