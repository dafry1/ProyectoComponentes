package adaptadoresDoc;

import dominio.DetallesSolicitud;
import dominio.Solicitud;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;

/**
 * Clase de utilería que adapta los documentos de las solicitudes a
 * entidades y viceversa mediante métodos estáticos puros.
 * 
 * @author aron
 * @author Andre
 */
public final class SolicitudDoc {
    
    // Constructor privado para evitar que se instancie la clase de utilerías
    private SolicitudDoc() {
        throw new UnsupportedOperationException("Clase de utilerías. No se permite instanciación.");
    }
    
    /**
     * Transforma un documento de MongoDB a una entidad Solicitud.
     */
    public static Solicitud toEntity(Document doc) {
        if (doc == null) return null;
        
        Solicitud solicitud = new Solicitud();
        
        solicitud.setId(AdaptadorDoc.idTexto(doc));
        
        Document clienteDoc = doc.get("cliente", Document.class);
        Document empleadoDoc = doc.get("empleado", Document.class);
        
        // Llamados estáticos directos a los adaptadores correspondientes
        solicitud.setCliente(ClienteDoc.toEntity(clienteDoc));
        solicitud.setEmpleado(EmpleadoDoc.toEntityEmbebido(empleadoDoc));
        
        List<Document> detallesDoc = doc.getList("detalles", Document.class);

        if (detallesDoc != null) {
            List<DetallesSolicitud> detallesSolicitud = detallesDoc.stream()
                    .map(DetallesSolicitudDoc::toEntity)
                    .collect(Collectors.toList());

            solicitud.setDetalles(detallesSolicitud);
        }
        
        // CORRECCIÓN: Extracción segura del total numérico (Double)
        if (doc.get("total") != null) {
            try {
                // Intenta obtenerlo directamente como Double (lo correcto según tu toDocument)
                solicitud.setTotal(doc.getDouble("total"));
            } catch (ClassCastException e) {
                // En caso de que haya registros viejos guardados como String de texto en la BD
                solicitud.setTotal(Double.parseDouble(doc.getString("total")));
            }
        }
        
        solicitud.setEstado(doc.getString("estado"));
        solicitud.setFechaHora(doc.getString("fechaHora"));
        solicitud.setFolio(doc.getString("folio"));
        solicitud.setFechaEntrega(doc.getString("fechaEntrega"));
        solicitud.setFechaEntregaEstimada(doc.getString("fechaEntregaEstimada"));
        solicitud.setDireccion(doc.getString("direccion"));
        
        return solicitud;
    }

    /**
     * Transforma una entidad Solicitud a un documento de MongoDB.
     */
    public static Document toDocument(Solicitud solicitud) {
        if (solicitud == null) return null;
        
        Document doc = new Document();

        if (solicitud.getId() != null && !solicitud.getId().isEmpty()) {
            doc.put("_id", AdaptadorDoc.textoId(solicitud.getId()));
        }

        // Llamados estáticos directos para construir el documento BSON
        doc.put("cliente", ClienteDoc.toDocumentEmbebido(solicitud.getCliente()));
        doc.put("empleado", EmpleadoDoc.toDocumentEmbebido(solicitud.getEmpleado()));
        
        if (solicitud.getDetalles() != null) {
            List<Document> detallesVentaDoc = solicitud.getDetalles().stream()
                    .map(DetallesSolicitudDoc::toDocument)
                    .collect(Collectors.toList());

            doc.put("detalles", detallesVentaDoc);
        }
        doc.put("estado", solicitud.getEstado());
        
        // Se guarda como un tipo numérico de forma nativa en Mongo
        doc.put("total", solicitud.getTotal());
        doc.put("fechaHora", solicitud.getFechaHora());
        doc.put("folio", solicitud.getFolio());
        doc.put("fechaEntrega", solicitud.getFechaEntrega());
        doc.put("fechaEntregaEstimada", solicitud.getFechaEntregaEstimada());
        doc.put("direccion", solicitud.getDireccion());
        
        return doc;
    }
}