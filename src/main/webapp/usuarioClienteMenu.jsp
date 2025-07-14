<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.*"%>
<%@ page import="entidades.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
		<title>© 2025 Banco UTN</title>
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
	
	<div class="d-flex justify-content-center align-items-center" style="min-height: 90vh;">
	    <div class="bg-white p-5 rounded-4 shadow" style="width: 600px;">
	    
	        <h2 class="text-center fw-bold mb-4">Aplicación Bancaria – <strong>UTN FRGP</strong></h2>
	        
	        <div class="d-grid gap-3">
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
	        
	    </div>
	</div>
	
	
	<footer class="bg-light text-center text-muted py-3 mt-5 ">
			<div class="container">
				<span>© 2025 Banco UTN – Todos los derechos reservados</span>
			</div>
	</footer>

</body>
</html>	




