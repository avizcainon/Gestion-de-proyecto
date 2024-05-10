package ve.com.avss.empresa.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.BD.conectores.ConectorRoles;
import ve.com.avss.empresa.BD.conectores.ConectorUsuario;
import ve.com.avss.empresa.bean.DatosRol;
import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.util.AppJButton;
import ve.com.avss.ventas.util.Util;
import ve.com.avss.ventas.util.UtilFormas;

public abstract class AbstractDialogoBase extends JDialog {
	private static final Logger log = Logger.getLogger(AbstractDialogoBase.class);
	private static final long serialVersionUID = 1L;
	/** Command string para la accion de "Cancelar". */
	public static final String CMD_CANCEL = "cmd.cancel";
	/** Command string para la accion de "Aceptar". */
	public static final String CMD_OK = "cmd.ok";
	/** Command string para la accion de "Ocultar procesando". */
	public static final String CMD_PROCESS = "cmd.process";
	/** Command string para la accion de "Cancelar". */
	public static final String CMD_UPDATE = "cmd.update";
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
	protected JButton eliminarButton = new JButton();
	protected JPanel jContentPane = new JPanel(new BorderLayout());
	protected JPanel jPanelNombre = new JPanel();
	protected JPanel jPanelModulos = new JPanel();
	protected JPanel jPanelCentral = new JPanel();
	protected JCheckBox jcheckModulo = null;
	
	/*paneles para usuarios*/
	protected JPanel jPanelCedulaU = new JPanel();
	protected JPanel jPanelNombreU = new JPanel();
	protected JPanel jPanelApellidoU = new JPanel();
	protected JPanel jPanelCorreoU = new JPanel();
	protected JPanel jPanelTelefonoU = new JPanel();
	protected JPanel jPanelUsuarioU = new JPanel();
	protected JPanel jPanelClaveU = new JPanel();
	protected JPanel jPanelRolU = new JPanel();
	/*paneles para empresa*/
	protected JPanel jPanelNombreE = new JPanel();
	protected JPanel jPanelRifE = new JPanel();
	protected JPanel jPanelDireccionE = new JPanel();
	protected JPanel jPanelCorreoE = new JPanel();
	protected JPanel jPanelTelefonoE = new JPanel();
	protected JPanel jPanelRutaInstalacion = new JPanel(); 
	protected JPanel jPanelLogoE= new JPanel();
	protected JPanel jPanelMontoDivisaE= new JPanel();
	protected JPanel jPanelImpuestoE= new JPanel();
	protected JPanel jPanelValidadCantidadProductoE= new JPanel();
	
	
	/** Panel de Campos. */
	protected JPanel camposPanel = new JPanel();
	/** Panel de ayuda. */
	protected JLabel ayudaPanel = new JLabel();
	/** Panel de Botones. */
	protected JPanel botonesPanel = new JPanel();
	
	protected JLabel eNombreRol = new JLabel("", JLabel.LEFT);
	protected JTextField nombreRol = null;
	
	protected JLabel eModulo = new JLabel("", JLabel.LEFT);
	/*datos para los usuarios*/
	protected JTextField idUsuario = new JTextField();
	protected JLabel eCedulaU = new JLabel();
	protected JTextField cedulaU = null;
	protected JLabel eNombreU = new JLabel();
	protected JTextField nombreU = null;
	protected JLabel eApellidoU = new JLabel();
	protected JTextField apellidoU = null;
	protected JLabel eCorreoU = new JLabel();
	protected JTextField correoU = null;
	protected JLabel eTelefonoU = new JLabel();
	protected JTextField telefonoU = null;
	protected JLabel eUsuarioU = new JLabel();
	protected JTextField usuarioU = null;
	protected JLabel eClaveU = new JLabel();
	protected JTextField claveU = null;
	protected JLabel eRolU = new JLabel();
	protected JComboBox comboRolU = new JComboBox();
	/*datos para Empresas*/
	protected JLabel eNombreE = new JLabel();
	protected JTextField nombreE= null;
	protected JLabel eRifE = new JLabel("",JLabel.LEFT);
	protected JTextField rifE = null;
	protected JLabel eDireccionE = new JLabel();
	protected JTextField direccionE = null;
	protected JLabel eCorreoE = new JLabel();
	protected JTextField correoE = null;
	protected JLabel eTelefonoE = new JLabel();
	protected JTextField telefonoE = null;
	protected JLabel eLogoE = new JLabel();
	protected JLabel eRutaLogoE = new JLabel("", JLabel.RIGHT);
	protected JLabel eRutaInstalacionTitulo = new JLabel("",JLabel.LEFT);
	protected JLabel eRutaInstalacion = new JLabel();
	protected JButton rutaInstalacionE = null;
	protected JButton logoE = null;
	protected JLabel eImagenLogoE = new JLabel("", JLabel.RIGHT);
	protected JLabel eRutaInstalacionE = new JLabel("", JLabel.RIGHT);
	protected JFormattedTextField montoDivisaE= null;
	protected JLabel eMontoDivisaE = new JLabel();
	protected JLabel eImpuestoE = new JLabel();
	protected JFormattedTextField impuestoE= null;
	protected JCheckBox checkBoxCantidadProducto = new JCheckBox(); 
	/*datos para la interfaz*/
	protected JPanel jContentPaneSuperior = new JPanel();
	protected JPanel jContentPaneCentral = new JPanel();
	protected JPanel jContentPaneInferior = new JPanel();
	protected JPanel jContentPaneDerecho = new JPanel();
	protected JPanel jContentPaneIzquierdo = new JPanel();
	protected JLabel eNombreFormulario = new JLabel();
	/*datos para lista de roles*/
	 protected JTable tablaListaRolesModulos = new JTable();
	 protected DefaultTableModel modelTablaListaRolesModulos = new DefaultTableModel();
	 /*datos para lista de roles*/
	 protected JTable tablaListaUsuarios = new JTable();
	 protected DefaultTableModel modelTablaListaUsuarios = new DefaultTableModel();
	
	protected Map<String, String> idNombreRol = new LinkedHashMap<String, String>();
	protected Map<String, String> nombreRolId = new LinkedHashMap<String, String>();
	
	protected List<DatosRol> listaRoles = null;
	protected List<DatosUsuario> listaUsuarios = null;
	protected ConectorRoles conectorRoles = new ConectorRoles();
	protected ConectorUsuario conectorUsuario = new ConectorUsuario();
	
	public AbstractDialogoBase(JFrame parent) throws Exception {
		super(parent, true);
		log.info("Constructor - construyendo paneles e interfaz");
		setIconImage(Util.getIconImage());
		try {
			this.parent = parent;


		} catch (Exception e) {
			return;
		}
		
		try {
			//listaModulos = QueryEmpresa.obtenerModulos();
			listaRoles = conectorRoles.obtenerRoles();
			listaUsuarios = conectorUsuario.obtenerListaUsuarios();
			for (int i = 0; i < listaRoles.size(); i++) {
				idNombreRol.put(listaRoles.get(i).getId(), listaRoles.get(i).getNombre());
				nombreRolId.put(listaRoles.get(i).getNombre(), listaRoles.get(i).getId());
			}
			
		} catch (Exception e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		}
		eNombreFormulario.setText("");
		eNombreFormulario.setPreferredSize(new java.awt.Dimension(140, 30));
		eNombreFormulario.setHorizontalAlignment(SwingConstants.CENTER);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				windowAction(CMD_CANCEL);
			}
		});
	}
	
	
	
	protected void construirTablaUsuarios() {
		log.info("construirTablaUsuarios");
		// Column Names
		String[] nombreColumnaRoles = {"id", "Cédula", "Nombre", "Apellido", "Correo", "Teléfono", "Rol","Usuario", "Clave" };

		// inicializamos el JTable
		modelTablaListaUsuarios.setColumnIdentifiers(nombreColumnaRoles);
		tablaListaUsuarios = new JTable();
		tablaListaUsuarios.setModel(modelTablaListaUsuarios);
		tablaListaUsuarios.setBounds(30, 40, 20, 20); 
		/*ocualtamos la columna id*/
		TableColumn column = tablaListaUsuarios.getColumnModel().getColumn(0);
	    column.setMinWidth(0);
	    column.setMaxWidth(0);
	    column.setWidth(0);
	    column.setPreferredWidth(0);

		// agregamos al JScrollPane
		JScrollPane sp = new JScrollPane(tablaListaUsuarios);
		jContentPaneCentral.add(sp);
		log.info("construirTablaUsuarios - fin");
	}

	
	protected void armarCampos(String campo) {

		switch (campo) {

		case "nombreRol":
			
			nombreRol = new JTextField();
			nombreRol.setName("nombreRol");
			nombreRol.setBackground(Color.WHITE);
			nombreRol.setPreferredSize(new java.awt.Dimension(110, 25));
			nombreRol.setOpaque(true);
			nombreRol.setText("");
			break;

	
		case "nombreU":
			
			nombreU = new JTextField();
			nombreU.setName("nombreU");
			nombreU.setBackground(Color.WHITE);
			nombreU.setPreferredSize(new java.awt.Dimension(110, 25));
			nombreU.setOpaque(true);
			nombreU.setText("");
			break;
		case "apellidoU":
			
			apellidoU = new JTextField();
			apellidoU.setName("apellidoU");
			apellidoU.setBackground(Color.WHITE);
			apellidoU.setPreferredSize(new java.awt.Dimension(110, 25));
			apellidoU.setOpaque(true);
			apellidoU.setText("");
			break;
		case "cedulaU":
				
				cedulaU = new JTextField();
				cedulaU.setName("cedulaU");
				cedulaU.setBackground(Color.WHITE);
				cedulaU.setPreferredSize(new java.awt.Dimension(110, 25));
				cedulaU.setOpaque(true);
				cedulaU.setText("");
			break;
		case "correoU":
			
			correoU = new JTextField();
			correoU.setName("correoU");
			correoU.setBackground(Color.WHITE);
			correoU.setPreferredSize(new java.awt.Dimension(150, 25));
			correoU.setOpaque(true);
			correoU.setText("");
			break;
		case "telefonoU":
			
			telefonoU = new JTextField();
			telefonoU.setName("telefonoU");
			telefonoU.setBackground(Color.WHITE);
			telefonoU.setPreferredSize(new java.awt.Dimension(110, 25));
			telefonoU.setOpaque(true);
			telefonoU.setText("");
			break;
		case "usuarioU":
			
			usuarioU = new JTextField();
			usuarioU.setName("usuarioU");
			usuarioU.setBackground(Color.WHITE);
			usuarioU.setPreferredSize(new java.awt.Dimension(110, 25));
			usuarioU.setOpaque(true);
			usuarioU.setText("");
			break;
		case "claveU":
			
			claveU = new JTextField();
			claveU.setName("claveU");
			claveU.setBackground(Color.WHITE);
			claveU.setPreferredSize(new java.awt.Dimension(110, 25));
			claveU.setOpaque(true);
			claveU.setText("");
			break;
		case "rolU":
			
			comboRolU.setName("rolU");
			comboRolU.setBackground(Color.WHITE);
			comboRolU.setPreferredSize(new java.awt.Dimension(100, 28));
			((JComboBox) comboRolU).addItem("");
			for (int i = 0; i < listaRoles.size(); i++) {

				((JComboBox) comboRolU).addItem(listaRoles.get(i).getNombre());
			}
			break;
		case "nombreE":
			
			nombreE = new JTextField();
			nombreE.setName("nombreE");
			nombreE.setBackground(Color.WHITE);
			nombreE.setPreferredSize(new java.awt.Dimension(300, 25));
			nombreE.setOpaque(true);
			nombreE.setText("");
			break;
		case "rifE":
			
			rifE = new JTextField();
			rifE.setName("rifE");
			rifE.setBackground(Color.WHITE);
			rifE.setPreferredSize(new java.awt.Dimension(300, 25));
			rifE.setOpaque(true);
			rifE.setText("");
		case "direccionE":
			direccionE = new JTextField();
			direccionE.setName("direccionE");
			direccionE.setBackground(Color.WHITE);
			direccionE.setPreferredSize(new java.awt.Dimension(300, 25));
			direccionE.setOpaque(true);
			direccionE.setText("");
			break;
		case "telefonoE":
			telefonoE = new JTextField();
			telefonoE.setName("telefonoE");
			telefonoE.setBackground(Color.WHITE);
			telefonoE.setPreferredSize(new java.awt.Dimension(300, 25));
			telefonoE.setOpaque(true);
			telefonoE.setText("");
			break;
		case "correoE":
			correoE = new JTextField();
			correoE.setName("correoE");
			correoE.setBackground(Color.WHITE);
			correoE.setPreferredSize(new java.awt.Dimension(300, 25));
			correoE.setOpaque(true);
			correoE.setText("");
			break;
		case "logoE":
			logoE = new JButton();
			logoE.setText("Abrir");
			logoE.setName("logoE");
			
			rutaInstalacionE = new JButton();
			rutaInstalacionE.setText("Abrir");
			rutaInstalacionE.setName("rutaInstalacion");
			
		case "montoDivisaE":
			montoDivisaE = new JFormattedTextField();
			montoDivisaE.setName("montoDivisaE");
			montoDivisaE.setBackground(Color.WHITE);
			montoDivisaE.setPreferredSize(new java.awt.Dimension(300, 25));
			montoDivisaE.setOpaque(true);
			montoDivisaE.setText("");
			montoDivisaE.setFormatterFactory(Util.armarMascaraMontoProvisional());
			montoDivisaE.addKeyListener(new java.awt.event.KeyAdapter() {

				public void keyReleased(java.awt.event.KeyEvent evt) {
					String aux;
					aux = montoDivisaE.getText();
					if (aux.length() <= 0) {
						return;
					}
					
					 String aux1 = aux.replace(",", "");
					 String aux2 = aux1.replace(".", "");
					 DecimalFormat formatter = new DecimalFormat("#,###.00");
					 aux=  formatter.format((new Double(aux2)/100));
					 montoDivisaE.setText(aux);
				}

				});
			break;
		case "impuestoE":
			impuestoE = new JFormattedTextField();
			impuestoE.setName("impuestoE");
			impuestoE.setBackground(Color.WHITE);
			impuestoE.setPreferredSize(new java.awt.Dimension(300, 25));
			impuestoE.setOpaque(true);
			impuestoE.setText("");
			impuestoE.setEditable(false);
			impuestoE.setFormatterFactory(Util.armarMascaraMontoProvisional());
			impuestoE.addKeyListener(new java.awt.event.KeyAdapter() {

				public void keyReleased(java.awt.event.KeyEvent evt) {
					String aux;
					aux = impuestoE.getText();
					if (aux.length() <= 0) {
						return;
					}
					
					 String aux1 = aux.replace(",", "");
					 String aux2 = aux1.replace(".", "");
					 DecimalFormat formatter = new DecimalFormat("#,###.00");
					 aux=  formatter.format((new Double(aux2)/100));
					 impuestoE.setText(aux);
				}

				});
			break;
		default:
			break;
		}
	}

	protected void armarEtiquetas(String campo) {

		switch (campo) {
 
		case "nombreRol":
			eNombreRol.setPreferredSize(new java.awt.Dimension(140, 15));
			eNombreRol.setText("Nombre del rol");
			break;
		case "modulos":
			eModulo.setPreferredSize(new java.awt.Dimension(140, 15));
			eModulo.setText("Modulos");
			break;
		case "cedulaU":
			eCedulaU.setPreferredSize(new java.awt.Dimension(140, 15));
			eCedulaU.setText("Cédula");
			break;
		case "nombreU":
			eNombreU.setPreferredSize(new java.awt.Dimension(140, 15));
			eNombreU.setText("Nombre");
			break;
		case "apellidoU":
			eApellidoU.setPreferredSize(new java.awt.Dimension(140, 15));
			eApellidoU.setText("Apellido");
			break;
		case "correoU":
			eCorreoU.setPreferredSize(new java.awt.Dimension(140, 15));
			eCorreoU.setText("Correo");
			break;
		case "telefonoU":
			eTelefonoU.setPreferredSize(new java.awt.Dimension(140, 15));
			eTelefonoU.setText("Teléfono");
			break;
		case "usuarioU":
			eUsuarioU.setPreferredSize(new java.awt.Dimension(140, 15));
			eUsuarioU.setText("@Usuario");
			break;
		case "claveU":
			eClaveU.setPreferredSize(new java.awt.Dimension(140, 15));
			eClaveU.setText("Clave");
			break;
		case "rolU":
			eRolU.setPreferredSize(new java.awt.Dimension(140, 15));
			eRolU.setText("Rol");
			break;
		case "nombreE":
			eNombreE.setPreferredSize(new java.awt.Dimension(140, 15));
			eNombreE.setText("Nombre");
			break;
		case "rifE":
			eRifE.setPreferredSize(new java.awt.Dimension(140, 15));
			eRifE.setText("RIF");
		case "correoE":
			eCorreoE.setPreferredSize(new java.awt.Dimension(140, 15));
			eCorreoE.setText("Correo");
		case "telefonoE":
			eTelefonoE.setPreferredSize(new java.awt.Dimension(140, 15));
			eTelefonoE.setText("Teléfono");
			break;
		case "direccionE":
			eDireccionE.setPreferredSize(new java.awt.Dimension(140, 15));
			eDireccionE.setText("Dirección");
			break;
		case "logoE":
			eLogoE.setPreferredSize(new java.awt.Dimension(200, 15));
			eLogoE.setText("Logo");
			eImagenLogoE.setSize(140, 140);
			eImagenLogoE.setText("");
			
			eRutaInstalacionTitulo.setPreferredSize(new java.awt.Dimension(140, 15));
			eRutaInstalacionTitulo.setText("Ruta de instalación");
			eRutaInstalacion.setPreferredSize(new java.awt.Dimension(200, 15));
			eRutaInstalacion.setText("Ruta...");
			
			
			
			
		case "montoDivisaE":
			eMontoDivisaE.setPreferredSize(new java.awt.Dimension(140, 15));
			eMontoDivisaE.setText("Monto divisa");
			break;
		case "impuestoE":
			eImpuestoE.setPreferredSize(new java.awt.Dimension(140, 15));
			eImpuestoE.setText("Impuesto ( % )");
			break;
		case "validarCantidadProductoE":
			checkBoxCantidadProducto.setPreferredSize(new java.awt.Dimension(140, 15));
			checkBoxCantidadProducto.setText("Validar cantidad de producto en la venta / cotización");
			checkBoxCantidadProducto.setPreferredSize(new java.awt.Dimension(350, 15));
			break;
			
			
			
		}

	}
	
	
	
	protected void construirPanelBotones(String textoPrimerBoton, String textoSegundoBoton) {

		// Botón Aceptar
		okButton = new AppJButton("", "");
		okButton = okButton.crearBoton();
		okButton.setText(textoPrimerBoton);
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
	protected abstract boolean actionUpdate();
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
			}

		} finally {
			if (closeWindow) {
				removeAll();
				dispose();
			}
		}
	}

}
