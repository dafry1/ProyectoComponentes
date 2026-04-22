/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IControlVentas {
    /**
     * Consulta las piezas de su BO respectivo
     * 
     * @return lista de tipo PiezaDTO
     */
    List<PiezaDTO> consultarPiezas();
    
    /**
     * Hace todo el proceso de llevar a cabo una venta
     * 
     * @param cliente
     * @param detalles
     * 
     * @return la venta
     */
    VentaDTO procesarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles);
}