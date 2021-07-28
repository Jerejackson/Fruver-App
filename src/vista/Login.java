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
import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JPanel{
    JTextField txtUsuario, txtContrasena;
    JButton btnIngresar, btnLimpiar, btnCerrar;
    JLabel lblUsuario, lblContrasena;
    ImageIcon background, icoLimpiar, icoIngresar, icoCerrar;
    
    String[] imagen = {"/img/agricultor.png", "/img/agricultora.png"};
    int aleatorio = new Random().nextInt(imagen.length);
    

    public Login(){
        inicializar();
        this.setLayout( new GridLayout(1,2));
        this.add(panelLogo(imagen, aleatorio));
        this.add(panelFormulario());
     
    }
    
    private JPanel panelLogo(String[] aleatorio, int num){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel(new ImageIcon(new ImageIcon(System.getProperty("user.dir")+aleatorio[num]).getImage())), BorderLayout.SOUTH);
        p.setOpaque(false);
        return p;
    }
    
    private JPanel panelFormulario(){
        JPanel pp = new JPanel();
        JPanel p = new JPanel();
        pp.setLayout(new BorderLayout());
        p.setLayout(new GridLayout(2,1));
        pp.setOpaque(false);
        p.setOpaque(false);
        p.add(panelSesion());
        p.add(panelBotones());
        pp.add(p, BorderLayout.SOUTH);
        return pp;
    }
    
    JPanel panelSesion(){
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new GridLayout(2,2));
        p.add(lblUsuario);
        p.add(lblContrasena);
        p.add(txtUsuario);
        p.add(txtContrasena);
        return p;
    }
    
    JPanel panelBotones(){
        JPanel p= new JPanel();
        p.setOpaque(false);
        p.add(btnIngresar);
        p.add(btnLimpiar);
        p.add(btnCerrar);
        return p;
    }
    
    @Override 
     public void paintComponent(Graphics g ){
        super.paintComponent(g);
        background = new ImageIcon(new ImageIcon(System.getProperty("user.dir")+"/img/Harvest.png").getImage());
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
       
     }
     
     
    private ImageIcon cambiarTamañoIcono(ImageIcon icon){
        Image img = icon.getImage();
        Image iconoGrande = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        return new ImageIcon(iconoGrande);
    }
    
    /*
    public ImageIcon cargarImg(String url)throws IOException{
        ClassLoader loader = this.getClass().getResource();
        BufferedImage icon = ImageIO.read(loader.getResourceAsStream(url));
        return new ImageIcon(icon);
    }*/
    
    private void inicializar(){
        txtUsuario = new JTextField(30);
        txtUsuario.setPreferredSize(new Dimension(100,10));
        txtContrasena  = new JTextField(30);
        txtContrasena.setPreferredSize(new Dimension(100,10));
        
        icoLimpiar = new ImageIcon(System.getProperty("user.dir")+"/img/icons/login/limpiar.png");
        icoIngresar = new ImageIcon(System.getProperty("user.dir")+"/img/icons/login/acceder.png");
        icoCerrar = new ImageIcon(System.getProperty("user.dir")+"/img/icons/login/cancelar.png");
        
        btnIngresar = new JButton(cambiarTamañoIcono(icoIngresar));
        btnLimpiar = new JButton(cambiarTamañoIcono(icoLimpiar));
        btnCerrar = new JButton(cambiarTamañoIcono(icoCerrar));
        btnIngresar.setBackground(Color.WHITE);
        btnLimpiar.setBackground(Color.WHITE);
        btnCerrar.setBackground(Color.WHITE);
       /* btnIngresar.setOpaque(false);
        btnLimpiar.setOpaque(false);
        btnCerrar.setOpaque(false);
        */
        lblUsuario = new JLabel("Usuario:");
        lblContrasena = new JLabel("Contraseña:");
        lblUsuario.setFont(new Font("Serif", Font.BOLD, 30));
        lblContrasena.setFont(new Font("Serif", Font.BOLD, 30));
        
    }   

    public JButton getBtnIngresar() {
        return btnIngresar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnCerrar() {
        return btnCerrar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JTextField getTxtContrasena() {
        return txtContrasena;
    }

    public void setTxtContrasena(JTextField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }
    
    public void registrarEscuchas(Controlador c){
        btnIngresar.addActionListener(c);
        btnLimpiar.addActionListener(c);
        btnCerrar.addActionListener(c);
    }
}