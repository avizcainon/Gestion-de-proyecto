package ve.com.avss.clientes.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.BD.ejecutores.QueryClienteProveedor;
import ve.com.avss.clientes.bean.DatosClientesProveedor;
import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.ventas.bean.DatosConfiguracion;

public class DialogoClienteProveedor extends AbstractDialogoBase {
	private DatosClientesProveedor datosClienteProveedor = new DatosClientesProveedor();
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DialogoClienteProveedor.class);
	private int rowSeleccionada = 0;
	public DialogoClienteProveedor(JFrame parent) {
		super(parent);
		log.info("Constructor - construyendo paneles e interfaz");
		contruirPanelesCampos();
		construirTablaClientes();
		cargarListaClienteProveedor();
		String[ ] botones = new String[4];
		botones[0] =  "Registrar";
		botones[1] =  "Cancelar";
		botones[2] =  "Actualizar";
		botones[3] =  "Detalles";
		construirPanelBotones(botones);
		jContentPaneIzquierdo.setLayout(new BoxLayout(jContentPaneIzquierdo, BoxLayout.Y_AXIS));
		
		jPanelBuscar.add(eBuscarCP);
		jPanelBuscar.add(buscarCP);
		jContentPaneIzquierdo.add(jPanelBuscar);
		
		idTipoClienteProeveedor.setVisible(false);
		idTipoClienteProeveedor.setPreferredSize(new Dimension(20,20));
		jPanelTipoClienteProveedor.add(idTipoClienteProeveedor);
		jPanelTipoClienteProveedor.add(eTipoCP);
		jPanelTipoClienteProveedor.add(comboTipoCP);
		jContentPaneIzquierdo.add(jPanelTipoClienteProveedor);

		jPanelIdentificacion.add(eIdentificacionCP);
		jPanelIdentificacion.add(identificacionCP);
		jContentPaneIzquierdo.add(jPanelIdentificacion);

		
		jPanelNombre.add(eNombreCP);
		jPanelNombre.add(nombreCP);
		jContentPaneIzquierdo.add(jPanelNombre);

		
		jPanelApellido.add(eApellidoCP);
		jPanelApellido.add(apellidoCP);
		jContentPaneIzquierdo.add(jPanelApellido);

		
		jPanelTelefono.add(eTelefonoCP);
		jPanelTelefono.add(telefonoCP);
		jContentPaneIzquierdo.add(jPanelTelefono);

		
		jPanelCorreo.add(eCorreoCP);
		jPanelCorreo.add(correoCP);
		jContentPaneIzquierdo.add(jPanelCorreo);


		
		jPanelDireccion.add(eDireccionCP);
		jPanelDireccion.add(direccionCP);
		jContentPaneIzquierdo.add(jPanelDireccion);

		eNombreFormulario.setText("Registro de Recursos");
		eNombreFormulario.setFont(DatosConfiguracion.TITULO_1_BOLD);
		eNombreFormulario.setForeground(Color.DARK_GRAY);
		jContentPaneSuperior.add(eNombreFormulario);

		jContentPane.add(jContentPaneSuperior, BorderLayout.NORTH);
		jContentPane.add(jContentPaneIzquierdo, BorderLayout.WEST);
		jContentPane.add(jContentPaneCentral, BorderLayout.CENTER);
		jContentPane.add(botonesPanel, BorderLayout.SOUTH);
		buscarCP.addKeyListener(new KeyAdapter() {
			
			@Override
			  public void keyReleased(KeyEvent e) {
				System.out.println(	buscarCP.getText());
				
				try {
					listaClienteProveedor = QueryClienteProveedor.obtenerListaClienteProveedor(buscarCP.getText());
					limpiarTabla();
					cargarListaClienteProveedor();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		buscarCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.info("guarda cita");
				
				
				log.info("guardar cita - fin");
			}
		});

		getContentPane().add(jContentPane);
		pack();
		setAlwaysOnTop(false);
		setLocationRelativeTo(null);
		setTitle("Recursos");
		setResizable(true);
		setVisible(true);
	}

	private void cargarListaClienteProveedor() {
		log.info("cargarListaClienteProveedor");
		for (int i = 0; i < listaClienteProveedor.size(); i++) {

			modelTablaListaClientes.addRow(new Object[] { listaClienteProveedor.get(i).getIdBD(),
					listaClienteProveedor.get(i).getTipoClienteProveedor(),
					listaClienteProveedor.get(i).getIdentificacion(), listaClienteProveedor.get(i).getNombre(),
					listaClienteProveedor.get(i).getApellido(), listaClienteProveedor.get(i).getTelefono(),
					listaClienteProveedor.get(i).getCorreo(), listaClienteProveedor.get(i).getDireccion() });
		}
		
		tablaListaClientes.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 1 ||me.getClickCount() == 2) {     // to detect doble click events
	            	log.info("seleccionando en lista de clientes");
	            	
	               JTable target = (JTable)me.getSource();
	               int row = target.getSelectedRow(); // select a row
	               rowSeleccionada = row;
	               String idTipoCliente = tablaListaClientes.getValueAt(row, 0).toString();
	               idTipoClienteProeveedor.setText(idTipoCliente);
	               String tipoCliente =  tablaListaClientes.getValueAt(row, 1).toString();
	               comboTipoCP.setSelectedItem(tipoCliente);
	               String identificacion = tablaListaClientes.getValueAt(row, 2).toString();
	               identificacionCP.setText(identificacion);
	               String nombre =  tablaListaClientes.getValueAt(row, 3).toString();
	               nombreCP.setText(nombre);
	               String apellido = tablaListaClientes.getValueAt(row, 4).toString();
	               apellidoCP.setText(apellido);
	               String telefono = tablaListaClientes.getValueAt(row, 5).toString();
	               telefonoCP.setText(telefono);
	               String correo =  tablaListaClientes.getValueAt(row, 6).toString();
	               correoCP.setText(correo);
	              
	               String direccion = tablaListaClientes.getValueAt(row, 7).toString();
	               direccionCP.setText(direccion);
	              okButton.setEnabled(false);
	               log.info("seleccionando en lista de clientes - fin "+identificacion);
	              //JOptionPane.showMessageDialog(null,row+" "+column+" "+ table.getValueAt(row, column)); // get the value of a row and column.

	              
	            }
	         }
	      });
		
		log.info("cargarListaClienteProveedor - fin");

	}

	private void contruirPanelesCampos() {

		String campo = "";

		/* armar campo identificacion */
		campo = "identificacionCP";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo nombre */
		campo = "nombreCP";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo apellido */
		campo = "apellidoCP";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo correo */
		campo = "correoCP";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo telefono */
		campo = "telefonoCP";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo usuario */
		campo = "direccionCP";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo Rol */
		campo = "tipoCP";
		armarEtiquetas(campo);
		armarCampos(campo);

	}

	@Override
	protected boolean actionOK() {
		log.info("validando");
		if (identificacionCP.getText().equals("")) {
			identificacionCP.setBackground(Color.CYAN);
			identificacionCP.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setIdentificacion(identificacionCP.getText());
		if (nombreCP.getText().equals("")) {
			nombreCP.setBackground(Color.CYAN);
			nombreCP.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setNombre(nombreCP.getText());
		if (apellidoCP.getText().equals("")) {
			apellidoCP.setBackground(Color.CYAN);
			apellidoCP.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setApellido(apellidoCP.getText());

		if (telefonoCP.getText().equals("")) {
			telefonoCP.setBackground(Color.CYAN);
			telefonoCP.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setTelefono(telefonoCP.getText());
		datosClienteProveedor.setCorreo(correoCP.getText());
		if (direccionCP.getText().equals("")) {
			direccionCP.setBackground(Color.CYAN);
			direccionCP.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setDireccion(direccionCP.getText());
		String valorComboTipoCP = (String) comboTipoCP.getSelectedItem();
		if (comboTipoCP.getSelectedItem().equals("")) {
			comboTipoCP.setPopupVisible(true);
			comboTipoCP.requestFocusInWindow();
			return false;
		}
		
		String tipoCliente = tipoClienteId.get(valorComboTipoCP);
		datosClienteProveedor.setTipoClienteProveedor(tipoCliente);
		
		if (registrarClienteProveedor() == 0) {
			JOptionPane.showMessageDialog(null, "No se pudo registrar",
					"Mensaje de la aplicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		tipoCliente = idTipoCliente.get(datosClienteProveedor.getTipoClienteProveedor());
		datosClienteProveedor.setTipoClienteProveedor(tipoCliente);
		
		modelTablaListaClientes.addRow(new Object[] {datosClienteProveedor.getIdBD(), datosClienteProveedor.getTipoClienteProveedor()
				,datosClienteProveedor.getIdentificacion(), datosClienteProveedor.getNombre(), datosClienteProveedor.getApellido()
				,datosClienteProveedor.getTelefono(), datosClienteProveedor.getCorreo(), datosClienteProveedor.getDireccion()});
		limpiarCampos();
		JOptionPane.showMessageDialog(null, "Se registró con éxito",
				"Mensaje de la aplicación", JOptionPane.INFORMATION_MESSAGE);
		log.info("validando - fin "+ datosClienteProveedor.getIdentificacion());
		return false;
	}
	
	@Override
	protected boolean actionUpdate() {
		if (tablaListaClientes.getSelectedRows().length == 0) {
			 JOptionPane.showMessageDialog(parent,"Debe seleccionar un cliente o proveedor","Alerta",JOptionPane.WARNING_MESSAGE); 
			 return false;
		}
		if (idTipoClienteProeveedor.getText().equals("")) {
			idTipoClienteProeveedor.setBackground(Color.CYAN);
			idTipoClienteProeveedor.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setIdBD(idTipoClienteProeveedor.getText());
		if (identificacionCP.getText().equals("")) {
			identificacionCP.setBackground(Color.CYAN);
			identificacionCP.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setIdentificacion(identificacionCP.getText());
		if (nombreCP.getText().equals("")) {
			nombreCP.setBackground(Color.CYAN);
			nombreCP.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setNombre(nombreCP.getText());
		if (apellidoCP.getText().equals("")) {
			apellidoCP.setBackground(Color.CYAN);
			apellidoCP.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setApellido(apellidoCP.getText());

		if (telefonoCP.getText().equals("")) {
			telefonoCP.setBackground(Color.CYAN);
			telefonoCP.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setTelefono(telefonoCP.getText());
		datosClienteProveedor.setCorreo(correoCP.getText());
		if (direccionCP.getText().equals("")) {
			direccionCP.setBackground(Color.CYAN);
			direccionCP.requestFocusInWindow();
			return false;
		}
		datosClienteProveedor.setDireccion(direccionCP.getText());
		String valorComboTipoCP = (String) comboTipoCP.getSelectedItem();
		if (comboTipoCP.getSelectedItem().equals("")) {
			comboTipoCP.setBackground(Color.CYAN);
			comboTipoCP.requestFocusInWindow();
			return false;
		}
		String tipoCliente = tipoClienteId.get(valorComboTipoCP);
		datosClienteProveedor.setTipoClienteProveedor(tipoCliente);
		
		if (actualizarClienteProveedor() == 0) {
			JOptionPane.showMessageDialog(null, "No se pudo actualizar",
					"Mensaje de la aplicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		tipoCliente = idTipoCliente.get(datosClienteProveedor.getTipoClienteProveedor());
		datosClienteProveedor.setTipoClienteProveedor(tipoCliente);
		limpiarCampos();
		okButton.setEnabled(true);
		JOptionPane.showMessageDialog(null, "Se actualizó con éxito",
				"Mensaje de la aplicación", JOptionPane.INFORMATION_MESSAGE);
		modelTablaListaClientes.setValueAt(datosClienteProveedor.getIdBD(), rowSeleccionada, 0);
		modelTablaListaClientes.setValueAt(datosClienteProveedor.getTipoClienteProveedor(), rowSeleccionada, 1);
		modelTablaListaClientes.setValueAt(datosClienteProveedor.getIdentificacion(), rowSeleccionada, 2);
		modelTablaListaClientes.setValueAt(datosClienteProveedor.getNombre(), rowSeleccionada, 3);
		modelTablaListaClientes.setValueAt(datosClienteProveedor.getApellido(), rowSeleccionada, 4);
		modelTablaListaClientes.setValueAt(datosClienteProveedor.getTelefono(), rowSeleccionada, 5);
		modelTablaListaClientes.setValueAt(datosClienteProveedor.getCorreo(), rowSeleccionada, 6);
		modelTablaListaClientes.setValueAt(datosClienteProveedor.getDireccion(), rowSeleccionada, 7);
		
		
		
		return false;
	}
	
	@Override
	protected boolean actionOpen() {
		if (tablaListaClientes.getSelectedRows().length == 0) {
			 JOptionPane.showMessageDialog(parent,"Debe seleccionar un cliente o proveedor","Alerta",JOptionPane.WARNING_MESSAGE); 
			 return false;
		}

		//JOptionPane.showMessageDialog(parent,"Se abre el dialogo con el detalle de las citas","Alerta",JOptionPane.INFORMATION_MESSAGE); 
		//datos del cliente
		datosClienteProveedor.setIdBD(idTipoClienteProeveedor.getText());
		datosClienteProveedor.setIdentificacion(identificacionCP.getText());
		datosClienteProveedor.setNombre(nombreCP.getText());
		datosClienteProveedor.setApellido(apellidoCP.getText());
		datosClienteProveedor.setTelefono(telefonoCP.getText());
		datosClienteProveedor.setCorreo(correoCP.getText());
		datosClienteProveedor.setDireccion(direccionCP.getText());
		String valorComboTipoCP = (String) comboTipoCP.getSelectedItem();
		//datos del usuario
		//lista de citas del cliente

		String idRecurso = idTipoClienteProeveedor.getText();

	    DatosUsuario datosUsuario = new DatosUsuario();
	    datosUsuario.setId("1");
		
		try {
			DialogoDetallesRecurso dialogoDetalleRecurso = new DialogoDetallesRecurso(parent, datosClienteProveedor, datosUsuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void limpiarCampos() {
		idTipoClienteProeveedor.setText("");
		identificacionCP.setText("");
		nombreCP.setText("");
		apellidoCP.setText("");
		telefonoCP.setText("");
		correoCP.setText("");
		direccionCP.setText("");
		comboTipoCP.setSelectedItem("");
	}
	
	private void limpiarTabla(){
		DefaultTableModel temp = (DefaultTableModel) tablaListaClientes.getModel();
	    int filas = tablaListaClientes.getRowCount();

	    for (int a = 0; filas > a; a++) {
	        temp.removeRow(0);
	    }
	}
	
	private int registrarClienteProveedor() {
		conectorCliente.setDatosClienteProveedor(datosClienteProveedor);
		
		return conectorCliente.registrarClienteProveedor();
	}
	
	private int actualizarClienteProveedor() {
		conectorCliente.setDatosClienteProveedor(datosClienteProveedor);
		
		return conectorCliente.actualizarClienteProveedor();
		
	}

	public DatosClientesProveedor getDatosClienteProveedor() {
		return datosClienteProveedor;
	}



}
