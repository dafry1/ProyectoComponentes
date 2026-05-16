package daos;

import adaptadoresDoc.VentaDoc;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import dominio.Venta;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * Acceso a datos para la entidad Venta utilizando MongoDB
 * y la red de adaptadores estáticos.
 * 
 * @author Andre
 */
public class VentaDAO implements IVentaDAO {

    private static final System.Logger LOG = System.getLogger(VentaDAO.class.getName());
    
    private final MongoCollection<Document> coleccion;
    
    /**
     * Constructor que recibe la colección de MongoDB.
     *
     * @param coleccion Colección de la base de datos
     */
    public VentaDAO(MongoCollection coleccion) {
        this.coleccion = coleccion;
    }
    
    /**
     * Extrae todas las ventas de la BD.
     *
     * @return lista de ventas mapeadas a objetos de dominio
     */
    @Override
    public List<Venta> consultarVentas() {
        List<Venta> lista = new ArrayList<>();
        
        for (Document doc : coleccion.find(new Document(), Document.class)) {
            lista.add(VentaDoc.toEntity(doc));
        }
        
        return lista;
    }
    
    /**
     * Muestra la cantidad de ventas registradas el mismo día de la consulta en la BD
     *
     * @return cantidad de ventas
     */
    @Override
    public int cantidadVentas() {
        String hoy = LocalDate.now().toString();
        return (int) coleccion.countDocuments(
                Filters.regex("fechaHora", "^" + hoy)
        );
    }

    /**
     * Registra una nueva venta en la base de datos transformándola a Document.
     */
    @Override
    public Venta registrarVenta(Venta venta) {
        if (venta == null) {
            LOG.log(System.Logger.Level.WARNING, "Se intentó registrar una venta nula o vacía");
            return null;
        }
        try {
            Document documentoVenta = VentaDoc.toDocument(venta);
            InsertOneResult resultado = coleccion.insertOne(documentoVenta);
            if (resultado.getInsertedId() != null) {
                if (venta.getId() == null || venta.getId().isEmpty()) {
                    venta.setId(resultado.getInsertedId().asObjectId().getValue().toString());
                }
                return venta;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "Error al registrar la venta: " + e.getMessage(), e);
            throw new PersistenciaException("No se pudo registrar la venta correctamente.");
        }
    }
}