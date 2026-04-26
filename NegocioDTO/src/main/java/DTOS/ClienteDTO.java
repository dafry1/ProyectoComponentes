package DTOS;

/**
 * ClienteDTO.java
 * 
 * Clase que representa la información de los clientes que interactuaran con
 * el programa.
 * 
 * @author Aaron
 */
public class ClienteDTO extends PersonaDTO {
    private String correo;
    private String telefono;

    /**
     * Constructor vacio de la clase ClienteDTO.
     */
    public ClienteDTO(){}

    public ClienteDTO(String correo, String telefono, String nombres, String apellidoPaterno, String apellidoMaterno) {
        super(nombres, apellidoPaterno, apellidoMaterno);
        this.correo = correo;
        this.telefono = telefono;
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public ClienteDTO build() {
        return new ClienteDTO();
    }
}