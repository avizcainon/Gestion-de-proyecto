package ve.com.avss.clientes.interfaz;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.BD.conectores.ConectorClienteProveedor;
import ve.com.avss.clientes.bean.DatosClientesProveedor;
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
	protected JLabel eIdentificacionCP = new JLabel();
	protected JTextField identificacionCP = null;
	protected JLabel eNombreCP = new JLabel();
	protected JTextField nombreCP = null;
	protected JLabel eApellidoCP = new JLabel();
	protected JTextField apellidoCP = null;
	protected JLabel eCorreoCP = new JLabel();
	protected JTextField correoCP = null;
	protected JLabel eTelefonoCP = new JLabel();
	protected JTextField telefonoCP = null;
	protected JLabel eDireccionCP = new JLabel();
	protected JTextField direccionCP = null;
	protected JLabel eTipoCP = new JLabel();
	protected JTextField buscarCP = null;
	protected JLabel eBuscarCP = new JLabel();
	protected JComboBox comboTipoCP = new JComboBox();
	protected List<DatosClientesProveedor> listaTipoCliente = null;
	 /*datos para lista de clientes*/
	 protected JTable tablaListaClientes = new JTable();
	 protected DefaultTableModel modelTablaListaClientes = new DefaultTableModel();
	 protected ConectorClienteProveedor conectorCliente = new ConectorClienteProveedor();
	 protected List<DatosClientesProveedor> listaClienteProveedor = new ArrayList<DatosClientesProveedor>();
	 protected Map<String, String> idTipoCliente = new LinkedHashMap<String, String>();
	protected Map<String, String> tipoClienteId = new LinkedHashMap<String, String>();
	public AbstractDialogoBase(JFrame parent) {
		super(parent, true);
		 
		setIconImage(Util.getIconImage());
		try {
			this.parent = parent;

		} catch (Exception e) {
			return;
		}
		
		try {
			listaTipoCliente = conectorCliente.obtenerTipoClienteProveedor();
			listaClienteProveedor = conectorCliente.listaClienteProveedor();
			
			for (int i = 0; i < listaTipoCliente.size(); i++) {
				idTipoCliente.put(listaTipoCliente.get(i).getIdBD(), listaTipoCliente.get(i).getTipoClienteProveedor());
				tipoClienteId.put(listaTipoCliente.get(i).getTipoClienteProveedor(), listaTipoCliente.get(i).getIdBD());
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
	
	protected void construirTablaClientes() {
		log.info("construirTablaClientes");
		// Column Names
		String[] nombreColumnaCategoria = {"id","Tipo","Identificación","Nombre","Apellido","Telefono","Correo","Dirección" };

		// inicializamos el JTable
		modelTablaListaClientes.setColumnIdentifiers(nombreColumnaCategoria);
		
		tablaListaClientes = new JTable();
		tablaListaClientes.setModel(modelTablaListaClientes);
		//tablaListaClientes.setBounds(30, 40, 50, 20);
		/*ocualtamos la columna id*/
		TableColumnModel columnModel = tablaListaClientes.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(0);
		columnModel.getColumn(0).setMinWidth(0);
		columnModel.getColumn(0).setMaxWidth(0);
		columnModel.getColumn(0).setWidth(0);
		columnModel.getColumn(0).setPreferredWidth(0);
	    columnModel.getColumn(1).setPreferredWidth(150);
	    columnModel.getColumn(2).setPreferredWidth(200);
	    columnModel.getColumn(3).setPreferredWidth(250);
	    columnModel.getColumn(4).setPreferredWidth(250);
	    columnModel.getColumn(5).setPreferredWidth(250);
	    columnModel.getColumn(6).setPreferredWidth(250);
	    columnModel.getColumn(7).setPreferredWidth(250);
//	    column.setMinWidth(0);
//	    column.setMaxWidth(0);
//	    column.setWidth(0);
//	    column.setPreferredWidth(0);
	    
	    tablaListaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// agregamos al JScrollPane
		JScrollPane sp = new JScrollPane(tablaListaClientes);
		sp.setPreferredSize(new Dimension(800, 400));
		//sp.setBounds(0,40,200,100);
		//jContentPaneCentral.setPreferredSize(new Dimension(800, 100));
		jContentPaneCentral.add(sp);
		log.info("construirTablaClientes - fin");
	}

	protected void armarCampos(String campo) {
		
		buscarCP = new JTextField();
		buscarCP.setName("identificacion");
		buscarCP.setBackground(Color.WHITE);
		buscarCP.setPreferredSize(new java.awt.Dimension(110, 25));
		buscarCP.setOpaque(true);
		buscarCP.setText("");
		

		switch (campo) {

			
		case "identificacionCP":
			identificacionCP = new JTextField();
			identificacionCP.setName("identificacion");
			identificacionCP.setBackground(Color.WHITE);
			identificacionCP.setPreferredSize(new java.awt.Dimension(110, 25));
			identificacionCP.setOpaque(true);
			identificacionCP.setText("");
			break;
		case "nombreCP":
			nombreCP = new JTextField();
			nombreCP.setName("nombreCP");
			nombreCP.setBackground(Color.WHITE);
			nombreCP.setPreferredSize(new java.awt.Dimension(110, 25));
			nombreCP.setOpaque(true);
			nombreCP.setText("");
			break;
		case "apellidoCP":
			apellidoCP = new JTextField();
			apellidoCP.setName("apellidoCP");
			apellidoCP.setBackground(Color.WHITE);
			apellidoCP.setPreferredSize(new java.awt.Dimension(110, 25));
			apellidoCP.setOpaque(true);
			apellidoCP.setText("");
			break;
		case "telefonoCP":
			telefonoCP = new JTextField();
			telefonoCP.setName("telefonoCP");
			telefonoCP.setBackground(Color.WHITE);
			telefonoCP.setPreferredSize(new java.awt.Dimension(110, 25));
			telefonoCP.setOpaque(true);
			telefonoCP.setText("");
			break;
		case "correoCP":
			correoCP = new JTextField();
			correoCP.setName("correoCP");
			correoCP.setBackground(Color.WHITE);
			correoCP.setPreferredSize(new java.awt.Dimension(110, 25));
			correoCP.setOpaque(true);
			correoCP.setText("");
			break;
		case "direccionCP":
			direccionCP = new JTextField();
			direccionCP.setName("direccionCP");
			direccionCP.setBackground(Color.WHITE);
			direccionCP.setPreferredSize(new java.awt.Dimension(110, 25));
			direccionCP.setOpaque(true);
			direccionCP.setText("");
			break;
		case "tipoCP":
			comboTipoCP.setName("tipoCP");
			comboTipoCP.setBackground(Color.WHITE);
			comboTipoCP.setPreferredSize(new java.awt.Dimension(100, 28));
			((JComboBox) comboTipoCP).addItem("");
			for (int i = 0; i < listaTipoCliente.size(); i++) {

				((JComboBox) comboTipoCP).addItem(listaTipoCliente.get(i).getTipoClienteProveedor());
			}
			break;
		default:
			break;

		}
	}

	protected void armarEtiquetas(String campo) {

		
		eBuscarCP.setPreferredSize(new java.awt.Dimension(140, 15));
		eBuscarCP.setText("Filtrar");
		
		
		switch (campo) {

		case "identificacionCP":
			eIdentificacionCP.setPreferredSize(new java.awt.Dimension(140, 15));
			eIdentificacionCP.setText("Identificación");
			break;
		case "nombreCP":
			eNombreCP.setPreferredSize(new java.awt.Dimension(140, 15));
			eNombreCP.setText("Nombre");
			break;
		case "apellidoCP":
			eApellidoCP.setPreferredSize(new java.awt.Dimension(140, 15));
			eApellidoCP.setText("Apellido");
			break;
		case "telefonoCP":
			eTelefonoCP.setPreferredSize(new java.awt.Dimension(140, 15));
			eTelefonoCP.setText("Teléfono");
			break;
		case "correoCP":
			eCorreoCP.setPreferredSize(new java.awt.Dimension(140, 15));
			eCorreoCP.setText("Correo");
			break;
		case "direccionCP":
			eDireccionCP.setPreferredSize(new java.awt.Dimension(140, 15));
			eDireccionCP.setText("Dirección");
			break;
		case "tipoCP":
			eTipoCP.setPreferredSize(new java.awt.Dimension(140, 15));
			eTipoCP.setText("Tipo");
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
