package bo;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
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
    private static List<PiezaDTO> PIEZAS = new ArrayList<>();
    static {
        PiezaDTO pieza1 = new PiezaDTO();
        pieza1.setNombre("Ryzen 5 9600X");
        pieza1.setCategoria("Procesador");
        pieza1.setMarcaPieza("AMD");
        pieza1.setModeloPieza("Zen 5");
        pieza1.setCostoPieza(2500.0);
        pieza1.setStockPieza(50);
        PIEZAS.add(pieza1);
        
        PiezaDTO pieza2 = new PiezaDTO();
        pieza2.setNombre("Core i9-14900K");
        pieza2.setCategoria("Procesador");
        pieza2.setMarcaPieza("Intel");
        pieza2.setModeloPieza("Raptor Lake");
        pieza2.setCostoPieza(5500.0);
        pieza2.setStockPieza(40);
        PIEZAS.add(pieza2);
        
        PiezaDTO pieza3 = new PiezaDTO();
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
    public void actualizarStock(DetallesVentaDTO detalle) {
        
        for (PiezaDTO pieza: PIEZAS) {
            
            if (pieza.getNombre().equalsIgnoreCase(detalle.getPieza().getNombre())) {
                int stockActual = pieza.getStockPieza();
                int cantidadVendida = detalle.getCantidad();
                
                if (stockActual >= cantidadVendida) {
                    pieza.setStockPieza(stockActual - cantidadVendida);
                    System.out.println("Stock actualizado: " + pieza.getNombre() + " ahora tiene " + pieza.getStockPieza());
                } else {
                    System.out.println("Error: No hay suficiente stock para " + pieza.getNombre());
                }
                return;
            }
        }
    }
}
