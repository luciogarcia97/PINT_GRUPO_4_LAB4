<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="entidades.Movimiento" %>
<%@ page import="entidades.Cuenta" %>
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
		<button class="btn btn-primary" onclick="togglePanel('movimientos', this); cargarCuentasAlAbrir();">Ver Movimientos</button>
        <button class="btn btn-primary" onclick="togglePanel('transferencias', this)">Transferencias</button>
        <button class="btn btn-primary" onclick="togglePanel('prestamos', this)">Préstamos</button>
        <button class="btn btn-primary" onclick="togglePanel('pagoPrestamos', this)">Pago de prestamos</button>
        <button class="btn btn-primary" onclick="togglePanel('datos', this)">Mis Datos</button>
    </div>
    
<div id="movimientos" class="panel">
    <h4><i class="bi bi-wallet2 me-2"></i>Consultar Movimientos</h4>
    
    <!-- Mensajes -->
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger">
            <i class="bi bi-exclamation-triangle me-2"></i><%= request.getAttribute("error") %>
        </div>
    <% } %>
    
    <!-- Formulario para seleccionar cuenta -->
    <form action="ServletMovimiento" method="get">
        <div class="row mb-4">
            <div class="col-md-8">
                <label for="selectCuenta" class="form-label">Seleccionar Cuenta</label>
                <select class="form-select" id="selectCuenta" name="idCuenta" onchange="mostrarInfoCuenta()" required>
                    <option value="" disabled selected>Seleccione una cuenta para ver movimientos</option>
                    <%
                        // Cargar cuentas al abrir el panel
                        if (request.getAttribute("cuentasCliente") == null) {
                            // Primera vez - cargar cuentas via JavaScript
                    %>
                        <option disabled>Cargando cuentas...</option>
                    <%
                        } else {
                            java.util.List<entidades.Cuenta> cuentasCliente = 
                                (java.util.List<entidades.Cuenta>) request.getAttribute("cuentasCliente");
                            
                            if (cuentasCliente != null && !cuentasCliente.isEmpty()) {
                                for (entidades.Cuenta cuenta : cuentasCliente) {
                    %>
                                    <option value="<%= cuenta.getIdCuenta() %>" 
                                            data-numero="<%= cuenta.getNumeroCuenta() %>"
                                            data-tipo="<%= cuenta.getIdTipoCuenta() == 1 ? "Cuenta Corriente" : "Caja de Ahorro" %>"
                                            data-saldo="<%= String.format("%,.2f", cuenta.getSaldo()) %>"
                                            data-cbu="<%= cuenta.getCbu() %>">
                                        <%= cuenta.getNumeroCuenta() %> - 
                                        <%= cuenta.getIdTipoCuenta() == 1 ? "Cuenta Corriente" : "Caja de Ahorro" %>
                                    </option>
                    <%
                                }
                            } else {
                    %>
                                <option disabled>No tienes cuentas activas</option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div class="col-md-4 d-flex align-items-end">
                <button type="submit" name="consultar" class="btn btn-primary">
                    <i class="bi bi-search me-2"></i>Ver Movimientos
                </button>
            </div>
        </div>
    </form>
    
    <!-- Información de la cuenta seleccionada -->
    <div id="infoCuenta" class="card mb-4" style="display: none;">
        <div class="card-header bg-primary text-white">
            <h6 class="mb-0" id="tituloCuenta"></h6>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-3">
                    <strong>Tipo:</strong> <span id="tipoCuenta"></span>
                </div>
                <div class="col-md-3">
                    <strong>Saldo:</strong> <span id="saldoCuenta" class="text-success"></span>
                </div>
                <div class="col-md-6">
                    <strong>CBU:</strong> <span id="cbuCuenta" class="text-muted"></span>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Tabla de movimientos -->
    <% if (request.getAttribute("movimientos") != null) { %>
        <%
            entidades.Cuenta cuentaSeleccionada = (entidades.Cuenta) request.getAttribute("cuentaSeleccionada");
            java.util.List<entidades.Movimiento> movimientos = 
                (java.util.List<entidades.Movimiento>) request.getAttribute("movimientos");
        %>
        
        <hr>
        <h5>
            <i class="bi bi-clock-history me-2"></i>
            Movimientos - Cuenta <%= cuentaSeleccionada.getNumeroCuenta() %>
        </h5>
        
        <% if (movimientos.isEmpty()) { %>
            <div class="alert alert-info">
                <i class="bi bi-info-circle me-2"></i>
                Esta cuenta no tiene movimientos registrados.
            </div>
        <% } else { %>
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Fecha</th>
                            <th>Tipo</th>
                            <th>Detalle</th>
                            <th class="text-end">Importe</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (entidades.Movimiento mov : movimientos) { %>
                            <tr>
                                <td><%= mov.getFecha() %></td>
                                <td>
                                    <% if (mov.getIdTipoMovimiento() == 1) { %>
                                        <span class="badge bg-success">Alta de cuenta</span>
                                    <% } else if (mov.getIdTipoMovimiento() == 4) { %>
                                        <span class="badge bg-primary">Transferencia</span>
                                    <% } else if (mov.getIdTipoMovimiento() == 5) { %>
                                        <span class="badge bg-info">Depósito</span>
                                    <% } else { %>
                                        <span class="badge bg-secondary">Otro</span>
                                    <% } %>
                                </td>
                                <td><%= mov.getDetalle() %></td>
                                <td class="text-end">
                                    <strong>$<%= String.format("%,.2f", mov.getImporte()) %></strong>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
                
                <div class="mt-3">
                    <small class="text-muted">
                        <i class="bi bi-info-circle me-1"></i>
                        Se encontraron <%= movimientos.size() %> movimientos
                    </small>
                </div>
            </div>
        <% } %>
    <% } %>
</div>
    
    <div id="transferencias" class="panel">
        <h4>Transferencias</h4>
        <form>
            <div class="mb-3">
                <label for="cuentaOrigen" class="form-label">Cuenta Origen</label>
                
                <select class="form-select" id="cuentaOrigen">
                    <option selected disabled>Seleccione una cuenta</option>
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
            <div class="mb-3">
                <label for="cbuDestino" class="form-label">CBU Destino</label>
                <input type="text" class="form-control" id="cbuDestino" placeholder="Ingrese CBU">
            </div>
            <div class="mb-3">
                <label for="montoTransferencia" class="form-label">Monto</label>
                <input type="number" class="form-control" id="montoTransferencia" placeholder="$XXXX">
            </div>
            <button type="submit" class="btn btn-success">Realizar Transferencia</button>
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
	function mostrarInfoCuenta() {
	    const select = document.getElementById('selectCuenta');
	    const selectedOption = select.options[select.selectedIndex];
	    const infoCuenta = document.getElementById('infoCuenta');
	    
	    if (selectedOption.value) {
	        document.getElementById('tituloCuenta').textContent = 'Cuenta N° ' + selectedOption.dataset.numero;
	        document.getElementById('tipoCuenta').textContent = selectedOption.dataset.tipo;
	        document.getElementById('saldoCuenta').textContent = '$' + selectedOption.dataset.saldo;
	        document.getElementById('cbuCuenta').textContent = selectedOption.dataset.cbu;
	        
	        infoCuenta.style.display = 'block';
	    } else {
	        infoCuenta.style.display = 'none';
	    }
	}
	
	function cargarCuentasAlAbrir() {
	    const select = document.getElementById('selectCuenta');
	    if (select && select.options.length <= 2) {
	        window.location.href = 'ServletMovimiento';
	    }
	}
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
