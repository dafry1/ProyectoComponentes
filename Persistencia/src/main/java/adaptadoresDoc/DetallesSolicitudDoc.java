package adaptadoresDoc;

import dominio.DetallesSolicitud;
import org.bson.Document;

/**
 * Clase de utilería que adapta los documentos de los detalles de solicitud a
 * entidades y viceversa mediante métodos estáticos puros.
 * 
 * @author DANIEL
 */
public final class DetallesSolicitudDoc {

    // Constructor privado para evitar la instanciación de la clase de utilerías
    private DetallesSolicitudDoc() {
        throw new UnsupportedOperationException("Clase de utilerías. No se permite instanciación.");
    }
    
    /**
     * Transforma un documento de MongoDB a una entidad DetallesSolicitud.
     */
    public static DetallesSolicitud toEntity(Document doc) {
        if (doc == null) return null;
    
        DetallesSolicitud detallesSolicitud = new DetallesSolicitud();
        Number costoNum = doc.get("costo", Number.class);
        detallesSolicitud.setCosto(costoNum != null ? costoNum.doubleValue() : 0.0);
        Number cantidadNum = doc.get("cantidad", Number.class);
        detallesSolicitud.setCantidad(cantidadNum != null ? cantidadNum.intValue() : 0);
        Number subtotalNum = doc.get("subtotal", Number.class);
        detallesSolicitud.setSubtotal(subtotalNum != null ? subtotalNum.doubleValue() : 0.0);
        Document piezaDoc = doc.get("pieza", Document.class);
        detallesSolicitud.setPieza(PiezaDoc.toEntity(piezaDoc));

        return detallesSolicitud;
    }

    /**
     * Transforma una entidad DetallesSolicitud a un documento de MongoDB.
     */
    public static Document toDocument(DetallesSolicitud detallesSolicitud) {
        if (detallesSolicitud == null) return null;
        
        Document doc = new Document();

        doc.put("costo", detallesSolicitud.getCosto());
        doc.put("cantidad", detallesSolicitud.getCantidad());
        doc.put("subtotal", detallesSolicitud.getSubtotal());
        
        // Llamada estática directa a PiezaDoc
        Document piezaDoc = PiezaDoc.toDocument(detallesSolicitud.getPieza());
        doc.put("pieza", piezaDoc);
        
        return doc;
    }
}