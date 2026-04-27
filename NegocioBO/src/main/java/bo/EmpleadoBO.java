package bo;

import DTOS.EmpleadoDTO;
import dominio.Empleado;
import excepciones.NegocioException;
import interfaces.IAdaptadorEmpleado;
import interfaces.IEmpleadoBO;
import interfaces.IEmpleadoDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * BO de un empleado
 * 
 * @author DANIEL
 */
public class EmpleadoBO implements IEmpleadoBO {

    private static final System.Logger LOG = System.getLogger(EmpleadoBO.class.getName());
    
    private static final String CAMPOS_VACIOS = "No se puede iniciar sesión con campos faltantes";
    private static final String EMPLEADO_INEXISTENTE = "No existe el empleado en el sistema";
    private static final List<EmpleadoDTO> EMPLEADOS = new ArrayList<>();
    
    //Atributos
    private IEmpleadoDAO empleadoDAO;
    private IAdaptadorEmpleado adaptadorEmpleado;
    
    public EmpleadoBO(IEmpleadoDAO empleadoDAO, IAdaptadorEmpleado adaptadorEmpleado) {
        this.empleadoDAO = empleadoDAO;
        this.adaptadorEmpleado = adaptadorEmpleado;
    }
    
    /** Centraliza la forma en la que se adapta de Entidad a DTO */
    private List<EmpleadoDTO> adaptarEmpleadosInternamente(List<Empleado> empleados) {
        return adaptadorEmpleado.listaDTO(empleados);
    }
    
    /**
     * Extrae todos los empleados de la BD
     *
     * @return lista de EmpleadoDTO mapeadas
     */
    @Override
    public List<EmpleadoDTO> consultarEmpleados() {
        return adaptarEmpleadosInternamente(empleadoDAO.consultarEmpleados());
    }
    
    /**
     * Consulta en la BD al empleado con los datos especificados
     * 
     * @param nombreUsuario del empleado
     * @param password contraseña del empleado
     * 
     * @return el empleado encontrado
     */
    @Override
    public EmpleadoDTO verificarEmpleado(String nombreUsuario, String password) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            String DEBUG = "Nombre de usuario vacío";
            LOG.log(System.Logger.Level.ERROR, DEBUG);
            throw new NegocioException(">>" + DEBUG);
        }
        if (password == null || password.isBlank()) {
            String DEBUG = "Contraseña vacía";
            LOG.log(System.Logger.Level.ERROR, DEBUG);
            throw new NegocioException(">>" + DEBUG);
        }
        return adaptadorEmpleado.DTO(empleadoDAO.verificarEmpleado(nombreUsuario, password));
    }
}