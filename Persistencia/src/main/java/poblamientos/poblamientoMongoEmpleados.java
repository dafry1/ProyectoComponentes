/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poblamientos;

import daos.IEmpleadoDAO;
import dominio.Empleado;
import java.util.List;

/**
 *
 * @author DANIEL
 */
public class poblamientoMongoEmpleados {
 private IEmpleadoDAO empleadoDAO;

    public poblamientoMongoEmpleados(IEmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }
 
    public void poblarEmpleados(){
        List<Empleado> empleados = List.of(
        new Empleado("yupityson", "miau12", "Andre", "Vega", "Menchaca"),
        new Empleado("Redondinho", "papafrita1", "Aaron Uriel", "Quintero", "Morales"),
        new Empleado("PablitoPro", "planta0", "Tadeo", "Murio", "En Reach"),
        new Empleado("Mamamasivo", "muriqui1", "va vithina para", "doue doue doue doue", "ah ah aha ah")
        );
        empleados.forEach(empleado -> empleadoDAO.insertarEmpleado(empleado));
        System.out.println("Base de datos poblada con empleados");
    }
}
