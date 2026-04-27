package interfaces;

import dominio.Venta;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IVentaDAO {
    /**
     * Extrae todas las ventas de la BD
     *
     * @return lista de ventas
     */
    List<Venta> consultarVentas();
    
    /**
     * Registra una venta en el sistema
     * 
     * @param venta
     * 
     * @return la venta creada
     */
    Venta registrarVenta(Venta venta);
    
    /**
     * Filtra las ventas por fecha
     * 
     * @param fecha a consultar
     * 
     * @return las ventas de ese día en específico
     */
    List<Venta> filtrarVentasFecha(LocalDate fecha);
    
    
    /**
     * Filtra ventas con un mínimo de ganancias
     * 
     * @param minimo de su total
     * 
     * @return ventas que cumplan el filtro
     */
    List<Venta> filtrarVentasTotalMinimo(double minimo);
    
    /**
     * Filtra ventas con un máximo de ganancias
     * 
     * @param maximo de su total
     * 
     * @return ventas que cumplan el filtro
     */
    List<Venta> filtrarVentasTotalMaximo(double maximo);
}