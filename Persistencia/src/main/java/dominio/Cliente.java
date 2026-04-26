package dominio;

/**
 * Atributo embebido dentro de una Venta
 * 
 * @author Andre
 */
public class Cliente extends Persona {
    private String Correo;
    private String Telefono;

    public Cliente() {}

    public Cliente(String Correo, String Telefono, String nombres, String apellidoPaterno, String apellidoMaterno) {
        super(nombres, apellidoPaterno, apellidoMaterno);
        this.Correo = Correo;
        this.Telefono = Telefono;
    }
    
    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }
}