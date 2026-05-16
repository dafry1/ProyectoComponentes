/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadoresDoc;

import dominio.Pieza;
import org.bson.Document;

/**
 * Clase adaptadora flexible capaz de mapear piezas con ObjectIds de Mongo
 * o con IDs numéricos/secuenciales en formato String de APIs externas.
 * 
 * @author Andre
 */
public final class PiezaDoc {
    private PiezaDoc(){}
    
    // Método que transforma un documento a una entidad Pieza
    public static Pieza toEntity(Document doc) {
        if (doc == null) return null;

        Pieza pieza = new Pieza();
        
        // Si el _id es un String simple, doc.get("_id") puede fallar si idTexto espera un ObjectId.
        // Lo resolvemos extrayendo el valor de forma segura.
        Object idRaw = doc.get("_id");
        if (idRaw != null) {
            pieza.setId(idRaw.toString());
        } else {
            pieza.setId(AdaptadorDoc.idTexto(doc)); 
        }
        
        pieza.setNombre(doc.getString("nombre"));
        pieza.setCategoria(doc.getString("categoria"));
        pieza.setMarcaPieza(doc.getString("marcaPieza"));
        pieza.setModeloPieza(doc.getString("modeloPieza"));
        
        Number costo = doc.get("costoPieza", Number.class);
        pieza.setCostoPieza(costo != null ? costo.doubleValue() : 0.0);
        
        pieza.setStockPieza(doc.getInteger("stockPieza") != null ? doc.getInteger("stockPieza") : 0);

        return pieza;
    }

    // Método que transforma un documento a una entidad Pieza (de forma embebida)
    public static Pieza toEntityEmbebido(Document doc) {
        if (doc == null) return null;

        Pieza pieza = new Pieza();
        
        Object idRaw = doc.get("_id");
        if (idRaw != null) {
            pieza.setId(idRaw.toString());
        } else {
            pieza.setId(AdaptadorDoc.idTexto(doc)); 
        }
        
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
            String idLimpio = pieza.getId().trim();
            
            // FILTRO DE FLEXIBILIDAD: ¿Es un ObjectId legítimo de MongoDB? (24 caracteres hex)
            if (idLimpio.length() == 24 && idLimpio.matches("^[0-9a-fA-F]{24}$")) {
                doc.put("_id", AdaptadorDoc.textoId(idLimpio)); // Lo convierte a org.bson.types.ObjectId
            } else {
                doc.put("_id", idLimpio); // Lo guarda directo como String plano ("1", "2", etc.)
            }
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
            String idLimpio = pieza.getId().trim();
            
            // FILTRO DE FLEXIBILIDAD: El mismo comportamiento para los detalles embebidos
            if (idLimpio.length() == 24 && idLimpio.matches("^[0-9a-fA-F]{24}$")) {
                doc.put("_id", AdaptadorDoc.textoId(idLimpio));
            } else {
                doc.put("_id", idLimpio);
            }
        }

        doc.put("nombre", pieza.getNombre());
        doc.put("categoria", pieza.getCategoria());
        doc.put("marcaPieza", pieza.getMarcaPieza());
        doc.put("modeloPieza", pieza.getModeloPieza());
        doc.put("costoPieza", pieza.getCostoPieza());
        
        return doc;
    }
}