package bo;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.VentaDTO;
import interfaces.IVentaBO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * BO para la entidad venta
 * 
 * @author Andre
 */
public class VentaBO implements IVentaBO {
    
    private static List<VentaDTO> VENTAS = new ArrayList<>();
    
    /**
     * Extrae todas las ventas de la BD
     *
     * @return lista de VentaDTO mapeadas
     */
    @Override
    public List<VentaDTO> consultarVentas() {
        return VENTAS;
    }
    
    /**
     * Registra una venta en el sistema
     * 
     * @param venta
     * 
     * @return la venta creada
     */
    @Override
    public VentaDTO registrarVenta(VentaDTO venta) {
        generarFecha(venta); //-> FIXME: ESTO ES TEMPORAL TAMBIEN
        generarFolio(venta); //-> FIXME: DE IGUAL FORMA ES UNA IMPLEMENTACIÓN TEMPORAL
        VENTAS.add(venta);
        return venta;
    }
    
    
    
    /**
     * Genera un folio para la venta
     * 
     * @param venta FIXME: TEMPORAL, EN FUNCIÓN REAL DEBE ACUDIR AL DAO PARA CONTAR LAS QUE LLEVAN EN EL DÍA
     */
    private void generarFolio(VentaDTO venta) {
        String PREFIJO = "TW-";
        venta.setFolio(PREFIJO + venta.contador);
    }
    
    
    /**
     * Auxiliar obtiene la fecha
     * 
     * @param venta 
     */
    private void generarFecha(VentaDTO venta) {
        LocalDateTime fechaHoraRegistro = LocalDateTime.now();
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        String stringFechaHora = fechaHoraRegistro.format(formato);
        
        venta.setFechaHora(stringFechaHora);
    }
}