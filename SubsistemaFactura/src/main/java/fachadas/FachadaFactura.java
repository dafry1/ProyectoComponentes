package fachadas;

import DTOS.ContribuyenteDTO;
import DTOS.FacturaDTO;
import DTOS.ParticipanteDTO;
import controles.ControlDatos;
import controles.ControlFactura;

/**
 * Fachada que expone el subsistema para consumo de presentación
 * * @author Andre
 */
public class FachadaFactura implements IFachadaFactura {

    //Controles
    ControlDatos controlDatos = new ControlDatos();
    ControlFactura controlFactura = new ControlFactura();
    
    /**
     * Consulta un contribuyente por RFC
     * 
     * @param rfc
     * 
     * @return 
     */
    @Override
    public ContribuyenteDTO obtenerContribuyente(String rfc) {
        ContribuyenteDTO contribuyente = controlFactura.obtenerContribuyente(rfc);
        if (contribuyente != null) {
            controlDatos.setContribuyente(contribuyente);
        }
        return contribuyente;
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
        return controlDatos.obtenerFormasPago();
    }

    /**
     * Obtiene los régimenes fiscales
     * @return los régimenes fiscales
     */
    @Override
    public String[] obtenerRegimenesFiscales() {
        return controlDatos.obtenerRegimenesFiscales();
    }

    /**
     * Obtiene los CFDIs
     * 
     * @return los CFDIs
     */
    @Override
    public String[] obtenerCfdis() {
        return controlDatos.obtenerCfdis();
    }
}