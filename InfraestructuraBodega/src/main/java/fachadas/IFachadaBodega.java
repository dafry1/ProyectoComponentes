package fachadas;

import DTO.PiezaInfraestructuraDTO;
import DTOS.PiezaDTO;
import java.util.List;

/**
 * Interfaz que define el contrato del subsistema de Bodega/API.
 * Expone los servicios de consulta y filtrado de piezas sin revelar
 * detalles de la implementación (como el uso de JSON o Gist).
 * 
 * @author Andre
 */
public interface IFachadaBodega {

    /**
     * Obtiene el catálogo completo de piezas disponibles en la bodega.
     * 
     * @return lista de PiezaDTO con todos los productos.
     */
    List<PiezaInfraestructuraDTO> consultarBodega();
    
    /**
     * Filtra las piezas cuyo nombre contenga la cadena proporcionada.
     * 
     * @param nombre Texto a buscar en el nombre de la pieza.
     * @return Lista de piezas que coinciden con el filtro.
     */
    List<PiezaInfraestructuraDTO> filtrarPorNombre(String nombre);

    /**
     * Filtra las piezas pertenecientes a una categoría específica.
     * 
     * @param categoria Categoría a buscar.
     * @return Lista de piezas que pertenecen a dicha categoría.
     */
    List<PiezaInfraestructuraDTO> filtrarPorCategoria(String categoria);

    /**
     * Filtra las piezas fabricadas por una marca específica.
     * 
     * @param marca Marca de la pieza a buscar.
     * @return Lista de piezas de esa marca.
     */
    List<PiezaInfraestructuraDTO> filtrarPorMarca(String marca);
    
    List<PiezaInfraestructuraDTO> filtrarPorModelo(String modelo);

    /**
     * Filtra las piezas que tengan un costo menor o igual al precio máximo.
     * 
     * @param precioMaximo Límite superior de precio.
     * @return Lista de piezas dentro del rango de precio.
     */
    List<PiezaInfraestructuraDTO> filtrarPorPrecioMax(double precioMaximo);
}