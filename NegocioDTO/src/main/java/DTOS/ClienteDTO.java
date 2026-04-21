package DTOS;

/**
 * ClienteDTO.java
 * 
 * Clase que representa la información de los clientes que interactuaran con
 * el programa.
 * 
 * @author Aaron
 */
public class ClienteDTO extends DTO {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String direccion;

    /**
     * Constructor vacio de la clase ClienteDTO.
     */
    public ClienteDTO(){}
    
    /**
     * Método para obtener el valor del nombre del cliente.
     * 
     * @return Valor del nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para asignar el valor del nombre al cliente.
     * 
     * @param nombre Valor del nombre a asignar al cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método para obtener el valor del apellido paterno del cliente.
     * 
     * @return Valor del apellido paterno del cliente.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Método para asignar el valor del apellido paterno al cliente.
     * 
     * @param apellidoPaterno Valor del apellido paterno a asignar al cliente.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Método para obtener el valor del apellido materno del cliente.
     * 
     * @return Valor del apellido materno del cliente.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Método para asignar el valor del apellido materno al cliente.
     * 
     * @param apellidoMaterno Valor del apellido materno a asignar al cliente.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Método para obtener el valor de la dirección del cliente.
     * 
     * @return Valor de la dirección del cliente.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Método para asignar el valor de la dirección al cliente.
     * 
     * @param direccion Valor de la dirección a asignar al cliente.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}