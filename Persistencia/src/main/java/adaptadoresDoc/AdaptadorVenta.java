package adaptadoresDoc;

import dominio.DetallesVenta;
import dominio.Empleado;
import dominio.Pieza;
import dominio.Venta;
import java.util.ArrayList;
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
    
    // Método que transforma un documento a una entidad Venta (de forma embebida)
    public Venta toEntityEmbebido(Document doc) {
        Venta venta = new Venta();
        
        Document clienteDoc = doc.get("cliente", Document.class);
        Document empleadoDoc = doc.get("empleado", Document.class);
        
        venta.setCliente(adaptadorCliente.toEntity(clienteDoc));
        
        Empleado empleado = new Empleado();
        
        empleado.setNombres(empleadoDoc.getString("nombres"));
        empleado.setApellidoPaterno(empleadoDoc.getString("apellidoPaterno"));
        empleado.setApellidoMaterno(empleadoDoc.getString("apellidoMaterno"));
        
        venta.setEmpleado(empleado);
        
        List<Document> detallesDoc = doc.getList("detalles", Document.class);

        List<DetallesVenta> detallesVenta = new ArrayList<>();
        
        if (detallesDoc != null) {
            for (Document detalleDoc : detallesDoc) {
                DetallesVenta detalleVenta = new DetallesVenta();
                
                detalleVenta.setCosto(Double.parseDouble(detalleDoc.getString("costo")));
                detalleVenta.setSubtotal(Double.parseDouble(detalleDoc.getString("subtotal")));
                detalleVenta.setCantidad(Integer.parseInt(detalleDoc.getString("cantidad")));
                
                Document piezaDoc = detalleDoc.get("pieza", Document.class);
                Pieza piezaDetalle = new Pieza();
                
                piezaDetalle.setNombre(piezaDoc.getString("nombre"));
                piezaDetalle.setMarcaPieza(piezaDoc.getString("marcaPieza"));
                piezaDetalle.setModeloPieza(piezaDoc.getString("modeloPieza"));
                piezaDetalle.setCostoPieza(Double.parseDouble(piezaDoc.getString("costoPieza")));
                piezaDetalle.setCategoria(piezaDoc.getString("categoria"));
                
                detalleVenta.setPieza(piezaDetalle);
                
                detallesVenta.add(detalleVenta);
            }
        }
        
        venta.setDetalles(detallesVenta);
        
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

        doc.put("total", String.valueOf(venta.getTotal()));
        doc.put("fechaHora", venta.getFechaHora());
        doc.put("folio", venta.getFolio());

        return doc;
    }
    
    // Método que transforma una entidad Venta a un documento (de forma embebida)
    public Document toDocumentEmbebido(Venta venta) {
        Document doc = new Document();

        doc.put("cliente", adaptadorCliente.toDocument(venta.getCliente()));

        Document empleadoDoc = new Document();
        empleadoDoc.put("nombres", venta.getEmpleado().getNombres());
        empleadoDoc.put("apellidoPaterno", venta.getEmpleado().getApellidoPaterno());
        empleadoDoc.put("apellidoMaterno", venta.getEmpleado().getApellidoMaterno());

        doc.put("empleado", empleadoDoc);

        if (venta.getDetalles() != null) {
            List<Document> detallesDoc = new ArrayList<>();
            for (DetallesVenta detalle : venta.getDetalles()) {
                Document detalleDoc = new Document();

                detalleDoc.put("costo", String.valueOf(detalle.getCosto()));
                detalleDoc.put("subtotal", String.valueOf(detalle.getSubtotal()));
                detalleDoc.put("cantidad", String.valueOf(detalle.getCantidad()));

                Document piezaDoc = new Document();
                piezaDoc.put("nombre", detalle.getPieza().getNombre());
                piezaDoc.put("marcaPieza", detalle.getPieza().getMarcaPieza());
                piezaDoc.put("modeloPieza", detalle.getPieza().getModeloPieza());
                piezaDoc.put("costoPieza", String.valueOf(detalle.getPieza().getCostoPieza()));
                piezaDoc.put("categoria", detalle.getPieza().getCategoria());

                detalleDoc.put("pieza", piezaDoc);

                detallesDoc.add(detalleDoc);
            }

            doc.put("detalles", detallesDoc);
        }

        doc.put("total", String.valueOf(venta.getTotal()));
        doc.put("fechaHora", venta.getFechaHora());
        doc.put("folio", venta.getFolio());

        return doc;
    }
}
