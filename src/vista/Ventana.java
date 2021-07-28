/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author Usuario
 */

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import modelo.ObtenerFechaActual;

public class Ventana extends JFrame{
    Login login;
    BuscarProducto bp;
    CRUDProducto cp;
    ResumenVenta rv;
    ConsultarVentas cv;
    MenuAdmin ma;
    EditarEliminarProducto eep;
    RegistrarUsuario re;
    
    ObtenerFechaActual ofa;
    JPanel pPrincipal;
    
    
    public Ventana(){
        ofa = new ObtenerFechaActual();
        login = new Login();
        bp = new BuscarProducto();
        cp = new CRUDProducto();
        rv = new ResumenVenta(ofa);
        cv = new ConsultarVentas();
        ma = new MenuAdmin();
        eep = new EditarEliminarProducto();
        re = new RegistrarUsuario();
        pPrincipal = new JPanel();
        pPrincipal.setLayout(new GridLayout(1,1));
        pPrincipal.add(login);
        bp.setFocusable(true);
        getContentPane().add(pPrincipal);
        setVisible(true);
        setTitle("Harvest System");
        setSize(1100,700);
        setLocationRelativeTo(null);
        
        //setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image image = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/img/agricultor.png");
        setIconImage(image);
    }

    public Login getLogin() {
        return login;
    }

    public BuscarProducto getBp() {
        return bp;
    }

    public CRUDProducto getCp() {
        return cp;
    }

    public ResumenVenta getRv() {
        return rv;
    }

    public ConsultarVentas getCv() {
        return cv;
    }

    public MenuAdmin getMa() {
        return ma;
    }

    public EditarEliminarProducto getEep() {
        return eep;
    }
    
    public JPanel getpPrincipal() {
        return pPrincipal;
    }

    public void setpPrincipal(JPanel pPrincipal) {
        this.pPrincipal = pPrincipal;
    }

    public RegistrarUsuario getRe() {
        return re;
    }
         
}