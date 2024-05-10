package ve.com.avss.ventas.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LeerConfiguracion {
	/** Log4J --> looger de la apliacion */
	private static final Logger log = Logger.getLogger(LeerConfiguracion.class);
	private static String DIRECCION_ARCHIVO_PROPERTIES = "./conf/configuracion.properties";
	private static Properties archivoProperties = new Properties();
	private static  String modo;
	private static  String urlBD;
	private static String puertoBD;
	private static String nombreBD;
	private static String usuarioBD;
	private static String claveBD;
	private static String front;
	private static String archivoBD;
	private static String direccionArchivoBD;
	private static String confMenu;
	private static String rutaInstalacion;
	private static String licencia;
	
//	public static void main(String[] ar) {
//		LeerConfiguracion lc = new LeerConfiguracion();
//		
//	}
	
	public LeerConfiguracion() throws Exception {
		
		super(); 
		cargarArchivo();
		recargarArchivo();
		log.info("Cargando configuracion");
		log.info("Configuracion cargada para "+front);
	}

	private static void cargarArchivo() throws Exception {
		try {
//			System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
//			System.out.println (new File (".").getAbsolutePath ());
			archivoProperties.load(new FileReader(DIRECCION_ARCHIVO_PROPERTIES));
		} catch (FileNotFoundException e) {
			log.error("error "+e.getMessage());
			throw new Exception("No se encontró el archivo de configración");
		} catch (IOException e) {
			log.error("error "+e.getMessage());
			throw new IOException("Error leyendo configración");
			
		}catch (Exception e) {
			log.error("error "+e.getMessage());
			throw new Exception("Error inesperado leyendo configración");
		}
	}
	
	private static void recargarArchivo() throws Exception {
		modo = archivoProperties.getProperty("modo");
		urlBD = archivoProperties.getProperty("urlBD");
		puertoBD = archivoProperties.getProperty("puertoBD");
		nombreBD = archivoProperties.getProperty("nombreBD");
		usuarioBD = archivoProperties.getProperty("usuarioBD");
		claveBD = archivoProperties.getProperty("claveBD");
		front = archivoProperties.getProperty("front");
		archivoBD = archivoProperties.getProperty("archivoBD");
		direccionArchivoBD = archivoProperties.getProperty("direccionArchivoBD");
		confMenu = archivoProperties.getProperty("confMenu");
		rutaInstalacion = archivoProperties.getProperty("rutaInstalacion");
		licencia = archivoProperties.getProperty("licencia");
		log.info("Recargando configuracion");
	}
	
	public static String getModo() {
		return modo;
	}


	public static void setModo(String modo) {
		LeerConfiguracion.modo = modo;
	}


	public static String getUrlBD() {
		return urlBD;
	}

	public static void setUrlBD(String urlBD) {
		LeerConfiguracion.urlBD = urlBD;
	}

	public static String getPuertoBD() {
		return puertoBD;
	}

	public static void setPuertoBD(String puertoBD) {
		LeerConfiguracion.puertoBD = puertoBD;
	}

	public static String getNombreBD() {
		return nombreBD;
	}

	public static void setNombreBD(String nombreBD) {
		LeerConfiguracion.nombreBD = nombreBD;
	}

	public static String getUsuarioBD() {
		return usuarioBD;
	}

	public static void setUsuarioBD(String usuarioBD) {
		LeerConfiguracion.usuarioBD = usuarioBD;
	}

	public static String getClaveBD() {
		return claveBD;
	}

	public static void setClaveBD(String claveBD) {
		LeerConfiguracion.claveBD = claveBD;
	}

	public static String getFront() {
		return front;
	}

	public static void setFront(String front) {
		LeerConfiguracion.front = front;
	}

	public static String getArchivoBD() {
		return archivoBD;
	}

	public static void setArchivoBD(String archivoBD) {
		LeerConfiguracion.archivoBD = archivoBD;
	}

	public static String getDireccionArchivoBD() {
		return direccionArchivoBD;
	}

	public static void setDireccionArchivoBD(String direccionArchivoBD) {
		LeerConfiguracion.direccionArchivoBD = direccionArchivoBD;
	}

	
	public static String getConfMenu() {
		return confMenu;
	}

	public static void setConfMenu(String confMenu) {
		LeerConfiguracion.confMenu = confMenu;
	}

	public static String getRutaInstalacion() {
		return rutaInstalacion;
	}

	public static void setRutaInstalacion(String rutaInstalacion) {
		LeerConfiguracion.rutaInstalacion = rutaInstalacion;
	}

	public static String getLicencia() {
		return licencia;
	}

	public static void setLicencia(String licencia) {
		LeerConfiguracion.licencia = licencia;
	}

	public static void grabarPropiedad(String propiedad, String valor) {
		
		log.info("Escribiendo propiedad");
		log.info(propiedad);
		log.info(valor);
		try {
			cargarArchivo();
			archivoProperties.setProperty(propiedad, valor);
			archivoProperties.store(new FileWriter(DIRECCION_ARCHIVO_PROPERTIES),"Actualizado");
			cargarArchivo();
			recargarArchivo();
		} catch (IOException e) {
			log.error("error "+e.getMessage());
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
	
	}

	
	
}
