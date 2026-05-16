package daos;

import dominio.Solicitud;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Solicitud.
 * Maneja el acceso a datos para las órdenes procesadas en el sistema
 * 
 * @author Andre
 */
public interface ISolicitudDAO {
    
    /**
     * Extrae todas las solicitudes registradas en la base de datos
     * 
     * @return lista de objetos Solicitud
     */
    List<Solicitud> consultarSolicitudes();
    
    /**
     * Registra una nueva solicitud en el sistema (persistencia)
     *  
     * @param solicitud La entidad a registrar
     * @return La solicitud creada con su ID generado, si aplica
     */
    Solicitud registrarSolicitud(Solicitud solicitud);
    
    int contarSolicitudesHoy();
}