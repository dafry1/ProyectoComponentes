package daos;

import dominio.Venta;
import interfaces.IVentaDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre
 */
public class VentaDAO implements IVentaDAO {

    private static final System.Logger LOG = System.getLogger(VentaDAO.class.getName());
    
    private static List<Venta> VENTAS = new ArrayList<>();
    
    @Override
    public List<Venta> consultarVentas() {
        return VENTAS;
    }

    @Override
    public Venta registrarVenta(Venta venta) {
        VENTAS.add(venta);
        LOG.log(System.Logger.Level.INFO, "Venta registrada exitosamente: " + venta.getCliente().getNombres() + " : " + venta.getDetalles().size());
        return venta;
    }

    @Override
    public List<Venta> filtrarVentasFecha(LocalDate fecha) {
        return VENTAS;
    }

    @Override
    public List<Venta> filtrarVentasTotalMinimo(double minimo) {
        return VENTAS;
    }

    @Override
    public List<Venta> filtrarVentasTotalMaximo(double maximo) {
        return VENTAS;
    }
}