package controlador;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexion.Conexion;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import vista.InterFacturacion;

/**
 *
 * @author Toshiba
 */
public class VentaPDF {

    private String nombreCliente;
    private String dniCliente;
    private String telefonoCliente;
    private String direccionCliente;

    private String fechaActual = "";
    private String nombreArchivoPDFVenta = "";

    // Metodo para obtener datos del cliente
    public void DatosCliente(int idCliente) {
        Connection cn = Conexion.conectar();
        String sql = "SELECT * FROM cliente WHERE idCliente = '" + idCliente + "'";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                nombreCliente = rs.getString("nombre") + " " + rs.getString("apellido");
                dniCliente = rs.getString("dni");
                telefonoCliente = rs.getString("telefono");
                direccionCliente = rs.getString("direccion");
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener datos del Cliente" + e);
        }
    }
    
    
    // Metodo para generar factura de venta
    
    public void generarFacturaPDF(){
        try {
            // Cargar la fecha actual
            Date date = new Date();
            fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(date);
            //Cambiar el formato de la fecha de / a _
            String fechaNueva = "";
            for (int i = 0; i < fechaActual.length(); i++) {
                if(fechaActual.charAt(i)=='/'){
                    fechaNueva = fechaActual.replace("/", "_");
                }
            }
            
             nombreArchivoPDFVenta = "Venta_"+ nombreCliente + "_"+ fechaNueva + ".pdf";
             
             FileOutputStream archivo;
             File file = new File("src/pdf/" + nombreArchivoPDFVenta);
             archivo = new FileOutputStream (file);
             
             Document doc = new Document();
             PdfWriter.getInstance(doc, archivo);
             doc.open();
             Image img = Image.getInstance("src/img/ventas.png");
             Paragraph fecha = new Paragraph();
             Font negrita = new Font(Font.FontFamily.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLUE);
             fecha.add(Chunk.NEWLINE); // Agregar nueva Linea
             fecha.add("Factura: 001" + "\nFecha: " + fechaActual + "\n\n");
             
             PdfPTable Encabezado = new PdfPTable(4);
             Encabezado.setWidthPercentage(100);
             Encabezado.getDefaultCell().setBorder(0);// Quitar el borde de la tabla
             // Tamaño de las celdas
             float [] ColumnaEncabezado = new float [] {20f, 30f, 70f, 40f};
             Encabezado.setWidths(ColumnaEncabezado);
             Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
             // Agregar Celdas
             Encabezado.addCell(img);
             
             String dni = "1258998";
             String nombre = "Alvaro Corporation";
             String telefono = "02614987564";
             String direccion = "Master 4324";
             String razon  = "El límite está solo en tu imaginación";
             
             Encabezado.addCell("");// Celda vacía
             Encabezado.addCell("DNI: " + dni + "\nNOMBRE: " + nombre + "\nTELEFONO: " + telefono + "\nDIRECCION: " + direccion + "\nRAZON SOCIAL: "+ razon);
             Encabezado.addCell(fecha);
             doc.add(Encabezado);
             
             //Cuerpo
             Paragraph cliente = new Paragraph();
             cliente.add(Chunk.NEWLINE);// Nueva línea
             cliente.add("Datos del cliente: " + "\n\n");
             doc.add(cliente);
             
             //Datos del Cliente
             PdfPTable tablaCliente = new PdfPTable(4);
             tablaCliente.setWidthPercentage(100);
             tablaCliente.getDefaultCell().setBorder(0);// Quitar bordes
             
             // Tamaño de las celdas
              float [] ColumnaCliente = new float [] {25f, 45f, 30f, 40f};
              tablaCliente.setWidths(ColumnaCliente);
              tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
              PdfPCell cliente1 = new PdfPCell(new Phrase("Cédula/Dni: ", negrita));
              PdfPCell cliente2 = new PdfPCell(new Phrase("Nombre: ", negrita));
              PdfPCell cliente3 = new PdfPCell(new Phrase("Teléfono: ", negrita));
              PdfPCell cliente4 = new PdfPCell(new Phrase("Dirección: ", negrita));
              //Quitar bordes
              cliente1.setBorder(0);
              cliente2.setBorder(0);
              cliente3.setBorder(0);
              cliente4.setBorder(0);
              // Agregar celda a la tabla
              tablaCliente.addCell(cliente1);
              tablaCliente.addCell(cliente2);
              tablaCliente.addCell(cliente3);
              tablaCliente.addCell(cliente4);
              tablaCliente.addCell(dniCliente);
              tablaCliente.addCell(nombreCliente);
              tablaCliente.addCell(telefonoCliente);
              tablaCliente.addCell(direccionCliente);
              // Agregar al documento
              doc.add(tablaCliente);
              
              // Espacio en blanco
              Paragraph espacio = new Paragraph();
              espacio.add(Chunk.NEWLINE);
              espacio.add("");
              espacio.setAlignment(Element.ALIGN_CENTER);
              doc.add(espacio);
              
              // Agregar los productos
              PdfPTable tablaProducto = new PdfPTable(4);
              tablaProducto.setWidthPercentage(100);
              tablaProducto.getDefaultCell().setBorder(0);
              // Tamaño de celda
               float [] ColumnaProducto = new float [] {15f, 50f, 15f, 20f};
               tablaProducto.setWidths(ColumnaProducto);
               tablaProducto.setHorizontalAlignment(Element.ALIGN_LEFT);
               PdfPCell producto1 = new PdfPCell (new Phrase("Cantidad: ",negrita) );
               PdfPCell producto2 = new PdfPCell (new Phrase("Descripción: ",negrita) );
               PdfPCell producto3 = new PdfPCell (new Phrase("Precio Unitario: ",negrita) );
               PdfPCell producto4 = new PdfPCell (new Phrase("Precio Total: ",negrita) ); 
               // Quitar los bordes
                producto1.setBorder(0);
                producto2.setBorder(0);
                producto3.setBorder(0);
                producto4.setBorder(0);
                //Agregar color al producto del encabezado
                producto1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                producto2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                producto3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                producto4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                // Agregar celda a la tabla
                tablaProducto.addCell(producto1);
                tablaProducto.addCell(producto2);
                tablaProducto.addCell(producto3);
                tablaProducto.addCell(producto4);
                
                for (int i = 0; i < InterFacturacion.jTableProductos.getRowCount(); i++) {
                   String producto = InterFacturacion.jTableProductos.getValueAt(i, 1).toString();
                   String cantidad = InterFacturacion.jTableProductos.getValueAt(i, 2).toString();
                   String precio = InterFacturacion.jTableProductos.getValueAt(i, 3).toString();
                   String total = InterFacturacion.jTableProductos.getValueAt(i, 7).toString();
                   
                   tablaProducto.addCell(cantidad);
                   tablaProducto.addCell(producto);
                   tablaProducto.addCell(precio);
                   tablaProducto.addCell(total);
                
            }
               // Agregar al documento
               doc.add(tablaProducto);
             
               // Total a pagar
               Paragraph info = new Paragraph();
               info.add(Chunk.NEWLINE);
               info.add("Total a Pagar: " + InterFacturacion.txtTotalPagar.getText());
               info.setAlignment(Element.ALIGN_RIGHT);
               doc.add(info);
               
               // Firma
               Paragraph firma = new Paragraph();
               firma.add(Chunk.NEWLINE);
               firma.add("Cancelacion y firma\n\n");
               firma.add("__________________________");
               firma.setAlignment(Element.ALIGN_CENTER);
               doc.add(firma);
               
               // Mensaje
               Paragraph mensaje = new Paragraph();
               mensaje.add(Chunk.NEWLINE);
               mensaje.add("Gracias por su compra");
               mensaje.setAlignment(Element.ALIGN_CENTER);
               doc.add(mensaje);
               
               // Cerrar el documento y el archivo
               doc.close();
               archivo.close();
               
               // Abrir el documento al ser generado automáticamente
               Desktop.getDesktop().open(file);
               
             
            
        } catch (DocumentException | IOException e) {
            System.out.println("Error en: " + e);
        }
    }
    
    

}
