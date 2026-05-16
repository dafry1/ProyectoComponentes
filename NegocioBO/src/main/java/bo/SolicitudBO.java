package bo;

import DTOS.ClienteDTO;
import DTOS.EmpleadoDTO;
import DTOS.SolicitudDTO;
import adaptadores.AdaptadorSolicitud;
import dominio.Solicitud;
import excepciones.NegocioException;
import daos.ISolicitudDAO;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import utilerias.UtilNegocio;

/**
 * BO para la entidad Solicitud
 * 
 * @author Andre
 */
public class SolicitudBO implements ISolicitudBO {
    private static final System.Logger LOG = System.getLogger(SolicitudBO.class.getName());
    private String DEBUG;
    
    // Atributos
    private final ISolicitudDAO solicitudDAO;

    /**
     * Constructor que inyecta DAO y adaptador
     * 
     * @param solicitudDAO
     */
    public SolicitudBO(ISolicitudDAO solicitudDAO) {
        this.solicitudDAO = solicitudDAO;
    }
    
    /** Centraliza la forma en la que se adaptan las solicitudes de Entidad a DTO */
    private List<SolicitudDTO> adaptarInternamente(List<Solicitud> solicitudes) {
        return AdaptadorSolicitud.listaDTO(solicitudes);
    }
    
    /**
     * Extrae todas las solicitudes de la BD
     *
     * @return lista de SolicitudDTO mapeadas
     */
    @Override
    public List<SolicitudDTO> consultarSolicitudes() {
        return adaptarInternamente(solicitudDAO.consultarSolicitudes());
    }
    
    
    
    /**
     * Registra una solicitud en el sistema
     * 
     * @param solicitud
     * @return la solicitud creada
     */
    @Override
    public SolicitudDTO registrarSolicitud(SolicitudDTO solicitud) {
        
        // Asigna folio y fecha y hora
        solicitud.setFolio(generarFolio());
        solicitud.setFechaEntrega(UtilNegocio.hoyTexto());
        
        solicitud.setEstado("PENDIENTE");

        double totalCalculado = solicitud.getDetalles().stream()
                .mapToDouble(d -> d.getSubtotal())
                .sum();
        solicitud.setTotal(totalCalculado);
        
        if (solicitud.getFechaEntregaEstimada() == null) {
            solicitud.setFechaEntregaEstimada("3 a 5 días hábiles");
        }
        
        // Registra la solicitud
        Solicitud s = AdaptadorSolicitud.Entidad(solicitud);
        return AdaptadorSolicitud.DTO(solicitudDAO.registrarSolicitud(s));
    }
    
    /**
     * Genera un folio para la solicitud
     */
    private String generarFolio() {
        int numero = solicitudDAO.consultarSolicitudes().size() + 1;
        return "SOL - " + numero;
    }
    
    
    @Override
    public List<SolicitudDTO> filtrarSolicitudesFecha(LocalDate fecha) {
        if (fecha == null) {
            DEBUG = "Fecha de filtrado nula";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        return adaptarInternamente(solicitudDAO.consultarSolicitudes());
    }

    @Override
    public List<SolicitudDTO> filtrarSolicitudesTotalMinimo(double minimo) {
        if (minimo < 0) {
            DEBUG = "Total mínimo menor a 0";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        return adaptarInternamente(solicitudDAO.consultarSolicitudes());
    }

    @Override
    public List<SolicitudDTO> filtrarSolicitudesTotalMaximo(double maximo) {
        if (maximo <= 0) {
            DEBUG = "Total máximo menor o igual a 0";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        return adaptarInternamente(solicitudDAO.consultarSolicitudes());
    }

    @Override
    public int contarSolicitudesHoy() {
        try {
            return solicitudDAO.contarSolicitudesHoy();
        } catch (PersistenciaException e) {
            DEBUG = "Error al contar las solicitudes: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
    }
}