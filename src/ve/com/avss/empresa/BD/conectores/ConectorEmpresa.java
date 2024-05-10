package ve.com.avss.empresa.BD.conectores;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.BD.ejecutores.QueryEmpresa;
import ve.com.avss.empresa.bean.DatosEmpresa;

public class ConectorEmpresa {
	private static final Logger log = Logger.getLogger(ConectorEmpresa.class);
	private DatosEmpresa datosEmpresa = new DatosEmpresa();
	private int statusQuery = 0;

	public ConectorEmpresa() {
		super();
		log.info("Constructor");

	}

	public int registrarDatosEmpresa() throws Exception {
		log.info("Conectando a actualizar datos de la empresa");

		try {
			statusQuery = QueryEmpresa.registrarEmpresa(datosEmpresa);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return statusQuery;
	}
	
	public int actualizarDatosEmpresa() throws Exception {
		log.info("Conectando a actualizar datos de la empresa");

		try {
			statusQuery = QueryEmpresa.actualizarEmpresa(datosEmpresa);
			log.info("Conectando a actualizar datos de la empresa: "+statusQuery);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return statusQuery;
	}

	

	public DatosEmpresa obtenerDatosEmpresa() throws Exception {

		DatosEmpresa datosEmpresa = new DatosEmpresa();

		try {
			datosEmpresa = QueryEmpresa.obtenerDatosEmpresa();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		if (!datosEmpresa.getImpuesto().isEmpty()) {
			double impuestoDouble = new Double(datosEmpresa.getImpuesto());
			impuestoDouble = impuestoDouble / 100;
			datosEmpresa.setImpuesto(impuestoDouble+"");
		}
		
		
		return datosEmpresa;
	}

	public DatosEmpresa getDatosEmpresa() {
		return datosEmpresa;
	}

	public void setDatosEmpresa(DatosEmpresa datosEmpresa) {
		this.datosEmpresa = datosEmpresa;
	}

}
