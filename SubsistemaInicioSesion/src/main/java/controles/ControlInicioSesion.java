package controles;

import DTOS.EmpleadoDTO;
import fabricas.FabricaBO;
import interfaces.IControlInicioSesion;
import interfaces.IFabricaBO;
import interfaces.IEmpleadoBO;
import java.util.List;

/**
 * Control que accede directamente a los BO encargados
 * del inicio de sesión de un empleado
 * 
 * @author Andre
 */
public class ControlInicioSesion implements IControlInicioSesion {
    
    //BOs necesarios
    private final IEmpleadoBO empleadoBO;
    
    /** Constructor que se autoinyecta dependencias gracias a la fábrica */
    public ControlInicioSesion() {
        IFabricaBO fabricaBO = FabricaBO.singleton();
        this.empleadoBO = fabricaBO.fabricarEmpleado();
    }
    
    /**
     * Llama al BO para consultar todos los empleados
     * 
     * @return la lista de empleados
     */
    @Override
    public List<EmpleadoDTO> consultarEmpleados() {
        return empleadoBO.consultarEmpleados();
    }
    
    /**
     * Llama a empleadoBO para buscar un empleado en específico
     * 
     * @param nombreUsuario del empleado
     * @param contra del empleado
     * 
     * @return el empleado
     */
    @Override
    public EmpleadoDTO verificarEmpleado(String nombreUsuario, String contra) {
        return empleadoBO.verificarEmpleado(nombreUsuario, contra);
    }
}