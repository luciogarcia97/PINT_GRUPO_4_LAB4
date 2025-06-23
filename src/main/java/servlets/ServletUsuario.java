package servlets;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Usuario;
import negocio.UsuarioNegocio;
import negocioImpl.UsuarioNegocioImpl;


@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UsuarioNegocio usuarioNegocio;   
   
    public ServletUsuario() {
        super(); 
        this.usuarioNegocio = new UsuarioNegocioImpl();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
			
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if(request.getParameter("btnRegistrarUsuario") != null) {
			Usuario usuario = new Usuario();
			usuario.setId_usuario(9);
			usuario.setId_cliente(6);
			usuario.setUsuario("nombreNuevo");
			usuario.setContrasena("000");
			usuario.setTipo_usuario("cliente6");
			usuario.setEliminado(0);
			usuario.setFecha_creacion(LocalDate.now());
			
			if(usuarioNegocio.insertarUsuario(usuario)) {
				System.out.println("usuario insertado");
			}
		}
		
		
	}

}
