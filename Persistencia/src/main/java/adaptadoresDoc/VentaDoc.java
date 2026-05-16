package adaptadoresDoc;

import dominio.DetallesVenta;
import dominio.Venta;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;

/**
 * Clase de utilería que adapta los documentos de las ventas a
 * entidades y viceversa mediante métodos estáticos puros.
 * 
 * @author aron
 * @author Andre
 */
public final class VentaDoc {
    
    // Constructor privado para evitar que se instancie la clase de utilerías
    private VentaDoc() {
        throw new UnsupportedOperationException("Clase de utilerías. No se permite instanciación.");
    }
    
    /**
     * Transforma un documento de MongoDB a una entidad Venta.
     */
    public static Venta toEntity(Document doc) {
        if (doc == null) return null;
        Venta venta = new Venta();
        
        // Uso directo de la red de estáticos de AdaptadorDoc
        venta.setId(AdaptadorDoc.idTexto(doc));
        
        Document clienteDoc = doc.get("cliente", Document.class);
        Document empleadoDoc = doc.get("empleado", Document.class);
        
        // Llamados estáticos directos a las otras clases de utilería
        venta.setCliente(ClienteDoc.toEntityEmbebido(clienteDoc));
        venta.setEmpleado(EmpleadoDoc.toEntityEmbebido(empleadoDoc));
        
        List<Document> detallesDoc = doc.getList("detalles", Document.class);
        
        if (detallesDoc != null) {
            List<DetallesVenta> detallesVenta = detallesDoc.stream()
                    .map(DetallesVentaDoc::toEntityEmbebido) // Referencia al método estático
                    .collect(Collectors.toList());

            venta.setDetalles(detallesVenta);
        }
        
        Number total = doc.get("total", Number.class);
        venta.setTotal(total != null ? total.doubleValue() : 0.0);
        
        venta.setFechaHora(doc.getString("fechaHora"));
        venta.setFolio(doc.getString("folio"));
        
        return venta;
    }

    /**
     * Transforma una entidad Venta a un documento de MongoDB.
     */
    public static Document toDocument(Venta venta) {
        if (venta == null) return null;
        Document doc = new Document();

        if (venta.getId() != null && !venta.getId().isEmpty()) {
            doc.put("_id", AdaptadorDoc.textoId(venta.getId()));
        }

        // Llamados estáticos directos para construir el documento
        doc.put("cliente", ClienteDoc.toDocumentEmbebido(venta.getCliente()));
        doc.put("empleado", EmpleadoDoc.toDocumentEmbebido(venta.getEmpleado()));

        if (venta.getDetalles() != null) {
            List<Document> detallesVentaDoc = venta.getDetalles().stream()
                    .map(DetallesVentaDoc::toDocumentEmbebido)
                    .collect(Collectors.toList());
            doc.put("detalles", detallesVentaDoc);
        }

        doc.put("total", venta.getTotal());
        doc.put("fechaHora", venta.getFechaHora());
        doc.put("folio", venta.getFolio());

        return doc;
    }
}