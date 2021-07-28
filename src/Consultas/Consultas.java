/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consultas;

import Conexion.Conexion;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeremi_Sanchez
 */
public class Consultas {

    /**********************************************************************************
     ************************************ LOGIN ***************************************
     * @param id
     * @param pass
     * @return 
    **********************************************************************************/
    
    public Object[] validarSesion(int id, String pass){
        ResultSet rs = Conexion.ejecutarConsulta("SELECT * FROM `empleado` WHERE id = "+ id +" and contrasena = '"+pass+"'");
        Object n[] =new Object[5];
        try {
                
                while(rs.next()){
                  //  System.out.println(rs.getInt(1) +" " + rs.getString(2) + " " + rs.getDate(3) + " " + rs.getInt(4) + " " + rs.getString(5));
                    n[0] = rs.getObject(1); n[1] = rs.getObject(2); n[2] = rs.getObject(3); n[3] = rs.getObject(4); n[4] = rs.getObject(5);
                }
            } catch (SQLException ex) {
                System.out.println("Error de consulta Login: "+ ex);
            }
            return n;
    }
    
    /**********************************************************************************
     ************************** BUSCAR PRODUCTO ***************************************
     * @param nombre
     * @return 
    **********************************************************************************/
    
    public ArrayList<Object[]> consultarProducto(String nombre){
        return guardarListaArreglo(Conexion.ejecutarConsulta("SELECT producto.codigo, producto.nombre, unidadespeso.medida, producto.precio FROM producto INNER JOIN unidadespeso ON producto.unidadMedida = unidadespeso.codigo WHERE producto.nombre LIKE '"+nombre+"'"));
    }

    
    /**********************************************************************************
     ******************** LLENAR LISTA MEDIDAS(CRUD PRODUCTO) *************************
     * @return 
    **********************************************************************************/
    
    public Object[][] consultarMedidasPeso(){
        return guardarArreglo(Conexion.ejecutarConsulta("SELECT * FROM unidadespeso"));
    }
    
    /**********************************************************************************
     ******************** CRUD PRODUCTO(AGREGAR PRODUCTO) *****************************
     * @param nombre
     * @return 
    **********************************************************************************/
    
    public String existenciaProducto(String nombre){
        String i = "";
        ResultSet rs = Conexion.ejecutarConsulta("Select nombre from producto where nombre like '"+nombre+"'");
        try {
            while(rs.next()){
                i = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    public void guardarProducto(String nombre, String medida, int precio){
        Conexion.ejecutarActualizacion("CALL registrarProducto('"+ nombre +"','"+medida+"',"+precio+")");
    }
    
    /**********************************************************************************
     ************************* EDITAR O ELIMINAR PRODUCTO *****************************
     * @return 
    **********************************************************************************/
    
    public ArrayList<Object[]> consultarProductos(){
        return guardarListaArreglo(Conexion.ejecutarConsulta("SELECT * FROM producto"));
    }
    
    public Object borrarProducto(String nombre){
        Object v= new Object();
        ResultSet rs = Conexion.ejecutarConsulta("call borrarProducto('"+nombre+"')");
        try {
            while(rs.next()){
               v = rs.getObject(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    
    public int[] consultarUnProducto(String nombre){
        int r[] = new int[2];
        ResultSet rs = Conexion.ejecutarConsulta("select unidadMedida, precio from producto where nombre like '"+nombre+"'");
        try {
            while(rs.next()){
                r[0] = rs.getInt(1);
                r[1] = rs.getInt(2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
    public void editarProducto(String nombre, String medida, int precio){
        Conexion.ejecutarActualizacion("CALL editarProducto('"+ nombre +"','"+medida+"',"+precio+")");
    }
    
    /**********************************************************************************
     ********************************* CRUD EMPLEADO **********************************
     * @param id
     * @param nombre
     * @param pass
     * @return 
    **********************************************************************************/
    
    public int guardarEmpleado(int id, String nombre, String pass){
        int i = 0;
        ResultSet rs = Conexion.ejecutarConsulta("select registroEmpleado("+id+",'"+nombre+"','"+pass+"')");
        try{
            while(rs.next()){
                i = rs.getInt(1);
            }
        }catch(SQLException e){
            
        }
        return i;
    }
    
    public ArrayList<Object[]> buscarEmpleado(int id){
        return  guardarListaArreglo(Conexion.ejecutarConsulta("CALL buscarEmpleado("+id+")"));
    }
    
    public void editarEmpleado(int idOld,int idNew, String nombre, String contrasena){
        Conexion.ejecutarActualizacion("UPDATE empleado SET id = "+idNew+", nombre = '"+nombre+"', contrasena = '"+contrasena+"' WHERE id = "+idOld);
    }
    
    public int existenciaEmpleado(int id){
        int i = 0;
        ResultSet rs = Conexion.ejecutarConsulta("Select id from empleado where id = "+id);
        try {
            while(rs.next()){
                i = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    /**********************************************************************************
     ************************* EDITAR O ELIMINAR PRODUCTO *****************************
     * @param idEmpleado
     * @return 
    **********************************************************************************/
    
    public int registroFactura(int idEmpleado){
        int i = 0;
        ResultSet rs = Conexion.ejecutarConsulta("Select registrarFactura("+idEmpleado+")");
        try {
            while(rs.next()){
                i = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    public int obtenerCodigoProductos(String nombreProducto){
        int i = 0;
        ResultSet rs = Conexion.ejecutarConsulta("Select codigo from producto where nombre like '"+nombreProducto+"'");
        try {
            while(rs.next()){
                i = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    public void registrarVenta(int idProducto,int idFactura,double peso, double precio, int unidadMedida){
        Conexion.ejecutarActualizacion("INSERT INTO facturaproducto value("+idProducto+","+idFactura+","+peso+","+precio+","+unidadMedida+")");
    }
    
    /**********************************************************************************
     ****************************** CONSULTAR VENTAS **********************************
     * @param fecha
     * @return 
    **********************************************************************************/
    
    public ArrayList<Object[]> consultarVentasDia(String fecha){
        return guardarListaArreglo(Conexion.ejecutarConsulta("Select f.codigo, p.nombre, fp.peso, fp.precio, up.medida from Factura f JOIN facturaproducto fp JOIN producto p JOIN unidadespeso up on f.codigo = fp.idFactura and fp.idProducto = p.codigo and up.codigo = fp.unidadMedidaVenta WHERE fecha like '"+fecha+"' ORDER BY f.codigo ASC"));
    }
    
    /**********************************************************************************
     ***********OBTENER EL RESULTADO DE LA BD Y ALMACENARLO EN UN ARREGLO**************
    **********************************************************************************/
    
    private Object[][] guardarArreglo(ResultSet rs)
    {
        Object obj[][]=null;
        try{
            rs.last();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            int numFils =rs.getRow();
            obj=new Object[numFils][numCols];
            int j = 0;
            rs.beforeFirst();
            while (rs.next()){
                for (int i=0;i<numCols;i++){
                    obj[j][i]=rs.getObject(i+1);
                }
                j++;
            }
        }
        catch(SQLException ex){
            System.out.println("Error al consultar la base de datos: " + ex);
        }
        return obj;
    }
    
    private ArrayList<Object[]> guardarListaArreglo(ResultSet rs)
    {   ArrayList<Object[]> lista = new ArrayList<>();
        Object obj[][]=null;
        try{
            rs.last();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            int numFils =rs.getRow();
            obj=new Object[numFils][numCols];
            int j = 0;
            rs.beforeFirst();
            while (rs.next()){
                for (int i=0;i<numCols;i++){
                    obj[j][i]=rs.getObject(i+1);
                    //System.out.println(rs.getObject(i+1));
                }
                lista.add(obj[j]);
                j++;
            }
        }
        catch(SQLException ex){
            System.out.println("Error al consultar la base de datos: " + ex);
        }
        return lista;
    }
}
