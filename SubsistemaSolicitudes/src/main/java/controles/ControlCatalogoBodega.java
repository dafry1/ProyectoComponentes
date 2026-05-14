package controles;

import DTOS.PiezaDTO;
import adaptadores.AdaptadorPiezaDTO;
import fabricas.IFabricaBO;
import bo.IPiezaBO;
import excepciones.InfraestructuraException;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import fachadas.FachadaBodega;
import java.util.List;

/**
 * Recibe y conecta a negocio los datos recuperados
 * la API
 * 
 * @author Andre
 */
public class ControlCatalogoBodega{
    
    private static final System.Logger LOG = System.getLogger(ControlCatalogoBodega.class.getName());
    
    private FachadaBodega fachadaBodega;
    
    /** Constructor
     * @param fachadaBodega */
    public ControlCatalogoBodega(FachadaBodega fachadaBodega) {
        this.fachadaBodega = fachadaBodega;
    }

    public ControlCatalogoBodega() {
        this.fachadaBodega = new FachadaBodega();
    }
   
    
    public List<PiezaDTO> consultarBodega() {
        try {
            return AdaptadorPiezaDTO.piezas(fachadaBodega.consultarBodega());
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }

    /*
    public PiezaDTO consultarPieza(Long id) {
        try {
            return fachadaBodega.consultarPieza(id);
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }
    */

    public List<PiezaDTO> filtrarPorNombre(String nombre) {
        try {
            return AdaptadorPiezaDTO.piezas(fachadaBodega.filtrarPorNombre(nombre));
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }

    public List<PiezaDTO> filtrarPorCategoria(String categoria) {
        try {
            return AdaptadorPiezaDTO.piezas(fachadaBodega.filtrarPorCategoria(categoria));
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }

    public List<PiezaDTO> filtrarPorMarca(String marca) {
        try {
            return AdaptadorPiezaDTO.piezas(fachadaBodega.filtrarPorMarca(marca));
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }

    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo) {
        try {
            return AdaptadorPiezaDTO.piezas(fachadaBodega.filtrarPorPrecioMax(precioMaximo));
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }
    
    /**
     * Centraliza el manejo de excepciones. En este caso,
     * registrarl en loggers y relanzar una de negocio
     * 
     * @param excepcion a manejar
     * 
     * @return NegocioException
     */
    private NegocioException atrapar(InfraestructuraException excepcion) {
        String DEBUG = excepcion.getMessage();
        LOG.log(System.Logger.Level.ERROR, DEBUG);
        return new NegocioException(DEBUG);
    }
}