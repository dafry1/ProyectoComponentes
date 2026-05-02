package fabricas;

import adaptadores.IAdaptadorVenta;
import adaptadores.IAdaptadorDetallesVenta;
import adaptadores.IAdaptadorPieza;
import adaptadores.IAdaptadorCliente;
import adaptadores.IAdaptadorEmpleado;

/**
 * Contrato para suministrar de interfaces los BO y
 * así puedan adaptar sin acoplamiento
 * 
 * @author Andre
 */
public interface IFabricaAdaptadores {
    
    /**
     * Establece que regresa un adaptador de pieza
     * 
     * @return el adaptador
     */
     IAdaptadorPieza fabricarAdaptadorPieza();  
    
     IAdaptadorVenta fabricarAdaptadorVenta();
    
     IAdaptadorCliente fabricarAdaptadorCliente();
    
     IAdaptadorEmpleado fabricarAdaptadorEmpleado();
    
    IAdaptadorDetallesVenta fabricarAdaptadorDetallesVenta();
}