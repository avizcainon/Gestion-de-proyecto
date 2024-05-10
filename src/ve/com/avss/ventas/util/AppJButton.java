package ve.com.avss.ventas.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.JButton;



/** Botón personalizado. */
public class AppJButton extends JButton {
	/** Serial Version Number. */
	private static final long serialVersionUID = -949101616411429346L;

	/** Variable encargada de almacenar el indice donde esta ubicada. */
	private int index;

	private String value = "";
	private String tipoProceso = "";
	private String tipoBoton = "";
	/** Constructor para AppJButton.java. 
	 * @return 
	 * @return */
	/*tipoBoton determina el color
	 * tipoProceso determina la intensidad del color
	 * */
	public AppJButton(String tipoBoton, String tipoProceso) {
		
		this.tipoBoton = tipoBoton;
		this.tipoProceso = tipoProceso;
		keyPressedListener();
	}
	


	/**
	 * Constructor para AppJButton.java.
	 *
	 * @param icon
	 *            Imagen a desplegar sobre el botón.
	 */
	public AppJButton(Icon icon) {
		super(icon);
		keyPressedListener();
	}


	public AppJButton() {
		
	}



	public AppJButton crearBoton() {
		AppJButton boton = new AppJButton();
//		Border line = new LineBorder(Color.LIGHT_GRAY,5);
//		Border margin = new EmptyBorder(5, 5,5, 5);
//		Border compound = new CompoundBorder(line, margin);
//		boton.setBorder(compound);
		
		switch (tipoBoton) {
		case "primary":
			if (tipoProceso.equals("proceso")) {
				boton.setBackground(new Color(0, 123, 255));
				boton.setForeground(new Color(254, 254, 254));
				
			}else if(tipoProceso.equals("subproceso")) {
				boton.setBackground(new Color(177, 213, 255));
				boton.setForeground(new Color(0, 64, 133));
			}
			
			break;
		case "success":
			if (tipoProceso.equals("proceso")) {
				boton.setBackground(new Color(40, 167, 69));
				boton.setForeground(new Color(254, 254, 254));
				
			}else if(tipoProceso.equals("subproceso")) {
				boton.setBackground(new Color(212, 237, 218));
				boton.setForeground(new Color(21, 87, 36));
			}
			
			break;
		case "info":
			if (tipoProceso.equals("proceso")) {
				boton.setBackground(new Color(23, 162, 184));
				boton.setForeground(new Color(254, 254, 254));
			}else if(tipoProceso.equals("subproceso")) {
				boton.setBackground(new Color(209, 236, 241));
				boton.setForeground(new Color(0, 123, 255));
			}
			
			break;
		case "warning":
			if (tipoProceso.equals("proceso")) {
				boton.setBackground(new Color(255, 193, 7));
				boton.setForeground(new Color(254, 254, 254));
			}else if(tipoProceso.equals("subproceso")) {
				boton.setBackground(new Color(255, 243, 205));
				boton.setForeground(new Color(133, 100, 4));
			}
			
			break;
		case "danger":
			if (tipoProceso.equals("proceso")) {
				boton.setBackground(new Color(220, 53, 69));
				boton.setForeground(new Color(254, 254, 254));
			}else if(tipoProceso.equals("subproceso")) {
				boton.setBackground(new Color(248, 215, 218));
				boton.setForeground(new Color(114, 28, 36));
			}
			
			break;
		case "secondary":
			if (tipoProceso.equals("proceso")) {
				boton.setBackground(new Color(108, 117, 125));
				boton.setForeground(new Color(254, 254, 254));
			}else if(tipoProceso.equals("subproceso")) {
				boton.setBackground(new Color(129, 129, 130));
				boton.setForeground(new Color(254, 254, 254));
			}
			
			break;
		case "light":
			if (tipoProceso.equals("proceso")) {
				
			}else if(tipoProceso.equals("subproceso")) {
				
			}
			
			break;

		default:
			break;
		}
		
		return boton;
	}

	/** Manejo de tecla Enter. */
	private void keyPressedListener() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				int key = evt.getKeyCode();

				if (key == KeyEvent.VK_ENTER) {
					doClick();
				}
			}
		});
	}

	public void setdModoAmpliado(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}


}
