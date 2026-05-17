/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import conexiones.ConexionSQL;
import daos.ISolicitudDAO;
import dominio.Solicitud;
import java.util.List;

/**
 *
 * @author Andre
 */
public class SolicitudJDBC implements ISolicitudDAO {
    private final ConexionSQL conexion;
    public SolicitudJDBC(ConexionSQL conexion) {
        this.conexion = conexion;
    }
    
    @Override
    public List<Solicitud> consultarSolicitudes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Solicitud registrarSolicitud(Solicitud solicitud) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int contarSolicitudesHoy() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
