/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Controlador.Controlador;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Jeremi_Sanchez
 */


public class ConsultarVentas extends JPanel{

    private JLabel lblTitulo,lblEmpleado;
    private final Calendar fecha = new GregorianCalendar();
    private ImageIcon background;
    private JTextField txtTotal;
    private JButton btnHoy, btnBuscar, btnVolver;
    private JDateChooser jcFecha;
    
    public ConsultarVentas(){
        inicializar(); 
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(facturaUp(), BorderLayout.NORTH);
        add(facturaDown(), BorderLayout.SOUTH);
    }
    
    private JPanel facturaUp(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2,1));
        p.add(facturaUp1());
        p.add(facturaUp2());
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaUp1(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,3));
        p.setBorder(BorderFactory.createEmptyBorder(15, 30, 5, 10));
        p.add(lblTitulo);
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaUp2(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,2));
        //p.add(facturaUp2Left());
        p.add(facturaUp2Right());
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaUp2Left(){
        JPanel p = new JPanel();
        p.add(btnHoy); 
        p.setBorder(BorderFactory.createTitledBorder("Ventas Realizadas Hoy"));
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaUp2Right(){
        JPanel p = new JPanel();
        p.add(jcFecha);
        p.add(btnBuscar);
        p.setBorder(BorderFactory.createTitledBorder("Día Especifico"));
        p.setOpaque(false);
        return p;
    }
    
    public JScrollPane listaProductos(ArrayList<Object[]> contenido){
        JPanel pPrincipal = new JPanel();
        JPanel pNorte = new JPanel();
        pNorte.setLayout(new GridLayout(1,5));
        JLabel titulo1 = new JLabel("No. Factura");
        JLabel titulo2 = new JLabel("Icono");
        JLabel titulo3 = new JLabel("Producto");
        JLabel titulo4 = new JLabel("Cantidad/Peso");
        JLabel titulo5 = new JLabel("Precio");
        //JLabel titulo6 = new JLabel("Retirar Producto de la lista");
        titulo1.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        titulo1.setHorizontalAlignment(SwingConstants.CENTER);
        titulo2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        titulo2.setHorizontalAlignment(SwingConstants.CENTER);
        titulo3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        titulo3.setHorizontalAlignment(SwingConstants.CENTER);
        titulo4.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        titulo4.setHorizontalAlignment(SwingConstants.CENTER);
        titulo5.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        titulo5.setHorizontalAlignment(SwingConstants.CENTER);
        pNorte.add(titulo1);pNorte.add(titulo2);pNorte.add(titulo3);pNorte.add(titulo4);pNorte.add(titulo5);
        pPrincipal.setLayout(new BorderLayout());
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 10));
        JScrollPane sp;
        p.setLayout(new GridLayout(contenido.size(), 5));
        pPrincipal.add(pNorte, BorderLayout.NORTH);
        pPrincipal.add(p, BorderLayout.CENTER);
        pNorte.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 10));
        for(int i = 0; i < contenido.size(); i++){
            JLabel nFactura = new JLabel(String.valueOf(contenido.get(i)[0]));
            JLabel icono = new JLabel(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/Icons/IconosP/"+String.valueOf(contenido.get(i)[1])+".png"),20,20));
            JLabel lblNombre = new JLabel(String.valueOf(contenido.get(i)[1]));
            JLabel lblCantidad;
            if("Unidad".equals(contenido.get(i)[4])){
                if(Double.parseDouble(String.valueOf(contenido.get(i)[2])) > 1){
                    lblCantidad = new JLabel(String.valueOf((int)Double.parseDouble(String.valueOf(contenido.get(i)[2])) + " "+contenido.get(i)[4]+"es"));
                }else{
                    lblCantidad = new JLabel(String.valueOf((int)Double.parseDouble(String.valueOf(contenido.get(i)[2])) + " "+contenido.get(i)[4]));
                }
            }else{
                lblCantidad = new JLabel(String.valueOf(contenido.get(i)[2] + " "+contenido.get(i)[4]));
            }
            
            
            JLabel lblPrecio = new JLabel(String.valueOf("$ "+contenido.get(i)[3]));
            nFactura.setHorizontalAlignment(SwingConstants.CENTER);
            icono.setHorizontalAlignment(SwingConstants.CENTER);
            lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
            lblCantidad.setHorizontalAlignment(SwingConstants.CENTER);
            lblPrecio.setHorizontalAlignment(SwingConstants.CENTER);
            p.add(nFactura); p.add(icono); p.add(lblNombre); p.add(lblCantidad); p.add(lblPrecio); 
        }
        int total = 0;
        for (int i = 0; i < contenido.size(); i++) {
            total = total + (int)Double.parseDouble(String.valueOf(contenido.get(i)[3]));
        }
        txtTotal.setText("$ "+String.valueOf(total));
        p.setOpaque(false);
        pNorte.setOpaque(false);
        pPrincipal.setOpaque(false);
        sp = new JScrollPane(pPrincipal);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        return sp;
    }
    
    
    private JPanel facturaDown(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,4));
        p.add(btnVolver);
        p.add(new JLabel());
        p.add(facturaDownLeft());
        p.add(facturaDownRight());
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaDownLeft(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2,1,10,10));
        p.add(new JLabel(""));
        p.add(lblEmpleado);
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaDownRight(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2,1,10,10));
        p.add(new JLabel("VALOR TOTAL"));
        p.add(txtTotal);
        p.setOpaque(false);
        return p;
    }
    
    @Override 
     public void paintComponent(Graphics g ){
        super.paintComponent(g);
        background = new ImageIcon(System.getProperty("user.dir")+"/img/Fondo.jpg");
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
       
     }
     
     private ImageIcon cambiarTamañoIcono(ImageIcon icon,int alto,int ancho){
        Image img = icon.getImage();
        Image iconoGrande = img.getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
        return new ImageIcon(iconoGrande);
    }
     
    private void inicializar(){ 
        String fechaSis = String.valueOf(fecha.get(Calendar.DAY_OF_MONTH)+" - "+(fecha.get(Calendar.MONTH)+1)+" - "+fecha.get(Calendar.YEAR));
        lblTitulo = new JLabel("CONSULTAR VENTAS");
        
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblEmpleado = new JLabel("");
        
        txtTotal = new JTextField();
        txtTotal.setEditable(false);
        
        btnHoy = new JButton("Hoy");
        btnVolver = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/login/atras.png"),30,30));
        btnBuscar = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/buscar.png"),30,30));
        btnBuscar.setBackground(Color.white);
        btnVolver.setBackground(Color.white);
        btnVolver.setOpaque(false);
        
        jcFecha = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
    }

    public JButton getBtnHoy() {
        return btnHoy;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JDateChooser getJcFecha() {
        return jcFecha;
    }
    
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }
    
    public void registrarEscuchas(Controlador c){
        btnBuscar.addActionListener(c);
        btnVolver.addActionListener(c);
        btnHoy.addActionListener(c);
    }
    
}