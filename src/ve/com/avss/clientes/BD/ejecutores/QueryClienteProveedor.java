package ve.com.avss.clientes.BD.ejecutores;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ve.com.avss.clientes.bean.DatosClientesProveedor;
import ve.com.avss.ventas.BD.conectores.Conexion;

public class QueryClienteProveedor extends Conexion{
	private static final Logger log = Logger.getLogger(QueryClienteProveedor.class);
	private static DatosClientesProveedor datosClienteProveedor = new DatosClientesProveedor();
	
	
	public static int registrarClienteProveedor() throws Exception {
		log.info("registrarClienteProveedor");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}

        	preparedStatement = cn.prepareStatement("INSERT INTO `tbl_cliente_proveedor`(`id_cliente`, `cedula_cliente_proveedor`, `nombre_cliente_proveedor`, `apellido_cliente_proveedor`, `correo_cliente_proveedor`, `telefono_cliente_proveedor`, `id_tipo_cliente`,`direccion_cliente_proveedor`) VALUES (null,?,?,?,?,?,?,?)");
           
        	preparedStatement.setString(1, datosClienteProveedor.getIdentificacion());
        	preparedStatement.setString(2, datosClienteProveedor.getNombre());
        	preparedStatement.setString(3,datosClienteProveedor.getApellido());
        	preparedStatement.setString(4,datosClienteProveedor.getCorreo());
        	preparedStatement.setString(5, datosClienteProveedor.getTelefono());
        	preparedStatement.setInt(6, new Integer(datosClienteProveedor.getTipoClienteProveedor()));
        	preparedStatement.setString(7, datosClienteProveedor.getDireccion());
        	
        	statusQuery = preparedStatement.executeUpdate();
            log.info("registrarClienteProveedor "+statusQuery);

        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("registrarClienteProveedor - fin");
        return statusQuery;
	}
	
	public static int actualizarClienteProveedor() throws Exception {
		log.info("registrarClienteProveedor");
		int statusQuery = 0;
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}

        	preparedStatement = cn.prepareStatement("UPDATE tbl_cliente_proveedor SET nombre_cliente_proveedor = '"+datosClienteProveedor.getNombre()+"', cedula_cliente_proveedor = '"+datosClienteProveedor.getIdentificacion()+"', apellido_cliente_proveedor = '"+datosClienteProveedor.getApellido()+"', correo_cliente_proveedor = '"+datosClienteProveedor.getCorreo()+"', telefono_cliente_proveedor = '"+datosClienteProveedor.getTelefono()+"', id_tipo_cliente ="+new Integer(datosClienteProveedor.getTipoClienteProveedor())+" WHERE id_cliente = "+new Integer(datosClienteProveedor.getIdBD()));
    
        	statusQuery = preparedStatement.executeUpdate();
            log.info("registrarClienteProveedor "+statusQuery);

        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("registrarClienteProveedor - fin");
        return statusQuery;
	}
	
	public static DatosClientesProveedor obtenerClienteProveedor(String identificacion) throws Exception {
		log.info("obtenerClienteProveedor");
		DatosClientesProveedor clienteProveedor = null;
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_cliente_proveedor.*, tbl_tipo_Cliente.desc_tipo_cliente FROM tbl_cliente_proveedor inner join tbl_tipo_Cliente ON tbl_cliente_proveedor.id_tipo_cliente = tbl_tipo_Cliente.id_tipo_cliente WHERE cedula_cliente_proveedor ='" + identificacion + "'");
           boolean existeResultado = false;
           existeResultado = resulSet.next(); 
           if (!existeResultado) { 
			return clienteProveedor;
		}
           clienteProveedor = new DatosClientesProveedor();
           
           
           	clienteProveedor.setIdBD(resulSet.getString("id_cliente"));
           	clienteProveedor.setNombre(resulSet.getString("nombre_cliente_proveedor"));
           	clienteProveedor.setIdentificacion(resulSet.getString("cedula_cliente_proveedor"));
           	clienteProveedor.setApellido(resulSet.getString("apellido_cliente_proveedor"));
           	clienteProveedor.setCorreo(resulSet.getString("correo_cliente_proveedor"));
           	clienteProveedor.setTelefono(resulSet.getString("telefono_cliente_proveedor"));
           	clienteProveedor.setTipoClienteProveedor(resulSet.getString("desc_tipo_cliente"));
           	clienteProveedor.setDireccion(resulSet.getString("direccion_cliente_proveedor"));

       
           
       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerClienteProveedor - fin");
       return clienteProveedor;
	}
	
	public static List<DatosClientesProveedor> obtenerListaClienteProveedor() throws Exception {
		log.info("obtenerListaClienteProveedor");
		DatosClientesProveedor clienteProveedor = null;
		List<DatosClientesProveedor> listaClienteProveedor = new ArrayList<DatosClientesProveedor>();
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_cliente_proveedor.*, tbl_tipo_Cliente.desc_tipo_cliente FROM tbl_cliente_proveedor inner join tbl_tipo_Cliente ON tbl_cliente_proveedor.id_tipo_cliente = tbl_tipo_Cliente.id_tipo_cliente ");
          
		     while (resulSet.next()) {
		    	 clienteProveedor = new DatosClientesProveedor();
	
		           	clienteProveedor.setIdBD(resulSet.getString("id_cliente"));
		           	clienteProveedor.setNombre(resulSet.getString("nombre_cliente_proveedor"));
		           	clienteProveedor.setIdentificacion(resulSet.getString("cedula_cliente_proveedor"));
		           	clienteProveedor.setApellido(resulSet.getString("apellido_cliente_proveedor"));
		           	clienteProveedor.setCorreo(resulSet.getString("correo_cliente_proveedor"));
		           	clienteProveedor.setTelefono(resulSet.getString("telefono_cliente_proveedor"));
		           	clienteProveedor.setDireccion(resulSet.getString("direccion_cliente_proveedor"));
		           	clienteProveedor.setTipoClienteProveedor(resulSet.getString("desc_tipo_cliente"));
		           	listaClienteProveedor.add(clienteProveedor);

			}

       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaClienteProveedor - fin");
       return listaClienteProveedor;
	}
	
	public static List<DatosClientesProveedor> obtenerListaClienteProveedor(String buscar) throws Exception {
		log.info("obtenerListaClienteProveedor");
		DatosClientesProveedor clienteProveedor = null;
		List<DatosClientesProveedor> listaClienteProveedor = new ArrayList<DatosClientesProveedor>();
       try {
    	   if (cn.isClosed()) {
    			conectar();
    		}
           statement = cn.createStatement();
           resulSet = statement.executeQuery("SELECT tbl_cliente_proveedor.*, tbl_tipo_Cliente.desc_tipo_cliente FROM tbl_cliente_proveedor inner join tbl_tipo_Cliente ON tbl_cliente_proveedor.id_tipo_cliente = tbl_tipo_Cliente.id_tipo_cliente WHERE tbl_cliente_proveedor.telefono_cliente_proveedor LIKE'%" + buscar + "%' OR tbl_cliente_proveedor.nombre_cliente_proveedor LIKE '%" + buscar + "%' OR tbl_cliente_proveedor.apellido_cliente_proveedor LIKE '%" + buscar + "%'  OR tbl_cliente_proveedor.cedula_cliente_proveedor LIKE '%" + buscar + "%' OR tbl_cliente_proveedor.direccion_cliente_proveedor LIKE '%" + buscar + "%' ");
          
		     while (resulSet.next()) {
		    	 clienteProveedor = new DatosClientesProveedor();
	
		           	clienteProveedor.setIdBD(resulSet.getString("id_cliente"));
		           	clienteProveedor.setNombre(resulSet.getString("nombre_cliente_proveedor"));
		           	clienteProveedor.setIdentificacion(resulSet.getString("cedula_cliente_proveedor"));
		           	clienteProveedor.setApellido(resulSet.getString("apellido_cliente_proveedor"));
		           	clienteProveedor.setCorreo(resulSet.getString("correo_cliente_proveedor"));
		           	clienteProveedor.setTelefono(resulSet.getString("telefono_cliente_proveedor"));
		           	clienteProveedor.setDireccion(resulSet.getString("direccion_cliente_proveedor"));
		           	clienteProveedor.setTipoClienteProveedor(resulSet.getString("desc_tipo_cliente"));
		           	listaClienteProveedor.add(clienteProveedor);

			}

       } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
       log.info("obtenerListaClienteProveedor - fin");
       return listaClienteProveedor;
	}
	
	public static List<DatosClientesProveedor> obtenerTipoClienteProveedor() throws Exception {
		log.info("obtenerTipoClienteProveedor - fin");
		 List<DatosClientesProveedor> listaClienteProveedor = new ArrayList<DatosClientesProveedor>();
        try {
        	if (cn.isClosed()) {
     			conectar();
     		}
            statement = cn.createStatement();
            resulSet = statement.executeQuery("SELECT * FROM tbl_tipo_Cliente");
            
            while (resulSet.next()) {
            	DatosClientesProveedor clienteProveedor = new DatosClientesProveedor();
            	clienteProveedor.setIdBD(resulSet.getString("id_tipo_cliente"));
            	clienteProveedor.setTipoClienteProveedor(resulSet.getString("desc_tipo_cliente"));
            	listaClienteProveedor.add(clienteProveedor);
            }
            
        } catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
        log.info("obtenerTipoClienteProveedor");
        return listaClienteProveedor;
	}
	
	public static int obtenerIdTipoClienteProveedor(String nombreTipo) throws Exception {
		 log.info("obtenerIdTipoClienteProveedor - fin");
		int IdTipoClienteProveedor = 0;
		try {
			if (cn.isClosed()) {
	 			conectar();
	 		}
			statement = cn.createStatement();
			resulSet = statement.executeQuery("SELECT `id_tipo_cliente` FROM `tbl_tipo_Cliente` WHERE desc_tipo_cliente ='" + nombreTipo + "'");

			while (resulSet.next()) {
				IdTipoClienteProveedor = resulSet.getInt("id_tipo_cliente");

			}

		} catch (SQLException e) {
			log.error("error "+e.getMessage());
			throw new Exception("Falló conexion con la base de datos ");
		} finally {
			cerraConexion();
		}
		 log.info("obtenerIdTipoClienteProveedor - fin");
		return IdTipoClienteProveedor;
	}

	public static DatosClientesProveedor getDatosClienteProveedor() {
		return datosClienteProveedor;
	}

	public static void setDatosClienteProveedor(DatosClientesProveedor datosClienteProveedor) {
		QueryClienteProveedor.datosClienteProveedor = datosClienteProveedor;
	}



}
