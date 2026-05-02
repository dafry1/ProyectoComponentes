package adaptadores;

import DTOS.ClienteDTO;
import dominio.Cliente;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public class AdaptadorCliente extends AdaptadorPersona implements IAdaptadorCliente {
    private static IAdaptadorCliente instancia;
    private AdaptadorCliente(){}
    
    /**
     * Singleton del adaptador
     * 
     * @return la única instancia
     */
    public static IAdaptadorCliente singleton() {
        if (instancia == null) {
            instancia = new AdaptadorCliente();
        }
        return instancia;
    }
    
    @Override
    public Cliente Entidad(ClienteDTO dto) {
        Cliente entidad = new Cliente();
        super.EntidadPersona(dto, entidad);
        entidad.setTelefono(dto.getTelefono());
        entidad.setCorreo(dto.getCorreo());
        return entidad;
    }

    @Override
    public ClienteDTO DTO(Cliente entidad) {
        ClienteDTO dto = new ClienteDTO();
        super.DTOPersona(entidad, dto);
        dto.setTelefono(entidad.getTelefono());
        dto.setCorreo(entidad.getCorreo());
        return dto;
    }

    @Override
    public List<Cliente> listaEntidad(List<ClienteDTO> dtos) {
         return dtos.stream().map(this::Entidad).collect(Collectors.toList());
    }

    @Override
    public List<ClienteDTO> listaDTO(List<Cliente> entidades) {
        return entidades.stream().map(this::DTO).collect(Collectors.toList());
    }
}