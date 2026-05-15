/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.persistencia;

import daos.EmpleadoDAO;
import daos.IEmpleadoDAO;
import daos.IPiezaDAO;
import fabricas.FabricaDAO;
import fabricas.IFabricaDAO;
import poblamientos.PoblamientoMongoPiezas;
import poblamientos.poblamientoMongoEmpleados;

/**
 * SOLO DEBE CORRERSE UNA VEZ SIRVE PARA POBLAR CON DATOS
 * YA ESTABLECIDOS
 * 
 * @author Andre
 */
public class Persistencia {

    public static void main(String[] args) {
        IFabricaDAO fabrica = FabricaDAO.singleton();
        
        IPiezaDAO piezaDAO = fabrica.fabricarPieza();
        PoblamientoMongoPiezas pm = new PoblamientoMongoPiezas(piezaDAO);
        pm.poblar();
        
        IEmpleadoDAO empleadoDAO = fabrica.fabricarEmpleado();
        poblamientoMongoEmpleados pme = new poblamientoMongoEmpleados(empleadoDAO);
        pme.poblarEmpleados();
    }
}
