/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adaptadores;

import DTOS.DetallesSolicitudDTO;
import java.util.List;
import dominio.DetallesSolicitud;

/**
 *
 * @author DANIEL
 */
public interface IAdaptadorDetallesSolicitud {
    DetallesSolicitud Entidad(DetallesSolicitudDTO dto);
    
    List<DetallesSolicitud> listaEntidad(List<DetallesSolicitudDTO> dtos);
    
    DetallesSolicitudDTO DTO(DetallesSolicitud entidad);
    
    List<DetallesSolicitudDTO> listaDTO(List<DetallesSolicitud> entidades);
}
