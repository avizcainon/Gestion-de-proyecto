package ve.com.avss.proyectos.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.BD.conectores.ConectorClienteProveedor;
import ve.com.avss.clientes.bean.DatosClientesProveedor;
import ve.com.avss.proyectos.BD.conectores.ConectorProyecto;
import ve.com.avss.proyectos.bean.DatosProyecto;
import ve.com.avss.proyectos.bean.DatosStatus;
import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.util.AppJButton;
import ve.com.avss.ventas.util.Util;
import ve.com.avss.ventas.util.UtilFormas;

public abstract class AbstractDialogoBase extends JDialog{

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AbstractDialogoBase.class);
	/** Command string para la accion de "Cancelar". */
	public static final String CMD_CANCEL = "cmd.cancel";
	/** Command string para la accion de "Aceptar". */
	public static final String CMD_OK = "cmd.ok";
	/** Command string para la accion de "Ocultar procesando". */
	public static final String CMD_PROCESS = "cmd.process"; 
	/** Command string para la accion de "Cancelar". */
	public static final String CMD_UPDATE = "cmd.update";
	/** Command string para la accion de "Cancelar". */
	public static final String CMD_OPEN = "cmd.open";
	/** JFrame base del dialogo */
	protected JFrame parent = null;
	/** Indicador de acción. */
	protected boolean buttonFlag;
	protected boolean closeWindow = false;
	/** Botón aceptar. */
	protected AppJButton okButton = null;
	/** Botón cancelar. */
	protected JButton cancelButton = new JButton();
	/*Boton actualizar*/
	protected AppJButton actualizarButton = null;
	/*Boton abrir citas*/
	protected AppJButton abrirCitasButton = null;
	/*datos para la interfaz*/
	protected JPanel jContentPaneSuperior = new JPanel();
	protected JPanel jContentPaneCentral = new JPanel();
	protected JPanel jContentPaneInferior = new JPanel();
	protected JPanel jContentPaneDerecho = new JPanel();
	protected JPanel jContentPaneIzquierdo = new JPanel();
	protected JLabel eNombreFormulario = new JLabel();
	protected JPanel jContentPane = new JPanel(new BorderLayout());
	
	
	/** Panel de Campos. */
	protected JPanel camposPanel = new JPanel();
	/** Panel de ayuda. */
	protected JLabel ayudaPanel = new JLabel();
	/** Panel de Botones. */
	protected JPanel botonesPanel = new JPanel();
	/*datos clientes proveedor*/
	protected JPanel jPanelBuscar = new JPanel();
	protected JPanel jPanelIdentificacion = new JPanel();
	protected JPanel jPanelNombre = new JPanel();
	protected JPanel jPanelApellido = new JPanel();
	protected JPanel jPanelTelefono = new JPanel();
	protected JPanel jPanelCorreo = new JPanel();
	protected JPanel jPanelDireccion = new JPanel();
	protected JPanel jPanelTipoClienteProveedor = new JPanel();
	
	protected JTextField idTipoClienteProeveedor = new JTextField();
	protected JLabel eDescripcion = new JLabel();
	protected JTextField descripcion = null;
	protected JLabel eObservacion = new JLabel();
	//protected JTextField observacion = null;
	protected JTextArea textAreaObservacion = null;
	protected JScrollPane scrollPaneObservacion = null;
	protected JLabel eFechaDesarrollo = new JLabel();
	protected JTextField fechaDesarrollo = null;
	protected JLabel eFechaCertificacion = new JLabel();
	protected JTextField fechaCertificacion = null;

	protected JLabel eStatusProyecto = new JLabel();
	protected JTextField buscarProyecto = null;
	protected JLabel eBuscarProyecto = new JLabel();
	protected JComboBox comboStatus = new JComboBox();
	protected List<DatosStatus> listaStatus = null;
	 /*datos para lista de clientes*/
	 protected JTable tablaListaProyectos = new JTable();
	 protected DefaultTableModel modelTablaListaProyectos = new DefaultTableModel();
	 protected ConectorProyecto conectorProyecto = new ConectorProyecto();
	 protected List<DatosProyecto> listaProyectos = new ArrayList<DatosProyecto>();
	 protected Map<String, String> idStatus = new LinkedHashMap<String, String>();
	protected Map<String, String> statusId = new LinkedHashMap<String, String>();
	public AbstractDialogoBase(JFrame parent) {
		super(parent, true);
		 
		setIconImage(Util.getIconImage());
		try {
			this.parent = parent;

		} catch (Exception e) {
			return;
		}
		
		try {
			listaStatus = conectorProyecto.obtenerStatus();
			listaProyectos = conectorProyecto.listaProyectos();
			
			for (int i = 0; i < listaStatus.size(); i++) {
				idStatus.put(listaStatus.get(i).getIdBD(), listaStatus.get(i).getStatus());
				statusId.put(listaStatus.get(i).getStatus(), listaStatus.get(i).getIdBD());
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		eNombreFormulario.setText("");
		eNombreFormulario.setPreferredSize(new java.awt.Dimension(300, 30));
		eNombreFormulario.setHorizontalAlignment(SwingConstants.CENTER);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				windowAction(CMD_CANCEL);
			}
		});
	}
	
	protected void construirTablaProyectos() {
		log.info("construirTablaProyectos");
		// Column Names
		String[] nombreColumnaCategoria = {"id","Status","Descripción","Fecha desarrollo","Fecha certificación","Observación" };

		// inicializamos el JTable
		modelTablaListaProyectos.setColumnIdentifiers(nombreColumnaCategoria);
		
		tablaListaProyectos = new JTable();
		tablaListaProyectos.setModel(modelTablaListaProyectos);
		//tablaListaClientes.setBounds(30, 40, 50, 20);
		/*ocualtamos la columna id*/
		TableColumnModel columnModel = tablaListaProyectos.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(0);
		columnModel.getColumn(0).setMinWidth(0);
		columnModel.getColumn(0).setMaxWidth(0);
		columnModel.getColumn(0).setWidth(0);
		
		 //status
		columnModel.getColumn(1).setMinWidth(80);
		columnModel.getColumn(1).setMaxWidth(80);
		columnModel.getColumn(1).setWidth(80);
		columnModel.getColumn(1).setPreferredWidth(100);
		
	    //fecha desarrollo
		columnModel.getColumn(3).setMinWidth(100);
		columnModel.getColumn(3).setMaxWidth(140);
		columnModel.getColumn(3).setWidth(140);
		columnModel.getColumn(3).setPreferredWidth(140);
		//fecha certificacion
		columnModel.getColumn(4).setMinWidth(100);
		columnModel.getColumn(4).setMaxWidth(150);
		columnModel.getColumn(4).setWidth(150);
		columnModel.getColumn(4).setPreferredWidth(150);

	    	    
	    tablaListaProyectos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// agregamos al JScrollPane
		JScrollPane sp = new JScrollPane(tablaListaProyectos);
		sp.setPreferredSize(new Dimension(800, 400));
		//sp.setBounds(0,40,200,100);
		//jContentPaneCentral.setPreferredSize(new Dimension(800, 100));
		jContentPaneCentral.add(sp);
		log.info("construirTablaProyectos - fin");
	}

	protected void armarCampos(String campo) {
		
		buscarProyecto = new JTextField();
		buscarProyecto.setName("buscarProyecto");
		buscarProyecto.setBackground(Color.WHITE);
		buscarProyecto.setPreferredSize(new java.awt.Dimension(110, 25));
		buscarProyecto.setOpaque(true);
		buscarProyecto.setText("");
		

		switch (campo) {

			
		case "descripcion":
			descripcion = new JTextField();
			descripcion.setName("descripcionProyecto");
			descripcion.setBackground(Color.WHITE);
			descripcion.setPreferredSize(new java.awt.Dimension(110, 25));
			descripcion.setOpaque(true);
			descripcion.setText("");
			break;
		case "observacion":
			textAreaObservacion = new JTextArea();
			textAreaObservacion.setName("observacion");
			textAreaObservacion.setBackground(Color.WHITE);
			//textAreaObservacion.setPreferredSize(new Dimension(200, 110));
			textAreaObservacion.setText("");
			textAreaObservacion.setEditable(true);
			textAreaObservacion.setAutoscrolls(true);
			textAreaObservacion.setLineWrap(true);
			textAreaObservacion.setWrapStyleWord(true);
			textAreaObservacion.setFont(new Font("Courier New", Font.PLAIN, 17));
			
			scrollPaneObservacion = new JScrollPane(textAreaObservacion,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPaneObservacion.setPreferredSize(new Dimension(200,150));

		
			break;
		case "fechaDesarrollo":
			fechaDesarrollo = new JTextField();
			fechaDesarrollo.setName("fechaDesarrollo");
			fechaDesarrollo.setBackground(Color.WHITE);
			fechaDesarrollo.setPreferredSize(new java.awt.Dimension(110, 25));
			fechaDesarrollo.setOpaque(true);
			fechaDesarrollo.setText("");
			break;
		case "fechaCertificacion":
			fechaCertificacion = new JTextField();
			fechaCertificacion.setName("fechaCertificacion");
			fechaCertificacion.setBackground(Color.WHITE);
			fechaCertificacion.setPreferredSize(new java.awt.Dimension(110, 25));
			fechaCertificacion.setOpaque(true);
			fechaCertificacion.setText("");
			break;
		
		case "comboStatus":
			comboStatus.setName("comboStatus");
			comboStatus.setBackground(Color.WHITE);
			comboStatus.setPreferredSize(new java.awt.Dimension(100, 28));
			((JComboBox) comboStatus).addItem("");
			for (int i = 0; i < listaStatus.size(); i++) {

				((JComboBox) comboStatus).addItem(listaStatus.get(i).getStatus());
			}
			break;
		default:
			break;

		}
	}

	protected void armarEtiquetas(String campo) {

		
		eBuscarProyecto.setPreferredSize(new java.awt.Dimension(140, 15));
		eBuscarProyecto.setText("Filtrar");
		
		
		switch (campo) {

		case "descripcion":
			eDescripcion.setPreferredSize(new java.awt.Dimension(140, 15));
			eDescripcion.setText("Descripción");
			break;
		case "observacion":
			eObservacion.setPreferredSize(new java.awt.Dimension(140, 15));
			eObservacion.setText("Observación");
			break;
		case "fechaDesarrollo":
			eFechaDesarrollo.setPreferredSize(new java.awt.Dimension(140, 15));
			eFechaDesarrollo.setText("Fecha desarrollo");
			break;
		case "fechaCertificacion":
			eFechaCertificacion.setPreferredSize(new java.awt.Dimension(140, 15));
			eFechaCertificacion.setText("Fecha certificación");
			break;
		
		case "comboStatus":
			eStatusProyecto.setPreferredSize(new java.awt.Dimension(140, 15));
			eStatusProyecto.setText("Status");
			break;
		default:
			break;
		}
	

	}



	protected void construirPanelBotones(String textoPrimerBoton, String textoSegundoBoton) {

		// Botón Aceptar
		okButton = new AppJButton("", "");
		okButton = okButton.crearBoton();
		okButton.setText(textoPrimerBoton);
		okButton.setActionCommand(CMD_OK);

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
						GridBagConstraints.WEST, new Insets(10, 0, 20, 0)));
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
		actualizarButton = new AppJButton("", "");
		actualizarButton = actualizarButton.crearBoton();
		actualizarButton.setText(textoBotones[2]);
		actualizarButton.setVerifyInputWhenFocusTarget(false);
		actualizarButton.setActionCommand(CMD_UPDATE);
		actualizarButton.setIcon(DatosConfiguracion.iActualizar);
		
		actualizarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				windowAction(event);
			}
		});
		
		// Botón abrir citas
				abrirCitasButton = new AppJButton("", "");
				abrirCitasButton = actualizarButton.crearBoton();
				abrirCitasButton.setText(textoBotones[3]);
				abrirCitasButton.setVerifyInputWhenFocusTarget(false);
				abrirCitasButton.setActionCommand(CMD_OPEN);
				abrirCitasButton.setIcon(DatosConfiguracion.iAbrirArchivo);
				
				abrirCitasButton.addActionListener(new ActionListener() {
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
		botonesPanel.add(actualizarButton,
				UtilFormas.setGridContraints("gridx=2,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
		botonesPanel.add(abrirCitasButton,
				UtilFormas.setGridContraints("gridx=3,gridy=0,fill=" + +GridBagConstraints.HORIZONTAL,
						GridBagConstraints.WEST, new Insets(10, 0, 20, 20)));
	}

	/**
	 * Retorna el indicador de acción satisfactoria.
	 *
	 * @return boolean: Indicador de acción satisfactoria.
	 */
	protected abstract boolean actionOK();
	protected abstract boolean actionUpdate();
	protected abstract boolean actionOpen();
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
				closeWindow = actionOK();
				buttonFlag = closeWindow;
			}else if (cmd.equals(CMD_UPDATE)) {
				closeWindow = actionUpdate();
				buttonFlag = closeWindow;
			}else if (cmd.equals(CMD_OPEN)) {
				closeWindow = actionOpen();
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
