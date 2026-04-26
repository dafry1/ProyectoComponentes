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
    public IAdaptadorPieza fabricarAdaptadorPieza();  
    
    public IAdaptadorVenta fabricarAdaptadorVenta();
    
    public IAdaptadorCliente fabricarAdaptadorCliente();
    
    public IAdaptadorEmpleado fabricarAdaptadorEmpleado();
}