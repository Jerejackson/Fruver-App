/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Jeremi_Sanchez
 */
public class BuscarProducto extends JPanel{
    ArrayList<JButton> botones = new ArrayList<>();
    ArrayList<Object> tempProducto = new ArrayList<>();
    JTextField txtPrecioProducto, txtPeso, txtPrecioProductoC, txtTotal, txtConsultarP;
    JLabel lblNombreP, lblMedidaPeso, lblImagenProducto, lblConfirmacion;
    ImageIcon imagen;
    ImageIcon background;
    JButton btnAgregar, btnFactura, btnVolver;
    

    JComboBox cbbMedidas;
    DefaultComboBoxModel modeloCbb;
    
    public BuscarProducto(){
        inicializar();
        setLayout(new BorderLayout());
        add(infoProducto(), BorderLayout.EAST);
        add(panelBuscar(), BorderLayout.NORTH);
        add(panelVolver(), BorderLayout.WEST);
        //setOpaque(false);
    }
    
    private JPanel panelBuscar(){
        JPanel p = new JPanel();
        p.add(new JLabel("Filtrar Por Nombre:"));
        p.add(txtConsultarP);
        p.setOpaque(false);
        return p;
    }
    
    private JPanel panelVolver(){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        //p.add(new JLabel(cambiarTamañoIcono(new ImageIcon(getClass().getResource("/img/icons/hojas.png")),100,500)), BorderLayout.CENTER);
        //p.add(btnVolver,BorderLayout.SOUTH);
        p.setOpaque(false);
        return p;
    }
    
    
    public JScrollPane botones(ArrayList<Object[]> contenido){
        JPanel p = new JPanel();
        double lenght = (contenido.size()/5);
        int filas = 0;
        if((contenido.size()%5) == 0){
            filas = (int)lenght;
        }else{
            filas = (int)(lenght + (1 - (lenght - (int)lenght)));
        }
        p.setLayout(new GridLayout(filas,5, 5, 5));
        JScrollPane sp = new JScrollPane(p);
        for(int i = 0; i < contenido.size(); i++){
            JPanel p2 = new JPanel();
            
            try{
                ImageIcon icon = new ImageIcon(System.getProperty("user.dir")+"/img/Icons/IconosP/"+String.valueOf(contenido.get(i)[1])+".png");
                botones.add( new JButton(cambiarTamañoIcono(icon,80,90)));
            }catch(NullPointerException ex){
                JOptionPane.showConfirmDialog(null, "Cargando el archivo", "Esperar...", JOptionPane.DEFAULT_OPTION);
            }
            try{
                botones.get(i).setBackground(Color.white);
                botones.get(i).setText(String.valueOf(contenido.get(i)[1]));
                botones.get(i).setVerticalTextPosition(SwingConstants.BOTTOM);
                botones.get(i).setHorizontalTextPosition(SwingConstants.CENTER);
                p2.add(botones.get(i));
                p2.setOpaque(false);
                p.add(p2);
            }catch(IndexOutOfBoundsException ex){
                JOptionPane.showConfirmDialog(null, "Aún no han Cargado la Base de Datos, Intenta Nuevamente", "Esperar...", JOptionPane.DEFAULT_OPTION);
            }
        }
        p.setOpaque(false);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        return sp;
    }
    
    private JPanel infoProducto(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4,1));
        p.add(infoProductoUp());
        p.add(infoProductoCenter());
        p.add(infoProductoDown());
        p.add(infoProductoDownBtns());
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p.setOpaque(false);
        return p;
        
    }
        
    JPanel infoProductoUp(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4,1));
        p.add(new JLabel("/********************Shopping Car********************/"));
   
        p.add(infoPrecioProducto());
        p.add(lblNombreP);
        p.setOpaque(false);
        return p;
    }
    
    
    JPanel infoProductoCenter(){
        JPanel p = new JPanel();
     
        imagen = new ImageIcon(System.getProperty("user.dir")+"/img/icons/default.png");
        lblImagenProducto = new JLabel(cambiarTamañoIcono(imagen,150,150));
        p.add(lblImagenProducto);
           
        p.setOpaque(false);
        return p;
    }
    
    JPanel infoProductoDown(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(7,1));
        p.add(new JLabel("Peso/Cantidad"));
        p.add(ingresoPeso());
        p.add(new JLabel("Precio"));
        p.add(txtPrecioProductoC);
        p.add(new JLabel("Valor Total Compra"));
        p.add(txtTotal);
       
        p.setOpaque(false);
        return p;
    }
    
    JPanel ingresoPeso(){
        JPanel p = new JPanel();
        p.add(txtPeso);
        p.add(cbbMedidas);
        p.setOpaque(false);
        return p;
    }
    
    JPanel infoProductoDownBtns(){
        JPanel p = new JPanel();
        JPanel pp = new JPanel();
        p.setLayout(new GridLayout(2,1)); 
        pp.add(btnAgregar);    pp.add(btnFactura);
        p.add(lblConfirmacion = new JLabel(""));
        p.add(pp);
        //pp.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        p.setOpaque(false);
        pp.setOpaque(false);
        return p;
    }
    
    JPanel infoPrecioProducto(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,2));
        p.add(txtPrecioProducto); p.add(lblMedidaPeso);
        p.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
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
        txtPrecioProducto = new JTextField(10);
        txtPeso = new JTextField(10);
        txtPrecioProductoC = new JTextField(10);
        txtTotal= new JTextField(10);
        txtConsultarP = new JTextField(10);
        
        txtPrecioProducto.setPreferredSize(new Dimension(100,10));
        txtPeso.setPreferredSize(new Dimension(100,20));
        txtPrecioProductoC.setPreferredSize(new Dimension(100,10));
        txtTotal.setPreferredSize(new Dimension(100,10));
        
        txtPrecioProducto.setEditable(false);
        txtPrecioProductoC.setEditable(false);
        txtTotal.setEditable(false);
        
        txtPrecioProducto.setHorizontalAlignment(SwingConstants.CENTER);
        txtPrecioProductoC.setHorizontalAlignment(SwingConstants.CENTER);
        txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
                
       // imagen = new ImageIcon(getClass().getResource("/img/icons/frutas/lulo.png"));
        
        lblNombreP = new JLabel("Nombre del Producto");
        lblMedidaPeso = new JLabel("Precio libra");
        //lblImagenProducto = new JLabel(cambiarTamañoIcono(imagen,150,150));
        
        btnAgregar = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/shopping_cart.png"),50,50));
        btnFactura = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/cuenta.png"),50,50));
        btnVolver = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/login/atras.png"),50,50));
        
        btnAgregar.setBackground(Color.white);
        btnFactura.setBackground(Color.white);
        
        modeloCbb = new DefaultComboBoxModel();
        cbbMedidas = new JComboBox(modeloCbb);
        cbbMedidas.setBackground(Color.white);
    }

    public ArrayList<Object> getTempProducto() {
        return tempProducto;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public void setTempProducto(ArrayList<Object> tempProducto) {
        this.tempProducto = tempProducto;
    }

    public JLabel getLblMedidaPeso() {
        return lblMedidaPeso;
    }

    public JLabel getLblConfirmacion() {
        return lblConfirmacion;
    }

    public JComboBox getCbbMedidas() {
        return cbbMedidas;
    }

    public void setCbbMedidas(JComboBox cbbMedidas) {
        this.cbbMedidas = cbbMedidas;
    }

    public DefaultComboBoxModel getModeloCbb() {
        return modeloCbb;
    }

    public void setModeloCbb(DefaultComboBoxModel modeloCbb) {
        this.modeloCbb = modeloCbb;
    }

    public void setLblMedidaPeso(JLabel lblMedidaPeso) {
        this.lblMedidaPeso = lblMedidaPeso;
    }

    public JLabel getLblNombreP() {
        return lblNombreP;
    }

    public void setLblNombreP(JLabel lblNombreP) {
        this.lblNombreP = lblNombreP;
    }

    public JLabel getLblImagenProducto() {
        return lblImagenProducto;
    }

    public void setLblImagenProducto(JLabel lblImagenProducto) {
        this.lblImagenProducto = lblImagenProducto;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnFactura() {
        return btnFactura;
    }

    public JTextField getTxtPrecioProducto() {
        return txtPrecioProducto;
    }

    public void setTxtPrecioProducto(JTextField txtPrecioProducto) {
        this.txtPrecioProducto = txtPrecioProducto;
    }

    public JTextField getTxtPeso() {
        return txtPeso;
    }

    public void setTxtPeso(JTextField txtPeso) {
        this.txtPeso = txtPeso;
    }

    public JTextField getTxtPrecioProductoC() {
        return txtPrecioProductoC;
    }

    public void setTxtPrecioProductoC(JTextField txtPrecioProductoC) {
        this.txtPrecioProductoC = txtPrecioProductoC;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }

    public void setBotones(ArrayList<JButton> botones) {
        this.botones = botones;
    }
    
    public void registrarEscuchasBotonesFrutas(Controlador c){
        for (int i = 0; i < botones.size(); i++) {
            botones.get(i).addActionListener(c);
        }
    }

    public JTextField getTxtConsultarP() {
        return txtConsultarP;
    }
    
    public void registrarEscuchas(Controlador c){
        btnAgregar.addActionListener(c);
        btnFactura.addActionListener(c);
        btnVolver.addActionListener(c);
        cbbMedidas.addActionListener(c);
    }

}