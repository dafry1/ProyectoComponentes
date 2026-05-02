/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos;

import dominio.Empleado;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IEmpleadoDAO {
    List<Empleado> consultarEmpleados();
    
    /**
     * Consulta en la BD al empleado con los datos especificados
     * 
     * @param nombreUsuario del empleado
     * @param password contraseña del empleado
     * 
     * @return el empleado encontrado
     */
    Empleado verificarEmpleado(String nombreUsuario, String password);
}
