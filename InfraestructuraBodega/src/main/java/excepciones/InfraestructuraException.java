package excepciones;

/**
 * Excepción para errores producidos a la hora de
 * conectarse con la API
 * 
 * @author Andre
 */
public class InfraestructuraException extends RuntimeException {

    /** Constructor vacío */
    public InfraestructuraException() {
        super();
    }

    /**
     * COnstructor con mensaje
     * 
     * @param mensaje el mensaje que se mostrará al usuario
     */
    public InfraestructuraException(String mensaje) {
        super(mensaje);
    }
}