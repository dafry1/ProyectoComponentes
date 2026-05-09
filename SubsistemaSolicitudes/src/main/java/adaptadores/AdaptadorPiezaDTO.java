package adaptadores;

import DTO.PiezaInfraestructuraDTO;
import DTOS.PiezaDTO;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Transforma DTOs de infraestructura en DTOs
 * de negocio. En este caso, el DTO de Pieza
 * 
 * @author Andre
 */
public class AdaptadorPiezaDTO {
    
    /**
     * Transforma una pieza individual
     * 
     * @param pieza de infraestructura a adaptar
     * 
     * @return pieza de negocio
     */
    private static PiezaDTO pieza(PiezaInfraestructuraDTO pieza) {
        if (pieza == null) {
            return null;
        }
        PiezaDTO piezaDTO = new PiezaDTO();
        piezaDTO.setNombre(pieza.getNombre());
        piezaDTO.setCategoria(pieza.getCategoria());
        piezaDTO.setMarcaPieza(pieza.getMarcaPieza());
        piezaDTO.setModeloPieza(pieza.getModeloPieza());
        piezaDTO.setCostoPieza(pieza.getCostoPieza());
        piezaDTO.setStockPieza(pieza.getStockPieza());
        return piezaDTO;
    }
    
    /**
     * Transforma toda una colección de piezas
     * 
     * @param piezas de infraestructura
     * 
     * @return piezas de negocio
     */
    public static List<PiezaDTO> piezas(List<PiezaInfraestructuraDTO> piezas) {
        if (piezas == null) {
            return Collections.emptyList();
        }
        return piezas.stream().map(AdaptadorPiezaDTO::pieza).collect(Collectors.toList());
    }   
}