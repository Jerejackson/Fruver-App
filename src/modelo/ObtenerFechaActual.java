/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Jeremi_Sanchez
 */
public class ObtenerFechaActual {
    Calendar fecha = new GregorianCalendar();
    
    public String getDia(){
        return String.valueOf(fecha.get(Calendar.DAY_OF_MONTH));
    }
    public int getMes(){
        return fecha.get(Calendar.MONTH);
    }
    public int getAnio(){
        return fecha.get(Calendar.YEAR);
    }
    
    public String getFechaActual(){
        return getDia()+" - "+(getMes()+1)+" - "+getAnio();
    }
    public String getFechaActualInverso(){
        return getAnio()+"-"+(getMes()+1)+"-"+getDia();
    }
}
