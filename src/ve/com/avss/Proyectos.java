package ve.com.avss;



import java.io.File;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.formdev.flatlaf.FlatLightLaf;

import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.ventas.BD.conectores.Conexion;
import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.interfaz.Autenticacion;
import ve.com.avss.ventas.util.CONSTANTES_INTERACCION;
import ve.com.avss.ventas.util.LeerConfiguracion;
import ve.com.avss.ventas.util.Log4JConfigurator;

public class Proyectos extends JFrame{
	private static final Logger log = Logger.getLogger(Proyectos.class);
	private static final long serialVersionUID = 1L;
	/** Diálogo de solicitud de datos. */
	protected transient static ve.com.avss.empresa.interfaz.AbstractDialogoBase dialogo;
	/** Indicador de datos aceptados. */
	protected transient static boolean aceptar;
	
	/*datos de usuario*/
	private static DatosUsuario datosUsuario = new DatosUsuario();
	/** JFrame base del dialogo */
	 /*datos de configuracion*/
	private static DatosConfiguracion datosConfiguracion = null;
	protected static JFrame parent = null;
	/** Autenticacion. */
	private Autenticacion aux = null;
	public Proyectos() {
		super();
		log.info("constructor");

	
	}
	
	
	
	/**
	 * createAndShowGui.
	 *
	 * @param ruta
	 *            Ruta de archivos de configuración.
	 */
	private void createAndShowGUI() {
		
		FlatLightLaf lookAndFeel = null;
		lookAndFeel = new FlatLightLaf();
		JFrame.setDefaultLookAndFeelDecorated(true);

		try {
			UIManager.setLookAndFeel(lookAndFeel);
			
		} catch (Exception e1) {
			log.fatal("error", e1);
		}
		Exception exc = null;
		try {
			aux = new Autenticacion(parent);
		} catch (Exception e) {
			exc = e;
			if (e.getMessage().length() > 0) {
				log.fatal("error", e);
				JOptionPane.showMessageDialog(null, e.getMessage(),CONSTANTES_INTERACCION.MENSAJE_APLICACION_DIALOGO, JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (exc != null) {
			System.exit(0);
		}
		
		
	}
	public static void main(String[] args) throws Exception {
		
		try {
			inicializarLog4j();
			new LeerConfiguracion();
			datosConfiguracion = new DatosConfiguracion();
		} catch (Exception e) {
			log.info(e);
			
			JOptionPane.showMessageDialog(null, e.getMessage(),CONSTANTES_INTERACCION.MENSAJE_APLICACION_DIALOGO, JOptionPane.ERROR_MESSAGE);
			throw new Exception(e.getMessage());
		}
		
		
		if (validarLicencia ()) {
			JOptionPane.showMessageDialog(null, "Contacte con el adminstrador del sistema. Fecha out.",CONSTANTES_INTERACCION.MENSAJE_APLICACION_DIALOGO, JOptionPane.ERROR_MESSAGE);
			return;
		}
	
		Proyectos proyectos = new Proyectos();
		proyectos.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		
		try {
		
			Conexion.conectar();
		} catch (Exception e) {
			log.info(e);
			JOptionPane.showMessageDialog(null, e.getMessage(),CONSTANTES_INTERACCION.MENSAJE_APLICACION_DIALOGO, JOptionPane.ERROR_MESSAGE);
			throw new Exception(e.getMessage());
		}
		
		
		final Proyectos ventasGUI = new Proyectos();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ventasGUI.createAndShowGUI();
			}
		});
		
	}
	
	private static boolean validarLicencia () {
		
		/*
		 * parametros de set de la fecha
		 * año, mes, dia*/
		String licencia = LeerConfiguracion.getLicencia();
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.get(Calendar.YEAR);
		fechaActual.get(Calendar.MONTH);
		fechaActual.get(Calendar.DATE);
		fechaActual.set(fechaActual.get(Calendar.YEAR), fechaActual.get(Calendar.MONTH), fechaActual.get(Calendar.DATE));
		String fechaSistema = fechaActual.get(Calendar.YEAR)+""+(fechaActual.get(Calendar.MONTH)+1)+""+fechaActual.get(Calendar.DATE);
		Integer licenciaGenerada = new Integer(fechaSistema)*9;
		
		
		
		return licenciaGenerada >new Integer(licencia) ? true : false;
		
//		String licenciaConfigurada = new Integer(licencia.substring(0, 4))+""+new Integer(licencia.substring(4, 6))+""+new Integer(licencia.substring(6, 8));
//		
//		Calendar fechaTop = Calendar.getInstance();
//		
//		fechaTop.set(new Integer(licencia.substring(0, 4)), new Integer(licencia.substring(4, 6)), new Integer(licencia.substring(6, 8)));
//		String fechaConf = fechaTop.get(Calendar.YEAR)+""+fechaTop.get(Calendar.DATE)+""+fechaTop.get(Calendar.MONTH);
		
		//return fechaConf.equals(fechaSistema) ? true : false;
	}
	
	private static void inicializarLog4j() {
		String ruta = "";

		ruta = "./conf" + File.separator+"log4j.xml";
		Log4JConfigurator.configure(ruta);
		
	}

}
