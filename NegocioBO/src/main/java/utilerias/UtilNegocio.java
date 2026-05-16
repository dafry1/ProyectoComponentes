package utilerias;

import DTOS.ClienteDTO;
import DTOS.EmpleadoDTO;
import DTOS.PersonaDTO;
import excepciones.NegocioException;
import java.lang.System.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Diferentes validaciones y métodos de negocio
 * 
 * @author Andre
 */
public final class UtilNegocio {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private UtilNegocio(){}
    
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
    
    
    public static String encriptar(String texto) {
        return Encriptador.encriptar(texto);
    }
    
    public static String desencrpitar(String texto) {
        return Encriptador.desencriptar(texto);
    }
    
    /**
     * Obtiene la fecha actual
     * 
     * @return fecha y hora actual
     */
    public static LocalDateTime hoy() {
        return LocalDateTime.now().withNano(0);
    }
    
    public static String hoyTexto() {
        return fechaString(hoy());
    }
    
    /**
     * Convierte un LocalDateTime a String usando el formato de la aplicación
     * 
     * @param fecha Objeto LocalDateTime a transformar
     * 
     * @return String formateado, o null si la fecha recibida es nula
     */
    public static String fechaString(LocalDateTime fecha) {
        if (fecha == null) {
            return null;
        }
        return fecha.format(FORMATO_FECHA);
    }
    
    /**
     * Convierte un String a LocalDateTime usando el formato de la aplicación
     * 
     * @param textoFecha Cadena de texto con la fecha
     * 
     * @return Objeto LocalDateTime, o null si el texto es inválido o no se puede parsear
     */
    public static LocalDateTime stringFecha(String textoFecha) {
        if (textoFecha == null || textoFecha.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(textoFecha, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new NegocioException("Error al convertir el String a fecha");
        }
    }
}