/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadoresDoc;

import dominio.Pieza;
import org.bson.Document;

/**
 *
 * @author Andre
 */
public final class PiezaDoc {
    private PiezaDoc(){}
    
    // Método que transforma un documento a una entidad Pieza
    public static Pieza toEntity(Document doc) {
        if (doc == null) return null;

        Pieza pieza = new Pieza();
        
        pieza.setId(AdaptadorDoc.idTexto(doc)); 
        pieza.setNombre(doc.getString("nombre"));
        pieza.setCategoria(doc.getString("categoria"));
        pieza.setMarcaPieza(doc.getString("marcaPieza"));
        pieza.setModeloPieza(doc.getString("modeloPieza"));
        
        Number costo = doc.get("costoPieza", Number.class);
        pieza.setCostoPieza(costo != null ? costo.doubleValue() : 0.0);
        
        pieza.setStockPieza(doc.getInteger("stockPieza"));

        return pieza;
    }

    // Método que transforma un documento a una entidad Pieza (de forma embebida)
    public static Pieza toEntityEmbebido(Document doc) {
        if (doc == null) return null;

        Pieza pieza = new Pieza();
        
        pieza.setId(AdaptadorDoc.idTexto(doc)); 
        pieza.setNombre(doc.getString("nombre"));
        pieza.setCategoria(doc.getString("categoria"));
        pieza.setMarcaPieza(doc.getString("marcaPieza"));
        pieza.setModeloPieza(doc.getString("modeloPieza"));
        
        Number costo = doc.get("costoPieza", Number.class);
        pieza.setCostoPieza(costo != null ? costo.doubleValue() : 0.0);

        return pieza;
    }

    // Método que transforma una entidad Pieza a un documento
    public static Document toDocument(Pieza pieza) {
        if (pieza == null) return null;

        Document doc = new Document();

        if (pieza.getId() != null && !pieza.getId().isEmpty()) {
            doc.put("_id", AdaptadorDoc.textoId(pieza.getId()));
        }

        doc.put("nombre", pieza.getNombre());
        doc.put("categoria", pieza.getCategoria());
        doc.put("marcaPieza", pieza.getMarcaPieza());
        doc.put("modeloPieza", pieza.getModeloPieza());
        doc.put("costoPieza", pieza.getCostoPieza());
        doc.put("stockPieza", pieza.getStockPieza());
        
        return doc;
    }

    // Método que transforma una entidad Pieza a un documento (de forma embebida)
    public static Document toDocumentEmbebido(Pieza pieza) {
        if (pieza == null) return null;

        Document doc = new Document();

        if (pieza.getId() != null && !pieza.getId().isEmpty()) {
            doc.put("_id", AdaptadorDoc.textoId(pieza.getId()));
        }

        doc.put("nombre", pieza.getNombre());
        doc.put("categoria", pieza.getCategoria());
        doc.put("marcaPieza", pieza.getMarcaPieza());
        doc.put("modeloPieza", pieza.getModeloPieza());
        doc.put("costoPieza", pieza.getCostoPieza());
        
        return doc;
    }
}