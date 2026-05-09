package fabricas;

import com.mongodb.client.MongoClient;
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
import dominio.Pieza;

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
    
    //Strings que declaran una colección en específico
    private static final String PIEZAS = "piezas";
    
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
            pieza = new PiezaDAO(BD.getCollection(PIEZAS, Pieza.class));
        }
        return pieza;
    }
    private IPiezaDAO pieza;

    
    
    
    @Override
    public IVentaDAO fabricarVenta() {
        return new VentaDAO();
    }

    @Override
    public IEmpleadoDAO fabricarEmpleado() {
        return new EmpleadoDAO();
    }

    @Override
    public ISolicitudDAO fabricarSolicitud() {
        return new SolicitudDAO();
    }
}