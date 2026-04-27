package fabricas;

import interfaces.IFabricaBO;
import bo.EmpleadoBO;
import bo.PiezaBO;
import bo.VentaBO;
import interfaces.IEmpleadoBO;
import interfaces.IFabricaAdaptadores;
import interfaces.IFabricaDAO;
import interfaces.IPiezaBO;
import interfaces.IVentaBO;

/**
 * Clase que implementa el patrón Fabrica y se encarga
 * de suministrar a toda la capa de negocio de los BO
 * necesarios en forma de contratos de interfaces. Maneja
 * el patrón singleton para los BO, cuidando de que cada
 * uno tenga una única instancia en todo el programa
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
    
    //Instancias de cada BO, implementando en la práctica un singleton
    private IPiezaBO instanciaPieza;
    private IVentaBO instanciaVenta;
    private IEmpleadoBO instanciaEmpleado;
    
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
        if (instanciaPieza == null) {
            instanciaPieza = new PiezaBO(
                    fabricaDAO.fabricarPieza(), 
                    fabricaAdaptadores.fabricarAdaptadorPieza(), 
                    fabricaAdaptadores.fabricarAdaptadorDetallesVenta()
                );
        }
        return instanciaPieza;
    }
    
    /**
     * Fabrica el BO de una venta
     * 
     * @return el contrato de VentaBO
     */
    @Override
    public IVentaBO fabricarVenta() {
        if (instanciaVenta == null) {
            instanciaVenta = new VentaBO(fabricaDAO.fabricarVenta(), fabricaAdaptadores.fabricarAdaptadorVenta());
        }
        return instanciaVenta;
    }
    
    /**
     * Fabrica el BO de un empleado
     * 
     * @return el contrato de EmpleadoBO
     */
    @Override
    public IEmpleadoBO fabricarEmpleado() {
        if (instanciaEmpleado == null) {
            instanciaEmpleado = new EmpleadoBO(fabricaDAO.fabricarEmpleado(), fabricaAdaptadores.fabricarAdaptadorEmpleado());
        }
        return instanciaEmpleado;
    }
}