<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
	    <title>Registrar</title>
	    <link rel="icon" type="image/x-icon" href="img/banco.png">
	
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">   		
		<link rel="stylesheet" href="style/indexStyle.css" />   
	</head>
<body>


	<%
	Boolean resultado = false;
	if(request.getAttribute("resultado") != null){
		resultado = Boolean.parseBoolean(request.getAttribute("resultado").toString());
	} 
	
	
	//
	// Cuando este terminado el ABML Cliente en realidad lo que tenemos que traer aca es el Cliente antes de
	// cargarlo a la DB, asi primero verificamos que el usuario tenga bien todos los valores.
	// De otra forma podemos cargar un cliente a la DB y que despues el usuario sea rechazado, quedandonos el cliente
	// Sin usuario.
	
	%>

	<form class="w-75 mx-auto mt-5" onsubmit="return validarContraseñas()" action="ServletUsuario" method="post">
		    <div class="inicio">
          
		        <h2 class="text-center pt-2 pb-2">Registrar Usuario</h2>    
				
				<div class="center row">
					<div class="mb-3"> <%//Si viene desde registrarCliente.jsp este campo se autocompleta%>
		                <label for="usuario" class="form-label">ID de cliente</label>
		               <input type="text" class="form-control" id="usuario" name="txtIDCliente" placeholder="ID de cliente" value="<%= request.getAttribute("IDCliente") %>" required>

		            </div>
		            <div class="mb-3">
		                <label for="usuario" class="form-label">Usuario</label>
		                <input type="text" class="form-control" id="usuario" name="txtUsuario" placeholder="Usuario" required>
		            </div>
		            <div class="mb-3">
		                <label for="clave" class="form-label">Contraseña</label>
		                <input type="password" class="form-control" id="clave" name="txtContrasena" placeholder="Tu contraseña" required>
		            </div>
		
		            <div class="mb-3">
		                <label for="repetirClave" class="form-label">Repetir Contraseña</label>
		                <input type="password" class="form-control" id="repetirClave" name="txtContrasenaR" placeholder="Repetir Contraseña" required>
		            </div>
		        </div>
		
		        <div class="text-center mt-3">
		            <a href="administrarUsuarios.jsp">Cancelar</a>
		        </div>
		
		        <div class="mt-3">
		            <button type="submit" class="btn btn-primary w-100" name="btnRegistrarUsuario">Registrar</button>
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


	<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	
    </body>
</html>