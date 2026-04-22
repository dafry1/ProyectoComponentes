package interfaces;

import DTOS.EmpleadoDTO;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IControlInicioSesion {
    /**
     * Llama al BO para consultar todos los empleados
     * 
     * @return la lista de empleados
     */
    List<EmpleadoDTO> consultarEmpleados();
    
    /**
     * Llama a empleadoBO para buscar un empleado en específico
     * 
     * @param nombreUsuario del empleado
     * @param contra del empleado
     * 
     * @return el empleado
     */
    EmpleadoDTO verificarEmpleado(String nombreUsuario, String contra);
}