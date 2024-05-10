package ve.com.avss.empresa.bean;

public class DatosEmpresa {
	private String id = "";
	private String nombre = "";
	private String rif = "";
	private String direccion = "";
	private String logo = "";
	private String montoDivisa = ""; 
	private String correo = ""; 
	private String telefono = "";  
	private String rutaInstalacion = "";
	private String impuesto = "";
	private boolean validarCantidadProducto = false;
	
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
	public String getRif() {
		return rif;
	}
	public void setRif(String rif) {
		this.rif = rif;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getMontoDivisa() {
		return montoDivisa;
	}
	public void setMontoDivisa(String montoDivisa) {
		this.montoDivisa = montoDivisa;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getRutaInstalacion() {
		return rutaInstalacion;
	}
	public void setRutaInstalacion(String rutaInstalacion) {
		this.rutaInstalacion = rutaInstalacion;
	}
	public String getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}
	public boolean isValidarCantidadProducto() {
		return validarCantidadProducto;
	}
	public void setValidarCantidadProducto(boolean validarCantidadProducto) {
		this.validarCantidadProducto = validarCantidadProducto;
	}
	
	

}
