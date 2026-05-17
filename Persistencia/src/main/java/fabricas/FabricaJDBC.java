package fabricas;

import conexiones.ConexionSQL;
import daos.IEmpleadoDAO;
import daos.IPiezaDAO;
import daos.ISolicitudDAO;
import daos.IVentaDAO;
import jdbc.EmpleadoJDBC;
import jdbc.PiezaJDBC;
import jdbc.SolicitudJDBC;
import jdbc.VentaJDBC;

/**
 * Fábrica encargada de suministrar DAOs que utilizan la
 * librería de JDBC. Para enchufar el proyecto con SQL, solo
 * se cambia por esta fábrica la instancia que posee la 
 * fábrica de BO
 * 
 * @author Andre
 */
public class FabricaJDBC implements IFabricaDAO {
    
    //Privados
    private static IFabricaDAO instancia;
    private FabricaJDBC() {}
    
    //Instancias de cada DAO de JDBC, implementando en la práctica un singleton
    private IPiezaDAO pieza;
    private IVentaDAO venta;
    private IEmpleadoDAO empleado;
    private ISolicitudDAO solicitud;
    
    /**
     * Singleton
     * 
     * @return la instancia única
     */
    public static IFabricaDAO singleton() {
        if (instancia == null) {
            instancia = new FabricaJDBC();
        }
        return instancia;
    }
    
    @Override
    public IPiezaDAO fabricarPieza() {
        if (pieza == null) {
            ConexionSQL conexion = new ConexionSQL();
            pieza = new PiezaJDBC(conexion);
        }
        return pieza;
    }
    
    @Override
    public IVentaDAO fabricarVenta() {
        if (venta == null) {
            ConexionSQL conexion = new ConexionSQL();
            venta = new VentaJDBC(conexion);
        }
        return venta;
    }
    
    @Override
    public IEmpleadoDAO fabricarEmpleado() {
        if (empleado == null) {
            ConexionSQL conexion = new ConexionSQL();
            empleado = new EmpleadoJDBC(conexion);
        }
        return empleado;
    }
    
    @Override
    public ISolicitudDAO fabricarSolicitud() {
        if (solicitud == null) {
            ConexionSQL conexion = new ConexionSQL();
            solicitud = new SolicitudJDBC(conexion);
        }
        return solicitud;
    }
}