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
		
		<div class="container mt-4">
			<div class="d-flex justify-content-center gap-3">
				<a href="ServletClienteMenu" class="btn btn-primary btn-lg rounded-pill">
  					<i class="bi bi-list me-2"></i> Menú
				</a>
				<a href="ServletClienteMovimiento" class="btn btn-primary btn-lg rounded-pill">
	                <i class="bi bi-journal-bookmark-fill me-2"></i> Ver movimientos
	            </a>
	            <a href="ServletClientePrestamo" class="btn btn-primary btn-lg rounded-pill">
	                <i class="bi bi-cash-stack me-2"></i> Préstamos
	            </a>
	            <a href="ServletClienteTransferencia" class="btn btn-primary btn-lg rounded-pill">
	                <i class="bi bi-currency-dollar me-2"></i> Transferencias
	            </a>
	            <a href="ServletClienteDatos" class="btn btn-primary btn-lg rounded-pill">
	                <i class="bi bi-person-circle me-2"></i> Mis datos
	            </a>
			</div>	

		<div>
				<% 
				    String mensaje = (String) request.getAttribute("mensaje");
				    if (mensaje != null && !mensaje.trim().isEmpty()) {
				%>
				    <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
				        <i class="bi bi-check-circle-fill"></i>
				        <%= mensaje %>
				        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
				    </div>
				<% } %>
					
					
			
				<%
				    List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
				%>
			
				
			<div id="movimientos" class="panel">
					<h4>Movimientos de Cuenta</h4>
		
					<form action="ServletClienteMovimiento" method="get">				
					
						
						<div class="row mb-3">
							<div class="col-md-6">
								<label for="cuentaMovimientos" class="form-label">Seleccionar Cuenta</label> 
									<select class="form-select" id="cuentaMovimientos" name="idCuenta" required>
									<option value="" disabled selected>Seleccione una cuenta</option>
									<% if (cuentas != null) {
										Integer cuentaSeleccionada = (Integer) request.getAttribute("cuentaSeleccionada");
										for (Cuenta cuenta : cuentas) { 					                       
					                        boolean selected = (cuentaSeleccionada != null && cuentaSeleccionada.equals(cuenta.getIdCuenta()));					                        
					                %>
									<option value="<%= cuenta.getIdCuenta() %>"
										<%= selected ? "selected" : "" %>><%= cuenta.getNumeroCuenta() %></option>
							  		<% 	} 
					                   } 
									%>
								</select>
							</div>
							<div class="col-md-6">
								<label class="form-label">&nbsp;</label>
								<button type="submit" name="listar"	class="btn btn-primary form-control">
									Ver Movimientos
								</button>
							</div>
						</div>
					</form>
					<%
						// Mostrar saldo actual solo si hay cuenta seleccionada
						Integer cuentaSeleccionada = (Integer) request.getAttribute("cuentaSeleccionada");
						if (cuentas != null && cuentaSeleccionada != null) {
							for (Cuenta cuenta : cuentas) {
								if (cuentaSeleccionada.equals(cuenta.getIdCuenta())) {
					%>	
						
						<div class="mt-3">
							<div class="alert alert-primary" role="alert">
								<i class="bi bi-wallet2"></i>
								Saldo actual: <strong>$<%= cuenta.getSaldo() %></strong>
							</div>
						</div>
						
					<%
									break;
								}
							}
						}
					%>
										
		
					<% List<Movimiento> listaMovimientos = (List<Movimiento>) request.getAttribute("listaMovimientos");
				  		 if (listaMovimientos != null && !listaMovimientos.isEmpty()) {
				  	%>
					<hr>
					<h5>Historial de Movimientos</h5>
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Fecha</th>
									<th>Tipo</th>
									<th>Detalle</th>
									<th>Importe</th>
								</tr>
							</thead>
							<tbody>
								<% for (Movimiento mov : listaMovimientos) { %>
								<tr>
									<td><%= mov.getFecha() %></td>
									<td><%= mov.getTipoDescripcion() %></td>
									<td><%= mov.getDetalle() %></td>
									<td
										class="<%= mov.getImporte().doubleValue() >= 0 ? "text-success" : "text-danger" %>">$<%= mov.getImporte() %></td>
								</tr>
								<% } %>
							</tbody>
						</table>
					</div>
					<% } else if (request.getAttribute("cuentaSeleccionada") != null) { %>
					<div class="alert alert-info">No hay movimientos para esta cuenta.</div>
					<% } %>
		
				</div>
			</div>	

		</div>
	</main>			


	<footer class="bg-light text-center text-muted py-3 mt-5 ">
			<div class="container">
				<span>© 2025 Banco UTN – Todos los derechos reservados</span>
			</div>
	</footer>

	<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
