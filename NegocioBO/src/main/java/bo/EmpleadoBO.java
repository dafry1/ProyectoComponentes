package bo;

import DTOS.EmpleadoDTO;
import excepciones.NegocioException;
import interfaces.IEmpleadoBO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * BO de un empleado
 * 
 * @author DANIEL
 */
public class EmpleadoBO implements IEmpleadoBO {
    private static final Logger LOG = Logger.getLogger(EmpleadoBO.class.getName());
    private static final String CAMPOS_VACIOS = "No se puede iniciar sesión con campos faltantes";
    private static final String EMPLEADO_INEXISTENTE = "No existe el empleado en el sistema";
    
    private static final List<EmpleadoDTO> EMPLEADOS = new ArrayList<>();

    public EmpleadoBO() {}

    static {
        EmpleadoDTO empleado1 = new EmpleadoDTO();
        empleado1.setNombreUsuario("juanperes1");
        empleado1.setContrasenia("12345");
        empleado1.setNombres("Daniel");
        empleado1.setApellidoPaterno("Ruiz");
        empleado1.setApellidoMaterno("Jocobi");
        EMPLEADOS.add(empleado1);
    }
    
    /**
     * Extrae todos los empleados de la BD
     *
     * @return lista de EmpleadoDTO mapeadas
     */
    @Override
    public List<EmpleadoDTO> consultarEmpleados() {
        return EMPLEADOS;
    }

    
    
    //-> FIXME: OBVIAMENTE ASÍ NO FUNCIONARÁ EN EL PROGRAMA COMPLETO
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
        
        //Excepción si los campos están vacíos
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() || password == null) {
            LOG.warning(">> " + CAMPOS_VACIOS);
            throw new NegocioException(CAMPOS_VACIOS);
        }

        //Itera y valida en la lista de empleados
        for (EmpleadoDTO empleado: consultarEmpleados()) {
            if (datosCoincidentes(empleado, nombreUsuario, password)) {
                LOG.warning(">> Empleado encontrado: " + nombreUsuario + ", " + password);
                return empleado;
            }
        }
        LOG.warning("El usuario '" + nombreUsuario + "' no existe en el sistema.");
        throw new NegocioException("El usuario no existe");
    }
    
    //AUXILAIR TEMPORAL NOMAS MIENTRAS ENCHUFAMOS EL DAO OE
    private boolean datosCoincidentes(EmpleadoDTO empleado, String usuario, String contra) {
        String nombre = empleado.getNombreUsuario();
        String con = empleado.getContrasenia();
        return nombre.equals(usuario) && con.equals(contra);
    }
}
