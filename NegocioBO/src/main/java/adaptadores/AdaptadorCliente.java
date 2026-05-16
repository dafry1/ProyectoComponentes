package adaptadores;

import DTOS.ClienteDTO;
import dominio.Cliente;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public final class AdaptadorCliente {
    private AdaptadorCliente(){}
    
    public static Cliente Entidad(ClienteDTO dto) {
        Cliente entidad = new Cliente();
        AdaptadorPersona.EntidadPersona(dto, entidad);
        entidad.setTelefono(dto.getTelefono());
        entidad.setCorreo(dto.getCorreo());
        return entidad;
    }

    public static ClienteDTO DTO(Cliente entidad) {
        ClienteDTO dto = new ClienteDTO();
        AdaptadorPersona.DTOPersona(entidad, dto);
        dto.setTelefono(entidad.getTelefono());
        dto.setCorreo(entidad.getCorreo());
        return dto;
    }

    public static List<Cliente> listaEntidad(List<ClienteDTO> dtos) {
         return dtos.stream().map(AdaptadorCliente::Entidad).collect(Collectors.toList());
    }


    public static List<ClienteDTO> listaDTO(List<Cliente> entidades) {
        return entidades.stream().map(AdaptadorCliente::DTO).collect(Collectors.toList());
    }
} 