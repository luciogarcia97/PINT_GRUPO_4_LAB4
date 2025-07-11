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
	
		
			<div class="d-flex justify-content-center gap-3 pb-4">
				<a href="ServletClienteMovimiento" class="btn btn-primary">Ver Movimientos</a>
				<a href="ServletClientePrestamo" class="btn btn-primary">Préstamos</a>
				<a href="ServletClienteTransferencia" class="btn btn-primary"> Transferencias</a>			
				<a href="ServletClienteDatos" class="btn btn-primary">Mis Datos</a>
			</div>
			
			<div>
				 <% if (request.getAttribute("error") != null) { %>
	                <div class="alert alert-danger" role="alert" >
	                    <%= request.getAttribute("error") %>
	                </div>
	            <% } %>
	            
	            <% if (request.getAttribute("mensaje") != null) { %>
				    <div class="alert alert-success" role="alert">
				        <%= request.getAttribute("mensaje") %>
				    </div>
				<% } %>			
			
			</div>			
					
		
		<div id="prestamos" class="panel" style="display: block">
				<h4>Solicitar Préstamo</h4>
				<form action="ServletClientePrestamo" method="post">
					<div class="mb-3">
						<label for="cuentaDestino" class="form-label">Cuenta Destino</label> <select class="form-select" id="cuentaPrestamo"
							name="cuentaDestino">
							<% if (cuentas != null) {
	                			for (Cuenta cuenta : cuentas) { %>
							<option value="<%= cuenta.getIdCuenta() %>"><%= cuenta.getNumeroCuenta() %>
								-
								<%= cuenta.getSaldo() %></option>
							<% 		}
	           					 } 
	           				%>
						</select>
					</div>
					<div class="mb-3">
						<label for="montoPrestamo" class="form-label">Monto	solicitado</label>
						 <input type="number" class="form-control" min="0" max="500000" step="0.01" placeholder="0"
							id="montoPrestamo" name="montoPrestamo">
					</div>
					<div class="mb-3">
						<label for="cuotasPrestamo" class="form-label">Cantidad de	cuotas</label> 
							<select class="form-select" id="cuotasPrestamo"	name="cuotasPrestamo" required>
								<option value="3">3</option>
								<option value="6">6</option>
								<option value="12">12</option>
							</select>
					</div>
					<button type="submit" class="btn btn-success"
						name="btnSolicitarPrestamo">Pedir Préstamo</button>
				</form>
			</div>
	
			<div id="pagoPrestamos" class="panel" style="display: block">
			    <h4>Pago de prestamos</h4>
			    <form method="post" action="ServletClientePrestamo">
			        <div class="mb-3">
			            <label for="prestamoSeleccionado" class="form-label">Préstamo</label>
			            <select class="form-select" id="prestamoSeleccionado" name="prestamoSeleccionado" onchange="this.form.submit()">
			                <option value="">Seleccione un préstamo</option>
			                <%
			                List<Prestamo> prestamosConCuotasPendientes = (List<Prestamo>) request.getAttribute("prestamosConCuotasPendientes");
			                String prestamoSeleccionadoId = (String) request.getAttribute("prestamoSeleccionadoId");
			                if (prestamosConCuotasPendientes != null) {
			                    for (Prestamo prestamo : prestamosConCuotasPendientes) {
			                        String selected = (prestamoSeleccionadoId != null && prestamoSeleccionadoId.equals(String.valueOf(prestamo.getId_prestamo()))) ? "selected" : "";
			                %>
			                    <option value="<%= prestamo.getId_prestamo() %>" <%= selected %>>
			                        Préstamo #<%= prestamo.getId_prestamo() %> - $<%= String.format("%.2f", prestamo.getImporte_solicitado()) %>
			                    </option>
			                <% 
			                    }
			                }
			                %>
			            </select>
			        </div>
			
			        <div class="mb-3">
			            <label for="cuotaSeleccion" class="form-label">Cuota a pagar</label>
			            <select class="form-select" id="cuotaSeleccion" name="cuotaSeleccion" <%= (request.getAttribute("cuotasPendientes") == null) ? "disabled" : "" %>>
			                <%
			                List<PrestamoCuota> cuotasPendientes = (List<PrestamoCuota>) request.getAttribute("cuotasPendientes");
			                if (cuotasPendientes != null && !cuotasPendientes.isEmpty()) {
			                    for (PrestamoCuota cuota : cuotasPendientes) {
			                %>
			                    <option value="<%= cuota.getIdCuota() %>|<%= cuota.getMonto() %>">
			                        Cuota <%= cuota.getNumCuota() %> - $<%= String.format("%.2f", cuota.getMonto()) %> - Vence: <%= cuota.getFechaVencimiento() %>
			                    </option>
			                <%
			                    }
			                } else {
			                %>
			                    <option value="">Primero seleccione un préstamo</option>
			                <%
			                }
			                %>
			            </select>
			        </div>
			
			        <div class="mb-3">
			            <label for="cuentaPago" class="form-label">Cuenta de Pago</label>
			            <select class="form-select" id="cuentaPago" name="cuentaPago">
			                <%
			                if (cuentas != null) {
			                    for (Cuenta cuenta : cuentas) {
			                %>
			                    <option value="<%= cuenta.getIdCuenta() %>">
			                        <%= cuenta.getNumeroCuenta() %> - $<%= String.format("%.2f", cuenta.getSaldo()) %>
			                    </option>
			                <%
			                    }
			                }
			                %>
			            </select>
			        </div>
			
			        <button type="submit" class="btn btn-success" name="btnPagarCuota" 
			                <%= (request.getAttribute("cuotasPendientes") == null) ? "disabled" : "" %>>
			            Pagar Cuota
			        </button>
			    </form>
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