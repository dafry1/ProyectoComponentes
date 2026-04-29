package DTOS;

/**
 * Típico DTO dirección que indica a una solicitud dónde 
 * va a parar
 * 
 * @author Andre
 */
public class DireccionDTO extends DTO {
    
    //Atributos
    String calle;
    String colonia;
    String numero;
    String codigoPostal;

    public DireccionDTO() {
    }

    public DireccionDTO(String calle, String colonia, String numero, String codigoPostal) {
        this.calle = calle;
        this.colonia = colonia;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }

    public DireccionDTO(String calle, String colonia, String numero, String codigoPostal, Long id) {
        super(id);
        this.calle = calle;
        this.colonia = colonia;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }
    
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}