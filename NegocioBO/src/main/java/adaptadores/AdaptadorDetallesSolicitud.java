/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import DTOS.DetallesSolicitudDTO;
import DTOS.DetallesVentaDTO;
import dominio.DetallesSolicitud;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public class AdaptadorDetallesSolicitud {
    private IAdaptadorPieza adaptadorPieza;
    public AdaptadorDetallesSolicitud(IAdaptadorPieza adaptadorPieza){
        this.adaptadorPieza = adaptadorPieza;
    }
    
    public DetallesSolicitud Entidad(DetallesSolicitudDTO dto) {
        DetallesSolicitud entidad = new DetallesSolicitud();
        entidad.setCosto(dto.getCosto());
        entidad.setSubtotal(dto.getSubtotal());
        entidad.setCantidad(dto.getCantidad());
        entidad.setPieza(adaptadorPieza.Entidad(dto.getPieza()));
        return entidad;
    }

    public List<DetallesSolicitud> listaEntidad(List<DetallesSolicitudDTO> dtos) {
        return dtos.stream().map(this::Entidad).collect(Collectors.toList());
    }

    public DetallesSolicitudDTO DTO(DetallesSolicitud entidad) {
        DetallesSolicitudDTO dto = new DetallesSolicitudDTO();
        dto.setCosto(entidad.getCosto());
        dto.setSubtotal(entidad.getSubtotal());
        dto.setCantidad(entidad.getCantidad());
        dto.setPieza(adaptadorPieza.DTO(entidad.getPieza()));
        return dto;
    }

    public List<DetallesSolicitudDTO> listaDTO(List<DetallesSolicitud> entidades) {
        return entidades.stream().map(this::DTO).collect(Collectors.toList());
    }
}
