package daos;

import dominio.Solicitud;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Solicitud.
 * Maneja el acceso a datos para las órdenes procesadas en el sistema.
 * 
 * @author Andre
 */
public interface ISolicitudDAO {
    
    /**
     * Extrae todas las solicitudes registradas en la base de datos.
     * 
     * @return lista de objetos Solicitud.
     */
    List<Solicitud> consultarSolicitudes();
    
    /**
     * Registra una nueva solicitud en el sistema (persistencia).
     *  
     * @param solicitud La entidad a registrar.
     * @return La solicitud creada con su ID generado, si aplica.
     */
    Solicitud registrarSolicitud(Solicitud solicitud);
    
    /**
     * Filtra las solicitudes realizadas en una fecha específica.
     * 
     * @param fecha Día a consultar.
     * @return Lista de solicitudes que coinciden con la fecha proporcionada.
     */
    List<Solicitud> filtrarSolicitudesFecha(LocalDate fecha);
    
    /**
     * Filtra solicitudes cuyo monto total sea igual o mayor al mínimo proporcionado.
     * 
     * @param minimo El umbral inferior del total.
     * @return Lista de solicitudes que cumplen el criterio.
     */
    List<Solicitud> filtrarSolicitudesTotalMinimo(double minimo);
    
    /**
     * Filtra solicitudes cuyo monto total no exceda el máximo proporcionado.
     * 
     * @param maximo El umbral superior del total.
     * @return Lista de solicitudes dentro del rango de precio.
     */
    List<Solicitud> filtrarSolicitudesTotalMaximo(double maximo);
}