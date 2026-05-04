package controles;

import DTOS.PiezaDTO;
import fabricas.IFabricaBO;
import bo.IPiezaBO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import fachadas.FachadaBodega;
import java.util.List;

/**
 * Control dedicado a acceder a las piezas disponibles LOCALMENTE, sin
 * meterse a la hipotética bodega de la API
 * 
 * @author Andre
 */
public class ControlCatalogoBodega{
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
        return fachadaBodega.consultarBodega();
    }

    public PiezaDTO consultarPieza(Long id) {
        return fachadaBodega.consultarPieza(id);
    }

    public List<PiezaDTO> filtrarPorNombre(String nombre) {
        return fachadaBodega.filtrarPorNombre(nombre);
    }

    public List<PiezaDTO> filtrarPorCategoria(String categoria) {
        return fachadaBodega.filtrarPorCategoria(categoria);
    }

    public List<PiezaDTO> filtrarPorMarca(String marca) {
        return fachadaBodega.filtrarPorMarca(marca);
    }

    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo) {
        return fachadaBodega.filtrarPorPrecioMax(precioMaximo);
    }
}