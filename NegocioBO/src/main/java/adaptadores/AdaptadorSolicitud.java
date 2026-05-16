package adaptadores;

import DTOS.SolicitudDTO;
import dominio.Solicitud;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public final class AdaptadorSolicitud {

    private AdaptadorSolicitud(){}
    
    public static Solicitud Entidad(SolicitudDTO dto) {
        if (dto == null) return null;
        
        Solicitud entidad = new Solicitud();
        entidad.setCliente(AdaptadorCliente.Entidad(dto.getCliente()));
        entidad.setEmpleado(AdaptadorEmpleado.Entidad(dto.getEmpleado()));
        entidad.setTotal(dto.getTotal());
        entidad.setEstado(dto.getEstado());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setFolio(dto.getFolio());
        entidad.setDetalles(AdaptadorDetallesSolicitud.listaEntidad(dto.getDetalles()));
        entidad.setDireccion(dto.getDireccion());
        entidad.setFechaEntrega(dto.getFechaEntrega());
        entidad.setFechaEntregaEstimada(dto.getFechaEntregaEstimada());
        return entidad;
    }

    public static List<Solicitud> listaEntidad(List<SolicitudDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(AdaptadorSolicitud::Entidad).collect(Collectors.toList());
    }

    public static SolicitudDTO DTO(Solicitud entidad) {
        if (entidad == null) return null;
        
        SolicitudDTO dto = new SolicitudDTO();
        dto.setCliente(AdaptadorCliente.DTO(entidad.getCliente()));
        dto.setEmpleado(AdaptadorEmpleado.DTO(entidad.getEmpleado()));
        dto.setTotal(entidad.getTotal());
        dto.setEstado(entidad.getEstado());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setFolio(entidad.getFolio());
        dto.setDetalles(AdaptadorDetallesSolicitud.listaDTO(entidad.getDetalles()));
        dto.setDireccion(entidad.getDireccion());
        dto.setFechaEntrega(entidad.getFechaEntrega());
        dto.setFechaEntregaEstimada(entidad.getFechaEntregaEstimada());
        return dto;
    }

    public static List<SolicitudDTO> listaDTO(List<Solicitud> entidades) {
        if (entidades == null) return null;
        return entidades.stream().map(AdaptadorSolicitud::DTO).collect(Collectors.toList());
    }
}