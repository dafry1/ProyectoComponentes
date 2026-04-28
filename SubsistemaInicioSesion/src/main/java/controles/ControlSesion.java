package controles;

import DTOS.EmpleadoDTO;
import fabricas.FabricaBO;
import fachada.FachadaInicioSesion;
import fachadas.FachadaVentas;
import interfaces.IFabricaBO;
import interfaces.IEmpleadoBO;
import interfaces.IFachadaInicioSesion;
import interfaces.IFachadaVentas;
import java.util.List;

/**
 * Control que accede directamente a los BO encargados
 * del inicio de sesión de un empleado
 * 
 * @author Andre
 */
public class ControlSesion {
    
    //BOs necesarios
    private final IEmpleadoBO empleadoBO;
    private EmpleadoDTO usuarioLogueado;
    private boolean administrador = false;
    
    /**
     * Constructor con fábrica inyectada
     * 
     * @param empleadoBO para trabajar
     */
    public ControlSesion(IEmpleadoBO empleadoBO) {
        this.empleadoBO = empleadoBO;
    }
    
    /**
     * Llama a empleadoBO para buscar un empleado en específico
     * 
     * @param nombreUsuario del empleado
     * @param contra del empleado
     * 
     * @return el empleado
     */
    public EmpleadoDTO verificarEmpleado(String nombreUsuario, String contra) {
        return empleadoBO.verificarEmpleado(nombreUsuario, contra);
    }
    
     /**
     * Regresa el empleado que está usando el sistema actualmente
     *
     * @return
     */
    public EmpleadoDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }

    /**
     * Guarda el empleado actual de manera global
     * 
     * @param empleado dueño de la sesión
     */
    public void establecerSesion(EmpleadoDTO empleado) {
        this.usuarioLogueado = empleado;
        if (empleado != null && empleado.getId() != null) {
            this.administrador = (empleado.getId() == 1L);
        }
    }
    
    /**
     * Cierra la sesión limpiando los datos
     */
    public void cerrarSesion() {
        this.usuarioLogueado = null;
        this.administrador = false;
    }
    
    /**
     * Indica si la sesión actual le pertenece a un administrador
     *
     * @return
     */
    public boolean esAdministrador() {
        return administrador;
    }
    
}