package ve.com.avss.ventas.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class ManejoDeCampos {
	
	protected DefaultFormatterFactory armarMascaraMontoProvisional() {

		String txtMascara = "#,###.00";
		Double maximoPermitido = Util.formatMontoDouble("999999999999999");

		Locale loc = new Locale("de", "FB");
		NumberFormat formato = NumberFormat.getNumberInstance( loc);
		DecimalFormat formatoCI = (DecimalFormat)  formato;
		formatoCI.applyPattern(txtMascara);
		
		NumberFormatter enFormatCI = new NumberFormatter(formatoCI);
		enFormatCI.setAllowsInvalid(true);
		enFormatCI.setMinimum(new Double(0));
		enFormatCI.setMaximum(maximoPermitido);
		enFormatCI.setOverwriteMode(true);
		enFormatCI.setCommitsOnValidEdit(true);

		NumberFormatter dnFormatCI = new NumberFormatter(formatoCI);		
		dnFormatCI.setMinimum(new Double(0));	
		dnFormatCI.setMaximum(maximoPermitido);
		dnFormatCI.setAllowsInvalid(true);

		DefaultFormatterFactory currFactoryCI = new DefaultFormatterFactory(dnFormatCI, enFormatCI);
		
		return currFactoryCI;
	}

}
