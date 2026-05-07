package controles;

import DTOS.PiezaDTO;
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
    private static String DEBUG = "Depuración";
    
    private static final System.Logger LOG = System.getLogger(ControlCatalogoBodega.class.getName());
    
    private FachadaBodega fachadaBodega;
    
    /** Constructor
     * @param fachadaBodega */
    public ControlCatalogoBodega(FachadaBodega fachadaBodega) {
        this.fachadaBodega = new FachadaBodega();
    }

    public ControlCatalogoBodega() {
        this.fachadaBodega = new FachadaBodega();
    }
   
    
    public List<PiezaDTO> consultarBodega() {
        try {
            return fachadaBodega.consultarBodega();
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }

    public PiezaDTO consultarPieza(Long id) {
        try {
            return fachadaBodega.consultarPieza(id);
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }

    public List<PiezaDTO> filtrarPorNombre(String nombre) {
        try {
            return fachadaBodega.filtrarPorNombre(nombre);
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }

    public List<PiezaDTO> filtrarPorCategoria(String categoria) {
        try {
            return fachadaBodega.filtrarPorCategoria(categoria);
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }

    public List<PiezaDTO> filtrarPorMarca(String marca) {
        try {
            return fachadaBodega.filtrarPorMarca(marca);
        } catch (InfraestructuraException e) {
            throw atrapar(e);
        }
    }

    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo) {
        try {
            return fachadaBodega.filtrarPorPrecioMax(precioMaximo);
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
        DEBUG = excepcion.getMessage();
        LOG.log(System.Logger.Level.ALL, DEBUG);
        return new NegocioException(DEBUG);
    }
}