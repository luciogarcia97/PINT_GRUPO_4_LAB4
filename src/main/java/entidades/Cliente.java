package entidades;

public class Cliente {

	private int idCliente;
	private int dni;
	private String cuil;
	private String nombre;
	private String apellido;
	private String sexo;
	private String nacionalidad;
	private String fechaNacimiento;
	private String direccion;
	private String localidad;
	private String provincia;
	private String correoElectronico;
	private boolean eliminado;
	
	
	public Cliente(int idCliente, int dni, String cuil, String nombre, String apellido, String sexo,
			String nacionalidad, String fechaNacimiento, String direccion, String localidad, String provincia,
			String correoElectronico, boolean eliminado) 
	{
		this.idCliente = idCliente;
		this.dni = dni;
		this.cuil = cuil;
		this.nombre = nombre;
		this.apellido = apellido;
		this.sexo = sexo;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.correoElectronico = correoElectronico;
		this.eliminado = eliminado;
	}
	
	public Cliente() {}
	

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	@Override
	public String toString() {
		return "idCliente: " + idCliente + "\ndni: " + dni + "\ncuil: " + cuil + "\nnombre: " + nombre
				+ "\napellido: " + apellido + "\nsexo: " + sexo + "\nnacionalidad: " + nacionalidad + "\nfechaNacimiento: "
				+ fechaNacimiento + "\ndireccion: " + direccion + "\nlocalidad: " + localidad + "\nprovincia: "
				+ provincia + "\ncorreoElectronico: " + correoElectronico + "\neliminado: " + eliminado;
	}
	
	
	
	
	
}
