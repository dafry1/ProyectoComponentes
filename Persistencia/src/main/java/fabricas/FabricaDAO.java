package fabricas;

import daos.EmpleadoDAO;
import daos.PiezaDAO;
import daos.VentaDAO;
import daos.IEmpleadoDAO;
import daos.IPiezaDAO;
import daos.ISolicitudDAO;
import daos.IVentaDAO;
import daos.SolicitudDAO;

/**
 * Suministra los DAO que contactan los BO
 * 
 * @author Andre
 */
public class FabricaDAO implements IFabricaDAO {

    //Privados
    private static FabricaDAO instancia;
    private FabricaDAO() {}
    
    //Fábrica auxiliar que suministra adaptadores entre entity y documento
    private IFabricaAdaptadores fabricaAdaptadores;

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

    @Override
    public ISolicitudDAO fabricarSolicitud() {
        return new SolicitudDAO();
    }
    
    
}