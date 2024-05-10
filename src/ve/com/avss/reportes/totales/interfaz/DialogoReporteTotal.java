package ve.com.avss.reportes.totales.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.toedter.calendar.JCalendar;

import ve.com.avss.ventas.bean.DatosConfiguracion;

public class DialogoReporteTotal extends AbstractDialogoBase{

	private static final long serialVersionUID = -2786315784137319771L;
	private JCalendar calendarioInicial;
	private JCalendar calendarioFinal;
	private String fechaInicial = "";
	private String fechaFinal = "";
	private String actionCommand = "";
	private Map<String,String> configuracionReporte = new HashMap<String, String>();
	public DialogoReporteTotal(JFrame parent) { 
		super(parent);
		
		accionesCheckBox();
		calendarioInicial = new JCalendar();
		calendarioFinal = new JCalendar();
		construirPanelBotones("Continuar", "Cancelar");
		eNombreFormulario.setText("Reporte total");
		eNombreFormulario.setFont(DatosConfiguracion.TITULO_1_BOLD);
		eNombreFormulario.setForeground(Color.DARK_GRAY);
		eConfiguracionReporte.setText("Configurar reporte");
		eConfiguracionReporte.setFont(DatosConfiguracion.TITULO_2_BOLD);
		eConfiguracionReporte.setForeground(Color.DARK_GRAY);
		jContentPaneSuperior.add(eNombreFormulario);
		BoxLayout boxlayoutBuscarInfomracion = new BoxLayout(jContentPaneConfiguracionReporte, BoxLayout.Y_AXIS);
		jContentPaneConfiguracionReporte.setLayout(boxlayoutBuscarInfomracion);
		
		eFechaInicio.setText("Fecha de inicio");
		eFechaFinal.setText("Fecha de final");
		
		calendarioInicial.setPreferredSize(new Dimension(250, 150));
		calendarioFinal.setPreferredSize(new Dimension(250, 150));
		
		jCheckTipoFactura.setText("Por tipo de facturas");
		jCheckEstadoGananciaPerdida.setText("Estado Ganancia / Perdida");
		jCheckListaFacurasDetalle.setText("Lista de facturas con detalles");
		jContentPaneConfiguracionReporte.add(eConfiguracionReporte);
		jContentPaneConfiguracionReporte.add(jCheckTipoFactura);
		jContentPaneConfiguracionReporte.add(jCheckEstadoGananciaPerdida);
		jContentPaneConfiguracionReporte.add(jCheckListaFacurasDetalle);
		
		
		jContentPaneCentral.add(eFechaInicio);
		jContentPaneCentral.add(calendarioInicial);
		jContentPaneCentral.add(eFechaFinal);
		jContentPaneCentral.add(calendarioFinal);
		jContentPaneDerecho.add(jContentPaneConfiguracionReporte);
		
		jContentPane.add(jContentPaneSuperior,BorderLayout.NORTH);
		jContentPane.add(jContentPaneCentral,BorderLayout.CENTER);
		jContentPane.add(botonesPanel, BorderLayout.SOUTH);
		jContentPane.add(jContentPaneDerecho, BorderLayout.EAST);
		getContentPane().add(jContentPane);
		pack();
		setAlwaysOnTop(false);
		setSize(600, 450);
		setTitle("Reporte total");
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
	}
	@Override
	protected boolean actionOK() {
		String diaInicial = calendarioInicial.getCalendar().get(Calendar.DATE)+"";
		diaInicial = diaInicial.length() < 2 ? "0"+diaInicial : diaInicial;
		String mesInicial = (calendarioInicial.getCalendar().get(Calendar.MONTH)+1)+"";
		mesInicial = mesInicial.length() < 2 ? "0"+mesInicial : mesInicial;
		fechaInicial = calendarioInicial.getCalendar().get(Calendar.YEAR)+"/"+mesInicial+"/"+diaInicial;
		
		
		Calendar fechaI = Calendar.getInstance();
		fechaI.set(calendarioInicial.getCalendar().get(Calendar.YEAR), calendarioInicial.getCalendar().get(Calendar.MONTH)+1, calendarioInicial.getCalendar().get(Calendar.DATE));
		 System.out.println(fechaI);
		String diaFinal = calendarioFinal.getCalendar().get(Calendar.DATE)+"";
		diaFinal = diaFinal.length() < 2 ? "0"+diaFinal : diaFinal;
		String mesFinal = (calendarioFinal.getCalendar().get(Calendar.MONTH)+1)+"";
		mesFinal = mesFinal.length() < 2 ? "0"+mesFinal : mesFinal;
		fechaFinal = calendarioFinal.getCalendar().get(Calendar.YEAR)+"/"+mesFinal+"/"+diaFinal;
	
		Calendar fechaF = Calendar.getInstance();
		fechaF.set(calendarioFinal.getCalendar().get(Calendar.YEAR), calendarioFinal.getCalendar().get(Calendar.MONTH)+1, calendarioFinal.getCalendar().get(Calendar.DATE));
		if (fechaI.after(fechaF)) {
			JOptionPane.showMessageDialog(null, "La fecha de INICIO no puede ser mayor a la FINAL","Mensaje de la aplicación",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		
		if (!jCheckEstadoGananciaPerdida.isSelected() && !jCheckTipoFactura.isSelected() && !jCheckListaFacurasDetalle.isSelected()) {
			JOptionPane.showMessageDialog(null, "Debe configurar el reporte","Mensaje de la aplicación",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
	if (jCheckEstadoGananciaPerdida.isSelected()) {
		configuracionReporte.put("ganaciaPerdida", "1");
	}
	
	if (jCheckTipoFactura.isSelected()) {
		configuracionReporte.put("tipoFactura", "1");
	}
	
	if (jCheckListaFacurasDetalle.isSelected()) {
		configuracionReporte.put("facturaDetalle", "1");
	}
		
		actionCommand = "continuar";
		return true;
	}
	
	private void accionesCheckBox() {
		jCheckEstadoGananciaPerdida.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	jCheckTipoFactura.setSelected(true);
		    }
		});
		
		
		jCheckTipoFactura.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	if (!jCheckTipoFactura.isSelected()) {
		    		jCheckEstadoGananciaPerdida.setSelected(false);
				}
		    }
		});
	}
	
	public String getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public String getActionCommand() {
		return actionCommand;
	}
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
	public Map<String, String> getConfiguracionReporte() {
		return configuracionReporte;
	}
	public void setConfiguracionReporte(Map<String, String> configuracionReporte) {
		this.configuracionReporte = configuracionReporte;
	}
	
	
}
