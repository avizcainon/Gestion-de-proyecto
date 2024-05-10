package ve.com.avss.clientes.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.toedter.calendar.JCalendar;

import ve.com.avss.clientes.BD.ejecutores.QueryClienteProveedor;
import ve.com.avss.clientes.bean.DatosClientesProveedor;
import ve.com.avss.clientes.interfaz.DialogoClienteProveedor;
import ve.com.avss.proyectos.BD.ejecutores.QueryProyecto;
import ve.com.avss.proyectos.bean.DatosProyecto;
import ve.com.avss.proyectos.bean.DatosStatus;
import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.util.AppJButton;
import ve.com.avss.ventas.util.CONSTANTES_INTERACCION;
import ve.com.avss.ventas.util.Util;
import ve.com.avss.ventas.util.UtilFormas;

public abstract class AbstractDialogoBaseRecurso extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AbstractDialogoBase.class);
	/** Command string para la accion de "Cancelar". */
	public static final String CMD_CANCEL = "cmd.cancel";
	/** Command string para la accion de "Aceptar". */
	public static final String CMD_OK = "cmd.ok";
	/** Command string para la accion de "Aceptar". */
	public static final String CMD_NOTA_ENTREGA = "cmd.notaEntrega";
	/** Command string para la accion de "Ocultar procesando". */
	public static final String CMD_PROCESS = "cmd.process";
	/** JFrame base del dialogo */
	protected JFrame parent = null;
	/** Indicador de acción. */ 
	protected boolean buttonFlag;
	protected boolean closeWindow = false;
	/** Botón aceptar. */
	protected AppJButton okButton = null;
	/** Botón cancelar. */
	protected JButton cancelButton = new JButton();
	/** Botón nota de entrega de factura. */
	protected JButton notaEntregaButton = new JButton();
	/*datos para la interfaz*/
	protected JPanel jContentPane = new JPanel(new BorderLayout());
	protected JPanel jContentPaneSuperior = new JPanel();
	protected JPanel jContentPaneCentral = new JPanel(new FlowLayout());
	protected JPanel jContentPaneInferior = new JPanel(new GridBagLayout());
	
	
	/** Panel de Campos. */
	protected JPanel camposPanel = new JPanel();
	/** Panel de ayuda. */
	protected JLabel ayudaPanel = new JLabel();
	/** Panel de Botones. */
	protected JPanel botonesPanel = new JPanel();
	
	/** datos de tipo de pago */
	protected JPanel jPanelTipoPago = new JPanel();
	
	protected JLabel eFechaTarea = new JLabel();
	protected JTextField fechaTarea = null;
	//descripcion
	protected JLabel eDescripcionTarea = new JLabel();
	protected JTextArea descripcionTarea = null;
	protected JScrollPane scrollPaneDescripcionTarea= null;
	//status
	protected JLabel eStatusTarea = new JLabel();
	protected JComboBox comboStatusTarea = new JComboBox();
	//recurso
	protected JLabel eProyectoTarea = new JLabel();
	protected JComboBox comboProyectoTarea = new JComboBox();
	//observacion
	protected JLabel eObservacionTarea = new JLabel();
	protected JTextArea observacionTarea = null;
	protected JScrollPane scrollPaneObservacionTarea= null;
//datos paraactualizar
	
	protected JComboBox comboStatusTareaAct = new JComboBox();
	protected JComboBox comboProyectosTareaAct = new JComboBox();
	protected JCalendar calendarioAct = new JCalendar();

	
	/*datos clientes proveedor*/
	protected JPanel jPanelInformacion = new JPanel(new GridLayout(2,3));
	protected JPanel jPanelIdentificacion = new JPanel();
	protected JPanel jPanelNombre = new JPanel();
	protected JPanel jPanelApellido = new JPanel();
	protected JPanel jPanelTelefono = new JPanel();
	protected JPanel jPanelDireccion = new JPanel();
	protected JPanel jPanelTipoClienteProveedor = new JPanel();
	
	protected JLabel eCorreo = new JLabel();
	protected JTextField observacion = null;
	protected JLabel eDescripcion = new JLabel();
	protected JTextField descripcion = null;
	protected JLabel eTelefono = new JLabel();
	protected JTextField fechaDesarrollo = null;

	protected JLabel eFechaCertificacion = new JLabel();
	protected JTextField fechaCertificacion = null;

	protected JLabel eStatus = new JLabel();
	protected JComboBox comboStatus = new JComboBox();
	
	
	/*Datos de productos*/
	protected JLabel eTablaCitas= new JLabel();
	protected JPanel jPanelTablaCitas = new JPanel(new FlowLayout());
	protected JPanel tablaCitas = new JPanel();
	protected DefaultTableModel modelTablaTareas = new DefaultTableModel();
	protected JTable tablaListaTareas = null;
	
	 
	/*datos buscar informacion*/
	protected JPanel jPanelBuscarInformacion= new JPanel();
	protected AppJButton btnActualizarTarea = null;
	protected AppJButton btnCrearTarea = null;
	protected AppJButton btnReportePDF = null;

	protected JTextArea textAreaObservacion = null;  
	protected JScrollPane scrollPaneObservacion= null;
	
	protected JTextArea textAreaDescripcion = null;  
	protected JScrollPane scrollPaneDescripcion= null;

	protected JComboBox comboTipoFactura = new JComboBox();
	protected BoxLayout boxlayoutBuscarInfomracion = null; 
	/*Datos de factura*/
	protected JLabel eNumeroFactura = new JLabel();
	protected JLabel eFechaFactura = new JLabel();
	protected JPanel jContentPaneDatosFactura = new JPanel();
	protected JLabel eOperadorFactura = new JLabel();
	protected List<DatosStatus> listaStatus = null;
	protected List<DatosProyecto> listaProyectos = null;
	 protected Map<String, String> listaStatusId = new HashMap<String, String>();
	 protected Map<String, String> listaIdStatus = new HashMap<String, String>();
	 protected Map<String, String> listaProyectosId = new HashMap<String, String>();
	 protected Map<String, String> listaIdProyectos = new HashMap<String, String>();
	 protected JLabel eNombreFormulario = new JLabel();
	public AbstractDialogoBaseRecurso(JFrame parent) {
		super(parent, true);
		log.info("Constructor");
		setIconImage(Util.getIconImage());
		try {
			this.parent = parent;
			listaProyectos = QueryProyecto.obtenerListaProyectosActivos();
			listaStatus = QueryProyecto.obtenerStatus();
		} catch (Exception e) {
			return;
		}
		comboStatusTareaAct.addItem("");
		comboProyectosTareaAct.addItem("");
		comboStatusTarea.addItem("");
		comboProyectoTarea.addItem("");
		for (int i = 0; i < listaStatus.size(); i++) {
			listaStatusId.put(listaStatus.get(i).getStatus(), listaStatus.get(i).getIdBD());
			listaIdStatus.put(listaStatus.get(i).getIdBD(),listaStatus.get(i).getStatus());
			comboStatusTareaAct.addItem(listaStatus.get(i).getStatus());
			
		}
		
		for (int i = 0; i < listaProyectos.size(); i++) {
			listaProyectosId.put(listaProyectos.get(i).getDescripcion(), listaProyectos.get(i).getIdBD());
			listaIdProyectos.put(listaProyectos.get(i).getIdBD(),listaProyectos.get(i).getDescripcion());
			comboProyectosTareaAct.addItem(listaProyectos.get(i).getDescripcion());
			
		}
		
		


		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				windowAction(CMD_CANCEL);
			}
		});
	}
	
	protected void armarCampos(String campo) {

		switch (campo) {
		case "fechaProx":
			
			fechaTarea = new JTextField();
			fechaTarea.setName("fechaProx");
			fechaTarea.setBackground(Color.WHITE);
			fechaTarea.setPreferredSize(new Dimension(110, 25));
			fechaTarea.setText("");
			

			break;
			case "observacionTarea":
			
			observacionTarea = new JTextArea();
			observacionTarea.setName("fechaProx");
			observacionTarea.setBackground(Color.WHITE);
			observacionTarea.setLineWrap(true);
			observacionTarea.setWrapStyleWord(true);
			observacionTarea.setText("");
			observacionTarea.setEditable(true);
			observacionTarea.setFont(new Font("Courier New", Font.PLAIN, 17));
			scrollPaneObservacionTarea = new JScrollPane(observacionTarea);
			//scrollPaneObservacion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPaneObservacionTarea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPaneObservacionTarea.setPreferredSize(new Dimension(600, 110));

			break;
			case "descripcionTarea":
				
				descripcionTarea = new JTextArea(10, 50);
				descripcionTarea.setName("descripcionTarea");
				descripcionTarea.setBackground(Color.WHITE);
				descripcionTarea.setLineWrap(true);
				descripcionTarea.setWrapStyleWord(true);
				descripcionTarea.setText("");
				descripcionTarea.setEditable(true);
				descripcionTarea.setFont(new Font("Courier New", Font.PLAIN, 17));
				scrollPaneDescripcionTarea = new JScrollPane(descripcionTarea);
				//scrollPaneObservacion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollPaneDescripcionTarea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scrollPaneDescripcionTarea.setPreferredSize(new Dimension(600, 110));

				break;

		case "observacion":
			observacion = new JTextField();
			observacion.setName("identificacion");
			observacion.setBackground(Color.WHITE);
			observacion.setPreferredSize(new Dimension(110, 25));
			observacion.setOpaque(true);
			observacion.setText("");

			break;
		case "descripcion":
			descripcion = new JTextField();
			descripcion.setName("nombreCP");
			descripcion.setBackground(Color.WHITE);
			descripcion.setPreferredSize(new Dimension(110, 25));
			descripcion.setOpaque(true);
			descripcion.setText("");
			break;
		case "fechaDesarrollo":
			fechaDesarrollo = new JTextField();
			fechaDesarrollo.setName("apellidoCP");
			fechaDesarrollo.setBackground(Color.WHITE);
			fechaDesarrollo.setPreferredSize(new Dimension(110, 25));
			fechaDesarrollo.setOpaque(true);
			fechaDesarrollo.setText("");
			break;
		case "fechaCertificacion":
			fechaCertificacion = new JTextField();
			fechaCertificacion.setName("telefonoCP");
			fechaCertificacion.setBackground(Color.WHITE);
			fechaCertificacion.setPreferredSize(new Dimension(110, 25));
			fechaCertificacion.setOpaque(true);
			fechaCertificacion.setText("");
			break;

		case "status":
			comboStatus.setName("tipoCP");
			comboStatus.setBackground(Color.WHITE);
			comboStatus.setPreferredSize(new Dimension(100, 28));
			((JComboBox) comboStatus).addItem("");
			for (int i = 0; i < listaStatus.size(); i++) {
				((JComboBox) comboStatus).addItem(listaStatus.get(i));
			}
			
			break;
		case "statusTarea":
			comboStatusTarea.setName("tipoCP");
			comboStatusTarea.setBackground(Color.WHITE);
			comboStatusTarea.setPreferredSize(new Dimension(100, 28));
			((JComboBox) comboStatus).addItem("");
			for (int i = 0; i < listaStatus.size(); i++) {
				((JComboBox) comboStatusTarea).addItem(listaStatus.get(i).getStatus());
			}
			break;
		case "recursoTarea":
			comboProyectoTarea.setName("tipoCP");
			comboProyectoTarea.setBackground(Color.WHITE);
			comboProyectoTarea.setPreferredSize(new Dimension(100, 28));
			((JComboBox) comboStatus).addItem("");
			for (int i = 0; i < listaProyectos.size(); i++) {
				((JComboBox) comboProyectoTarea).addItem(listaProyectos.get(i).getDescripcion());
			}
			break;
		case "guardarCita":
			btnActualizarTarea = new AppJButton("primary", "subproceso");
			btnActualizarTarea = btnActualizarTarea.crearBoton();
			btnActualizarTarea.setName("actualizarTarea");
			btnActualizarTarea.setPreferredSize(new Dimension(CONSTANTES_INTERACCION.ANCHO_BOTON, CONSTANTES_INTERACCION.ALTO_BOTON));
			btnActualizarTarea.setOpaque(true);
			btnActualizarTarea.setText("Actualizar tarea");
			btnActualizarTarea.setIcon(DatosConfiguracion.iBuscarClienteProveedor);
			btnActualizarTarea.setHorizontalTextPosition(SwingConstants.CENTER);
			btnActualizarTarea.setVerticalTextPosition(SwingConstants.BOTTOM);
			break;
		case "generarReportePDF":
			btnReportePDF = new AppJButton("primary", "subproceso");
			btnReportePDF = btnActualizarTarea.crearBoton();
			btnReportePDF.setName("generarReportePDF");
			btnReportePDF.setPreferredSize(new Dimension(CONSTANTES_INTERACCION.ANCHO_BOTON, CONSTANTES_INTERACCION.ALTO_BOTON));
			btnReportePDF.setOpaque(true);
			btnReportePDF.setText("Generar PDF");
			btnReportePDF.setIcon(DatosConfiguracion.iReporte);
			btnReportePDF.setHorizontalTextPosition(SwingConstants.CENTER);
			btnReportePDF.setVerticalTextPosition(SwingConstants.BOTTOM);
			break;
		
		case "crearTarea":
			btnCrearTarea = new AppJButton("primary", "subproceso");
			btnCrearTarea = btnActualizarTarea.crearBoton();
			btnCrearTarea.setName("crearTarea");
			btnCrearTarea.setPreferredSize(new Dimension(CONSTANTES_INTERACCION.ANCHO_BOTON, CONSTANTES_INTERACCION.ALTO_BOTON));
			btnCrearTarea.setOpaque(true);
			btnCrearTarea.setText("Crear tarea");
			btnCrearTarea.setIcon(DatosConfiguracion.iConfiguracion);
			btnCrearTarea.setHorizontalTextPosition(SwingConstants.CENTER);
			btnCrearTarea.setVerticalTextPosition(SwingConstants.BOTTOM);
			break;
		default:
			break;
		}
	}

	protected void armarEtiquetas(String campo) {

		switch (campo) {
		case "fechaProx":
			eFechaTarea.setPreferredSize(new java.awt.Dimension(250, 15));
			eFechaTarea.setText("Fecha estimada");
			break;
		case "observacionProx":
			eObservacionTarea.setPreferredSize(new java.awt.Dimension(250, 15));
			eObservacionTarea.setText("Observación");
			break;
		case "observacion":
			eCorreo.setPreferredSize(new java.awt.Dimension(250, 15));
			eCorreo.setText("Correo: ");
			break;
		case "observacionTarea":
			eObservacionTarea.setPreferredSize(new java.awt.Dimension(250, 15));
			eObservacionTarea.setText("Observación: ");
			break;
		case "descripcion":
			eDescripcion.setPreferredSize(new java.awt.Dimension(250, 15));
			eDescripcion.setText("Proyecto: ");
			break;
		case "descripcionTarea":
			eDescripcionTarea.setPreferredSize(new java.awt.Dimension(200, 15));
			eDescripcionTarea.setText("Tarea: ");
			break;
		case "fechaDesarrollo":
			eTelefono.setPreferredSize(new java.awt.Dimension(200, 15));
			eTelefono.setText("Teléfono: ");
			break;
		case "fechaCertificacion":
			eFechaCertificacion.setPreferredSize(new java.awt.Dimension(200, 15));
			eFechaCertificacion.setText("");
			break;

		case "status":
			eStatus.setPreferredSize(new java.awt.Dimension(200, 15));
			eStatus.setText("");
			break;
		case "statusTarea":
			eStatusTarea.setPreferredSize(new java.awt.Dimension(70, 15));
			eStatusTarea.setText("Status");
			break;
		case "recursoTarea":
			eProyectoTarea.setPreferredSize(new java.awt.Dimension(70, 15));
			eProyectoTarea.setText("Proyecto:");
			break;
		case "tablaProductos":
			eTablaCitas.setPreferredSize(new java.awt.Dimension(140, 15));
			eTablaCitas.setText("Productos");
			break;
		

		
		}

	}
	
	protected DatosClientesProveedor buscarRegistrarClienteProveedor(String identificacion) throws Exception {
		DatosClientesProveedor datosClienteProveedor = null;
		try {
			datosClienteProveedor = QueryClienteProveedor.obtenerClienteProveedor(identificacion);
		} catch (Exception e) {
			log.info(e);
			throw new Exception(e.getMessage());
		}
		if (datosClienteProveedor == null) {
			
			JOptionPane.showMessageDialog(parent, "No se encotró cliente con esa identificación", "Alerta",
					JOptionPane.WARNING_MESSAGE);
			int n = JOptionPane.showConfirmDialog(parent,
	                "¿Desea registrar al Cliente / Proveedor?" ,
	                CONSTANTES_INTERACCION.IMPRIMIR_DIALOGO,JOptionPane.YES_NO_OPTION);
			
			if (n == JOptionPane.NO_OPTION) {
				return datosClienteProveedor;
			}else {
				DialogoClienteProveedor dClienteProveedor = null;
				
				dClienteProveedor = new DialogoClienteProveedor(parent);
				identificacion = dClienteProveedor.getDatosClienteProveedor().getIdentificacion();
				
				if (!identificacion.equals("")) {
					datosClienteProveedor = QueryClienteProveedor.obtenerClienteProveedor(identificacion);
				}
				
				dClienteProveedor.removeAll();
				dClienteProveedor = null;
			}
			
			
		}
		return datosClienteProveedor;
		
		
		
	}
	
	protected String obtenerFechaActual() {
		log.info("obtenerFechaActual");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String fecha = dtf.format(LocalDateTime.now());
		 
		return fecha;
	}



	protected void construirPanelBotones(String textoPrimerBoton, String textoSegundoBoton) {

		// Botón Aceptar
		okButton = new AppJButton("", "");
		okButton = okButton.crearBoton();
		okButton.setText(textoPrimerBoton);
		okButton.setActionCommand(CMD_OK);
		okButton.setIcon(DatosConfiguracion.iAceptar);
		okButton.setEnabled(false);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				windowAction(event);
			}
		});
		okButton.setVerifyInputWhenFocusTarget(true);

		// Botón Cancelar
		cancelButton = new JButton();
		cancelButton.setText(textoSegundoBoton);
		cancelButton.setVerifyInputWhenFocusTarget(false);
		cancelButton.setActionCommand(CMD_CANCEL);
		cancelButton.setIcon(DatosConfiguracion.iCancelar);
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				windowAction(event);
			}
		});

		botonesPanel.setLayout(new GridBagLayout());
		botonesPanel.add(okButton,
				UtilFormas.setGridContraints("gridx=0,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
		botonesPanel.add(cancelButton,
				UtilFormas.setGridContraints("gridx=1,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
	}
	
	protected void construirPanelBotones(String textoBotones[]) {

		// Botón Aceptar
		okButton = new AppJButton("", "");
		okButton = okButton.crearBoton();
		okButton.setText(textoBotones[0]);
		okButton.setActionCommand(CMD_OK);
		okButton.setIcon(DatosConfiguracion.iAceptar);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				windowAction(event);
			}
		});
		okButton.setVerifyInputWhenFocusTarget(true);

		// Botón Cancelar
		cancelButton = new JButton();
		cancelButton.setText(textoBotones[1]);
		cancelButton.setVerifyInputWhenFocusTarget(false);
		cancelButton.setActionCommand(CMD_CANCEL);
		cancelButton.setIcon(DatosConfiguracion.iCancelar);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				windowAction(event);
			}
		});
		
		// Botón actualizar
		notaEntregaButton = new JButton();
		notaEntregaButton.setText(textoBotones[2]);
		notaEntregaButton.setVerifyInputWhenFocusTarget(false);
		notaEntregaButton.setActionCommand(CMD_NOTA_ENTREGA);

		notaEntregaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				windowAction(event);
			}
		});

		botonesPanel.setLayout(new GridBagLayout());
		botonesPanel.add(okButton,
				UtilFormas.setGridContraints("gridx=0,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
//		botonesPanel.add(notaEntregaButton,
//				UtilFormas.setGridContraints("gridx=1,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
//						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
		botonesPanel.add(cancelButton,
				UtilFormas.setGridContraints("gridx=2,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
	}

	/**
	 * Retorna el indicador de acción satisfactoria.
	 *
	 * @return boolean: Indicador de acción satisfactoria.
	 */
	protected abstract boolean actionOK();
	/**
	 * Retorna el indicador de acción satisfactoria.
	 *
	 * @return boolean: Indicador de acción satisfactoria.
	 */
	protected abstract boolean actionOK(String actionCommand);
	/**
	 * Retorna el indicador de acción tomada.
	 *
	 * @return boolean: Indicador de acción tomada.
	 */
	public boolean isButtonFlag() {
		return buttonFlag;
	}
	/**
	 * Selección de opción.
	 *
	 * @param actionCommand Acción a realizar.
	 */
	public void windowAction(Object actionCommand) {

		try {
			String cmd = null;

			if (actionCommand != null) {
				if (actionCommand instanceof ActionEvent) {
					cmd = ((ActionEvent) actionCommand).getActionCommand();
				} else {
					cmd = actionCommand.toString();
				}
			}
			if (cmd.equals(CMD_CANCEL)) {
				closeWindow = true;
				buttonFlag = false;
			} else if (cmd.equals(CMD_OK)) {
				closeWindow = actionOK(CMD_OK);
				buttonFlag = closeWindow;
			}else if (cmd.equals(CMD_NOTA_ENTREGA)) {
				closeWindow = actionOK(CMD_NOTA_ENTREGA);
				buttonFlag = closeWindow;
			}
			

		} finally {
			if (closeWindow) {
				removeAll();
				dispose();
			}
		}
	}

}
