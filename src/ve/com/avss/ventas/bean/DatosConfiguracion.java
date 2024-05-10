package ve.com.avss.ventas.bean;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

import ve.com.avss.empresa.bean.DatosEmpresa;
import ve.com.avss.empresa.bean.DatosUsuario;

public class DatosConfiguracion {
	private DatosEmpresa datosEmpresa = new DatosEmpresa();
	private DatosUsuario datosUsuario= new DatosUsuario();
	
	private Map<String, String> listaTipoFactura = new HashMap<String, String>();
	public static  Border LINEA_GRIS_CLARA_1PX = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
	public static ImageIcon iAceptar;
	public static ImageIcon iCancelar;
	public static ImageIcon iActualizar;
	public static ImageIcon iUsuarioAutenticacion;
	public static ImageIcon iClaveAutenticacion;
	public static ImageIcon iArchivo;
	public static ImageIcon iAbrirArchivo;
	public static ImageIcon iImprimir;
	public static ImageIcon iSalir;
	public static ImageIcon iCompra;
	public static ImageIcon iNuevaCompra;
	public static ImageIcon iVenta;
	public static ImageIcon iNuevaVenta;
	public static ImageIcon iCotizacionVenta;
	public static ImageIcon iVerFactura;
	public static ImageIcon iProducto;
	public static ImageIcon iRegistrarProducto;
	public static ImageIcon iBuscarProducto;
	public static ImageIcon iReverso;
	public static ImageIcon iNuevoReverso;
	public static ImageIcon iCotizacion;
	public static ImageIcon iNuevaCotizacion;
	public static ImageIcon iVerCotizacion;
	public static ImageIcon iUsuario;
	public static ImageIcon iNuevoUsuario;
	public static ImageIcon iClienteProveedor;
	public static ImageIcon iNuevoClienteProveedor;
	public static ImageIcon iBuscarClienteProveedor;
	public static ImageIcon iReporte;
	public static ImageIcon iReporteTotal;
	public static ImageIcon iReporteInventario;
	public static ImageIcon iConfiguracion;
	public static ImageIcon iDatosEmpresa;
	public static ImageIcon iRolesUsuario;
	public static ImageIcon iCategoriaProducto;
	public static ImageIcon iPrecioDivisa;
	public static ImageIcon iAcercaDe;
	public static ImageIcon iAgregarPago;
	public static ImageIcon iEliminarPago;
	public static ImageIcon iEliminarProducto;
	public static Font TITULO_1_PLAIN = new Font("SansSerif", Font.PLAIN,16);
	public static Font TITULO_2_PLAIN = new Font("SansSerif", Font.PLAIN,14);
	public static Font TITULO_3_PLAIN = new Font("SansSerif", Font.PLAIN,12);
	
	public static Font TITULO_1_BOLD = new Font("SansSerif", Font.BOLD,16);
	public static Font TITULO_2_BOLD = new Font("SansSerif", Font.BOLD,14);
	public static Font TITULO_3_BOLD = new Font("SansSerif", Font.BOLD,12);

	
	
	
	public DatosConfiguracion() {

		iAceptar = new ImageIcon(".\\img\\iconos\\aceptar.png", "");
		iCancelar = new ImageIcon(".\\img\\iconos\\cancelar.png", "");
		iActualizar = new ImageIcon(".\\img\\iconos\\actualizar.png", "");
		iUsuarioAutenticacion = new ImageIcon(".\\img\\iconos\\usuario-autenticacion.png", "");
		iClaveAutenticacion = new ImageIcon(".\\img\\iconos\\clave-autenticacion.png", "");
		iArchivo = new ImageIcon(".\\img\\iconos\\archivo.png", "");
		iAbrirArchivo = new ImageIcon(".\\img\\iconos\\abrir-archivo.png", "");
		iImprimir = new ImageIcon(".\\img\\iconos\\imprimir.png", "");
		iSalir = new ImageIcon(".\\img\\iconos\\salir.png", "");
		iCompra = new ImageIcon(".\\img\\iconos\\compra.png", "");
		iNuevaCompra = new ImageIcon(".\\img\\iconos\\nueva-compra.png", "");
		iVenta = new ImageIcon(".\\img\\iconos\\venta.png", "");
		iNuevaVenta = new ImageIcon(".\\img\\iconos\\nueva-venta.png", "");
		iCotizacionVenta = new ImageIcon(".\\img\\iconos\\cotizacion-venta.png", "");
		iVerFactura = new ImageIcon(".\\img\\iconos\\ver-factura.png", "");
		iProducto = new ImageIcon(".\\img\\iconos\\producto.png", "");
		iRegistrarProducto = new ImageIcon(".\\img\\iconos\\nuevo-producto.png", "");
		iBuscarProducto = new ImageIcon(".\\img\\iconos\\buscar-producto.png", "");
		iReverso = new ImageIcon(".\\img\\iconos\\reverso.png", "");
		iNuevoReverso = new ImageIcon(".\\img\\iconos\\nuevo-reverso.png", "");
		iCotizacion = new ImageIcon(".\\img\\iconos\\cotizacion.png", "");
		iNuevaCotizacion = new ImageIcon(".\\img\\iconos\\nueva-cotizacion.png", "");
		iVerCotizacion = new ImageIcon(".\\img\\iconos\\ver-factura.png", "");
		iUsuario = new ImageIcon(".\\img\\iconos\\usuarios.png", "");
		iNuevoUsuario = new ImageIcon(".\\img\\iconos\\registrar-usuario.png", "");
		
		iClienteProveedor = new ImageIcon(".\\img\\iconos\\cliente-proveedor.png", "");
		iNuevoClienteProveedor = new ImageIcon(".\\img\\iconos\\registrar-cliente-proveedor.png", "");
		iBuscarClienteProveedor = new ImageIcon(".\\img\\iconos\\buscar-cliente.png", "");
		iReporte = new ImageIcon(".\\img\\iconos\\reporte.png", "");
		iReporteTotal = new ImageIcon(".\\img\\iconos\\reporte-total.png", "");
		iReporteInventario = new ImageIcon(".\\img\\iconos\\reporte-producto.png", "");
		iConfiguracion = new ImageIcon(".\\img\\iconos\\configuracion.png", "");
		iDatosEmpresa = new ImageIcon(".\\img\\iconos\\empresa.png", "");
		iRolesUsuario = new ImageIcon(".\\img\\iconos\\roles-usuario.png", "");
		iCategoriaProducto = new ImageIcon(".\\img\\iconos\\categoria.png", "");
		iPrecioDivisa = new ImageIcon(".\\img\\iconos\\precio-divisa.png", "");
		iAcercaDe = new ImageIcon(".\\img\\iconos\\logoAVSS.png", "");
		
		iAgregarPago = new ImageIcon(".\\img\\iconos\\agregar-pago.png", "");
		iEliminarPago = new ImageIcon(".\\img\\iconos\\eliminar-pago.png", "");
		iEliminarProducto = new ImageIcon(".\\img\\iconos\\eliminar-producto.png", "");
		
		
	}
	public DatosEmpresa getDatosEmpresa() {
		return datosEmpresa;
	}
	public void setDatosEmpresa(DatosEmpresa datosEmpresa) {
		this.datosEmpresa = datosEmpresa;
	}
	public DatosUsuario getDatosUsuario() {
		return datosUsuario;
	}
	public void setDatosUsuario(DatosUsuario datosUsuario) {
		this.datosUsuario = datosUsuario;
	}
	
	public Map<String, String> getListaTipoFactura() {
		return listaTipoFactura;
	}
	public void setListaTipoFactura(Map<String, String> listaTipoFactura) {
		this.listaTipoFactura = listaTipoFactura;
	}
	
	
	

}
