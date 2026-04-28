package utilEstilos;

/**
 * Utilerías donde se verifica lógica de presentación de forma instantánea, como
 * validar un número o un String. Sirve para no despertar innecesariamente a la
 * capa de Negocio, encargado de lógica más pesada. Podríamos decir que aquí está
 * lo, relativamente, universal. No vas a escribir "abc" si te preguntan por cantidad
 * 
 * @author Andre
 */
public class UtilFormato {
    
    /**
     * Valida si un número (aún en String fresco de un formulario)
     * es mayor a 0, sin decimales. Confirma con el regex que sean
     * únicamente números del 1 al 9.
     * 
     * @param numero en String para validar
     * 
     * @return true si es entero positivo, false de lo contrario
     */
    public static boolean numeroEnteroPositivo(String numero) {
        return numero.matches("[1-9]\\d*");
    }
    
    /**
     * Metodo de validacion para nombres, tnato
     * de pila como 
     * 
     * @param nombre
     * @return boolean
     */
    public static boolean validarNombre(String nombre) {
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]{1,100}$";
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
}