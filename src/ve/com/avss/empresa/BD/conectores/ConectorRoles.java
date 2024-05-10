package ve.com.avss.empresa.BD.conectores;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.BD.ejecutores.QueryRoles;
import ve.com.avss.empresa.bean.DatosRol;

public class ConectorRoles {
	private static final Logger log = Logger.getLogger(ConectorRoles.class);
	private DatosRol datosRol= new DatosRol();
	
	
	
	
	public List<DatosRol> obtenerRoles() {

		List<DatosRol> listaRoles = new ArrayList<DatosRol>();
		try {
			listaRoles = QueryRoles.obtenerRoles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return listaRoles;
	}


	public DatosRol getDatosRol() {
		return datosRol;
	}


	public void setDatosRol(DatosRol datosRol) {
		this.datosRol = datosRol;
	}
	
	

}
