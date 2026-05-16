package adaptadoresDoc;

import excepciones.PersistenciaException;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Superclase de adaptadores JSON-Dominio que
 * equipa a todos los adaptadores descendientes 
 * de métodos básicos, como adaptar el ID.
 *  
 * @author Andre
 */
public final class AdaptadorDoc {
    private AdaptadorDoc(){}
    private static final System.Logger LOG = System.getLogger(AdaptadorDoc.class.getName());
    
    // Atributo para depurar y excepciones. Valor por defecto
    private static String DEBUG = "Depuración";
    
    /**
     * Adapta un Id de mongo a un String de forma segura.
     * Si el documento es nulo o no contiene un _id (como en subdocumentos embebidos),
     * retorna null en lugar de romper la ejecución de la app.
     * 
     * @param documento a extraer Id
     * @return Id en String, o null si no cuenta con un _id
     */
    protected static String idTexto(Document documento) {
        if (documento == null) {
            return null;
        }
        
        // Si no tiene _id, no lanzamos excepción porque puede ser un objeto embebido (ej. Pieza en Detalle)
        if (!documento.containsKey("_id")) {
            return null; 
        }
        
        Object id = documento.get("_id");
        if (id != null) {
            return id.toString();
        }
        
        return null;
    }
    
    /**
     * Convierte un String hexadecimal en un
     * ObjectId de Mongo. Solo lanza excepción si el ID es obligatorio
     * y viene corrupto.
     * 
     * @param id de la entidad 
     * @return ObjectId de Mongo o null si el id provisto era vacío/nulo
     */
    protected static ObjectId textoId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException e) {
            DEBUG = "Error al convertir el Id: Formato hexadecimal inválido (" + id + ")";
            debugExcepcion();
        }
        return null;
    }
    
    /**
     * Depuración centralizada que muestra un 
     * logger y una excepción
     */
    protected static void debugExcepcion() {
        LOG.log(System.Logger.Level.ERROR, DEBUG);
        throw new PersistenciaException(DEBUG);
    }
}