<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entidades.Cliente"%>
<%@ page import="negocio.ClienteNegocio"%>
<%@ page import="negocioImpl.ClienteNegociolmpl"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modificar Cliente</title>
<link rel="icon" type="image/x-icon" href="img/banco.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet" href="style/indexStyle.css" />
</head>
<body>
	<%
	
	Cliente cliente = (Cliente) request.getAttribute("clienteEditado");

	if (cliente == null) {
	    String idStr = request.getParameter("id");
	    if (idStr != null) {
	        int id = Integer.parseInt(idStr);
	        ClienteNegocio negocio = new ClienteNegociolmpl();
	        cliente = negocio.BuscarPorID(id);
	    }
	} 
	
    int dni = 0; 
    int cuil = 0;
    
    if(request.getAttribute("dni") != null)
    {
    	dni = Integer.parseInt(request.getAttribute("dni").toString());
    }
    
    if(request.getAttribute("cuil") != null)
    {
    	cuil = Integer.parseInt(request.getAttribute("cuil").toString());
    }
    
    %>

	<% 
		  boolean dniInvalido = false;
		        	
		  if(request.getAttribute("dniInvalido") != null) {
			     dniInvalido = (boolean)request.getAttribute("dniInvalido");
			        	
			     if(dniInvalido){
	 %>
		<div class="alert alert-danger" role="alert">
			¡El DNI solo lleva números!
		</div>

	<% 	  }
		}		        
	%>

	<% 
		boolean cuilInvalido = false;
		        	
		if(request.getAttribute("cuilInvalido") != null) {
		    cuilInvalido = (boolean)request.getAttribute("cuilInvalido");
			        	
			if(cuilInvalido){
   %>
	<div class="alert alert-danger" role="alert">¡El CUIL solo lleva
		números!</div>

	<% 	  }
		}		        
	%>


	<% 
		 boolean menorEdad = false;
		        	
		if(request.getAttribute("menorEdad") != null) {
			 menorEdad = (boolean)request.getAttribute("menorEdad");
			        	
			if(menorEdad){
	%>
	<div class="alert alert-danger" role="alert">¡La persona no tiene
		la edad suficiente para registrarse como cliente!</div>

	<% 	  }
		        	}		        
		        %>




	<form class="w-75 mx-auto mt-5" action="ServletCliente" method="post"
		onsubmit="return validarContraseñas()">

		<div class="inicio">
			<h2 class="text-center">Modificar cliente</h2>

			<%
		        if(dni != 0){
		        %>
			<div class="alert alert-danger" role="alert">¡Ya existe un
				cliente con ese DNI!</div>
			<%
		        }else if(cuil != 0){
		        %>
			<div class="alert alert-danger" role="alert">¡Ya existe un
				cliente con ese CUIL!</div>
			<%
		        }
		        %>

			<div class="row">

				<input type="hidden" name="idCliente" class="form-control" id="id"
					value="<%= cliente != null ? cliente.getIdCliente() : "" %>"
					required>

				<div class="mb-3 col-md-6">

					<label for="nombre" class="form-label">Nombre</label> <input
						type="text" name="txtNombre" class="form-control" id="nombre"
						value="<%= cliente != null ? cliente.getNombre() : "" %>" required>
				</div>
				<div class="mb-3 col-md-6">
					<label for="apellido" class="form-label">Apellido</label> <input
						type="text" name="txtApellido" class="form-control" id="apellido"
						value="<%= cliente != null ? cliente.getApellido() : "" %>"
						required>

				</div>

				<div class="mb-3 col-md-6">
					<label for="sexo" class="form-label">Sexo</label> <select id="sexo"
						name="txtSexo" class="form-control">
						<option value="femenino"
							<%= cliente != null && "femenino".equals(cliente.getSexo()) ? "selected" : "" %>>Femenino</option>
						<option value="masculino"
							<%= cliente != null && "masculino".equals(cliente.getSexo()) ? "selected" : "" %>>Masculino</option>
						<option value="otro"
							<%= cliente != null && "otro".equals(cliente.getSexo()) ? "selected" : "" %>>No
							contesta</option>
					</select>
				</div>
				<div class="mb-3 col-md-6">
					<label for="nacionalidad" class="form-label">Nacionalidad</label> <input
						type="text" name="txtNacionalidad" class="form-control"
						id="nacionalidad" placeholder="Nacionalidad"
						value="<%= cliente != null ? cliente.getNacionalidad() : "" %>"
						required>
				</div>

				<div class="mb-3 col-md-6">
					<label for="fechaNacimiento" class="form-label">Fecha
						Nacimiento</label> <input type="date" name="txtFechaNacimiento"
						class="form-control" id="fechaNacimiento"
						value="<%= cliente != null ? cliente.getFechaNacimiento() : "" %>"
						required>
				</div>
				<div class="mb-3 col-md-6">
					<label for="direccion" class="form-label">Dirección</label> <input
						type="text" name="txtDireccion" class="form-control"
						id="direccion" placeholder="Tu Dirección"
						value="<%= cliente != null ? cliente.getDireccion() : "" %>"
						required>
				</div>

				<div class="mb-3 col-md-6">
					<label for="localidad" class="form-label">Localidad</label> <input
						type="text" name="txtLocalidad" class="form-control"
						id="localidad" placeholder="Localidad"
						value="<%= cliente != null ? cliente.getLocalidad() : "" %>"
						required>
				</div>
				<div class="mb-3 col-md-6">
					<label for="provincia" class="form-label">Provincia</label> <input
						type="text" name="txtProvincia" class="form-control"
						id="provincia" placeholder="Provincia"
						value="<%= cliente != null ? cliente.getProvincia() : "" %>"
						required>
				</div>

				<div class="mb-3 col-md-6">
					<label for="correo" class="form-label">Email</label> <input
						type="email" name="txtEmail" class="form-control" id="correo"
						placeholder="Email"
						value="<%= cliente != null ? cliente.getCorreoElectronico() : "" %>"
						required>
				</div>
				<div class="mb-3 col-md-6">
					<label for="dni" class="form-label">DNI</label> <input
						type="number" name="txtDni" class="form-control" id="dni"
						name="txtDni" placeholder="DNI"
						value="<%= cliente != null ? cliente.getDni() : "" %>" required>
				</div>
				<div class="mb-3 col-md-6">
					<label for="cuil" class="form-label">CUIL</label> <input
						type="number" name="txtCuil" class="form-control" id="cuil"
						 placeholder="CUIL"
						value="<%= cliente != null ? cliente.getCuil() : "" %>" required>
				</div>

			</div>

			<div class="text-center mt-3">
				<a href="administrarClientes.jsp">Cancelar</a>
			</div>

			<div class="mt-3">
				<button type="submit" name="btnModificarCliente"
					value="btnModificarCliente" class="btn btn-primary w-100">Modificar</button>
			</div>

		</div>
	</form>



	<script>
        function validarContraseñas() {
            const clave = document.getElementById("clave").value;
            const repetir = document.getElementById("repetirClave").value;

            if (clave !== repetir) {
            alert("Las contraseñas no coinciden.");
            return false; 
            }
            return true; 
        }
    </script>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


</body>
</html>


