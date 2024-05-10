package ve.com.avss.empresa.BD.ejecutores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.bean.DatosRol;
import ve.com.avss.ventas.BD.conectores.Conexion;

public class QueryRoles extends Conexion {
	private static final Logger log = Logger.getLogger(QueryRoles.class);
	protected static DatosRol datosRol = new DatosRol();
	
	public static int registrarRol() throws Exception {
		log.info("registrando rol");
		int statusQuery = 0;
		try {
			if (cn.isClosed()) {
	 			conectar();
	 		}
			preparedStatement = cn.prepareStatement(
					"INSERT INTO `tbl_roles`(`id_rol`, `desc_rol`, `id_empresa`) VALUES (null,?,?)");
			preparedStatement.setString(1, datosRol.getNombre());
			preparedStatement.setInt(2, 1);
			statusQuery = preparedStatement.executeUpdate();
			
			/*obtenemos el id del rol insertado*/
			int idRolInsertado = obtenerIdRol(datosRol.getNombre());
			datosRol.setId(idRolInsertado+"");
			log.info("registrando rol "+statusQuery);
			log.info("registrando rol "+idRolInsertado);
		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
		return statusQuery;
	}

	
	public static List<DatosRol> obtenerRoles() throws Exception {
		log.info("obteniendo rol ");
		 List<DatosRol> listaRoles = new ArrayList<DatosRol>();
       try {
    	   if (cn.isClosed()) { 
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT * FROM tbl_roles");
           
           while (resulSet.next()) {
        	   DatosRol rol = new DatosRol();
        	   rol.setId(resulSet.getString("id_rol"));
        	   rol.setNombre(resulSet.getString("desc_rol"));
           	listaRoles.add(rol);
           }
           
       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       return listaRoles;
	}
	
	

	public static int obtenerIdRol(String nombreRol) throws Exception {
		log.info("obtenerIdRol");
		int idRol = 0;
		try {
			if (cn.isClosed()) {
	 			conectar();
	 		}
			statement = cn.createStatement();
			resulSet = statement.executeQuery("SELECT `id_rol` FROM `tbl_roles` WHERE desc_rol ='" + nombreRol + "'");

			while (resulSet.next()) {
				idRol = resulSet.getInt("id_rol");

			}

		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
		return idRol;
	}

	public static DatosRol getDatosRol() {
		return datosRol;
	}

	public static void setDatosRol(DatosRol datosRol) {
		QueryRoles.datosRol = datosRol;
	}



}
