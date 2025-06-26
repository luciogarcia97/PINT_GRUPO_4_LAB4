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
    int dni = 0; 
    int cuil = 0;
    int usuario = 0;
    %>
    
    <%
    if(request.getAttribute("dni") != null)
    {
    	dni = Integer.parseInt(request.getAttribute("dni").toString());
    }
    
    if(request.getAttribute("cuil") != null)
    {
    	cuil = Integer.parseInt(request.getAttribute("cuil").toString());
    }
    
    if(request.getAttribute("usuario") != null)
    {
    	usuario = Integer.parseInt(request.getAttribute("usuario").toString());
    }
    %>

	    <form class="w-75 mx-auto mt-5" action="ServletCliente" method="post" onsubmit="return validarContraseñas()">
		    <div class="inicio">
		        <h2 class="text-center">Registrar Cliente</h2>
		        	   
		        <%
		        if(dni != 0){
		        %>
		        <div class="alert alert-danger" role="alert">
  				¡Ya existe un cliente con ese DNI!
				</div>
		        <%
		        }else if(cuil != 0){
		        %>
		        <div class="alert alert-danger" role="alert">
  				¡Ya existe un cliente con ese CUIL!
				</div>
		        <%
		        }
		        %>			       
		
		        <div class="row">
		            <div class="mb-3 col-md-6">
		                <label for="nombre" class="form-label">Nombre</label>
		                <input type="text" class="form-control" id="nombre" name="txtNombre" placeholder="Tu Nombre" required>
		            </div>
		            <div class="mb-3 col-md-6">
		                <label for="apellido" class="form-label">Apellido</label>
		                <input type="text" class="form-control" id="apellido" name="txtApellido" placeholder="Tu Apellido" required>
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
		                <input type="text" class="form-control" id="nacionalidad" name="txtNacionalidad" placeholder="Nacionalidad" required>
		            </div>
		
		            <div class="mb-3 col-md-6">
		                <label for="fechaNacimiento" class="form-label">Fecha Nacimiento</label>
		                <input type="date" class="form-control" id="fechaNacimiento" name="txtFechaNacimiento" required>
		            </div>
		            <div class="mb-3 col-md-6">
		                <label for="direccion" class="form-label">Dirección</label>
		                <input type="text" class="form-control" id="direccion" name="txtDireccion" placeholder="Tu Dirección" required>
		            </div>
		
		            <div class="mb-3 col-md-6">
		                <label for="localidad" class="form-label">Localidad</label>
		                <input type="text" class="form-control" id="localidad" name="txtLocalidad" placeholder="Localidad" required>
		            </div>
		            <div class="mb-3 col-md-6">
		                <label for="provincia" class="form-label">Provincia</label>
		                <input type="text" class="form-control" id="provincia" name="txtProvincia" placeholder="Provincia" required>
		            </div>
		
		            <div class="mb-3 col-md-6">
		                <label for="correo" class="form-label">Email</label>
		                <input type="email" class="form-control" id="correo" name="txtCorreo" placeholder="Email" required>
		            </div>
		            <div class="mb-3 col-md-6">
		                <label for="telefono" class="form-label">Teléfono</label>
		                <input type="number" class="form-control" id="telefono" name="txtTelefono" placeholder="Teléfono"
		                    oninput="this.value = this.value.slice(0, 11);" required>
		            </div>
		
		            <div class="mb-3 col-md-6">
		                <label for="dni" class="form-label">DNI</label>
		                <input type="number" class="form-control" id="dni" name="txtDni" placeholder="DNI"
		                    oninput="this.value = this.value.slice(0, 8);" required>
		            </div>
		            <div class="mb-3 col-md-6">
		                <label for="cuil" class="form-label">CUIL</label>
		                <input type="number" class="form-control" id="cuil" name="txtCuil" placeholder="CUIL"
		                    oninput="this.value = this.value.slice(0, 11);" required>
		            </div>
		         </div> 
		         
		       <div class="inicio">
          
		        <h2 class="text-center pt-2 pb-2">Registrar Usuario</h2>    
				
				<%
		        if(usuario != 0){
		        %>
		        <div class="alert alert-danger" role="alert">
  				¡Ya existe un usuario con ese nombre!
				</div>
		        <%
		        }
		        %>
				
				<div class="center row">					
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
		            <a href="administrarClientes.jsp">Cancelar</a>
		        </div>
		
		        <div class="mt-3">
		            <button type="submit" name="btnRegistrarCliente" class="btn btn-primary w-100">Registrar</button>
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