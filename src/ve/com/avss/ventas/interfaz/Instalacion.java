package ve.com.avss.ventas.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.BD.conectores.ConectorEmpresa;
import ve.com.avss.empresa.bean.DatosEmpresa;
import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.util.Log4JConfigurator;
import ve.com.avss.ventas.util.Util;

public class Instalacion extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5850246605544071025L;
	private static final Logger log = Logger.getLogger(Instalacion.class);
	protected JLabel eRutaInstalacion = new JLabel();
	protected JLabel eTituloRutaInstalacion = new JLabel();
	protected JButton rutaInstalacionE = new JButton("Abrir");
	protected JButton btnInstalar = new JButton("Instalar");
	protected JButton btnCerrar = new JButton("Cerrar");
	protected JPanel jPanelInferior = new JPanel(); 
	protected JPanel jPanelRutaInstalacion = new JPanel(); 
	protected JPanel jPanelProcesoInstalacion = new JPanel(); 
	protected JPanel jPanelCrearDirectorios = new JPanel(); 
	protected JPanel jPanelEscribirArchivoConf = new JPanel(); 
	protected JPanel jPanelCopiarArchivos = new JPanel(); 
	protected JPanel jPanelCrearArchivoAccesoDirecto = new JPanel(); 
	protected JLabel eTituloInstalacion = new JLabel();
	protected JLabel eTituloFormulario = new JLabel("",JLabel.CENTER);
	protected JLabel eCrearDirectorios = new JLabel();
	protected JLabel eEscribirArchivoConf = new JLabel();
	protected JLabel eCopiarArchivos = new JLabel();
	protected JLabel eCrearArchivoAccesoDirecto = new JLabel();
	protected JProgressBar jb = new JProgressBar(0,100);;
	protected ConectorEmpresa conectorEmpresa = new ConectorEmpresa();
	protected DatosEmpresa datosEmpresa = new DatosEmpresa();
	protected String APP = "proyectos";
	/** JFrame base del dialogo */
	protected static JFrame parent = null;
	public Instalacion(JFrame parent) {
		Instalacion.parent = parent;
		inicializarEtiquetaProcesos();
		
		getContentPane().setLayout(new BorderLayout());
		BoxLayout boxlayoutProcesoInstalacion = new BoxLayout(jPanelProcesoInstalacion, BoxLayout.Y_AXIS);
		jPanelProcesoInstalacion.setLayout(boxlayoutProcesoInstalacion);
		jPanelProcesoInstalacion.add(eTituloInstalacion);
		jPanelProcesoInstalacion.add(eCrearDirectorios);
		jPanelProcesoInstalacion.add(eEscribirArchivoConf);
		jPanelProcesoInstalacion.add(eCopiarArchivos);
		jPanelProcesoInstalacion.add(eCrearArchivoAccesoDirecto);
		jPanelRutaInstalacion.add(eTituloRutaInstalacion);
		jPanelRutaInstalacion.add(eRutaInstalacion);
		jPanelRutaInstalacion.add(jb);
		
		jPanelInferior.add(rutaInstalacionE);
		jPanelInferior.add(btnInstalar);
		jPanelInferior.add(btnCerrar);
		
		getContentPane().add(eTituloFormulario,BorderLayout.NORTH);
		getContentPane().add(jPanelRutaInstalacion,BorderLayout.CENTER);
		getContentPane().add(jPanelInferior,BorderLayout.SOUTH);
		getContentPane().add(jPanelProcesoInstalacion,BorderLayout.WEST);
		
		btnInstalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				datosEmpresa.setRutaInstalacion(eRutaInstalacion.getText()+"\\avss");
				if (eRutaInstalacion.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Seleccione una ruta de instalación, presione el botón de Abrir ","Alerta",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					crearDirectorios();
					copiarDirectorio();
					escribirArchivoConf();
					copiarArchivos();
					crearArchivoAccesoDirecto();
					JOptionPane.showMessageDialog(null,"Programa instalado con exito ","Alerta",JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Error al instalar "+e.getMessage(),"Alerta",JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
		});
		
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				
				removeAll();
				dispose();
				System.exit(0);
			}
		});
		pack();
		setSize(800, 400);
		setAlwaysOnTop(false);
		setLocationRelativeTo(null);
		setTitle("Instalar programa");
		setIconImage(Util.getIconImage());
		setResizable(true);
		setVisible(true);
	}

	public static void main(String[] args) {
		
		inicializarLog4j();
		Instalacion instalar = new Instalacion(null);
		parent = instalar;
		instalar.leerRutaInstalacion();
		
	}
	
	private void leerRutaInstalacion(){
		rutaInstalacionE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser jfRutaInstalacion = new JFileChooser();
				jfRutaInstalacion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
				int result = jfRutaInstalacion.showOpenDialog(parent);
				if (result == JFileChooser.CANCEL_OPTION) {
					return;
				}
				jfRutaInstalacion.getCurrentDirectory();

				eRutaInstalacion.setText(jfRutaInstalacion.getSelectedFile().getAbsolutePath());
				log.info("Ruta de instalacion: "+jfRutaInstalacion.getSelectedFile().getAbsolutePath());
				
				

			}
		});
	}
	private void copiarArchivos() throws Exception {
		
		log.info("Copiando archivos de la aplicacion");
		String[][] archivosCopiar = { 
				{"bd_"+APP+".db","\\"+APP+"\\bd\\"}
				,{APP+".exe","\\"+APP+"\\"}
				,{APP+".jar","\\"+APP+"\\"}
				,{"configuracion.properties","\\"+APP+"\\conf\\"}
				,{"log4j.xml","\\"+APP+"\\conf\\"}
				//,{"Cargar por lote.bat","\\"+APP+"\\conf\\"}
				//,{"CargaPorLote.jar","\\"+APP+"\\conf\\"}
				//,{"fondoAplicacion.jpg","\\"+APP+"\\img\\"}
				
				};
		File ficheroCopiar = null;
		File ficheroDestino = null;
		
		for (int i = 0; i < archivosCopiar.length; i++) {
			
			String archivo = archivosCopiar[i][0];
			String directorio = archivosCopiar[i][1];
			
			 /*copiando archivo de base de datos*/
			ficheroCopiar = new File(".\\", archivo);
	        ficheroDestino = new File(datosEmpresa.getRutaInstalacion()+directorio, archivo);
	        if (ficheroCopiar.exists()) {
	            try {
					Files.copy(Paths.get(ficheroCopiar.getAbsolutePath()), Paths.get(ficheroDestino.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
					log.info("Se ha copiado con exito "+archivo);
					//JOptionPane.showMessageDialog(null,"Se ha copiado con exito "+archivo,"Alerta",JOptionPane.INFORMATION_MESSAGE);
					jb.setValue(100);
					eCopiarArchivos.setForeground(new Color(0, 153, 0));
	            } catch (IOException e) {
	            	JOptionPane.showMessageDialog(null,"No se pudo copiar "+archivo+" "+e.getMessage(),"Alerta",JOptionPane.ERROR_MESSAGE);
	            	eCopiarArchivos.setForeground(Color.RED);
					eCrearArchivoAccesoDirecto.setForeground(Color.RED);
	            	log.error("No se pudo copiar "+archivo+" "+e.getMessage());
					throw new Exception(e.getMessage());
				}
	        } else {
	        	log.error("Archivo "+archivo+" no existe");
	        	JOptionPane.showMessageDialog(null,"Archivo "+archivo+" no existe","Alerta",JOptionPane.ERROR_MESSAGE);
	        	eCopiarArchivos.setForeground(Color.RED);
				eCrearArchivoAccesoDirecto.setForeground(Color.RED);
	        	throw new Exception("No existe archivo "+archivo);
	        }
			log.info("archivo "+archivo);
			log.info("directorio "+directorio);
			
		
		}
		
	}
	
	private void escribirArchivoConf() throws Exception {
		Properties archivoProperties  = new Properties();
		log.info("Escribiendo archivos de configuracion");
				try {
					archivoProperties.load(new FileReader("./configuracion.properties"));
					archivoProperties.setProperty("direccionArchivoBD", datosEmpresa.getRutaInstalacion()+"\\"+APP+"\\bd\\");
					archivoProperties.setProperty("rutaInstalacion", datosEmpresa.getRutaInstalacion());
					archivoProperties.setProperty("front", APP);
					archivoProperties.setProperty("archivoBD","bd_"+ APP+".db");
					archivoProperties.store(new FileWriter("./configuracion.properties"),"Actualizado");
					log.info("direccionArchivoBD "+datosEmpresa.getRutaInstalacion()+"\\"+APP+"\\bd\\");
					log.info("rutaInstalacion "+datosEmpresa.getRutaInstalacion());
					jb.setValue(75);
					eEscribirArchivoConf.setForeground(new Color(0, 153, 0));
					
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null,"Error al leer conf "+e,"Alerta",JOptionPane.ERROR_MESSAGE);
					eEscribirArchivoConf.setForeground(Color.RED);
					eCopiarArchivos.setForeground(Color.RED);
					eCrearArchivoAccesoDirecto.setForeground(Color.RED);
					log.error("error "+e.getMessage());
					throw new Exception(e.getMessage());

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"Error al leer conf "+e,"Alerta",JOptionPane.ERROR_MESSAGE);
					eEscribirArchivoConf.setForeground(Color.RED);
					eCopiarArchivos.setForeground(Color.RED);
					eCrearArchivoAccesoDirecto.setForeground(Color.RED);
					log.error("error "+e.getMessage());
					throw new Exception(e.getMessage());
				}
				

	}
	private void crearDirectorios() {
		log.info("Creando directorios de instalacion");
		String rutaInstalacion = datosEmpresa.getRutaInstalacion();
		 File directorios = null;
		
		 directorios = new File(rutaInstalacion);
	        if (!directorios.exists()) {
	            if (directorios.mkdirs()) {
	            	//JOptionPane.showMessageDialog(null,"Se ha creado con exito la ruta base de instalacion","Alerta",JOptionPane.INFORMATION_MESSAGE);
	            	log.info("Creado con exito ruta instalacion");
	            } else {
	            	JOptionPane.showMessageDialog(null,"Error al crear ruta base de instalacion","Alerta",JOptionPane.ERROR_MESSAGE);
	            }
	        }
	        
	        String[] directoriosCrear = {"\\"+APP+""
	        		,"\\"+APP+"\\bd"
	        		,"\\"+APP+"\\img","\\"+APP+"\\img\\iconos"
	        		,"\\"+APP+"\\conf"
	        		,"\\"+APP+"\\reportes"
	        		}; 
	        
	        if (directorios.exists()) {
	        	Path path = null;
	            try {
	            	
	            	for (int i = 0; i < directoriosCrear.length; i++) {
	            		path = Paths.get(rutaInstalacion+directoriosCrear[i]);
						Files.createDirectories(path);
						log.info("directorio creado "+path);
					}
	       
					JOptionPane.showMessageDialog(null,"Se ha creado con exito los directorios de la aplicación","Alerta",JOptionPane.INFORMATION_MESSAGE);
					log.info("Se ha creado con exito los directorios de la aplicación");
					eCrearDirectorios.setForeground(new Color(0, 153, 0));
					jb.setValue(200);
	            } catch (IOException e) {
					JOptionPane.showMessageDialog(null,"Error al crear los directorios","Alerta",JOptionPane.ERROR_MESSAGE);
					eCrearDirectorios.setForeground(Color.RED);
					eEscribirArchivoConf.setForeground(Color.RED);
					eCopiarArchivos.setForeground(Color.RED);
					eCrearArchivoAccesoDirecto.setForeground(Color.RED);
					log.error("Error al crear los directorios");
				}
				
			}

	}
	
	private void crearArchivoAccesoDirecto() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fichero = null;
		PrintWriter pw = null;
		String archivoLeido = "";
	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File (".\\", "acceso directo");
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	       
	         while((linea=br.readLine())!=null) {
	        	
	        	 archivoLeido += "\n"+linea;
	         }
	         archivoLeido = archivoLeido.replace("@ruta_instalacion@", datosEmpresa.getRutaInstalacion()+"\\"+APP+""); 
	        log.info(archivoLeido);
	         
	         fichero = new FileWriter(".\\acceso directo.bat");
	         pw = new PrintWriter(fichero);
	         pw.println(archivoLeido);
	        	
	      }
	      catch(Exception e){
	        log.info(e);
	        JOptionPane.showMessageDialog(null,"Error al crear acceso directo "+e.getMessage(),"Alerta",JOptionPane.ERROR_MESSAGE);
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();  
	               fichero.close();
	               //JOptionPane.showMessageDialog(null,"Se ha creado acceso directo","Alerta",JOptionPane.INFORMATION_MESSAGE);
	               eCrearArchivoAccesoDirecto.setForeground(new Color(0, 153, 0));
	            }                  
	         }catch (Exception e2){ 
	        	 log.info(e2);
	        	 JOptionPane.showMessageDialog(null,"Error al crear acceso directo "+e2.getMessage(),"Alerta",JOptionPane.ERROR_MESSAGE);
	        	 eCrearArchivoAccesoDirecto.setForeground(Color.RED);
	         }
	      }
		
	}
	
	private void copiarDirectorio() {
		log.info("Copiando iconos de la aplicacion");
		 String[] directoriosCrear = {"img\\iconos"};
		 File directorioCrear = null;
		 File directorioApp = null;
		for (int i = 0; i < directoriosCrear.length; i++) {
			directorioCrear = new File(".\\"+directoriosCrear[i]);
			directorioApp = new File(datosEmpresa.getRutaInstalacion()+"\\"+APP+"\\"+directoriosCrear[i]);
			 if (directorioApp.isDirectory()) {
				 if (!directorioApp.exists()) {
			            //No existe directorio destino, lo crea.
					 directorioApp.mkdir();
			        }
			        for (String f : directorioCrear.list()) {
			        	
			        	try {
			        		String ficheroCopiar = datosEmpresa.getRutaInstalacion()+"\\"+APP+"\\"+directoriosCrear[i]+"\\"+f;
			        		Files.copy(Paths.get(".\\"+directoriosCrear[i]+"\\"+f), Paths.get(ficheroCopiar), StandardCopyOption.REPLACE_EXISTING);
							log.info("Se ha copiado con exito "+ficheroCopiar);
							jb.setValue(50);
						} catch (Exception e) {
							log.info(e);
							 JOptionPane.showMessageDialog(null,"Error al copiar iconos "+e.getMessage(),"Alerta",JOptionPane.ERROR_MESSAGE);
						}
			        	
			        	}
		        } 
		}
		
		log.info("Fin - Copiando iconos de la aplicacion");
		jb.setValue(20);
	}
	
	private void inicializarEtiquetaProcesos() {
		
		eTituloInstalacion.setFont(DatosConfiguracion.TITULO_2_BOLD);
		eTituloFormulario.setFont(DatosConfiguracion.TITULO_1_BOLD);
		eCrearDirectorios.setFont(DatosConfiguracion.TITULO_3_BOLD);
		eEscribirArchivoConf.setFont(DatosConfiguracion.TITULO_3_BOLD);
		eCopiarArchivos.setFont(DatosConfiguracion.TITULO_3_BOLD);
		eCrearArchivoAccesoDirecto.setFont(DatosConfiguracion.TITULO_3_BOLD);
		
		eTituloFormulario.setForeground(Color.BLACK);
		eTituloInstalacion.setForeground(Color.DARK_GRAY);
		eCrearDirectorios.setForeground(Color.LIGHT_GRAY);
		eEscribirArchivoConf.setForeground(Color.LIGHT_GRAY);
		eCopiarArchivos.setForeground(Color.LIGHT_GRAY);
		eCrearArchivoAccesoDirecto.setForeground(Color.LIGHT_GRAY);
		
		eTituloFormulario.setPreferredSize(new Dimension(800, 80));
		eCrearDirectorios.setPreferredSize(new Dimension(150, 100));
		eRutaInstalacion.setPreferredSize(new Dimension(400, 50));
		
		eTituloFormulario.setText("Instalar programa");
		eTituloInstalacion.setText("Proceso de instalación:");
		eCrearDirectorios.setText("1.- Crear directorios");
		eCopiarArchivos.setText("2.- Copiar archivos");
		eEscribirArchivoConf.setText("3.- Escribir archivo de configuración");
		eCrearArchivoAccesoDirecto.setText("4.- Crear acceso directo");
		eTituloRutaInstalacion.setText("Ruta de instalación: ");
		
		jb.setValue(0);
		jb.setStringPainted(true);    
	}
	
	private static void inicializarLog4j() {
		String ruta = "";

		ruta = "./conf" + File.separator+"log4j.xml";
		Log4JConfigurator.configure(ruta);
		
	}

}
