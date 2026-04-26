package adaptadores;

import DTOS.DetallesVentaDTO;
import dominio.DetallesVenta;
import interfaces.IAdaptadorDetallesVenta;
import interfaces.IAdaptadorPieza;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public class AdaptadorDetallesVenta implements IAdaptadorDetallesVenta {
    private IAdaptadorPieza adaptadorPieza;
    public AdaptadorDetallesVenta(IAdaptadorPieza adaptadorPieza){
        this.adaptadorPieza = adaptadorPieza;
    }
    
    @Override
    public DetallesVenta Entidad(DetallesVentaDTO dto) {
        DetallesVenta entidad = new DetallesVenta();
        entidad.setCosto(dto.getCosto());
        entidad.setSubtotal(dto.getSubtotal());
        entidad.setCantidad(dto.getCantidad());
        entidad.setPieza(adaptadorPieza.Entidad(dto.getPieza()));
        return entidad;
    }

    @Override
    public List<DetallesVenta> listaEntidad(List<DetallesVentaDTO> dtos) {
        return dtos.stream().map(this::Entidad).collect(Collectors.toList());
    }

    @Override
    public DetallesVentaDTO DTO(DetallesVenta entidad) {
        DetallesVentaDTO dto = new DetallesVentaDTO();
        dto.setCosto(entidad.getCosto());
        dto.setSubtotal(entidad.getSubtotal());
        dto.setCantidad(entidad.getCantidad());
        dto.setPieza(adaptadorPieza.DTO(entidad.getPieza()));
        return dto;
    }

    @Override
    public List<DetallesVentaDTO> listaDTO(List<DetallesVenta> entidades) {
        return entidades.stream().map(this::DTO).collect(Collectors.toList());
    }
}
