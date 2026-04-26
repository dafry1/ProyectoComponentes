package fabricas;

import adaptadores.AdaptadorPieza;
import interfaces.IFabricaBO;
import bo.EmpleadoBO;
import bo.PiezaBO;
import bo.VentaBO;
import interfaces.IAdaptadorPieza;
import interfaces.IEmpleadoBO;
import interfaces.IFabricaAdaptadores;
import interfaces.IFabricaDAO;
import interfaces.IPiezaBO;
import interfaces.IVentaBO;

/**
 * Clase que implementa el patrón Fabrica y se encarga
 * de suministrar a toda la capa de negocio de los BO
 * necesarios en forma de contratos de interfaces. También
 * está respaldada por una interfaz ya para que amarre 🚬
 * 
 * @author Andre
 */
public class FabricaBO implements IFabricaBO {
    private static IFabricaBO instancia;
    private FabricaBO() {}
    
    //Fábrica que suministra los DAO
    private IFabricaDAO fabricaDAO = FabricaDAO.singleton();
    
    //Fábrica que suministra los adaptadores
    private IFabricaAdaptadores fabricaAdaptadores = FabricaAdaptadores.singleton();
    
    /**
     * Singleton de la fábrica
     * 
     * @return la única instancia
     */
    public static IFabricaBO singleton() {
        if (instancia == null) {
            instancia = new FabricaBO();
        }
        return instancia;
    }
    
    /**
     * Fabrica el Bo de una pieza
     * 
     * @return el contrato de PiezaBO
     */
    @Override
    public IPiezaBO fabricarPieza() {
        return new PiezaBO(fabricaDAO.fabricarPieza(), fabricaAdaptadores.fabricarAdaptadorPieza());
    }
    
    /**
     * Fabrica el BO de una venta
     * 
     * @return el contrato de VentaBO
     */
    @Override
    public IVentaBO fabricarVenta() {
        return new VentaBO(fabricaDAO.fabricarVenta(), fabricaAdaptadores.fabricarAdaptadorVenta());
    }
    
    /**
     * Fabrica el BO de un empleado
     * 
     * @return el contrato de EmpleadoBO
     */
    @Override
    public IEmpleadoBO fabricarEmpleado() {
        return new EmpleadoBO();
    }
}