/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOS;

/**
 *
 * @author Andre
 */
public class ParticipanteDTO extends DTO {
    private String cfdi, regimenFiscal;
    private String correo, celular;
    private DireccionDTO direccion;

    public ParticipanteDTO() {
    }

    public ParticipanteDTO(String cfdi, String regimenFiscal, String correo, String celular, DireccionDTO direccion, String id) {
        super(id);
        this.cfdi = cfdi;
        this.regimenFiscal = regimenFiscal;
        this.correo = correo;
        this.celular = celular;
        this.direccion = direccion;
    }

    public String getCfdi() {
        return cfdi;
    }

    public void setCfdi(String cfdi) {
        this.cfdi = cfdi;
    }

    public String getRegimenFiscal() {
        return regimenFiscal;
    }

    public void setRegimenFiscal(String regimenFiscal) {
        this.regimenFiscal = regimenFiscal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public DireccionDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionDTO direccion) {
        this.direccion = direccion;
    }
}
