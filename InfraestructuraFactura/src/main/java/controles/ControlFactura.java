package controles;

import dto.ContribuyenteAPIDTO;

/**
 * Control encargado de hablar directamente
 * con la API
 * 
 * @author Andre
 */
public class ControlFactura {
    
    public String timbrar(String facturaXML) {
        return facturaXML;
    }
    
    public boolean validarCorreo(String correo) {
        return true;
    }
    
    public ContribuyenteAPIDTO consultarContribuyente(String rfc) {
        return new ContribuyenteAPIDTO(
                        "Juan Carlos",
                        "Pérez",
                        "Gómez",
                        "PEGA920415AA1",  
                        "RESICO",          
                        "5512345678",
                        "juan.perez@email.com",
                        "Av. Paseo de la Reforma",
                        "222",
                        "Juárez",
                        "06600"           
                    );
    }
}