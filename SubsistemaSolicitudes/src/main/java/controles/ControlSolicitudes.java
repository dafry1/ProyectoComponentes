package controles;

import DTOS.SolicitudDTO;
import excepciones.NegocioException;
import bo.IPiezaBO;
import bo.ISolicitudBO;
import java.util.List;

/**
 * Control dedicado a todo lo relacionado a las solicitudes de mercancía: 
 * registrar solicitudes, consultar historial y gestionar stock relacionado.
 * 
 * @author Andre
 */
public class ControlSolicitudes {
    private static final System.Logger LOG = System.getLogger(ControlSolicitudes.class.getName());
    
    private static final String CARRITO_VACIO = "No se puede procesar una solicitud sin artículos seleccionados";
    private static final String SIN_CLIENTE = "No se asignó un cliente para la solicitud";
    
    // BO
    private final ISolicitudBO solicitudBO;
    private final IPiezaBO piezaBO;
    
    /**
     * Constructor que inyecta los objetos de negocio necesarios.
     * 
     * @param piezaBO BO para gestión de inventario
     * @param solicitudBO BO para gestión de solicitudes
     */
    public ControlSolicitudes(IPiezaBO piezaBO, ISolicitudBO solicitudBO) {
        this.piezaBO = piezaBO;
        this.solicitudBO = solicitudBO;
    }
    
    /**
     * Consulta las solicitudes registradas a través de su BO respectivo.
     *
     * @return lista de tipo SolicitudDTO
     */
    public List<SolicitudDTO> consultarSolicitudes() {
        List<SolicitudDTO> solicitudes = solicitudBO.consultarSolicitudes();
        LOG.log(System.Logger.Level.INFO, () -> ">> Solicitudes consultadas con éxito: " + solicitudes.size());
        return solicitudes;
    }
    
    /**
     * Coordina la actualización del stock y el registro
     * de la nueva solicitud.
     * 
     * @param solicitud a procesar
     * @return la solicitud registrada con datos generados (folio, fechas, etc.)
     * @throws NegocioException si la solicitud es nula o inválida
     */
    public SolicitudDTO procesarSolicitud(SolicitudDTO solicitud) {
        
        if (solicitud == null) {
            throw new NegocioException("La solicitud es nula");
        }

        LOG.log(System.Logger.Level.INFO, () -> ">> INICIANDO EL PROCESO DE UNA SOLICITUD");
        
        // 1. Validaciones preliminares en capa de control
        if (solicitud.getDetalles() == null || solicitud.getDetalles().isEmpty()) {
            LOG.log(System.Logger.Level.ERROR, ">> Intentando procesar solicitud sin detalles");
            throw new NegocioException(CARRITO_VACIO);
        }
        
        if (solicitud.getCliente() == null) {
            LOG.log(System.Logger.Level.ERROR, ">> Intentando procesar solicitud sin cliente");
            throw new NegocioException(SIN_CLIENTE);
        }
        
        // 2. Actualizar stock (Se asume que las solicitudes también afectan el inventario disponible)
        piezaBO.actualizarStockTrasVenta(solicitud.getDetalles());
        
        // 3. Persistir la solicitud a través del BO
        SolicitudDTO solicitudRegistrada = solicitudBO.registrarSolicitud(solicitud);
        
        LOG.log(System.Logger.Level.INFO, () -> ">> Solicitud procesada exitosamente. Folio: " + solicitudRegistrada.getFolio());
        
        return solicitudRegistrada;
    }
}