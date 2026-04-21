/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import DTOS.EmpleadoDTO;
import bo.EmpleadoBO;
import interfaces.IEmpleadoBO;
import interfaces.IFachadaInicioSesion;
import java.util.List;

/**
 *
 * @author Andre
 */
public class FachadaInicioSesion implements IFachadaInicioSesion {
    
    
    private IEmpleadoBO empleadoBO = new EmpleadoBO();
    
    
    @Override
    public List<EmpleadoDTO> consultarEmpleados() {
        return empleadoBO.consultarEmpleados();
    }
    
    @Override
    public EmpleadoDTO login(String usuario, String contra) {
        return empleadoBO.login(usuario, contra);
    }
    
    
}
