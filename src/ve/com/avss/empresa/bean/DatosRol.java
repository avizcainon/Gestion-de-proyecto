package ve.com.avss.empresa.bean;

import java.util.ArrayList;
import java.util.List;

public class DatosRol {
	private String id = "";
	private String nombre = "";
	private String[] idModulo; 
	
	 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String[] getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(String[] idModulo) {
		this.idModulo = idModulo;
	}
	


}
