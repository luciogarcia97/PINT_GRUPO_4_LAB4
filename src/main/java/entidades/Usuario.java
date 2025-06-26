package entidades;

import java.time.LocalDate;

public class Usuario {

	private int id_usuario;
	private int id_cliente;
	private String usuario;
	private String contrasena;
	private String tipo_usuario;
	private int eliminado;
	private LocalDate fecha_creacion;

	public Usuario() {
		super();
	}

	public Usuario(int id_usuario, int id_cliente, String usuario, String contrasena, String tipo_usuario,
			int eliminado, LocalDate fecha_creacion) {
		super();
		this.id_usuario = id_usuario;
		this.id_cliente = id_cliente;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.tipo_usuario = tipo_usuario;
		this.eliminado = eliminado;
		this.fecha_creacion = fecha_creacion;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getTipo_usuario() {
		return tipo_usuario;
	}

	public void setTipo_usuario(String tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}

	public int getEliminado() {
		return eliminado;
	}

	public void setEliminado(int eliminado) {
		this.eliminado = eliminado;
	}

	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	@Override
	public String toString() {
		return "Usuario [id_usuario=" + id_usuario + ", id_cliente=" + id_cliente + ", usuario=" + usuario
				+ ", contrasena=" + contrasena + ", tipo_usuario=" + tipo_usuario + ", eliminado=" + eliminado
				+ ", fecha_creacion=" + fecha_creacion + "]";
	}	
	

}
