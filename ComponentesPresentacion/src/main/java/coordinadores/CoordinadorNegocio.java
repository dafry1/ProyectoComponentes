package coordinadores;

import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import excepciones.PresentacionException;
import excepciones.NegocioException;
import fachada.FachadaInicioSesion;
import fachadas.FachadaVentas;
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
public class CoordinadorNegocio implements ICoordinadorNegocio {
    private static final System.Logger LOG = System.getLogger(CoordinadorNegocio.class.getName());
    
    //Instancia de la fachada del subsistema de las ventas
    //Ya lo creo a la brava porque ya me cansé de tanta fábrica :( TODO: FÁBRICA DE FACHADAS
    private IFachadaVentas fachadaVentas = new FachadaVentas();
    private IFachadaInicioSesion fachadaInicioSesion = new FachadaInicioSesion();
    
    //Constructor
    public CoordinadorNegocio() {
        //this.fachadaVentas = fachadaVentas;
        //this.fachadaInicioSesion = fachadaInicioSesion;
    };
    
    /**
     * Regresa todas las piezas del sistema, dadas directamente
     * por el IFachadaVentas
     * 
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        return fachadaVentas.consultarPiezas();
    }
    
    /**
     * Regresa la cantidad de piezas individuales. No de productos
     * en total, sino de cada tipo de pieza en stock. Se ve como
     * un wrapper simple, pero es importante no meter mano en
     * lógica de negocio como la lista directamente
     * 
     * @return la cantidad de tipos de piezas en específico
     */
    @Override
    public int totalProductos() {
        return fachadaVentas.consultarPiezas().size();
    }
    
    /**
     * Regresa las piezas más vendidas al día en el sistema, dadas directamente
     * por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopDiaPiezas() {
        return fachadaVentas.consultarTopDiaPiezas();
    }

    /**
     * Regresa las piezas más vendidas a la semana en el sistema, dadas
     * directamente por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopSemanaPiezas() {
        return fachadaVentas.consultarTopSemanaPiezas();
    }

    /**
     * Regresa las piezas más vendidas al mes en el sistema, dadas directamente
     * por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopMesPiezas() {
        return fachadaVentas.consultarTopMesPiezas();
    }

    /**
     * Regresa las piezas más vendidas en todo el tiempo en el sistema, dadas
     * directamente por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopTodoPiezas() {
        return fachadaVentas.consultarTopTodoPiezas();
    }
    
    /**
     * Orquesta todos los métodos necesarios para llevar a
     * cabo una venta dentro del sistema. Actualiza stock,
     * limpia el carrito, etc.
     * 
     * @param coordinadorEstados
     * @param observador si se necesita actualizar algo. Puede ser null
     */
    @Override
    public VentaDTO procesarVenta(ICoordinadorEstados coordinadorEstados, IObservador observador) {
        
        List<DetallesVentaDTO> carrito = coordinadorEstados.getCarritoVenta();
        
        //Excepción si la lista está vacía o el carrito es null
        if (carrito == null || carrito.isEmpty()) {
            throw new PresentacionException("El carrito está vacío");
        }
        
        //Procesa la venta directamente de la fachada
        VentaDTO venta = fachadaVentas.procesarVenta(null, carrito);
        
        //Limpia el carrito de ventas
        coordinadorEstados.limpiarCarritoVenta();
        
        //Activa al observador si existe
        if (observador != null) {
            observador.observar();
        }
        
        //Regresa la venta
        return venta;
    }
    
    
    
    /**
     * Valida que el usuario con los datos ingresados
     * exista dentro del sistema y en caso de éxito lo
     * almacena en el CoordinadorEstados
     * 
     * @param nombreUsuario que quier acceder al sistema
     * @param contra del usuario
     * 
     * @return el empleado DTO en caso de que exista 
     */
    @Override
    public EmpleadoDTO iniciarSesion(String nombreUsuario, String contra) {
        EmpleadoDTO empleado;
        
        //-> FIXME? TAL VEZ CUANDO EXISTA PERSISTENCIA NO SE IMPLEMENTE ASÍ
        try {
            empleado = fachadaInicioSesion.verificarEmpleado(nombreUsuario, contra);
            CoordinadorEstados.singleton().establecerSesion(empleado);
            LOG.log(System.Logger.Level.INFO, ">> Sesión iniciada con éxito; " + nombreUsuario); 
            return empleado;
        } catch (NegocioException e){
            throw new PresentacionException("No existe el empleado");
        }
    }
}