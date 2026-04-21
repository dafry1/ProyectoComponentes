package coordinadores;

import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import bo.EmpleadoBO;
import fachada.FachadaInicioSesion;
import fachada.FachadaVentas;
import interfaces.IFachadaInicioSesion;
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
    private IFachadaInicioSesion fachadaInicioSesion = new FachadaInicioSesion();
    
    /**
     * Crea la instancia única del coordiandor
     * 
     * @return la instancia única
     */
    public static CoordinadorNegocio getInstance() {
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
        VentaDTO venta = fachadaVentas.procesarVenta(null, carrito);
        int numPieza = 1;

        //FIXME: PRINTS TEMPORALES
        System.out.println("================ Venta registrada ================");
        System.out.println("Cliente: " + venta.getCliente().getNombre());
        System.out.println("Detalles: ");
        for (DetallesVentaDTO detalle : venta.getDetalles()) {
            System.out.println("-------- Pieza #" + numPieza + " --------");
            System.out.println("-> Pieza: " + detalle.getPieza().getNombre());
            System.out.println("-> Cantidad: " + detalle.getCantidad());
            System.out.println("-> Subtotal: " + detalle.getSubtotal());
            numPieza++;
        }
        System.out.println("Fecha y hora; " + venta.getFechaHora());
        System.out.println("Folio; " + venta.getFolio());
        System.out.println("==================================================");
        
        //Limpia el carrito de ventas
        CoordinadorEstados.singleton().limpiarCarritoVenta();
        
        //Activa al observador si existe
        if (observador != null) {
            observador.observar();
        }
    }
    
    public EmpleadoDTO autenticar(String user, String pass) {
        return fachadaInicioSesion.login(user, pass);
    }
}