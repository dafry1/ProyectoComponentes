package bo;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import excepciones.NegocioException;
import interfaces.IPiezaBO;
import java.util.ArrayList;
import java.util.List;

/**
 * BO que se conecta directamente con la persistencia para
 * extraer los datos de la BD
 * 
 * @author Andre
 */
public class PiezaBO implements IPiezaBO {
    private static final System.Logger LOG = System.getLogger(VentaBO.class.getName());
    private static String CARRITO_VACIO = "No se puede procesar una venta con un carrito vacío";
    
    private static List<PiezaDTO> PIEZAS = new ArrayList<>();
    
    static {
        PiezaDTO pieza1 = new PiezaDTO();
        pieza1.setId(1L);
        pieza1.setNombre("Ryzen 5 9600X");
        pieza1.setCategoria("Procesador");
        pieza1.setMarcaPieza("AMD");
        pieza1.setModeloPieza("Zen 5");
        pieza1.setCostoPieza(2500.0);
        pieza1.setStockPieza(50);
        PIEZAS.add(pieza1);
        
        PiezaDTO pieza2 = new PiezaDTO();
        pieza2.setId(2L);
        pieza2.setNombre("Core i9-14900K");
        pieza2.setCategoria("Procesador");
        pieza2.setMarcaPieza("Intel");
        pieza2.setModeloPieza("Raptor Lake");
        pieza2.setCostoPieza(5500.0);
        pieza2.setStockPieza(40);
        PIEZAS.add(pieza2);
        
        PiezaDTO pieza3 = new PiezaDTO();
        pieza3.setId(3L);
        pieza3.setNombre("Trident Z5 RGB");
        pieza3.setCategoria("RAM");
        pieza3.setMarcaPieza("G.Skill");
        pieza3.setModeloPieza("DDR5-6400");
        pieza3.setCostoPieza(1800.0);
        pieza3.setStockPieza(30);
        PIEZAS.add(pieza3);
    }
    
    
    
    /**
     * Extrae todas las piezas de la BD
     * 
     * @return lista de PiezaDTO mapeadas
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        return PIEZAS;
    }
    
    
    
    /**
     * Busca la pieza del detalle para actualizar el stock de esa pieza
     * 
     * @param detalle 
     */
    @Override
    public void actualizarStock(DetallesVentaDTO detalle) { //-> FIXME: ESTO DEBE CONSULTAR DIRECTO AL DAO
        
        for (PiezaDTO pieza: PIEZAS) {
            
            if (pieza.equals(detalle.getPieza())) {
                int stockActual = pieza.getStockPieza();
                int cantidadVendida = detalle.getCantidad();
                
                if (stockActual >= cantidadVendida) {
                    pieza.setStockPieza(stockActual - cantidadVendida);
                    LOG.log(System.Logger.Level.DEBUG, "Stock actualizado: " + pieza.getNombre() + " ahora tiene " + pieza.getStockPieza());
                } else {
                    LOG.log(System.Logger.Level.ERROR, CARRITO_VACIO);
                }
                return;
            }
        }
    }
    
    
    
    /**
     * Utiliza el método actualizarStock iterando sobre la lista
     * de detalles para actualizar todos las piezas
     * 
     * @param detalles de la venta y el stock se debe actualizar
     */
    @Override
    public void actualizarStockTrasVenta(List<DetallesVentaDTO> detalles) {
        
        //Excepción si la lista está vacía o es null
        if (detalles == null || detalles.isEmpty()) {
            LOG.log(System.Logger.Level.ERROR, CARRITO_VACIO);
            throw new NegocioException(CARRITO_VACIO);
        }
        
        //Hace la iteración propiamente dicha
        for (DetallesVentaDTO detalle: detalles) {
            actualizarStock(detalle);
        }
    }
}