package ve.com.avss.ventas.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;


/** Contiene herramientas generales. */
public class Util {
	
	public DecimalFormat dfMonto = new DecimalFormat("###########0.00#");
	public DecimalFormat dfAlfanumerico = new DecimalFormat("###########0.00#");
	
	private static SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd");
	private static String horaReloj = "";
	// el que formatea
	private static SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
	private static Date date = null;
	

	public static int anchoPantalla() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize.width;
	}
	
	public static int altoPantalla() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
		
		return screenSize.height;
	}
	
	/**
	 * Formateo de monto.
	 *
	 * @param monto
	 *            Monto a formatear.
	 * @return Double: Monto formateado.
	 */
	public static Double formatMontoDouble(String monto) {
		String montoTxn = monto;
		montoTxn = Util.replace(montoTxn, ".", "");
		montoTxn = Util.replace(montoTxn, ",", "");
		double dbAux = 0;
		try {
			dbAux = Double.parseDouble(montoTxn);
		} catch (Exception e) {
			return new Double(0);
		}
		if (dbAux == 0 || dbAux == -1) {
			return new Double(dbAux);
		}
		dbAux = dbAux / 100;
		Double dbMonto = new Double(dbAux);
		return dbMonto;
	}

	/**
	 * Reemplaza una cadena de caracteres por otra sobre una cadena de
	 * caracteres.
	 *
	 * @param origen
	 *            Cadena original.
	 * @param patron
	 *            Patrón a reemplazar.
	 * @param reemplazo
	 *            Patrón de reemplazo.
	 * @return String: Cadena con valor reemplazado.
	 */
	public static String replace(String origen, String patron, String reemplazo) {

		if (origen != null) {
			final int len = patron.length();
			StringBuffer sb = new StringBuffer();
			int found = -1;
			int inicio = 0;

			while ((found = origen.indexOf(patron, inicio)) != -1) {
				sb.append(origen.substring(inicio, found));
				sb.append(reemplazo);
				inicio = found + len;
			}
			sb.append(origen.substring(inicio));
			return sb.toString();

		} else {
			return "";
		}
	}



	/**
	 * Convertir monto de notacion cientifica a double.
	 *
	 * @param valorDouble
	 *            Valor en double,
	 */
	
	public static String convertirNotacionCientificaDecimal(double valorDouble){
	    Locale.setDefault(Locale.US);
	    DecimalFormat num = new DecimalFormat("###.00");
	    return num.format(valorDouble);
	}
	
	/**
	 * Convertir monto de notacion cientifica a double.
	 *
	 * @param valorDouble
	 *            Valor en double,
	 */
	
	public static String convertirNotacionCientificaDecimal(String valorString){
		
		if (valorString.equals("0.00") || valorString.equals("0.0") ||valorString.equals("")) {
			return "0.00";
		}
	    Locale.setDefault(Locale.US);
	    DecimalFormat num = new DecimalFormat("#,###.##");
	    Double aux = new Double(valorString);
	    return num.format(aux);
	}
	
	
	/**
	 * Convertir monto de notacion cientifica a double.
	 *
	 * @param valorDouble
	 *            Valor en double,
	 */
	
	public static String sumarNotacionCientificaDecimal(String monto1, double monto2){
		double montoSumado = Double.parseDouble(monto1) + monto2;
	    Locale.setDefault(Locale.US);
	    DecimalFormat num = new DecimalFormat("###.00");
	    return num.format(montoSumado);
	}
	
	public static String formatoMonedaBs(Double monto) {

		NumberFormat formatoNumero = NumberFormat.getNumberInstance();
		System.out.println(formatoNumero.format(monto));
		//Muestra 1.624.882,333
		formatoNumero.setMaximumFractionDigits(2);
		System.out.println(formatoNumero.format(monto));
		//Muestra 1.624.882,3
		
		return formatoNumero.format(monto);
	}
	
	public static String formatoMonedaGeneral(Double monto) {
		DecimalFormat formato = new DecimalFormat("#,##0.00");
		String valorFormateado = formato.format(monto);
		//Muestra en pantalla el valor 123.456,79 teniendo en cuenta que se usa la puntuación española
		System.out.println(valorFormateado);
		
		return valorFormateado;
	}
	
	public static String formatearMonto(String aux) {
		 String aux1 = aux.replace(",", "");
		 String aux2 = aux1.replace(".", "");
		 DecimalFormat formatter = new DecimalFormat("#,##0.00");
		 aux =  formatter.format((new Double(aux2)/100));
		
		return aux;
	}
	
	
	public static String formatearFechaSinHora(String fecha){
		 try {
			date = parseador.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        fecha = formateador.format(date);
        
        
        return fecha;
	}
	
	public static DefaultFormatterFactory armarMascaraMontoProvisional() {

		String txtMascara = "#,##0.00";
		Double maximoPermitido = Util.formatMontoDouble("999999999999999");

		Locale loc = new Locale("de", "FB");
		NumberFormat formato = NumberFormat.getNumberInstance( loc);
		DecimalFormat formatoCI = (DecimalFormat)  formato;
		formatoCI.applyPattern(txtMascara);
		
		NumberFormatter enFormatCI = new NumberFormatter(formatoCI);
		enFormatCI.setAllowsInvalid(true);
		enFormatCI.setMinimum(new Double(0));
		enFormatCI.setMaximum(maximoPermitido);
		enFormatCI.setOverwriteMode(true);
		enFormatCI.setCommitsOnValidEdit(true);

		NumberFormatter dnFormatCI = new NumberFormatter(formatoCI);		
		dnFormatCI.setMinimum(new Double(0));	
		dnFormatCI.setMaximum(maximoPermitido);
		dnFormatCI.setAllowsInvalid(true);

		DefaultFormatterFactory currFactoryCI = new DefaultFormatterFactory(dnFormatCI, enFormatCI);
		
		return currFactoryCI;
	}
	
	public static String fechaCalendario(Long fecha){

       String fechaFormat = parseador.format(fecha);
       
       
       return fechaFormat;
	}
	/*numeros enteros y decimales
	 * enteros: 4500000
	 * decimales:4500000.00
	 * 
	 * no acepta:
	 * 
	 * decimales con coma (,)
	 * 
	 * */
	public static boolean isNumero(String cadena){
		if (cadena == null) {
			return false;
		}

       return cadena.matches("^\\d*(\\.\\d+)?$");
	}
	
	public static String obtenerFechaActual() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String fecha = dtf.format(LocalDateTime.now());
		 
		return fecha;
	}
	
	public static String obtenerExtensionArchivo(String nombreArchivo) {
		String extension = "";
		int i = nombreArchivo.lastIndexOf('.');
		if (i > 0) {
			extension = nombreArchivo.substring(i);
		}
		
		return extension;
	}
	
	
	
	public static boolean imprimirArchivo(String direccionArchivo) throws IOException {
		
		File archivo = new File(direccionArchivo);
		 PDDocument document = null;
		 boolean isPrint = false;
		try {
			document = PDDocument.load(archivo);
		} catch (IOException e) {
			throw new IOException("No se encontro el archivo");
			
		}
		 
		    PrinterJob job = PrinterJob.getPrinterJob();
		 
		    if (job.printDialog() == true) {
		        job.setPageable(new PDFPageable(document));
		        try {
		        	 job.print();
		        	 isPrint = true;
		        	
				} catch (PrinterException e) {
					isPrint = false;
					throw new IOException("Error al imprimir el archivo");
				}
		    }

		return isPrint;
	}
	
	
	public static Image getIconImage() {
	   Image retValue = Toolkit.getDefaultToolkit().
	         getImage(ClassLoader.getSystemResource("ve/com/avss/ventas/icon/logoAVSS.png"));


	   return retValue;
	}
	
	public static String relojActual() {
		
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("HH:mm:ss");
		Runnable runnable = new Runnable() {
		    @Override
		    public void run() {
		        while (true) {
		            try {
		                Thread.sleep(500);
		                horaReloj = formateador.format(LocalDateTime.now());
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		};
		Thread hilo = new Thread(runnable);
		hilo.start();
		return horaReloj;
	}
	


}
