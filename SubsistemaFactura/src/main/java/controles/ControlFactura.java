package controles;

import DTOS.ContribuyenteDTO;
import adaptadores.AdaptadorContribuyente;
import fachadas.FachadaFacturaInfraestructura;
import fachadas.IFachadaFacturaInfraestructura;

/**
 * Control encargado de comunicarse con infraestructura
 * 
 * @author Andre
 */
public class ControlFactura {
    
    //Fachada de infraestructura
    private IFachadaFacturaInfraestructura fachadaInfraestructura = new FachadaFacturaInfraestructura();
    
    public ContribuyenteDTO obtenerContribuyente(String rfc) {
        return AdaptadorContribuyente.adaptar(fachadaInfraestructura.consultarContribuyente(rfc));
    }
    
}
