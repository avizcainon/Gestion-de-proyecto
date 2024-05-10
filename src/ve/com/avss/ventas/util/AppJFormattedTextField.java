package ve.com.avss.ventas.util;

import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

/** JFormattedTextField personalizado. */
public class AppJFormattedTextField extends JFormattedTextField {
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constructor para AppJFormattedTextField.java. */
	public AppJFormattedTextField() {
		super();
		keyPressedListener();
    }
	
	/** Constructor para AppJFormattedTextField.java. */
	public AppJFormattedTextField(MaskFormatter formatter) {
		super(formatter);
		keyPressedListener();
    }

	/** Manejo de tecla Enter. */
	private void keyPressedListener() {
		addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                int key = evt.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    transferFocus();
                }
            }
		});
	}

	/**
	 * Proceso de eventos relacionados al foco.
	 * @param evt FocusEvent.
	 */
	protected void processFocusEvent(FocusEvent evt) {
		super.processFocusEvent(evt);

		if (evt.getID() == FocusEvent.FOCUS_GAINED) {
			setCaretPosition(getText().trim().length());
	        moveCaretPosition(0);
	    } else {
	    	select(0, 0);
	    }
	}

	protected void processMouseEvent(MouseEvent e) {
		super.processMouseEvent(e);
		
		if (e.getID() == MouseEvent.MOUSE_CLICKED) {
			if (getText().trim().length() == 0) {
				setCaretPosition(0);
			}
		}
	}
}
