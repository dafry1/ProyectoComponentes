package fachada;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import bo.PiezaBO;
import bo.VentaBO;
import interfaces.IFachadaVentas;
import interfaces.IPiezaBO;
import interfaces.IVentaBO;
import java.util.List;

/**
 * Unifica los BOs con sus métodos en una sola clase para
 * centralizar toda la lógica necesaria para registrar y
 * manejar ventas
 * 
 * @author Andre
 */
public class FachadaVentas implements IFachadaVentas {
    
    //Interfaces
    private IPiezaBO piezaBO = new PiezaBO();
    private IVentaBO ventaBO = new VentaBO();
    
    /**
     * Consulta las piezas de su BO respectivo
     * 
     * @return lista de tipo PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        return piezaBO.consultarPiezas();
    }
    
    
    
    /**
     * Cambia el stock actual de la pieza correspondiente
     * al detalle ingresado como parámetro, restando la
     * cantidad de dicha venta. Público porque podría
     * quererse hacer atómicamente, aunque lo dudo...
     * 
     * @param detalle de la pieza para restarle stock 
     */
    @Override
    public void actualizarStock(DetallesVentaDTO detalle) {
        piezaBO.actualizarStock(detalle);
    }
    
    
    
    /**
     * Orquesta la lógica de procesar una venta (registro de
     * la venta, actualización de stock)
     * 
     * @param cliente que compró las piezas
     * @param detalles de la venta
     * 
     * @return la venta registrada
     */
    @Override
    public VentaDTO procesarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles) {
        actualizarStockTrasVenta(detalles);
        return ventaBO.registrarVenta(cliente, detalles);
    }
    
    
    
    /**
     * Utiliza el método actualizarStock iterando sobre la lista
     * de detalles para actualizar todos las piezas
     * 
     * @param detalles de la venta y el stock se debe actualizar
     */
    private void actualizarStockTrasVenta(List<DetallesVentaDTO> detalles) {
        for (DetallesVentaDTO detalle: detalles) {
            actualizarStock(detalle);
        }
    }
}