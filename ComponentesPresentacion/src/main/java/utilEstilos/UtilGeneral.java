package utilEstilos;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import utilClases.ComboBoxPersonalizado;
import utilClases.ComboBoxRenderizador;
import utilClases.CampoTextoRedondeado;
import utilPresentacion.Constantes;

/**
 * Utilerías que guarda cosas que se ocupan en toda la presentación
 */
public class UtilGeneral {

    /**
     * Fábrica de un label que ya carga consigo mismo la fuente del
     * programa, lo que lo aleja un poco de un simple envoltorio
     * innecesario. Anticipa decisiones más complejas en un
     * futuro
     * 
     * @param texto del label
     * 
     * @return el label estilizado
     */
    public static JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(Constantes.FUENTE);
        return label;
    }
    
    /**
     * Fábrica de un ComboBox personalizado
     * 
     * @param items Elementos del combo
     * 
     * @return JComboBox personalizado
     */
    public static <T> JComboBox<T> crearComboBox(T[] items) {
        JComboBox<T> combo = new JComboBox<>(items);
        
        //Aplica UI
        combo.setUI(new ComboBoxPersonalizado());
        combo.setOpaque(false);
        
        //Decisiones visuales
        combo.setFont(Constantes.FUENTE);
        combo.setBackground(Constantes.COLOR_BOTONES);
        combo.setForeground(Constantes.COLOR_TEXTO_BOTONES);
        
        //Bordes internos
        combo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        //Manita por ncima
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //Asigna el renderizador para la lista desplegable
        combo.setRenderer(new ComboBoxRenderizador());
        
        //Regresa el Combobox
        return combo;
    }
    
    /**
     * Fábrica de una tabla ya estilizada
     *
     * @param columnas arreglo con los campos
     * @return la tabla ya lista
     */
    public static JTable crearTabla(String[] columnas) {
        
        //Evita que puedan editar la tabla
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        //Crea la tabla
        JTable tabla = new JTable(modeloTabla);
        
        //Crea un renderizado central para cada celda
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(JLabel.CENTER);
        
        //Aplica el centrado a cada celda
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
        
        //Elimina la selección múltiple, limitando a solo elegir/resaltar una fila a la vez
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.setDragEnabled(false);
        
        //Evita que el usuario pueda arrastrar las columnas
        tabla.getTableHeader().setReorderingAllowed(false);
        
        //Espacio en las filas
        tabla.setRowHeight(30);

        //Configura colores
        tabla.setSelectionBackground(Constantes.COLOR_TABLA);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setSelectionBackground(Constantes.COLOR_ELEGIR_FILA);
        tabla.setSelectionForeground(Color.BLACK);
        
        //Elimina bordes
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));

        //Encabezado
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(Constantes.COLOR_TABLA);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 35));

        //Regresa la tabla
        return tabla;
    }
    
    /**
     * Fábrica de un campo de texto redondeado
     *
     * @return el campo redondeado
     */
    public static JTextField crearCampoTexto() {
        return new CampoTextoRedondeado(Constantes.NUM_CARACTERES);
    }
    
    /**
     * Método que añade un registro a la tabla 
     * Utiliza el último guardado por el coordinador
     *
     * @param <T> puede ser cualquier DTO
     * @param tabla
     * @param lista
     * @param mapeador el método lambda para que sea flexible con cualquier clase
     */
    public static <T> void registrarTabla(JTable tabla, List<T> lista, Function<T, Object[]> mapeador) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        //Si la lista tiene más de un elemento, entonces es para cargar toda la tabla con registros ya existentes
        modelo.setRowCount(0);
        if (lista != null) {
            for (T elemento: lista) {
                modelo.addRow(mapeador.apply(elemento));
            }
        }
    }
    
    /**
     * Procesa (reescala) una imagen de forma centralizada
     * Toma los bytes y con ciertos parámetros los transforma
     * Regresa la imagen lista
     * 
     * @param bytes de la imagen
     * @return la imagen lista para mostrar
     */
    public static ImageIcon procesarImagen(byte[] bytes) {
        ImageIcon iconoOriginal = new ImageIcon(bytes);
        Image imagen = iconoOriginal.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
}