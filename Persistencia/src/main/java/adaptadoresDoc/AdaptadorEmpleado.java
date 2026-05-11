package adaptadoresDoc;

import dominio.Empleado;
import org.bson.Document;

/**
 * Clase que adapta los documentos de los empleados a entidades
 * y viceversa (Hereda de la clase AdaptadorPersona).
 * 
 * @author aron
 */
public class AdaptadorEmpleado extends AdaptadorPersona{
    // Método que transforma un documento a una entidad Empleado
    public Empleado toEntity(Document doc) {
        Empleado empleado = new Empleado();

        empleado.setNombreUsuario(doc.getString("nombreUsuario"));
        empleado.setContrasenia(doc.getString("contrasenia"));

        super.llenarPersonaDesdeDoc(doc, empleado);

        return empleado;
    }

    // Método que transforma una entidad Empleado a un documento
    public Document toDocument(Empleado empleado) {
        Document doc = new Document();

        doc.put("nombreUsuario", empleado.getNombreUsuario());
        doc.put("contrasenia", empleado.getContrasenia());

        super.llenarDocDesdePersona(doc, empleado);

        return doc;
    }
}