package adaptadores;

import DTOS.PersonaDTO;
import dominio.Persona;

/**
 *
 * @author Andre
 */
public abstract class AdaptadorPersona {

    //Método común para llenar los datos de Persona en cualquier DTO
    protected void DTOPersona(Persona entidad, PersonaDTO dto) {
        dto.setNombres(entidad.getNombres());
        dto.setApellidoPaterno(entidad.getApellidoPaterno());
        dto.setApellidoMaterno(entidad.getApellidoMaterno());
    }

    //Método común para llenar los datos de Persona en cualquier Entidad
    protected void EntidadPersona(PersonaDTO dto, Persona entidad) {
        entidad.setNombres(dto.getNombres());
        entidad.setApellidoPaterno(dto.getApellidoPaterno());
        entidad.setApellidoMaterno(dto.getApellidoMaterno());
    }
}
