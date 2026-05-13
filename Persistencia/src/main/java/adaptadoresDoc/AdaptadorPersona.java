package adaptadoresDoc;

import dominio.Persona;
import org.bson.Document;

/**
 * Adaptador auxiliar para rellenar la información de los
 * documentos o entidades de las personas, ya sea cliente
 * o empleado (Hereda de la clase Adaptador).
 * babayii
 * @author aron
 */
public class AdaptadorPersona extends Adaptador{
    // Método que llena una clase Persona desde un documento
    public void llenarPersonaDesdeDoc(Document doc, Persona persona){
        persona.setNombres(doc.getString("nombres"));
        persona.setApellidoPaterno(doc.getString("apellidoPaterno"));
        persona.setApellidoMaterno(doc.getString("apellidoMaterno"));
    }
    
    // Método que llena un documento desde una clase Persona
    public void llenarDocDesdePersona(Document doc, Persona persona){
        doc.put("nombres", persona.getNombres());
        doc.put("apellidoPaterno", persona.getApellidoPaterno());
        doc.put("apellidoMaterno", persona.getApellidoMaterno());
    }
}