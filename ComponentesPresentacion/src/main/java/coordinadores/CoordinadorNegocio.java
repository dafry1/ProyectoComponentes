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
    private IFachadaVentas fachadaVentas = new FachadaVentas();
    private IFachadaInicioSesion fachadaInicioSesion = new FachadaInicioSesion();
    
    //Coordinador auxiliar
    
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
     * Regresa todas las ventas del sistema, dadas directamente por el
     * IFachadaVentas
     *
     * @return lista de VentaDTO
     */
    @Override
    public List<VentaDTO> consultarVentas() {
        return fachadaVentas.consultarVentas();
    }
    
    /**
     * Procesa la venta de la fachada y verifica lo que le importa
     * de presentación: el observador
     * 
     * @param venta a procesar
     * @param observador si se necesita actualizar algo. Puede ser null
     */
    @Override
    public VentaDTO procesarVenta(VentaDTO venta, IObservador observador) {
        
        //Excepción si es null
        if (venta == null) {
            throw new PresentacionException("Venta vacía");
        }
        
        //Procesa la venta directamente de la fachada
        fachadaVentas.procesarVenta(venta);

        //TEMPORAL LIMPIA EL CARRITO!!
        CoordinadorEstados.singleton().limpiarCarritoVenta();
        
        //Activa al observador si existe
        if (observador != null) {
            observador.observar();
        }
        
        //Regresa la venta
        System.out.println("Venta" + venta.getDetalles().size());
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
    
    /**
     * Consulta todos los empleados de la BD
     * 
     * @return 
     */
    @Override
    public List<EmpleadoDTO> consultarEmpleados() {
        return fachadaInicioSesion.consultarEmpleados();
    }
}