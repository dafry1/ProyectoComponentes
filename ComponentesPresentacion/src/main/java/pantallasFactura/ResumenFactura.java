package pantallasFactura;

import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import javax.swing.JFrame;
import utilPresentacion.Util;

/**
 *
 * @author Andre
 */
public class ResumenFactura extends JFrame {
    private ICoordinadorPresentacion coordinadorPresentacion;
    private ICoordinadorNegocio coordinadorNegocio;
    
    public ResumenFactura(ICoordinadorPresentacion coordinadorPresentacion, ICoordinadorNegocio coordinadorNegocio) {
        Util.configurarFrame("Resumen de la factura", this);
        this.coordinadorPresentacion = coordinadorPresentacion;
        this.coordinadorNegocio = coordinadorNegocio;
    }
}
