package fachada;

import DTOS.EmpleadoDTO;
import controles.ControlInicioSesion;
import interfaces.IFachadaInicioSesion;
import java.util.List;

/**
 * Fachada del subsistema de iniciar sesión
 * 
 * @author Andre
 */
public class FachadaInicioSesion implements IFachadaInicioSesion {
    private ControlInicioSesion controlInicioSesion = new ControlInicioSesion();
    
    
    /**
     * Consulta todos los empleados de la BD
     * 
     * @return 
     */
    @Override
    public List<EmpleadoDTO> consultarEmpleados() {
        return controlInicioSesion.consultarEmpleados();
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
        return controlInicioSesion.verificarEmpleado(usuario, contra);
    }
}