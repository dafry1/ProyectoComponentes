package utilerias;

import DTOS.ClienteDTO;
import DTOS.EmpleadoDTO;
import DTOS.PersonaDTO;

/**
 * Diferentes validaciones de negocio
 * 
 * @author Andre
 */
public class UtilNegocio {
    
    /**
     * Metodo de validacion para nombres, tanto
     * de pila como apellido
     * 
     * @param nombre
     * @return boolean
     */
    public static boolean validarNombre(String nombre) {
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$";
        return nombre.matches(regex);

    }
    
    /**
     * Metodo de validacion para el correo electronico
     * 
     * @param correo
     * @return boolean
     */
    public static boolean validarCorreo(String correo) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return correo.matches(regex);
    }
    
    /**
     * Metodo de validacion del telefono
     * 
     * @param telefono
     * @return boolean
     */
    public static boolean validarTelefono(String telefono) {
        String regex = "^[0-9]{10}$";
        return telefono.matches(regex);
    }
    
    /**
     * Valida una persona
     * 
     * @param persona
     * @return 
     */
    public static boolean validarPersona(PersonaDTO persona) {
        boolean nValido = validarNombre(persona.getNombres());
        boolean apValido = validarNombre(persona.getApellidoPaterno());
        boolean amValido = validarNombre(persona.getApellidoMaterno());
        return nValido && apValido && amValido;
    }
    
    public static boolean validarCliente(ClienteDTO cliente) {
        //Primero valida al cliente como persona
        if (!validarPersona(cliente)) {
            return false;
        }
        
        //Valida sus atributos individuales
        boolean cValido = validarCorreo(cliente.getCorreo());
        boolean tValido = validarTelefono(cliente.getTelefono());
        return cValido && tValido;
    }
    
    /**
     * Parece un wrapper innecesario, pero protege por si
     * la lógica para validar un empleado crece
     * 
     * @param empleado
     * @return 
     */
    public static boolean validarEmpleado(EmpleadoDTO empleado) {
        return validarPersona(empleado);
    }
}