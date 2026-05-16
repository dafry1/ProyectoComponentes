package adaptadores;

import DTOS.DetallesSolicitudDTO;
import dominio.DetallesSolicitud;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public final class AdaptadorDetallesSolicitud {
    
    private AdaptadorDetallesSolicitud(){}
    
    public static DetallesSolicitud Entidad(DetallesSolicitudDTO dto) {
        if (dto == null) return null;
        
        DetallesSolicitud entidad = new DetallesSolicitud();
        entidad.setCosto(dto.getCosto());
        entidad.setSubtotal(dto.getSubtotal());
        entidad.setCantidad(dto.getCantidad());
        entidad.setPieza(AdaptadorPieza.Entidad(dto.getPieza()));
        return entidad;
    }

    public static List<DetallesSolicitud> listaEntidad(List<DetallesSolicitudDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(AdaptadorDetallesSolicitud::Entidad).collect(Collectors.toList());
    }

    public static DetallesSolicitudDTO DTO(DetallesSolicitud entidad) {
        if (entidad == null) return null;
        
        DetallesSolicitudDTO dto = new DetallesSolicitudDTO();
        dto.setCosto(entidad.getCosto());
        dto.setSubtotal(entidad.getSubtotal());
        dto.setCantidad(entidad.getCantidad());
        dto.setPieza(AdaptadorPieza.DTO(entidad.getPieza()));
        return dto;
    }

    public static List<DetallesSolicitudDTO> listaDTO(List<DetallesSolicitud> entidades) {
        if (entidades == null) return null;
        return entidades.stream().map(AdaptadorDetallesSolicitud::DTO).collect(Collectors.toList());
    }
}