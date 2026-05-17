/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import conexiones.ConexionSQL;
import daos.IEmpleadoDAO;
import dominio.Empleado;
import java.util.List;

/**
 *
 * @author Andre
 */
public class EmpleadoJDBC implements IEmpleadoDAO {
    private final ConexionSQL conexion;
    public EmpleadoJDBC(ConexionSQL conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Empleado> consultarEmpleados() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Empleado verificarEmpleado(String nombreUsuario, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insertarEmpleado(Empleado empleado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
