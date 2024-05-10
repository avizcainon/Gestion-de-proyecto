package ve.com.avss.ventas.interfaz;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import ve.com.avss.empresa.BD.conectores.ConectorEmpresa;
import ve.com.avss.empresa.bean.DatosEmpresa;
import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.proyectos.MetodosPublicos;
import ve.com.avss.proyectos.BD.ejecutores.QueryProyecto;
import ve.com.avss.proyectos.bean.DatosProyecto;
import ve.com.avss.proyectos.bean.DatosTarea;
import ve.com.avss.proyectos.interfaz.DialogoDetallesProyecto;
import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.util.AppJButton;
import ve.com.avss.ventas.util.CONSTANTES_INTERACCION;
import ve.com.avss.ventas.util.Util;

public class Inicio extends AbstractDialogoBase implements ActionListener{
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Inicio.class);
	private DatosUsuario datosUsuario = new DatosUsuario();
	private DatosEmpresa datosEmpresa = new DatosEmpresa();

	private ConectorEmpresa conectorEmpresa = new ConectorEmpresa();

	private MetodosPublicos metodosPublicos = null;
	protected JPanel jPanelTablaCitas = new JPanel(new FlowLayout());
	protected JPanel panelTablatareas = new JPanel();
	protected DefaultTableModel modelTablaTareas = new DefaultTableModel();
	protected JTable tablaListaTareas = null;
	protected List<DatosTarea> listaTareas = null;
	protected int rowSeleccionada = 0;
	protected JDateChooser dateChooser;
	protected JScrollPane sp = null;
	
	public 	Inicio(JFrame parent, DatosUsuario datosUsuario) throws Exception {
		super(parent);
		log.info("Constructor - construyendo paneles e interfaz");
		this.datosUsuario = datosUsuario;
		
		try {
			datosEmpresa = conectorEmpresa.obtenerDatosEmpresa(); 
			
		} catch (Exception e) {
			log.info("error: "+e);
		}
		
		construirPanelesCampos();
		construirTablaCitas();
		cargarListaCitas(true);
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				windowAction(CMD_CANCEL);

			}
		});
		
		metodosPublicos = new MetodosPublicos(parent,this.datosEmpresa,this.datosUsuario);
		
		
		eUsuario.setPreferredSize(new Dimension(Util.anchoPantalla()-100, 30));
		eUsuario.setText("Usuario: "+datosUsuario.getUsuario());
		eUsuario.setHorizontalAlignment(JLabel.LEFT);
		eUsuario.setFont(DatosConfiguracion.TITULO_3_BOLD);
		

		JLabel imagenFondo = new JLabel();
		
		imagenFondo.setSize(Util.anchoPantalla()-50, Util.altoPantalla());
		ImageIcon imgIcon = new ImageIcon("."+CONSTANTES_INTERACCION.PATH_IMAGENES+"fondoAplicacion.jpg");
        Image imgEscalada = imgIcon.getImage().getScaledInstance(imagenFondo.getWidth(),
        		imagenFondo.getHeight(), Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        imagenFondo.setIcon(iconoEscalado);
       
        
        
        jContentPaneInferior.add(etiquetaReloj);
		jContentPaneInferior.add(eUsuario);
		jContentPaneCentral.add(imagenFondo);
		
		jContentPaneCentral.add(panelTablatareas);
		
		
		btnBuscarCitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.info("guarda cita");
				Date date = dateChooser.getDate();
				java.sql.Date sDate = new java.sql.Date(date.getTime());
				//JOptionPane.showMessageDialog(null, sDate+" Introduzca código valido","Mensaje de la aplicación",JOptionPane.ERROR_MESSAGE);
				try {
					listaTareas = QueryProyecto.obtenerListaTareaFecha(sDate+"");
					//listaTareas = QueryCita.obtenerListaCitasFecha(sDate+"");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cargarListaCitas(false);
				log.info("guardar cita - fin");
			}
		});
		
		btnCargarCitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.info("guarda cita");
				

				try {
					listaTareas = QueryProyecto.obtenerListaTareaFecha("");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cargarListaCitas(false);
				log.info("guardar cita - fin");
			}
		});
		// Instanciar Componente
		JPanel jpanelBuscarCita = new JPanel();
		JPanel jpanelCargarCita = new JPanel();
		JLabel tituloBuscar = new JLabel("Buscar Cita por fecha");
		//tituloBuscar.setBackground(Color.BLUE);
		JLabel tituloCargar = new JLabel("Cargar todas las citas");
		//tituloCargar.setBackground(Color.GREEN);
		
		jpanelBuscarCita.setPreferredSize(new Dimension(300,200));
		//jpanelBuscarCita.setBackground(Color.BLUE);
		jpanelCargarCita.setPreferredSize(new Dimension(200,200));
		//jpanelCargarCita.setBackground(Color.GREEN);
		
		dateChooser = new JDateChooser();
		jContentPaneIzquierdo.setLayout(new BoxLayout(jContentPaneIzquierdo, BoxLayout.Y_AXIS));
		
		jpanelBuscarCita.add(dateChooser);
		jpanelBuscarCita.add(btnBuscarCitas);
		jpanelCargarCita.add(btnCargarCitas);
		
		jContentPaneIzquierdo.add(tituloBuscar);
		jContentPaneIzquierdo.add(jpanelBuscarCita);
		jContentPaneIzquierdo.add(tituloCargar);
		jContentPaneIzquierdo.add(jpanelCargarCita);
		
		
		
		jContentPane.add(jContentPaneCentral, BorderLayout.CENTER);
		jContentPane.add(menuBar,BorderLayout.NORTH);
		jContentPane.add(jContentPaneIzquierdo,BorderLayout.EAST);
		jContentPane.add(jContentPaneInferior,BorderLayout.SOUTH);

		getContentPane().add(jContentPane);
		setTitle("Inicio | Sistema de Gestión de proyectos");
		pack();
		
		
		setSize(Util.anchoPantalla()-100, Util.altoPantalla()-100);
		setAlwaysOnTop(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	private void construirPanelesCampos() {

		String campo = "";

		/* armar botones de inicio */
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		
		menuArchivo = new JMenu("Archivo");
		menuArchivo.setIcon(DatosConfiguracion.iArchivo);
		menuArchivo.setFont(DatosConfiguracion.TITULO_2_PLAIN);
		menuBar.add(menuArchivo);
		menuItemSalir = new JMenuItem("Salir");
		menuItemSalir.addActionListener(this);
		menuItemSalir.setActionCommand(CMD_CANCEL);
		menuItemSalir.setIcon(DatosConfiguracion.iSalir);
		
		
		menuItemAbrirArchivo = new JMenuItem("Abrir archivo...");
		menuItemAbrirArchivo.addActionListener(this);
		menuItemAbrirArchivo.setIcon(DatosConfiguracion.iAbrirArchivo);
		
		menuArchivo.add(menuItemAbrirArchivo);

		menuArchivo.add(menuItemSalir);
		
		menuConfiguracion = new JMenu("Configuración");
		menuConfiguracion.setIcon(DatosConfiguracion.iConfiguracion);
		menuConfiguracion.setFont(DatosConfiguracion.TITULO_2_PLAIN);
		
		btnBuscarCitas = new AppJButton("primary", "subproceso");
		btnBuscarCitas.setName("buscarCitas");
		btnBuscarCitas.setPreferredSize(new Dimension(CONSTANTES_INTERACCION.ANCHO_BOTON, CONSTANTES_INTERACCION.ALTO_BOTON));
		btnBuscarCitas.setOpaque(true);
		btnBuscarCitas.setText("Filtrar cita");
		btnBuscarCitas.setIcon(DatosConfiguracion.iConfiguracion);
		btnBuscarCitas.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBuscarCitas.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		btnCargarCitas = new AppJButton("primary", "subproceso");
		btnCargarCitas.setName("cargarCitas");
		btnCargarCitas.setPreferredSize(new Dimension(CONSTANTES_INTERACCION.ANCHO_BOTON, CONSTANTES_INTERACCION.ALTO_BOTON));
		btnCargarCitas.setOpaque(true);
		btnCargarCitas.setText("Cargar todas las citas");
		btnCargarCitas.setIcon(DatosConfiguracion.iReporteTotal);
		btnCargarCitas.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCargarCitas.setVerticalTextPosition(SwingConstants.BOTTOM);
		armarBotones("Administracion");
		armarBotones("Usuario");
		/*
		for (int i = 0; i < datosUsuario.getListaModulos().size(); i++) {
			campo = datosUsuario.getListaModulos().get(i).getNombre();
			if (datosUsuario.getListaModulos().get(i).getActivo().equals("1")) {
				armarBotones(campo);
			}
			
		}
		*/
		armarBotones("Cliente");
		armarBotones("Proyecto");
		menuBar.add(menuConfiguracion);
		menuItemAcercade = new JMenuItem("Acerca de...");
		menuConfiguracion.add(menuItemAcercade);
		menuItemAcercade.addActionListener((ActionListener)this);
		menuItemAcercade.setIcon(DatosConfiguracion.iAcercaDe);
		
		//menuConfiguracion.add(menuItemUsuario);
		
	}
	

	

	@Override
	protected boolean actionOK() {
		
		return false;
	}


	public void actionPerformed(ActionEvent event) {
		/*accion para salir del sistema*/
		if (event.getSource() == menuItemSalir) {

			windowAction(event);
		
        }
		
		/*accion para abrir archivo en el directorio de reporte*/
		if (event.getSource() == menuItemAbrirArchivo) {
			JFileChooser abrirArchivo = null;
			 abrirArchivo = new JFileChooser(datosEmpresa.getRutaInstalacion()+"\\ventas"+CONSTANTES_INTERACCION.PATH_REPORTES);
			abrirArchivo.showOpenDialog(abrirArchivo);
			if (abrirArchivo.getSelectedFile() == null) {
				return;
			}
			 try {
				Desktop.getDesktop().open(abrirArchivo.getSelectedFile().getAbsoluteFile());
			} catch (IOException ex) {
				log.info(ex);
				mostrarException(ex.getMessage());
			}
			 return;
        }
		/*accion para mostrar el dialogo de los datos de la empresa*/
		if (event.getSource() == menuItemEmpresa) {
			try {
				metodosPublicos.dialogoEmpresa();
			} catch (Exception ex) {
				log.info(ex);
				mostrarException(ex.getMessage());
				
			}
			return;
        }
		
		/*accion para mostrar el dialogo de los productos*/
		if (event.getSource() == menuItemProyecto) {
			try {
				metodosPublicos.dialogoProyecto();
			} catch (Exception ex) {
				log.info(ex);
				mostrarException(ex.getMessage());
			}
			
			return;
        }
		/*accion para mostrar el dialogo de los usuarios del sistema*/
		if (event.getSource() == menuItemUsuario) {
			try {
				metodosPublicos.dialogoUsuario();
			} catch (Exception ex) {
				log.info(ex);
				mostrarException(ex.getMessage());
			}
			
			return;
        }
		/*accion para mostrar el dialogo de los clientes / proveedor*/
		if (event.getSource() == menuItemClienteProveedor) {
			try {
				metodosPublicos.dialogoCliente();
				cargarListaCitas(true);
			} catch (Exception ex) {
				log.info(ex);
				mostrarException(ex.getMessage());
			}
			return;
           
        }
		
		/*accion para mostrar mensaje de la aplicacion*/
		if (event.getSource() == menuItemAcercade) {
			JOptionPane.showMessageDialog(null,"Sistema de ventas e inventario creado por\nAVSS Antonio Vizcaino Software Solution\n2021","Acerca de",JOptionPane.INFORMATION_MESSAGE); 
			return;
        }

	}

	private void construirTablaCitas() {
		log.info("construirTablaCitas");
		// Column Names
		String[] nombreColumnaProductos = {"id", "Proyecto", "Status","Fecha","Asignado","Descripción","Observación" };

		// inicializamos el JTable
		modelTablaTareas.setColumnIdentifiers(nombreColumnaProductos);
		tablaListaTareas = new JTable();
		tablaListaTareas.setModel(modelTablaTareas);

		/*ocualtamos la columna id*/
		TableColumn column = tablaListaTareas.getColumnModel().getColumn(0);
	    column.setMinWidth(0);
	    column.setMaxWidth(0);
	    column.setWidth(0);
	    column.setPreferredWidth(0);
	    
	    TableColumnModel columnModel = tablaListaTareas.getColumnModel();

	    
	    columnModel.getColumn(1).setPreferredWidth(150);
	    columnModel.getColumn(2).setPreferredWidth(200);
	    columnModel.getColumn(3).setPreferredWidth(250);
	    columnModel.getColumn(4).setPreferredWidth(200);
	    columnModel.getColumn(5).setPreferredWidth(450);
	    
	    tablaListaTareas.getColumnModel().getColumn(1).setPreferredWidth(180);
	    tablaListaTareas.getColumnModel().getColumn(2).setPreferredWidth(100);
	    tablaListaTareas.getColumnModel().getColumn(3).setPreferredWidth(90);
	    tablaListaTareas.getColumnModel().getColumn(3).setPreferredWidth(200);
	    
	    
		// agregamos al JScrollPane
		sp = new JScrollPane(tablaListaTareas);
		sp.setPreferredSize(new Dimension(Util.anchoPantalla()-600, Util.altoPantalla()-400));
		panelTablatareas.add(sp);
		log.info("construirTablaCitas - fin");
	}
	
	private void cargarListaCitas(boolean consultar) {
		log.info("cargarListaCitas");
		limpiartabla();
		if (consultar) {
			try {
				listaTareas = QueryProyecto.obtenerListaTareaFecha("");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		

		for (int i = 0; i < listaTareas.size(); i++) { 


			modelTablaTareas.addRow(new Object[] { listaTareas.get(i).getIdBD()
					,listaIdProyectos.get(listaTareas.get(i).getProyecto())
					, listaIdStatus.get(listaTareas.get(i).getStatus())
					, listaTareas.get(i).getFechaDesarrollo()
					, listaIdRecursos.get(listaTareas.get(i).getRecurso())
					, listaTareas.get(i).getDescripcion()
					,listaTareas.get(i).getObservacion() });
			

		}
		
		tablaListaTareas.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 1 ||me.getClickCount() == 2) {     // to detect doble click events
	            	log.info("seleccionando en lista de tareas");
	            	//JOptionPane.showMessageDialog(null,"Aqui debe abrir el dialogo de las citas por cliente"); 
	            	
	               JTable target = (JTable)me.getSource();
	               int row = target.getSelectedRow(); // select a row
	               rowSeleccionada = row;
	              
	               
	               DatosProyecto datosProyecto;
				try {
					String idProyecto =  listaProyectosId.get(tablaListaTareas.getValueAt(row, 1).toString());
					datosProyecto = QueryProyecto.obtenerProyecto(idProyecto);
					//datosProyecto = QueryClienteProveedor.obtenerClienteProveedor(tablaListaCitas.getValueAt(row, 3).toString());
					DialogoDetallesProyecto dialogo = new DialogoDetallesProyecto(parent, datosProyecto, datosUsuario);
					cargarListaCitas(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            	
	              // String observacion = tablaListaCitas.getValueAt(row, 3).toString();
	              // textAreaObservacion.setText(observacion);
	              //okButton.setEnabled(false);
	               log.info("seleccionando en lista de citas - fin "+tablaListaTareas.getValueAt(row, 0).toString());
	              //JOptionPane.showMessageDialog(null,row+" "+column+" "+ table.getValueAt(row, column)); // get the value of a row and column.

	              
	            }
	         }
	      });
		log.info("cargarListaCitas - fin");
	}
	
	private void limpiartabla(){
		DefaultTableModel temp = (DefaultTableModel) tablaListaTareas.getModel();
	    int filas = tablaListaTareas.getRowCount();

	    for (int a = 0; filas > a; a++) {
	        temp.removeRow(0);
	    }
	}
	private void mostrarException(String mensajeException) {
		JOptionPane.showMessageDialog(null, "Error: "+mensajeException,"Mensaje de la aplicación",JOptionPane.ERROR_MESSAGE);
	}


}
