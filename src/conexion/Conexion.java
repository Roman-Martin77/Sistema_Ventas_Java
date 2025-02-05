package conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 *
 * @author Toshiba
 */
public class Conexion {

    //Coneccion Local
    public static Connection conectar() {

        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3307/proyecto_venta", "root", "");
            return cn;

        } catch (SQLException e) {

            System.out.println("Error en conexión local" + e);
        }
        return null;
    }

}
