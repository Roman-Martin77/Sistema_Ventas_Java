package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Cliente;
import modelo.Producto;

/**
 *
 * @author Toshiba
 */
public class CtrlCliente {

    //Metodo para guardar un nuevo cliente
        public boolean guardar(Cliente objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("INSERT INTO cliente ( nombre, apellido, dni, telefono, direccion, estado) VALUES (?,?,?,?,?,?)");

           // consulta.setInt(1, 0);//id
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getApellido());
            consulta.setString(3, objeto.getDni());
            consulta.setString(4, objeto.getTelefono());
            consulta.setString(5, objeto.getDireccion());
            consulta.setInt(6, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar cliente" + e);
        }
        return respuesta;
    } 
    
  /*  public boolean guardar(Cliente objeto) {
    boolean respuesta = false;
    Connection cn = Conexion.conectar();
    try {
        // Eliminamos idCliente si es AUTO_INCREMENT
        PreparedStatement consulta = cn.prepareStatement(
            "INSERT INTO cliente (nombre, apellido, dni, telefono, direccion, estado) VALUES (?,?,?,?,?,?)"
        );

        // Seteamos los parámetros
        consulta.setString(1, objeto.getNombre());
        consulta.setString(2, objeto.getApellido());
        consulta.setString(3, objeto.getDni());
        consulta.setString(4, objeto.getTelefono());
        consulta.setString(5, objeto.getDireccion());
        consulta.setInt(6, objeto.getEstado());

        if (consulta.executeUpdate() > 0) {
            respuesta = true;
        }
        cn.close();

    } catch (SQLException e) {
        System.out.println("Error al guardar cliente: " + e);
    }
    return respuesta;
}

    public boolean existeCliente(String dni) {
    boolean respuesta = false;
    String sql = "SELECT dni FROM cliente WHERE dni = ?";
    try (Connection cn = Conexion.conectar();
         PreparedStatement consulta = cn.prepareStatement(sql)) {
        consulta.setString(1, dni);
        ResultSet rs = consulta.executeQuery();
        if (rs.next()) {
            respuesta = true;
        }
    } catch (SQLException e) {
        System.out.println("Error al consultar al cliente: " + e);
    }
    return respuesta;
}*/


    //Metodo para consultar si el cliente esta registrado en la Base de Datos
    public boolean existeCliente(String dni) {
        boolean respuesta = false;
        String sql = "SELECT dni FROM cliente WHERE dni = '" + dni + "'";
        Statement st;

        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar al cliente" + e);
        }
        return respuesta;
    }
    
    // Metodo para actualizar un cliente

     public boolean actualizar(Cliente objeto, int idCliente) {
    boolean respuesta = false;
    Connection cn = conexion.Conexion.conectar();
    try {
        // La consulta ahora tiene 8 placeholders
        String sql = "UPDATE cliente SET nombre = ?, apellido = ?, dni = ?, telefono = ?, direccion = ?, estado = ? WHERE idCliente = ?";
        PreparedStatement consulta = cn.prepareStatement(sql);

        // Asigna los valores a los 8 placeholders en orden
        consulta.setString(1, objeto.getNombre());       // Primer placeholder
        consulta.setString(2, objeto.getApellido());    // Segundo placeholder
        consulta.setString(3, objeto.getDni());        // Tercer placeholder
        consulta.setString(4, objeto.getTelefono());   // Cuarto placeholder
        consulta.setString(5, objeto.getDireccion());  // Quinto placeholder
        consulta.setInt(6, objeto.getEstado());           // Sexto placeholder
        consulta.setInt(7, idCliente);                    // Séptimo placeholder (idCliente)                  

        // Ejecuta la consulta
        if (consulta.executeUpdate() > 0) {
            respuesta = true; // Actualización exitosa
        }
        cn.close();

    } catch (SQLException e) {
        System.out.println("Error al actualizar cliente: " + e);
    }
    return respuesta;
}

    
    //Metodo para eliminar Cliente
    public boolean eliminar( int idCliente) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("DELETE FROM cliente WHERE idCliente = '"+ idCliente +"'");
            consulta.executeUpdate();
            
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al eliminar Cliente:" + e);
        }
        return respuesta;
    }
    
    
}

    

