package ve.com.avss.reportes.totales.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ve.com.avss.clientes.bean.DatosClientesProveedor;
import ve.com.avss.proyectos.bean.DatosProyecto;
import ve.com.avss.proyectos.bean.DatosTarea;

public class DatosReporte {
	private DatosClientesProveedor datosRecurso = null;
	private DatosProyecto datosProyecto = null;
	private List<DatosTarea> listaTareas = new ArrayList<DatosTarea>();
	private String fechaReporte = "";
	private Map<String,String> configuracionReporte = new HashMap<String, String>();
	
	
	
	
	public DatosClientesProveedor getDatosRecurso() {
		return datosRecurso;
	}
	public void setDatosRecurso(DatosClientesProveedor datosRecurso) {
		this.datosRecurso = datosRecurso;
	}
	public DatosProyecto getDatosProyecto() {
		return datosProyecto;
	}
	public void setDatosProyecto(DatosProyecto datosProyecto) {
		this.datosProyecto = datosProyecto;
	}
	public List<DatosTarea> getListaTareas() {
		return listaTareas;
	}
	public void setListaTareas(List<DatosTarea> listaTareas) {
		this.listaTareas = listaTareas;
	}
	public String getFechaReporte() {
		return fechaReporte;
	}
	public void setFechaReporte(String fechaReporte) {
		this.fechaReporte = fechaReporte;
	}
	public Map<String, String> getConfiguracionReporte() {
		return configuracionReporte;
	}
	public void setConfiguracionReporte(Map<String, String> configuracionReporte) {
		this.configuracionReporte = configuracionReporte;
	}

}
