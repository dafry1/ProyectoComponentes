package adaptadores;

import DTOS.SolicitudDTO;
import dominio.Solicitud;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador para transformar entre Solicitud y SolicitudDTO.
 * Incluye los nuevos atributos de dirección y fechas de entrega.
 * 
 * @author Andre
 */
public class AdaptadorSolicitud implements IAdaptadorSolicitud { 
    private IAdaptadorEmpleado adaptadorEmpleado;
    private IAdaptadorCliente adaptadorCliente;
    private IAdaptadorDetallesSolicitud adaptadorDetallesSolicitud;

    public AdaptadorSolicitud(IAdaptadorEmpleado adaptadorEmpleado, IAdaptadorCliente adaptadorCliente, IAdaptadorDetallesSolicitud adaptadorDetallesSolicitud){
        this.adaptadorEmpleado = adaptadorEmpleado;
        this.adaptadorCliente = adaptadorCliente;
        this.adaptadorDetallesSolicitud = adaptadorDetallesSolicitud;
    }
    
    @Override
    public Solicitud Entidad(SolicitudDTO dto) {
        Solicitud entidad = new Solicitud();
        // Atributos base
        entidad.setCliente(adaptadorCliente.Entidad(dto.getCliente()));
        entidad.setEmpleado(adaptadorEmpleado.Entidad(dto.getEmpleado()));
        entidad.setTotal(dto.getTotal());
        entidad.setEstado(dto.getEstado());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setFolio(dto.getFolio());
        entidad.setDetalles(adaptadorDetallesSolicitud.listaEntidad(dto.getDetalles()));
        
        // Atributos especiales de Solicitud
        entidad.setDireccion(dto.getDireccion());
        entidad.setFechaEntrega(dto.getFechaEntrega());
        entidad.setFechaEntregaEstimada(dto.getFechaEntregaEstimada());
        
        return entidad;
    }

    @Override
    public List<Solicitud> listaEntidad(List<SolicitudDTO> dtos) {
        return dtos.stream().map(this::Entidad).collect(Collectors.toList());
    }

    @Override
    public SolicitudDTO DTO(Solicitud entidad) {
        SolicitudDTO dto = new SolicitudDTO();
        // Atributos base
        dto.setCliente(adaptadorCliente.DTO(entidad.getCliente()));
        dto.setEmpleado(adaptadorEmpleado.DTO(entidad.getEmpleado()));
        dto.setTotal(entidad.getTotal());
        dto.setEstado(entidad.getEstado());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setFolio(entidad.getFolio());
        dto.setDetalles(adaptadorDetallesSolicitud.listaDTO(entidad.getDetalles()));
        
        // Atributos especiales de Solicitud
        dto.setDireccion(entidad.getDireccion());
        dto.setFechaEntrega(entidad.getFechaEntrega());
        dto.setFechaEntregaEstimada(entidad.getFechaEntregaEstimada());
        
        return dto;
    }

    @Override
    public List<SolicitudDTO> listaDTO(List<Solicitud> entidades) {
        return entidades.stream().map(this::DTO).collect(Collectors.toList());
    }
}