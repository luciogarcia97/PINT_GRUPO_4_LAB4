<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="entidades.*" %>
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

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <style>
        .panel {
            display: none;
            background-color: white;
            padding: 20px;
            margin-top: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
        }
        .active-button {
            background-color: #0056b3;
            color: white;
        }
    </style>
</head>

<body style="background-color: rgb(104, 109, 250);">
	<%
	
	int saldoInsuficiente = 0;
	
	if(request.getAttribute("saldo") != null)
    {
		saldoInsuficiente = Integer.parseInt(request.getAttribute("saldo").toString());
    }
	
	int cbuInexistente = 0;
	
	if(request.getAttribute("cbu") != null)
    {
		cbuInexistente = Integer.parseInt(request.getAttribute("cbu").toString());
    }
	%>
<nav class="navbar navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <i class="bi bi-person-circle"></i> 
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

<div class="container mt-4">
    <% 
    String error = (String) request.getAttribute("error");
    if (error != null && !error.isEmpty()) {
    %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill"></i> <%= error %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    <% } %>
    
    <div class="d-flex justify-content-center gap-3">
        <button class="btn btn-primary" onclick="togglePanel('movimientos', this)">Ver Movimientos</button>
        <button class="btn btn-primary" onclick="togglePanel('transferencias', this)">Transferencias</button>
        <button class="btn btn-primary" onclick="togglePanel('prestamos', this)">Préstamos</button>
        <button class="btn btn-primary" onclick="togglePanel('pagoPrestamos', this)">Pago de prestamos</button>
        <button class="btn btn-primary" onclick="togglePanel('datos', this)">Mis Datos</button>
    </div>
    
    <div id="movimientos" class="panel">
    <h4>Movimientos</h4>
    
    <form action="">
    		
    		 <div class="mb-3">
                <label for="cuentaOrigen" class="form-label">Cuenta Origen</label>
               
                <select class="form-select" id="cuentaOrigen"  onchange="mostrarSaldo()">
                    <option selected disabled>Seleccione una cuenta</option>
                    <% 
                    List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentas");
                    if (cuentas != null) {
                        for (Cuenta cuenta : cuentas) {
                    %>
                        <option value="<%= cuenta.getIdCuenta() %>"><%= cuenta.getNumeroCuenta() %> - <%= cuenta.getSaldo() %></option>
                    <%
                        }
                    }
                    %>
                </select>
            </div>
            
             <div id="saldoVisor" class="alert alert-info d-none">
		        Saldo disponible: <strong id="saldoValor">$0.00</strong>
		    </div>
    
    
	        <div class="col-11">	
		      <div class="table-responsive">
		          <table id="example" class="table table-striped">
		            <thead>
		                <tr>
		                    <th >Tipo de movimiento</th>
		                    <th >Fecha</th>
		                    <th >Monto</th>                                    
		                </tr>
		            </thead>
		            <tbody>
		                <tr>
		                    <td>Desposito</td>
		                    <td>01/01/2025</td>
		                    <td>$10.000</td>            
		                    <td>
		                                        
		                </tr>
		                <tr>
		                    <td>Pago de prestamo</td>
		                    <td>05/03/2025</td>
		                    <td>$30.000</td>            
		                                       
		                </tr>	
		                </tbody>
		           
		        </table>
		      </div>	
		    </div>
	 </form>  
	    
    </div>
    
    <div id="transferencias" class="panel">
        <h4>Transferencias</h4>
        
        	<%
			if(saldoInsuficiente != 0){
		    %>
		    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        		<strong>Error:</strong> No tenés saldo suficiente para realizar la transferencia.
        		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    		</div>
		    <%
		    }
		    %>
		    
		    <%
			if(cbuInexistente != 0){
		    %>
		    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        		<strong>Error:</strong> El CBU ingresado es inexistente.
        		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
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
		                	<option value="<%= cuenta.getIdCuenta() %>"><%= cuenta.getNumeroCuenta() %> - <%= cuenta.getSaldo() %></option>
		            	<% }
	            	} %>
        		</select>
            </div>
            <div class="mb-3">
                <label for="cbuDestino" class="form-label">CBU Destino</label>
                <input type="number" class="form-control" name="txtCbu" id="cbuDestino" placeholder="Ingrese CBU">
            </div>
            <div class="mb-3">
                <label for="montoTransferencia" class="form-label">Monto</label>
                <input type="number" name="txtMonto" class="form-control" id="montoTransferencia" placeholder="$XXXX">
            </div>
            <button type="submit" name="btnTransferencia" class="btn btn-success">Realizar Transferencia</button>
        </form>
        <hr>
        <h5>Historial de Transferencias</h5>
        <div class="col-11">	
	      <div class="table-responsive">
	          <table id="example" class="table table-striped">
	            <thead>
	                <tr>
	                    <th >Fecha</th>
	                    <th >Monto</th>
	                    <th >Cuenta destino</th>                                    
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
    
    <div id="prestamos" class="panel">
        <h4>Solicitar Préstamo</h4>
       <form action="ServletPrestamo" method="post">
    <div class="mb-3">
        <label for="cuentaPrestamo" class="form-label">Cuenta Destino</label>
        <select class="form-select" id="cuentaPrestamo" name="cuentaPrestamo">
            <% if (cuentas != null) {
                for (Cuenta cuenta : cuentas) { %>
                <option value="<%= cuenta.getIdCuenta() %>"><%= cuenta.getNumeroCuenta() %> - <%= cuenta.getSaldo() %></option>
            <% }
            } %>
        </select>
    </div>
    <div class="mb-3">
        <label for="montoPrestamo" class="form-label">Monto solicitado</label>
        <input type="number" class="form-control" id="montoPrestamo" name="montoPrestamo">
    </div>
    <div class="mb-3">
        <label for="cuotasPrestamo" class="form-label">Cantidad de cuotas</label>
        <select class="form-select" id="cuotasPrestamo" name="cuotasPrestamo">
            <option>3</option>
            <option>6</option>
            <option>12</option>
        </select>
    </div>
    <button type="submit" class="btn btn-success" name="btnSolicitarPrestamo">Pedir Préstamo</button>
</form>
    </div>

    <div id="pagoPrestamos" class="panel">
        <h4>Pago de prestamos</h4>
        <form>
            <div class="mb-3">
                <label for="cuotaSeleccion" class="form-label">Seleccione Cuota</label>
                <select class="form-select" id="cuotaSeleccion">
                    <option value="1">Cuota 1 - $2.500 - Vence: 10/06/2025</option>
                    <option value="2">Cuota 2 - $2.500 - Vence: 10/07/2025</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="cuentaPago" class="form-label">Cuenta de Pago</label>
                <select class="form-select" id="cuentaPago">
                    <% 
                    if (cuentas != null) {
                        for (Cuenta cuenta : cuentas) {
                    %>
                        <option value="<%= cuenta.getIdCuenta() %>"><%= cuenta.getNumeroCuenta() %> - <%= cuenta.getSaldo() %></option>
                    <%
                        }
                    }
                    %>
                </select>
            </div>
            <button type="submit" class="btn btn-success">Pagar Cuota</button>
        </form>
    </div>

    <div id="datos" style="background-color: rgb(104, 109, 250)" class="panel">
	    <div id="datosPersonales" class="mt-3 container bg-white p-4 rounded">
	        <h4 class="mb-3">Datos Personales</h4>
	        <% if (cliente != null) { %>
	            <ul>
	                <li><strong>Nombre:</strong> <%= cliente.getNombre() %> <%= cliente.getApellido() %></li>
	                <li><strong>DNI:</strong> <%= cliente.getDni() %></li>
	                <li><strong>CUIL:</strong> <%= cliente.getCuil() %></li>
	                <li><strong>Correo Electrónico:</strong> <%= cliente.getCorreoElectronico() %></li>
	                <li><strong>Dirección:</strong> <%= cliente.getDireccion() %></li>
	                <li><strong>Localidad:</strong> <%= cliente.getLocalidad() %></li>
	                <li><strong>Provincia:</strong> <%= cliente.getProvincia() %></li>
	                <li><strong>Nacionalidad:</strong> <%= cliente.getNacionalidad() %></li>
	                <li><strong>Fecha de Nacimiento:</strong> <%= cliente.getFechaNacimiento() %></li>
	                <li><strong>Sexo:</strong> <%= cliente.getSexo() %></li>
	            </ul>
	        <% } else { %>
	            <p class="text-muted">No se pudieron cargar los datos personales.</p>
	        <% } %>
	    </div> 
	    
	    <div id="datosCuenta" class="mt-3 container bg-white p-4 rounded"> 
	        <h4 class="mb-3">Mis cuentas</h4>
	        <% if (cuentas != null && !cuentas.isEmpty()) { %>
	            <table id="example" class="table table-striped">
	                <thead>
	                    <tr>
	                        <th>Número de cuenta</th>
	                        <th>Tipo de cuenta</th>
	                        <th>Saldo</th>
	                        <th>CBU</th>
	                        <th>Fecha de creación</th>                                    
	                    </tr>
	                </thead>
	                <tbody>
	                    <% 
	                    List<TipoCuenta> tiposCuenta = (List<TipoCuenta>) request.getAttribute("tiposCuenta");
	                    for (Cuenta cuenta : cuentas) { 
	                    %>
	                        <tr>
	                            <td><%= cuenta.getNumeroCuenta() %></td>
	                            <td>
	                                <% 
	                                if (tiposCuenta != null) {
	                                    for (TipoCuenta tipo : tiposCuenta) {
	                                        if (tipo.getId() == cuenta.getIdTipoCuenta()) {
	                                            out.print(tipo.getNombre());
	                                            break;
	                                        }
	                                    }
	                                }
	                                %>
	                            </td>
	                            <td>$<%= cuenta.getSaldo() %></td>
	                            <td><%= cuenta.getCbu() %></td>
	                            <td><%= cuenta.getFechaCreacion() %></td>
	                        </tr>
	                    <% } %>
	                </tbody>
	            </table>
	        <% } else { %>
	            <p class="text-muted">No tienes cuentas registradas.</p>
	        <% } %>
	    </div> 
	    
    </div>
    
</div>

<script>
    let panelActual = null;

    function togglePanel(panelId, button) {
        const panel = document.getElementById(panelId);

        if (panelActual && panelActual !== panel) {
            panelActual.style.display = "none";
            const botones = document.querySelectorAll(".btn.btn-primary");
            botones.forEach(b => b.classList.remove("active-button"));
        }

        if (panel.style.display === "block") {
            panel.style.display = "none";
            button.classList.remove("active-button");
            panelActual = null;
        } else {
            panel.style.display = "block";
            button.classList.add("active-button");
            panelActual = panel;
        }
    }
    
    function mostrarSaldo() {
        const select = document.getElementById("cuentaOrigen");
        const visor = document.getElementById("saldoVisor");
        const saldoTexto = document.getElementById("saldoValor");

        const seleccion = select.value;
        
        if (seleccion && seleccion !== "Seleccione una cuenta") {
            // Buscar la opción seleccionada para obtener el texto que contiene el saldo
            const option = select.options[select.selectedIndex];
            const texto = option.text;
            const saldo = texto.split(" - ")[1]; // Obtener la parte del saldo
            
            if (saldo) {
                saldoTexto.textContent = saldo;
                visor.classList.remove("d-none");
            } else {
                visor.classList.add("d-none");
            }
        } else {
            visor.classList.add("d-none");
        }
    }    
    
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
