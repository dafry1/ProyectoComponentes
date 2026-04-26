package interfaces;

/**
 * Establece el contrato sobre cómo debería suministrar
 * una fábrica todos los DAO del sistema
 * 
 * @author Andre
 */
public interface IFabricaDAO {
    
    /**
     * Fabrica el DAO de una pieza
     * 
     * @return el contrato de PiezaDAO
     */
    IPiezaDAO fabricarPieza();
    
    /**
     * Fabrica el DAO de una venta
     * 
     * @return el contrato de VentaDAO
     */
    IVentaDAO fabricarVenta();
    
    /**
     * Fabrica el DAO de un empleado
     * 
     * @return el contrato de EmpleadoDAO
     */
    IEmpleadoDAO fabricarEmpleado();
}
