package fachadas;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import fabricas.FabricaControlesVentas;
import interfaces.IControlVentas;
import interfaces.IFabricaControlesVentas;
import interfaces.IFachadaVentas;
import java.util.List;

/**
 * Fachada que encapsula los métodos de los controles
 * 
 * @author Andre
 */
public class FachadaVentas implements IFachadaVentas {

    //Controles
    IControlVentas controlVentas;
    
    public FachadaVentas() {
        IFabricaControlesVentas fabricaControlesVenta = FabricaControlesVentas.singleton();
        this.controlVentas = fabricaControlesVenta.fabricarControlVentas();
    }
    
    /**
     * Consulta las piezas del sistema
     * 
     * @return lista de tipo PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        return controlVentas.consultarPiezas();
    }

    /**
     * Consulta las piezas más vendidas en el día del sistema
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopDiaPiezas() {
        return controlVentas.consultarPiezas();
    }

    /**
     * Consulta las piezas más vendidas en la semana del sistema
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopSemanaPiezas() {
        return controlVentas.consultarPiezas();
    }

    /**
     * Consulta las piezas más vendidas en el mes del sistema
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopMesPiezas() {
        return controlVentas.consultarPiezas();
    }

    /**
     * Consulta las piezas más vendidas en todo el tiempo del sistema
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopTodoPiezas() {
        return controlVentas.consultarPiezas();
    }
    
    /**
     * Orquesta la lógica de procesar una venta (registro de
     * la venta, actualización de stock)
     * 
     * @param cliente que compró las piezass
     * @param detalles de la venta
     * 
     * @return la venta registrada
     */
    @Override
    public VentaDTO procesarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles) {
        return controlVentas.procesarVenta(cliente, detalles);
    }
}