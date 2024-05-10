package ve.com.avss.reportes.BD.ejecutores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.proyectos.bean.DatosTarea;
import ve.com.avss.reportes.totales.bean.DatosReporte;
import ve.com.avss.ventas.BD.conectores.Conexion;

public class QueryReporte extends Conexion {
	private static final Logger log = Logger.getLogger(QueryReporte.class);
	private static DatosReporte datosReporteTotal = new DatosReporte();
	
	public static List<DatosTarea> obtenerListaTareasPorProyecto(String idProyecto, String filtro, String fecha) throws Exception {
		List<DatosTarea> listaTareas = new ArrayList<DatosTarea>();
		DatosTarea tarea = null;
		try {
			if (cn.isClosed()) {
				conectar();
			}
			String queryWhere = " WHERE tbl_proyectos.id_proyecto = '"+idProyecto+"' ";
			statement = cn.createStatement();
			if (fecha.equals("")) {
				resulSet = statement.executeQuery("SELECT tbl_tareas_p.*, tbl_cliente_proveedor.*, tbl_status_p.*, tbl_proyectos.nombre_proyecto FROM tbl_tareas_p INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_tareas_p.id_recurso INNER JOIN tbl_status_p ON tbl_status_p.id_statusP = tbl_tareas_p.status_tareaP INNER JOIN tbl_proyectos ON tbl_proyectos.id_proyecto = tbl_tareas_p.id_proyecto "+queryWhere+"  ORDER BY  tbl_tareas_p.id_proyecto DESC ,tbl_tareas_p.fecha_desarrollo_tareaP ASC");
			}else {
				if (!filtro.equals("")) {
					queryWhere += " AND fecha_desarrollo_tareaP"+filtro+"'"+fecha+"' ";
				}
				
				resulSet = statement.executeQuery("SELECT tbl_tareas_p.*, tbl_cliente_proveedor.*, tbl_status_p.*, tbl_proyectos.nombre_proyecto FROM tbl_tareas_p INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_tareas_p.id_recurso INNER JOIN tbl_status_p ON tbl_status_p.id_statusP = tbl_tareas_p.status_tareaP INNER JOIN tbl_proyectos ON tbl_proyectos.id_proyecto = tbl_tareas_p.id_proyecto"+ queryWhere+ "ORDER BY  tbl_tareas_p.id_proyecto DESC ,tbl_tareas_p.fecha_desarrollo_tareaP ASC");
			}
			String nombreRecurso = "";
			while (resulSet.next()) {
		    	 tarea = new DatosTarea();
		    	 tarea.setIdBD(resulSet.getString("id_tareaP"));
		    	 tarea.setDescripcion(resulSet.getString("descripcion_tareaP"));
		    	 tarea.setObservacion(resulSet.getString("observacion_tareaP"));
		    	 tarea.setFechaDesarrollo(resulSet.getString("fecha_desarrollo_tareaP"));
		    	 tarea.setStatus(resulSet.getString("descripcion_statusP"));
		    	 tarea.setProyecto(resulSet.getString("nombre_proyecto"));
		    	 nombreRecurso = resulSet.getString("nombre_cliente_proveedor") + " "+resulSet.getString("apellido_cliente_proveedor");
		    	 tarea.setRecurso(nombreRecurso);
		    	 listaTareas.add(tarea);
			}
		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
		return listaTareas;
	}
	
	public static List<DatosTarea> obtenerListaTareasPorRecurso(String idRecurso, String filtro, String fecha) throws Exception {
		List<DatosTarea> listaTareas = new ArrayList<DatosTarea>();
		DatosTarea tarea = null;
		try {
			if (cn.isClosed()) {
				conectar();
			}
			String queryWhere = " WHERE tbl_tareas_p.id_recurso = '"+idRecurso+"' ";
			statement = cn.createStatement();
			if (fecha.equals("")) {
				resulSet = statement.executeQuery("SELECT tbl_tareas_p.*, tbl_cliente_proveedor.*, tbl_status_p.*,tbl_proyectos.* FROM tbl_tareas_p  INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_tareas_p.id_recurso INNER JOIN tbl_status_p ON tbl_status_p.id_statusP = tbl_tareas_p.status_tareaP INNER JOIN tbl_proyectos ON tbl_proyectos.id_proyecto = tbl_tareas_p.id_proyecto WHERE tbl_cliente_proveedor.id_cliente = '"+idRecurso+"' ORDER BY  tbl_tareas_p.id_proyecto DESC ,tbl_tareas_p.fecha_desarrollo_tareaP ASC");
			}else {
				if (!filtro.equals("")) {
					queryWhere += " AND fecha_desarrollo_tareaP"+" "+filtro+"'"+fecha+"' ";
				}
				
				resulSet = statement.executeQuery("SELECT tbl_tareas_p.*, tbl_cliente_proveedor.*, tbl_status_p.*,tbl_proyectos.* FROM tbl_tareas_p  INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_tareas_p.id_recurso INNER JOIN tbl_status_p ON tbl_status_p.id_statusP = tbl_tareas_p.status_tareaP INNER JOIN tbl_proyectos ON tbl_proyectos.id_proyecto = tbl_tareas_p.id_proyecto "+ queryWhere+ "ORDER BY  tbl_tareas_p.id_proyecto DESC ,tbl_tareas_p.fecha_desarrollo_tareaP ASC");
			}
			String nombreRecurso = "";
			while (resulSet.next()) {
		    	 tarea = new DatosTarea();
		    	 tarea.setIdBD(resulSet.getString("id_tareaP"));
		    	 tarea.setDescripcion(resulSet.getString("descripcion_tareaP"));
		    	 tarea.setObservacion(resulSet.getString("observacion_tareaP"));
		    	 tarea.setFechaDesarrollo(resulSet.getString("fecha_desarrollo_tareaP"));
		    	 tarea.setStatus(resulSet.getString("descripcion_statusP"));
		    	 tarea.setProyecto(resulSet.getString("nombre_proyecto"));
		    	 nombreRecurso = resulSet.getString("nombre_cliente_proveedor") + " "+resulSet.getString("apellido_cliente_proveedor");
		    	 tarea.setRecurso(nombreRecurso); 	
		    	 listaTareas.add(tarea);
			}
		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
		return listaTareas;
	}
	
	



}
