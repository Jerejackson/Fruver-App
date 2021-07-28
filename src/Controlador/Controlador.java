/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Consultas.Consultas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import modelo.Formulas;
import modelo.ObtenerFechaActual;
import vista.Ventana;

/**
 *
 * @author Jeremi_Sanchez
 */
public class Controlador implements ActionListener, KeyListener{

    Ventana v;
    Consultas c;
    Formulas f;
    ObtenerFechaActual ofa;
    
    File file;
    
    public Controlador(Ventana v, Consultas c){
        this.v = v;
        this.c = c;
        f = new Formulas();
        ofa = new ObtenerFechaActual();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        /*****************************************************************************
        *********************************** LOGIN ***********************************
        ******************************************************************************/
        
        if(ae.getSource() == v.getLogin().getBtnIngresar()){
            validarInicioSesion();
        }
        
        if(ae.getSource() == v.getLogin().getBtnLimpiar()){
            limpiarLogin();
        }
        
        if(ae.getSource() == v.getLogin().getBtnCerrar()){
            v.dispose();
        }
        
        /*****************************************************************************
        ***************************** BUSCAR PRODUCTO *******************************
        ******************************************************************************/
        
        if(ae.getSource() == v.getBp().getBtnAgregar()){
            agregarProductoALista();
        }
        
        if(ae.getSource() == v.getBp().getCbbMedidas()){
            try{
                v.getBp().getTxtPrecioProductoC().setText(String.valueOf(f.calcularPrecio(Double.parseDouble(v.getBp().getTxtPeso().getText()), Double.parseDouble(String.valueOf(v.getBp().getTempProducto().get(3))), String.valueOf(v.getBp().getTempProducto().get(2)),String.valueOf(v.getBp().getCbbMedidas().getSelectedItem()))));
            }catch(NumberFormatException ex){
                v.getBp().getTxtPrecioProductoC().setText("0");
            }catch(IndexOutOfBoundsException ex){
                JOptionPane.showConfirmDialog(null, "Debe Seleccionar Primero el Producto", "Añadir Producto", JOptionPane.DEFAULT_OPTION);
                v.getBp().getTxtPeso().setText("");
            }
            
            v.getBp().getTxtPeso().requestFocus();
        }
        
        if(ae.getSource() == v.getBp().getBtnFactura()){
            //limpiarNulosListaProductos();
            try{
                v.getRv().remove(2);
            }catch(ArrayIndexOutOfBoundsException ex){}

            for (int i = 0; i < f.getListaProductos().size(); i++) {
                System.out.println(f.getListaProductos().get(i)[0]+" - "+f.getListaProductos().get(i)[1]+" - "+f.getListaProductos().get(i)[2]+" - "+f.getListaProductos().get(i)[3]+" - ");
            }
            
            //mostrar valor total de la venta en resumen venta
            v.getRv().getTxtTotal().setText(String.valueOf(f.calcularTotal(f.getListaProductos())));
            if(v.getRv().getTxtDevolucion().getText() != null || !"".equals(v.getRv().getTxtDevolucion().getText())){
                actualizarDevolucionResumenVenta();
            } else {}
            
            v.getRv().add(v.getRv().listaProductos(f.getListaProductos()), BorderLayout.CENTER);
            v.getRv().registrarEscuchasBotonesX(this);
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getRv());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();
            
            v.getRv().getLblEmpleado().setText("Cajero: "+ f.getDatosUsuario()[1]);
            v.getRv().getTxtRecibido().requestFocus();
            
        }
        
        for (int i = 0; i < v.getBp().getBotones().size(); i++) {
            if(ae.getSource() == v.getBp().getBotones().get(i)){
                ArrayList<Object[]> producto = new ArrayList<>(c.consultarProducto(v.getBp().getBotones().get(i).getText()));
                v.getBp().getTempProducto().add(0, producto.get(0)[0]);
                v.getBp().getTempProducto().add(1, producto.get(0)[1]);
                v.getBp().getTempProducto().add(2, producto.get(0)[2]);
                v.getBp().getTempProducto().add(3, producto.get(0)[3]);
                v.getBp().getTxtPrecioProducto().setText(String.valueOf(producto.get(0)[3]));
                v.getBp().getLblMedidaPeso().setText(String.valueOf(producto.get(0)[2]));
                v.getBp().getLblNombreP().setText(String.valueOf(producto.get(0)[1]));
                v.getBp().getLblImagenProducto().setIcon(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/Icons/IconosP/"+String.valueOf(producto.get(0)[1])+".png"),150,150));
                switch(String.valueOf(v.getBp().getTempProducto().get(2))){
                    case "Gramo(g)" :   v.getBp().getCbbMedidas().setSelectedIndex(0);
                                        v.getBp().getCbbMedidas().setEnabled(true);
                        break;
                    case "Libra(Lb)" :   v.getBp().getCbbMedidas().setSelectedIndex(0);
                                        v.getBp().getCbbMedidas().setEnabled(true);
                        break;
                    case "Kilogramo(Kg)" :   v.getBp().getCbbMedidas().setSelectedIndex(0);
                                        v.getBp().getCbbMedidas().setEnabled(true);
                        break;
                    case "Unidad" : v.getBp().getCbbMedidas().setSelectedIndex(3);
                                    v.getBp().getCbbMedidas().setEnabled(false);
                        break;
                }
                v.getBp().getTxtPeso().setText("");
                v.getBp().getTxtPrecioProductoC().setText("");
                producto.clear();
                v.getBp().getTxtPeso().requestFocus();
                
                v.getBp().getTxtConsultarP().setText("");
                try{
                    v.getBp().remove(3);
                    v.getBp().getBotones().clear();
                    v.getBp().add(v.getBp().botones(buscarPalabra(v.getBp().getTxtConsultarP().getText())), BorderLayout.CENTER);
                    v.getBp().registrarEscuchasBotonesFrutas(this);
                    v.getpPrincipal().revalidate();
                    v.getpPrincipal().repaint();
                }catch(ArrayIndexOutOfBoundsException ex){
                    v.getBp().getBotones().clear();
                    v.getBp().add(v.getBp().botones(buscarPalabra(v.getBp().getTxtConsultarP().getText())), BorderLayout.CENTER);
                    v.getBp().registrarEscuchasBotonesFrutas(this);
                    v.getpPrincipal().revalidate();
                    v.getpPrincipal().repaint();
                }
            }
        }
        
        /*****************************************************************************
        ***************************** RESUMEN VENTA *********************************
        ******************************************************************************/
        
        if(ae.getSource() == v.getRv().getBtnAgregar()){
            
            v.getBp().remove(3);
            v.getBp().getBotones().clear();
            v.getBp().add(v.getBp().botones(c.consultarProductos()), BorderLayout.CENTER);
            v.getBp().registrarEscuchasBotonesFrutas(this);
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getBp());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();
            v.getBp().getTxtTotal().setText(String.valueOf(f.calcularTotal(f.getListaProductos())));
            v.getBp().getTxtPeso().requestFocus();
        }
        
        for (int i = 0; i < v.getRv().getBotones().size(); i++) {
            if(ae.getSource() == v.getRv().getBotones().get(i)){
                System.out.println(String.valueOf(f.getListaProductos().get(i)[0]));
                f.getListaProductos().get(i)[0] = null;
                f.getListaProductos().get(i)[1] = null;
                f.getListaProductos().get(i)[2] = null;
                f.getListaProductos().get(i)[3] = null;
                limpiarNulosListaProductos();
                
                v.getRv().getTxtTotal().setText(String.valueOf(f.calcularTotal(f.getListaProductos())));
                actualizarDevolucionResumenVenta();
                try{
                    
                    v.getRv().remove(2);
                    v.getRv().getBotones().clear();
                    v.getRv().add(v.getRv().listaProductos(f.getListaProductos()), BorderLayout.CENTER);
                    v.getRv().registrarEscuchasBotonesX(this);
                    v.getpPrincipal().revalidate();
                    v.getpPrincipal().repaint();
                }catch(ArrayIndexOutOfBoundsException ex){}
                }
        }
        
        if(ae.getSource() == v.getRv().getBtnImprimir()){
            switch(JOptionPane.showConfirmDialog(null,"¿Realmente Desea Realizar la Venta?", "Confirmar Compra",JOptionPane.YES_NO_OPTION)){
                case 0: imprimir();
                    break;
            }
        }
        
        /*****************************************************************************
        ***************************** CONSULTAR VENTAS ******************************
        ******************************************************************************/
        
        if(ae.getSource() == v.getCv().getBtnHoy()){
            try{
                v.getCv().remove(2);
                v.getCv().add(v.getCv().listaProductos(c.consultarVentasDia(ofa.getFechaActualInverso())), BorderLayout.CENTER);
                v.getpPrincipal().revalidate();
                v.getpPrincipal().repaint();
            }catch(ArrayIndexOutOfBoundsException ex){
                v.getCv().add(v.getCv().listaProductos(c.consultarVentasDia(ofa.getFechaActualInverso())), BorderLayout.CENTER);
                v.getpPrincipal().revalidate();
                v.getpPrincipal().repaint();
            }
                
        }

        if(ae.getSource() == v.getCv().getBtnVolver()){
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getMa());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();
        }
        
        if(ae.getSource() == v.getCv().getBtnBuscar()){
            String dia= Integer.toString(v.getCv().getJcFecha().getCalendar().get(Calendar.DAY_OF_MONTH));
            if(dia.length() == 1){
                dia = "0"+dia;
            }
            String mes = Integer.toString(v.getCv().getJcFecha().getCalendar().get(Calendar.MONTH)+1);
            if(mes.length() == 1){
                mes = "0"+mes;
            }
            String anio = Integer.toString(v.getCv().getJcFecha().getCalendar().get(Calendar.YEAR));
            try{
                v.getCv().remove(2);
                v.getCv().add(v.getCv().listaProductos(c.consultarVentasDia(anio+"-"+mes+"-"+dia)), BorderLayout.CENTER);
                v.getpPrincipal().revalidate();
                v.getpPrincipal().repaint();
            }catch(ArrayIndexOutOfBoundsException ex){
                v.getCv().add(v.getCv().listaProductos(c.consultarVentasDia(anio+"-"+mes+"-"+dia)), BorderLayout.CENTER);
                v.getpPrincipal().revalidate();
                v.getpPrincipal().repaint();
            }
        }
        
        /*****************************************************************************
        ******************************** MENU ADMIN **********************************
        ******************************************************************************/
        
        if(ae.getSource() == v.getMa().getBtnCaja()){
            v.getBp().getBotones().clear();
            v.getBp().add(v.getBp().botones(c.consultarProductos()), BorderLayout.CENTER);
            v.getBp().registrarEscuchasBotonesFrutas(this);
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getBp());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();
            
            if(v.getBp().getCbbMedidas().getItemCount() < 2){
                for (int i = 0; i < c.consultarMedidasPeso().length; i++) {
                    v.getBp().getModeloCbb().addElement(String.valueOf(c.consultarMedidasPeso()[i][1]));
                }
            }
        }
        
        if(ae.getSource() == v.getMa().getBtnHistorico()){
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getCv());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();
        }
        
        if(ae.getSource() == v.getMa().getBtnRegistrarUsuario()){
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getRe());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();
        }
        
        if(ae.getSource() == v.getMa().getBtnRegistrarP()){
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getCp());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();
            
            //System.out.println("lenght " +c.consultarMedidasPeso().length);
            //System.out.println("lenght " +c.consultarMedidasPeso()[0].length);
            
            if(v.getCp().getCbbMedidas().getItemCount() < 2){
                for (int i = 0; i < c.consultarMedidasPeso().length; i++) {
                    v.getCp().getModeloCbb().addElement(String.valueOf(c.consultarMedidasPeso()[i][1]));
                }
            }
        }
        
        /*****************************************************************************
        ***************************** CRUD PRODUCTO **********************************
        ******************************************************************************/
        
        if(ae.getSource() == v.getCp().getBtnAgregar()){
            try{
                //System.out.println(c.existenciaProducto(ucFirst(v.getCp().getTxtNombre().getText())));
                
                //evitar un exception

                if(c.existenciaProducto(ucFirst(v.getCp().getTxtNombre().getText())).equals(ucFirst(v.getCp().getTxtNombre().getText()))){
                    switch(JOptionPane.showConfirmDialog(null,"El producto ya Existe ¿Desea Actualizar sus valores?", "Actualizar Producto", 
                                    JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null)){

                        case 0: {
                            
                            c.editarProducto(ucFirst(v.getCp().getTxtNombre().getText()), String.valueOf(v.getCp().getCbbMedidas().getSelectedItem()), Integer.parseInt(v.getCp().getTxtPrecio().getText()));
                            deleteFile(String.valueOf(System.getProperty("user.dir")+"/img/Icons/IconosP/"+ucFirst(v.getCp().getTxtNombre().getText())+".png"));
                            try{
                                copyFile(file.getPath(),new File(System.getProperty("user.dir"))+"/img/Icons/IconosP/"+ucFirst(v.getCp().getTxtNombre().getText())+".png");
                            }catch(NullPointerException ex){
                                copyFile(String.valueOf(System.getProperty("user.dir")+"/img/Icons/IconosP/temp.png"),new File(System.getProperty("user.dir"))+"/img/Icons/IconosP/"+ucFirst(v.getCp().getTxtNombre().getText())+".png");
                            }
                        }
                            break;
                    }
                }else{
                    c.guardarProducto(ucFirst(v.getCp().getTxtNombre().getText()), String.valueOf(v.getCp().getCbbMedidas().getSelectedItem()), Integer.parseInt(v.getCp().getTxtPrecio().getText()));
                    try{
                        copyFile(file.getPath(),new File(System.getProperty("user.dir"))+"/img/Icons/IconosP/"+ucFirst(v.getCp().getTxtNombre().getText())+".png");
                        limpiarCRUDProducto();
                    }catch(NullPointerException ex){
                        copyFile(System.getProperty("user.dir")+"/img/Icons/IconosP/temp.png",new File(System.getProperty("user.dir"))+"/img/Icons/IconosP/"+ucFirst(v.getCp().getTxtNombre().getText())+".png");
                        limpiarCRUDProducto();
                    }
                    
                }
                deleteFile(String.valueOf(System.getProperty("user.dir")+"/img/Icons/IconosP/temp.png"));
            }catch(NumberFormatException ex){
                JOptionPane.showConfirmDialog(null, "Todos los Campos Deben Encontrarse Diligenciados", "Producto", JOptionPane.DEFAULT_OPTION);
            }
            
        }
        
        if(ae.getSource() == v.getCp().getBtnAtras()){
            limpiarCRUDProducto();
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getMa());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();
        }
        
        if(ae.getSource() == v.getCp().getBtnLimpiar()){
            limpiarCRUDProducto();
        }
        
        if(ae.getSource() == v.getCp().getBtnSeleccionImagen()){
            JFileChooser fc = new JFileChooser(System.getProperty("user.dir")+"/img/Icons/frutas/");
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                try {
                    v.getCp().getLblImagen().setIcon(cambiarTamañoIcono(new ImageIcon(ImageIO.read(file)), 500,500));
                } catch (IOException e) {
                }
                //Cambia el nombre del archivo imagen
                String ruta = file.getParent()+"/"+file.getName().replaceAll(" ", "");
                file.renameTo(new File(ruta));
                file = new File(ruta);
            }
        }
        
        if(ae.getSource() == v.getCp().getJexit()){
            System.exit(0);
        }
        
        if(ae.getSource() == v.getCp().getBtnListaProductos()){
            limpiarCRUDProducto();
            v.getEep().getBotones().clear();
            v.getEep().add(v.getEep().botones(c.consultarProductos()), BorderLayout.CENTER);
            
            v.getEep().registrarEscuchasBotonesFrutas(this);
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getEep());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();

        }
        
        if(ae.getSource() == v.getBp().getBtnVolver()){
            if(Integer.parseInt(f.getDatosUsuario()[2]) == 2){
                v.getpPrincipal().removeAll();
                v.getpPrincipal().add(v.getMa());
                v.getpPrincipal().revalidate();
                v.getpPrincipal().repaint();
            }else{
                v.getpPrincipal().removeAll();
                v.getpPrincipal().add(v.getLogin());
                v.getpPrincipal().revalidate();
                v.getpPrincipal().repaint();
            }
        }
        
       /*****************************************************************************
        ******************************** CRUD EMPLEADO *******************************
        ******************************************************************************/
        if(ae.getSource() == v.getRe().getBtnBuscar()){
            try{
                ArrayList<Object[]> lista = new ArrayList<>(c.buscarEmpleado(Integer.parseInt(v.getRe().getTxtCodigo().getText())));
                try{
                    System.out.println(lista.get(0)[0]);
                }catch(IndexOutOfBoundsException ex){
                    Object i[] = {0};
                    lista.add(i);
                }

                if((Integer.parseInt(String.valueOf(lista.get(0)[0]))) == 0){
                    v.getRe().setIdentificador(Integer.parseInt(String.valueOf(lista.get(0)[0])));
                    JOptionPane.showConfirmDialog(null, "Identificación No Encontrada", "Registro Usuario", JOptionPane.DEFAULT_OPTION);
                }else{

                    v.getRe().setIdentificador(Integer.parseInt(String.valueOf(lista.get(0)[0])));
                    v.getRe().getTxtUsuario().setText(String.valueOf(lista.get(0)[1]));
                    v.getRe().getTxtContrasena().setText(String.valueOf(lista.get(0)[4]));
                    v.getRe().getTxtnewContrasena().setText(String.valueOf(lista.get(0)[4]));
                }
                lista.clear();
            }catch(NumberFormatException ex){
                JOptionPane.showConfirmDialog(null, "Solo es posible registrar o buscar valores numéricos en la Identificación", "Busqueda de Identificación", JOptionPane.DEFAULT_OPTION);
            }
        }
       
        if(ae.getSource() == v.getRe().getBtnGuardar()){
            if(v.getRe().getTxtContrasena().getText().equals(v.getRe().getTxtnewContrasena().getText())){
                try{
                    if(v.getRe().getIdentificador() != 0){
                        switch(JOptionPane.showConfirmDialog(null,"La Identificación del Empleado ya Existe ¿Desea Actualizar sus valores?", "Actualizar Empleado", 
                                        JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null)){
                            case 0: {
                                c.editarEmpleado(v.getRe().getIdentificador(), Integer.parseInt(v.getRe().getTxtCodigo().getText()), v.getRe().getTxtUsuario().getText(), v.getRe().getTxtnewContrasena().getText());
                            }
                                break;
                        }
                    }
                    else{
                        if(c.guardarEmpleado(Integer.parseInt(v.getRe().getTxtCodigo().getText()), v.getRe().getTxtUsuario().getText(), v.getRe().getTxtnewContrasena().getText()) == 0){
                            JOptionPane.showConfirmDialog(null, "Registro Exitoso", "Registro Usuario", JOptionPane.DEFAULT_OPTION); 
                        }else{
                            JOptionPane.showConfirmDialog(null, "El Usuario ya Existe en la Base de Datos", "Error En El Registro", JOptionPane.DEFAULT_OPTION); 
                        }
                    }
                }catch(NumberFormatException ex){
                    JOptionPane.showConfirmDialog(null, "No es posible Guardar o Actualizar los Datos del Usuario con valores Vacíos", "Usuario", JOptionPane.DEFAULT_OPTION);
                }
            }else{
                JOptionPane.showConfirmDialog(null, "Las Contraseñas no Coinciden", "Error de Registro", JOptionPane.DEFAULT_OPTION); 
            }
        }
        
        if(ae.getSource() == v.getRe().getBtnLimpiar()){
            limpiarRegistroEmpleado();
        }
       
        if(ae.getSource() == v.getRe().getBtnCerrar()){
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getMa());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();
            limpiarRegistroEmpleado();
        }
             
        /*****************************************************************************
        ******************** ELIMINAR EDITAR LISTA PRODUCTOS**************************
        ******************************************************************************/
        
        if(ae.getSource() == v.getEep().getBtnCancelar()){
            v.getpPrincipal().removeAll();
            v.getpPrincipal().add(v.getCp());
            v.getpPrincipal().revalidate();
            v.getpPrincipal().repaint();
            v.getEep().remove(2);            
        }
        
        
        for (int i = 0; i < v.getEep().getBotones().size(); i++) {
            if(ae.getSource() == v.getEep().getBotones().get(i)){
                //System.out.println(String.valueOf(v.getEep().getBotones().get(i).getText()));
                switch(v.getEep().ventanaConfirmacion()){
                case 0: v.getCp().getTxtNombre().setText(v.getEep().getBotones().get(i).getText());
                        v.getCp().getTxtPrecio().setText(String.valueOf(c.consultarUnProducto(v.getEep().getBotones().get(i).getText())[1]));
                        v.getCp().getCbbMedidas().setSelectedIndex(c.consultarUnProducto(v.getEep().getBotones().get(i).getText())[0]);
                        v.getCp().getLblImagen().setIcon(new ImageIcon(System.getProperty("user.dir")+"/img/Icons/IconosP/"+ucFirst(v.getCp().getTxtNombre().getText())+".png"));
                        //cambia el panel
                        v.getpPrincipal().removeAll();
                        v.getpPrincipal().add(v.getCp());
                        v.getpPrincipal().revalidate();
                        v.getpPrincipal().repaint();
                        copyFile(v.getCp().getLblImagen().getIcon().toString(),new File(System.getProperty("user.dir"))+"/img/Icons/IconosP/temp.png");
                        v.getEep().getBotones().clear();
                        v.getEep().remove(2);
                        
                    break;
                case 1: switch(JOptionPane.showConfirmDialog(null,"¿Realmente Desea Eliminar el Producto?", "Eliminar Producto", 
                                JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null)){
                        case 0: if("1451".equals(String.valueOf(c.borrarProducto(v.getEep().getBotones().get(i).getText())))){
                                    JOptionPane.showConfirmDialog(null, "El Producto No Puede Ser Eliminado, Debido A Que Se Han Realizado Ventas del Mismo", "Eliminar Producto", JOptionPane.DEFAULT_OPTION);
                                }else{
                                    deleteFile(String.valueOf(System.getProperty("user.dir")+"/img/Icons/IconosP/"+v.getEep().getBotones().get(i).getText()+".png"));
                                }
                                v.getEep().remove(2);
                            break;
                        case 1: System.out.println("no");
                            break;
                    }
                    break;
                
                }
                try{
                    v.getEep().remove(2);
                    v.getEep().add(v.getEep().botones(c.consultarProductos()), BorderLayout.CENTER);
                    v.getEep().revalidate();
                    v.getEep().repaint();
                }catch(ArrayIndexOutOfBoundsException ex){
                    v.getEep().add(v.getEep().botones(c.consultarProductos()), BorderLayout.CENTER);
                    v.getEep().revalidate();
                    v.getEep().repaint();
                }
                
            }
        }
    }
    //cambia a mayuscula el primer caracter de un texto
    String ucFirst(String str) {
        if (str == null || str.isEmpty()) {
          return "";            
        } else {
          return  Character.toUpperCase(str.charAt(0)) + str.substring(1, str.length()).toLowerCase();
        }
    }
    
    void deleteFile(String urlArchivo) {  
        File archivo=new File(urlArchivo);
        if (archivo.delete()){  
            System.out.println("El fichero ha sido borrado satisfactoriamente");
        }
        else{  
            System.out.println("El fichero no puede ser borrado");
        } 
    }
  
    void copyFile(String sourceFile, String destinationFile) {
        System.out.println("Desde: " + sourceFile);
        System.out.println("Hacia: " + destinationFile);
     
        try{  
            File inFile = new File(sourceFile);
            File outFile = new File(destinationFile);
            FileInputStream in = new FileInputStream(inFile);
            FileOutputStream out = new FileOutputStream(outFile);
            System.out.println("ruta inicio"+in+"ruta destino"+out);
      
            int band;
            while( (band = in.read() ) != -1)
             out.write(band);
             in.close();
             out.close();
        } catch(IOException e){  
            System.err.println("ERROR EN COPIADO DE FICHERO "+ e);
        }
    }
    
    private ImageIcon cambiarTamañoIcono(ImageIcon icon, int ancho, int alto){
        Image img = icon.getImage();
        Image iconoGrande = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(iconoGrande);
    }
    
    void validarInicioSesion(){
        if(!"".equals(v.getLogin().getTxtContrasena().getText()) && !"".equals(v.getLogin().getTxtUsuario().getText())){
            Object temp[] = new Object[5];
            temp = c.validarSesion(Integer.parseInt(v.getLogin().getTxtUsuario().getText()), v.getLogin().getTxtContrasena().getText()).clone();
            if(temp[3] == null){
                JOptionPane.showConfirmDialog(null, "El Usuario No Se Encuentra Registrado", "Inicio de Sesión", JOptionPane.DEFAULT_OPTION);
            }else{
                if(Integer.parseInt(String.valueOf(temp[3])) == 1){
                    f.getDatosUsuario()[0] = v.getLogin().getTxtUsuario().getText();
                    f.getDatosUsuario()[1] = String.valueOf(temp[1]);
                    f.getDatosUsuario()[2] = String.valueOf(temp[3]);
                    
                    v.getBp().getBotones().clear();
                    v.getBp().add(v.getBp().botones(c.consultarProductos()), BorderLayout.CENTER);
                    v.getBp().registrarEscuchasBotonesFrutas(this);
                    v.getpPrincipal().removeAll();
                    v.getpPrincipal().add(v.getBp());
                    v.getpPrincipal().revalidate();
                    v.getpPrincipal().repaint();

                    if(v.getBp().getCbbMedidas().getItemCount() < 2){
                        for (int i = 0; i < c.consultarMedidasPeso().length; i++) {
                            v.getBp().getModeloCbb().addElement(String.valueOf(c.consultarMedidasPeso()[i][1]));
                        }
                    }

                }else if(Integer.parseInt(String.valueOf(temp[3])) == 2){
                    f.getDatosUsuario()[0] = v.getLogin().getTxtUsuario().getText();
                    f.getDatosUsuario()[1] = String.valueOf(temp[1]);
                    f.getDatosUsuario()[2] = String.valueOf(temp[3]);
                    
                    v.getpPrincipal().removeAll();
                    v.getpPrincipal().add(v.getMa());
                    v.getpPrincipal().revalidate();
                    v.getpPrincipal().repaint();
                }
            }
        }else{
            JOptionPane.showConfirmDialog(null, "El Campo Usuario y/o Contraseña Está Vacío", "Inicio de Sesión", JOptionPane.DEFAULT_OPTION);
        }
    }
    
    void agregarProductoALista(){
        try{
            int posicion = -1;
            if(Integer.parseInt(v.getBp().getTxtPeso().getText()) >= 0){
                System.out.println(Integer.parseInt(v.getBp().getTxtPeso().getText()));
                for (int i = 0; i < f.getListaProductos().size(); i++){
                    if (v.getBp().getLblNombreP().getText().equals(String.valueOf(f.getListaProductos().get(i)[0]))) {
                        posicion = i;
                    }else{}
                }
                if(posicion >= 0){
                    System.out.println(f.getListaProductos().get(posicion)[3]);
                    double nuevoPeso = 0.0;
                    switch(String.valueOf(f.getListaProductos().get(posicion)[3])){
                        case "g": 
                            switch(v.getBp().getCbbMedidas().getSelectedIndex()){
                                case 0: nuevoPeso = Double.parseDouble(v.getBp().getTxtPeso().getText());
                                        f.getListaProductos().get(posicion)[1] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[1])) + nuevoPeso;
                                        f.getListaProductos().get(posicion)[2] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[2])) + Double.parseDouble(v.getBp().getTxtPrecioProductoC().getText());
                                        limpiarBuscarP(String.valueOf(f.getListaProductos().get(posicion)[0]));
                                    break;
                                case 1: nuevoPeso = Double.parseDouble(v.getBp().getTxtPeso().getText()) * 500;
                                        f.getListaProductos().get(posicion)[1] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[1])) + nuevoPeso;
                                        f.getListaProductos().get(posicion)[2] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[2])) + Double.parseDouble(v.getBp().getTxtPrecioProductoC().getText());
                                        limpiarBuscarP(String.valueOf(f.getListaProductos().get(posicion)[0]));
                                    break;
                                case 2: nuevoPeso = Double.parseDouble(v.getBp().getTxtPeso().getText()) * 1000;
                                        f.getListaProductos().get(posicion)[1] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[1])) + nuevoPeso;
                                        f.getListaProductos().get(posicion)[2] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[2])) + Double.parseDouble(v.getBp().getTxtPrecioProductoC().getText());
                                        limpiarBuscarP(String.valueOf(f.getListaProductos().get(posicion)[0]));
                                    break;
                                case 3: JOptionPane.showConfirmDialog(null, "El Producto Seleccionado NO Está Disponible en Unidades", "Añadir Producto", JOptionPane.DEFAULT_OPTION);
                                    break;
                            };
                            break;
                        case "Lb": switch(v.getBp().getCbbMedidas().getSelectedIndex()){
                                case 0: nuevoPeso = Double.parseDouble(v.getBp().getTxtPeso().getText()) / 500;
                                        f.getListaProductos().get(posicion)[1] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[1])) + nuevoPeso;
                                        f.getListaProductos().get(posicion)[2] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[2])) + Double.parseDouble(v.getBp().getTxtPrecioProductoC().getText());
                                        limpiarBuscarP(String.valueOf(f.getListaProductos().get(posicion)[0]));
                                    break;
                                case 1: nuevoPeso = Double.parseDouble(v.getBp().getTxtPeso().getText());
                                        f.getListaProductos().get(posicion)[1] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[1])) + nuevoPeso;
                                        f.getListaProductos().get(posicion)[2] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[2])) + Double.parseDouble(v.getBp().getTxtPrecioProductoC().getText());
                                        limpiarBuscarP(String.valueOf(f.getListaProductos().get(posicion)[0]));
                                    break;
                                case 2: nuevoPeso = Double.parseDouble(v.getBp().getTxtPeso().getText()) * 2;
                                        f.getListaProductos().get(posicion)[1] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[1])) + nuevoPeso;
                                        f.getListaProductos().get(posicion)[2] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[2])) + Double.parseDouble(v.getBp().getTxtPrecioProductoC().getText());
                                        limpiarBuscarP(String.valueOf(f.getListaProductos().get(posicion)[0]));
                                    break;
                                case 3: JOptionPane.showConfirmDialog(null, "El Producto Seleccionado NO Está Disponible en Unidades", "Añadir Producto", JOptionPane.DEFAULT_OPTION);
                                    break;
                            };
                            break;
                        case "Kg": switch(v.getBp().getCbbMedidas().getSelectedIndex()){
                                case 0: nuevoPeso = Double.parseDouble(v.getBp().getTxtPeso().getText()) / 1000;
                                        f.getListaProductos().get(posicion)[1] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[1])) + nuevoPeso;
                                        f.getListaProductos().get(posicion)[2] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[2])) + Double.parseDouble(v.getBp().getTxtPrecioProductoC().getText());
                                        limpiarBuscarP(String.valueOf(f.getListaProductos().get(posicion)[0]));
                                    break;
                                case 1: nuevoPeso = Double.parseDouble(v.getBp().getTxtPeso().getText()) / 2;
                                        f.getListaProductos().get(posicion)[1] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[1])) + nuevoPeso;
                                        f.getListaProductos().get(posicion)[2] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[2])) + Double.parseDouble(v.getBp().getTxtPrecioProductoC().getText());
                                        limpiarBuscarP(String.valueOf(f.getListaProductos().get(posicion)[0]));
                                    break;
                                case 2: nuevoPeso = Double.parseDouble(v.getBp().getTxtPeso().getText());
                                        f.getListaProductos().get(posicion)[1] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[1])) + nuevoPeso;
                                        f.getListaProductos().get(posicion)[2] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[2])) + Double.parseDouble(v.getBp().getTxtPrecioProductoC().getText());
                                        limpiarBuscarP(String.valueOf(f.getListaProductos().get(posicion)[0]));
                                    break;
                                case 3: JOptionPane.showConfirmDialog(null, "El Producto Seleccionado NO Está Disponible en Unidades", "Añadir Producto", JOptionPane.DEFAULT_OPTION);
                                    break;
                            };
                            break;
                        case "Und": switch(v.getBp().getCbbMedidas().getSelectedIndex()){
                                case 3: nuevoPeso = Double.parseDouble(v.getBp().getTxtPeso().getText());
                                        f.getListaProductos().get(posicion)[1] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[1])) + nuevoPeso;
                                        f.getListaProductos().get(posicion)[2] = Double.parseDouble(String.valueOf(f.getListaProductos().get(posicion)[2])) + Double.parseDouble(v.getBp().getTxtPrecioProductoC().getText());
                                        limpiarBuscarP(String.valueOf(f.getListaProductos().get(posicion)[0]));
                                    break;
                                default : JOptionPane.showConfirmDialog(null, "El Producto Seleccionado Solo Está Disponible en Unidades", "Añadir Producto", JOptionPane.DEFAULT_OPTION);
                                          v.getBp().getCbbMedidas().setSelectedIndex(3);
                                };
                            break;
                    }
                    
                }else{
                    if(!"Unidad".equals(v.getBp().getLblMedidaPeso().getText())){
                         if (!"Unidad".equals(v.getBp().getCbbMedidas().getSelectedItem())) {
                            String medida = "";
                            switch(v.getBp().getCbbMedidas().getSelectedIndex()){
                                case 0: medida = "g";
                                    break;
                                case 1: medida = "Lb";
                                    break;
                                case 2: medida = "Kg";
                                    break;
                                case 3: medida = "Und";
                                    break;
                            }

                            Object o[] = new Object[4];
                            o[0] = v.getBp().getLblNombreP().getText();
                            o[1] = v.getBp().getTxtPeso().getText();
                            o[2] = v.getBp().getTxtPrecioProductoC().getText();
                            o[3] = medida;
                            f.getListaProductos().add(o);
                            limpiarBuscarP(v.getBp().getLblNombreP().getText());
                        }else{
                             JOptionPane.showConfirmDialog(null, "Este Producto NO Está Disponible en Unidad", "Añadir Producto", JOptionPane.DEFAULT_OPTION);
                        }
                    }else{
                        String medida = "";
                        switch(v.getBp().getCbbMedidas().getSelectedIndex()){
                            case 0: medida = "g";
                                break;
                            case 1: medida = "Lb";
                                break;
                            case 2: medida = "Kg";
                                break;
                            case 3: medida = "Und";
                                break;
                        }

                        Object o[] = new Object[4];
                        o[0] = v.getBp().getLblNombreP().getText();
                        o[1] = v.getBp().getTxtPeso().getText();
                        o[2] = v.getBp().getTxtPrecioProductoC().getText();
                        o[3] = medida;
                        f.getListaProductos().add(o);
                        limpiarBuscarP(v.getBp().getLblNombreP().getText());
                    }
                    
                }
            }else{
                JOptionPane.showConfirmDialog(null, "Se Debe Ingresar una Cantidad Valida de Peso o Número de Unidades del Producto", "Añadir Producto", JOptionPane.DEFAULT_OPTION);
            }
        }catch(NumberFormatException | NullPointerException e){
            JOptionPane.showConfirmDialog(null, "Todos los Campos Deben Encontrarse Diligenciados", "Añadir Producto", JOptionPane.DEFAULT_OPTION);
        }
        //calcular valor total de la venta en buscar producto
        v.getBp().getTxtTotal().setText(String.valueOf(f.calcularTotal(f.getListaProductos())));
    }
    
    void imprimir(){
        if(f.getListaProductos().isEmpty()){
            JOptionPane.showConfirmDialog(null, "No Hay Productos a Registrar", "Generar Factura", JOptionPane.DEFAULT_OPTION);
        }else{
            if(v.getRv().getTxtRecibido().getText().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Falta Ingresar El Dinero Recibido", "Generar Factura", JOptionPane.DEFAULT_OPTION);
            }else{
                if(f.valorRetornar(v.getRv().getTxtTotal().getText(),v.getRv().getTxtRecibido().getText()) < 0){
                    JOptionPane.showConfirmDialog(null, "Dinero Insuficiente Para Efectuar la Compra", "Generar Factura", JOptionPane.DEFAULT_OPTION);
                }else{
                    int i = c.registroFactura(Integer.parseInt(f.getDatosUsuario()[0]));
                    int codigos[] = new int[f.getListaProductos().size()];
                    for (int j = 0; j < f.getListaProductos().size(); j++) {
                        codigos[j] = c.obtenerCodigoProductos(String.valueOf(f.getListaProductos().get(j)[0]));
                    }
                    for (int j = 0; j < codigos.length; j++) {
                        switch(String.valueOf(f.getListaProductos().get(j)[3])){
                            case "g": c.registrarVenta(codigos[j], i, Double.parseDouble(String.valueOf(f.getListaProductos().get(j)[1])), Double.parseDouble(String.valueOf(f.getListaProductos().get(j)[2])),1);
                                break;
                            case "Lb": c.registrarVenta(codigos[j], i, Double.parseDouble(String.valueOf(f.getListaProductos().get(j)[1])), Double.parseDouble(String.valueOf(f.getListaProductos().get(j)[2])),2);
                                break;
                            case "Kg": c.registrarVenta(codigos[j], i, Double.parseDouble(String.valueOf(f.getListaProductos().get(j)[1])), Double.parseDouble(String.valueOf(f.getListaProductos().get(j)[2])),3);
                                break;
                            case "Und": c.registrarVenta(codigos[j], i, Double.parseDouble(String.valueOf(f.getListaProductos().get(j)[1])), Double.parseDouble(String.valueOf(f.getListaProductos().get(j)[2])),4);
                                break;
                        }
                    }
                      //Imprimir
                    prepararFactura(f.getDatosUsuario()[1], String.valueOf(i), v.getRv().getLblFecha().getText(), v.getRv().getTxtTotal().getText(),v.getRv().getTxtRecibido().getText(),v.getRv().getTxtDevolucion().getText());
                    try {
                        v.getRv().getImpresion().print();
                    } catch (PrinterException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Limpiar todos los valores
                    v.getRv().getImpresion().setText("");
                    f.getListaProductos().clear();
                    v.getRv().getTxtDevolucion().setText("");
                    v.getRv().getTxtTotal().setText("");
                    v.getRv().getTxtRecibido().setText("");
                    v.getRv().getTxtDevolucion().setBackground(Color.white);

                    v.getRv().remove(2);
                    v.getRv().getBotones().clear();
                    v.getRv().add(v.getRv().listaProductos(f.getListaProductos()), BorderLayout.CENTER);
                    v.getpPrincipal().revalidate();
                    v.getpPrincipal().repaint();
                }
            }
        }
    }
    
    ArrayList<Object[]> buscarPalabra(String palabra){
        ArrayList<Object[]> arrayTemp = new ArrayList<>(c.consultarProductos());
        if ("".equals(palabra)) {
            return arrayTemp;
        }else{
            ArrayList<Object[]> arrayFinal = new ArrayList<>();
            ArrayList<String> nombres = new ArrayList<>();

            for (int i = 0; i < arrayTemp.size(); i++) {
                nombres.add(String.valueOf(arrayTemp.get(i)[1]));
            }

            nombres.stream().forEach((elemento) ->{
                String dato = elemento.toLowerCase();
                if(dato.contains(palabra.toLowerCase())){
                    for (int i = 0; i < arrayTemp.size(); i++) {
                        if(elemento.equals(arrayTemp.get(i)[1])){
                            arrayFinal.add(arrayTemp.get(i));
                        }
                    }
                }
            });
            return arrayFinal;
        }
        
    }
    
    void prepararFactura(String cajero, String nFactura, String fecha, String totalVenta,String recibido,String devuelto){
        v.getRv().getImpresion().append("*********************************************\n");
        v.getRv().getImpresion().append("*                DISTFRUVER             *\n");
        v.getRv().getImpresion().append("*********************************************\n");
        v.getRv().getImpresion().append(" NIT: 1087993537-0\n");
        v.getRv().getImpresion().append(" No. Factura: "+nFactura+"\n");
        v.getRv().getImpresion().append(" "+fecha+"\n");
        v.getRv().getImpresion().append("   Empleado: "+cajero+"  \n");
        v.getRv().getImpresion().append("__________________________________________\n");
        v.getRv().getImpresion().append("__________________________________________\n");
        v.getRv().getImpresion().append("---------------------------------------------------------\n");
        v.getRv().getImpresion().append(" | Prod. | Peso/cant. | Precio | \n");
        v.getRv().getImpresion().append("---------------------------------------------------------\n");
        v.getRv().getImpresion().append("\n");
        for (int i = 0; i < f.getListaProductos().size(); i++) {
            v.getRv().getImpresion().append(" "+f.getListaProductos().get(i)[0]+ ": \n  "+f.getListaProductos().get(i)[1]+" "+f.getListaProductos().get(i)[3]+" ------------- $"+f.getListaProductos().get(i)[2]+"\n");
        }
        v.getRv().getImpresion().append("\n");
        v.getRv().getImpresion().append("__________________________________________\n");
        v.getRv().getImpresion().append(" Precio Total:  $"+totalVenta+"\n");
        v.getRv().getImpresion().append(" Valor Recibido:  $"+recibido+"\n");
        v.getRv().getImpresion().append(" Valor Devuelto:  $"+devuelto+"\n");
        v.getRv().getImpresion().append("__________________________________________\n");
        v.getRv().getImpresion().append("******************************************\n");
        v.getRv().getImpresion().append("*   GRACIAS POR SU COMPRA    *\n");
        v.getRv().getImpresion().append("*    LO ESPERAMOS PRONTO      *\n");
        v.getRv().getImpresion().append("*******************************************\n");
    }
    
    void actualizarDevolucionResumenVenta(){
        try{
            v.getRv().getTxtDevolucion().setText(String.valueOf(f.valorRetornar(v.getRv().getTxtTotal().getText(),v.getRv().getTxtRecibido().getText())));
            if(f.valorRetornar(v.getRv().getTxtTotal().getText(),v.getRv().getTxtRecibido().getText()) > 0){
                v.getRv().getTxtDevolucion().setBackground(Color.green);
            }else if(f.valorRetornar(v.getRv().getTxtTotal().getText(),v.getRv().getTxtRecibido().getText()) == 0){
                v.getRv().getTxtDevolucion().setBackground(Color.white);
            }else{
                v.getRv().getTxtDevolucion().setBackground(Color.red);
            }
        }catch(NumberFormatException ex){
            v.getRv().getTxtDevolucion().setText(v.getRv().getTxtRecibido().getText());
        }
    }
 
    /*****************************************************************************
    *************************** METODOS DE LIMPIEZA ******************************
    ******************************************************************************/
    
    void limpiarNulosListaProductos(){
        ArrayList <Object[]> arrayTemp = new ArrayList <>();
        int contador = 0;
        for (int i = 0; i < f.getListaProductos().size(); i++) {
            if(f.getListaProductos().get(i)[0] != null){
                contador = contador + 1;
            }
        }
        System.out.println("contador: "+contador);
        Object objectTemp[][] = new Object[contador][4];
        
        int cont = 0;
        for (int i = 0; i < f.getListaProductos().size(); i++) {
            if(f.getListaProductos().get(i)[0] != null){
                objectTemp[cont][0] = f.getListaProductos().get(i)[0];
                objectTemp[cont][1] = f.getListaProductos().get(i)[1];
                objectTemp[cont][2] = f.getListaProductos().get(i)[2];
                objectTemp[cont][3] = f.getListaProductos().get(i)[3];
                cont = cont + 1;
            }else{
                System.out.println(" null:" + f.getListaProductos().get(i)[0]);
            }
        }
        arrayTemp.addAll(Arrays.asList(objectTemp)); //if(f.getListaProductos().get(i)[0] != null || "0".equals(f.getListaProductos().get(i)[0])){
        //} else {}
        f.setListaProductos(arrayTemp);
    }
    
    void limpiarLogin(){    
        v.getLogin().getTxtUsuario().setText("");
        v.getLogin().getTxtContrasena().setText("");
    }
    
    void limpiarBuscarP(String producto){
        
        v.getBp().getTxtPeso().setText("");
        v.getBp().getTxtPrecioProducto().setText("");
        v.getBp().getTxtPrecioProductoC().setText("");
        v.getBp().getTxtConsultarP().setText("");
        v.getBp().getCbbMedidas().setSelectedIndex(0);
        v.getBp().getLblImagenProducto().setIcon(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/default.png"),150,150));
        v.getBp().getLblConfirmacion().setText("¡¡"+producto +" Agregada(o) al Carrito!!");
    }
    
    void limpiarCRUDProducto(){
        v.getCp().getTxtNombre().setText("");
        v.getCp().getTxtPrecio().setText("");
        v.getCp().getCbbMedidas().setSelectedIndex(0);
        v.getCp().getLblImagen().setIcon(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/default.png"), 500,500));
        file = null;
    }
    
    void limpiarResumenVenta(){
        v.getRv().getTxtDevolucion().setText("");
        v.getRv().getTxtRecibido().setText("");
        v.getRv().getTxtTotal().setText("");
    }
    
    void limpiarConsultarVentas(){
        v.getCv().getTxtTotal();
    }
    
    
    void reset(){
        limpiarLogin();
        v.getBp().getTxtPeso().setText("");
        v.getBp().getTxtPrecioProducto().setText("");
        v.getBp().getTxtPrecioProductoC().setText("");
        v.getBp().getTxtConsultarP().setText("");
        v.getBp().getLblImagenProducto().setIcon(cambiarTamañoIcono(new ImageIcon(System.getProperty("user.dir")+"/img/icons/default.png"),150,150));
        v.getBp().getLblConfirmacion().setText("");
        limpiarCRUDProducto();
        limpiarResumenVenta();
        limpiarConsultarVentas();
        limpiarRegistroEmpleado();
        v.getBp().getBotones().clear();
        v.getBp().getTempProducto().clear();
        v.getBp().getTxtTotal().setText("");
        v.getBp().getLblMedidaPeso().setText("Peso/Unidad");
        v.getBp().getLblNombreP().setText("Nombre del Producto");
        f.getDatosUsuario()[0] = null;
        f.getDatosUsuario()[1] = null;
        f.getDatosUsuario()[2] = null;
        f.getListaProductos().clear();
        v.getEep().getBotones().clear();
        v.getRe().setIdentificador(0);
        v.getRv().getBotones().clear();
        v.getLogin().getTxtUsuario().requestFocus();
        v.getBp().registrarEscuchasBotonesFrutas(this);
    }
    
    void limpiarRegistroEmpleado(){
        v.getRe().getTxtCodigo().setText("");
        v.getRe().getTxtContrasena().setText("");
        v.getRe().getTxtUsuario().setText("");
        v.getRe().getTxtnewContrasena().setText("");
    }

    public void registrarEscuchasTeclado(){
        v.getLogin().getTxtContrasena().addKeyListener(this);
        v.getLogin().getTxtUsuario().addKeyListener(this);
        
        v.getRe().getTxtCodigo().addKeyListener(this);
        
        v.getBp().getTxtPeso().addKeyListener(this);
        
        v.getRv().getTxtRecibido().addKeyListener(this);
        
        v.getBp().getTxtConsultarP().addKeyListener(this);
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        if (v.getBp().getTxtPeso().isFocusOwner()) {
            char caracter = ke.getKeyChar();

            // Verificar si la tecla pulsada no es un digito
            if(((caracter != '.') &&
               (caracter < '0') ||
               (caracter > '9')) &&
               (caracter != '\b' /*corresponde a BACK_SPACE*/))
            {
               ke.consume();  // ignorar el evento de teclado
            }
        }
        
        if (v.getRv().getTxtRecibido().isFocusOwner() || v.getLogin().getTxtUsuario().isFocusOwner() || v.getRe().getTxtCodigo().isFocusOwner()) {
            char caracter = ke.getKeyChar();

            // Verificar si la tecla pulsada no es un digito
            if(((caracter < '0') ||
               (caracter > '9')) &&
               (caracter != '\b' /*corresponde a BACK_SPACE*/))
            {
               ke.consume();  // ignorar el evento de teclado
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (v.getBp().getTxtPeso().isFocusOwner()) {
            try{
                v.getBp().getTxtPrecioProductoC().setText(String.valueOf(f.calcularPrecio(Double.parseDouble(v.getBp().getTxtPeso().getText()), Double.parseDouble(String.valueOf(v.getBp().getTempProducto().get(3))), String.valueOf(v.getBp().getTempProducto().get(2)),String.valueOf(v.getBp().getCbbMedidas().getSelectedItem()))));
            }catch(NumberFormatException ex){
                v.getBp().getTxtPrecioProductoC().setText("0");
            }catch(IndexOutOfBoundsException ex){
                JOptionPane.showConfirmDialog(null, "Debe Seleccionar Primero el Producto", "Añadir Producto", JOptionPane.DEFAULT_OPTION);
                v.getBp().getTxtPeso().setText("");
            }
        }
        
        if(v.getBp().getTxtConsultarP().isFocusOwner()){
            try{
                v.getBp().remove(3);
                v.getBp().getBotones().clear();
                v.getBp().add(v.getBp().botones(buscarPalabra(v.getBp().getTxtConsultarP().getText())), BorderLayout.CENTER);
                v.getBp().registrarEscuchasBotonesFrutas(this);
                v.getpPrincipal().revalidate();
                v.getpPrincipal().repaint();
            }catch(ArrayIndexOutOfBoundsException ex){
                v.getBp().getBotones().clear();
                v.getBp().add(v.getBp().botones(buscarPalabra(v.getBp().getTxtConsultarP().getText())), BorderLayout.CENTER);
                v.getBp().registrarEscuchasBotonesFrutas(this);
                v.getpPrincipal().revalidate();
                v.getpPrincipal().repaint();
            }
            
        }
        
        if (v.getRv().getTxtRecibido().isFocusOwner()) {
            actualizarDevolucionResumenVenta();
        }
        
        if(ke.getKeyCode()== KeyEvent.VK_ENTER){
            if (v.getBp().getTxtPeso().isFocusOwner()) {
                agregarProductoALista();
            }
            if(v.getBp().getCbbMedidas().isFocusOwner()){
                agregarProductoALista();
            }
            if(v.getLogin().getTxtUsuario().isFocusOwner() || v.getLogin().getTxtContrasena().isFocusOwner()){
                validarInicioSesion();
            }
            //resumen venta
            if(v.getRv().getTxtRecibido().isFocusOwner()){
                imprimir();
            }
        }
    }
    
}