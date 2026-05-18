package DTOS;

/**
 *
 * @author Andre
 */
public class ContribuyenteDTO extends ParticipanteDTO {
    
    //Atributos
    private String nombres, apellidoPaterno, apellidoMaterno;
    private String rfc;

    public ContribuyenteDTO() {
    }

    public ContribuyenteDTO(String nombres, String apellidoPaterno, String apellidoMaterno, String rfc, String cfdi, String regimenFiscal, String correo, String celular, DireccionDTO direccion, String id) {
        super(cfdi, regimenFiscal, correo, celular, direccion, id);
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.rfc = rfc;
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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
}
