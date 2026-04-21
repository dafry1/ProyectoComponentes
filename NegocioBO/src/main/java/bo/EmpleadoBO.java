/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import interfaces.IEmpleadoBO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author DANIEL
 */
public class EmpleadoBO implements IEmpleadoBO {

    private static final Logger LOG = Logger.getLogger(EmpleadoBO.class.getName());
    private static EmpleadoBO empleadoBO;
    private static final List<EmpleadoDTO> EMPLEADOS = new ArrayList<>();

    public EmpleadoBO() {
    }

    static {
        EmpleadoDTO empleado1 = new EmpleadoDTO();
        empleado1.setNombreUsuario("juanperes1");
        empleado1.setContrasenia("12345");
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

    /**
     * Obtenemos la instancia de EmpleadoBO.
     *
     * @return EmpleadoBO.
     */
    public static EmpleadoBO getInstanceEmpleadoBO() {
        if (empleadoBO == null) {
            empleadoBO = new EmpleadoBO();
        }
        return empleadoBO;
    }

    @Override
    public EmpleadoDTO login(String usuario, String password) {
    if (usuario == null || usuario.trim().isEmpty() || password == null) {
        LOG.warning("Se intentó un login con campos nulos o vacíos.");
        return null; 
    }

    for (EmpleadoDTO emp : EMPLEADOS) {
        if (emp.getNombreUsuario().equals(usuario)) {
            if (emp.getContrasenia().equals(password)) {
                LOG.info("Login exitoso: " + usuario);
                return emp;
            } else {
                LOG.warning("Contraseña incorrecta para el usuario: " + usuario);
                return null; 
            }
        }
    }
    LOG.warning("El usuario '" + usuario + "' no existe en el sistema.");
    return null;
}
}
