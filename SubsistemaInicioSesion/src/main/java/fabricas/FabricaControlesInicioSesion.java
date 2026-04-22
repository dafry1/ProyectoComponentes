package fabricas;

/**
 *
 * @author Andre
 */
public class FabricaControlesInicioSesion {
    
    private static FabricaControlesInicioSesion instancia;
    private FabricaControlesInicioSesion() {}
    
    public static FabricaControlesInicioSesion singleton() {
        if (instancia == null) {
            instancia = new FabricaControlesInicioSesion();
        }
        return instancia;
    }
    
    public FabricaControlesInicioSesion fabricarControlVentas() {
        return new FabricaControlesInicioSesion();
    }
}
