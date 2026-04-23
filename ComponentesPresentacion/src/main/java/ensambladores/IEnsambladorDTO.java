package ensambladores;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;

/**
 * Establece el contrato para un ensamblador
 * de DTOs. Este ensamblador evitará ensuciar
 * a presentación eso de set
 * 
 * @author Andre
 */
public interface IEnsambladorDTO {
    
    /**
     * Obtiene la información necesaria para crear el DTO
     * de un detalle de una venta de forma centralizada
     * 
     * @param cantidad de la piza en específico
     * @param pieza que está en el detalle
     * 
     * @return DTO listo
     */
    DetallesVentaDTO ensamblarDetalleVentaDTO(int cantidad, PiezaDTO pieza);
}