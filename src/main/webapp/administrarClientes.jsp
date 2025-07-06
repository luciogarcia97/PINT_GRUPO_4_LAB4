<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidades.Cliente"%>
<%@ page import="entidades.Usuario" %>
<%
    // Verificar autenticación de admin
    Usuario adminLogueado = (Usuario) session.getAttribute("adminLogueado");
    if (adminLogueado == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>

<html lang="es">

<head>
		<meta charset="UTF-8">
		<title>Administrador - Clientes</title>
		<link rel="icon" type="image/x-icon" href="img/banco.png">
		
		<link
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
			rel="stylesheet">
		<link rel="stylesheet"
			href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
		<link rel="stylesheet"
			href="https://cdn.datatables.net/2.3.2/css/dataTables.bootstrap5.css">
		
		<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
		<script src="https://cdn.datatables.net/2.3.2/js/dataTables.min.js"></script>
		<script
			src="https://cdn.datatables.net/2.3.2/js/dataTables.bootstrap5.min.js"></script>
</head>

<body
	style="background-color: rgb(104, 109, 250); min-height: 100vh; display: flex; flex-direction: column;">

	<nav class="navbar navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="#"> <i class="bi bi-bank2 me-2"></i>
				Administrador - Clientes
			</a>
			<form action="ServletLogin" method="get" class="d-inline">
				<button class="btn btn-outline-dark" type="submit" name="btnCerrar">Cerrar
					Sesión</button>
			</form>

		</div>
	</nav>	
	

	<div class="container text-center flex-grow-1">
		<div class="row">

			<div
				class="col-2 d-flex flex-column align-items-center justify-content-start pt-4">
				<div class="d-grid gap-2 w-100">

					<a href="ServletCliente?listar=1" class="btn btn-primary fw-bold">
						<i class="bi bi-people me-2"></i> Administrar Clientes
					</a>					
					<a href="ServletUsuario?listar=1" class="btn btn-light"> <i
						class="bi bi-person-gear me-2"></i> Administrar Usuarios
					</a>
				    <a href="ServletCuenta?listar=!" class="btn btn-light"> <i
						class="bi bi-credit-card me-2"></i> Administrar Cuentas
					</a>
				    <a href="ServletPrestamo?listar=1" class="btn btn-light"> <i
						class="bi bi-cash-coin me-2"></i> Autorizar Préstamos
					</a> 
					<a href="reportesInformes.jsp" class="btn btn-light"> <i
						class="bi bi-graph-up me-2"></i> Informe/Reportes
					</a>

				</div>
			</div>

			<div class="col-10">

				<div
					class="d-flex justify-content-between align-items-center mb-4 pt-4">
					<h3 class="text-white">
						<i class="bi bi-people me-2"></i> Administrar Clientes
					</h3>
					<a href="ServletCliente?cargarFormulario=1" class="btn btn-success"> <i
						class="bi bi-plus me-1"></i> Registrar Cliente y Usuario
					</a>
				</div>
				
				<div>
						<% 
							 boolean errorBorrar = false;		        	
							 if(request.getAttribute("error") != null) {
								    errorBorrar = (boolean)request.getAttribute("error");
								        	
								    if(errorBorrar){
						 %>
							<div class="alert alert-danger" role="alert">
								¡No se pudo eliminar el cliente!
							</div>
					
						<% 	  }
							}		        
						%>
						
						<% 
							 boolean errorModificar = false;		        	
							 if(request.getAttribute("errorModificar") != null) {
								    errorModificar = (boolean)request.getAttribute("errorModificar");
								        	
								    if(errorModificar){
						 %>
							<div class="alert alert-danger" role="alert">
								¡No se puede modificar un cliente que ya está eliminado¡
							</div>
					
						<% 	  }
							}		        
						%>						
						
						<% 
							 boolean exitoBorrar = false;		        	
							 if(request.getAttribute("exito") != null) {
								   exitoBorrar = (boolean)request.getAttribute("exito");
								        	
								   if(exitoBorrar){
						 %>
							<div class="alert alert-success" role="alert">
								¡Cliente, usuario y sus cuentas relacionadas eliminados!
							</div>
					
						<% 	  }
							}		        
						%>	
						
						<% 
							 boolean exitoModificado = false;		        	
							 if(request.getAttribute("exitoModificado") != null) {
								   exitoModificado = (boolean)request.getAttribute("exitoModificado");
								        	
								   if(exitoModificado){
						 %>
							<div class="alert alert-success" role="alert">
								¡Cliente modificado con éxito!
							</div>
					
						<% 	  }
							}		        
						%>	
						
									
				</div>				

				<div class="card shadow">
					<div class="card-header bg-primary text-white">
						<h5 class="mb-0">
							<i class="bi bi-table me-2"></i> Lista de Clientes
						</h5>
					</div>
					<div class="card-body bg-white">
						<div class="table-responsive">
							<table id="example" class="table table-striped table-hover">
								<thead class="table-light">
									<tr>
										<th>IDCliente</th>
										<th>DNI</th>
										<th>Nombre</th>
										<th>Apellido</th>
										<th>Sexo</th>
										<th>Fecha de Nacimiento</th>
										<th>Direccion</th>
										<th>Localidad</th>
										<th>Provincia</th>
										<th>Email</th>
										<th>Estado</th>
										<th>Acciones</th>
									</tr>
								</thead>
								<tbody>
									<% List<entidades.Cliente> listaClientes = (List<entidades.Cliente>) request.getAttribute("listaClientes");
                                                        if (listaClientes != null) {
                                                        for (entidades.Cliente c : listaClientes) {
                                                        %>
									<tr>
										<td><%= c.getIdCliente() %></td>
										<td><%= c.getDni() %></td>
										<td><%= c.getNombre() %></td>
										<td><%= c.getApellido() %></td>
										<td><%= c.getSexo() %></td>
										<td><%= c.getFechaNacimiento() %></td>
										<td><%= c.getDireccion() %></td>
										<td><%= c.getLocalidad() %></td>
										<td><%= c.getProvincia() %></td>
										<td><%= c.getCorreoElectronico() %></td>
										<td>
											<span class="badge <%= c.getEliminado() ? "bg-danger" : "bg-success" %>">
													<%= c.getEliminado() ? "Inactivo" : "Activo" %>
											</span>
										</td>
										<td>
											<div class="d-flex gap-1">
												<a href="modificarCliente.jsp?id=<%= c.getIdCliente() %>"
													class="btn btn-sm btn-outline-primary"
													title="Modificar Cliente"> <i class="bi bi-pencil"></i>
												</a>
												<form action="ServletCliente?eliminar=1" method="post"
													class="d-inline">
													<input type="hidden" name="idCliente"
														value="<%= c.getIdCliente() %>" />														
													<button type="submit" class="btn btn-sm btn-outline-danger" 
														onclick="return confirm('¿Estás seguro que deseas eliminar este cliente?')"
														title="Eliminar Cliente" name="btnEliminarCliente">
														<i class="bi bi-trash"></i>
													</button>
												</form>
											</div>
										</td>
									</tr>
									<% } } %>
								</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<footer class="bg-light text-center text-muted py-3 mt-auto">
		<div class="container">
			<span>© 2025 Banco UTN – Todos los derechos reservados</span>
		</div>
	</footer>

	<script>
       new DataTable('#example');
    </script>

</body>

</html>