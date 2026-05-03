package fachadas;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import controles.ControlCarrito;
import controles.ControlCatalogoBodega;
import controles.ControlVentas;
import fabricas.FabricaBO;
import fabricas.IFabricaBO;
import java.util.List;

/**
 * Fachada que encapsula y coordina los métodos de los controles para
 * procesos enteros de negocio. Por ejemplo, para procesar una venta,
 * llama a ControlVentas para registrarla y a ControlCarrito para
 * vaciar los productos seleccionados en la sesión
 * 
 * @author Andre
 */
public class FachadaVentas implements IFachadaVentas {

    //Fábrica de BO que inyecta a los controles
    private final IFabricaBO fabricaBO = FabricaBO.singleton();
    
    //Controles
    private final ControlCatalogoBodega controlCatalogo = new ControlCatalogoBodega(fabricaBO.fabricarPieza());
    private final ControlVentas controlVentas = new ControlVentas(fabricaBO.fabricarPieza(), fabricaBO.fabricarVenta());
    private final ControlCarrito controlCarrito = new ControlCarrito();
    
    /** Constructor vacío */
    public FachadaVentas() {}
    
    public PiezaDTO consultarPieza(Long id) {
        return controlCatalogo.consultarPieza(id);
    }
    
    /**
     * Consulta las piezas del sistema
     * 
     * @return lista de tipo PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        return controlCatalogo.consultarPiezas();
    }

    /**
     * Consulta las piezas más vendidas en el día del sistema
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopDiaPiezas() {
        return controlCatalogo.consultarPiezas();
    }

    /**
     * Consulta las piezas más vendidas en la semana del sistema
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopSemanaPiezas() {
        return controlCatalogo.consultarPiezas();
    }

    /**
     * Consulta las piezas más vendidas en el mes del sistema
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopMesPiezas() {
        return controlCatalogo.consultarPiezas();
    }

    /**
     * Consulta las piezas más vendidas en todo el tiempo del sistema
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopTodoPiezas() {
        return controlCatalogo.consultarPiezas();
    }
    
    /**
     * Consulta todas las ventas del sistema
     *
     * @return lista de VentaDTO
     */
    @Override
    public List<VentaDTO> consultarVentas() {
        return controlVentas.consultarVentas();
    }
    
    /**
     * Orquesta la lógica de procesar una venta (registro de
     * la venta, actualización de stock)
     * 
     * @param venta a procesar
     * 
     * @return la venta registrada
     */
    @Override
    public VentaDTO procesarVenta(VentaDTO venta) {
        controlVentas.procesarVenta(venta);
        controlCarrito.limpiarCarritoVenta();
        return venta;
    }

    /**
     * Regresa el carrito
     * 
     * @return lista de detalles
     */
    @Override
    public List<DetallesVentaDTO> getCarritoVenta() {
        return controlCarrito.getCarritoVenta();
    }

    /**
     * Agrega un detalle
     * 
     * @param detalle 
     */
    @Override
    public void agregarCarritoVenta(DetallesVentaDTO detalle) {
        controlCarrito.agregarCarritoVenta(detalle);
        controlCarrito.agruparDetalles();
    }

    /**
     * Elimina un detalle
     * 
     * @param detalle 
     */
    @Override
    public void eliminarCarritoVenta(DetallesVentaDTO detalle) {
        controlCarrito.eliminarCarritoVenta(detalle);
        controlCarrito.agruparDetalles();
    }

    /** Regresa el total */
    @Override
    public double totalCarritoVenta() {
        return controlCarrito.totalCarritoVenta();
    }

    /**
     * Determina si está vacío el carrito
     * 
     * @return true o false
     */
    @Override
    public boolean carritoVentaVacio() {
        return controlCarrito.carritoVentaVacio();
    }

    //Vacía el carrito
    @Override
    public void limpiarCarritoVenta() {
        controlCarrito.limpiarCarritoVenta();
    }

    /**
     * Calcula el stock disponible de cierta pieza aún en medio proceso de la
     * venta. Sirve para validaciones rápidas y lógica de experiencia de
     * usuario. Aunque existan 20 piezas en la BD, si ya elegiste 10, y quieres
     * otras 15, no podrás elegirlas
     *
     * @param id de la pieza a calcular stock antes de la venta
     *
     * @return cantidad de stock de dicha pieza
     */
    @Override
    public int calcularStockAntesVenta(Long id) {
        return controlCarrito.calcularStockAntesVenta(id);
    } 

    @Override
    public boolean existePiezaCarrito(Long id) {
        return controlCarrito.existePiezaCarrito(id);
    }

    @Override
    public List<PiezaDTO> filtrarPorNombre(String nombre) {
        return controlCatalogo.filtrarPorNombre(nombre);
    }

    @Override
    public List<PiezaDTO> filtrarPorCategoria(String categoria) {
        return controlCatalogo.filtrarPorCategoria(categoria);
    }

    @Override
    public List<PiezaDTO> filtrarPorMarca(String marca) {
        return controlCatalogo.filtrarPorMarca(marca);
    }

    @Override
    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo) {
        return controlCatalogo.filtrarPorPrecioMax(precioMaximo);
    }
}