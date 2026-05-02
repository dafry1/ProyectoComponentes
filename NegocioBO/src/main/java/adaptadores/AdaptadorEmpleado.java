package adaptadores;

import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import dominio.Empleado;
import dominio.Pieza;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public class AdaptadorEmpleado extends AdaptadorPersona implements IAdaptadorEmpleado {
    private static IAdaptadorEmpleado instancia;
    private AdaptadorEmpleado(){}
    
    /**
     * Singleton del adaptador
     * 
     * @return la única instancia
     */
    public static IAdaptadorEmpleado singleton() {
        if (instancia == null) {
            instancia = new AdaptadorEmpleado();
        }
        return instancia;
    }
    
    @Override
    public Empleado Entidad(EmpleadoDTO dto) {
        Empleado entidad = new Empleado();
        super.EntidadPersona(dto, entidad);
        entidad.setNombreUsuario(dto.getNombreUsuario());
        entidad.setContrasenia(dto.getContrasenia());
        return entidad;
    }

    @Override
    public EmpleadoDTO DTO(Empleado entidad) {
        EmpleadoDTO dto = new EmpleadoDTO();
        super.DTOPersona(entidad, dto);
        dto.setNombreUsuario(entidad.getNombreUsuario());
        dto.setContrasenia(entidad.getContrasenia());
        return dto;
    }

    @Override
    public List<Empleado> listaEntidad(List<EmpleadoDTO> dtos) {
        return dtos.stream().map(this::Entidad).collect(Collectors.toList());
    }

    @Override
    public List<EmpleadoDTO> listaDTO(List<Empleado> entidades) {
        return entidades.stream().map(this::DTO).collect(Collectors.toList());
    }
}