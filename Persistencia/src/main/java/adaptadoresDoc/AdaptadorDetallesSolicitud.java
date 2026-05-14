package adaptadoresDoc;

import dominio.DetallesSolicitud;
import org.bson.Document;

/**
 *
 * @author DANIEL
 */
public class AdaptadorDetallesSolicitud extends Adaptador {
 private PiezaDoc adaptadorPieza;
    
    public AdaptadorDetallesSolicitud(PiezaDoc adaptadorPieza){
        this.adaptadorPieza = adaptadorPieza;
    }
    
    // Método que transforma un documento a una entidad DetallesVenta
    public DetallesSolicitud toEntity(Document doc) {
        DetallesSolicitud detallesSolicitud = new DetallesSolicitud();
        
        detallesSolicitud.setCosto(Double.parseDouble(doc.getString("costo")));
        detallesSolicitud.setCantidad(Integer.parseInt(doc.getString("cantidad")));
        detallesSolicitud.setSubtotal(Double.parseDouble(doc.getString("subtotal")));

        Document piezaDoc = doc.get("pieza", Document.class);
        detallesSolicitud.setPieza(adaptadorPieza.toEntity(piezaDoc));
        
        return detallesSolicitud;
    }

    // Método que transforma una entidad DetallesVenta a un documento
    public Document toDocument(DetallesSolicitud detallesSolicitud) {
        Document doc = new Document();

        doc.put("costo", detallesSolicitud.getCosto());
        doc.put("cantidad", detallesSolicitud.getCantidad());
        doc.put("subtotal", detallesSolicitud.getSubtotal());
        
        Document piezaDoc = adaptadorPieza.toDocument(detallesSolicitud.getPieza());
        doc.put("pieza", piezaDoc);
        
        return doc;
    }
}