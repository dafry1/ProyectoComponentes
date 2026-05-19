package adaptadores;

import DTOS.VentaDTO;
import dominio.Venta;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Andre
 */
public final class AdaptadorVenta {

    private AdaptadorVenta(){}
    
    public static Venta Entidad(VentaDTO dto) {
        if (dto == null) return null;
        
        Venta entidad = new Venta();
        
        //Datos del CU base
        entidad.setCliente(AdaptadorCliente.Entidad(dto.getCliente()));
        entidad.setEmpleado(AdaptadorEmpleado.Entidad(dto.getEmpleado()));
        entidad.setTotal(dto.getTotal());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setFolio(dto.getFolio());
        entidad.setDetalles(AdaptadorDetallesVenta.listaEntidad(dto.getDetalles()));
        
        //Datos del CU Factura
        entidad.setAntesImpuestos(dto.getAntesImpuestos());
        entidad.setFacturada(dto.isFacturada());
        
        return entidad;
    }

    public static List<Venta> listaEntidad(List<VentaDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(AdaptadorVenta::Entidad).collect(Collectors.toList());
    }

    public static VentaDTO DTO(Venta entidad) {
        if (entidad == null) return null;
        
        VentaDTO dto = new VentaDTO();
        
        //Datos del CU base
        dto.setCliente(AdaptadorCliente.DTO(entidad.getCliente()));
        dto.setEmpleado(AdaptadorEmpleado.DTO(entidad.getEmpleado()));
        dto.setTotal(entidad.getTotal());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setFolio(entidad.getFolio());
        dto.setDetalles(AdaptadorDetallesVenta.listaDTO(entidad.getDetalles()));
        
        //Datos del CU factura
        dto.setAntesImpuestos(dto.getAntesImpuestos());
        dto.setFacturada(dto.isFacturada());
        
        return dto;
    }

    public static List<VentaDTO> listaDTO(List<Venta> entidades) {
        if (entidades == null) return null;
        return entidades.stream().map(AdaptadorVenta::DTO).collect(Collectors.toList());
    }
}