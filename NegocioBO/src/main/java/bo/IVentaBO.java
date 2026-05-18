package bo;

import DTOS.VentaDTO;
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
     * Devuelve la cantidad de ventas del dia de la consulta, desde la BD
     *
     * @return cantidad de ventas registradas
     */
    int cantidadVentasDiarias();
    
    /**
     * Registra una venta en el sistema
     * 
     * @param venta a registrar
     * 
     * @return la venta creada
     */
    VentaDTO registrarVenta(VentaDTO venta);
    
    /**
     * Marca una venta como facturada
     * 
     * @param idVenta
     */
    void marcarFacturada(String idVenta);
}