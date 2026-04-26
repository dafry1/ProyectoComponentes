package interfaces;

import DTOS.PiezaDTO;
import dominio.Pieza;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interfaz para adaptar una pieza
 * 
 * @author Andre
 */
public interface IAdaptadorPieza {
    
    /**
     * Adapta de DTO a entidad
     * 
     * @param dto a adaptar
     * 
     * @return la entidad adaptada
     */
    Pieza Entidad(PiezaDTO dto);
    
    /**
     * Adapta un conjunto de DTO a entidades
     * 
     * @param dtos a adaptar
     * 
     * @return todo adaptado en una lista de entidades
     */
    List<Pieza> listaEntidad(List<PiezaDTO> dtos);
    
    /**
     * Adapta de entidad a DTO
     * 
     * @param entidad la entidad a adaptar
     * 
     * @return el DTO adaptado
     */
    PiezaDTO DTO(Pieza entidad);
    
    /**
     * Adapta un conjunto de entidades a DTO
     * 
     * @param entidades a adaptar
     * 
     * @return todo adaptado en una lista de DTO
     */
    List<PiezaDTO> listaDTO(List<Pieza> entidades);
}