/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

/**
 *
 * @author Jeremi_Sanchez
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion 
{   private static Connection con = null;
    
    public static Connection getConexion(String url, String usuario, String password) {  
        if (con == null){  
            try{   
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(url,usuario,password);
                if(con != null){
                    System.out.println("Conectado a la base de datos FRUVER");
                }
            } catch (ClassNotFoundException | SQLException e){
                System.out.println("Error de conexion: " + e);
            }
        }
        return con;
    }
    
    public static void cerrarConexion(){
        if(con != null){
            con = null;
        }
    }

    public static int ejecutarActualizacion(String sql){  
        Statement st = null;
        try{  
            st = con.createStatement();
            int res = st.executeUpdate(sql);
            return res;
        } catch (SQLException e){  
            e.printStackTrace();
            return 0;
        } finally{  
            try{  
                if (st != null){  
                    st.close();
                }
            } catch (SQLException e){  
                e.printStackTrace();
            } 
        }
    }

      public static ResultSet ejecutarConsulta(String sql){  
        Statement st = null;
        try{
            st = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(sql);
            return res;
        } catch (SQLException e){  
            e.printStackTrace();
            return null;
        } 
    }
}