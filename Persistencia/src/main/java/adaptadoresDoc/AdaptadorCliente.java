package adaptadoresDoc;

import dominio.Cliente;
import org.bson.Document;

/**
 * Clase que adapta los documentos de los clientes a entidades
 * y viceversa (Hereda de la clase AdaptadorPersona).
 * 
 * @author aron
 */
public class AdaptadorCliente extends AdaptadorPersona{
    // Método que transforma un documento a una entidad Cliente
    public Cliente toEntity(Document doc) {
        Cliente cliente = new Cliente();

        cliente.setCorreo(doc.getString("correo"));
        cliente.setTelefono(doc.getString("telefono"));

        super.llenarPersonaDesdeDoc(doc, cliente);

        return cliente;
    }

    // Método que transforma una entidad Cliente a un documento
    public Document toDocument(Cliente cliente) {
        Document doc = new Document();

        doc.put("correo", cliente.getCorreo());
        doc.put("telefono", cliente.getTelefono());

        super.llenarDocDesdePersona(doc, cliente);

        return doc;
    }
}