package ve.com.avss.proyectos.interfaz;

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

import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.proyectos.BD.ejecutores.QueryProyecto;
import ve.com.avss.proyectos.bean.DatosProyecto;
import ve.com.avss.ventas.bean.DatosConfiguracion;

public class DialogoProyecto extends AbstractDialogoBase {
	private DatosProyecto datosProyecto = new DatosProyecto();
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DialogoProyecto.class);
	private int rowSeleccionada = 0;
	public DialogoProyecto(JFrame parent) {
		super(parent);
		log.info("Constructor - construyendo paneles e interfaz");
		contruirPanelesCampos();
		construirTablaProyectos();
		cargarListaProyectos();
		String[ ] botones = new String[4];
		botones[0] =  "Registrar";
		botones[1] =  "Cancelar";
		botones[2] =  "Actualizar";
		botones[3] =  "Abrir";
		construirPanelBotones(botones);
		jContentPaneIzquierdo.setLayout(new BoxLayout(jContentPaneIzquierdo, BoxLayout.Y_AXIS));
		
		jPanelBuscar.add(eBuscarProyecto);
		jPanelBuscar.add(buscarProyecto);
		jContentPaneIzquierdo.add(jPanelBuscar);
		
		idTipoClienteProeveedor.setVisible(false);
		idTipoClienteProeveedor.setPreferredSize(new Dimension(20,20));
		jPanelTipoClienteProveedor.add(idTipoClienteProeveedor);
		jPanelTipoClienteProveedor.add(eStatusProyecto);
		jPanelTipoClienteProveedor.add(comboStatus);
		jContentPaneIzquierdo.add(jPanelTipoClienteProveedor);

		jPanelIdentificacion.add(eDescripcion);
		jPanelIdentificacion.add(descripcion);
		jContentPaneIzquierdo.add(jPanelIdentificacion);

		
		

		
		jPanelApellido.add(eFechaDesarrollo);
		jPanelApellido.add(fechaDesarrollo);
		jContentPaneIzquierdo.add(jPanelApellido);

		
		jPanelTelefono.add(eFechaCertificacion);
		jPanelTelefono.add(fechaCertificacion);
		jContentPaneIzquierdo.add(jPanelTelefono);
		
		jPanelNombre.add(eObservacion);
		jPanelNombre.add(scrollPaneObservacion);
		jContentPaneIzquierdo.add(jPanelNombre);


		jContentPaneIzquierdo.add(jPanelCorreo);


		jContentPaneIzquierdo.add(jPanelDireccion);

		eNombreFormulario.setText("Registro de Proyectos");
		eNombreFormulario.setFont(DatosConfiguracion.TITULO_1_BOLD);
		eNombreFormulario.setForeground(Color.DARK_GRAY);
		jContentPaneSuperior.add(eNombreFormulario);

		jContentPane.add(jContentPaneSuperior, BorderLayout.NORTH);
		jContentPane.add(jContentPaneIzquierdo, BorderLayout.WEST);
		jContentPane.add(jContentPaneCentral, BorderLayout.CENTER);
		jContentPane.add(botonesPanel, BorderLayout.SOUTH);
		buscarProyecto.addKeyListener(new KeyAdapter() {
			
			@Override
			  public void keyReleased(KeyEvent e) {
				System.out.println(	buscarProyecto.getText());
				
				try {
					listaProyectos = QueryProyecto.obtenerListaProyecto(buscarProyecto.getText());
					limpiarTabla();
					cargarListaProyectos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		buscarProyecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.info("guarda cita");
				
				
				log.info("guardar cita - fin");
			}
		});

		getContentPane().add(jContentPane);
		pack();
		setAlwaysOnTop(false);
		setLocationRelativeTo(null);
		setTitle("Proyectos");
		setResizable(true);
		setVisible(true);
	}

	private void cargarListaProyectos() {
		log.info("cargarListaProyectos");
		for (int i = 0; i < listaProyectos.size(); i++) {

			modelTablaListaProyectos.addRow(new Object[] { listaProyectos.get(i).getIdBD(),
					listaProyectos.get(i).getStatus(),
					listaProyectos.get(i).getDescripcion(), listaProyectos.get(i).getFechaDesarrollo(),
					listaProyectos.get(i).getFechaCertificacion(), listaProyectos.get(i).getObservacion() });
		}
		
		tablaListaProyectos.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 1 ||me.getClickCount() == 2) {     // to detect doble click events
	            	log.info("seleccionando en lista de clientes");
	            	
	               JTable target = (JTable)me.getSource();
	               int row = target.getSelectedRow(); // select a row
	               rowSeleccionada = row;
	               String idTipoCliente = tablaListaProyectos.getValueAt(row, 0).toString();
	               idTipoClienteProeveedor.setText(idTipoCliente);
	               String tipoCliente =  tablaListaProyectos.getValueAt(row, 1).toString();
	               comboStatus.setSelectedItem(tipoCliente);
	               String identificacion = tablaListaProyectos.getValueAt(row, 2).toString();
	               descripcion.setText(identificacion);
	               String apellido = tablaListaProyectos.getValueAt(row, 3).toString();
	               fechaDesarrollo.setText(apellido);
	               String telefono = tablaListaProyectos.getValueAt(row, 4).toString();
	               fechaCertificacion.setText(telefono);
	               String nombre =  tablaListaProyectos.getValueAt(row, 5).toString();
	               textAreaObservacion.setText(nombre);

	               log.info("seleccionando en lista de proyectos - fin "+identificacion);
	              //JOptionPane.showMessageDialog(null,row+" "+column+" "+ table.getValueAt(row, column)); // get the value of a row and column.

	              
	            }
	         }
	      });
		
		log.info("cargarListaProyectos - fin");

	}

	private void contruirPanelesCampos() {

		String campo = "";

		/* armar campo descripcion */
		campo = "descripcion";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo observacion */
		campo = "observacion";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo fechaDesarrollo */
		campo = "fechaDesarrollo";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo fechaCertificacion */
		campo = "fechaCertificacion";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo status */
		campo = "comboStatus";
		armarEtiquetas(campo);
		armarCampos(campo);


	}

	@Override
	protected boolean actionOK() {
		log.info("validando");
		if (descripcion.getText().equals("")) {
			descripcion.setBackground(Color.CYAN);
			descripcion.requestFocusInWindow();
			return false;
		}
		datosProyecto.setDescripcion(descripcion.getText());
		

		datosProyecto.setObservacion(textAreaObservacion.getText());
		
		if (fechaDesarrollo.getText().equals("")) {
			fechaDesarrollo.setBackground(Color.CYAN);
			fechaDesarrollo.requestFocusInWindow();
			return false;
		}
		datosProyecto.setFechaDesarrollo(fechaDesarrollo.getText());


		datosProyecto.setFechaCertificacion(fechaCertificacion.getText());

		String valorComboTipoCP = (String) comboStatus.getSelectedItem();
		if (comboStatus.getSelectedItem().equals("")) {
			comboStatus.setPopupVisible(true);
			comboStatus.requestFocusInWindow();
			return false;
		}
		
		String status = statusId.get(valorComboTipoCP);
		datosProyecto.setStatus(status);
		
		if (registrarProyecto() == 0) {
			JOptionPane.showMessageDialog(null, "No se pudo registrar",
					"Mensaje de la aplicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		status = idStatus.get(datosProyecto.getStatus());
		datosProyecto.setStatus(status);
		
		modelTablaListaProyectos.addRow(new Object[] {datosProyecto.getIdBD(), datosProyecto.getStatus()
				,datosProyecto.getDescripcion(), datosProyecto.getFechaDesarrollo(), datosProyecto.getFechaCertificacion()
				,datosProyecto.getObservacion()});
		limpiarCampos();
		JOptionPane.showMessageDialog(null, "Se registró con éxito",
				"Mensaje de la aplicación", JOptionPane.INFORMATION_MESSAGE);
		log.info("validando - fin "+ datosProyecto.getDescripcion());
		return false;
	}
	
	@Override
	protected boolean actionUpdate() {
		if (tablaListaProyectos.getSelectedRows().length == 0) {
			 JOptionPane.showMessageDialog(parent,"Debe seleccionar un cliente o proveedor","Alerta",JOptionPane.WARNING_MESSAGE); 
			 return false;
		}
		if (idTipoClienteProeveedor.getText().equals("")) {
			idTipoClienteProeveedor.setBackground(Color.CYAN);
			idTipoClienteProeveedor.requestFocusInWindow();
			return false;
		}
		datosProyecto.setIdBD(idTipoClienteProeveedor.getText());
		if (descripcion.getText().equals("")) {
			descripcion.setBackground(Color.CYAN);
			descripcion.requestFocusInWindow();
			return false;
		}
		datosProyecto.setDescripcion(descripcion.getText());

		datosProyecto.setObservacion(textAreaObservacion.getText());
		
		if (fechaDesarrollo.getText().equals("")) {
			fechaDesarrollo.setBackground(Color.CYAN);
			fechaDesarrollo.requestFocusInWindow();
			return false;
		}
		datosProyecto.setFechaDesarrollo(fechaDesarrollo.getText());


		datosProyecto.setFechaCertificacion(fechaCertificacion.getText());

		String valorComboTipoCP = (String) comboStatus.getSelectedItem();
		if (comboStatus.getSelectedItem().equals("")) {
			comboStatus.setBackground(Color.CYAN);
			comboStatus.requestFocusInWindow();
			return false;
		}
		String status = statusId.get(valorComboTipoCP);
		datosProyecto.setStatus(status);
		
		if (actualizarProyecto() == 0) {
			JOptionPane.showMessageDialog(null, "No se pudo actualizar",
					"Mensaje de la aplicación", JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			try {
				QueryProyecto.actualizarStatusTarea(idTipoClienteProeveedor.getText(),status);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		status = idStatus.get(datosProyecto.getStatus());
		datosProyecto.setStatus(status);
		limpiarCampos();
		okButton.setEnabled(true);
		JOptionPane.showMessageDialog(null, "Se actualizó con éxito",
				"Mensaje de la aplicación", JOptionPane.INFORMATION_MESSAGE);
		modelTablaListaProyectos.setValueAt(datosProyecto.getIdBD(), rowSeleccionada, 0);
		modelTablaListaProyectos.setValueAt(datosProyecto.getStatus(), rowSeleccionada, 1);
		modelTablaListaProyectos.setValueAt(datosProyecto.getDescripcion(), rowSeleccionada, 2);
		modelTablaListaProyectos.setValueAt(datosProyecto.getFechaDesarrollo(), rowSeleccionada, 3);
		modelTablaListaProyectos.setValueAt(datosProyecto.getFechaCertificacion(), rowSeleccionada, 4);
		modelTablaListaProyectos.setValueAt(datosProyecto.getObservacion(), rowSeleccionada, 5);

		
		
		
		return false;
	}
	
	@Override
	protected boolean actionOpen() {
		if (tablaListaProyectos.getSelectedRows().length == 0) {
			 JOptionPane.showMessageDialog(parent,"Debe seleccionar un cliente o proveedor","Alerta",JOptionPane.WARNING_MESSAGE); 
			 return false;
		}

		//JOptionPane.showMessageDialog(parent,"Se abre el dialogo con el detalle de las citas","Alerta",JOptionPane.INFORMATION_MESSAGE); 
		//datos del cliente
		//datos del usuario
		//lista de citas del cliente
		
	    try {
			datosProyecto = QueryProyecto.obtenerProyecto(idTipoClienteProeveedor.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    DatosUsuario datosUsuario = new DatosUsuario();
	    datosUsuario.setId("1");
		
		try {
			DialogoDetallesProyecto dialogo = new DialogoDetallesProyecto(parent, datosProyecto, datosUsuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void limpiarCampos() {
		idTipoClienteProeveedor.setText("");
		descripcion.setText("");
		textAreaObservacion.setText("");
		fechaDesarrollo.setText("");
		fechaCertificacion.setText("");
		
		comboStatus.setSelectedItem("");
	}
	
	private void limpiarTabla(){
		DefaultTableModel temp = (DefaultTableModel) tablaListaProyectos.getModel();
	    int filas = tablaListaProyectos.getRowCount();

	    for (int a = 0; filas > a; a++) {
	        temp.removeRow(0);
	    }
	}
	
	private int registrarProyecto() {
		conectorProyecto.setDatosProyecto(datosProyecto);
		
		return conectorProyecto.registrarProyecto();
	}
	
	private int actualizarProyecto() {
		conectorProyecto.setDatosProyecto(datosProyecto);
		
		return conectorProyecto.actualizarProyecto();
		
	}

	public DatosProyecto getDatosProyecto() {
		return datosProyecto;
	}



}
