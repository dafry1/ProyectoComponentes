package controles;

import DTO.AdaptadorPiezaDTO;
import DTO.PiezaInfraestructuraDTO;
import DTOS.PiezaDTO;
import conexiones.ProcesadorJSON;
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
    private static String DEBUG = "Depuración";
    private static final String NO_FILTRADO = "No hay resultados para filtrar por: ";
    
    //Singleton de la clase que se conecta con la API
    ProcesadorJSON pjson = ProcesadorJSON.singleton();
    
    /**
     * Obtiene las piezas directamente de la API
     * 
     * @return lista de PiezaDTO
     */
    public List<PiezaInfraestructuraDTO> consultarBodega() {
        return pjson.obtenerBodega();
    }
    
    /**
     * Filtra por nombre 
     * 
     * @param nombre para filtrar
     * 
     * @return piezas filtradas
     */
    public List<PiezaInfraestructuraDTO> filtrarPorNombre(String nombre) {
        validarEntrada(nombre);
        return pjson.obtenerBodegaFiltrada("nombre", nombre);
    }
    
    /**
     * Filtra por categoría
     * 
     * @param categoria
     * 
     * @return lista filtrada
     */
    public List<PiezaInfraestructuraDTO> filtrarPorCategoria(String categoria) {
        validarEntrada(categoria);
        return pjson.obtenerBodegaFiltrada("categoria", categoria);
    }
    
    /**
     * Consulta todas las piezas cuya marca
     * coincida con el campo
     * 
     * @param marca para filtrar
     * 
     * @return piezas filtradas 
     */ 
    public List<PiezaInfraestructuraDTO> filtrarPorMarca(String marca) {
        validarEntrada(marca);
        return pjson.obtenerBodegaFiltrada("marca", marca);
    }
    
    /**
     * Consulta todas las piezas cuyo precio
     * coincida con el máximo
     * 
     * @param precioMaximo para filtrar
     * 
     * @return piezas filtradas 
     */
    public List<PiezaInfraestructuraDTO> filtrarPorPrecioMax(double precioMaximo) {
        if (precioMaximo < 0) {
            throw lanzar();
        }
        return pjson.obtenerBodegaFiltrada("precio", String.valueOf(precioMaximo));
    }
    
    /** Centraliza el arrojo de excepción y depuración */
    private InfraestructuraException lanzar() {
        DEBUG = "Filtro inválido";
        LOG.log(System.Logger.Level.ERROR, ">> " + DEBUG);
        return new InfraestructuraException(DEBUG);
    }
    
    private void validarEntrada(String valor) {
        if (valor == null || valor.isBlank()) {
            throw lanzar();
        }
    }
}