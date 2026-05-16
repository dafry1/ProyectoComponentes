package adaptadores;

import DTOS.PersonaDTO;
import dominio.Persona;

/**
 *
 * @author Andre
 */
public final class AdaptadorPersona {

    private AdaptadorPersona() {
    }

    
    //Método común para llenar los datos de Persona en cualquier DTO
    protected static void DTOPersona(Persona entidad, PersonaDTO dto) {
        dto.setNombres(entidad.getNombres());
        dto.setApellidoPaterno(entidad.getApellidoPaterno());
        dto.setApellidoMaterno(entidad.getApellidoMaterno());
    }

    //Método común para llenar los datos de Persona en cualquier Entidad
    protected static void EntidadPersona(PersonaDTO dto, Persona entidad) {
        entidad.setNombres(dto.getNombres());
        entidad.setApellidoPaterno(dto.getApellidoPaterno());
        entidad.setApellidoMaterno(dto.getApellidoMaterno());
    }
}
