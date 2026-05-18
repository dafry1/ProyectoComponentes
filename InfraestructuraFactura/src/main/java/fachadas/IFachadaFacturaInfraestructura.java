package fachadas;

import dto.ContribuyenteAPIDTO;

/**
 * Interfaz que el subsistema expone para el
 * subsistema de factura
 * 
 * @author Andre
 */
public interface IFachadaFacturaInfraestructura {
    
    /**
     * Coloca el timbrado en el XML de la factura
     * 
     * @param facturaXML a timbrar
     * 
     * @return el XML timbrado
     */
    String timbrar(String facturaXML);
    
    /**
     * Valida si el correo existe
     * 
     * @param correo a validar
     * 
     * @return true si lo es, false si no
     */
    boolean validarCorreo(String correo);
    
    /**
     * Consulta un contribuyente mediante su RFC
     * 
     * @param rfc del contribuyente
     * 
     * @return el contribuyente
     */
    ContribuyenteAPIDTO consultarContribuyente(String rfc);
}