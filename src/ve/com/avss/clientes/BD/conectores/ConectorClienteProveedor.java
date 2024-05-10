package ve.com.avss.clientes.BD.conectores;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.BD.ejecutores.QueryClienteProveedor;
import ve.com.avss.clientes.bean.DatosClientesProveedor;

public class ConectorClienteProveedor {
	private static final Logger log = Logger.getLogger(ConectorClienteProveedor.class);
	private DatosClientesProveedor datosClienteProveedor = new DatosClientesProveedor();

	public ConectorClienteProveedor() {
		log.info("constructor");
		
	}
	 
	public int registrarClienteProveedor() {
		log.info("conectando registrarClienteProveedor");
		int statusQuery = 0;
		QueryClienteProveedor.setDatosClienteProveedor(datosClienteProveedor);
		try {
			statusQuery = QueryClienteProveedor.registrarClienteProveedor();

		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		log.info("conectando registrarClienteProveedor - fin");
		return statusQuery;
	}
	
	public int actualizarClienteProveedor() {
		log.info("conectando actualizarClienteProveedor");
		int statusQuery = 0;
		QueryClienteProveedor.setDatosClienteProveedor(datosClienteProveedor);
		try {
			statusQuery = QueryClienteProveedor.actualizarClienteProveedor();
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		log.info("conectando actualizarClienteProveedor - fin");
		return statusQuery;
	}
	
	public List<DatosClientesProveedor> listaClienteProveedor() {
		log.info("conectando listaClienteProveedor");
		List<DatosClientesProveedor> listaClienteProveedor = new ArrayList<DatosClientesProveedor>();
		
		try {
			listaClienteProveedor = QueryClienteProveedor.obtenerListaClienteProveedor();
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		log.info("conectando listaClienteProveedor - fin");
		return listaClienteProveedor;
	}
	
	public List<DatosClientesProveedor> obtenerTipoClienteProveedor() {
		log.info("conectando obtenerTipoClienteProveedor");
		List<DatosClientesProveedor> listaClienteProveedor = new ArrayList<DatosClientesProveedor>();
		
		try {
			listaClienteProveedor = QueryClienteProveedor.obtenerTipoClienteProveedor();
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		log.info("conectando obtenerTipoClienteProveedor - fin");
		return listaClienteProveedor;
	}
	
	

	public DatosClientesProveedor getDatosClienteProveedor() {
		return datosClienteProveedor;
	}

	public void setDatosClienteProveedor(DatosClientesProveedor datosClienteProveedor) {
		this.datosClienteProveedor = datosClienteProveedor;
	}

}
