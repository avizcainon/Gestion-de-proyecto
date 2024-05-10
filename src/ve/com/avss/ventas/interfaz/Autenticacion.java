package ve.com.avss.ventas.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.BD.conectores.ConectorEmpresa;
import ve.com.avss.empresa.BD.conectores.ConectorUsuario;
import ve.com.avss.empresa.bean.DatosEmpresa;
import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.util.AppJButton;
import ve.com.avss.ventas.util.CONSTANTES_INTERACCION;
import ve.com.avss.ventas.util.Util;
import ve.com.avss.ventas.util.UtilFormas;

public class Autenticacion extends AbstractDialogoBase {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Autenticacion.class);
	protected DatosUsuario datosUsuario = new DatosUsuario();
	protected DatosEmpresa datosEmpresa = new DatosEmpresa();
	private ConectorUsuario conectorUsuario = null;
	private ConectorEmpresa conectorEmpresa = new ConectorEmpresa();
	public boolean isAutenticar = false;
	private Inicio inicio = null;
	private JFrame parent = null;
	
	
	public Autenticacion (JFrame parent) throws Exception {
		super(parent);
		this.parent = parent;
		log.info("Constructor - construyendo paneles e interfaz");
		try {
			this.datosEmpresa = conectorEmpresa.obtenerDatosEmpresa();
		} catch (Exception e) {
			log.info("error: "+e);
			throw new Exception(e.getMessage());
		}
		
		contruirPaneles();
		construirPanelBotones("Entrar", "Cancelar"); 
		pack();
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Autenticación | Sistema de Gestión de proyectos");
		setSize(600, 400);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	
	private void contruirPaneles() {
		String campo = "";

		/* armar campo identificacion */
		campo = "usuario";
		armarEtiquetas(campo);
		armarCampos(campo);

		/* armar campo nombre */
		campo = "clave";
		armarEtiquetas(campo);
		armarCampos(campo);
		
		JLabel lbImage = new JLabel();
		lbImage.setBorder(DatosConfiguracion.LINEA_GRIS_CLARA_1PX);
		lbImage.setSize(140, 140);
		lbImage.setPreferredSize(new Dimension(140, 140));
		JPanel panel = new JPanel();
		
		ImageIcon imgIcon = null;
		if (datosEmpresa.getLogo().equals("")) {
			imgIcon = new ImageIcon(datosEmpresa.getRutaInstalacion()+"\\proyectos"+CONSTANTES_INTERACCION.PATH_IMAGENES+"iconos\\logoAVSS.png");
		}else {
			imgIcon = new ImageIcon(datosEmpresa.getLogo());
		}
		
        Image imgEscalada = imgIcon.getImage().getScaledInstance(lbImage.getWidth(),
                lbImage.getHeight(), Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        lbImage.setIcon(iconoEscalado);
        panel.add(lbImage);
        ayudaPanel.setPreferredSize(new Dimension(110,30));
        jContentPaneSuperior.setLayout(new BoxLayout(jContentPaneSuperior, BoxLayout.Y_AXIS));
        jContentPaneSuperior.add(ayudaPanel);
        
        jContentPaneSuperior.add(panel);
       
       
		jContentPaneUsuario.add(eUsuario);
		jContentPaneUsuario.add(usuario);
		jContentPaneClave.add(eClave);
		jContentPaneClave.add(clave);
		jContentPaneCentral.setLayout(new BoxLayout(jContentPaneCentral, BoxLayout.Y_AXIS));
		jContentPaneCentral.add(jContentPaneUsuario);
		jContentPaneCentral.add(jContentPaneClave);
		
		

		
		
		jContentPane.add(jContentPaneSuperior,BorderLayout.NORTH);
		
		jContentPane.add(jContentPaneCentral,BorderLayout.CENTER);
		jContentPane.add(botonesPanel,BorderLayout.SOUTH);
		
		getContentPane().add(jContentPane);
		
	}
	
	private void buscarDatosUsuario() {
		log.info("buscando los datos de usuario "+datosUsuario.getUsuario());
		try {
			conectorUsuario = new ConectorUsuario();
			conectorUsuario.setDatosUsuario(datosUsuario);
			this.datosUsuario = conectorUsuario.obtenerDatosUsuario();
		} catch (Exception e) {
			log.info("error: "+e);
		}
		
	}
	

	

	@Override
	protected boolean actionOK() {
		log.info("validando datos de usuario");
		this.datosUsuario = new DatosUsuario();
		if (usuario.getText().equals("")) {
			usuario.setBackground(Color.CYAN);
			usuario.requestFocusInWindow();
			return false;
		}
		datosUsuario.setUsuario(usuario.getText());
		char[] arrayC = clave.getPassword();
		String pass = new String(arrayC);
		
		if (pass.equals("")) {
			clave.setBackground(Color.CYAN);
			clave.requestFocusInWindow();
			return false;
		}
		
		try {
			buscarDatosUsuario();
		} catch (Exception e) {
			log.info("error: "+e);
			JOptionPane.showMessageDialog(null, e.getMessage(),CONSTANTES_INTERACCION.MENSAJE_APLICACION_DIALOGO,JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		
		
		if (datosUsuario == null) {
			ayudaPanel.setText("No existe registro con ese usuario");
			log.info("No existe registro con ese usuario");
			usuario.setText("");
			usuario.requestFocus();
			clave.setText("");
			return false;
		}
		
		if (!datosUsuario.getClave().equals(pass)) {
			ayudaPanel.setText("Las credenciales son incorrectas");
			log.info("Las credenciales son incorrectas");
			usuario.setText("");
			clave.setText("");
			usuario.requestFocus();
			return false;
		}
		

		isAutenticar = true;
		return true;
	}
	
	protected void construirPanelBotones(String textoPrimerBoton, String textoSegundoBoton) {

		// Botón Aceptar
		okButton = new AppJButton("","");
		okButton = okButton.crearBoton();
		okButton.setText(textoPrimerBoton);
		okButton.setActionCommand(CMD_OK);
		okButton.setIcon(DatosConfiguracion.iAceptar);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (actionOK()) {
					dispose();
					try {
						inicio = new Inicio(parent, datosUsuario);
					} catch (Exception e) {
						log.error(e);
						
					}
				}
				
				
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
	
	
	



	public DatosUsuario getDatosUsuario() {
		return datosUsuario;
	}

	public void setDatosUsuario(DatosUsuario datosUsuario) {
		this.datosUsuario = datosUsuario;
	}
	
	public boolean isAutenticar() {
		return isAutenticar;
	}

	public void setAutenticar(boolean isAutenticar) {
		this.isAutenticar = isAutenticar;
	}

}
