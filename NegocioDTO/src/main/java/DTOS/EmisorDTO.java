package DTOS;

/**
 *
 * @author Andre
 */
public class EmisorDTO extends ParticipanteDTO {
    private String razonSocial;
    
    public EmisorDTO() {
    }

    public EmisorDTO(String razonSocial, String cfdi, String regimenFiscal, String correo, String celular, DireccionDTO direccion, String id) {
        super(cfdi, regimenFiscal, correo, celular, direccion, id);
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
}
