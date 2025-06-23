<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
    <title>Modificar Cliente</title>
    <link rel="icon" type="image/x-icon" href="img/banco.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">       
		
	<link rel="stylesheet" href="style/indexStyle.css" /> 
	</head>
	<body>

     <form class="w-75 mx-auto mt-5"  action="ServletCliente" method="post" onsubmit="return validarContraseñas()">
	    <div class="inicio">
	        <h2 class="text-center">Modificar cliente</h2>
	
	        <div class="row">
	            <div class="mb-3 col-md-6">
	                <label for="nombre" class="form-label">Nombre</label>
	                <input type="text"  name="txtNombre" class="form-control" id="nombre" placeholder="Tu Nombre" required>
	            </div>
	            <div class="mb-3 col-md-6">
	                <label for="apellido" class="form-label">Apellido</label>
	                <input type="text" name="txtApellido" class="form-control" id="apellido" placeholder="Tu Apellido" required>
	            </div>
	
	            <div class="mb-3 col-md-6">
	                <label for="sexo" class="form-label">Sexo</label>
	                <select id="sexo" name="txtSexo" class="form-control">
	                    <option value="femenino">Femenino</option>
	                    <option value="masculino">Masculino</option>
	                    <option value="otro">No contesta</option>
	                </select>
	            </div>
	            <div class="mb-3 col-md-6">
	                <label for="nacionalidad" class="form-label">Nacionalidad</label>
	                <input type="text" name="txtNacionalidad" class="form-control" id="nacionalidad" placeholder="Nacionalidad" required>
	            </div>
	
	            <div class="mb-3 col-md-6">
	                <label for="fechaNacimiento" class="form-label">Fecha Nacimiento</label>
	                <input type="date"  name="txtFechaNacimiento" class="form-control" id="fechaNacimiento" required>
	            </div>
	            <div class="mb-3 col-md-6">
	                <label for="direccion" class="form-label">Dirección</label>
	                <input type="text" name="txtDireccion" class="form-control" id="direccion" placeholder="Tu Dirección" required>
	            </div>
	
	            <div class="mb-3 col-md-6">
	                <label for="localidad" class="form-label">Localidad</label>
	                <input type="text" name="txtLocalidad" class="form-control" id="localidad" placeholder="Localidad" required>
	            </div>
	            <div class="mb-3 col-md-6">
	                <label for="provincia" class="form-label">Provincia</label>
	                <input type="text"name="txtProvincia" class="form-control" id="provincia" placeholder="Provincia" required>
	            </div>
	
	            <div class="mb-3 col-md-6">
	                <label for="correo" class="form-label">Email</label>
	                <input type="email" name="txtEmail" class="form-control" id="correo" placeholder="Email" required>
	            </div>
	            <div class="mb-3 col-md-6">
	                <label for="telefono" class="form-label">Teléfono</label>
	                <input type="number" name="txtTelefono" class="form-control" id="telefono" placeholder="Teléfono"
	                    oninput="this.value = this.value.slice(0, 11);" required>
	            </div>
	
	         </div>   
	
	        <div class="text-center mt-3">
	            <a href="administrarClientes.jsp">Cancelar</a>
	        </div>
	
	        <div class="mt-3">
	            <button type="submit" name="btnModificarCliente" class="btn btn-primary w-100">Modificar</button>
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