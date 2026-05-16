package adaptadores;

import DTOS.DetallesVentaDTO;
import dominio.DetallesVenta;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public final class AdaptadorDetallesVenta {
    
    private AdaptadorDetallesVenta(){}
    
    public static DetallesVenta Entidad(DetallesVentaDTO dto) {
        if (dto == null) return null;
        
        DetallesVenta entidad = new DetallesVenta();
        entidad.setCosto(dto.getCosto());
        entidad.setSubtotal(dto.getSubtotal());
        entidad.setCantidad(dto.getCantidad());
        entidad.setPieza(AdaptadorPieza.Entidad(dto.getPieza()));
        return entidad;
    }

    public static List<DetallesVenta> listaEntidad(List<DetallesVentaDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(AdaptadorDetallesVenta::Entidad).collect(Collectors.toList());
    }

    public static DetallesVentaDTO DTO(DetallesVenta entidad) {
        if (entidad == null) return null;
        
        DetallesVentaDTO dto = new DetallesVentaDTO();
        dto.setCosto(entidad.getCosto());
        dto.setSubtotal(entidad.getSubtotal());
        dto.setCantidad(entidad.getCantidad());
        dto.setPieza(AdaptadorPieza.DTO(entidad.getPieza()));
        return dto;
    }

    public static List<DetallesVentaDTO> listaDTO(List<DetallesVenta> entidades) {
        if (entidades == null) return null;
        return entidades.stream().map(AdaptadorDetallesVenta::DTO).collect(Collectors.toList());
    }
}