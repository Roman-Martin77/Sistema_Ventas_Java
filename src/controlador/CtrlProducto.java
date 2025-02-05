
package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Categoria;
import modelo.Producto;


/**
 *
 * @author Toshiba
 */
public class CtrlProducto {
    
    //Metodo para guardar un nuevo producto
    
    public boolean guardar(Producto objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
           PreparedStatement consulta = cn.prepareStatement("INSERT INTO producto (idProducto, nombre, cantidad, precio, descripcion, porcentajeIva, idCategoria, estado) VALUES (?,?,?,?,?,?,?,?)");

            consulta.setInt(1, 0);//id
            consulta.setString(2, objeto.getNombre());
            consulta.setInt(3, objeto.getCantidad());
            consulta.setDouble(4, objeto.getPrecio());
            consulta.setString(5, objeto.getDescripcion());
            consulta.setInt(6, objeto.getPorcentajeIva());
            consulta.setInt(7,objeto.getIdCategoria());
            consulta.setInt(8, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar producto" + e);
        }
        return respuesta;
    }
    
    //Metodo para consultar si el producto esta registrado en la Base de Datos
    
     public boolean existeProducto(String producto) {
        boolean respuesta = false;
        String sql = "SELECT nombre FROM producto WHERE nombre = '" + producto + "'";
        Statement st;

        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al Consultar Producto" + e);
        }
        return respuesta;
    }
     
     
    //Metodo para actualizar Producto
   /* public boolean actualizar(Producto objeto, int idProducto) {
        boolean respuesta = false;
        Connection cn = conexion.Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("UPDATE producto SET nombre=?, cantidad=?, precio=?, descripcion=?, porcentajeIva=?, IdCategoria=?, estado=? WHERE idProducto ='"+ idProducto +"'");
            
            consulta.setString(1, objeto.getNombre());
            consulta.setInt(2,objeto.getCantidad());
            consulta.setDouble(3,objeto.getPrecio());
            consulta.setString(4,objeto.getDescripcion());
            consulta.setInt(5,objeto.getPorcentajeIva());
            consulta.setInt(6,objeto.getIdCategoria());
            consulta.setInt(7,objeto.getEstado());
            consulta.setInt(8, objeto.getIdProducto());
            
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto:" + e);
        }
        return respuesta;
    }*/
     
     public boolean actualizar(Producto objeto, int idProducto) {
    boolean respuesta = false;
    Connection cn = conexion.Conexion.conectar();
    try {
        // La consulta ahora tiene 8 placeholders
        String sql = "UPDATE producto SET nombre = ?, cantidad = ?, precio = ?, descripcion = ?, porcentajeIva = ?, IdCategoria = ?, estado = ? WHERE idProducto = ?";
        PreparedStatement consulta = cn.prepareStatement(sql);

        // Asigna los valores a los 8 placeholders en orden
        consulta.setString(1, objeto.getNombre());        // Primer placeholder
        consulta.setInt(2, objeto.getCantidad());         // Segundo placeholder
        consulta.setDouble(3, objeto.getPrecio());        // Tercer placeholder
        consulta.setString(4, objeto.getDescripcion());   // Cuarto placeholder
        consulta.setInt(5, objeto.getPorcentajeIva());    // Quinto placeholder
        consulta.setInt(6, objeto.getIdCategoria());      // Sexto placeholder
        consulta.setInt(7, objeto.getEstado());           // Séptimo placeholder
        consulta.setInt(8, idProducto);                   // Octavo placeholder (para el WHERE)

        // Ejecuta la consulta
        if (consulta.executeUpdate() > 0) {
            respuesta = true; // Actualización exitosa
        }
        cn.close();

    } catch (SQLException e) {
        System.out.println("Error al actualizar producto: " + e);
    }
    return respuesta;
}

    
    //Metodo para eliminar Producto
    public boolean eliminar( int idProducto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("DELETE FROM producto WHERE idProducto = '"+ idProducto +"'");
            consulta.executeUpdate();
            
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al eliminar Producto:" + e);
        }
        return respuesta;
    }
    
    // Metodo para actualizar stock del producto
    
     public boolean actualizarStock(Producto object, int idProducto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("UPDATE producto SET cantidad = ? WHERE idProducto = '"+ idProducto +"'");
            consulta.setInt(1, object.getCantidad());
            
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al actualizar stock del Producto:" + e);
        }
        return respuesta;
    }
}
