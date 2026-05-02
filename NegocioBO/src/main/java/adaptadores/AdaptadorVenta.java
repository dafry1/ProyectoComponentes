package adaptadores;

import DTOS.VentaDTO;
import dominio.Venta;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public class AdaptadorVenta implements IAdaptadorVenta { 
    private IAdaptadorEmpleado adaptadorEmpleado;
    private IAdaptadorCliente adaptadorCliente;
    private IAdaptadorDetallesVenta adaptadorDetallesVenta;
    public AdaptadorVenta(IAdaptadorEmpleado adaptadorEmpleado, IAdaptadorCliente adaptadorCliente, IAdaptadorDetallesVenta adaptadorDetallesVenta){
        this.adaptadorEmpleado = adaptadorEmpleado;
        this.adaptadorCliente = adaptadorCliente;
        this.adaptadorDetallesVenta = adaptadorDetallesVenta;
    }
    
    @Override
    public Venta Entidad(VentaDTO dto) {
        Venta entidad = new Venta();
        entidad.setCliente(adaptadorCliente.Entidad(dto.getCliente()));
        entidad.setEmpleado(adaptadorEmpleado.Entidad(dto.getEmpleado()));
        entidad.setTotal(dto.getTotal());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setFolio(dto.getFolio());
        entidad.setDetalles(adaptadorDetallesVenta.listaEntidad(dto.getDetalles()));
        return entidad;
    }

    @Override
    public List<Venta> listaEntidad(List<VentaDTO> dtos) {
        return dtos.stream().map(this::Entidad).collect(Collectors.toList());
    }

    @Override
    public VentaDTO DTO(Venta entidad) {
        VentaDTO dto = new VentaDTO();
        dto.setCliente(adaptadorCliente.DTO(entidad.getCliente()));
        dto.setEmpleado(adaptadorEmpleado.DTO(entidad.getEmpleado()));
        dto.setTotal(entidad.getTotal());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setFolio(entidad.getFolio());
        dto.setDetalles(adaptadorDetallesVenta.listaDTO(entidad.getDetalles()));
        return dto;
    }

    @Override
    public List<VentaDTO> listaDTO(List<Venta> entidades) {
        return entidades.stream().map(this::DTO).collect(Collectors.toList());
    }
    
}
