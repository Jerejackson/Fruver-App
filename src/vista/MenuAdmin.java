package vista;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jeremi_Sanchez
 */
public class MenuAdmin extends JPanel{
    JButton btnCaja, btnHistorico, btnRegistrarP,btnRegistrarUsuario;
    JLabel lblDerechos1;
    JLabel lblDerechos2;
    JLabel lblDerechos3;
    
    public MenuAdmin(){
        inicializar();
        setLayout(new BorderLayout());
        add(panelBotones(), BorderLayout.WEST);
        add(panelCentro(), BorderLayout.CENTER);
    }
    
    private JPanel panelBotones(){
        JPanel p = new JPanel();
        p. setLayout(new GridLayout(4,1));
        p.add(alinearBtn(btnCaja,new JLabel("Nueva Venta")));
        p.add(alinearBtn(btnHistorico,new JLabel("Consultar Ventas")));
        p.add(alinearBtn(btnRegistrarP,new JLabel("Producto Nuevo")));
        p.add(alinearBtn(btnRegistrarUsuario,new JLabel("Usuario Nuevo")));
        p.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 10));
        p.setOpaque(false);
        return p;
    }
    
    JPanel alinearBtn(JButton btn, JLabel lbl){
        JPanel p = new JPanel();
        
        p.setLayout(new GridBagLayout());
        lbl.setBackground(Color.white);
        lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
       
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        p.add(btn, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        p.add(lbl, constraints);
        p.setOpaque(false);
        return p;
    }
    
    private JPanel panelCentro(){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(alineartexto(), BorderLayout.SOUTH);
        p.setOpaque(false);
        return p;
    }
    
    JPanel alineartexto(){
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3,1));
        p.add(lblDerechos1);
        p.add(lblDerechos2);
        p.add(lblDerechos3);
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 50));
        return p;
    }
    
    @Override 
     public void paintComponent(Graphics g ){
        super.paintComponent(g);
        ImageIcon background = new ImageIcon(System.getProperty("user.dir")+"/img/Harvest.png");
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
       
     }
    private ImageIcon cambiarTamañoIcono(ImageIcon icon,int alto,int ancho){
        Image img = icon.getImage();
        Image iconoGrande = img.getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
        return new ImageIcon(iconoGrande);
    }
    
    private void inicializar(){
        btnCaja = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/shopping_cart.png"),100,100));
        btnHistorico = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/calendario.png"),100,100));
        btnRegistrarP = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/default.png"),100,100));
        btnRegistrarUsuario = new JButton(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/agricultor.png"),100,100));
        btnCaja.setBackground(Color.white);
        btnHistorico.setBackground(Color.white);
        btnRegistrarP.setBackground(Color.white);
        btnRegistrarUsuario.setBackground(Color.white);

        lblDerechos1 =new JLabel("CREADO Y DISTRIBUIDO POR SEBAS & JEREMI");
        lblDerechos1.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        lblDerechos1.setHorizontalAlignment(JLabel.RIGHT);
        lblDerechos2 =new JLabel("2020 Todos los derechos reservados");
        lblDerechos2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        lblDerechos2.setHorizontalAlignment(JLabel.RIGHT);
        lblDerechos3 =new JLabel("Soporte 3163311159");
        lblDerechos3.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        lblDerechos3.setHorizontalAlignment(JLabel.RIGHT);
    }

    public JButton getBtnCaja() {
        return btnCaja;
    }

    public JButton getBtnHistorico() {
        return btnHistorico;
    }

    public JButton getBtnRegistrarP() {
        return btnRegistrarP;
    }

    public JButton getBtnRegistrarUsuario() {
        return btnRegistrarUsuario;
    }
    
    public void registrarEscuchas(Controlador c){
        btnCaja.addActionListener(c);
        btnHistorico.addActionListener(c);
        btnRegistrarP.addActionListener(c);
        btnRegistrarUsuario.addActionListener(c);
    }
    
}