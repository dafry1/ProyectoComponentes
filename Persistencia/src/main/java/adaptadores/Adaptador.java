package adaptadores;

import excepciones.PersistenciaException;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Superclase de adaptadores JSON-Dominio que
 * equipa a todos los adaptadores descendientes 
 * de métodos básicos, como adaptar el ID. Es
 * abstracto porque también se necesitan métodos
 * específicos para trabajar
 *  
 * @author Andre
 */
public abstract class Adaptador {
    private static final System.Logger LOG = System.getLogger(Adaptador.class.getName());
    
    //Atributo para depurar y excepciones. Valor por defecto
    String DEBUG = "Depuración";
    
    /**
     * Adapta una Id de mongo a un String
     * 
     * @param documento a extraer Id

     * @return Id en String
     */
    protected String idTexto(Document documento) {
        if (documento == null || !documento.containsKey("_id")) {
            DEBUG = "Id inválida";
            debugExcepcion(DEBUG);
        }
        ObjectId idMongo = documento.getObjectId("_id");
        if (idMongo != null) {
            return idMongo.toHexString();
        }
        return null;
    }
    
    /**
     * Convierte un String hexadecimal en un
     * ObjectId de Mongo
     * 
     * @param id de la entidad 
     * 
     * @return ObjectId de Mongo
     */
    protected ObjectId textoId(String id) {
        if (id == null || id.trim().isEmpty()) {
            DEBUG = "Id inválida";
            debugExcepcion(DEBUG);
        }
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException e) {
            DEBUG = "Error al convertir el Id";
            debugExcepcion(DEBUG);
        }
        return null;
    }
    
    /**
     * Depuración centralizada que muestra un 
     * logger y una excepción
     * 
     * @param mensaje 
     */
    protected void debugExcepcion(String mensaje) {
        LOG.log(System.Logger.Level.ERROR, mensaje);
        throw new PersistenciaException(mensaje);
    }
}