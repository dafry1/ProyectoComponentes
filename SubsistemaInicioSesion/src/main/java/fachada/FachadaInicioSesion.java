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
}