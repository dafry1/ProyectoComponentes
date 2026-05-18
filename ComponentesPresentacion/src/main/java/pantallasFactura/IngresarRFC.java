package pantallasFactura;

import coordinadores.ICoordinadorNegocio;
import javax.swing.JDialog;
import observadores.IObservador;
import utilPresentacion.FachadaUtil;

/**
 *
 * @author Andre
 */
public class IngresarRFC extends JDialog {
    private ICoordinadorNegocio coordinadorNegocio;
    private IObservador observador;
    
    public IngresarRFC(ICoordinadorNegocio coordinadorNegocio, IObservador observador) {
        FachadaUtil.configurarDialogoInicio(this, "Ingresar RFC del contribuyente");
        
        this.coordinadorNegocio = coordinadorNegocio;
        this.observador = observador;
        
        FachadaUtil.configurarDialogoFinal(this);
    }
    
    
    
}
