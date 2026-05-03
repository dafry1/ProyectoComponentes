package controles;

import DTOS.PiezaDTO;
import conexiones.ConexionBodegaAPI;
import excepciones.InfraestructuraException;
import java.util.List;

/**
 * Control que ejecuta la conexión con la API
 * e impone lógica de negocio
 * 
 * @author Andre
 */
public class ControlBodega {
    private static final System.Logger LOG = System.getLogger(ControlBodega.class.getName());
    String DEBUG = "Depuración";
    String NO_FILTRADO = "No hay resultados para filtrar por: ";
    
    //Singleton de la conexión a la API
    ConexionBodegaAPI api = ConexionBodegaAPI.singleton();
    
    /**
     * Obtiene las piezas directamente de la API
     * 
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarBodega() {
        return api.consultarBodega();
    }
    
    /**
     * Consulta una pieza por id
     * 
     * @param id
     * 
     * @return la PiezaDTO
     */
    public PiezaDTO consultarPieza(Long id) {
        if (id == null) {
            DEBUG = "Id inválida";
            debugExcepcion();
        }
        PiezaDTO pieza = null;
        for (PiezaDTO p: consultarBodega()) {
            if (p.getId().equals(id)) {
                pieza = p;
            }
        }
        if (pieza == null) {
            DEBUG = "No se halló pieza con el Id " + id;
            debugExcepcion();
        }
        return pieza;
    }
    
    /**
     * Filtra por nombre
     * 
     * @param nombre para filtrar
     * 
     * @return piezas filtradas
     */
    public List<PiezaDTO> filtrarPorNombre(String nombre) {
        if (nombre.isBlank()) {
            DEBUG = NO_FILTRADO + nombre;
            debugExcepcion();
        }
        return consultarBodega().stream().filter(p -> p.getNombre().toLowerCase()
                                .contains(nombre.toLowerCase()))
                                .toList();
    }
    
    /**
     * Filtra por categoría
     * 
     * @param categoria
     * 
     * @return lista filtrada
     */
    public List<PiezaDTO> filtrarPorCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            DEBUG = NO_FILTRADO + categoria;
            debugExcepcion();
        }
        return consultarBodega().stream().filter(p -> p.getCategoria().toLowerCase()
                                .contains(categoria.toLowerCase()))
                                .toList();
    }
    
    /**
     * Consulta todas las piezas cuya marca
     * coincida con el campo
     * 
     * @param marca para filtrar
     * 
     * @return piezas filtradas 
     */
    public List<PiezaDTO> filtrarPorMarca(String marca) {
        if (marca.isBlank()) {
            DEBUG = NO_FILTRADO + marca;
            debugExcepcion();
        }
        return consultarBodega().stream().filter(p -> p.getMarcaPieza().toLowerCase()
                                .contains(marca.toLowerCase()))
                                .toList();
    }
    
    /**
     * Consulta todas las piezas cuyo precio
     * coincida con el máximo
     * 
     * @param precioMaximo para filtrar
     * 
     * @return piezas filtradas 
     */
    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo) {
        if (precioMaximo < 1) {
            DEBUG = NO_FILTRADO + precioMaximo;
            debugExcepcion();
        }
        return consultarBodega().stream().filter(p -> p.getCostoPieza() <= precioMaximo).toList();
    }
    
    /** Centraliza el arrojo de excepción y depuración */
    private void debugExcepcion() {
        LOG.log(System.Logger.Level.ERROR, ">> " + DEBUG);
        throw new InfraestructuraException(DEBUG);
    }
}