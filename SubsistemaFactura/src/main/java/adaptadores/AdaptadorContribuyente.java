package adaptadores;

import DTOS.ContribuyenteDTO;
import DTOS.DireccionDTO;
import dto.ContribuyenteAPIDTO;

/**
 * Adaptador unilateral (infraestructura -> negocio) de
 * un contribuyente
 * 
 * @author Andre
 */
public class AdaptadorContribuyente {
    
    /**
     * Adapta un DTO de infraestructura a uno de negocio
     * con la información de un contribuyente
     * 
     * @param contribuyente de infraestructura a mapear
     * 
     * @return el cliente mapeado a negocio
     */
    public static ContribuyenteDTO adaptar(ContribuyenteAPIDTO contribuyente) {
        ContribuyenteDTO c = new ContribuyenteDTO();
        
        //Nombre
        c.setNombres(contribuyente.getNombres());
        c.setApellidoPaterno(contribuyente.getApellidoPaterno());
        c.setApellidoMaterno(contribuyente.getApellidoMaterno());
        
        //Contacto
        c.setCelular(contribuyente.getCelular());
        c.setCorreo(contribuyente.getCorreo());
        
        //Información fiscal
        c.setRfc(contribuyente.getRfc());
        c.setRegimenFiscal(contribuyente.getRegimenFiscal());
        
        //Dirección
        DireccionDTO d = new DireccionDTO();
        d.setCalle(contribuyente.getCalle());
        d.setNumero(contribuyente.getNumero());
        d.setColonia(contribuyente.getColonia());
        d.setCodigoPostal(contribuyente.getCodigoPostal());
        c.setDireccion(d);
        
        //Regresa el contribuyente adaptado
        return c;
    }
}