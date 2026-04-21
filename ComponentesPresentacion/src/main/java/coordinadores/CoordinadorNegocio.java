package coordinadores;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import fachada.FachadaVentas;
import interfaces.IFachadaVentas;
import java.util.List;
import observadores.IObservador;

/**
 * Coordinador encargado de recopilar todas las fachadas con lógica
 * de negocio en un solo lugar
 * 
 * @author Andre
 */
public class CoordinadorNegocio {
    //Instancia singleton
    private static CoordinadorNegocio instancia;
    
    //Instancia de la fachada del subsistema de las ventas
    private IFachadaVentas fachadaVentas = new FachadaVentas();
    
    /**
     * Crea la instancia única del coordiandor
     * 
     * @return la instancia única
     */
    public static CoordinadorNegocio singleton() {
        if (instancia == null) {
            instancia = new CoordinadorNegocio();
        }
        return instancia;
    }
    
    
    
    /**
     * Regresa todas las piezas del sistema, dadas directamente
     * por el IFachadaVentas
     * 
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarPiezas() {
        return fachadaVentas.consultarPiezas();
    }
    
    
    
    /**
     * Orquesta todos los métodos necesarios para llevar a
     * cabo una venta dentro del sistema. Actualiza stock,
     * limpia el carrito, etc.
     * 
     * @param carrito para la venta. No puede ser null
     * @param observador si se necesita actualizar algo. Puede ser null
     */
    public void procesarVenta(List<DetallesVentaDTO> carrito, IObservador observador) {
        
        //Procesa la venta directamente de la fachada
        fachadaVentas.procesarVenta(null, carrito);
        
        //Limpia el carrito de ventas
        CoordinadorEstados.singleton().limpiarCarritoVenta();
        
        //Activa al observador si existe
        if (observador != null) {
            observador.observar();
        }
    }
}