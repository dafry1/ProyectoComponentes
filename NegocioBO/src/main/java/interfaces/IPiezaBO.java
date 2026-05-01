package interfaces;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import dominio.Pieza;
import java.util.List;

/**
 * Interfaz que establece el contrato para los métodos 
 * de PiezaBO
 * 
 * @author Andre
 */
public interface IPiezaBO {
    
    /**
     * Busca pieza por id
     * 
     * @param id de pieza
     * 
     * @return la pieza
     */
    PiezaDTO consultarPieza(Long id);
    
    /**
     * Consulta todas las piezas registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<PiezaDTO> consultarPiezas();
    
    /**
     * Consulta las piezas más vendidas al día registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<PiezaDTO> consultarTopDiaPiezas();
    
    /**
     * Consulta las piezas más vendidas a la semana registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<PiezaDTO> consultarTopSemanaPiezas();
    
    /**
     * Consulta las piezas más vendidas al mes registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<PiezaDTO> consultarTopMesPiezas();
    
    /**
     * Consulta las piezas más vendidas en todo el tiempo registradas en
     * el sistema
     * 
     * @return la lista de piezas en DTO
     */
    List<PiezaDTO> consultarTopTodoPiezas();
    
    /**
     * Filtra las piezas según nombre
     * 
     * @param nombre
     * 
     * @return la lista
     */
    List<PiezaDTO> filtrarPorNombre(String nombre);
    
    /**
     * Filtra las piezas según categoria
     * 
     * @param categoria
     * 
     * @return la lista
     */
    List<PiezaDTO> filtrarPorCategoria(String categoria);
    
    /**
     * Filtra las piezas según marca
     * 
     * @param marca
     * 
     * @return la lista
     */
    List<PiezaDTO> filtrarPorMarca(String marca);
    
    /**
     * Filtra las piezas según precio máximo
     * 
     * @param precioMaximo
     * 
     * @return la lista
     */
    List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo);
    
    /**
     * Actualiza el stock de una pieza en específica basándose
     * en un DetallesVentaDTO en específico
     * 
     * @param detalle de la pieza a actualizar el stock
     */
    void actualizarStock(DetallesVentaDTO detalle); 
    
    /**
     * Utiliza el método actualizarStock iterando sobre la lista
     * de detalles para actualizar todos las piezas
     * 
     * @param detalles de la venta y el stock se debe actualizar
     */
    void actualizarStockTrasVenta(List<DetallesVentaDTO> detalles);
    
    
}