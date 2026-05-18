package pantallasFactura;

import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import javax.swing.JFrame;
import utilPresentacion.FachadaUtil;

/**
 *
 * @author Andre
 */
public class IngresarDatosContribuyente extends JFrame {
    private ICoordinadorPresentacion coordinadorPresentacion;
    private ICoordinadorNegocio coordinadorNegocio;
    
    public IngresarDatosContribuyente(ICoordinadorPresentacion coordinadorPresentacion, ICoordinadorNegocio coordinadorNegocio) {
        FachadaUtil.configurarFrame("Ingresar datos fiscales", this);
        this.coordinadorPresentacion = coordinadorPresentacion;
        this.coordinadorNegocio = coordinadorNegocio;
    }
    
}
