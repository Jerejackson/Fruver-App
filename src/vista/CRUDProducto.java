package vista;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import javafx.stage.FileChooser;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Jeremi_Sanchez
 */
public class CRUDProducto extends JPanel{
    JTextField txtNombre, txtPrecio;
    String medida[] = {"Seleccione Medida"};
    
    JButton btnAgregar, btnLimpiar, btnAtras,btnSeleccionImagen, btnListaProductos;
    JLabel lblImagen, lblTitulo;
    ImageIcon imagen,background;
    JComboBox cbbMedidas;
    JMenuItem jexit;
    
    File archivo;
    FileChooser selector;
    
    DefaultComboBoxModel modeloCbb;
    
    public CRUDProducto(){

        inicializar();
        setLayout(new BorderLayout());
        
        add(formularioCenter(), BorderLayout.CENTER);
        add(botones(), BorderLayout.SOUTH);
    }
    
    
    //Formulario Principal del centro donde agrego la parte izquierda de imagen y la derecha de los fill boxes
    private JPanel formularioCenter(){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        p.add(formularioRight(),BorderLayout.EAST);
        p.add(formularioLeft(),BorderLayout.WEST);
        p.setOpaque(false);
        return p;
    }
    
    
    //Fill boxes de nombre precio y unidad de medida
    
    JPanel formularioRight(){
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(150, 0, 10,90));
        p.add(ingresoDatos());
        p.setOpaque(false);
        return p;
    }
    
 
    //aqui esta la imagen a la izquierda
    JPanel formularioLeft(){
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(50, 100, 10, 0));
        p.add(lblImagen);
        p.setOpaque(false);
        return p;
    }
    
    JPanel ingresoDatos(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3,1));
        p.add(ingresoDatosUp());
        p.add(ingresoDatosCenter());
        p.add(ingresoDatosDown());
        p.setOpaque(false);
        return p;
    }
    
    JPanel ingresoDatosUp(){
        JPanel p = new JPanel();
        p.add(lblTitulo);
        p.setOpaque(false);
        return p;
    }
    
    JPanel ingresoDatosCenter(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3,2));
        p.add(new JLabel("NOMBRE:"));    p.add(txtNombre);
        p.add(new JLabel("PRECIO:"));    p.add(txtPrecio);
        p.add(new JLabel("UNIDAD DE MEDIDA:"));    p.add(cbbMedidas);
        p.setOpaque(false);
        return p;
    }
    
    JPanel ingresoDatosDown(){
        JPanel p = new JPanel();
        p.add(btnSeleccionImagen); 
        p.setOpaque(false);
        return p;
    }
    
    private JPanel botones(){
        JPanel p = new JPanel();
        p.add(btnAgregar);
        p.add(btnLimpiar);
        p.add(btnAtras);
        p.add(btnListaProductos);
        
        p.setOpaque(false);
        
        return p;
    }

    @Override
    public void paintComponent(Graphics g ){
        super.paintComponent(g);
        background = new ImageIcon(System.getProperty("user.dir")+"/img/Fondo.jpg");
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
     
    private ImageIcon cambiarTamañoIcono(ImageIcon icon, int ancho, int alto){
        Image img = icon.getImage();
        Image iconoGrande = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(iconoGrande);
    }

    private void inicializar(){
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        
        btnListaProductos = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/Lista.png"),50,50));
        btnAgregar = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/disquete.png"),50,50));
        btnSeleccionImagen = new JButton("Seleccionar imagen");
        btnLimpiar = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/login/limpiar.png"),50,50));
        btnAtras = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/login/atras.png"),50,50));
        
        btnListaProductos.setBackground(Color.white);
        btnAgregar.setBackground(Color.white);
        btnSeleccionImagen.setBackground(Color.white);
        btnLimpiar.setBackground(Color.white);
        btnAtras.setBackground(Color.white);
        
        lblTitulo = new JLabel("SELECCIONAR IMAGEN");
        lblTitulo.setFont(new Font("Comic sans MS", Font.BOLD, 30));
        
        lblImagen = new JLabel("");
        lblImagen.setIcon(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/default.png"), 500,500));
        
        modeloCbb = new DefaultComboBoxModel();
        modeloCbb.addElement("Seleccionar Medida");
        cbbMedidas = new JComboBox(modeloCbb);
        cbbMedidas.setBackground(Color.white);
        jexit = new JMenuItem("Exit");
    }

    public String[] getMedida() {
        return medida;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnAtras() {
        return btnAtras;
    }

    public JButton getBtnSeleccionImagen() {
        return btnSeleccionImagen;
    }

    public JButton getBtnListaProductos() {
        return btnListaProductos;
    }

    public JLabel getLblImagen() {
        return lblImagen;
    }

    public JMenuItem getJexit() {
        return jexit;
    }

    public JComboBox getCbbMedidas() {
        return cbbMedidas;
    }

    public DefaultComboBoxModel getModeloCbb() {
        return modeloCbb;
    }

    public void setModeloCbb(DefaultComboBoxModel modeloCbb) {
        this.modeloCbb = modeloCbb;
    }

    public void setLblImagen(JLabel lblImagen) {
        this.lblImagen = lblImagen;
    }
    
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }
    
    public void registrarEscuchas(Controlador c){
        btnAgregar.addActionListener(c);
        btnLimpiar.addActionListener(c);
        btnAtras.addActionListener(c);
        btnSeleccionImagen.addActionListener(c);
        btnListaProductos.addActionListener(c);
    }
}