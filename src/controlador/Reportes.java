package controlador;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.log.Level;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexion.Conexion;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Toshiba
 */
public class Reportes {

    /*
    Metodo para crear reportes de clientes en el sistema
     */
    public void ReportesClientes() {
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reportes_Clientes.pdf"));
            Image header = Image.getInstance("src/img/header1.jpg");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            //Formato al texto
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte creado por \n Martin Román © Programador\n\n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reportes de Clientes\n\n");

            documento.open();

            // Agregamos los datos
            documento.add(header);
            documento.add(parrafo);

            PdfPTable tabla = new PdfPTable(5);
            tabla.addCell("Código");
            tabla.addCell("Nombres");
            tabla.addCell("Dni");
            tabla.addCell("Teléfono");
            tabla.addCell("Dirección");

            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                        "SELECT idCliente, concat(nombre, ' ' , apellido) AS nombres, dni, telefono, direccion FROM cliente");
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                    } while (rs.next());
                    documento.add(tabla);
                }

            } catch (SQLException e) {
                System.out.println("Error 4 en: " + e);
            }
            documento.close();
            
            JOptionPane.showMessageDialog(null, "Reporte creado");

        } catch (DocumentException e) {
            System.out.println("Error 1 en: " + e);
            
         } catch (FileNotFoundException ex ) {
             System.out.println("Error 2 en: " + ex);        
            
        } catch (IOException ex) {
            System.out.println("Error 3 en: " + ex);
           
        }
         
}

    
    /*
    Metodo para crear reportes de productos en el sistema
     */
    public void ReportesProductos() {
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reportes_Productos.pdf"));
            Image header = Image.getInstance("src/img/header1.jpg");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            //Formato al texto
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte creado por \n Martin Román © Programador\n\n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reporte de Productos\n\n");

            documento.open();

            // Agregamos los datos
            documento.add(header);
            documento.add(parrafo);
            
            float [] columnWidths = {5, 8, 6, 7, 7, 7, 7};

            PdfPTable tabla = new PdfPTable(columnWidths );
            tabla.addCell("Código");
            tabla.addCell("Nombre");
            tabla.addCell("Cantidad");
            tabla.addCell("Precio");
            tabla.addCell("Descripción");
            tabla.addCell("Porc.Iva");
            tabla.addCell("Categoría");

            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement("SELECT p.idProducto, \n" +
"       p.nombre, \n" +
"       p.cantidad, \n" +
"       p.precio, \n" +
"       p.descripcion, \n" +
"       p.porcentajeIva, \n" +
"       c.descripcion AS categoria, \n" +
"       p.estado\n" +
"FROM producto AS p\n" +
"JOIN categoria AS c ON p.idCategoria = c.idCategoria");
                        /*"SELECT p.idProducto, p.nombre, p.cantidad, p.precio, p.descripcion,"
                                + " p.porcentajeIva, c.descripcion AS categoria,  p.estado "
                                + " FROM producto AS p, categoria AS c"
                                + "  WHERE p.idCategoria = c.idCategoria");*/
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                        tabla.addCell(rs.getString(6));
                        tabla.addCell(rs.getString(7));
                    } while (rs.next());
                    documento.add(tabla);
                }

            } catch (SQLException e) {
                System.out.println("Error 4 en: " + e);
            }
            documento.close();
            
            JOptionPane.showMessageDialog(null, "Reporte creado");

        } catch (DocumentException e) {
            System.out.println("Error 1 en: " + e);
            
         } catch (FileNotFoundException ex ) {
             System.out.println("Error 2 en: " + ex);        
            
        } catch (IOException ex) {
            System.out.println("Error 3 en: " + ex);
           
        }
         
}
    
     /*
    Metodo para crear reportes de categorías en el sistema
     */
    public void ReportesCategorias() {
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reportes_Categorias.pdf"));
            Image header = Image.getInstance("src/img/header1.jpg");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            //Formato al texto
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte creado por \n Martin Román © Programador\n\n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reportes de Categorías\n\n");

            documento.open();

            // Agregamos los datos
            documento.add(header);
            documento.add(parrafo);

            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Código");
            tabla.addCell("Descripcion");
            tabla.addCell("Estado");
            
            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                        "SELECT * FROM categoria");
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                       
                    } while (rs.next());
                    documento.add(tabla);
                }

            } catch (SQLException e) {
                System.out.println("Error 4 en: " + e);
            }
            documento.close();
            
            JOptionPane.showMessageDialog(null, "Reporte creado");

        } catch (DocumentException e) {
            System.out.println("Error 1 en: " + e);
            
         } catch (FileNotFoundException ex ) {
             System.out.println("Error 2 en: " + ex);        
            
        } catch (IOException ex) {
            System.out.println("Error 3 en: " + ex);
           
        }
         
}
    
    /*
    Metodo para crear reportes de ventas en el sistema
     */
    public void ReportesVentas() {
        Document documento = new Document();
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Reportes_Ventas.pdf"));
            Image header = Image.getInstance("src/img/header1.jpg");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            //Formato al texto
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte creado por \n Martin Román © Programador\n\n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reporte de Ventas\n\n");

            documento.open();

            // Agregamos los datos
            documento.add(header);
            documento.add(parrafo);
            
            float [] columnWidths = {3, 9, 5, 5, 3};

            PdfPTable tabla = new PdfPTable(columnWidths );
            tabla.addCell("Código");
            tabla.addCell("Cliente");
            tabla.addCell("Tot.Pagar");
            tabla.addCell("Fecha Venta");
            tabla.addCell("Estado");
           

            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement("SELECT \n" +
"    cv.idCabeceraVenta AS id,\n" +
"    CONCAT(c.nombre, ' ', c.apellido) AS cliente,\n" +
"    cv.valorPagar AS total,\n" +
"    cv.fechaVenta AS fecha,\n" +
"    cv.estado\n" +
"FROM \n" +
"    cabecera_venta AS cv\n" +
"JOIN \n" +
"    cliente AS c ON cv.idCliente = c.idCliente");
                        /*"SELECT p.idProducto, p.nombre, p.cantidad, p.precio, p.descripcion,"
                                + " p.porcentajeIva, c.descripcion AS categoria,  p.estado "
                                + " FROM producto AS p, categoria AS c"
                                + "  WHERE p.idCategoria = c.idCategoria");*/
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                       
                    } while (rs.next());
                    documento.add(tabla);
                }

            } catch (SQLException e) {
                System.out.println("Error 4 en: " + e);
            }
            documento.close();
            
            JOptionPane.showMessageDialog(null, "Reporte creado");

        } catch (DocumentException e) {
            System.out.println("Error 1 en: " + e);
            
         } catch (FileNotFoundException ex ) {
             System.out.println("Error 2 en: " + ex);        
            
        } catch (IOException ex) {
            System.out.println("Error 3 en: " + ex);
           
        }
         
}
    

}



