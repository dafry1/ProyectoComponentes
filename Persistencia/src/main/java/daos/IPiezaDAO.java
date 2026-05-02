package daos;

import dominio.DetallesVenta;
import dominio.Pieza;
import java.util.List;

/**
 * Establece el contrato de métodos relacionados a 
 * una pieza en la BD
 * 
 * @author Andre
 */
public interface IPiezaDAO {
    
    /**
     * Consulta una piez por su Id
     * 
     * @param id de la pieza
     * 
     * @return la pieza
     */
    Pieza consultarPieza(Long id);
    
    /**
     * Consulta todas las piezas registradas en el sistema
     * 
     * @return la lista de piezas 
     */
    List<Pieza> consultarPiezas();
    
    /**
     * Consulta las piezas más vendidas al día registradas en el sistema
     * 
     * @return la lista de piezas
     */
    List<Pieza> consultarTopDiaPiezas();
    
    /**
     * Consulta las piezas más vendidas a la semana registradas en el sistema
     * 
     * @return la lista de piezas
     */
    List<Pieza> consultarTopSemanaPiezas();
    
    /**
     * Consulta las piezas más vendidas al mes registradas en el sistema
     * 
     * @return la lista de piezas
     */
    List<Pieza> consultarTopMesPiezas();
    
    /**
     * Consulta las piezas más vendidas en todo el tiempo registradas en
     * el sistema
     * 
     * @return la lista de piezas
     */
    List<Pieza> consultarTopTodoPiezas();
    
    /**
     * Filtra las piezas según nombre
     * 
     * @param nombre
     * 
     * @return la lista
     */
    List<Pieza> filtrarPorNombre(String nombre);
    
    /**
     * Filtra las piezas según categoria
     * 
     * @param categoria
     * 
     * @return la lista
     */
    List<Pieza> filtrarPorCategoria(String categoria);
    
    /**
     * Filtra las piezas según marca
     * 
     * @param marca
     * 
     * @return la lista
     */
    List<Pieza> filtrarPorMarca(String marca);
    
    /**
     * Filtra las piezas según precio máximo
     * 
     * @param precioMaximo
     * 
     * @return la lista
     */
    List<Pieza> filtrarPorPrecioMax(double precioMaximo);
    
    /**
     * Actualiza el stock de una pieza en específica basándose
     * en un DetallesVenta en específico
     * 
     * @param detalle de la pieza a actualizar el stock
     */
    void actualizarStock(DetallesVenta detalle); 
    
    /**
     * Utiliza el método actualizarStock iterando sobre la lista
     * de detalles para actualizar todos las piezas
     * 
     * @param detalles de la venta y el stock se debe actualizar
     */
    void actualizarStockTrasVenta(List<DetallesVenta> detalles);
}
