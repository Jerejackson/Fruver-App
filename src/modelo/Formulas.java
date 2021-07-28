/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Jeremi_Sanchez
 */
public class Formulas {
    ArrayList <Object[]> listaProductos = new ArrayList<>();
    
    String datosUsuario[] = new String[3];
    
    public double calcularPrecio(double pesoCompra, double precioVenta, String unidadMedidaProducto, String unidadMedidaSeleccionada){
        double cantidadUnidad = 0;
        switch(unidadMedidaProducto){
            case "Libra(Lb)": cantidadUnidad = 500.0;
                break;
            case "Kilogramo(Kg)": cantidadUnidad = 1000.0;
                break;
            case "Unidad": cantidadUnidad = 1.0;
                break;
            case "Gramo(g)": cantidadUnidad = 1.0;
                break;
        }
        
        switch(unidadMedidaSeleccionada){
            case "Libra(Lb)": pesoCompra = pesoCompra * 500.0;
                break;
            case "Kilogramo(Kg)": pesoCompra = pesoCompra * 1000.0;
                break;
            case "Unidad": pesoCompra = pesoCompra * 1.0;
                break;
            case "Gramo(g)": pesoCompra = pesoCompra * 1.0;
                break;
        }
        
        return ((pesoCompra*precioVenta)/(cantidadUnidad));
    }
    
    public double valorRetornar(String costo, String valorRecibido){
        return (Double.parseDouble(valorRecibido) - Double.parseDouble(costo));
    }
    
    public double calcularTotal(ArrayList<Object[]> listaProductos){
        double total=0.0;
        
        for (int i = 0; i < listaProductos.size(); i++) {
            total = total + Double.parseDouble(String.valueOf(listaProductos.get(i)[2]));
        }
        return total;
    }
    
    ArrayList <Object[]> listaProductosModificada(ArrayList <Object[]> entrada, Object[] targetEntry){
        Object target[] = targetEntry;
        ArrayList <Object[]> lista = entrada;
        Iterator<Object[]> itr = lista.iterator();
        while(itr.hasNext()){
            Object iterador = itr.next();
            if(iterador.getClass() == target.getClass()){
                itr.remove();
            }
        }
        return lista;
    }

    public ArrayList<Object[]> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Object[]> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public String[] getDatosUsuario() {
        return datosUsuario;
    }
    
}
