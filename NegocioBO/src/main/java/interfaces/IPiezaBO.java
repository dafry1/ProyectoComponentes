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
     * Actualiza el stock de una pieza en específica basándose
     * en un DetallesVentaDTO en específico
     * 
     * @param detalle de la pieza a actualizar el stock
     */
    void actualizarStock(DetallesVentaDTO detalle); 
}