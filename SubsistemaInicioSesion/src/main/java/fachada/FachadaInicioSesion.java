package fachada;

import DTOS.EmpleadoDTO;
import controles.ControlSesion;
import excepciones.NegocioException;
import fabricas.FabricaBO;
import fabricas.IFabricaBO;
import java.util.List;

/**
 * Fachada del subsistema de iniciar sesión
 * 
 * @author Andre
 */
public class FachadaInicioSesion implements IFachadaInicioSesion {

    private static final System.Logger LOG = System.getLogger(FachadaInicioSesion.class.getName());
    
    
    //Fábrica que inyecta a los controles
    IFabricaBO fabricaBO = FabricaBO.singleton();
    
    //Controles
    private ControlSesion controlSesion = new ControlSesion(fabricaBO.fabricarEmpleado());
    
    @Override
    public EmpleadoDTO iniciarSesion(String usuario, String contra) {
        try {
            EmpleadoDTO empleado = controlSesion.verificarEmpleado(usuario, contra);
            if (empleado == null) {
                throw new NegocioException("No se encontró el usuario");
            }
            controlSesion.establecerSesion(empleado);
            return empleado;
        } catch (NegocioException e) {
            String MSJ = "Error al iniciar sesión: " + e.getMessage();
            LOG.log(System.Logger.Level.ALL, MSJ);
            throw new NegocioException(MSJ);
        }
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
    
    /** Cierra la sesión */
    @Override
    public void cerrarSesion() {
        controlSesion.cerrarSesion();
    }
}