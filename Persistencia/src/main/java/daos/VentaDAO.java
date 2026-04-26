package daos;

import dominio.Venta;
import interfaces.IVentaDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre
 */
public class VentaDAO implements IVentaDAO {
    private static List<Venta> VENTAS = new ArrayList<>();
    
    @Override
    public List<Venta> consultarVentas() {
        return VENTAS;
    }

    @Override
    public Venta registrarVenta(Venta venta) {
        VENTAS.add(venta);
        return venta;
    }
}