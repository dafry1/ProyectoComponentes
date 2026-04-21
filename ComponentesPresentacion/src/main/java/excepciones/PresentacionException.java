package excepciones;

/**
 * Excepción para errores producidos en la capa de presentación
 * 
 * @author Andre
 */
public class PresentacionException extends RuntimeException {

    /** Constructor vacío */
    public PresentacionException() {
        super();
    }

    /**
     * COnstructor con mensaje
     * 
     * @param mensaje el mensaje que se mostrará al usuario
     */
    public PresentacionException(String mensaje) {
        super(mensaje);
    }
}