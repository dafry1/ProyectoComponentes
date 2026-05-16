package fabricas;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexiones.ConexionMongo;
import daos.EmpleadoDAO;
import daos.PiezaDAO;
import daos.VentaDAO;
import daos.IEmpleadoDAO;
import daos.IPiezaDAO;
import daos.ISolicitudDAO;
import daos.IVentaDAO;
import daos.SolicitudDAO;
import dominio.Empleado;
import dominio.Pieza;
import dominio.Solicitud;
import dominio.Venta;

/**
 * Maneja a los DAO del sistema en un lugar centralizado, por
 * lo que estos viven absurdamente ignorantes. No conocen sobre
 * su ciclo de vida (pues la fábrica ya maneja los singleton),
 * no saben cómo obtener sus herramientas. Simplemente nacen
 * recibiendo listos para trabajar
 * 
 * @author Andre
 */
public class FabricaDAO implements IFabricaDAO {

    //Instancia única de la base de datos
    private static final MongoDatabase BD = ConexionMongo.obtenerBD();
    
    //Instancias de los DAO
    private IPiezaDAO pieza;
    private IVentaDAO venta;
    private IEmpleadoDAO empleado;
    private ISolicitudDAO solicitud;
    
    //Strings que declaran una colección en específico
    private static final String PIEZAS = "piezas";
    private static final String VENTAS = "ventas";
    private static final String SOLICITUDES = "solicitudes";
    private static final String EMPLEADOS = "empleados";
    
    //Privados
    private static FabricaDAO instancia;
    private FabricaDAO() {}
    
    /**
     * Singleton
     * 
     * @return la instancia única
     */
    public static IFabricaDAO singleton() {
        if (instancia == null) {
            instancia = new FabricaDAO();
        }
        return instancia;
    }
    
    @Override
    public IPiezaDAO fabricarPieza() {
        if (pieza == null) {
            pieza = new PiezaDAO(BD.getCollection(PIEZAS, Pieza.class), BD.getCollection(VENTAS, Venta.class));
        }
        return pieza;
    }
    
    @Override
    public IVentaDAO fabricarVenta() {
        if (venta == null) {
            venta = new VentaDAO(BD.getCollection(VENTAS, Venta.class));
        }
        return venta;
    }
    
    @Override
    public IEmpleadoDAO fabricarEmpleado() {
        if (empleado == null) {
            empleado = new EmpleadoDAO(BD.getCollection(EMPLEADOS, org.bson.Document.class));
        }
        return empleado;
    }
    
    @Override
    public ISolicitudDAO fabricarSolicitud() {
        if (solicitud == null) {
            solicitud = new SolicitudDAO(BD.getCollection(SOLICITUDES, Solicitud.class));
        }
        return solicitud;
    }
}