package ve.com.avss.reportes;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import ve.com.avss.empresa.BD.conectores.ConectorEmpresa;
import ve.com.avss.empresa.bean.DatosEmpresa;
import ve.com.avss.ventas.util.Util;

public abstract class AbstractReporte {
	private static final Logger log = Logger.getLogger(AbstractReporte.class);
	private ConectorEmpresa conectorEmpresa = new ConectorEmpresa();
	private DatosEmpresa datosEmpresa = new DatosEmpresa();
	protected Font fuenteDetalleTabla = new Font();
	public AbstractReporte() {
		super();
		try {
			datosEmpresa = conectorEmpresa.obtenerDatosEmpresa();
			fuenteDetalleTabla.setSize(6);
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
	}
	
	protected PdfPTable encabezado(String tipoReporte) {
		
		
		PdfPTable tableEncabezado = new PdfPTable(3);
        try {
        	tableEncabezado.setWidths(new int[]{1,69,50});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        PdfPCell cellSinBordeLogo = new PdfPCell();
        cellSinBordeLogo.setBorder(0);
        
        PdfPCell cellSinBordeTitulo = new PdfPCell();
        cellSinBordeTitulo.setBorder(0);
        PdfPCell cellSinBordeFecha = new PdfPCell();
        cellSinBordeFecha.setBorder(0);
        String logo = getDatosEmpresa().getLogo();
//        Image companyLogo;
//		try {
//			companyLogo = Image.getInstance(logo);
//			companyLogo.scaleAbsoluteWidth(40f);
//		    companyLogo.scaleAbsoluteHeight(40f);
//		        
//
		        
//		        cellSinBordeLogo.setImage(companyLogo);
		        tableEncabezado.addCell(cellSinBordeLogo);
//		} catch (BadElementException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
      
        
        Paragraph pInventario = new Paragraph(tipoReporte);
        pInventario.setAlignment(Element.ALIGN_CENTER);
        cellSinBordeTitulo.addElement(pInventario);
        tableEncabezado.addCell(cellSinBordeTitulo);
        cellSinBordeFecha.addElement(new Phrase("Fecha "+Util.obtenerFechaActual()+""));
        tableEncabezado.addCell(cellSinBordeFecha);
		
        return tableEncabezado;
	}
	
	
	public DatosEmpresa getDatosEmpresa() {
		return datosEmpresa;
	}
	public void setDatosEmpresa(DatosEmpresa datosEmpresa) {
		this.datosEmpresa = datosEmpresa;
	}

}
