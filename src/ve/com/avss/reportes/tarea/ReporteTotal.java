package ve.com.avss.reportes.tarea;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ve.com.avss.reportes.AbstractReporte;
import ve.com.avss.reportes.totales.bean.DatosReporte;
import ve.com.avss.ventas.util.CONSTANTES_INTERACCION;

public class ReporteTotal extends AbstractReporte{
	private Document document;
	protected String directorioSave = "";
	protected DatosReporte datosReporteTotal = new DatosReporte();
	
	
	public ReporteTotal(DatosReporte datosReporteTotal) {
		this.datosReporteTotal = datosReporteTotal;
		
	}
	 
	 public boolean createPdf(){
		 if (!(datosReporteTotal.getDatosProyecto() == null)) {
			 this.directorioSave = getDatosEmpresa().getRutaInstalacion()+"\\proyectos"+CONSTANTES_INTERACCION.PATH_REPORTE_PROYECTOS+"\\"+datosReporteTotal.getDatosProyecto().getDescripcion()+ "\\";
		}
		 
		 if (!(datosReporteTotal.getDatosRecurso() == null)) {
			 this.directorioSave = getDatosEmpresa().getRutaInstalacion()+"\\proyectos"+CONSTANTES_INTERACCION.PATH_REPORTE_RECURSOS+"\\"+datosReporteTotal.getDatosRecurso().getNombre()+"-"+datosReporteTotal.getDatosRecurso().getApellido()+ "\\";
		}
		 
		 
		 Date fecha = new Date();
		 String nombreArchivo = "rpt"+fecha.getTime()+".pdf";
		 
		 File directorio = null;
		 directorio = new File(directorioSave);
	        if (!directorio.exists()) {
	            if (directorio.mkdirs()) {
	            	
	            } else {
	            	JOptionPane.showMessageDialog(null,"Error al crear ruta de reporte","Alerta",JOptionPane.ERROR_MESSAGE);
	            }
	        }
		 try {
			 document = new Document(PageSize.A4.rotate());
             PdfWriter.getInstance(document, new FileOutputStream(directorioSave+nombreArchivo));
             PdfPTable tableEncabezado = null;
             if (!(datosReporteTotal.getDatosProyecto() == null)) {
            	 tableEncabezado = encabezado("REPORTE DE TAREA "+datosReporteTotal.getDatosProyecto().getDescripcion());
    		}
    		 
    		 if (!(datosReporteTotal.getDatosRecurso() == null)) {
    			 tableEncabezado = encabezado("REPORTE DE TAREA "+datosReporteTotal.getDatosRecurso().getNombre()+" "+datosReporteTotal.getDatosRecurso().getApellido());
    		}
			 
			 
			 PdfPCell cellSinBordeFecha = null;
			 PdfPTable tableFechas = new PdfPTable(1);
//			 cellSinBordeFecha = new PdfPCell(new Phrase("DESDE "+datosReporteTotal.getFechaReporte()));
//			 cellSinBordeFecha.setBorder(0);
			 
			  
			 //tableFechas.addCell(cellSinBordeFecha);
			 cellSinBordeFecha = new PdfPCell(new Phrase("HASTA "+datosReporteTotal.getFechaReporte()));
			 cellSinBordeFecha.setBorder(0);
			 tableFechas.addCell(cellSinBordeFecha);
			 document.open();
             document.add(tableEncabezado);
             document.add(tableFechas);
             
//             document.add(new Phrase(Chunk.NEWLINE));
//             document.add(tablaTotalesPorTipoFactura());
//             document.add(new Phrase(Chunk.NEWLINE));
//             document.add(tablaEstadoGananciaPerdida());
             if (!(datosReporteTotal.getDatosProyecto() == null)) {
            	 document.add(new Phrase(Chunk.NEWLINE));
                 document.add(tablaListaTareasPorProyecto());
    		}
    		 
    		 if (!(datosReporteTotal.getDatosRecurso() == null)) {
    			 document.add(new Phrase(Chunk.NEWLINE));
                 document.add(tablaListaTareasPorRecurso());
    		}
             
             document.close();
		 } catch (Exception e) {
	           return false;
	        }
		 try {
		     File path = new File (directorioSave+nombreArchivo);
		     Desktop.getDesktop().open(path);
		}catch (IOException ex) {
		     ex.printStackTrace();
		}
		 return true;
		 }
	 
	 
	 
	 private PdfPTable tablaListaTareasPorRecurso() throws DocumentException {
		 /*agregar lista de detalles de las facturas*/
         PdfPTable tableListaTareas = new PdfPTable(5); 
         /*
          * status
          * fecha
          * proyecto
          * tarea
          * observacion
          * */
         tableListaTareas.setWidths(new int[]{30,30,50,70,80});
         
         PdfPCell cell = null;
         
         cell = new PdfPCell(new Phrase("Status"));
         cell.setHorizontalAlignment (Element.ALIGN_CENTER);
         cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell.setPaddingBottom(5);
         cell.setBorder(0);
         tableListaTareas.addCell(cell);
         
         cell = new PdfPCell(new Phrase("Fecha"));
         cell.setHorizontalAlignment (Element.ALIGN_CENTER);
         cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell.setPaddingBottom(5);
         cell.setBorder(0);
         tableListaTareas.addCell(cell);
         
         cell = new PdfPCell(new Phrase("Proyecto"));
         cell.setHorizontalAlignment (Element.ALIGN_CENTER);
         cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell.setPaddingBottom(5);
         cell.setBorder(0);
         tableListaTareas.addCell(cell);
         
         cell = new PdfPCell(new Phrase("Tarea"));
         cell.setHorizontalAlignment (Element.ALIGN_CENTER);
         cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell.setPaddingBottom(5);
         cell.setBorder(0);
         tableListaTareas.addCell(cell);
         
         cell = new PdfPCell(new Phrase("Observación"));
         cell.setHorizontalAlignment (Element.ALIGN_CENTER);
         cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell.setPaddingBottom(5);
         cell.setBorder(0);
         tableListaTareas.addCell(cell);
         

         PdfPCell cellItemTable = null;
         for (int i = 0; i < datosReporteTotal.getListaTareas().size(); i++) {
        	//status
        	 cellItemTable = new PdfPCell(new Phrase(datosReporteTotal.getListaTareas().get(i).getStatus()));
        	 cellItemTable.setBorder(0);
        	 tableListaTareas.addCell(cellItemTable);
        	 //fecha
        	 cellItemTable = new PdfPCell(new Phrase(datosReporteTotal.getListaTareas().get(i).getFechaDesarrollo()));
        	 cellItemTable.setBorder(0);
        	 tableListaTareas.addCell(cellItemTable);
        	 //proyecto
        	 cellItemTable = new PdfPCell(new Phrase(datosReporteTotal.getListaTareas().get(i).getProyecto()));
        	 cellItemTable.setBorder(0);
        	 tableListaTareas.addCell(cellItemTable);
        	//descripcion
        	 cellItemTable = new PdfPCell(new Phrase(datosReporteTotal.getListaTareas().get(i).getDescripcion()));
        	 cellItemTable.setBorder(0);
        	 tableListaTareas.addCell(cellItemTable);
        	//observacion
        	 cellItemTable = new PdfPCell(new Phrase(datosReporteTotal.getListaTareas().get(i).getObservacion()));
        	 cellItemTable.setBorder(0);
        	 tableListaTareas.addCell(cellItemTable);
        	 			
		}
         
         
         return tableListaTareas;
	 }
	 
	 private PdfPTable tablaListaTareasPorProyecto() throws DocumentException {
		 /*agregar lista de detalles de las facturas*/
		 /*status
		  * fecha
		  * recurso
		  * tarea
		  * observacion
		  * */
         PdfPTable tableListaTareas = new PdfPTable(5); 
         tableListaTareas.setWidths(new int[]{30,30,50,70,80});
       
         
         PdfPCell cell = null;
         
         cell = new PdfPCell(new Phrase("Status"));
         cell.setHorizontalAlignment (Element.ALIGN_CENTER);
         cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell.setPaddingBottom(5);
         cell.setBorder(0);
         tableListaTareas.addCell(cell);
         
         cell = new PdfPCell(new Phrase("Fecha"));
         cell.setHorizontalAlignment (Element.ALIGN_CENTER);
         cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell.setPaddingBottom(5);
         cell.setBorder(0);
         tableListaTareas.addCell(cell);
         
         cell = new PdfPCell(new Phrase("Recurso"));
         cell.setHorizontalAlignment (Element.ALIGN_CENTER);
         cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell.setPaddingBottom(5);
         cell.setBorder(0);
         tableListaTareas.addCell(cell);
         
         cell = new PdfPCell(new Phrase("Tarea"));
         cell.setHorizontalAlignment (Element.ALIGN_CENTER);
         cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell.setPaddingBottom(5);
         cell.setBorder(0);
         tableListaTareas.addCell(cell);
         
         cell = new PdfPCell(new Phrase("Observación"));
         cell.setHorizontalAlignment (Element.ALIGN_CENTER);
         cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell.setPaddingBottom(5);
         cell.setBorder(0);
         tableListaTareas.addCell(cell);
         
         PdfPCell cellItemTable = null;
         for (int i = 0; i < datosReporteTotal.getListaTareas().size(); i++) {
        	 //status
        	 cellItemTable = new PdfPCell(new Phrase(datosReporteTotal.getListaTareas().get(i).getStatus()));
        	 cellItemTable.setBorder(0);
        	 tableListaTareas.addCell(cellItemTable);
        	 //fecha
        	 cellItemTable = new PdfPCell(new Phrase(datosReporteTotal.getListaTareas().get(i).getFechaDesarrollo()));
        	 cellItemTable.setBorder(0);
        	 tableListaTareas.addCell(cellItemTable);
        	 //recurso
        	 cellItemTable = new PdfPCell(new Phrase(datosReporteTotal.getListaTareas().get(i).getRecurso()));
        	 cellItemTable.setBorder(0);
        	 tableListaTareas.addCell(cellItemTable);
        	//descripcion
        	 cellItemTable = new PdfPCell(new Phrase(datosReporteTotal.getListaTareas().get(i).getDescripcion()));
        	 cellItemTable.setBorder(0);
        	 tableListaTareas.addCell(cellItemTable);
        	//observacion
        	 cellItemTable = new PdfPCell(new Phrase(datosReporteTotal.getListaTareas().get(i).getObservacion()));
        	 cellItemTable.setBorder(0);
        	 tableListaTareas.addCell(cellItemTable);
			
		}
         
         
         return tableListaTareas;
	 }

	public DatosReporte getDatosReporteTotal() {
		return datosReporteTotal;
	}

	public void setDatosReporteTotal(DatosReporte datosReporteTotal) {
		this.datosReporteTotal = datosReporteTotal;
	}

	public String getDirectorioSave() {
		return directorioSave;
	}

	public void setDirectorioSave(String directorioSave) {
		this.directorioSave = directorioSave;
	}
		 
	 

}
