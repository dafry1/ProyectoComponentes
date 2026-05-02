package adaptadores;

import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import dominio.Empleado;
import dominio.Pieza;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IAdaptadorEmpleado {

    Empleado Entidad(EmpleadoDTO dto);
    
    List<Empleado> listaEntidad(List<EmpleadoDTO> dtos);
    
    EmpleadoDTO DTO(Empleado entidad);
    
    List<EmpleadoDTO> listaDTO(List<Empleado> entidades);
}
