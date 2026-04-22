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