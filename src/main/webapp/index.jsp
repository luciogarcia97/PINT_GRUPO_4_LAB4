<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login Banco UTN</title>
		<link rel="icon" type="image/x-icon" href="img/banco.png">

		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
		
		<link rel="stylesheet" href="style/indexStyle.css" />
	</head>
<body>



	<form class="w-25 mx-auto mt-5" action="ServletLogin" method="get">
		
		<div class="inicio">
            <h2 class="text-center">Home Banking UTN</h2>
            <% if (request.getAttribute("error") != null) { %>
				<div class="alert alert-danger" role="alert">
					<i class="bi bi-exclamation-triangle me-2"></i>
					<%= request.getAttribute("error") %>
				</div>
			<% } %>
		<div class="mb-3">
			<label for="usuario" class="form-label">Usuario</label>
				<input type="text" class="form-control" id="usuario" name="usuario"
				placeholder="Tu usuario">
		</div>		
		<div class="mb-3">
			<label for="clave" class="form-label">Contraseña</label> <input
				type="password" class="form-control" id="clave" name="contrasena"
				placeholder="Tu contraseña">
		</div>
		
    	<br>		
		<button type="submit" class="btn btn-primary w-100" name="btnLogin" >Ingresar</button>
        </div>
	</form>


	<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


</body>
</html>