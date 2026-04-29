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

    /**
     * Recibe el builder
     * 
     * @param builder 
     */
    private ClienteDTO(Builder builder) {
      this.setNombres(builder.nombres);
      this.setApellidoPaterno(builder.apellidoPaterno);
      this.setApellidoMaterno(builder.apellidoMaterno);
      this.correo = builder.correo;
      this.telefono = builder.telefono;
    }
    
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
    
    /**
     * Builder que recibe
     * 
     */
    public static class Builder {
       private String nombres;
       private String apellidoPaterno;
       private String apellidoMaterno;
       private String correo;
       private String telefono;

       //Atributos internos del builder
       public Builder nombres(String nombres) { this.nombres = nombres; return this; }
       public Builder apellidoPaterno(String apP) { this.apellidoPaterno = apP; return this; }
       public Builder apellidoMaterno(String apM) { this.apellidoMaterno = apM; return this; }

       public Builder correo(String correo) { this.correo = correo; return this; }
       public Builder telefono(String telefono) { this.telefono = telefono; return this; }

       //Regresa el m étodo ya 
       public ClienteDTO build() {
           return new ClienteDTO(this);
       }
    }
}