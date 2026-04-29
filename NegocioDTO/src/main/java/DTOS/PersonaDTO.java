package DTOS;

/**
 * DTO abstracto que representa a una persona, que siempre tiene
 * atributos básicos, como su nombre
 * 
 * @author Andre
 */
public abstract class PersonaDTO extends DTO {
    
    protected String nombres;
    protected String apellidoPaterno;
    protected String apellidoMaterno;

    public PersonaDTO() {}

    public PersonaDTO(String nombres, String apellidoPaterno, String apellidoMaterno) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    
    public String nombreCompleto() {
        return nombres + " " + apellidoPaterno + " " + apellidoMaterno;
    }
}