package ve.com.avss.proyectos.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultCaret;

import org.apache.log4j.Logger;

import com.toedter.calendar.JCalendar;

import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.proyectos.BD.ejecutores.QueryProyecto;
import ve.com.avss.proyectos.bean.DatosProyecto;
import ve.com.avss.proyectos.bean.DatosTarea;
import ve.com.avss.reportes.BD.ejecutores.QueryReporte;
import ve.com.avss.reportes.tarea.ReporteTotal;
import ve.com.avss.reportes.totales.bean.DatosReporte;
import ve.com.avss.ventas.util.UtilFormas;

public class DialogoDetallesProyecto extends AbstractDialogoBaseProyecto {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DialogoDetallesProyecto.class);
	private List<DatosTarea> listaTareas = new ArrayList<DatosTarea>();

	private DatosUsuario datosUsuario = new DatosUsuario();
	private DatosProyecto datosProyecto = new DatosProyecto();

	private int rowSeleccionada = 0;
	private String actionCommand = "";
	
	public DialogoDetallesProyecto(JFrame parent, DatosProyecto datosProyecto,DatosUsuario datosUsuario) throws Exception { 
		super(parent);
		log.info("Constructor - construyendo paneles e interfaz");

		this.datosUsuario = datosUsuario;
		this.datosProyecto = datosProyecto;
		contruirPanelesCampos();
		construirTablaTareas();
		cargarListaTareas();
		construirPanelBotones(" ", "Cerrar");
		//establecer datos del cliente
		eDescripcion.setText(eDescripcion.getText()+" "+datosProyecto.getDescripcion());
		eFechaDesarrollo.setText(eFechaDesarrollo.getText()+" "+datosProyecto.getFechaDesarrollo());
		eObservacion.setText(eObservacion.getText()+" "+datosProyecto.getObservacion());
		eStatus.setText(eStatus.getText()+" "+datosProyecto.getStatus());
		
		
		descripcion.setText(datosProyecto.getDescripcion());
		descripcion.setEnabled(false);
		fechaDesarrollo.setText(datosProyecto.getFechaDesarrollo());
		fechaDesarrollo.setEnabled(false);
		observacion.setText(datosProyecto.getObservacion());
		observacion.setEnabled(false);
		fechaCertificacion.setText(datosProyecto.getFechaCertificacion());
		fechaCertificacion.setEnabled(false);
		comboStatus.setSelectedItem(datosProyecto.getStatus());
		comboStatus.setEnabled(false);
		

		int pos = 0;
		jPanelIdentificacion.add(eDescripcion); 
		//jPanelIdentificacion.add(descripcion);
		jPanelInformacion.add(jPanelIdentificacion,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=0,gridwidth=1," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));
		
		++pos;
		jPanelApellido.add(eFechaDesarrollo);
		//jPanelApellido.add(fechaDesarrollo);
		jPanelInformacion.add(jPanelApellido,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=0,gridwidth=2," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));
		pos = 0;
		jPanelTelefono.add(eFechaCertificacion);
		//jPanelTelefono.add(fechaCertificacion);
		jPanelInformacion.add(jPanelTelefono,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=1,gridwidth=2," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));
		++pos;
		jPanelNombre.add(eObservacion);
		//jPanelNombre.add(observacion);
		jPanelInformacion.add(jPanelNombre,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=0,gridwidth=1," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));

		
		++pos;
		jPanelTipoClienteProveedor.add(eStatus);
		//jPanelTipoClienteProveedor.add(comboStatus);
		jPanelInformacion.add(jPanelTipoClienteProveedor,
				UtilFormas.setGridContraints(
						"gridx=" + pos + ",gridy=2,gridwidth=2," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));

		
		jPanelBuscarInformacion.add(btnCrearTarea);
		jPanelBuscarInformacion.add(btnActualizarTarea);
		jPanelBuscarInformacion.add(btnReportePDF);
		
		
		jPanelBuscarInformacion.setPreferredSize(new Dimension(200, 600));
		btnActualizarTarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.info("btnActualizarTarea");
				
				if (tablaListaTareas.getSelectedRows().length == 0) {
					 JOptionPane.showMessageDialog(parent,"Debe seleccionar una tarea","Alerta",JOptionPane.WARNING_MESSAGE); 
					return;
				}
				// id de tarea
				String idTarea = tablaListaTareas.getValueAt(rowSeleccionada, 0).toString();
				// status
				String status = listaStatusId.get(comboStatusTareaAct.getSelectedItem());
				
				//fecha
				Date date = calendarioAct.getDate();
				java.sql.Date fechaActualizar = new java.sql.Date(date.getTime());
				//descripcion
				String descripcion = textAreaDescripcion.getText();
				//asignado
				String asignado = listaRecursosId.get(comboRecursoTareaAct.getSelectedItem());
				//observacion
				String observacion = textAreaObservacion.getText();
				
				
				 
				try {
					DatosTarea tarea = new DatosTarea();
					tarea.setIdBD(idTarea);
					tarea.setDescripcion(descripcion);
					tarea.setFechaDesarrollo(fechaActualizar+"");
					tarea.setObservacion(observacion);
					tarea.setRecurso(asignado);
					tarea.setStatus(status);
					int result = QueryProyecto.actualizarTarea(tarea);
					if (result == 1) {
						
					}else {
						
					}
					cargarListaTareas();
					JOptionPane.showMessageDialog(parent,"Se actualizó correctamente","Alerta",JOptionPane.INFORMATION_MESSAGE);
					
					textAreaDescripcion.setText("");
					textAreaObservacion.setText("");
					comboRecursoTareaAct.setSelectedIndex(0);
					comboStatusTareaAct.setSelectedIndex(0);
				
					
				} catch (Exception e) {
					log.error("error "+e.getMessage());
				}
				log.info("btnActualizarTarea - fin");
			}
		});

		btnCrearTarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.info("crear tarea");
				
				 /*
				  * descripcion
				  * fecha desarrollo
				  * recurso
				  * status
				  * observacion
				  * */
				try {
				//JOptionPane.showMessageDialog(parent,"Logica para programar cita","Alerta",JOptionPane.WARNING_MESSAGE); 
				
				JPanel panel = new JPanel(new GridLayout(2, 1));
				//descripcion de la tarea
				JPanel panelDescripcionProx = new JPanel();
				panelDescripcionProx.add(eDescripcionTarea);
				panelDescripcionProx.add(scrollPaneDescripcionTarea);
				panel.add(panelDescripcionProx);
				//fecha de desarrollo
				JPanel panelFechaProx = new JPanel();
				panelFechaProx.add(eFechaTarea);
				panel.add(panelFechaProx);
				JCalendar calendario = new JCalendar();
				calendario.setTodayButtonVisible(true);
				calendario.setTodayButtonText("Hoy Día");
				panel.add(calendario);
				//recurso
				JPanel panelRecurso = new JPanel();
				panelRecurso.add(eRecursoTarea);
				panelRecurso.add(comboRecursoTarea);
				panel.add(panelRecurso);
				//status
				JPanel panelStatus = new JPanel();
				panelStatus.add(eStatusTarea);
				panelStatus.add(comboStatusTarea);
				panel.add(panelStatus);
				//observacion
				JPanel panelObservacionProx = new JPanel();
				panelObservacionProx.add(eObservacionTarea);
				panelObservacionProx.add(scrollPaneObservacionTarea);
				panel.add(panelObservacionProx);

				BoxLayout boxlayoutPanel = new BoxLayout(panel, BoxLayout.Y_AXIS);
				panel.setLayout(boxlayoutPanel);

				int resp = JOptionPane.showConfirmDialog(parent, panel, "Crear tarea", JOptionPane.YES_NO_OPTION);
				if (resp  == 1) {
					return;
				}
				Date date = calendario.getDate();
				java.sql.Date sDate = new java.sql.Date(date.getTime());
				
				DatosTarea tarea = new DatosTarea();
				tarea.setDescripcion(descripcionTarea.getText());
				tarea.setObservacion(observacionTarea.getText());
				tarea.setFechaDesarrollo(sDate+"");
				tarea.setStatus(listaStatusId.get(comboStatusTarea.getSelectedItem()));
				tarea.setRecurso(listaRecursosId.get(comboRecursoTarea.getSelectedItem()));
				tarea.setProyecto(datosProyecto.getIdBD());
				
				int result = QueryProyecto.registrarTarea(tarea);
				
				if (result == 1) {
					descripcionTarea.setText("");
					observacionTarea.setText("");
					comboRecursoTarea.setSelectedIndex(0);
					comboStatusTarea.setSelectedIndex(0);
					cargarListaTareas();
				}
					
				
				} catch (Exception e) {
					log.error("error "+e.getMessage());
				}
				log.info("programar cita - fin");
			}
		});
		
		
		btnReportePDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.info("crear reporte pdf");
				
				 /*
				  * descripcion
				  * fecha desarrollo
				  * recurso
				  * status
				  * observacion
				  * */
				try {
				//JOptionPane.showMessageDialog(parent,"Logica para programar cita","Alerta",JOptionPane.WARNING_MESSAGE); 
				
				JPanel panel = new JPanel(new GridLayout(2, 1));
				//Armar condicion de consulta
				
				JRadioButton radioTodas = new JRadioButton("Todas");    
				JRadioButton radioIgualFecha = new JRadioButton("Igual a "); 
				JRadioButton radioMayorFecha = new JRadioButton("Mayor que "); 
				
				ButtonGroup groupRadio = new ButtonGroup();    
				groupRadio.add(radioTodas);
				groupRadio.add(radioIgualFecha);
				groupRadio.add(radioMayorFecha);
				
				
				JPanel panelCondicion = new JPanel();
				panelCondicion.add(radioTodas);
				panelCondicion.add(radioIgualFecha);
				panelCondicion.add(radioMayorFecha);
				panel.add(panelCondicion);
				//fecha de desarrollo
				JPanel panelFechaProx = new JPanel();
				panelFechaProx.add(eFechaTarea);
				panel.add(panelFechaProx);
				JCalendar calendario = new JCalendar();
				calendario.setTodayButtonVisible(true);
				calendario.setTodayButtonText("Hoy Día");
				panel.add(calendario);
				
				// agregando listener de los radiobutton
				
				radioTodas.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			        	calendario.setEnabled(false);
			        	calendario.setTodayButtonVisible(false);

			        }
			    });
				
				radioIgualFecha.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			        	calendario.setEnabled(true);
			        	calendario.setTodayButtonVisible(true);
			        }
			    });
				
				radioMayorFecha.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			        	calendario.setEnabled(true);
			        	calendario.setTodayButtonVisible(true);
			        }
			    });
				

				BoxLayout boxlayoutPanel = new BoxLayout(panel, BoxLayout.Y_AXIS);
				panel.setLayout(boxlayoutPanel);

				int resp = JOptionPane.showConfirmDialog(parent, panel, "Crear tarea", JOptionPane.YES_NO_OPTION);
				if (resp  == 1) {
					return;
				}
				Date date = calendario.getDate();
				java.sql.Date sDate = new java.sql.Date(date.getTime());
				
				String condicionOperador = "";
				if (radioIgualFecha.isSelected()) {
					condicionOperador = "=";
					
				} else if (radioMayorFecha.isSelected()) {
					condicionOperador = ">";
				}
					
				
				List<DatosTarea> listaTareasReporte = QueryReporte.obtenerListaTareasPorProyecto(datosProyecto.getIdBD(), condicionOperador, sDate+"")	;
				//datos de reporte
				DatosReporte datosReporte = new DatosReporte();
				
				//datos de proyecto
				datosReporte.setDatosProyecto(datosProyecto);
				//lista de tareas
				datosReporte.setListaTareas(listaTareasReporte);
				datosReporte.setFechaReporte(sDate+"");
				//instanciar PDF
				ReporteTotal reporte = new ReporteTotal(datosReporte);
				boolean generadorPDF = reporte.createPdf();
				if (generadorPDF) {
					JOptionPane.showMessageDialog(parent,"Se generó correctamente el reporte","Notificación",JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(parent,"No se pudo generar el reporte","Notificación",JOptionPane.ERROR_MESSAGE);
				}
				} catch (Exception e) {
					log.error("error "+e.getMessage());
				}
				log.info("crear reporte pdf - fin");
			}
		});
		
		
		jContentPaneSuperior.add(jPanelInformacion);
		jContentPaneSuperior.add(jContentPaneDatosFactura);
		jPanelTablaCitas.add(tablaCitas,
				UtilFormas.setGridContraints("gridx=0,gridy=0,gridwidth=2," + "fill=" + GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(0, 0, 0, 0)));

		
		textAreaObservacion = new JTextArea();
		textAreaObservacion.setName("observacion");
		textAreaObservacion.setBackground(Color.WHITE);
		textAreaObservacion.setText("");
		textAreaObservacion.setEditable(true);
		textAreaObservacion.setAutoscrolls(true);
		textAreaObservacion.setLineWrap(true);
		textAreaObservacion.setWrapStyleWord(true);
		textAreaObservacion.setFont(new Font("Courier New", Font.PLAIN, 17));
		
		scrollPaneObservacion = new JScrollPane(textAreaObservacion,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneObservacion.setPreferredSize(new Dimension(200,150));

		
		textAreaDescripcion = new JTextArea();
		textAreaDescripcion.setName("observacion");
		textAreaDescripcion.setBackground(Color.WHITE);
		textAreaDescripcion.setText("");
		textAreaDescripcion.setEditable(true);
		textAreaDescripcion.setAutoscrolls(true);
		textAreaDescripcion.setLineWrap(true);
		textAreaDescripcion.setWrapStyleWord(true);
		textAreaDescripcion.setFont(new Font("Courier New", Font.PLAIN, 17));
		scrollPaneDescripcion = new JScrollPane(textAreaDescripcion,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneDescripcion.setPreferredSize(new Dimension(200, 150));
		//panel de datos para actualizar
		
		calendarioAct = new JCalendar();
		calendarioAct.setTodayButtonVisible(true);
		calendarioAct.setTodayButtonText("Hoy Día");
		
		JPanel panelDatos = new JPanel();
		panelDatos.add(scrollPaneDescripcion);
		panelDatos.add(scrollPaneObservacion);
		panelDatos.add(calendarioAct);
		
		BoxLayout boxlayoutPanelCentral = new BoxLayout(panelDatos, BoxLayout.Y_AXIS);
		panelDatos.setLayout(boxlayoutPanelCentral);
		panelDatos.add(comboRecursoTareaAct);
		panelDatos.add(comboStatusTareaAct);
		
		
	    jContentPaneCentral.add(panelDatos);
	    jContentPaneCentral.add(jPanelTablaCitas);
		//jContentPaneCentral.setBackground(Color.BLUE);

		jContentPane.add(jContentPaneSuperior, BorderLayout.NORTH);
		jContentPane.add(jContentPaneCentral, BorderLayout.CENTER);
		jContentPane.add(botonesPanel, BorderLayout.SOUTH);
		jContentPane.add(jPanelBuscarInformacion, BorderLayout.EAST);

		JScrollPane jScrollPane = new JScrollPane();

		jScrollPane.setViewportView(jContentPane);

		getContentPane().add(jScrollPane);
		pack();
		setAlwaysOnTop(false);
		setLocationRelativeTo(null);
		setTitle("Detalle del proyecto");
		setResizable(true);
		setVisible(true);
	}

	private void cargarListaTareas() {
		log.info("cargarListaTareas");
		limpiartabla();
		try {
			listaTareas = QueryProyecto.obtenerListaTarea(this.datosProyecto.getIdBD());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < listaTareas.size(); i++) { 


			modelTablaTareas.addRow(new Object[] { listaTareas.get(i).getIdBD()
					,listaIdStatus.get(listaTareas.get(i).getStatus())
					, listaTareas.get(i).getFechaDesarrollo()
					,listaTareas.get(i).getDescripcion()
					, listaIdRecursos.get(listaTareas.get(i).getRecurso())
					,listaTareas.get(i).getObservacion() });
			

		}
		
		tablaListaTareas.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 1 ||me.getClickCount() == 2) {     // to detect doble click events
	            	log.info("seleccionando en lista de citas");
	            	
	               JTable target = (JTable)me.getSource();
	               int row = target.getSelectedRow(); // select a row
	               rowSeleccionada = row;
	              
	               String observacion = tablaListaTareas.getValueAt(row, 5).toString();
	               textAreaObservacion.setText(observacion);
	          
	               
	               
	               
	               String descripcion = tablaListaTareas.getValueAt(row, 3).toString();
	               textAreaDescripcion.setText(descripcion);
	               
	               String status = tablaListaTareas.getValueAt(row, 1).toString();
	               
	               String recurso = tablaListaTareas.getValueAt(row, 4).toString();
	               
	               String fecha = tablaListaTareas.getValueAt(row, 2).toString();
	               String[] newFecha = fecha.split("-");
	              
	               
	               Calendar calendario = new GregorianCalendar(new Integer(newFecha[0]),(new Integer(newFecha[1])-1),new Integer(newFecha[2]));
	               calendarioAct.setDate(calendario.getTime());
	               
	               comboRecursoTareaAct.setSelectedItem(recurso);
	               comboStatusTareaAct.setSelectedItem(status);
	               
	              okButton.setEnabled(false);
	               log.info("seleccionando en lista de citas - fin "+tablaListaTareas.getValueAt(row, 0).toString());
	              //JOptionPane.showMessageDialog(null,row+" "+column+" "+ table.getValueAt(row, column)); // get the value of a row and column.

	              
	            }
	         }
	      });
		log.info("cargarListaTareas - fin");
	}


	private void construirTablaTareas() {
		log.info("construirTablaCitas");
		// Column Names
		String[] nombreColumnaProductos = {"id", "Status","Fecha", "Descripción", "Asignado", "Observación" };

		// inicializamos el JTable
		modelTablaTareas.setColumnIdentifiers(nombreColumnaProductos);
		tablaListaTareas = new JTable();
		tablaListaTareas.setModel(modelTablaTareas);
		//tablaListaCitas.setBounds(60, 60, 60, 60);
		/*ocualtamos la columna id*/
		TableColumn column = tablaListaTareas.getColumnModel().getColumn(0);
		//id
	    column.setMinWidth(0);
	    column.setMaxWidth(0);
	    column.setWidth(0);
	    column.setPreferredWidth(0);
	    //status
	    column = tablaListaTareas.getColumnModel().getColumn(1);
	    column.setMinWidth(80);
	    column.setMaxWidth(100);
	    column.setWidth(80);
	    column.setPreferredWidth(100);
	    //fecha
	    column = tablaListaTareas.getColumnModel().getColumn(2);
	    column.setMinWidth(80);
	    column.setMaxWidth(100);
	    column.setWidth(80);
	    column.setPreferredWidth(100);
	    // asignado
	    column = tablaListaTareas.getColumnModel().getColumn(4);
	    column.setMinWidth(80);
	    column.setMaxWidth(120);
	    column.setWidth(80);
	    column.setPreferredWidth(120);

		// agregamos al JScrollPane
		JScrollPane sp = new JScrollPane(tablaListaTareas);
		sp.setPreferredSize(new Dimension(900, 700));
		tablaCitas.add(sp);
		log.info("construirTablaCitas - fin");
	}

	


	private void contruirPanelesCampos() {

		String campo = "";

		/* armar campo identificacion */
		campo = "descripcionTarea";
		armarEtiquetas(campo);
		armarCampos(campo);
		
		/* armar campo identificacion */
		campo = "descripcion";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo nombre */
		campo = "observacionTarea";
		armarEtiquetas(campo);
		armarCampos(campo);
		
		/* armar campo nombre */
		campo = "observacion";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo apellido */
		campo = "fechaDesarrollo";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo correo */
		campo = "fechaCertificacion";
		armarEtiquetas(campo);
		armarCampos(campo);


		/* armar campo tipo de cliente proveedor */
		campo = "status";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo tipo de cliente proveedor */
		campo = "statusTarea";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo tipo de cliente proveedor */
		campo = "recursoTarea";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo tipo factura */
		campo = "guardarCita";
		armarCampos(campo);
		/* armar campo tipo factura */
		campo = "crearTarea";
		armarCampos(campo);
		/*armar boton de generar reporte de tareas*/
		campo = "generarReportePDF";
		armarCampos(campo);
		/* armar campo tabla de productos */
		campo = "tablaProductos";
		armarEtiquetas(campo);
		
		/* armar campo fecha */
		campo = "fechaProx";
		armarEtiquetas(campo);
		armarCampos(campo);
		
		/* armar campo observacion */
		campo = "observacionProx";
		armarEtiquetas(campo);
		armarCampos(campo);


		boxlayoutBuscarInfomracion = new BoxLayout(jPanelBuscarInformacion, BoxLayout.Y_AXIS);
		jPanelBuscarInformacion.setLayout(boxlayoutBuscarInfomracion);
		//jPanelBuscarInformacion.setBorder(new EmptyBorder(new Insets(10, 5, 5, 10)));

	}
	

	
	
	
	@Override
	protected boolean actionOK(String actionCommandButton) {
		log.info("registrar");
		if (observacion.getText().equals("")) {
			observacion.setBackground(Color.CYAN);
			observacion.requestFocusInWindow();
			return false;
		}
		if (comboTipoFactura.getSelectedItem().equals("")) {
			comboTipoFactura.setBackground(Color.CYAN);
			comboTipoFactura.setPopupVisible(true);
			return false;
		}
		
		if (tablaListaTareas.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "Debe agregar al menos 1 producto","Mensaje de la aplicación", JOptionPane.WARNING_MESSAGE);
			return false;
		}
			
		
		
		actionCommand = "registrar";
		log.info("registrar - fin");
		return true;
	}

	@Override
	protected boolean actionOK() {
		log.info("registrar");
		if (observacion.getText().equals("")) {
			observacion.setBackground(Color.CYAN);
			observacion.requestFocusInWindow();
			return false;
		}
		if (comboTipoFactura.getSelectedItem().equals("")) {
			comboTipoFactura.setBackground(Color.CYAN);
			comboTipoFactura.setPopupVisible(true);
			return false;
		}
		

		
		actionCommand = "registrar";
		log.info("registrar - fin");
		return true;
	}
	
	private void limpiartabla(){
		DefaultTableModel temp = (DefaultTableModel) tablaListaTareas.getModel();
	    int filas = tablaListaTareas.getRowCount();

	    for (int a = 0; filas > a; a++) {
	        temp.removeRow(0);
	    }
	}
	
	
	

	public DatosUsuario getDatosUsuario() {
		return datosUsuario;
	}

	public void setDatosUsuario(DatosUsuario datosUsuario) {
		this.datosUsuario = datosUsuario;
	}



	public String getActionCommand() {
		return actionCommand;
	}

	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}

	

}
