/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;

import DTOS.EmpleadoDTO;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IFachadaInicioSesion {
    EmpleadoDTO getUsuarioLogueado();
    EmpleadoDTO verificarEmpleado(String usuario, String contra);
    void establecerSesion(EmpleadoDTO empleado);
    public void cerrarSesion();
    public boolean esAdministrador();
}
