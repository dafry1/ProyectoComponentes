package fabricas;

import controles.ControlVentas;
import interfaces.IControlVentas;
import interfaces.IFabricaControlesVentas;

/**
 *
 * @author Andre
 */
public class FabricaControlesVentas implements IFabricaControlesVentas {
    private static FabricaControlesVentas instancia;
    private FabricaControlesVentas() {}
    
    public static FabricaControlesVentas singleton() {
        if (instancia == null) {
            instancia = new FabricaControlesVentas();
        }
        return instancia;
    }
    
    @Override
    public IControlVentas fabricarControlVentas() {
        return new ControlVentas();
    }
}