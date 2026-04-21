/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import DTOS.EmpleadoDTO;
import java.util.List;

/**
 *
 * @author DANIEL
 */
public interface IEmpleadoBO {
    public List<EmpleadoDTO> consultarEmpleados();
    public EmpleadoDTO login(String usuario, String password);
}
