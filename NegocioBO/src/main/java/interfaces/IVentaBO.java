/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.VentaDTO;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IVentaBO {
    
    /**
     * Registra una venta en el sistema
     * 
     * @param cliente que compró las piezas
     * @param detalles que compró el client
     * 
     * @return la venta creada
     */
    VentaDTO registrarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles);
}