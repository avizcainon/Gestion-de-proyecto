package ve.com.avss.empresa.BD.conectores;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.BD.ejecutores.QueryUsuario;
import ve.com.avss.empresa.bean.DatosUsuario;

public class ConectorUsuario {
	private static final Logger log = Logger.getLogger(ConectorUsuario.class);
	private DatosUsuario datosUsuario = new DatosUsuario();
	

	public ConectorUsuario() {
		
	}
	
	public int registrarUsuario() {
		log.info("Conectando a registrar usuario");
		int statusQuery = 0;
		QueryUsuario.setDatosUsuario(datosUsuario);
		
		try {
			statusQuery = QueryUsuario.registrarUsuario();
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		return statusQuery;
	}
	
	public int actualizarUsuario() {
		log.info("Conectando a actualizar usuario");
		int statusQuery = 0;
		QueryUsuario.setDatosUsuario(datosUsuario);
		
		try {
			statusQuery = QueryUsuario.actualizarUsuario();
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		return statusQuery;
	}
	
	public DatosUsuario obtenerDatosUsuario() {
		log.info("Conectando a obtener datos usuario");
		QueryUsuario.setDatosUsuario(datosUsuario);
		DatosUsuario datosUsuario = new DatosUsuario();
		
		try {
			datosUsuario = QueryUsuario.obtenerDatosUsuario();
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}
		
		if (datosUsuario == null) {
			return datosUsuario;
		}
	
		return datosUsuario;
	}
	
	public List<DatosUsuario> obtenerListaUsuarios() {
		log.info("Conectando a obtener lista datos usuario");
		List<DatosUsuario> listaUsuarios= new ArrayList<DatosUsuario>();
		try {
			listaUsuarios = QueryUsuario.obtenerListaUsuario();
		} catch (Exception e) {
			log.error("error "+e.getMessage());
		}

		return listaUsuarios;
	}

	public DatosUsuario getDatosUsuario() {
		return datosUsuario;
	}

	public void setDatosUsuario(DatosUsuario datosUsuario) {
		this.datosUsuario = datosUsuario;
	}
	

}
