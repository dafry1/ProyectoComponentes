package adaptadores;

import DTOS.SolicitudDTO;
import dominio.Solicitud;
import java.util.List;

/**
 * Interfaz para adaptar objetos entre el dominio y los DTOs de Solicitud.
 * Sigue el patrón Mapper para mantener el desacoplamiento entre capas.
 * 
 * @author Andre
 */
public interface IAdaptadorSolicitud {
    
    /**
     * Adapta de DTO a entidad de dominio.
     * 
     * @param dto El objeto de transferencia de datos.
     * @return La entidad de dominio Solicitud.
     */
    Solicitud Entidad(SolicitudDTO dto);
    
    /**
     * Adapta una colección de DTOs a una lista de entidades de dominio.
     * 
     * @param dtos Lista de DTOs a transformar.
     * @return Lista de entidades Solicitud.
     */
    List<Solicitud> listaEntidad(List<SolicitudDTO> dtos);
    
    /**
     * Adapta de entidad de dominio a DTO.
     * 
     * @param entidad La entidad Solicitud con los datos de negocio.
     * @return El DTO listo para ser transportado a otras capas.
     */
    SolicitudDTO DTO(Solicitud entidad);
    
    /**
     * Adapta una colección de entidades de dominio a una lista de DTOs.
     * 
     * @param entidades Lista de objetos de dominio.
     * @return Lista de SolicitudDTO.
     */
    List<SolicitudDTO> listaDTO(List<Solicitud> entidades);
}