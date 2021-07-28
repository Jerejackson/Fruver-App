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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Jeremi_Sanchez
 */
public class EditarEliminarProducto extends JPanel{
    ArrayList<JButton> botones = new ArrayList<>();
    JButton btnCancelar;
    ImageIcon background;
    JLabel lblTitulo;
    
    public EditarEliminarProducto(){
        inicializar();
        setLayout(new BorderLayout());
        add(norte(), BorderLayout.NORTH);
        add(btnCancelar, BorderLayout.SOUTH);
    }
    
    private JPanel norte(){
        JPanel p = new JPanel();
        p.add(lblTitulo);
        p.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
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
    
    public int ventanaConfirmacion(){
        String option[] = {"Editar","Eliminar"};
        return JOptionPane.showOptionDialog(null,"¿Que Desea Hacer Con Este Registro?","Editar o Eliminar Producto", 0,JOptionPane.QUESTION_MESSAGE,null,option,null);
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
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(Color.red);
        lblTitulo = new JLabel("Lista de Productos");
        lblTitulo.setFont(new Font("Comic sans MS", Font.BOLD, 30));
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }

    public void setBotones(ArrayList<JButton> botones) {
        this.botones = botones;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }    
    
    
    public void registrarEscuchas(Controlador c){
        btnCancelar.addActionListener(c);
    }
    public void registrarEscuchasBotonesFrutas(Controlador c){
        for (int i = 0; i < botones.size(); i++) {
            botones.get(i).addActionListener(c);
        }
    }
}