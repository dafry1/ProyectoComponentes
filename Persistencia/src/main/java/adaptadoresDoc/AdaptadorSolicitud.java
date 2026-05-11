package adaptadoresDoc;

import dominio.DetallesVenta;
import dominio.Solicitud;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;

/**
 * Clase que adapta los documentos de las solicitudes a
 * entidades y viceversa (Hereda de la clase Adaptador).
 * 
 * @author aron
 */
public class AdaptadorSolicitud extends Adaptador{
    private AdaptadorCliente adaptadorCliente;
    private AdaptadorEmpleado adaptadorEmpleado;
    private AdaptadorDetallesVenta adaptadorDetallesVenta;
    
    public AdaptadorSolicitud(AdaptadorCliente adaptadorCliente,
            AdaptadorEmpleado adaptadorEmpleado,
            AdaptadorDetallesVenta adaptadorDetallesVenta){
        this.adaptadorCliente = adaptadorCliente;
        this.adaptadorEmpleado = adaptadorEmpleado;
        this.adaptadorDetallesVenta = adaptadorDetallesVenta;
    }
    
    // Método que transforma un documento a una entidad Solicitud
    public Solicitud toEntity(Document doc) {
        Solicitud solicitud = new Solicitud();
        
        Document clienteDoc = doc.get("cliente", Document.class);
        Document empleadoDoc = doc.get("empleado", Document.class);
        
        solicitud.setCliente(adaptadorCliente.toEntity(clienteDoc));
        solicitud.setEmpleado(adaptadorEmpleado.toEntity(empleadoDoc));
        
        List<Document> detallesDoc = doc.getList("detalles", Document.class);

        if (detallesDoc != null) {
            List<DetallesVenta> detallesVenta = detallesDoc.stream()
                    .map(adaptadorDetallesVenta::toEntity)
                    .collect(Collectors.toList());

            solicitud.setDetalles(detallesVenta);
        }
        
        solicitud.setTotal(Double.parseDouble(doc.getString("total")));
        solicitud.setFechaHora(doc.getString("fechaHora"));
        solicitud.setFolio(doc.getString("folio"));
        
        solicitud.setFechaEntrega(doc.getString("fechaEntrega"));
        solicitud.setFechaEntregaEstimada(doc.getString("fechaEntregaEstimada"));
        solicitud.setDireccion(doc.getString("direccion"));
        
        return solicitud;
    }

    // Método que transforma una entidad Solicitud a un documento
    public Document toDocument(Solicitud solicitud) {
        Document doc = new Document();

        doc.put("cliente", adaptadorCliente.toDocument(solicitud.getCliente()));
        doc.put("empleado", adaptadorEmpleado.toDocument(solicitud.getEmpleado()));
        if (solicitud.getDetalles() != null) {
            List<Document> detallesVentaDoc = solicitud.getDetalles().stream()
                    .map(adaptadorDetallesVenta::toDocument)
                    .collect(Collectors.toList());

            doc.put("detalles", detallesVentaDoc);
        }
        
        doc.put("total", solicitud.getTotal());
        doc.put("fechaHora", solicitud.getFechaHora());
        doc.put("folio", solicitud.getFolio());
        
        doc.put("fechaEntrega", solicitud.getFechaEntrega());
        doc.put("fechaEntregaEstimada", solicitud.getFechaEntregaEstimada());
        doc.put("direccion", solicitud.getDireccion());
        
        return doc;
    }
}
