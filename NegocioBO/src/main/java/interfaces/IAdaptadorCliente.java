/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import DTOS.ClienteDTO;
import dominio.Cliente;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IAdaptadorCliente {
    
    Cliente Entidad(ClienteDTO dto);
    
    List<Cliente> listaEntidad(List<ClienteDTO> dtos);
    
    ClienteDTO DTO(Cliente entidad);
    
    List<ClienteDTO> listaDTO(List<Cliente> entidades);
    
}
