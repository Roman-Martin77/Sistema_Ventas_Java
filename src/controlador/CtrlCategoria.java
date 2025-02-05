package controlador;

import conexion.Conexion;
import modelo.Categoria;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Toshiba
 */
public class CtrlCategoria {

    //Metodo para registrar Categoria
    public boolean guardar(Categoria objeto) {
        boolean respuesta = false;
        Connection cn = conexion.Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("INSERT INTO categoria VALUES (?,?,?)");
            consulta.setInt(1, 0);
            consulta.setString(2, objeto.getDescripcion());
            consulta.setInt(3, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar Categoría:" + e);
        }
        return respuesta;
    }

    //Metodo para consultar si existe la Categoria
    public boolean existeCategoria(String categoria) {
        boolean respuesta = false;
        String sql = "SELECT * FROM categoria WHERE descripcion = '" + categoria + "'";
        Statement st;

        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al Consultar Categoría:" + e);
        }
        return respuesta;
    }
  
    //Metodo para actualizar Categoria
    public boolean actualizar(Categoria objeto, int idCategoria) {
        boolean respuesta = false;
        Connection cn = conexion.Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("UPDATE categoria SET descripcion = ? WHERE idCategoria = '"+ idCategoria +"'");
            consulta.setString(1, objeto.getDescripcion());
            
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al actualizar Categoría:" + e);
        }
        return respuesta;
    }
    
    
     //Metodo para eliminar Categoria
    public boolean eliminar( int idCategoria) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("DELETE FROM categoria WHERE idCategoria = '"+ idCategoria +"'");
            consulta.executeUpdate();
            
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al eliminar Categoría:" + e);
        }
        return respuesta;
    }
    
    
}
