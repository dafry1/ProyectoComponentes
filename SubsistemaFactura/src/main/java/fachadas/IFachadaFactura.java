package fachadas;

import DTOS.ContribuyenteDTO;
import DTOS.FacturaDTO;
import DTOS.ParticipanteDTO;

/**
 * Fachada que expone el subsistema para consumo de presentación
 * 
 * @author Andre
 */
public interface IFachadaFactura {
    
    /**
     * Consulta un contribuyente por RFC
     * 
     * @param rfc
     * @return 
     */
    ContribuyenteDTO obtenerContribuyente(String rfc);
    
    /**
     * Valida que un juego de datos sea válido
     * 
     * @param celular
     * @param correo
     * 
     * @return true si son válidos
     */
    boolean validarDatos(String celular, String correo);
    
    /**
     * Inicia el proceso para mandar por correo una factura
     * 
     * @param factura a enviar
     */
    void enviarFactura(FacturaDTO factura);
    
    /**
     * Determina si el rango de fecha y hora es válido para
     * proceder con la factura
     * 
     * @param fechaHora
     * 
     * @return true si es válido
     */
    boolean validarRangoFecha(String fechaHora);
    
    /**
     * Muestra la factura
     * 
     * @param infoFormularioReceptor que se hace en presentación
     * @param formaPago
     * 
     * @return el DTO de la factura
     */
    FacturaDTO obtenerFacturaMostrar(ParticipanteDTO infoFormularioReceptor, String formaPago);
    
    /**
     * Obtiene las formas de pago
     * 
     * @return formas de pago
     */
    String[] obtenerFormasPago();
    
    /**
     * Obtiene los régimenes fiscales
     * 
     * @return los régimenes fiscales
     */
    String[] obtenerRegimenesFiscales();
    
    /**
     * Obtiene los CFDIs
     * 
     * @return los CFDIs
     */
    String[] obtenerCfdis();
}