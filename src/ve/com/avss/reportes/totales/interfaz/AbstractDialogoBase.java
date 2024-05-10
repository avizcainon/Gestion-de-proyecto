package ve.com.avss.reportes.totales.interfaz;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ve.com.avss.ventas.bean.DatosConfiguracion;
import ve.com.avss.ventas.util.Util;
import ve.com.avss.ventas.util.UtilFormas;

public abstract class AbstractDialogoBase extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3793747985286136079L;
	/** Command string para la accion de "Cancelar". */
	public static final String CMD_CANCEL = "cmd.cancel";
	/** Command string para la accion de "Aceptar". */
	public static final String CMD_OK = "cmd.ok";
	/** Indicador de acción. */ 
	protected boolean buttonFlag;
	protected boolean closeWindow = false;
	/** Panel de Botones. */
	protected JPanel botonesPanel = new JPanel();
	/** Botón aceptar. */
	protected JButton okButton = new JButton();
	/** Botón cancelar. */
	protected JButton cancelButton = new JButton();
	/*datos para la interfaz*/
	protected JPanel jContentPane = new JPanel(new BorderLayout());
	protected JPanel jContentPaneSuperior = new JPanel();
	protected JPanel jContentPaneCentral = new JPanel();
	protected JPanel jContentPaneInferior = new JPanel();
	protected JPanel jContentPaneDerecho = new JPanel();
	protected JPanel jContentPaneIzquierdo = new JPanel();
	protected JLabel eNombreFormulario = new JLabel();
	
	protected JLabel eFechaInicio = new JLabel("", JLabel.LEFT);
	protected JLabel eFechaFinal = new JLabel("", JLabel.LEFT);
	
	protected JLabel eConfiguracionReporte = new JLabel();
	protected JPanel jContentPaneConfiguracionReporte = new JPanel();
	protected JCheckBox jCheckTipoFactura = new JCheckBox();
	protected JCheckBox jCheckEstadoGananciaPerdida = new JCheckBox();
	protected JCheckBox jCheckListaFacurasDetalle = new JCheckBox();
	
	public AbstractDialogoBase(JFrame parent) {
		super(parent, true);
		setIconImage(Util.getIconImage());
	}
	
	protected void construirPanelBotones(String textoPrimerBoton, String textoSegundoBoton) {

		// Botón Aceptar
		okButton = new JButton();
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
