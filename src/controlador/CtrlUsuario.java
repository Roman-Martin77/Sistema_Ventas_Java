package controlador;

import java.sql.Statement;
import java.sql.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import modelo.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Toshiba
 */
public class CtrlUsuario {

    // Metodo para iniciar secion
    public boolean loginUser(Usuario objeto) {
        
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        String sql = "SELECT usuario, password FROM usuario WHERE usuario = '" + objeto.getUsuario() + "'and password = '" + objeto.getPassword() + "'";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al Iniciar Sesión");
            JOptionPane.showMessageDialog(null, "Error al Iniciar Sesión");
        }
        return respuesta;
    }

    //Metodo para guardar un nuevo usuario
    public boolean guardar(Usuario objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("INSERT INTO usuario ( nombre, apellido, usuario, password, telefono, estado) VALUES (?,?,?,?,?,?)");

            // consulta.setInt(1, 0);//id
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getApellido());
            consulta.setString(3, objeto.getUsuario());
            consulta.setString(4, objeto.getPassword());
            consulta.setString(5, objeto.getTelefono());
            consulta.setInt(6, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar usuario" + e);
        }
        return respuesta;
    }

    //Metodo para consultar si el usuario esta registrado en la Base de Datos
    public boolean existeUsuario(String usuario) {
        boolean respuesta = false;
        String sql = "SELECT usuario FROM usuario WHERE usuario = '" + usuario + "'";
        Statement st;

        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar al usuario" + e);
        }
        return respuesta;
    }

    // Metodo para actualizar un usuario

     public boolean actualizar(Usuario objeto, int idUsuario) {
    boolean respuesta = false;
    Connection cn = conexion.Conexion.conectar();
    try {
        // La consulta ahora tiene 8 placeholders
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, usuario = ?, password = ?, telefono = ?, estado = ? WHERE idUsuario = ?";
        PreparedStatement consulta = cn.prepareStatement(sql);

        // Asigna los valores a los 8 placeholders en orden
        consulta.setString(1, objeto.getNombre());       // Primer placeholder
        consulta.setString(2, objeto.getApellido());    // Segundo placeholder
        consulta.setString(3, objeto.getUsuario());        // Tercer placeholder
        consulta.setString(4, objeto.getPassword());   // Cuarto placeholder
        consulta.setString(5, objeto.getTelefono());  // Quinto placeholder
        consulta.setInt(6, objeto.getEstado());           // Sexto placeholder
        consulta.setInt(7, idUsuario);                    // Séptimo placeholder (idCliente)                  

        // Ejecuta la consulta
        if (consulta.executeUpdate() > 0) {
            respuesta = true; // Actualización exitosa
        }
        cn.close();

    } catch (SQLException e) {
        System.out.println("Error al actualizar usuario: " + e);
    }
    return respuesta;
}

    
    //Metodo para eliminar Usuario
    public boolean eliminar( int idUsuario) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("DELETE FROM usuario WHERE idUsuario = '"+ idUsuario +"'");
            consulta.executeUpdate();
            
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al eliminar Usuario:" + e);
        }
        return respuesta;
    }
    
    
}
