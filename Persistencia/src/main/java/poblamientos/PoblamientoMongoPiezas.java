package poblamientos;

import daos.IPiezaDAO;
import daos.PiezaDAO;
import dominio.Pieza;
import java.util.List;

/**
 * Puebla datos ya establecidos en la BD
 * 
 * @author Andre
 */
public class PoblamientoMongoPiezas {
    private IPiezaDAO piezaDAO;

    public PoblamientoMongoPiezas(IPiezaDAO piezaDAO) {
        this.piezaDAO = piezaDAO;
    }
    
    public void poblar() {
        double IVA = 1.16;
        List<Pieza> catalogoInicial = List.of(
            new Pieza("Ryzen 5 9600X", "Procesador", "AMD", "Zen 5", 2500.0, 50, IVA),
            new Pieza("Core i9-14900K", "Procesador", "Intel", "Raptor Lake", 5500.0, 40, IVA),
            new Pieza("Trident Z5 RGB", "RAM", "G.Skill", "DDR5-6400", 1800.0, 30, IVA),
            new Pieza("Vengeance RGB Frosted", "RAM", "Corsair", "DDR5-6000", 2100.0, 25, IVA),
            new Pieza("GeForce RTX 4090", "Tarjeta Gráfica", "NVIDIA", "Founders Edition", 35000.0, 10, IVA),
            new Pieza("Radeon RX 7800 XT", "Tarjeta Gráfica", "AMD", "Steel Legend", 9800.0, 15, IVA)
        );
        catalogoInicial.forEach(pieza -> piezaDAO.insertar(pieza));
        System.out.println("Base de datos inicializada con éxito.");
    }
}
