package controles;

import DTOS.EmpleadoDTO;
import fabricas.FabricaBO;
import interfaces.IFabricaBO;
import interfaces.IEmpleadoBO;
import java.util.List;

/**
 * Control que accede directamente a los BO encargados
 * del inicio de sesión de un empleado
 * 
 * @author Andre
 */
public class ControlSesion {
    
    //BOs necesarios
    private final IEmpleadoBO empleadoBO;
    
    /**
     * Constructor con fábrica inyectada
     * 
     * @param fabricaBO que suministra
     */
    public ControlSesion(IFabricaBO fabricaBO) {
        this.empleadoBO = fabricaBO.fabricarEmpleado();
    }
    
    /**
     * Llama a empleadoBO para buscar un empleado en específico
     * 
     * @param nombreUsuario del empleado
     * @param contra del empleado
     * 
     * @return el empleado
     */
    public EmpleadoDTO verificarEmpleado(String nombreUsuario, String contra) {
        return empleadoBO.verificarEmpleado(nombreUsuario, contra);
    }
}