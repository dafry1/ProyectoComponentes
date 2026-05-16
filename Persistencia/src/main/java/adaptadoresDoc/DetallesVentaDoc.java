package adaptadoresDoc;

import dominio.DetallesVenta;
import org.bson.Document;

/**
 * Clase de utilería que adapta los documentos de los detalles de una
 * venta a entidades y viceversa mediante métodos estáticos puros.
 * 
 * @author aron
 * @author Andre
 */
public final class DetallesVentaDoc {
    
    // Constructor privado para evitar que se instancie la clase de utilerías
    private DetallesVentaDoc() {
        throw new UnsupportedOperationException("Clase de utilerías. No se permite instanciación.");
    }
    
    /**
     * Transforma un documento a una entidad DetallesVenta.
     */
    public static DetallesVenta toEntity(Document doc) {
        if (doc == null) return null;
        DetallesVenta detallesVenta = new DetallesVenta();
        
        Number costo = doc.get("costo", Number.class);
        detallesVenta.setCosto(costo != null ? costo.doubleValue() : 0.0);
        
        Number cantidad = doc.get("cantidad", Number.class);
        detallesVenta.setCantidad(cantidad != null ? cantidad.intValue() : 0);
        
        Number subtotal = doc.get("subtotal", Number.class);
        detallesVenta.setSubtotal(subtotal != null ? subtotal.doubleValue() : 0.0);

        Document piezaDoc = doc.get("pieza", Document.class);
        if (piezaDoc != null) {
            // Llamada estática directa a PiezaDoc
            detallesVenta.setPieza(PiezaDoc.toEntity(piezaDoc));
        }
        
        return detallesVenta;
    }
    
    /**
     * Transforma un documento a una entidad DetallesVenta (de forma embebida).
     */
    public static DetallesVenta toEntityEmbebido(Document doc) {
        if (doc == null) return null;
        DetallesVenta detallesVenta = new DetallesVenta();
        
        Number costo = doc.get("costo", Number.class);
        detallesVenta.setCosto(costo != null ? costo.doubleValue() : 0.0);
        
        Number cantidad = doc.get("cantidad", Number.class);
        detallesVenta.setCantidad(cantidad != null ? cantidad.intValue() : 0);
        
        Number subtotal = doc.get("subtotal", Number.class);
        detallesVenta.setSubtotal(subtotal != null ? subtotal.doubleValue() : 0.0);

        Document piezaDoc = doc.get("pieza", Document.class);
        if (piezaDoc != null) {
            // Llamada estática directa a PiezaDoc
            detallesVenta.setPieza(PiezaDoc.toEntityEmbebido(piezaDoc));
        }
        
        return detallesVenta;
    }

    /**
     * Transforma una entidad DetallesVenta a un documento.
     */
    public static Document toDocument(DetallesVenta detallesVenta) {
        if (detallesVenta == null) return null;
        Document doc = new Document();

        doc.put("costo", detallesVenta.getCosto());
        doc.put("cantidad", detallesVenta.getCantidad());
        doc.put("subtotal", detallesVenta.getSubtotal());
        
        if (detallesVenta.getPieza() != null) {
            // Llamada estática directa a PiezaDoc
            doc.put("pieza", PiezaDoc.toDocument(detallesVenta.getPieza()));
        }
        
        return doc;
    }
    
    /**
     * Transforma una entidad DetallesVenta a un documento (de forma embebida).
     */
    public static Document toDocumentEmbebido(DetallesVenta detallesVenta) {
        if (detallesVenta == null) return null;
        Document doc = new Document();

        doc.put("costo", detallesVenta.getCosto());
        doc.put("cantidad", detallesVenta.getCantidad());
        doc.put("subtotal", detallesVenta.getSubtotal());
        
        if (detallesVenta.getPieza() != null) {
            // Llamada estática directa a PiezaDoc
            doc.put("pieza", PiezaDoc.toDocumentEmbebido(detallesVenta.getPieza()));
        }
        
        return doc;
    }
}