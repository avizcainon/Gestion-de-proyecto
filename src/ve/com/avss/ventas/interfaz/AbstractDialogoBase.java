package ve.com.avss.ventas.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.BD.ejecutores.QueryClienteProveedor;
import ve.com.avss.clientes.bean.DatosClientesProveedor;
import ve.com.avss.proyectos.BD.ejecutores.QueryProyecto;
import ve.com.avss.proyectos.bean.DatosProyecto;
import ve.com.avss.proyectos.bean.DatosStatus;
import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.util.AppJButton;
import ve.com.avss.ventas.util.Util;
import ve.com.avss.ventas.util.UtilFormas;

public abstract class AbstractDialogoBase extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9054696090078160104L;
	private static final Logger log = Logger.getLogger(AbstractDialogoBase.class);
	/** Command string para la accion de "Cancelar". */
	public static final String CMD_CANCEL = "cmd.cancel";
	/** Command string para la accion de "Aceptar". */
	public static final String CMD_OK = "cmd.ok";
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
	/*Menu de inicio*/
	protected JMenuBar menuBar = null;
	protected JMenu menuArchivo, menuConfiguracion, menuProyecto, menuUsuario, menuCliente, menuReporte, menuAcercade;
	/*Item de menu de archivo*/
	protected JMenuItem menuItemSalir, menuItemAbrirArchivo;
	/*Item de menu de configuracion*/
	protected JMenuItem menuItemEmpresa, menuItemAcercade;
	/*Item de menu de Producto*/
	protected JMenuItem menuItemProyecto;
	/*Item de menu de Usuario*/
	protected JMenuItem menuItemUsuario;
	/*Item de menu de Cliente proveedor*/
	protected JMenuItem menuItemClienteProveedor;
	/*Item menu de Reporte*/
	protected JMenuItem menuItemReporteTotal,menuItemReporteInventario;
	/*datos para la interfaz*/
	protected JPanel jContentPane = new JPanel(new BorderLayout());
	protected JPanel jContentPaneSuperior = new JPanel();
	protected JPanel jContentPaneCentral = new JPanel();
	protected JPanel jContentPaneInferior = new JPanel();
	protected JPanel jContentPanederecho = new JPanel();
	protected JPanel jContentPaneIzquierdo = new JPanel();
	protected JLabel eNombreFormulario = new JLabel();
	protected JLabel etiquetaReloj = new JLabel();
	/** Panel de Campos. */
	protected JPanel camposPanel = new JPanel();
	/** Panel de ayuda. */
	protected JLabel ayudaPanel = new JLabel();
	/** Panel de Botones. */
	protected JPanel botonesPanel = new JPanel();
	/*datos para usuario y clave*/
	protected JLabel eUsuario = new JLabel();
	protected JFormattedTextField usuario = null;
	protected JLabel eClave = new JLabel();
	protected JPasswordField clave = null;
	protected String anioApp = "";
	protected JPanel jContentPaneUsuario = new JPanel();
	protected JPanel jContentPaneClave = new JPanel();
	/*Botones del panel de inicio*/
	protected JButton btnAdministracion = null;
	protected JButton btnVerFactura = null; 
	protected JButton btnVenta = null;
	protected JButton btnCompra = null;
	protected JButton btnProducto = null;
	protected JButton btnReverso = null;
	protected JButton btnCotizacionVenta = null;
	
	protected JButton btnBuscarCitas = null;
	protected JButton btnCargarCitas = null;
	protected List<DatosStatus> listaStatus = null;
	protected List<DatosClientesProveedor> listaRecursos = null;
	protected List<DatosProyecto> listaProyectos = null;
	protected Map<String, String> listaStatusId = new HashMap<String, String>();
	protected Map<String, String> listaIdStatus = new HashMap<String, String>();
	protected Map<String, String> listaRecursosId = new HashMap<String, String>();
	protected Map<String, String> listaIdRecursos = new HashMap<String, String>();
	protected Map<String, String> listaProyectosId = new HashMap<String, String>();
	protected Map<String, String> listaIdProyectos = new HashMap<String, String>();
	
	public AbstractDialogoBase(JFrame parent) {
		
		
		try {
			this.parent = parent;
			listaRecursos = QueryClienteProveedor.obtenerListaClienteProveedor();
			listaStatus = QueryProyecto.obtenerStatus();
			listaProyectos = QueryProyecto.obtenerListaProyectos();
		} catch (Exception e) {
			return;
		}
		
		for (int i = 0; i < listaStatus.size(); i++) {
			listaStatusId.put(listaStatus.get(i).getStatus(), listaStatus.get(i).getIdBD());
			listaIdStatus.put(listaStatus.get(i).getIdBD(),listaStatus.get(i).getStatus());
			
			
		}
		
		for (int i = 0; i < listaRecursos.size(); i++) {
			listaRecursosId.put(listaRecursos.get(i).getNombre()+" "+listaRecursos.get(i).getApellido(), listaRecursos.get(i).getIdBD());
			listaIdRecursos.put(listaRecursos.get(i).getIdBD(),listaRecursos.get(i).getNombre()+" "+listaRecursos.get(i).getApellido());
			
			
		}
		
		for (int i = 0; i < listaProyectos.size(); i++) {
			listaProyectosId.put(listaProyectos.get(i).getDescripcion(), listaProyectos.get(i).getIdBD());
			listaIdProyectos.put(listaProyectos.get(i).getIdBD(),listaProyectos.get(i).getDescripcion());
			
			
		}

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				windowAction(CMD_CANCEL);
			}
		});
		
		Date date = new Date();

        ZoneId timeZone = ZoneId.systemDefault();
        LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
        anioApp = getLocalDate.getYear()+"";
        
        setIconImage(Util.getIconImage());

	}
	
	
	
	protected void armarCampos(String campo) {

		switch (campo) {
		case "usuario":
			
			usuario = new JFormattedTextField();
			usuario.setName("usuario");
			//usuario.setBackground(Color.WHITE);
			usuario.setPreferredSize(new java.awt.Dimension(110, 25));
			usuario.setOpaque(true);
			usuario.setText("");
			

			break;
		case "clave":
			clave = new JPasswordField();
			clave.setName("identificacion");
			//clave.setBackground(Color.WHITE);
			clave.setPreferredSize(new java.awt.Dimension(110, 25));
			clave.setOpaque(true);
			clave.setText("");

			break;
	

		default:
			break;
		}
	}
		
	protected void armarBotones(String campo) {

			switch (campo) {
				case "Administracion":
					
					
					menuItemEmpresa = new JMenuItem("Datos de empresa");
					menuItemEmpresa.setIcon(DatosConfiguracion.iDatosEmpresa);
//					menuItemRoles = new JMenuItem("Roles de usuario");
//					menuItemRoles.setIcon(DatosConfiguracion.iRolesUsuario);
//					menuItemCategoria = new JMenuItem("Categoría de producto");
//					menuItemCategoria.setIcon(DatosConfiguracion.iCategoriaProducto);
//					menuItemPrecioDivisa = new JMenuItem("Precio de divisa");
//					menuItemPrecioDivisa.setIcon(DatosConfiguracion.iPrecioDivisa);
					
					
					menuConfiguracion.add(menuItemEmpresa);
//					menuConfiguracion.add(menuItemRoles);
//					menuConfiguracion.add(menuItemCategoria);
//					menuConfiguracion.add(menuItemPrecioDivisa);
					
					
					menuItemEmpresa.addActionListener((ActionListener) this);
//					menuItemRoles.addActionListener((ActionListener) this);
//					menuItemCategoria.addActionListener((ActionListener) this);
//					menuItemPrecioDivisa.addActionListener((ActionListener) this);
					
					
					
				
					break;
				
				case "Ver factura":
					
					btnVerFactura = botonInicio("Ver factura", "btnVerFactura");
					
					
					break;
				
				case "Proyecto":
					menuProyecto = new JMenu("Proyectos");
					menuProyecto.setIcon(DatosConfiguracion.iProducto);
					menuProyecto.setFont(DatosConfiguracion.TITULO_2_PLAIN);
					menuBar.add(menuProyecto);
					
					menuItemProyecto = new JMenuItem("Registrar / Modificar");
					menuItemProyecto.setIcon(DatosConfiguracion.iRegistrarProducto);
					menuProyecto.add(menuItemProyecto);
					
					menuItemProyecto.addActionListener((ActionListener) this);
	
					break;
				
				case "Cliente":
					menuCliente = new JMenu("Recursos");
					menuCliente.setIcon(DatosConfiguracion.iClienteProveedor);
					menuCliente.setFont(DatosConfiguracion.TITULO_2_PLAIN);
					menuBar.add(menuCliente);
					
					menuItemClienteProveedor = new JMenuItem("Registrar / Modificar");
					menuItemClienteProveedor.setIcon(DatosConfiguracion.iNuevoClienteProveedor);
					menuCliente.add(menuItemClienteProveedor);
					
					menuItemClienteProveedor.addActionListener((ActionListener) this);
					break;
				case "Usuario":
					menuUsuario = new JMenu("Usuarios");
					menuUsuario.setIcon(DatosConfiguracion.iUsuario);
					menuUsuario.setFont(DatosConfiguracion.TITULO_2_PLAIN);
					menuConfiguracion.add(menuUsuario);
					
					menuItemUsuario = new JMenuItem("Registrar / Modificar");
					menuItemUsuario.setIcon(DatosConfiguracion.iNuevoUsuario);
					menuUsuario.add(menuItemUsuario);
					
					menuItemUsuario.addActionListener((ActionListener) this);
					break;
				case "Reportes":
					menuReporte = new JMenu("Reportes");
					menuReporte.setIcon(DatosConfiguracion.iReporte);
					menuReporte.setFont(DatosConfiguracion.TITULO_2_PLAIN);
					menuBar.add(menuReporte);
					
					menuItemReporteTotal = new JMenuItem("Total");
					menuItemReporteTotal.setIcon(DatosConfiguracion.iReporteTotal);
					menuReporte.add(menuItemReporteTotal);
					menuItemReporteInventario = new JMenuItem("Inventario");
					menuItemReporteInventario.setIcon(DatosConfiguracion.iReporteInventario);
					menuReporte.add(menuItemReporteInventario);
					
					menuItemReporteTotal.addActionListener((ActionListener) this);
					menuItemReporteInventario.addActionListener((ActionListener) this);
					break;
			

			default:
				break;
			}
	}

	protected void armarEtiquetas(String campo) {

		switch (campo) {
		case "usuario":
			eUsuario.setPreferredSize(new java.awt.Dimension(140, 30));
			eUsuario.setText("Usuario");
			eUsuario.setIcon(DatosConfiguracion.iUsuarioAutenticacion);
			eUsuario.setFont(DatosConfiguracion.TITULO_3_BOLD);
			break;

		case "clave":
			eClave.setPreferredSize(new java.awt.Dimension(140, 30));
			eClave.setText("Clave");
			eClave.setIcon(DatosConfiguracion.iClaveAutenticacion);
			eClave.setFont(DatosConfiguracion.TITULO_3_BOLD);
			break;


		default:
			break;
		}

	}

	private JButton botonInicio(String nombre, String action) {
		JButton boton = new JButton();
		boton.setActionCommand(action);
		boton.setName(action);
		boton.setPreferredSize(new Dimension(150, 50));
		boton.setMaximumSize(new Dimension(150, 50));
		boton.setMinimumSize(new Dimension(150, 50));
		boton.setText(nombre);
		boton.setVerifyInputWhenFocusTarget(false);
		return boton;
		
	}

	protected void construirPanelBotones(String textoPrimerBoton, String textoSegundoBoton) {

		// Botón Aceptar
		okButton = new AppJButton("success","proceso");
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

	/**
	 * Retorna el indicador de acción satisfactoria.
	 *
	 * @return boolean: Indicador de acción satisfactoria.
	 */
	protected abstract boolean actionOK();
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
			}

		} finally {
			if (closeWindow) {
				removeAll();
				dispose();
			}
		}
	}
	


}
