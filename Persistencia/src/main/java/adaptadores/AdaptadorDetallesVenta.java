package adaptadores;

import dominio.DetallesVenta;
import org.bson.Document;

/**
 * Clase que adapta los documentos de los detalles de una
 * venta a entidades y viceversa (Hereda de la clase Adaptador).
 * 
 * @author aron
 */
public class AdaptadorDetallesVenta {
    private AdaptadorPieza adaptadorPieza;
    
    public AdaptadorDetallesVenta(AdaptadorPieza adaptadorPieza){
        this.adaptadorPieza = adaptadorPieza;
    }
    
    // Método que transforma un documento a una entidad DetallesVenta
    public DetallesVenta toEntity(Document doc) {
        DetallesVenta detallesVenta = new DetallesVenta();
        
        detallesVenta.setCosto(Double.parseDouble(doc.getString("costo")));
        detallesVenta.setCantidad(Integer.parseInt(doc.getString("cantidad")));
        detallesVenta.setSubtotal(Double.parseDouble(doc.getString("subtotal")));

        Document piezaDoc = doc.get("pieza", Document.class);
        detallesVenta.setPieza(adaptadorPieza.toEntity(piezaDoc));
        
        return detallesVenta;
    }

    // Método que transforma una entidad DetallesVenta a un documento
    public Document toDocument(DetallesVenta detallesVenta) {
        Document doc = new Document();

        doc.put("costo", detallesVenta.getCosto());
        doc.put("cantidad", detallesVenta.getCantidad());
        doc.put("subtotal", detallesVenta.getSubtotal());
        
        Document piezaDoc = adaptadorPieza.toDocument(detallesVenta.getPieza());
        doc.put("pieza", piezaDoc);
        
        return doc;
    }
}