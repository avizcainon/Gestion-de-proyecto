package ve.com.avss.ventas.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.border.TitledBorder;

/** Utilidades. */
public class UtilFormas {

	public static JDialog positionDialogRelativeToParent(
			final Component parentComponent,
			final JDialog dialog) {
		final Dimension d = dialog.getSize();

		final int x = d.width / 2;
		final int y = d.height / 2;

		dialog.setBounds(parentComponent.getX()+x, parentComponent.getY()+y, d.width, d.height);
		return dialog;
	}

	public static Point positionComponentRelativeToParent(
			final Component parentComponent,
			final JDialog dialog) {
		final Dimension d = dialog.getSize();

		final int x = d.width / 2;
		final int y = d.height / 2;

		Point localizacion = new Point(parentComponent.getX()+x, parentComponent.getY()+y);
		return localizacion;
	}

	/**
	 * GridBagConstraints.
	 *
	 * @param parametros
	 *            Parámetros.
	 * @param anchor
	 *            Ancho.
	 * @param insets
	 *            Insets.
	 * @return GridBagConstraints: Caracteristicas.
	 */
	public static GridBagConstraints setGridContraints(String parametros,
			int anchor, Insets insets) {
		GridBagConstraints gbc = new GridBagConstraints();
		int pos;
		String propiedad;
		String valor;
		String tmp;

		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = anchor;

		if (insets != null) {
			gbc.insets = insets;
		}

		StringTokenizer stParm = new StringTokenizer(parametros, ",");
		while (stParm.hasMoreElements()) {
			tmp = stParm.nextToken();
			pos = tmp.indexOf('=');
			propiedad = tmp.substring(0, pos);
			valor = tmp.substring(pos + 1);

			if ("gridx".equals(propiedad)) {
				gbc.gridx = Integer.parseInt(valor);
			} else if ("gridy".equals(propiedad)) {
				gbc.gridy = Integer.parseInt(valor);
			} else if ("ipadx".equals(propiedad)) {
				gbc.ipadx = Integer.parseInt(valor);
			} else if ("ipady".equals(propiedad)) {
				gbc.ipady = Integer.parseInt(valor);
			} else if ("gridwidth".equals(propiedad)) {
				gbc.gridwidth = Integer.parseInt(valor);
			} else if ("gridheight".equals(propiedad)) {
				gbc.gridheight = Integer.parseInt(valor);
			} else if ("fill".equals(propiedad)) {
				gbc.fill = Integer.parseInt(valor);
			}

			if ("weightx".equals(propiedad)) {
				gbc.weightx = Double.parseDouble(valor);
			}
			if ("weighty".equals(propiedad)) {
				gbc.weighty = Double.parseDouble(valor);
			}
		}
		return gbc;
	}
	
public static TitledBorder crearBordePanel(String nombreBorde) {
		
		TitledBorder titledBorder = javax.swing.BorderFactory
				.createTitledBorder(BorderFactory
				.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
				nombreBorde,
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				null, null);
		titledBorder.setTitleColor(java.awt.Color.BLACK);
		titledBorder.setTitleFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 11));
		
		return titledBorder;
	}
}
