package adaptadores;

import DTOS.DetallesVentaDTO;
import dominio.DetallesVenta;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IAdaptadorDetallesVenta {
    DetallesVenta Entidad(DetallesVentaDTO dto);
    
    List<DetallesVenta> listaEntidad(List<DetallesVentaDTO> dtos);
    
    DetallesVentaDTO DTO(DetallesVenta entidad);
    
    List<DetallesVentaDTO> listaDTO(List<DetallesVenta> entidades);
}
