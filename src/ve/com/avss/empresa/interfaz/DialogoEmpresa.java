package ve.com.avss.empresa.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.BD.conectores.ConectorEmpresa;
import ve.com.avss.empresa.bean.DatosEmpresa;
import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.util.LeerConfiguracion;
import ve.com.avss.ventas.util.Util;

public class DialogoEmpresa extends AbstractDialogoBase {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DialogoEmpresa.class);
	private DatosEmpresa datosEmpresa = new DatosEmpresa();
	private ConectorEmpresa conectorEmpresa = new ConectorEmpresa();
	private String actionCommand = "";
	private JFileChooser jfLogoE = new JFileChooser();
  
	public DialogoEmpresa(JFrame parent) throws Exception {
		super(parent);
		log.info("Constructor - construyendo paneles e interfaz");
		contruirPanelesCampos();
		cargarDatosEmpresa();
		String[ ] botones = new String[3];
		botones[0] =  "Continuar";
		botones[1] =  "Cancelar";
		botones[2] =  "Actualizar";
		construirPanelBotones(botones);

		okButton.setVisible(false);
		jPanelCentral.setLayout(new BoxLayout(jPanelCentral, BoxLayout.Y_AXIS));
		
		/*datos nombre empresa*/
		jPanelNombreE.add(eNombreE);
		jPanelNombreE.add(nombreE);
		jPanelCentral.add(jPanelNombreE);
		
		/*datos rif empresa*/
		jPanelRifE.add(eRifE);
		jPanelRifE.add(rifE);
		jPanelCentral.add(jPanelRifE);
		/*datos direccion empresa*/
		jPanelDireccionE.add(eDireccionE);
		jPanelDireccionE.add(direccionE);
		jPanelCentral.add(jPanelDireccionE);
		/*datos telefono empresa*/
		jPanelTelefonoE.add(eTelefonoE);
		jPanelTelefonoE.add(telefonoE);
		jPanelCentral.add(jPanelTelefonoE);
		/*datos correo empresa*/
		jPanelCorreoE.add(eCorreoE);
		jPanelCorreoE.add(correoE);
		jPanelCentral.add(jPanelCorreoE);
//		/*datos monto divisa*/
//		jPanelMontoDivisaE.add(eMontoDivisaE);
//		jPanelMontoDivisaE.add(montoDivisaE);
//		jPanelCentral.add(jPanelMontoDivisaE);
//		/*datos impuesto*/
//		jPanelImpuestoE.add(eImpuestoE);
//		jPanelImpuestoE.add(impuestoE);
//		jPanelCentral.add(jPanelImpuestoE);
//		/*datos validar cantidad de producto*/
//		jPanelValidadCantidadProductoE.add(checkBoxCantidadProducto);
//		jPanelCentral.add(jPanelValidadCantidadProductoE);
		/*datos ruta instalacion*/
		jPanelRutaInstalacion.add(eRutaInstalacionTitulo);
		jPanelRutaInstalacion.add(eRutaInstalacion);
		
		jPanelCentral.add(jPanelRutaInstalacion);

		/*datos logo empresa*/
		jPanelLogoE.add(eLogoE);
		jPanelLogoE.add(logoE);
		logoE.setToolTipText(datosEmpresa.getLogo());
		jPanelLogoE.add(eImagenLogoE);
		jPanelCentral.add(jPanelLogoE);
		logoE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				log.info("Constructor - seleecionando logo");
				
				jfLogoE.showOpenDialog(parent);
				jfLogoE.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				File archivo = jfLogoE.getSelectedFile();
				if (archivo != null) {
					log.info("Constructor - logo cargado");
					log.info(jfLogoE.getSelectedFile().getName());
					log.info(archivo.getAbsolutePath());
					log.info(archivo.getAbsoluteFile());
					log.info(archivo.getAbsoluteFile().getAbsolutePath());
					log.info(archivo.getName());
					log.info(jfLogoE.getSelectedFile().toString().replace(archivo.getName(),""));
					eLogoE.setText(archivo.getAbsolutePath());
					mostrarLogo();
				}
			}
		});
		


		
		

		/*datos nombre formulario*/
		eNombreFormulario.setText("Registro de la empresa");
		eNombreFormulario.setFont(DatosConfiguracion.TITULO_1_BOLD);
		eNombreFormulario.setForeground(Color.DARK_GRAY);
		jContentPane.add(eNombreFormulario,BorderLayout.NORTH);
		jContentPane.add(jPanelCentral,BorderLayout.CENTER);
		jContentPane.add(botonesPanel,BorderLayout.SOUTH);

		getContentPane().add(jContentPane);
		pack();
		setAlwaysOnTop(false);
		setTitle("Registro de la empresa");
		setSize(Util.anchoPantalla()-200, Util.altoPantalla()-200);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void contruirPanelesCampos() {
		log.info("Constructor - construyendo campos y etiquetas");
		String campo = "";

		/* armar campo nombre */
		campo = "nombreE";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo direccion */
		campo = "direccionE";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo RIF */
		campo = "rifE";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo logo */
		campo = "logoE";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo divisa */
		campo = "montoDivisaE";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo impuesto */
		campo = "impuestoE";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo correo */
		campo = "correoE";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo telefono */
		campo = "telefonoE";
		armarEtiquetas(campo);
		armarCampos(campo);
		/* armar campo validar cantidad de producto */
		campo = "validarCantidadProductoE";
		armarEtiquetas(campo);
		

	}
	
	private void cargarDatosEmpresa() throws Exception {
		log.info("Constructor - cargando datos de la empresa");
		datosEmpresa = conectorEmpresa.obtenerDatosEmpresa();
		nombreE.setText(datosEmpresa.getNombre());
		rifE.setText(datosEmpresa.getRif());
		direccionE.setText(datosEmpresa.getDireccion());
		eLogoE.setText(datosEmpresa.getLogo());
		telefonoE.setText(datosEmpresa.getTelefono());
		correoE.setText(datosEmpresa.getCorreo());
		montoDivisaE.setText(Util.formatoMonedaGeneral(new Double(datosEmpresa.getMontoDivisa())));
		impuestoE.setText(Util.formatoMonedaGeneral(new Double(datosEmpresa.getImpuesto())*100));
		eRutaInstalacion.setText(datosEmpresa.getRutaInstalacion());
		eRutaInstalacion.setToolTipText(datosEmpresa.getRutaInstalacion());
		if (datosEmpresa.isValidarCantidadProducto()) {
			checkBoxCantidadProducto.setSelected(true);
		}else {
			checkBoxCantidadProducto.setSelected(false);
		}
		mostrarLogo();
		
		
	}
	
	private void mostrarLogo() {
		log.info("Constructor - cargando logo de la empresa "+datosEmpresa.getLogo());
		ImageIcon imgIcon = new ImageIcon(eLogoE.getText());
        Image imgEscalada = imgIcon.getImage().getScaledInstance(eImagenLogoE.getWidth(),
        		eImagenLogoE.getHeight(), Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        eImagenLogoE.setIcon(iconoEscalado);
		
	}

	@Override
	protected boolean actionOK() {
		if (nombreE.getText().equals("")) {
			nombreE.setBackground(Color.CYAN);
			nombreE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setNombre(nombreE.getText());
		
		if (rifE.getText().equals("")) {
			rifE.setBackground(Color.CYAN);
			rifE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setRif(rifE.getText());
		
		if (direccionE.getText().equals("")) {
			direccionE.setBackground(Color.CYAN);
			direccionE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setDireccion(direccionE.getText());
		
		if (impuestoE.getText().equals("")) {
			impuestoE.setBackground(Color.CYAN);
			impuestoE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setImpuesto(impuestoE.getText());
		if (eLogoE.getText().equals("")) {
			eLogoE.setBackground(Color.CYAN);
			eLogoE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setValidarCantidadProducto(checkBoxCantidadProducto.isSelected());
		datosEmpresa.setLogo(eLogoE.getText());
		datosEmpresa.setRutaInstalacion(LeerConfiguracion.getRutaInstalacion());
		
		 moverLogo();
		 String rutaLogo = datosEmpresa.getRutaInstalacion()+"\\proyectos\\img\\"+jfLogoE.getSelectedFile().getName();
		 datosEmpresa.setLogo(rutaLogo);
		actionCommand = "registrar";
		
		return true;
	}
	
	@Override
	protected boolean actionUpdate() {
		if (nombreE.getText().equals("")) {
			nombreE.setBackground(Color.CYAN);
			nombreE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setNombre(nombreE.getText());
		
		if (rifE.getText().equals("")) {
			rifE.setBackground(Color.CYAN);
			rifE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setRif(rifE.getText());
		
		if (direccionE.getText().equals("")) {
			direccionE.setBackground(Color.CYAN);
			direccionE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setDireccion(direccionE.getText());
		
		if (correoE.getText().equals("")) {
			correoE.setBackground(Color.CYAN);
			correoE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setCorreo(correoE.getText());
		
		if (telefonoE.getText().equals("")) {
			telefonoE.setBackground(Color.CYAN);
			telefonoE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setTelefono(telefonoE.getText());
		if (eLogoE.getText().equals("")) {
			eLogoE.setBackground(Color.CYAN);
			eLogoE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setMontoDivisa(Util.formatMontoDouble(montoDivisaE.getText())+"");
		
		
		if (datosEmpresa.getRutaInstalacion().equals("")) {
			datosEmpresa.setRutaInstalacion(LeerConfiguracion.getRutaInstalacion());
		}
		if (impuestoE.getText().equals("")) {
			impuestoE.setBackground(Color.CYAN);
			impuestoE.requestFocusInWindow();
			return false;
		}
		datosEmpresa.setImpuesto(impuestoE.getText().replace(",", "."));
		datosEmpresa.setValidarCantidadProducto(checkBoxCantidadProducto.isSelected());
		if (eLogoE.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar el logo de la empresa	","Mensaje de la aplicación", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if (!eLogoE.getText().equals(datosEmpresa.getLogo())) {
			moverLogo();
			datosEmpresa.setLogo(eLogoE.getText());
			 String rutaLogo = datosEmpresa.getRutaInstalacion()+"\\proyectos\\img\\"+"logoEmpresa"+Util.obtenerExtensionArchivo(jfLogoE.getSelectedFile().getName());
			 datosEmpresa.setLogo(rutaLogo);
		}
		
		
		actionCommand = "actualizar";
		return true;
	}
	
	private void moverLogo() {
		log.info("Copiando archivos de la aplicacion");
		File ficheroCopiar = null;
		File ficheroDestino = null;
		 /*copiando archivo de base de datos*/
		ficheroCopiar = new File(jfLogoE.getSelectedFile().toString().replace(jfLogoE.getSelectedFile().getName(),""), jfLogoE.getSelectedFile().getName());
        ficheroDestino = new File(datosEmpresa.getRutaInstalacion()+"\\proyectos\\img\\", jfLogoE.getSelectedFile().getName().toString().replace(jfLogoE.getSelectedFile().getName(), "logoEmpresa"+Util.obtenerExtensionArchivo(jfLogoE.getSelectedFile().getName())));
        if (ficheroCopiar.exists()) {
            try {
				Files.copy(Paths.get(ficheroCopiar.getAbsolutePath()), Paths.get(ficheroDestino.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
				log.info("Se ha copiado con exito el logo de la aplicacion");
				JOptionPane.showMessageDialog(null,"Se ha copiado con exito el logo de la aplicacion","Alerta",JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
            	JOptionPane.showMessageDialog(null,"No se pudo copiar el logo de la aplicacion","Alerta",JOptionPane.ERROR_MESSAGE);
				log.error("No se pudo copiar el logo de la aplicacion");
			}
        } else {
        	log.error("Archivo de el logo de la aplicacion no existe");
        	JOptionPane.showMessageDialog(null,"Archivo de el logo de la aplicacion no existe","Alerta",JOptionPane.ERROR_MESSAGE);
        }
	}

	public String getActionCommand() {
		return actionCommand;
	}

	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}

	public DatosEmpresa getDatosEmpresa() {
		return datosEmpresa;
	}

	
	

}
