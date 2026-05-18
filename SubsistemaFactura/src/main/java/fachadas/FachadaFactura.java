package fachadas;

import DTOS.ContribuyenteDTO;
import DTOS.FacturaDTO;
import DTOS.ParticipanteDTO;

/**
 * Fachada que expone el subsistema para consumo de presentación
 * * @author Andre
 */
public class FachadaFactura implements IFachadaFactura {

    /**
     * Consulta un contribuyente por RFC
     * 
     * @param rfc
     * 
     * @return 
     */
    @Override
    public ContribuyenteDTO obtenerContribuyente(String rfc) {
        return new ContribuyenteDTO();
    }

    /**
     * Valida que un juego de datos sea válido
     * 
     * @param celular
     * @param correo
     * 
     * @return true si son válidos
     */
    @Override
    public boolean validarDatos(String celular, String correo) {
        return true;
    }

    /**
     * Inicia el proceso para mandar por correo una factura
     * 
     * @param factura a enviar
     */
    @Override
    public void enviarFactura(FacturaDTO factura) {
        // Método vacío
    }

    /**
     * Determina si el rango de fecha y hora es válido para
     * proceder con la factura
     * 
     * @param fechaHora
     * 
     * @return true si es válido
     */
    @Override
    public boolean validarRangoFecha(String fechaHora) {
        return true;
    }

    /**
     * Muestra la factura
     * 
     * @param infoFormularioReceptor que se hace en presentación
     * @param formaPago
     * 
     * @return el DTO de la factura
     */
    @Override
    public FacturaDTO obtenerFacturaMostrar(ParticipanteDTO infoFormularioReceptor, String formaPago) {
        return new FacturaDTO();
    }

    /**
     * Obtiene las formas de pago
     * 
     * @return formas de pago
     */
    @Override
    public String[] obtenerFormasPago() {
        return new String[0];
    }

    /**
     * Obtiene los régimenes fiscales
     * @return los régimenes fiscales
     */
    @Override
    public String[] obtenerRegimenesFiscales() {
        return new String[0];
    }

    /**
     * Obtiene los CFDIs
     * 
     * @return los CFDIs
     */
    @Override
    public String[] obtenerCfdis() {
        return new String[0];
    }
}