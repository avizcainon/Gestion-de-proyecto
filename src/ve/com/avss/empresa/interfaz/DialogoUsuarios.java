package ve.com.avss.empresa.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.ventas.bean.DatosConfiguracion;

public class DialogoUsuarios extends AbstractDialogoBase {

	private static final long serialVersionUID = -7135904572250948421L;
	private static final Logger log = Logger.getLogger(DialogoUsuarios.class);
	private DatosUsuario datosUsuario = new DatosUsuario();
	private String actionCommand = "";
	private int rowSeleccionada = 0;
	public DialogoUsuarios(JFrame parent) throws Exception {
		super(parent);
		log.info("Constructor - construyendo paneles e interfaz");
		contruirPanelesCampos();
		String[ ] botones = new String[3];
		botones[0] =  "Registrar";
		botones[1] =  "Cancelar";
		botones[2] =  "Actualizar";
		construirPanelBotones(botones);
		construirTablaUsuarios();
		cargarListaUsuarios();
		jContentPaneIzquierdo.setLayout(new BoxLayout(jContentPaneIzquierdo, BoxLayout.Y_AXIS));

		idUsuario.setVisible(false);
		idUsuario.setPreferredSize(new Dimension(20,20));
		jPanelCedulaU.add(idUsuario);
		jPanelCedulaU.add(eCedulaU);
		jPanelCedulaU.add(cedulaU);

		jContentPaneIzquierdo.add(jPanelCedulaU);

		jPanelNombreU.add(eNombreU);
		jPanelNombreU.add(nombreU);
		jContentPaneIzquierdo.add(jPanelNombreU);

		jPanelApellidoU.add(eApellidoU);
		jPanelApellidoU.add(apellidoU);
		jContentPaneIzquierdo.add(jPanelApellidoU);

		jPanelCorreoU.add(eCorreoU);
		jPanelCorreoU.add(correoU);
		jContentPaneIzquierdo.add(jPanelCorreoU);

		jPanelTelefonoU.add(eTelefonoU);
		jPanelTelefonoU.add(telefonoU);
		jContentPaneIzquierdo.add(jPanelTelefonoU);

		jPanelRolU.add(eRolU);
		jPanelRolU.add(comboRolU);
		jContentPaneIzquierdo.add(jPanelRolU);

		jPanelUsuarioU.add(eUsuarioU);
		jPanelUsuarioU.add(usuarioU);
		jContentPaneIzquierdo.add(jPanelUsuarioU);

		jPanelClaveU.add(eClaveU);
		jPanelClaveU.add(claveU);
		jContentPaneIzquierdo.add(jPanelClaveU);

		eNombreFormulario.setText("Registro de usuario");
		eNombreFormulario.setFont(DatosConfiguracion.TITULO_1_BOLD);
		eNombreFormulario.setForeground(Color.DARK_GRAY);
		jContentPane.add(eNombreFormulario, BorderLayout.NORTH);
		jContentPane.add(botonesPanel, BorderLayout.SOUTH);
		jContentPane.add(jContentPaneCentral, BorderLayout.CENTER);

		jContentPane.add(jContentPaneIzquierdo, BorderLayout.WEST);
		getContentPane().add(jContentPane);
		pack();
		setAlwaysOnTop(false);
		setLocationRelativeTo(null);
		setTitle("Usuarios");
		setResizable(true);
		setVisible(true);
	}

	private void cargarListaUsuarios() {
		log.info("cargarListaUsuarios");

		for (int i = 0; i < listaUsuarios.size(); i++) {

			modelTablaListaUsuarios.addRow(new Object[] { listaUsuarios.get(i).getId(),
					listaUsuarios.get(i).getCedula(), listaUsuarios.get(i).getNombre(),
					listaUsuarios.get(i).getApellido(), listaUsuarios.get(i).getCorreo(),
					listaUsuarios.get(i).getTelefono(), listaUsuarios.get(i).getRol(),
					listaUsuarios.get(i).getUsuario(), listaUsuarios.get(i).getClave() });

		}
		
		tablaListaUsuarios.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 1 ||me.getClickCount() == 2) {     // to detect doble click events
	            	log.info("seleccinado rows - fin");
	            	
	               JTable target = (JTable)me.getSource();
	               int row = target.getSelectedRow(); // select a row
	               rowSeleccionada = row;
	               String idUsuarioS = tablaListaUsuarios.getValueAt(row, 0).toString();
	               idUsuario.setText(idUsuarioS);
	               String cedula = tablaListaUsuarios.getValueAt(row, 1).toString();
	               cedulaU.setText(cedula);
	               String nombre =  tablaListaUsuarios.getValueAt(row, 2).toString();
	               nombreU.setText(nombre);
	               String apellido = tablaListaUsuarios.getValueAt(row, 3).toString();
	               apellidoU.setText(apellido);
	               String correo =  tablaListaUsuarios.getValueAt(row, 4).toString();
	               correoU.setText(correo);
	               String telefono = tablaListaUsuarios.getValueAt(row, 5).toString();
	               telefonoU.setText(telefono);
	               String rol =  tablaListaUsuarios.getValueAt(row, 6).toString();
	               comboRolU.setSelectedItem(rol);
	               String usuario = tablaListaUsuarios.getValueAt(row, 7).toString();
	               usuarioU.setText(usuario);
	               String clave =  tablaListaUsuarios.getValueAt(row, 8).toString();
	               claveU.setText(clave);
	               okButton.setEnabled(false);
	               log.info("seleccinado rows - fin "+usuario);
	              //JOptionPane.showMessageDialog(null,row+" "+column+" "+ table.getValueAt(row, column)); // get the value of a row and column.

	               log.info("seleccinado rows - fin");
	            }
	         }
	      });
		
		log.info("cargarListaUsuarios - fin");
	}

	private void contruirPanelesCampos() {

		String campo = "";

		/* armar campo nombre */
		campo = "cedulaU";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo nombre */
		campo = "nombreU";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo apellido */
		campo = "apellidoU";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo correo */
		campo = "correoU";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo telefono */
		campo = "telefonoU";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo usuario */
		campo = "usuarioU";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo clave */
		campo = "claveU";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo Rol */
		campo = "rolU";
		armarEtiquetas(campo);
		armarCampos(campo);

	}

	@Override
	protected boolean actionOK() {
		log.info("validar registrar usuario ");
		if (cedulaU.getText().equals("")) {
			cedulaU.setBackground(Color.CYAN);
			cedulaU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setCedula(cedulaU.getText());
		if (nombreU.getText().equals("")) {
			nombreU.setBackground(Color.CYAN);
			nombreU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setNombre(nombreU.getText());
		if (apellidoU.getText().equals("")) {
			apellidoU.setBackground(Color.CYAN);
			apellidoU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setApellido(apellidoU.getText());
		if (telefonoU.getText().equals("")) {
			telefonoU.setBackground(Color.CYAN);
			telefonoU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setTelefono(telefonoU.getText());
		datosUsuario.setCorreo(correoU.getText());

		if (usuarioU.getText().equals("")) {
			usuarioU.setBackground(Color.CYAN);
			usuarioU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setUsuario(usuarioU.getText());
		if (claveU.getText().equals("")) {
			claveU.setBackground(Color.CYAN);
			claveU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setClave(claveU.getText());

		String valorComboRolU = (String) comboRolU.getSelectedItem();
		if (valorComboRolU.equals("")) {
			comboRolU.setBackground(Color.CYAN);
			comboRolU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setRol(nombreRolId.get(valorComboRolU));
		
		if (registrarUsuarioBD() == 0) {
			JOptionPane.showMessageDialog(null, "No se pudo registrar",
					"Mensaje de la aplicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		String rol = idNombreRol.get(datosUsuario.getRol());
		datosUsuario.setRol(rol);
		modelTablaListaUsuarios.addRow(new Object[] { datosUsuario.getId(), datosUsuario.getCedula(), 
				datosUsuario.getNombre(), datosUsuario.getApellido(), datosUsuario.getCorreo(), datosUsuario.getTelefono()
				, datosUsuario.getRol(), datosUsuario.getUsuario(), datosUsuario.getClave()});
		
		JOptionPane.showMessageDialog(null, "Se registró con éxito",
				"Mensaje de la aplicación", JOptionPane.INFORMATION_MESSAGE);
		limpiarCampos();
		cedulaU.requestFocus();
		actionCommand = "registrar";
		log.info("registrar usuario "+datosUsuario.getUsuario());
		return false;
	}

	public DatosUsuario getDatosUsuario() {
		return datosUsuario;
	}

	@Override
	protected boolean actionUpdate() {
		log.info("validar actualizar usuario ");
		if (tablaListaUsuarios.getSelectedRows().length == 0) {
			 JOptionPane.showMessageDialog(parent,"Debe seleccionar un usuario","Alerta",JOptionPane.WARNING_MESSAGE); 
			 return false;
		}
		if (idUsuario.getText().equals("")) {
			idUsuario.setBackground(Color.CYAN);
			idUsuario.requestFocusInWindow();
			return false;
		}
		datosUsuario.setId(idUsuario.getText());
		datosUsuario.setCedula(cedulaU.getText());
		if (cedulaU.getText().equals("")) {
			cedulaU.setBackground(Color.CYAN);
			cedulaU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setCedula(cedulaU.getText());
		if (nombreU.getText().equals("")) {
			nombreU.setBackground(Color.CYAN);
			nombreU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setNombre(nombreU.getText());
		if (apellidoU.getText().equals("")) {
			apellidoU.setBackground(Color.CYAN);
			apellidoU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setApellido(apellidoU.getText());
		if (telefonoU.getText().equals("")) {
			telefonoU.setBackground(Color.CYAN);
			telefonoU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setTelefono(telefonoU.getText());
		datosUsuario.setCorreo(correoU.getText());

		if (usuarioU.getText().equals("")) {
			usuarioU.setBackground(Color.CYAN);
			usuarioU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setUsuario(usuarioU.getText());
		if (claveU.getText().equals("")) {
			claveU.setBackground(Color.CYAN);
			claveU.requestFocusInWindow();
			return false;
		}
		datosUsuario.setClave(claveU.getText());

		String valorComboRolU = (String) comboRolU.getSelectedItem();
		if (valorComboRolU.equals("")) {
			comboRolU.setBackground(Color.CYAN);
			comboRolU.requestFocusInWindow();
			return false;
		}
		
		String rol = nombreRolId.get(valorComboRolU);
		datosUsuario.setRol(rol);
		
		if (actualizarUsuarioBD() == 0) {
			JOptionPane.showMessageDialog(null, "No se pudo actualizar",
					"Mensaje de la aplicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		rol = idNombreRol.get(datosUsuario.getRol());
		datosUsuario.setRol(rol);
		limpiarCampos();
		JOptionPane.showMessageDialog(null, "Se actualizó con éxito",
				"Mensaje de la aplicación", JOptionPane.INFORMATION_MESSAGE);
		modelTablaListaUsuarios.setValueAt(datosUsuario.getId(), rowSeleccionada, 0);
		modelTablaListaUsuarios.setValueAt(datosUsuario.getCedula(), rowSeleccionada, 1);
		modelTablaListaUsuarios.setValueAt(datosUsuario.getNombre(), rowSeleccionada, 2);
		modelTablaListaUsuarios.setValueAt(datosUsuario.getApellido(), rowSeleccionada, 3);
		modelTablaListaUsuarios.setValueAt(datosUsuario.getCorreo(), rowSeleccionada, 4);
		modelTablaListaUsuarios.setValueAt(datosUsuario.getTelefono(), rowSeleccionada, 5);
		modelTablaListaUsuarios.setValueAt(datosUsuario.getRol(), rowSeleccionada, 6);
		modelTablaListaUsuarios.setValueAt(datosUsuario.getUsuario(), rowSeleccionada, 7);
		modelTablaListaUsuarios.setValueAt(datosUsuario.getClave(), rowSeleccionada, 8);
		limpiarCampos();
		okButton.setEnabled(true);
		actionCommand = "actualizar";
		log.info("actualizar usuario "+datosUsuario.getUsuario());
		return false;
	}
	
	private int registrarUsuarioBD() {
		conectorUsuario.setDatosUsuario(datosUsuario);
		return conectorUsuario.registrarUsuario();
	}
	
	private int actualizarUsuarioBD() {
		conectorUsuario.setDatosUsuario(datosUsuario);
		return conectorUsuario.actualizarUsuario();
	}
	
	private void limpiarCampos() {
		cedulaU.setText("");
		nombreU.setText("");
		apellidoU.setText("");
		correoU.setText("");
		telefonoU.setText("");
		usuarioU.setText("");
		claveU.setText("");
		comboRolU.setSelectedItem("");
		
		cedulaU.requestFocus();
	}


	public String getActionCommand() {
		return actionCommand;
	}

	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}

}
