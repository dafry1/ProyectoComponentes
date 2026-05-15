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
    if (solicitud.getFolio() == null || solicitud.getFolio().isBlank()) {
        solicitud.setFolio("SOLICITUD-" + (SOLICITUDES.size() + 1));
    }

    List copiaDetalles = new ArrayList<>(solicitud.getDetalles());
    solicitud.setDetalles(copiaDetalles);

    SOLICITUDES.add(solicitud);
    
    LOG.log(System.Logger.Level.INFO, ">> Solicitud guardada: " + solicitud.getFolio() + " con " + solicitud.getDetalles().size() + " piezas.");
    
    return solicitud;
}
}