package fabricas;

import bo.IEmpleadoBO;
import bo.IPiezaBO;
import bo.ISolicitudBO;
import bo.IVentaBO;

/**
 * Interfaz de FabricaBO
 * 
 * @author Andre
 */
public interface IFabricaBO {
    
    /**
     * Fabrica el Bo de una pieza
     * 
     * @return el contrato de PiezaBO
     */
    IPiezaBO fabricarPieza();
    
    /**
     * Fabrica el BO de una venta
     * 
     * @return el contrato de VentaBO
     */
    IVentaBO fabricarVenta();
    
    /**
     * Fabrica el BO de un empleado
     * 
     * @return el contrato de EmpleadoBO
     */
    IEmpleadoBO fabricarEmpleado();
    
    ISolicitudBO fabricarSolicitud();
}