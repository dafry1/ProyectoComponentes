package bo;

import DTOS.SolicitudDTO;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface ISolicitudBO {
    /**
     * Extrae todas las solicitud de la BD
     *
     * @return lista de VentaDTO mapeadas
     */
    List<SolicitudDTO> consultarSolicitudes();
    
    /**
     * Filtra las solicitud por fecha
     * 
     * @param fecha a consultar
     * 
     * @return las solicitud de ese día en específico
     */
    List<SolicitudDTO> filtrarSolicitudesFecha(LocalDate fecha);
    
    
    /**
     * Filtra solicitud con un mínimo de ganancias
     * 
     * @param minimo de su total
     * 
     * @return solicitud que cumplan el filtro
     */
    List<SolicitudDTO> filtrarSolicitudesTotalMinimo(double minimo);
    
    /**
     * Filtra solicitud con un máximo de ganancias
     * 
     * @param maximo de su total
     * 
     * @return solicitud que cumplan el filtro
     */
    List<SolicitudDTO> filtrarSolicitudesTotalMaximo(double maximo);
    
    /**
     * Registra una solicitud en el sistema
     * 
     * @param solicitud a registrar
     * 
     * @return la solicitud creada
     */
    SolicitudDTO registrarSolicitud(SolicitudDTO solicitud);
}
