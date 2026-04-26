package interfaces;

import DTOS.VentaDTO;
import java.util.List;
import dominio.Venta;

/**
 * Interfaz para adaptar ventas
 * 
 * @author Andre
 */
public interface IAdaptadorVenta {
    
    /**
     * Adapta de DTO a entidad
     * 
     * @param dto a adaptar
     * 
     * @return la entidad adaptada
     */
    Venta Entidad(VentaDTO dto);
    
    /**
     * Adapta un conjunto de DTO a entidades
     * 
     * @param dtos a adaptar
     * 
     * @return todo adaptado en una lista de entidades
     */
    List<Venta> listaEntidad(List<VentaDTO> dtos);
    
    /**
     * Adapta de entidad a DTO
     * 
     * @param entidad la entidad a adaptar
     * 
     * @return el DTO adaptado
     */
    VentaDTO DTO(Venta entidad);
    
    /**
     * Adapta un conjunto de entidades a DTO
     * 
     * @param entidades a adaptar
     * 
     * @return todo adaptado en una lista de DTO
     */
    List<VentaDTO> listaDTO(List<Venta> entidades);
}
