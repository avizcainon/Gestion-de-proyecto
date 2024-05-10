package ve.com.avss.ventas.BD.conectores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import ve.com.avss.ventas.util.LeerConfiguracion;

public class Conexion {
	private static final Logger log = Logger.getLogger(Conexion.class);
	private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://";
	private static final String TIMEZOME = "?serverTimezone=UTC";
	private static Connection conexion = null;
	protected static Connection cn = null;
	protected static PreparedStatement preparedStatement = null;
	protected static Statement statement = null;
	protected static ResultSet resulSet = null;
	protected static int resultadoQuery = 0;

	public static Connection conectar() throws Exception {
		log.info("Conectando a la base de datos");

		try {
			switch (LeerConfiguracion.getModo()) { 
			case "local":
				log.info("Conectando a la base de datos local "+LeerConfiguracion.getDireccionArchivoBD() +LeerConfiguracion.getArchivoBD());
				conexion = DriverManager.getConnection(
						"jdbc:sqlite:" + LeerConfiguracion.getDireccionArchivoBD() + LeerConfiguracion.getArchivoBD());
				break;
			case "servidor":

				Class.forName(CONTROLADOR);
				conexion = DriverManager.getConnection(
						URL + LeerConfiguracion.getUrlBD() + ":" + LeerConfiguracion.getPuertoBD() + "/"
								+ LeerConfiguracion.getNombreBD() + TIMEZOME,
						LeerConfiguracion.getUsuarioBD(), LeerConfiguracion.getClaveBD());
				break;
			default:
				throw new Exception("Error de conexión BD");
			}

			log.info("Conexion establecida ");

		} catch (ClassNotFoundException e) {
			log.info("No se encontró el controlador BD ClassNotFoundException");
			throw new Exception("No se encontró el controlador BD ClassNotFoundException");
			

		} catch (SQLException e) {
			log.info("Error e SQLException BD "+e.getMessage());
			throw new Exception("Error e SQLException BD");

		} catch (Exception e) {
			
			throw e;
		}
		cn = conexion;
		return conexion;
	}

	public static void cerraConexion() throws SQLException {
		log.info("Cerrando conexion ");
		if (resulSet != null) {
			resulSet.close();
		}

		if (preparedStatement != null) {
			preparedStatement.close();
		}
		
		if (statement != null) {
			statement.close();
		}

		if (cn != null) {
			cn.close();
		}
		
		resultadoQuery = 0;

	}
}
