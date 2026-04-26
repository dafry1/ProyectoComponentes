package interfaces;

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