package daos;

import dominio.Emisor;

/**
 * Interfaz para acceder a la información fiscal de
 * la empresar para facturar
 *  
 * @author Andre
 */
public interface IEmisorDAO {
    Emisor obtenerInfoEmisor();
}
