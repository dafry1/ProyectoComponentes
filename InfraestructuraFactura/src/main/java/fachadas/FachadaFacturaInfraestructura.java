package fachadas;

import controles.ControlFactura;
import dto.ContribuyenteAPIDTO;

/**
 * Interfaz que expone infraestructura para el
 * consumo del subsistema de factura
 * 
 * @author Andre
 */
public class FachadaFacturaInfraestructura implements IFachadaFacturaInfraestructura {

    //Controles
    ControlFactura controlFactura = new ControlFactura();
    
    /**
     * Consulta a la API para poder facturar el XML
     * 
     * @param facturaXML
     * 
     * @return 
     */
    @Override
    public String timbrar(String facturaXML) {
        return controlFactura.timbrar(facturaXML);
    }

    /**
     * Valida que el correo exista realmente
     * usando Zerobounce
     * 
     * @param correo
     * 
     * @return 
     */
    @Override
    public boolean validarCorreo(String correo) {
        return controlFactura.validarCorreo(correo);
    }

    /**
     * Consulta a una API con su propia BD el contribuyente
     * 
     * @param rfc
     * 
     * @return 
     */
    @Override
    public ContribuyenteAPIDTO consultarContribuyente(String rfc) {
        return controlFactura.consultarContribuyente(rfc);
    }
}