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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegistrarUsuario extends JPanel{
    JTextField txtCodigo, txtUsuario, txtContrasena ,txtnewContrasena;
    JButton btnBuscar, btnGuardar, btnLimpiar, btnCerrar;
    JLabel lblCodigo,lblUsuario, lblContrasena,lblnewContrasena;
    ImageIcon background, icoLimpiar, icoIngresar, icoCerrar, icoBuscar;
    int identificador= 0;
    
    public RegistrarUsuario(){
        inicializar();
        this.setLayout( new GridLayout(1,2));
        this.add(MenuLeft());
        this.add(MenuRight());
        this.setBorder(BorderFactory.createEmptyBorder(0, 50, 0,0));
    }
    
    JPanel panelLogo(String[] aleatorio, int num){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        ImageIcon imagen = new ImageIcon(System.getProperty("user.dir")+aleatorio[num]);
        p.add(new JLabel(imagen), BorderLayout.SOUTH);
        p.setOpaque(false);
        return p;
    }
    
    private JPanel MenuRight(){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(panelBotones(), BorderLayout.SOUTH);
        p.setOpaque(false);
        return p;
    }
    
    private JPanel MenuLeft(){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setOpaque(false);
        p.add(panelSesion(),BorderLayout.WEST);
        return p;
    }
    
    JPanel panelSesion(){
        JPanel p = new JPanel();
        JPanel pp = new JPanel();
        pp.setOpaque(false);
        p.setOpaque(false);
        pp.setLayout(new GridLayout(4,1));
        pp.add(subPanelBuscar(SubPanel(lblCodigo,txtCodigo),btnBuscar));
        pp.add(SubPanel(lblUsuario,txtUsuario));
        pp.add(SubPanel(lblContrasena,txtContrasena));
        pp.add(SubPanel(lblnewContrasena,txtnewContrasena));
        return pp;
    }
    
    JPanel subPanelBuscar(JPanel pp, JButton btn){
        JPanel p = new JPanel();
        JPanel ppp = new JPanel();        
        p.setOpaque(false);
        ppp.setOpaque(false);
        ppp.add(btn);
        ppp.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));
        p.add(pp);
        p.add(ppp);
        p.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));
        return p;
    }
    
    JPanel SubPanel(JLabel lbl, JTextField txt){
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new GridLayout(2,1));
        p.add(lbl);
        p.add(txt);
        p.setBorder(BorderFactory.createEmptyBorder(40,0,40,0));
        return p;
    }
    
    JPanel panelBotones(){
        JPanel p= new JPanel();
        p.setOpaque(false);
        p.add(btnGuardar);
        p.add(btnLimpiar);
        p.add(btnCerrar);
        p.setBorder(BorderFactory.createEmptyBorder(0,0,100,100));
        return p;
    }
    
    @Override 
     public void paintComponent(Graphics g ){
        super.paintComponent(g);
        background = new ImageIcon(System.getProperty("user.dir")+"/img/Harvest.png");
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
     }
     
    private ImageIcon cambiarTamañoIcono(ImageIcon icon, int ancho, int alto){
        Image img = icon.getImage();
        Image iconoGrande = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(iconoGrande);
    }
    
    private void inicializar(){
        txtCodigo = new JTextField(30);
        txtCodigo.setPreferredSize(new Dimension(100,10));
        txtUsuario = new JTextField(30);
        txtUsuario.setPreferredSize(new Dimension(100,10));
        txtContrasena  = new JTextField(30);
        txtContrasena.setPreferredSize(new Dimension(100,10));
        txtnewContrasena  = new JTextField(30);
        txtnewContrasena.setPreferredSize(new Dimension(100,10));
        
        icoLimpiar = new ImageIcon(System.getProperty("user.dir")+"/img/icons/login/limpiar.png");
        icoIngresar = new ImageIcon(System.getProperty("user.dir")+"/img/icons/disquete.png");
        icoCerrar = new ImageIcon(System.getProperty("user.dir")+"/img/icons/login/cancelar.png");
        icoBuscar = new ImageIcon(System.getProperty("user.dir")+"/img/icons/buscar.png");
        
        btnGuardar = new JButton(cambiarTamañoIcono(icoIngresar,100,100));
        btnLimpiar = new JButton(cambiarTamañoIcono(icoLimpiar,100,100));
        btnCerrar = new JButton(cambiarTamañoIcono(icoCerrar,100,100));
        btnBuscar = new JButton(cambiarTamañoIcono(icoBuscar,30,30));
        btnGuardar.setBackground(Color.WHITE);
        btnLimpiar.setBackground(Color.WHITE);
        btnCerrar.setBackground(Color.WHITE);
        btnBuscar.setBackground(Color.WHITE);
        
        lblCodigo = new JLabel("Identificación:");
        lblUsuario = new JLabel("Nombre:");
        lblContrasena = new JLabel("Contraseña:");
        lblnewContrasena = new JLabel("Nueva Contraseña:");
        lblCodigo.setFont(new Font("Serif", Font.BOLD, 30));
        lblUsuario.setFont(new Font("Serif", Font.BOLD, 30));
        lblContrasena.setFont(new Font("Serif", Font.BOLD, 30));
        lblnewContrasena.setFont(new Font("Serif", Font.BOLD, 30));
        
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnCerrar() {
        return btnCerrar;
    }
    
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
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

    public JTextField getTxtnewContrasena() {
        return txtnewContrasena;
    }

    public void setTxtnewContrasena(JTextField txtnewContrasena) {
        this.txtnewContrasena = txtnewContrasena;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }
    
    public void registrarEscuchas(Controlador c){
        btnCerrar.addActionListener(c);
        btnGuardar.addActionListener(c);
        btnLimpiar.addActionListener(c);
        btnBuscar.addActionListener(c);
    }
}