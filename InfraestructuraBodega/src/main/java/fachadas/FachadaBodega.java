package fachadas;

import DTO.PiezaInfraestructuraDTO;
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
    public List<PiezaInfraestructuraDTO> consultarBodega() {
        return controlBodega.consultarBodega();
    }
    
    @Override
    public List<PiezaInfraestructuraDTO> filtrarPorNombre(String nombre) {
        return controlBodega.filtrarPorNombre(nombre);
    }

    @Override
    public List<PiezaInfraestructuraDTO> filtrarPorCategoria(String categoria) {
        return controlBodega.filtrarPorCategoria(categoria);
    }

    @Override
    public List<PiezaInfraestructuraDTO> filtrarPorMarca(String marca) {
        return controlBodega.filtrarPorMarca(marca);
    }

    @Override
    public List<PiezaInfraestructuraDTO> filtrarPorPrecioMax(double precioMaximo) {
        return controlBodega.filtrarPorPrecioMax(precioMaximo);
    }

    @Override
    public List<PiezaInfraestructuraDTO> filtrarPorModelo(String modelo) {
        return controlBodega.filtrarPorModelo(modelo);
    }
}
