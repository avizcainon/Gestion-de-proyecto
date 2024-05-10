package ve.com.avss.proyectos.BD.ejecutores;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.bean.DatosClientesProveedor;
import ve.com.avss.proyectos.bean.DatosProyecto;
import ve.com.avss.proyectos.bean.DatosStatus;
import ve.com.avss.proyectos.bean.DatosTarea;
import ve.com.avss.ventas.BD.conectores.Conexion;

public class QueryProyecto extends Conexion{
	private static final Logger log = Logger.getLogger(QueryProyecto.class);
	private static DatosProyecto datosProyecto = new DatosProyecto();
	
	
	public static int registrarProyecto() throws Exception {
		log.info("registrarProyecto");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}

        	preparedStatement = cn.prepareStatement("INSERT INTO `tbl_proyectos`(`id_proyecto`, `nombre_proyecto`, `observacion_proyecto`, `fecha_desarrollo_proyecto`, `fecha_qa_proyecto`, `status_proyecto`) VALUES (null,?,?,?,?,?)");
           
        	preparedStatement.setString(1, datosProyecto.getDescripcion());
        	preparedStatement.setString(2, datosProyecto.getObservacion());
        	preparedStatement.setString(3,datosProyecto.getFechaDesarrollo());
        	preparedStatement.setString(4,datosProyecto.getFechaCertificacion());
        	preparedStatement.setString(5, datosProyecto.getStatus());

        	
        	statusQuery = preparedStatement.executeUpdate();
            log.info("registrarProyecto "+statusQuery);

        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("registrarProyecto - fin");
        return statusQuery;
	}
	
	public static int registrarTarea(DatosTarea tarea) throws Exception {
		log.info("registrarTarea");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}

        	preparedStatement = cn.prepareStatement("INSERT INTO `tbl_tareas_p`(`id_tareaP`, `descripcion_tareaP`,  `fecha_desarrollo_tareaP`, `fecha_qa_tareaP`, `observacion_tareaP`,`status_tareaP`,`id_recurso`,`id_proyecto`) VALUES (null,?,?,?,?,?,?,?)");
           
        	preparedStatement.setString(1, tarea.getDescripcion());
        	preparedStatement.setString(2, tarea.getFechaDesarrollo());
        	preparedStatement.setString(3,tarea.getFechaDesarrollo());
        	preparedStatement.setString(4,tarea.getObservacion());
        	preparedStatement.setString(5, tarea.getStatus());
        	preparedStatement.setString(6, tarea.getRecurso());
        	preparedStatement.setString(7, tarea.getProyecto());

        	
        	statusQuery = preparedStatement.executeUpdate();
            log.info("registrarTarea "+statusQuery);

        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("registrarTarea - fin");
        return statusQuery;
	}
	
	public static int actualizarProyecto() throws Exception {
		log.info("actualizarProyecto");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}

        	preparedStatement = cn.prepareStatement("UPDATE tbl_proyectos SET nombre_proyecto = '"+datosProyecto.getDescripcion()+"', observacion_proyecto = '"+datosProyecto.getObservacion()+"', fecha_desarrollo_proyecto = '"+datosProyecto.getFechaDesarrollo()+"', fecha_qa_proyecto = '"+datosProyecto.getFechaCertificacion()+"', status_proyecto = '"+new Integer(datosProyecto.getStatus())+"' WHERE id_proyecto = "+new Integer(datosProyecto.getIdBD()));
    
        	statusQuery = preparedStatement.executeUpdate();
            log.info("actualizarProyecto "+statusQuery);

        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("actualizarProyecto - fin");
        return statusQuery;
	}
	
	public static int actualizarTarea(DatosTarea tarea) throws Exception {
		log.info("actualizarTarea");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}
        	
        	if (tarea.getProyecto().equals("")) {
        		preparedStatement = cn.prepareStatement("UPDATE tbl_tareas_p SET descripcion_tareaP = '"+tarea.getDescripcion()+"', observacion_tareaP = '"+tarea.getObservacion()+"', fecha_desarrollo_tareaP = '"+tarea.getFechaDesarrollo()+"', id_recurso = '"+new Integer(tarea.getRecurso())+"', status_tareaP = '"+new Integer(tarea.getStatus())+"' WHERE id_tareaP = "+new Integer(tarea.getIdBD()));
			}else {
				preparedStatement = cn.prepareStatement("UPDATE tbl_tareas_p SET id_proyecto = '"+tarea.getProyecto()+"', descripcion_tareaP = '"+tarea.getDescripcion()+"', observacion_tareaP = '"+tarea.getObservacion()+"', fecha_desarrollo_tareaP = '"+tarea.getFechaDesarrollo()+"', id_recurso = '"+new Integer(tarea.getRecurso())+"', status_tareaP = '"+new Integer(tarea.getStatus())+"' WHERE id_tareaP = "+new Integer(tarea.getIdBD()));
			}

        	
    
        	statusQuery = preparedStatement.executeUpdate();
            log.info("actualizarTarea "+statusQuery);

        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("actualizarTarea - fin");
        return statusQuery;
	}
	
	public static int actualizarStatusTarea(String idProyecto, String status) throws Exception {
		log.info("actualizarTarea");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}
        	
        	preparedStatement = cn.prepareStatement("UPDATE tbl_tareas_p SET status_tareaP = '"+status+"' WHERE status_tareaP = 4 AND id_proyecto = "+new Integer(idProyecto));

        	
    
        	statusQuery = preparedStatement.executeUpdate();
            log.info("actualizarTarea "+statusQuery);

        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("actualizarTarea - fin");
        return statusQuery;
	}
	
	public static DatosProyecto obtenerProyecto(String identificacion) throws Exception {
		log.info("obtenerProyecto");
		DatosProyecto proyecto = null;
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_proyectos.*, tbl_status_p.descripcion_statusP FROM tbl_proyectos inner join tbl_status_p ON tbl_proyectos.status_proyecto = tbl_status_p.id_statusP WHERE id_proyecto ='" + identificacion + "'");
           boolean existeResultado = false;
           existeResultado = resulSet.next(); 
           if (!existeResultado) { 
			return proyecto;
		}
           proyecto = new DatosProyecto();
           
           
           proyecto.setIdBD(resulSet.getString("id_proyecto"));
          	proyecto.setDescripcion(resulSet.getString("nombre_proyecto"));
          	proyecto.setFechaCertificacion(resulSet.getString("fecha_qa_proyecto"));
          	proyecto.setFechaDesarrollo(resulSet.getString("fecha_desarrollo_proyecto"));
          	proyecto.setObservacion(resulSet.getString("observacion_proyecto"));
          	proyecto.setStatus(resulSet.getString("descripcion_statusP"));

       
           
       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerProyecto - fin");
       return proyecto;
	}
	
	public static List<DatosProyecto> obtenerListaProyectos() throws Exception {
		log.info("obtenerListaProyectos");
		DatosProyecto proyecto = null;
		List<DatosProyecto> listaProyectos = new ArrayList<DatosProyecto>();
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_proyectos.*, tbl_status_p.* FROM tbl_proyectos inner join tbl_status_p ON tbl_proyectos.status_proyecto = tbl_status_p.id_statusP ORDER BY  tbl_proyectos.status_proyecto,tbl_proyectos.nombre_proyecto, tbl_proyectos.fecha_desarrollo_proyecto ASC");
          
		     while (resulSet.next()) {
		    	 proyecto = new DatosProyecto();
	
		    	 proyecto.setIdBD(resulSet.getString("id_proyecto"));
		          	proyecto.setDescripcion(resulSet.getString("nombre_proyecto"));
		          	proyecto.setFechaCertificacion(resulSet.getString("fecha_qa_proyecto"));
		          	proyecto.setFechaDesarrollo(resulSet.getString("fecha_desarrollo_proyecto"));
		          	proyecto.setObservacion(resulSet.getString("observacion_proyecto"));
		          	proyecto.setStatus(resulSet.getString("descripcion_statusP"));
		          	listaProyectos.add(proyecto);

			}

       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaClienteProveedor - fin");
       return listaProyectos;
	}
	public static List<DatosProyecto> obtenerListaProyectosActivos() throws Exception {
		log.info("obtenerListaProyectos");
		DatosProyecto proyecto = null;
		List<DatosProyecto> listaProyectos = new ArrayList<DatosProyecto>();
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_proyectos.*, tbl_status_p.* FROM tbl_proyectos inner join tbl_status_p ON tbl_proyectos.status_proyecto = tbl_status_p.id_statusP WHERE tbl_proyectos.status_proyecto != 4 ORDER BY  tbl_proyectos.status_proyecto,tbl_proyectos.nombre_proyecto, tbl_proyectos.fecha_desarrollo_proyecto ASC");
          
		     while (resulSet.next()) {
		    	 proyecto = new DatosProyecto();
	
		    	 proyecto.setIdBD(resulSet.getString("id_proyecto"));
		          	proyecto.setDescripcion(resulSet.getString("nombre_proyecto"));
		          	proyecto.setFechaCertificacion(resulSet.getString("fecha_qa_proyecto"));
		          	proyecto.setFechaDesarrollo(resulSet.getString("fecha_desarrollo_proyecto"));
		          	proyecto.setObservacion(resulSet.getString("observacion_proyecto"));
		          	proyecto.setStatus(resulSet.getString("descripcion_statusP"));
		          	listaProyectos.add(proyecto);

			}

       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaClienteProveedor - fin");
       return listaProyectos;
	}
	public static List<DatosProyecto> obtenerListaProyecto(String buscar) throws Exception {
		log.info("obtenerListaProyecto");
		DatosProyecto proyecto = null;
		List<DatosProyecto> listaProyectos = new ArrayList<DatosProyecto>();
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_proyectos.*, tbl_status_p.* FROM tbl_proyectos inner join tbl_status_p ON tbl_proyectos.status_proyecto = tbl_status_p.id_statusP WHERE tbl_proyectos.observacion_proyecto LIKE'%" + buscar + "%' OR tbl_proyectos.nombre_proyecto LIKE '%" + buscar + "%' OR tbl_proyectos.fecha_desarrollo_proyecto LIKE '%" + buscar + "%'  OR tbl_proyectos.fecha_qa_proyecto LIKE '%" + buscar + "%' OR tbl_status_p.descripcion_statusP LIKE '%" + buscar + "%' ");
          
		     while (resulSet.next()) {
		    	 proyecto = new DatosProyecto();
	
		    	 proyecto.setIdBD(resulSet.getString("id_proyecto"));
		          	proyecto.setDescripcion(resulSet.getString("nombre_proyecto"));
		          	proyecto.setFechaCertificacion(resulSet.getString("fecha_qa_proyecto"));
		          	proyecto.setFechaDesarrollo(resulSet.getString("fecha_desarrollo_proyecto"));
		          	proyecto.setObservacion(resulSet.getString("observacion_proyecto"));
		          	proyecto.setStatus(resulSet.getString("descripcion_statusP"));
		           	
		           	listaProyectos.add(proyecto);

			}

       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaProyecto - fin");
       return listaProyectos;
	}
	
	public static List<DatosTarea> obtenerListaTarea(String buscar) throws Exception {
		log.info("obtenerListaTarea");
		DatosTarea tarea = null;
		List<DatosTarea> listaTareas = new ArrayList<DatosTarea>();
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_tareas_p.*, tbl_cliente_proveedor.* FROM tbl_tareas_p INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_tareas_p.id_recurso WHERE tbl_tareas_p.id_proyecto ='" + buscar + "'  ORDER BY tbl_tareas_p.status_tareaP ASC");
          
		     while (resulSet.next()) {
		    	 tarea = new DatosTarea();
	
		    	 tarea.setIdBD(resulSet.getString("id_tareaP"));
		    	 tarea.setDescripcion(resulSet.getString("descripcion_tareaP"));
		    	 tarea.setObservacion(resulSet.getString("observacion_tareaP"));
		    	 tarea.setFechaDesarrollo(resulSet.getString("fecha_desarrollo_tareaP"));
		    	 tarea.setStatus(resulSet.getString("status_tareaP"));
		    	 tarea.setProyecto(resulSet.getString("id_proyecto"));
		    	 tarea.setRecurso(resulSet.getString("id_recurso"));
		           	
		    	 listaTareas.add(tarea);

			}

       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaTarea - fin");
       return listaTareas;
	}
	
	public static List<DatosTarea> obtenerListaTareaPorRecurso(String buscar) throws Exception {
		log.info("obtenerListaTareaPorRecurso");
		DatosTarea tarea = null;
		List<DatosTarea> listaTareas = new ArrayList<DatosTarea>();
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_tareas_p.*, tbl_cliente_proveedor.* FROM tbl_tareas_p INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_tareas_p.id_recurso WHERE tbl_tareas_p.id_recurso ='" + buscar + "'  ORDER BY tbl_tareas_p.fecha_desarrollo_tareaP ASC, tbl_tareas_p.status_tareaP ASC, tbl_tareas_p.id_proyecto ASC");
          
		     while (resulSet.next()) {
		    	 tarea = new DatosTarea();
	
		    	 tarea.setIdBD(resulSet.getString("id_tareaP"));
		    	 tarea.setDescripcion(resulSet.getString("descripcion_tareaP"));
		    	 tarea.setObservacion(resulSet.getString("observacion_tareaP"));
		    	 tarea.setFechaDesarrollo(resulSet.getString("fecha_desarrollo_tareaP"));
		    	 tarea.setStatus(resulSet.getString("status_tareaP"));
		    	 tarea.setProyecto(resulSet.getString("id_proyecto"));
		    	 tarea.setRecurso(resulSet.getString("id_recurso"));
		           	
		    	 listaTareas.add(tarea);

			}

       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaTareaPorRecurso - fin");
       return listaTareas;
	}
	
	public static List<DatosTarea> obtenerListaTareaFecha(String buscar) throws Exception {
		log.info("obtenerListaTarea");
		DatosTarea tarea = null;
		List<DatosTarea> listaTareas = new ArrayList<DatosTarea>();
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
    	   
    	   statement = cn.createStatement();
    	   if (buscar.length() > 0) {
    		   resulSet = statement.executeQuery("SELECT tbl_tareas_p.*, tbl_cliente_proveedor.* FROM tbl_tareas_p INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_tareas_p.id_recurso WHERE tbl_tareas_p.fecha_desarrollo_tareaP ='" + buscar +"' AND tbl_tareas_p.status_tareaP != 4 ORDER BY  tbl_tareas_p.id_proyecto DESC,tbl_tareas_p.fecha_desarrollo_tareaP ASC");
    	   }else {
    		   resulSet = statement.executeQuery("SELECT tbl_tareas_p.*, tbl_cliente_proveedor.* FROM tbl_tareas_p INNER JOIN tbl_cliente_proveedor ON tbl_cliente_proveedor.id_cliente = tbl_tareas_p.id_recurso WHERE tbl_tareas_p.status_tareaP != 4 ORDER BY  tbl_tareas_p.id_proyecto DESC ,tbl_tareas_p.fecha_desarrollo_tareaP ASC");
    	   }
           
           
          
		     while (resulSet.next()) {
		    	 tarea = new DatosTarea();
	
		    	 tarea.setIdBD(resulSet.getString("id_tareaP"));
		    	 tarea.setDescripcion(resulSet.getString("descripcion_tareaP"));
		    	 tarea.setObservacion(resulSet.getString("observacion_tareaP"));
		    	 tarea.setFechaDesarrollo(resulSet.getString("fecha_desarrollo_tareaP"));
		    	 tarea.setStatus(resulSet.getString("status_tareaP"));
		    	 tarea.setProyecto(resulSet.getString("id_proyecto"));
		    	 tarea.setRecurso(resulSet.getString("id_recurso"));
		           	
		    	 listaTareas.add(tarea);

			}

       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaTarea - fin");
       return listaTareas;
	}
	
	public static List<DatosStatus> obtenerStatus() throws Exception {
		log.info("obtenerStatus - fin");
		 List<DatosStatus> listaStatus = new ArrayList<DatosStatus>();
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}
            statement = cn.createStatement();
            resulSet = statement.executeQuery("SELECT * FROM tbl_status_p");
            
            while (resulSet.next()) {
            	DatosStatus status = new DatosStatus();
            	status.setIdBD(resulSet.getString("id_statusP"));
            	status.setStatus(resulSet.getString("descripcion_statusP"));
            	listaStatus.add(status);
            }
            
        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("obtenerStatus");
        return listaStatus;
	}
	

	public static DatosProyecto getDatosProyecto() {
		return datosProyecto;
	}

	public static void setDatosProyecto(DatosProyecto datosProyecto) {
		QueryProyecto.datosProyecto = datosProyecto;
	}



}
