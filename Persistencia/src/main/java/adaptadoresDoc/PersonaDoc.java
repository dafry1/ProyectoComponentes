package adaptadoresDoc;

import dominio.Persona;
import org.bson.Document;

/**
 * Clase de utilería auxiliar para rellenar la información de los
 * documentos o entidades de las personas (Cliente o Empleado)
 * mediante métodos estáticos puros.
 * 
 * @author aron
 * @author Andre
 */
public final class PersonaDoc {

    // Constructor privado para evitar que se instancie la clase de utilerías
    private PersonaDoc() {
        throw new UnsupportedOperationException("Clase de utilerías. No se permite instanciación.");
    }
    
    /**
     * Llena una instancia existente de Persona (o sus subclases) desde un documento.
     */
    public static void llenarPersonaDesdeDoc(Document doc, Persona persona){
        if (doc == null || persona == null) return;

        persona.setNombres(doc.getString("nombres"));
        persona.setApellidoPaterno(doc.getString("apellidoPaterno"));
        persona.setApellidoMaterno(doc.getString("apellidoMaterno"));
    }
    
    /**
     * Llena una instancia existente de Document desde una clase Persona.
     */
    public static void llenarDocDesdePersona(Document doc, Persona persona){
        if (doc == null || persona == null) return;

        doc.put("nombres", persona.getNombres());
        doc.put("apellidoPaterno", persona.getApellidoPaterno());
        doc.put("apellidoMaterno", persona.getApellidoMaterno());
    }
}