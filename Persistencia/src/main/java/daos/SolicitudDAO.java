package daos;

import dominio.Solicitud;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre
 */
public class SolicitudDAO implements ISolicitudDAO {
    private static final System.Logger LOG = System.getLogger(SolicitudDAO.class.getName());
    
    private static List<Solicitud> SOLICITUDES = new ArrayList<>();
    
    @Override
    public List<Solicitud> consultarSolicitudes() {
        return SOLICITUDES;
    }

    @Override
    public Solicitud registrarSolicitud(Solicitud solicitud) {
        SOLICITUDES.add(solicitud);
        LOG.log(System.Logger.Level.INFO, ">> Venta registrada exitosamente: " + solicitud.getCliente().getNombres() + " : " + solicitud.getDetalles().size());
        return solicitud;
    }
}