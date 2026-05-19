package controles;

import DTOS.ContribuyenteDTO;

/**
 * Control que se encarga de almacenar información relacionada
 * al proceso de factura
 * 
 * @author Andre
 */
public class ControlDatos {
    
    //Configuraciones fiscales
    private String[] FORMAS_PAGO = {"Efectivo", "Crédito", "Débito"};
    private String[] REGIMENES_FISCALES = {"RESICO", "Sueldos y salarios", "Arrendamiento"};
    private String[] CFDIS = {"Ingreso", "Egreso"};
    
    //Contribuyente actual durante el proceso
    private ContribuyenteDTO contribuyente;

    /** Constructor */
    public ControlDatos() {}

    public String[] obtenerFormasPago() {
        return FORMAS_PAGO;
    }

    public String[] obtenerRegimenesFiscales() {
        return REGIMENES_FISCALES;
    }

    public String[] obtenerCfdis() {
        return CFDIS;
    }

    public void setContribuyente(ContribuyenteDTO contribuyente) {
        this.contribuyente = contribuyente;
    }
    
    public ContribuyenteDTO getContribuyente() {
        return contribuyente;
    }
}