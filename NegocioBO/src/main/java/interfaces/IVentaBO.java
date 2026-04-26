package interfaces;

import DTOS.VentaDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz para el BO de ventas
 * 
 * @author Andre
 */
public interface IVentaBO {
    
    /**
     * Extrae todas las ventas de la BD
     *
     * @return lista de VentaDTO mapeadas
     */
    List<VentaDTO> consultarVentas();
    
    /**
     * Filtra las ventas por fecha
     * 
     * @param fecha a consultar
     * 
     * @return las ventas de ese día en específico
     */
    List<VentaDTO> filtrarVentasFecha(LocalDate fecha);
    
    
    /**
     * Filtra ventas con un mínimo de ganancias
     * 
     * @param minimo de su total
     * 
     * @return ventas que cumplan el filtro
     */
    List<VentaDTO> filtrarVentasTotalMinimo(double minimo);
    
    /**
     * Filtra ventas con un máximo de ganancias
     * 
     * @param maximo de su total
     * 
     * @return ventas que cumplan el filtro
     */
    List<VentaDTO> filtrarVentasTotalMaximo(double maximo);
    
    /**
     * Registra una venta en el sistema
     * 
     * @param venta a registrar
     * 
     * @return la venta creada
     */
    VentaDTO registrarVenta(VentaDTO venta);
}