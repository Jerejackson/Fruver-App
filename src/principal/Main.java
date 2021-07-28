/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import Conexion.Conexion;
import Consultas.Consultas;
import Controlador.Controlador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import vista.Ventana;

/**
 *
 * @author Usuario
 */
public class Main {
    
    Main(){
        String url = "jdbc:mysql://localhost:3306/fruver";
        String usuario = "root";
        String password = "";
        Conexion.getConexion(url, usuario, password);
    }
    public static void main(String[] args) {
        new Main();
        Consultas sql = new Consultas();
        Ventana v = new Ventana();
        Controlador c = new Controlador(v,sql);
        
        v.getLogin().registrarEscuchas(c);
        v.getCp().registrarEscuchas(c);
        v.getCv().registrarEscuchas(c);
        v.getMa().registrarEscuchas(c);
        v.getRe().registrarEscuchas(c);
        v.getRv().registrarEscuchas(c);
        //v.getRv().registrarEscuchasBotonesX(c);
        v.getBp().registrarEscuchas(c);
        //v.getBp().registrarEscuchasBotonesFrutas(c);
        v.getEep().registrarEscuchas(c);
        //v.getEep().registrarEscuchasBotonesFrutas(c);
        c.registrarEscuchasTeclado();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 

        
    }
}
