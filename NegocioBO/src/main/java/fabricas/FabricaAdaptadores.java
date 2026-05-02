package fabricas;

import adaptadores.AdaptadorCliente;
import adaptadores.AdaptadorDetallesVenta;
import adaptadores.AdaptadorEmpleado;
import adaptadores.AdaptadorPieza;
import adaptadores.AdaptadorVenta;
import adaptadores.IAdaptadorCliente;
import adaptadores.IAdaptadorDetallesVenta;
import adaptadores.IAdaptadorEmpleado;
import adaptadores.IAdaptadorPieza;
import adaptadores.IAdaptadorVenta;

/**
 * Interfaz para una fábrica que suministra adaptadores
 * 
 * @author Andre
 */
public class FabricaAdaptadores implements IFabricaAdaptadores {
    private IAdaptadorVenta instanciaVenta;
    
    private static IFabricaAdaptadores instancia;
    private FabricaAdaptadores(){}
    
    /**
     * Singleton de la fábrica
     * 
     * @return la única instancia
     */
    public static IFabricaAdaptadores singleton() {
        if (instancia == null) {
            instancia = new FabricaAdaptadores();
        }
        return instancia;
    }
    
    /**
     * Regresa un adaptador de pieza
     * 
     * @return el adaptador
     */
    @Override
    public IAdaptadorPieza fabricarAdaptadorPieza() {
        return AdaptadorPieza.singleton();
    }

    /**
     * Regresa un adaptador de venta, usando un tipo
     * singleton que solo maneja esta fábrica
     * 
     * @return 
     */
    @Override
    public IAdaptadorVenta fabricarAdaptadorVenta() {
        if (instanciaVenta == null) {
            instanciaVenta = new AdaptadorVenta(fabricarAdaptadorEmpleado(), fabricarAdaptadorCliente(), fabricarAdaptadorDetallesVenta());
        }
        return instanciaVenta;
    }

    @Override
    public IAdaptadorCliente fabricarAdaptadorCliente() {
        return AdaptadorCliente.singleton();
    }

    @Override
    public IAdaptadorEmpleado fabricarAdaptadorEmpleado() {
        return AdaptadorEmpleado.singleton();
    }
    
    @Override
    public IAdaptadorDetallesVenta fabricarAdaptadorDetallesVenta() {
        return new AdaptadorDetallesVenta(fabricarAdaptadorPieza());
    }
}
