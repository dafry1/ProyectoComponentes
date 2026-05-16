package bo;

import DTOS.EmpleadoDTO;
import adaptadores.AdaptadorEmpleado;
import dominio.Empleado;
import excepciones.NegocioException;
import daos.IEmpleadoDAO;
import excepciones.PersistenciaException;
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
    
    public EmpleadoBO(IEmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }
    
    /** Centraliza la forma en la que se adapta de Entidad a DTO */
    private List<EmpleadoDTO> adaptarEmpleadosInternamente(List<Empleado> empleados) {
        return AdaptadorEmpleado.listaDTO(empleados);
    }
    
    /**
     * Extrae todos los empleados de la BD
     *
     * @return lista de EmpleadoDTO mapeadas
     */
    @Override
    public List<EmpleadoDTO> consultarEmpleados() {
        try {
            return adaptarEmpleadosInternamente(empleadoDAO.consultarEmpleados());
        } catch (PersistenciaException e) {
            String MSJ = "Error al consultar los empleados: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
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
        try {
            return AdaptadorEmpleado.DTO(empleadoDAO.verificarEmpleado(nombreUsuario, password));
        } catch (PersistenciaException e) {
            String MSJ = "Error al verificar empleado: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        } 
    }
}