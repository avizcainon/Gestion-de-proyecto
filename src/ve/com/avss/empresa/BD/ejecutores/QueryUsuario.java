package ve.com.avss.empresa.BD.ejecutores;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.bean.DatosUsuario;
import ve.com.avss.ventas.BD.conectores.Conexion;

public class QueryUsuario extends Conexion{
	
	private static final Logger log = Logger.getLogger(QueryUsuario.class);
	private static DatosUsuario datosUsuario = new DatosUsuario();
	
	
	public static int registrarUsuario() throws Exception {
		log.info("registrar Usuario");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}
        	preparedStatement = cn.prepareStatement("INSERT INTO `tbl_usuarios`(`id_usuario`, `cedula_usuario`, `nombre_usuario`, `apellido_usuario`, `correo_usuario`, `telefono_usuario`, `usuario_usuario`, `clave_usuario`, `id_rol_usuario`) VALUES (null,?,?,?,?,?,?,?,?)");
           
        	preparedStatement.setString(1, datosUsuario.getCedula());
        	preparedStatement.setString(2, datosUsuario.getNombre());
        	preparedStatement.setString(3,datosUsuario.getApellido());
        	preparedStatement.setString(4,datosUsuario.getCorreo());
        	preparedStatement.setString(5, datosUsuario.getTelefono());
        	preparedStatement.setString(6, datosUsuario.getUsuario());
        	preparedStatement.setString(7,datosUsuario.getClave());
        	preparedStatement.setInt(8,new Integer(datosUsuario.getRol()));
        	statusQuery = preparedStatement.executeUpdate();
            log.info("registrar Usuario "+statusQuery);
        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        return statusQuery;
	}
	
	public static int actualizarUsuario() throws Exception {
		log.info("registrar Usuario");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}
        	preparedStatement = cn.prepareStatement("UPDATE tbl_usuarios SET cedula_usuario = '"+datosUsuario.getCedula()+"', nombre_usuario = '"+datosUsuario.getNombre()+"', apellido_usuario = '"+datosUsuario.getApellido()+"', correo_usuario = '"+datosUsuario.getCorreo()+"', telefono_usuario = '"+datosUsuario.getTelefono()+"', usuario_usuario = '"+datosUsuario.getUsuario()+"', clave_usuario = '"+datosUsuario.getClave()+"', id_rol_usuario = '"+datosUsuario.getRol()+"' WHERE id_usuario = "+new Integer(datosUsuario.getId()));
           
        	statusQuery = preparedStatement.executeUpdate();
            log.info("registrar Usuario "+statusQuery);
        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        return statusQuery;
	}
	
	
	public static DatosUsuario obtenerDatosUsuario() throws Exception {

		log.info("obtener Datos Usuario ");
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT * FROM tbl_usuarios WHERE usuario_usuario ='"+datosUsuario.getUsuario()+"'");
           boolean existeResultado = false;
           existeResultado = resulSet.next(); 
           if (!existeResultado) { 
        	   datosUsuario = null;
			return datosUsuario;
		}
           datosUsuario.setId(resulSet.getString("id_usuario"));
           datosUsuario.setCedula(resulSet.getString("cedula_usuario"));
           datosUsuario.setNombre(resulSet.getString("nombre_usuario"));
           datosUsuario.setApellido(resulSet.getString("apellido_usuario"));
           datosUsuario.setCorreo(resulSet.getString("correo_usuario"));
           datosUsuario.setTelefono(resulSet.getString("telefono_usuario"));
           datosUsuario.setUsuario(resulSet.getString("usuario_usuario"));
           datosUsuario.setClave(resulSet.getString("clave_usuario"));
           datosUsuario.setRol(resulSet.getString("id_rol_usuario"));
       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       return datosUsuario;
	}
	
	public static List<DatosUsuario> obtenerListaUsuario() throws Exception {
		log.info("obtener lista Datos Usuario ");
		List<DatosUsuario> listaUsuarios= new ArrayList<DatosUsuario>();
		
	       try {
	    	   if (cn.isClosed()) {
	    			conectar();
	    		}
	           statement = cn.createStatement();
	           resulSet = statement.executeQuery("SELECT * FROM tbl_usuarios INNER JOIN tbl_roles ON tbl_roles.id_rol = tbl_usuarios.id_rol_usuario");
	          
	           while (resulSet.next()) {
	        	   DatosUsuario datosUsuario = new DatosUsuario();
	        	   datosUsuario.setId(resulSet.getString("id_usuario"));
		           datosUsuario.setNombre(resulSet.getString("nombre_usuario"));
		           datosUsuario.setRol(resulSet.getString("desc_rol"));
		           datosUsuario.setCedula(resulSet.getString("cedula_usuario"));
		           datosUsuario.setApellido(resulSet.getString("apellido_usuario"));
		           datosUsuario.setCorreo(resulSet.getString("correo_usuario"));
		           datosUsuario.setTelefono(resulSet.getString("telefono_usuario"));
		           datosUsuario.setUsuario(resulSet.getString("usuario_usuario"));
		           datosUsuario.setClave(resulSet.getString("clave_usuario"));
				
		           listaUsuarios.add(datosUsuario);
	           }
	          

	       } catch (SQLException e) {
				log.error("error "+e.getMessage());
				throw new Exception("Falló conexion con la base de datos ");
			} finally {
				cerraConexion();
			}
	       return listaUsuarios;
		}
	

	public static DatosUsuario getDatosUsuario() {
		return datosUsuario;
	}

	public static void setDatosUsuario(DatosUsuario datosUsuario) {
		QueryUsuario.datosUsuario = datosUsuario;
	}

	
	
	

}
