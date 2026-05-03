package bo;

import DTOS.ClienteDTO;
import DTOS.EmpleadoDTO;
import DTOS.SolicitudDTO;
import dominio.Solicitud;
import excepciones.NegocioException;
import adaptadores.IAdaptadorSolicitud;
import daos.ISolicitudDAO;
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
    private final IAdaptadorSolicitud adaptadorSolicitud;
    
    /**
     * Constructor que inyecta DAO y adaptador
     * 
     * @param solicitudDAO
     * @param adaptadorSolicitud 
     */
    public SolicitudBO(ISolicitudDAO solicitudDAO, IAdaptadorSolicitud adaptadorSolicitud) {
        this.solicitudDAO = solicitudDAO;
        this.adaptadorSolicitud = adaptadorSolicitud;
    }
    
    /** Centraliza la forma en la que se adaptan las solicitudes de Entidad a DTO */
    private List<SolicitudDTO> adaptarInternamente(List<Solicitud> solicitudes) {
        return adaptadorSolicitud.listaDTO(solicitudes);
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
        // Solicitud inválida (mantiene lógica de DetallesVenta/Detalles)
        if (solicitud == null || solicitud.getDetalles().isEmpty()) {
            DEBUG = "Solicitud vacía o sin detalles";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        
        // Sin cliente
        ClienteDTO cliente = solicitud.getCliente();
        if (cliente == null) {
            DEBUG = "Solicitud sin cliente";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG); 
        }
        
        // Datos del cliente inválidos
        if (!UtilNegocio.validarCliente(cliente)) {
            DEBUG = "Cliente con datos inválidos";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG); 
        }
        
        // Sin empleado
        EmpleadoDTO empleado = solicitud.getEmpleado();
        if (empleado == null) {
            DEBUG = "Solicitud sin empleado";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG); 
        }
        
        // Datos del empleado inválidos
        if (!UtilNegocio.validarEmpleado(empleado)) {
            DEBUG = "Empleado con datos inválidos";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG); 
        }
        
        // Asigna folio y fecha y hora
        solicitud.setFolio(generarFolio());
        solicitud.setFechaEntrega(generarFecha());
        
        // Registra la solicitud
        Solicitud s = adaptadorSolicitud.Entidad(solicitud);
        return adaptadorSolicitud.DTO(solicitudDAO.registrarSolicitud(s));
    }
    
    /**
     * Genera un folio para la solicitud
     */
    private String generarFolio() {
        int numero = solicitudDAO.consultarSolicitudes().size() + 1;
        return "SOL - " + numero;
    }
    
    /**
     * Auxiliar obtiene la fecha formateada
     */
    private String generarFecha() {
        LocalDateTime fechaHoraRegistro = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaHoraRegistro.format(formato);
    }

    @Override
    public List<SolicitudDTO> filtrarSolicitudesFecha(LocalDate fecha) {
        if (fecha == null) {
            DEBUG = "Fecha de filtrado nula";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        // Aquí llamarías al método del DAO correspondiente cuando esté implementado
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
}