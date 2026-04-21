package excepciones;

/**
 * Excepción para errores producidos en toda la lógica de negocio
 * distribuida en los subsistemas, BO y mappers
 * 
 * @author Andre
 */
public class NegocioException extends RuntimeException {

    /** Constructor vacío */
    public NegocioException() {
        super();
    }

    /**
     * COnstructor con mensaje
     * 
     * @param mensaje el mensaje que se mostrará al usuario
     */
    public NegocioException(String mensaje) {
        super(mensaje);
    }
}