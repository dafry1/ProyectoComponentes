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
public class PiezaDoc extends Adaptador{
    private PiezaDoc(){}
    
    private static PiezaDoc instancia;
    public static PiezaDoc singleton() {
        if (instancia == null) {
            instancia = new PiezaDoc();
        }
        return instancia;
    }
    
     private String METODOPRUEBACOMPILAR() {
        return "hola";
    }
    

    public Pieza toEntity(Document doc) {
        if (doc == null) return null;

        Pieza pieza = new Pieza();
        
        pieza.setId(super.idTexto(doc)); 
        pieza.setNombre(doc.getString("nombre"));
        pieza.setCategoria(doc.getString("categoria"));
        pieza.setMarcaPieza(doc.getString("marcaPieza"));
        pieza.setModeloPieza(doc.getString("modeloPieza"));
        pieza.setCostoPieza(doc.get("costoPieza", Double.class));
        pieza.setStockPieza(doc.getInteger("stockPieza"));

        return pieza;
    }

    public Document toDocument(Pieza pieza) {
        if (pieza == null) return null;

        Document doc = new Document();

        if (pieza.getId() != null && !pieza.getId().isEmpty()) {
            doc.put("_id", super.textoId(pieza.getId()));
        }

        doc.put("nombre", pieza.getNombre());
        doc.put("categoria", pieza.getCategoria());
        doc.put("marcaPieza", pieza.getMarcaPieza());
        doc.put("modeloPieza", pieza.getModeloPieza());
        doc.put("costoPieza", pieza.getCostoPieza());
        doc.put("stockPieza", pieza.getStockPieza());
        
        return doc;
    }
    
}
