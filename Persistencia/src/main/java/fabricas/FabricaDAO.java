package fabricas;

import daos.EmpleadoDAO;
import daos.PiezaDAO;
import daos.VentaDAO;
import interfaces.IEmpleadoDAO;
import interfaces.IFabricaDAO;
import interfaces.IPiezaDAO;
import interfaces.IVentaDAO;

/**
 * Suministra los DAO que contactan los BO
 * 
 * @author Andre
 */
public class FabricaDAO implements IFabricaDAO {

    //Privados
    private static FabricaDAO instancia;
    private FabricaDAO() {}

    /**
     * Singleton
     * 
     * @return la instancia única
     */
    public static FabricaDAO singleton() {
        if (instancia == null) {
            instancia = new FabricaDAO();
        }
        return instancia;
    }
    
    @Override
    public IPiezaDAO fabricarPieza() {
        return new PiezaDAO();
    }

    @Override
    public IVentaDAO fabricarVenta() {
        return new VentaDAO();
    }

    @Override
    public IEmpleadoDAO fabricarEmpleado() {
        return new EmpleadoDAO();
    }
}