package controles;

import DTOS.EmpleadoDTO;
import interfaces.IEmpleadoBO;
import interfaces.IFabricaBO;
import java.util.List;

/**
 * Control que conoce y sabe cómo extraer los empleados
 * de la base de datos
 * 
 * @author Andre
 */
public class ControlEmpleados {
    
    IEmpleadoBO empleadoBO;
    
    /**
     * Constructor que inyecta la fábrica
     * 
     * @param fabricaBO que suministra los BO
     */
    public ControlEmpleados(IFabricaBO fabricaBO) {
        this.empleadoBO = fabricaBO.fabricarEmpleado();
    }
    
    /**
     * Llama al BO para consultar todos los empleados
     * 
     * @return la lista de empleados
     */
    public List<EmpleadoDTO> consultarEmpleados() {
        return empleadoBO.consultarEmpleados();
    }
}
