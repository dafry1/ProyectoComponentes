package daos;

import adaptadoresDoc.AdaptadorCliente;
import adaptadoresDoc.AdaptadorDetallesVenta;
import adaptadoresDoc.AdaptadorEmpleado;
import adaptadoresDoc.AdaptadorVenta;
import adaptadoresDoc.PiezaDoc;
import dominio.Venta;
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
    
    private static final AdaptadorCliente adaptadorCliente = new AdaptadorCliente();
    private static final AdaptadorEmpleado adaptadorEmpleado = new AdaptadorEmpleado();
    private static final AdaptadorDetallesVenta adaptadorDetallesVenta = new AdaptadorDetallesVenta(PiezaDoc.singleton());
    private static final AdaptadorVenta adaptadorVenta = new AdaptadorVenta(adaptadorCliente, adaptadorEmpleado, adaptadorDetallesVenta);
    
    @Override
    public List<Venta> consultarVentas() {
        return VENTAS;
    }

    @Override
    public Venta registrarVenta(Venta venta) {
        System.out.println("DAO ANTES DE REGISTRAR VENTA: " + venta.getFechaHora());
        VENTAS.add(venta);
        LOG.log(System.Logger.Level.INFO, ">> Venta registrada exitosamente: " + venta.getCliente().getNombres() + " : " + venta.getDetalles().size());
        System.out.println("DAO DESPUES DE REGISTRAR VENTA: " + venta.getFechaHora());
        return venta;
    }
}