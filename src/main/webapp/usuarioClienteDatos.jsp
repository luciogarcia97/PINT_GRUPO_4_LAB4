<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="entidades.*"%>
<%
    // Autenticación de cliente
   Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) {
        response.sendRedirect("index.jsp");
        return;
    }      
%>

<!DOCTYPE html>
<html lang="es">
<head>
		<meta charset="UTF-8">
		<title>Cliente</title>
		<link rel="icon" type="image/x-icon" href="img/banco.png">
		
		<link
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
			rel="stylesheet">
		<link rel="stylesheet"
			href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
		
		<link rel="stylesheet" href="style/usuarioClienteStyle.css" />		
</head>

<body class="d-flex flex-column min-vh-100">	
	<nav class="navbar navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="#"> <i class="bi bi-person-circle"></i>
				<% 
		            Cliente cliente = (Cliente) request.getAttribute("cliente");
		            if (cliente != null) {
		                out.print(cliente.getNombre() + " " + cliente.getApellido());
		            } else {
		                out.print("Usuario Cliente");
		            }
           		 %>
			</a>
			<form action="ServletLogin" method="get" class="d-inline">
				<button class="btn btn-outline-dark" type="submit" name="btnCerrar">Cerrar Sesión</button>
			</form>
		</div>
	</nav>
	
	<main class="flex-grow-1">
	
		<%
		   List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
		%>
		
		<div class="container mt-4">	
		
			<div class="d-flex justify-content-center gap-3">
				<a href="ServletClienteMovimiento" class="btn btn-primary">Ver Movimientos</a>
				<a href="ServletClientePrestamo" class="btn btn-primary">Préstamos</a>
				<a href="ServletClienteTransferencia" class="btn btn-primary"> Transferencias</a>			
				<a href="ServletClienteDatos" class="btn btn-primary">Mis Datos</a>
			</div>	
			
			<div id="datos" style="background-color: rgb(104, 109, 250); display: block">
				<div id="datosPersonales" class="mt-3 container bg-white p-4 rounded">
					<h4 class="mb-3">Datos Personales</h4>
					<% if (cliente != null) { %>
					<ul>
						<li><strong>Nombre:</strong> <%= cliente.getNombre() %> <%= cliente.getApellido() %></li>
						<li><strong>DNI:</strong> <%= cliente.getDni() %></li>
						<li><strong>CUIL:</strong> <%= cliente.getCuil() %></li>
						<li><strong>Correo Electrónico:</strong> <%= cliente.getCorreoElectronico() %></li>
						<li><strong>Dirección:</strong> <%= cliente.getDireccion() %></li>
						<li><strong>Localidad:</strong> <%= cliente.getLocalidad() %></li>
						<li><strong>Provincia:</strong> <%= cliente.getProvincia() %></li>
						<li><strong>Nacionalidad:</strong> <%= cliente.getNacionalidad() %></li>
						<li><strong>Fecha de Nacimiento:</strong> <%= cliente.getFechaNacimiento() %></li>
						<li><strong>Sexo:</strong> <%= cliente.getSexo() %></li>
					</ul>
					<% } else { %>
						<p class="text-muted">No se pudieron cargar los datos personales.</p>
					<% } %>
				</div>
	
				<div id="datosCuenta" class="mt-3 container bg-white p-4 rounded">
					<h4 class="mb-3">Mis cuentas</h4>
					<% if (cuentas != null && !cuentas.isEmpty()) { %>
					<table id="example" class="table table-striped">
						<thead>
							<tr>
								<th>Número de cuenta</th>
								<th>Tipo de cuenta</th>
								<th>Saldo</th>
								<th>CBU</th>
								<th>Fecha de creación</th>
							</tr>
						</thead>
						<tbody>
							<% 
		                    List<TipoCuenta> tiposCuenta = (List<TipoCuenta>) request.getAttribute("tiposCuenta");
		                    for (Cuenta cuenta : cuentas) { 
		                    %>
							<tr>
								<td><%= cuenta.getNumeroCuenta() %></td>
								<td>
									<% 
		                                if (tiposCuenta != null) {
		                                    for (TipoCuenta tipo : tiposCuenta) {
		                                        if (tipo.getId() == cuenta.getIdTipoCuenta()) {
		                                            out.print(tipo.getNombre());
		                                            break;
		                                        }
		                                    }
		                                }
		                                %>
								</td>
								<td>$<%= cuenta.getSaldo() %></td>
								<td><%= cuenta.getCbu() %></td>
								<td><%= cuenta.getFechaCreacion() %></td>
							</tr>
							<% } %>
						</tbody>
					</table>
					<% } else { %>
					<p class="text-muted">No tienes cuentas registradas.</p>
					<% } %>
				</div>
	
			</div>
		</div>
	</main>
	
	<footer class="bg-light text-center text-muted py-3 mt-5">
			<div class="container">
				<span>© 2025 Banco UTN – Todos los derechos reservados</span>
			</div>
	</footer>	

</body>
</html>