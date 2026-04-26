package adaptadores;

import DTOS.PiezaDTO;
import dominio.Pieza;
import interfaces.IAdaptadorPieza;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public class AdaptadorPieza implements IAdaptadorPieza {

    private static IAdaptadorPieza instancia;
    private AdaptadorPieza(){}
    
    /**
     * Singleton del adaptador
     * 
     * @return la única instancia
     */
    public static IAdaptadorPieza singleton() {
        if (instancia == null) {
            instancia = new AdaptadorPieza();
        }
        return instancia;
    }
    
    /**
     * Adapta de DTO a entidad
     * 
     * @param dto a adaptar
     * 
     * @return la entidad adaptada
     */
    @Override
    public Pieza Entidad(PiezaDTO dto) {
        Pieza entidad = new Pieza();
        entidad.setId(dto.getId());
        entidad.setNombre(dto.getNombre());
        entidad.setCategoria(dto.getCategoria());
        entidad.setMarcaPieza(dto.getMarcaPieza());
        entidad.setModeloPieza(dto.getModeloPieza());
        entidad.setCostoPieza(dto.getCostoPieza());
        entidad.setStockPieza(dto.getStockPieza());
        return entidad;
    }
    
    /**
     * Adapta un conjunto de DTO a entidades
     * 
     * @param dtos a adaptar
     * 
     * @return todo adaptado en una lista de entidades
     */
    @Override
    public List<Pieza> listaEntidad(List<PiezaDTO> dtos) {
        return dtos.stream().map(this::Entidad).collect(Collectors.toList());
    }
    
    /**
     * Adapta de entidad a DTO
     * 
     * @param entidad la entidad a adaptar
     * 
     * @return el DTO adaptado
     */
    @Override
    public PiezaDTO DTO(Pieza entidad) {
        PiezaDTO dto = new PiezaDTO();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setCategoria(entidad.getCategoria());
        dto.setMarcaPieza(entidad.getMarcaPieza());
        dto.setModeloPieza(entidad.getModeloPieza());
        dto.setCostoPieza(entidad.getCostoPieza());
        dto.setStockPieza(entidad.getStockPieza());
        return dto;
    }
    
    /**
     * Adapta un conjunto de entidades a DTO
     * 
     * @param entidades a adaptar
     * 
     * @return todo adaptado en una lista de DTO
     */
    @Override
    public List<PiezaDTO> listaDTO(List<Pieza> entidades) {
        return entidades.stream().map(this::DTO).collect(Collectors.toList());
    }
}
