package DTOS;

/**
 * EmpleadoDTO.java
 * 
 * Clase que representa la información de los empleados que interactuaran con
 * el programa.
 * 
 * @author Aaron
 */
public class EmpleadoDTO {
    private Long id;
    private String nombreUsuario;
    private String contrasenia;
    
    /**
     * Constructor vacio de la clase EmpleadoDTO.
     */
    public EmpleadoDTO(){}

    /**
     * Método para obtener el valor del ID del empleado.
     * 
     * @return Valor del ID del empleado.
     */
    public Long getID(){
        return id;
    }
    
    /**
     * Método para asignar el valor del ID al empleado.
     * 
     * @param id Valor del ID a asignar al empleado.
     */
    public void setID(Long id){
        this.id = id;
    }
    
    /**
     * Método para obtener el valor del nombre de usuario del empleado.
     * 
     * @return Valor del nombre de usuario del empleado.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Método para asignar el valor del nombre de usuario al empleado.
     * 
     * @param nombreUsuario Valor del nombre de usuario a asignar al empleado.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Método para obtener el valor de la contraseña del usuario del empleado.
     * 
     * @return Valor de la contraseña del usuario del empleado.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Método para asignar el valor de la contraseña al usuario del empleado.
     * 
     * @param contrasenia Valor de la contraseña a asignar al usuario del empleado.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}