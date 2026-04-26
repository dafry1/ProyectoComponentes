package excepciones;

/**
 * Excepción para errores producidos a la hora de
 * trabajar con la BD
 * 
 * @author Andre
 */
public class PersistenciaException extends RuntimeException {

    /** Constructor vacío */
    public PersistenciaException() {
        super();
    }

    /**
     * COnstructor con mensaje
     * 
     * @param mensaje el mensaje que se mostrará al usuario
     */
    public PersistenciaException(String mensaje) {
        super(mensaje);
    }
}