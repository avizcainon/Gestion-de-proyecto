package ve.com.avss.proyectos.BD.conectores;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.BD.ejecutores.QueryClienteProveedor;
import ve.com.avss.clientes.bean.DatosClientesProveedor;
import ve.com.avss.proyectos.BD.ejecutores.QueryProyecto;
import ve.com.avss.proyectos.bean.DatosProyecto;
import ve.com.avss.proyectos.bean.DatosStatus;

public class ConectorProyecto {
	private static final Logger log = Logger.getLogger(ConectorProyecto.class);
	private DatosProyecto datosProyecto = new DatosProyecto();

	public ConectorProyecto() {
		log.info("constructor");
		
	}
	 
	public int registrarProyecto() {
		log.info("conectando registrarProyecto");
		int statusQuery = 0;
		QueryProyecto.setDatosProyecto(datosProyecto);
		try {
			statusQuery = QueryProyecto.registrarProyecto();

		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		log.info("conectando registrarProyecto - fin");
		return statusQuery;
	}
	
	public int actualizarProyecto() {
		log.info("conectando actualizarProyecto");
		int statusQuery = 0;
		QueryProyecto.setDatosProyecto(datosProyecto);
		try {
			statusQuery = QueryProyecto.actualizarProyecto();
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		log.info("conectando actualizarProyecto - fin");
		return statusQuery;
	}
	
	public List<DatosProyecto> listaProyectos() {
		log.info("conectando listaProyectos");
		List<DatosProyecto> listaProyectos = new ArrayList<DatosProyecto>();
		
		try {
			listaProyectos = QueryProyecto.obtenerListaProyectos();
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		log.info("conectando listaProyectos - fin");
		return listaProyectos;
	}
	
	public List<DatosStatus> obtenerStatus() {
		log.info("conectando obtenerStatus");
		List<DatosStatus> listaStatus = new ArrayList<DatosStatus>();
		
		try {
			listaStatus = QueryProyecto.obtenerStatus();
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		log.info("conectando obtenerStatus - fin");
		return listaStatus;
	}
	
	

	public DatosProyecto getDatosProyecto() {
		return datosProyecto;
	}

	public void setDatosProyecto(DatosProyecto datosProyecto) {
		this.datosProyecto = datosProyecto;
	}

}
