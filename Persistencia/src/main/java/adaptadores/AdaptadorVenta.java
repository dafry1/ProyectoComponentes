package adaptadores;

import dominio.DetallesVenta;
import dominio.Venta;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;

/**
 * Clase que adapta los documentos de las ventas a
 * entidades y viceversa (Hereda de la clase Adaptador).
 * 
 * @author aron
 */
public class AdaptadorVenta extends Adaptador{
    private AdaptadorCliente adaptadorCliente;
    private AdaptadorEmpleado adaptadorEmpleado;
    private AdaptadorDetallesVenta adaptadorDetallesVenta;
    
    public AdaptadorVenta(AdaptadorCliente adaptadorCliente,
            AdaptadorEmpleado adaptadorEmpleado,
            AdaptadorDetallesVenta adaptadorDetallesVenta){
        this.adaptadorCliente = adaptadorCliente;
        this.adaptadorEmpleado = adaptadorEmpleado;
        this.adaptadorDetallesVenta = adaptadorDetallesVenta;
    }
    
    // Método que transforma un documento a una entidad Venta
    public Venta toEntity(Document doc) {
        Venta venta = new Venta();
        
        Document clienteDoc = doc.get("cliente", Document.class);
        Document empleadoDoc = doc.get("empleado", Document.class);
        
        venta.setCliente(adaptadorCliente.toEntity(clienteDoc));
        venta.setEmpleado(adaptadorEmpleado.toEntity(empleadoDoc));
        
        List<Document> detallesDoc = doc.getList("detalles", Document.class);

        if (detallesDoc != null) {
            List<DetallesVenta> detallesVenta = detallesDoc.stream()
                    .map(adaptadorDetallesVenta::toEntity)
                    .collect(Collectors.toList());

            venta.setDetalles(detallesVenta);
        }
        
        venta.setTotal(Double.parseDouble(doc.getString("total")));
        venta.setFechaHora(doc.getString("fechaHora"));
        venta.setFolio(doc.getString("folio"));
        
        return venta;
    }

    // Método que transforma una entidad Venta a un documento
    public Document toDocument(Venta venta) {
        Document doc = new Document();

        doc.put("cliente", adaptadorCliente.toDocument(venta.getCliente()));
        doc.put("empleado", adaptadorEmpleado.toDocument(venta.getEmpleado()));
        if (venta.getDetalles() != null) {
            List<Document> detallesVentaDoc = venta.getDetalles().stream()
                    .map(adaptadorDetallesVenta::toDocument)
                    .collect(Collectors.toList());

            doc.put("detalles", detallesVentaDoc);
        }
        
        doc.put("total", venta.getTotal());
        doc.put("fechaHora", venta.getFechaHora());
        doc.put("folio", venta.getFolio());
        
        return doc;
    }
}
