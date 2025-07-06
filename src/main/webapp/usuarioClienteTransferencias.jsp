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
			
				int saldoInsuficiente = 0;
					
				if(request.getAttribute("saldo") != null) {				  
					saldoInsuficiente = Integer.parseInt(request.getAttribute("saldo").toString());
				 }
					
				int cbuInexistente = 0;
					
				if(request.getAttribute("cbu") != null) {				   
					cbuInexistente = Integer.parseInt(request.getAttribute("cbu").toString());
				 }
			%>	
			
			
			
			<% 
				 String error = (String) request.getAttribute("error");
				 if (error != null && !error.isEmpty()) {
			 %>
				<div class="alert alert-danger alert-dismissible fade show"	role="alert">
					<i class="bi bi-exclamation-triangle-fill"></i>
					<%= error %>
					<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				</div>
			<% } %>		
					
			
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
		
		
			<div id="transferencias" class="panel" style="display: block">
					<h4>Transferencias</h4>
		
					<%
					if(saldoInsuficiente != 0){
				    %>
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Error:</strong> No tenés saldo suficiente para realizar la
						transferencia.
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"></button>
					</div>
					<%
				    }
				    %>
		
					<%
					if(cbuInexistente != 0){
				    %>
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Error:</strong> El CBU ingresado es inexistente.
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"></button>
					</div>
					<%
				    }
				    %>
		
					<form action="ServletTransferencia" method="post">
						<div class="mb-3">
							<label for="cuentaOrigen" class="form-label">Cuenta Origen</label>
		
							<select class="form-select" id="idCuenta" name="idCuenta">
								<% if (cuentas != null) {
				                for (Cuenta cuenta : cuentas) { %>
								<option value="<%= cuenta.getIdCuenta() %>"><%= cuenta.getNumeroCuenta() %>
									-
									<%= cuenta.getSaldo() %></option>
								<% }
			            	} %>
							</select>
						</div>
						<div class="mb-3">
							<label for="cbuDestino" class="form-label">CBU Destino</label> <input
								type="number" class="form-control" name="txtCbu" id="cbuDestino"
								placeholder="Ingrese CBU">
						</div>
						<div class="mb-3">
							<label for="montoTransferencia" class="form-label">Monto</label> <input
								type="number" name="txtMonto" class="form-control"
								id="montoTransferencia" placeholder="$XXXX">
						</div>
						<button type="submit" name="btnTransferencia"
							class="btn btn-success">Realizar Transferencia</button>
					</form>
					<hr>
					<h5>Historial de Transferencias</h5>
					<div class="col-11">
						<div class="table-responsive">
							<table id="example" class="table table-striped">
								<thead>
									<tr>
										<th>Fecha</th>
										<th>Monto</th>
										<th>Cuenta destino</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>01/01/2025</td>
										<td>$15.000</td>
										<td>***4352</td>
										<td>
									</tr>
									<tr>
										<td>05/03/2025</td>
										<td>$30.000</td>
										<td>***6672</td>
		
									</tr>
								</tbody>
		
							</table>
						</div>
					</div>
				</div>
			
		</div>
	
	</main>			


	<footer class="bg-light text-center text-muted py-3 mt-5 ">
			<div class="container">
				<span>© 2025 Banco UTN – Todos los derechos reservados</span>
			</div>
	</footer>


</body>
</html>