package ve.com.avss.empresa.BD.ejecutores;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import ve.com.avss.empresa.bean.DatosEmpresa;
import ve.com.avss.ventas.BD.conectores.Conexion;

public class QueryEmpresa extends Conexion {
	private static final Logger log = Logger.getLogger(QueryEmpresa.class);

	public static int registrarEmpresa(DatosEmpresa datosEmpresa) throws Exception {
		log.info("Registrando datos de la empresa");

		try {
			if (cn.isClosed()) {
				conectar();
			}
			preparedStatement = cn.prepareStatement(
					"INSERT INTO `tbl_empresas`(`id_empresa`, `nombre_empresa`, `rif_empresa`, `direccion_empresa`, `logo_empresa`,`ruta_instalacion_empresa`,`correo_empresa`, `telefono_empresa`) VALUES (null,?,?,?,?,?,?,?)");

			preparedStatement.setString(1, datosEmpresa.getNombre());
			preparedStatement.setString(2, datosEmpresa.getRif());
			preparedStatement.setString(3, datosEmpresa.getDireccion());
			preparedStatement.setString(4, datosEmpresa.getLogo());
			preparedStatement.setString(5, datosEmpresa.getRutaInstalacion());
			preparedStatement.setString(6, datosEmpresa.getCorreo());
			preparedStatement.setString(7, datosEmpresa.getTelefono());

			resultadoQuery = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
			
		}
		return resultadoQuery;
	}

	public static int actualizarEmpresa(DatosEmpresa datosEmpresa) throws Exception {
		log.info("Actualizando datos de la empresa");
		int statusQuery = 0;
		int validarCantProd = datosEmpresa.isValidarCantidadProducto() == true ? 1 : 0;
		try {
			if (cn.isClosed() || cn == null) {
				conectar();
			}
			preparedStatement = cn.prepareStatement("update tbl_empresas set rif_empresa = '" + datosEmpresa.getRif()
					+ "',nombre_empresa ='" + datosEmpresa.getNombre() + "',direccion_empresa ='" + datosEmpresa.getDireccion()
					+ "',logo_empresa ='" + datosEmpresa.getLogo() + "', monto_divisa_empresa ='" + datosEmpresa.getMontoDivisa()
					+ "',ruta_instalacion_empresa ='" + datosEmpresa.getRutaInstalacion() + "'"
							+ ", correo_empresa='"+datosEmpresa.getCorreo()+"', telefono_empresa = '"+datosEmpresa.getTelefono()+"', validar_cant_producto_empresa = '"+(datosEmpresa.isValidarCantidadProducto() == true ? 1 : 0)+"', impuesto_empresa = '"+datosEmpresa.getImpuesto()+"'");
			statusQuery = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			if (statusQuery == 0) {
				throw new Exception("Actualizar datos "+e.getMessage());
			}
		} finally {
			
			cerraConexion();
		}
		return statusQuery;
	}

	

	public static DatosEmpresa obtenerDatosEmpresa() throws Exception {
		log.info("Obteniendo datos de la empresa");
		DatosEmpresa datosEmpresa = new DatosEmpresa();

		try {

			if (cn.isClosed()) {
				conectar();
			}

			statement = cn.createStatement();
			resulSet = statement.executeQuery("SELECT * FROM tbl_empresas");

			while (resulSet.next()) {
				datosEmpresa.setId(resulSet.getString("id_empresa"));
				datosEmpresa.setNombre(resulSet.getString("nombre_empresa"));
				datosEmpresa.setRif(resulSet.getString("rif_empresa"));
				datosEmpresa.setDireccion(resulSet.getString("direccion_empresa"));
				datosEmpresa.setLogo(resulSet.getString("logo_empresa"));
				datosEmpresa.setRutaInstalacion(resulSet.getString("ruta_instalacion_empresa"));
				datosEmpresa.setMontoDivisa(resulSet.getString("monto_divisa_empresa"));
				datosEmpresa.setTelefono(resulSet.getString("telefono_empresa"));
				datosEmpresa.setCorreo(resulSet.getString("correo_empresa"));
				datosEmpresa.setImpuesto(resulSet.getString("impuesto_empresa"));
				datosEmpresa.setValidarCantidadProducto(resulSet.getBoolean("validar_cant_producto_empresa"));
			}

		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos "+e.getMessage());
		} catch (Exception e) {
			throw e;
		} finally {
			cerraConexion();
		}
		return datosEmpresa;
	}

	

	

}
