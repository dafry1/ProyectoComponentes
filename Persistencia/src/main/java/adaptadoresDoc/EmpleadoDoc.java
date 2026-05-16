package adaptadoresDoc;

import dominio.Empleado;
import org.bson.Document;

/**
 * Clase de utilería que adapta los documentos de los empleados a entidades
 * y viceversa mediante métodos estáticos puros.
 * 
 * @author aron
 * @author Andre
 */
public final class EmpleadoDoc {

    // Constructor privado para evitar la instanciación
    private EmpleadoDoc() {
        throw new UnsupportedOperationException("Clase de utilerías. No se permite instanciación.");
    }

    /**
     * Transforma un documento de MongoDB a una entidad Empleado completa.
     */
    public static Empleado toEntity(Document doc) {
        if (doc == null) return null;
        
        Empleado empleado = new Empleado();

        empleado.setId(AdaptadorDoc.idTexto(doc));
        empleado.setNombreUsuario(doc.getString("nombreUsuario"));
        empleado.setContrasenia(doc.getString("contrasenia"));

        PersonaDoc.llenarPersonaDesdeDoc(doc, empleado);

        return empleado;
    }
    
    /**
     * Transforma un documento de MongoDB a una entidad Empleado (de forma embebida).
     */
    public static Empleado toEntityEmbebido(Document doc) {
        if (doc == null) return null;
        
        Empleado empleado = new Empleado();

        PersonaDoc.llenarPersonaDesdeDoc(doc, empleado);

        return empleado;
    }

    /**
     * Transforma una entidad Empleado a un documento de MongoDB completo.
     */
    public static Document toDocument(Empleado empleado) {
        if (empleado == null) return null;
        
        Document doc = new Document();

        if (empleado.getId() != null && !empleado.getId().isEmpty()) {
            doc.put("_id", AdaptadorDoc.textoId(empleado.getId()));
        }
        
        doc.put("nombreUsuario", empleado.getNombreUsuario());
        doc.put("contrasenia", empleado.getContrasenia());

        // Uso de la red de estáticos para volcar datos base de persona al documento
        PersonaDoc.llenarDocDesdePersona(doc, empleado);

        return doc;
    }

    /**
     * Transforma una entidad Empleado a un documento de MongoDB (de forma embebida).
     */
    public static Document toDocumentEmbebido(Empleado empleado) {
        if (empleado == null) return null;
        
        Document doc = new Document();

        // En la versión embebida del documento solo guardamos la información básica
        PersonaDoc.llenarDocDesdePersona(doc, empleado);

        return doc;
    }
}