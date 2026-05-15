package daos;

import adaptadoresDoc.AdaptadorCliente;
import adaptadoresDoc.AdaptadorDetallesVenta;
import adaptadoresDoc.AdaptadorEmpleado;
import adaptadoresDoc.AdaptadorVenta;
import adaptadoresDoc.PiezaDoc;
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
 *
 * @author Andre
 */
public class VentaDAO implements IVentaDAO {

    private static final System.Logger LOG = System.getLogger(VentaDAO.class.getName());
    
    private static List<Venta> VENTAS = new ArrayList<>();
    
    private static final AdaptadorCliente adaptadorCliente = new AdaptadorCliente();
    private static final AdaptadorEmpleado adaptadorEmpleado = new AdaptadorEmpleado();
    private static final AdaptadorDetallesVenta adaptadorDetallesVenta = new AdaptadorDetallesVenta(PiezaDoc.singleton());
    private static final AdaptadorVenta adaptadorVenta = new AdaptadorVenta(adaptadorCliente, adaptadorEmpleado, adaptadorDetallesVenta);
    
    private MongoCollection<Venta> coleccion;
    
    /**
     * Constructor
     *
     * @param coleccion
     */
    public VentaDAO(MongoCollection coleccion) {
        this.coleccion = coleccion;
    }
    
    /**
     * Extrae todas las ventas de la BD
     *
     * @return lista de ventas
     */
    @Override
    public List<Venta> consultarVentas() {
        List<Venta> lista = new ArrayList<>();
        for (Document doc : coleccion.find(new Document(), Document.class)) {
            lista.add(adaptadorVenta.toEntity(doc));
        }
        return lista;
    }
    
    /**
     * Muestra la cantidad de ventas registradas el mismo dia de la consulta en la BD
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

    @Override
    public Venta registrarVenta(Venta venta) {
        if (venta == null) {
            LOG.log(System.Logger.Level.WARNING, "Se intentó registrar una venta nula o vacía");
            return null;
        }
        try {
            InsertOneResult resultado = coleccion.insertOne(venta);
            if (resultado.getInsertedId() != null) {
                return venta;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "Error al registrar la venta " + e.getMessage());
            throw new PersistenciaException("No se registrar la venta correctamente.");
        }
    }
}