<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>Modificar usuario</title>
    <link rel="icon" type="image/x-icon" href="img/banco.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">       
		
	<link rel="stylesheet" href="style/indexStyle.css" /> 

    </head>
<body>	

	<%
    	String idUsuario = request.getParameter("id");
	    String idCliente = request.getParameter("idCliente");
	    String fechaCreacion = request.getParameter("fechaCreacion");
	%>


     <form class="w-75 mx-auto mt-5" onsubmit="return validarContraseñas()" action="ServletUsuario" method="post">
     	<input type="hidden" name="idUsuario" value="<%= idUsuario %>">
     	<input type="hidden" name="idCliente" value="<%= idCliente %>">
		<input type="hidden" name="fechaCreacion" value="<%= fechaCreacion %>">
	    <div class="inicio">
	         
	        <h2 class="text-center pt-2 pb-2"> Modificar usuario</h2>    
			
			<div class="center row">
	            <div class="mb-3">
	                <label for="usuario" class="form-label">Usuario</label>
	                <input type="text" class="form-control" id="usuario" placeholder="Usuario" name="txtNombre" required>
	            </div>
	            <div class="mb-3">
	                <label for="clave" class="form-label">Contraseña</label>
	                <input type="password" class="form-control" id="clave" placeholder="Tu contraseña" name="txtContrasena" required>
	            </div>
	
	            <div class="mb-3">
	                <label for="repetirClave" class="form-label">Repetir Contraseña</label>
	                <input type="password" class="form-control" id="repetirClave" placeholder="Repetir Contraseña" name="txtReContrasena" required>
	            </div>
	        </div>	        
	
	        <div class="text-center mt-3">
	            <a href="ServletUsuario?listar=1">Cancelar</a>
	        </div>
	
	        <div class="mt-3">
	            <button type="submit" class="btn btn-primary w-100" name="btnModificar" >Modificar</button>
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