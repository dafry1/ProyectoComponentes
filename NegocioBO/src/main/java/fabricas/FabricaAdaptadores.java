package fabricas;

import adaptadores.AdaptadorPieza;
import interfaces.IAdaptadorPieza;
import interfaces.IFabricaAdaptadores;

/**
 * Interfaz para una fábrica que suministra adaptadores
 * 
 * @author Andre
 */
public class FabricaAdaptadores implements IFabricaAdaptadores {

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
    
}
