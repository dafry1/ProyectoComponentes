package DTOS;

/**
 *
 * @author DANIEL
 */
public class EmpleadoDTO extends PersonaDTO {
    private String nombreUsuario;

    public EmpleadoDTO() {
    }

    public EmpleadoDTO(String nombreUsuario, String nombres, String apellidoPaterno, String apellidoMaterno) {
        super(nombres, apellidoPaterno, apellidoMaterno);
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}