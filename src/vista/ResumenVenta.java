/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import modelo.ObtenerFechaActual;

/**
 *
 * @author Jeremi_Sanchez
 */


public class ResumenVenta extends JPanel{
    ArrayList<JButton> botones = new ArrayList<>();
    JLabel lblTitulo, lblNumFactura, lblFecha, lblEmpleado;
    ObtenerFechaActual fecha; 
    ImageIcon background;
    JTextField txtTotal, txtRecibido, txtDevolucion;
    JButton btnAgregar, btnImprimir;
    JTextArea impresion;
    
    public ResumenVenta(ObtenerFechaActual fecha){
        this.fecha =  fecha;
        inicializar();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(facturaUp(), BorderLayout.NORTH);
        add(facturaDown(), BorderLayout.SOUTH);
    }
    
    private JPanel facturaUp(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,3));
        p.setBorder(BorderFactory.createEmptyBorder(15, 30, 5, 10));
        p.add(lblTitulo);    p.add(lblNumFactura);    p.add(lblFecha);
        p.setOpaque(false);
        return p;
    }
    
    public JScrollPane listaProductos(ArrayList<Object[]> contenido){
        JPanel pPrincipal = new JPanel();
        JPanel pNorte = new JPanel();
        pNorte.setLayout(new GridLayout(1,5));
        JLabel titulo1 = new JLabel("Icono");
        JLabel titulo2 = new JLabel("Producto");
        JLabel titulo3 = new JLabel("Cantidad/Peso");
        JLabel titulo4 = new JLabel("Precio");
        JLabel titulo5 = new JLabel("Retirar Producto de la lista");
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
            JPanel p2 = new JPanel();
            JLabel icono = new JLabel(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/Icons/IconosP/"+String.valueOf(contenido.get(i)[0])+".png"),20,20));
            JLabel lblNombre = new JLabel(String.valueOf(contenido.get(i)[0]));
            JLabel lblCantidad = new JLabel(String.valueOf(contenido.get(i)[1] + " "+contenido.get(i)[3]));
            JLabel lblPrecio = new JLabel(String.valueOf("$ "+contenido.get(i)[2]));
            icono.setHorizontalAlignment(SwingConstants.CENTER);
            lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
            lblCantidad.setHorizontalAlignment(SwingConstants.CENTER);
            lblPrecio.setHorizontalAlignment(SwingConstants.CENTER);
            botones.add(new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/Icons/eliminar.png"),20,20)));
            botones.get(i).setBackground(Color.GREEN);
            botones.get(i).setOpaque(false);
            p2.setLayout(new BoxLayout(p2, BoxLayout.LINE_AXIS));
            p2.add(botones.get(i));
            p2.setOpaque(false);
            p.add(icono); p.add(lblNombre); p.add(lblCantidad); p.add(lblPrecio); p.add(p2);
        }
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
        p.setLayout(new GridLayout(1,5));
        p.add(facturaDownLeft());
        p.add(facturaDownCenter());
        p.add(facturaDownRight());
        p.add(facturaDownRightOne());
        p.add(facturaDownRightTwo());
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaDownLeft(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2,1,10,10));
        p.add(lblEmpleado);
        p.add(new JLabel("¡GRACIAS POR SU COMPRA!"));
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaDownCenter(){
        JPanel p = new JPanel();
        p.add(btnAgregar);
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaDownRight(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2,1,10,10));
        p.add(new JLabel("VALOR TOTAL DE LA COMPRA"));
        p.add(txtTotal);
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaDownRightOne(){
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(5,30,5,5));
        p.setLayout(new GridLayout(4,1));
        p.add(new JLabel("Valor recibido:"));
        p.add(txtRecibido);
        p.add(new JLabel("Devolucion:"));
        p.add(txtDevolucion);
        p.setOpaque(false);
        return p;
    }
    
    JPanel facturaDownRightTwo(){
        JPanel p = new JPanel();
        p.add(btnImprimir);
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
        lblTitulo = new JLabel("RESUMEN VENTA");
        lblNumFactura = new JLabel("Productos en Carrito... ");
        lblFecha = new JLabel("Fecha: "+fecha.getFechaActual());
        
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblNumFactura.setFont(new Font("Arial", Font.BOLD, 20));
        lblEmpleado = new JLabel("Cajero: uvevevvwvevwve");
        
        txtTotal = new JTextField();
        txtRecibido = new JTextField();
        txtDevolucion = new JTextField();
        txtTotal.setEditable(false);
        txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
        txtDevolucion.setEditable(false);
        txtDevolucion.setHorizontalAlignment(SwingConstants.CENTER);
        
        btnAgregar = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/shopping_cart.png"),50,50));
        btnAgregar.setBackground(Color.WHITE);
        
        btnImprimir = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/imprimir.png"),50,50));
        btnImprimir.setBackground(Color.WHITE);
        
        impresion = new JTextArea();
    }
    
    public void registrarEscuchasBotonesX(Controlador c){
        for (int i = 0; i < botones.size(); i++) {
            botones.get(i).addActionListener(c);
        }
    }
    public void registrarEscuchas(Controlador c){
        btnAgregar.addActionListener(c);
        btnImprimir.addActionListener(c);
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public void setBotones(ArrayList<JButton> botones) {
        this.botones = botones;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnImprimir() {
        return btnImprimir;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JTextField getTxtRecibido() {
        return txtRecibido;
    }

    public void setTxtRecibido(JTextField txtRecibido) {
        this.txtRecibido = txtRecibido;
    }

    public JTextField getTxtDevolucion() {
        return txtDevolucion;
    }

    public void setTxtDevolucion(JTextField txtDevolucion) {
        this.txtDevolucion = txtDevolucion;
    }

    public JLabel getLblEmpleado() {
        return lblEmpleado;
    }

    public JTextArea getImpresion() {
        return impresion;
    }
    
}