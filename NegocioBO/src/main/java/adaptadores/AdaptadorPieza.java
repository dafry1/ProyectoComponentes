package adaptadores;

import DTOS.PiezaDTO;
import dominio.Pieza;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public final class AdaptadorPieza {

    private AdaptadorPieza(){}
    
    public static Pieza Entidad(PiezaDTO dto) {
        if (dto == null) return null;
        
        Pieza entidad = new Pieza();
        
        //CU base
        entidad.setId(dto.getId());
        entidad.setNombre(dto.getNombre());
        entidad.setCategoria(dto.getCategoria());
        entidad.setMarcaPieza(dto.getMarcaPieza());
        entidad.setModeloPieza(dto.getModeloPieza());
        entidad.setCostoPieza(dto.getCostoPieza());
        entidad.setStockPieza(dto.getStockPieza());
        
        //CU factura
        entidad.setAntesImpuestos(dto.getAntesImpuestos());
        entidad.setIva(dto.getIva());
        
        return entidad;
    }
    
    public static List<Pieza> listaEntidad(List<PiezaDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(AdaptadorPieza::Entidad).collect(Collectors.toList());
    }
    
    public static PiezaDTO DTO(Pieza entidad) {
        if (entidad == null) return null;
        
        PiezaDTO dto = new PiezaDTO();
        
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setCategoria(entidad.getCategoria());
        dto.setMarcaPieza(entidad.getMarcaPieza());
        dto.setModeloPieza(entidad.getModeloPieza());
        dto.setCostoPieza(entidad.getCostoPieza());
        dto.setStockPieza(entidad.getStockPieza());
        
        //CU factura
        dto.setAntesImpuestos(dto.getAntesImpuestos());
        dto.setIva(dto.getIva());
        
        return dto;
    }
    
    public static List<PiezaDTO> listaDTO(List<Pieza> entidades) {
        if (entidades == null) return null;
        return entidades.stream().map(AdaptadorPieza::DTO).collect(Collectors.toList());
    }
}