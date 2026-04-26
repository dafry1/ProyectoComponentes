package fachada;

import DTOS.EmpleadoDTO;
import controles.ControlEmpleados;
import controles.ControlSesion;
import fabricas.FabricaBO;
import interfaces.IFabricaBO;
import interfaces.IFachadaInicioSesion;
import java.util.List;

/**
 * Fachada del subsistema de iniciar sesión
 * 
 * @author Andre
 */
public class FachadaInicioSesion implements IFachadaInicioSesion {
    
    //Fábrica que inyecta a los controles
    IFabricaBO fabricaBO = FabricaBO.singleton();
    
    //Controles
    private ControlSesion controlSesion = new ControlSesion(fabricaBO);
    private ControlEmpleados controlEmpleados = new ControlEmpleados(fabricaBO);
    
    
    /**
     * Consulta todos los empleados de la BD
     * 
     * @return 
     */
    @Override
    public List<EmpleadoDTO> consultarEmpleados() {
        return controlEmpleados.consultarEmpleados();
    }
    
    /**
     * Regresa el empleado que está usando el sistema actualmente
     *
     * @return
     */
    @Override
    public EmpleadoDTO getUsuarioLogueado() {
        return controlSesion.getUsuarioLogueado();
    }
 
    
    /**
     * Verifica la existencia de un empleado
     * 
     * @param usuario
     * @param contra
     * @return 
     */
    @Override
    public EmpleadoDTO verificarEmpleado(String usuario, String contra) {
        return controlSesion.verificarEmpleado(usuario, contra);
    }
    
     /**
     * Guarda el empleado actual de manera global
     * 
     * @param empleado dueño de la sesión
     */
    @Override
    public void establecerSesion(EmpleadoDTO empleado) {
        controlSesion.establecerSesion(empleado);
    }
    
    @Override
    public void cerrarSesion() {
        controlSesion.cerrarSesion();
    }
    
    /**
     * Indica si la sesión actual le pertenece a un administrador
     *
     * @return
     */
    @Override
    public boolean esAdministrador() {
        return controlSesion.esAdministrador();
    }
    
}