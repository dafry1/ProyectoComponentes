/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import dominio.Empleado;
import excepciones.PersistenciaException;
import interfaces.IEmpleadoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Andre
 */
public class EmpleadoDAO implements IEmpleadoDAO {
    private static final Logger LOG = Logger.getLogger(EmpleadoDAO.class.getName());
    private static final String CAMPOS_VACIOS = "No se puede iniciar sesión con campos faltantes";
    private static final String EMPLEADO_INEXISTENTE = "No existe el empleado en el sistema";
    
    private static final List<Empleado> EMPLEADOS = new ArrayList<>();

    public EmpleadoDAO() {}

    static {
        Empleado empleado1 = new Empleado();
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
    public List<Empleado> consultarEmpleados() {
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
    public Empleado verificarEmpleado(String nombreUsuario, String password) {
        
        //Excepción si los campos están vacíos
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() || password == null) {
            LOG.warning(">> " + CAMPOS_VACIOS);
            throw new PersistenciaException(CAMPOS_VACIOS);
        }

        //Itera y valida en la lista de empleados
        for (Empleado empleado: consultarEmpleados()) {
            if (datosCoincidentes(empleado, nombreUsuario, password)) {
                LOG.warning(">> Empleado encontrado: " + nombreUsuario + ", " + password);
                return empleado;
            }
        }
        LOG.warning("El usuario '" + nombreUsuario + "' no existe en el sistema.");
        throw new PersistenciaException("El usuario no existe");
    }
    
    //AUXILAIR TEMPORAL NOMAS MIENTRAS ENCHUFAMOS EL DAO OE
    private boolean datosCoincidentes(Empleado empleado, String usuario, String contra) {
        String nombre = empleado.getNombreUsuario();
        String con = empleado.getContrasenia();
        return nombre.equals(usuario) && con.equals(contra);
    }
    
}
