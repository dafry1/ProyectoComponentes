package adaptadores;

import dominio.Pieza;
import org.bson.Document;

/**
 * Clase que adapta los documentos de las piezas a entidades
 * y viceversa (Hereda de la clase Adaptador).
 * 
 * @author aron
 */
public class AdaptadorPieza extends Adaptador{
    // Método que transforma un documento a una entidad Pieza
    public Pieza toEntity(Document doc) {
        Pieza pieza = new Pieza();
        
        pieza.setId(Long.valueOf(super.idTexto(doc)));
        pieza.setNombre(doc.getString("nombre"));
        pieza.setCategoria(doc.getString("categoria"));
        pieza.setMarcaPieza(doc.getString("marcaPieza"));
        pieza.setModeloPieza(doc.getString("modeloPieza"));
        pieza.setCostoPieza(Double.parseDouble(doc.getString("costoPieza")));
        pieza.setStockPieza(Integer.parseInt(doc.getString("stockPieza")));

        return pieza;
    }

    // Método que transforma una entidad Pieza a un documento
    public Document toDocument(Pieza pieza) {
        Document doc = new Document();

        doc.put("nombre", pieza.getNombre());
        doc.put("categoria", pieza.getCategoria());
        doc.put("marcaPieza", pieza.getMarcaPieza());
        doc.put("modeloPieza", pieza.getModeloPieza());
        doc.put("costoPieza", pieza.getCostoPieza());
        doc.put("stockPieza", pieza.getStockPieza());
        
        return doc;
    }
}