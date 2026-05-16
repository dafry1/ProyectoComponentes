package daos;

import adaptadoresDoc.SolicitudDoc;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import dominio.Solicitud;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Andre
 */
public class SolicitudDAO implements ISolicitudDAO {
    private static final System.Logger LOG = System.getLogger(SolicitudDAO.class.getName());
    
    private MongoCollection<Document> coleccion;

    public SolicitudDAO(MongoCollection coleccion) {
        this.coleccion = coleccion;
    }
    
    @Override
    public List<Solicitud> consultarSolicitudes() {
        List<Solicitud> lista = new ArrayList<>();
        for (Document doc : coleccion.find(new Document(), Document.class)) {
            lista.add(SolicitudDoc.toEntity(doc));
        }
        return lista;
    }

    @Override
    public Solicitud registrarSolicitud(Solicitud solicitud) {
        if (solicitud == null) {
            LOG.log(System.Logger.Level.WARNING, "Se intentó registrar una venta nula o vacía");
            return null;
        }
        try {
            Document documentoSolicitud = SolicitudDoc.toDocument(solicitud);
            InsertOneResult resultado = coleccion.insertOne(documentoSolicitud);
            if (resultado.getInsertedId() != null) {
                if (solicitud.getId() == null || solicitud.getId().isEmpty()) {
                    solicitud.setId(resultado.getInsertedId().asObjectId().getValue().toString());
                }
                return solicitud;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "Error al registrar la venta: " + e.getMessage(), e);
            throw new PersistenciaException("No se pudo registrar la venta correctamente.");
        }
    }

    @Override
    public int contarSolicitudesHoy() {
        String hoy = LocalDate.now().toString();
        return (int) coleccion.countDocuments(
                Filters.regex("fechaHora", "^" + hoy)
        );
    }
}