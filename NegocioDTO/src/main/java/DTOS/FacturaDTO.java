package DTOS;

import java.util.List;

/**
 *
 * @author Andre
 */
public class FacturaDTO {
    
    //Atributos
    private String totalConImpuestos, totalSinImpuestos;
    private String fechaHoraFactura;
    private String formaPago;
    private EmisorDTO emisor;
    private ContribuyenteDTO contribuyente;
    private List<DetallesVentaDTO> detalles;

    public FacturaDTO() {
    }

    public FacturaDTO(String totalConImpuestos, String totalSinImpuestos, String fechaHoraFactura, String formaPago, EmisorDTO emisor, ContribuyenteDTO contribuyente, List<DetallesVentaDTO> detalles) {
        this.totalConImpuestos = totalConImpuestos;
        this.totalSinImpuestos = totalSinImpuestos;
        this.fechaHoraFactura = fechaHoraFactura;
        this.formaPago = formaPago;
        this.emisor = emisor;
        this.contribuyente = contribuyente;
        this.detalles = detalles;
    }

    public String getTotalConImpuestos() {
        return totalConImpuestos;
    }

    public void setTotalConImpuestos(String totalConImpuestos) {
        this.totalConImpuestos = totalConImpuestos;
    }

    public String getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(String totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public String getFechaHoraFactura() {
        return fechaHoraFactura;
    }

    public void setFechaHoraFactura(String fechaHoraFactura) {
        this.fechaHoraFactura = fechaHoraFactura;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public EmisorDTO getEmisor() {
        return emisor;
    }

    public void setEmisor(EmisorDTO emisor) {
        this.emisor = emisor;
    }

    public ContribuyenteDTO getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(ContribuyenteDTO contribuyente) {
        this.contribuyente = contribuyente;
    }

    public List<DetallesVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallesVentaDTO> detalles) {
        this.detalles = detalles;
    }
}