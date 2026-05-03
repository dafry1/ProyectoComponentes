package fachadas;

import DTOS.PiezaDTO;
import controles.ControlBodega;
import java.util.List;

/**
 * Implementación de la bodega
 * 
 * @author Andre
 */
public class FachadaBodega implements IFachadaBodega{
    ControlBodega controlBodega;
    
    public FachadaBodega() {
        controlBodega = new ControlBodega();
    }
    
    @Override
    public List<PiezaDTO> consultarBodega() {
        return controlBodega.consultarBodega();
    }

    @Override
    public PiezaDTO consultarPieza(Long id) {
        return controlBodega.consultarPieza(id);
    }

    @Override
    public List<PiezaDTO> filtrarPorNombre(String nombre) {
        return controlBodega.filtrarPorNombre(nombre);
    }

    @Override
    public List<PiezaDTO> filtrarPorCategoria(String categoria) {
        return controlBodega.filtrarPorCategoria(categoria);
    }

    @Override
    public List<PiezaDTO> filtrarPorMarca(String marca) {
        return controlBodega.filtrarPorMarca(marca);
    }

    @Override
    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo) {
        return controlBodega.filtrarPorPrecioMax(precioMaximo);
    }
}
