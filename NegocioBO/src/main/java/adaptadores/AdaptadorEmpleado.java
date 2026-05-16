package adaptadores;

import DTOS.EmpleadoDTO;
import dominio.Empleado;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador estático para la conversión de Empleados entre Entidades y DTOs
 * @author Andre
 */
public final class AdaptadorEmpleado {
    
    // Constructor privado para evitar la instanciación de la clase de utilerías
    private AdaptadorEmpleado(){}
    
    /**
     * Convierte un EmpleadoDTO a una entidad Empleado.
     */
    public static Empleado Entidad(EmpleadoDTO dto) {
        if (dto == null) return null;
        
        Empleado entidad = new Empleado();
        
        AdaptadorPersona.EntidadPersona(dto, entidad);
        entidad.setNombreUsuario(dto.getNombreUsuario());
        
        return entidad;
    }

    /**
     * Convierte una entidad Empleado a un EmpleadoDTO.
     */
    public static EmpleadoDTO DTO(Empleado entidad) {
        if (entidad == null) return null;
        
        EmpleadoDTO dto = new EmpleadoDTO();
        
        AdaptadorPersona.DTOPersona(entidad, dto);
        dto.setNombreUsuario(entidad.getNombreUsuario());
        
        return dto;
    }

    /**
     * Convierte una lista de EmpleadoDTOs a una lista de entidades Empleado.
     */
    public static List<Empleado> listaEntidad(List<EmpleadoDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(AdaptadorEmpleado::Entidad).collect(Collectors.toList());
    }

    /**
     * Convierte una lista de entidades Empleado a una lista de EmpleadoDTOs.
     */
    public static List<EmpleadoDTO> listaDTO(List<Empleado> entidades) {
        if (entidades == null) return null;
        return entidades.stream().map(AdaptadorEmpleado::DTO).collect(Collectors.toList());
    }
}