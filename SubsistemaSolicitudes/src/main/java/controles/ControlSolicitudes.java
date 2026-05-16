package controles;

import DTOS.ClienteDTO;
import DTOS.EmpleadoDTO;
import DTOS.SolicitudDTO;
import excepciones.NegocioException;
import bo.IPiezaBO;
import bo.ISolicitudBO;
import java.util.List;
import utilerias.UtilNegocio;

/**
 * Control dedicado a todo lo relacionado a las solicitudes de mercancía:
 * registrar solicitudes, consultar historial y gestionar stock relacionado.
 *
 * @author Andre
 */
public class ControlSolicitudes {
    private static String DEBUG = "Depuración";
    
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
     * @param solicitud a procesar
     * @return la solicitud registrada con datos generados (folio, fechas, etc.)
     * @throws NegocioException si la solicitud es nula o inválida
     */
    public SolicitudDTO procesarSolicitud(SolicitudDTO solicitud) {

        if (solicitud == null) {
            throw new NegocioException("La solicitud es nula");
        }
        
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
        
        LOG.log(System.Logger.Level.INFO, () -> ">> INICIANDO EL PROCESO DE UNA SOLICITUD");

        // Validaciones preliminares en capa de control
        if (solicitud.getDetalles() == null || solicitud.getDetalles().isEmpty()) {
            LOG.log(System.Logger.Level.ERROR, ">> Intentando procesar solicitud sin detalles");
            throw new NegocioException(CARRITO_VACIO);
        }

        if (solicitud.getCliente() == null) {
            LOG.log(System.Logger.Level.ERROR, ">> Intentando procesar solicitud sin cliente");
            throw new NegocioException(SIN_CLIENTE);
        }

        solicitud.setFolio(generarFolio());
        solicitud.setFechaHora(UtilNegocio.hoyTexto());
        double totalCalculado = solicitud.getDetalles().stream()
                .mapToDouble(d -> d.getSubtotal())
                .sum();
        solicitud.setTotal(totalCalculado);
        
        //Persistir la solicitud a través del BO
        SolicitudDTO solicitudRegistrada = solicitudBO.registrarSolicitud(solicitud);
        
        LOG.log(System.Logger.Level.INFO, () -> ">> Solicitud procesada exitosamente. Folio: " + solicitudRegistrada.getFolio());

        return solicitudRegistrada;
    }
    
    
    /**
     * Genera un folio para la solicitud
     */
    private String generarFolio() {
        int numero = solicitudBO.contarSolicitudesHoy() + 1;
        return "SOL - " + numero;
    }
    
    

}
