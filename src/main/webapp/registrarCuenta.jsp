<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>Agregar Cuenta</title>
    <link rel="icon" type="image/x-icon" href="img/banco.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">       
		
	<link rel="stylesheet" href="style/indexStyle.css" /> 

    </head>
<body>


     <form class="w-75 mx-auto mt-5">
	    <div class="inicio">
	        <h2 class="text-center">Agregar Cuenta</h2>
	
	        <div class="row">
	        
	        	<div class="center row">
	            <div class="mb-3">
	                <label for="usuario" class="form-label">Usuario</label> <%//Solo si el usuario existe, se puede hacer la cuenta %>
	                <input type="text" class="form-control" id="usuario" placeholder="Usuario" required>
	            </div> 
	        	<div class="mb-3 col-md-6">
	                <label for="tipo" class="form-label">Tipo de Cuenta</label>
	                <select id="tipo" name="tipo" class="form-control">
	                    <option value="cajaAhorro">Caja Ahorro</option>
	                    <option value="cuentaCorriente">Cuenta Corriente</option>	                    
	                </select>
	            </div>
	            
	            <div class="mb-3 col-md-6">
	                <label for="fechaCreacion" class="form-label">Fecha Creación</label>
	                <input type="date" class="form-control" id="fechaNacimiento" required>
	            </div>  
	            	        
		        <div class="text-center mt-3">
		            <a href="administrarCuentas.jsp">Cancelar</a>
		        </div>
		
		        <div class="mt-3">
		            <button type="submit" class="btn btn-primary w-100">Crear Cuenta</button>
		        </div>
	    </div>
	</form>     


	<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


</body>
</html>