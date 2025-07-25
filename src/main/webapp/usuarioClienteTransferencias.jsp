<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="entidades.*"%>
<%
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
    <link rel="stylesheet" href="style/usuarioClienteStyle.css" />
</head>
<body class="d-flex flex-column min-vh-100">
    <nav class="navbar navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"><i class="bi bi-person-circle"></i>
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
            int saldoInsuficiente = request.getAttribute("saldoInsuficiente") != null ? Integer.parseInt(request.getAttribute("saldoInsuficiente").toString()) : 0;
            int cbuInexistente = request.getAttribute("cbuInexistente") != null ? Integer.parseInt(request.getAttribute("cbuInexistente").toString()) : 0;
            int montoInvalido = request.getAttribute("montoInvalido") != null ? Integer.parseInt(request.getAttribute("montoInvalido").toString()) : 0;
            int mismoCbu = request.getAttribute("mismoCbu") != null ? Integer.parseInt(request.getAttribute("mismoCbu").toString()) : 0;
            String error = (String) request.getAttribute("error");
        %>

        <% if (error != null && !error.isEmpty()) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i> <%= error %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <%
            List<Cuenta> cuentas = (List<Cuenta>) request.getAttribute("cuentasCliente");
        %>

        <div class="container mt-4">
            <div class="d-flex justify-content-center gap-3">
				<a href="ServletClienteMenu" class="btn btn-primary btn-lg rounded-pill">
  					<i class="bi bi-list me-2"></i> Menú
				</a>
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

            <div id="transferencias" class="panel" style="display: block">
                <h4>Transferencias</h4>                        

                <% if(saldoInsuficiente != 0) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Error:</strong> No tenés saldo suficiente para realizar la transferencia.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                <% } %>

                <% if(cbuInexistente != 0) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Error:</strong> El CBU ingresado es inexistente o esta dado de baja.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                <% } %>

                <% if(montoInvalido != 0) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Error:</strong> Debe ingresar un monto.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                <% } %>
                
                  <% if(mismoCbu != 0) { %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Error:</strong> No podes mandar y recibir al mismo CBU.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                <% } %> 
                
                <% String exito = (String) request.getAttribute("exito"); %>
					<% if (exito != null && !exito.isEmpty()) { %>
						 <div class="alert alert-success alert-dismissible fade show" role="alert">
						      <%= exito %>
						      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
						  </div>
				<% } %>               

               
                <form id="formTransferencia" action="ServletClienteTransferencia" method="post">
                <input type="hidden" name="btnTransferencia" value="1">            
                                            
                    
                    <% if (cuentas != null && !cuentas.isEmpty()) { %>
							<div class="mb-3">
								<label for="cuentaOrigen" class="form-label">Cuenta Origen</label>
								<select class="form-select" id="idCuenta" name="idCuenta">								
									<% for (Cuenta cuenta : cuentas) { %>
										<option value="<%= cuenta.getIdCuenta() %>" data-cbu="<%= cuenta.getCbu() %>">
											<%= cuenta.getNumeroCuenta() %> - <%= cuenta.getSaldo() %>
										</option>
									<% } %>
								</select>
								<input type="hidden" name="cbuOrigen" id="cbuOrigen">
							</div>
						<% } else { %>
							<div class="alert alert-warning" role="alert">
								⚠️ Todavía no tiene cuentas asociadas para realizar una transferencia.
							</div>
						<% } %>										
                  
                    <div class="mb-3">
                        <label for="cbuDestino" class="form-label">CBU Destino</label>
                        <input type="text" <% if (cuentas == null || cuentas.isEmpty()) { %> disabled <% } %> class="form-control" name="txtCbu" id="cbuDestino" placeholder="Ingrese CBU"
                        oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/^(\d*\.\d{0,2}).*$/, '$1').replace(/(\..*)\./g, '$1');">
                    </div>

                    <div class="mb-3">
                        <label for="montoTransferencia" class="form-label">Monto</label>
                        <input type="text" <% if (cuentas == null || cuentas.isEmpty()) { %> disabled <% } %> name="txtMonto" class="form-control" id="montoTransferencia" placeholder="$XXXX"
                        oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/^(\d*\.\d{0,2}).*$/, '$1').replace(/(\..*)\./g, '$1');">
                    </div>

                    <div id="infoTitular" class="alert alert-info d-none">
                        Titular: <span id="nombreTitular"></span>
                    </div>

                    <button type="button" <% if (cuentas == null || cuentas.isEmpty()) { %> disabled <% } %>  class="btn btn-success" onclick="validarYMostrarModal()">Realizar Transferencia</button>
                </form>
                
                
                <script>
				    document.addEventListener("DOMContentLoaded", function () {
				        const selectCuenta = document.getElementById("idCuenta");
				        const inputCbu = document.getElementById("cbuOrigen");
				
				        function actualizarCbu() {
				            const selectedOption = selectCuenta.options[selectCuenta.selectedIndex];
				            inputCbu.value = selectedOption.getAttribute("data-cbu");
				        }
				
				        actualizarCbu(); // Lo setea al cargar la página
				        selectCuenta.addEventListener("change", actualizarCbu); // Lo actualiza al cambiar
				    });
				</script>            
                
                
                <hr>
                <h5>Historial de Transferencias</h5>
                <div class="col-11">
                    <div class="table-responsive">
                        <table id="example" class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Fecha</th>
                                    <th>Monto</th>
                                    <th>Cuenta origen</th>
                                    <th>Cuenta destino</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<Transferencia> historial = (List<Transferencia>) request.getAttribute("historial");
                                    if (historial != null && !historial.isEmpty()) {
                                        for (Transferencia transferencia : historial) {
                                %>
                                <tr>
                                    <td><%= transferencia.getFecha() %></td>
                                    <td>$<%= transferencia.getImporte() %></td>
                                    <td><%= transferencia.getCuenta_origen() %></td>
                                    <td><%= transferencia.getCuenta_destino() %></td>
                                </tr>
                                <%   }
                                    } else { %>
                                <tr>
                                    <td colspan="3" class="text-center">No hay transferencias registradas</td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </main>

   
    <div class="modal fade" id="modalConfirmacion" tabindex="-1" aria-labelledby="modalConfirmacionLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Confirmar Transferencia</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
          </div>
          <div class="modal-body">
            <p><strong>Destinatario:</strong> <span id="modalNombreApellido"></span></p>
            <p><strong>DNI:</strong> <span id="modalDNI"></span></p>
            <p><strong>CBU:</strong> <span id="modalCBU"></span></p>
            <p><strong>Monto:</strong> $<span id="modalMonto"></span></p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
            <button type="button" class="btn btn-success" id="btnConfirmarTransferencia">Confirmar</button>
          </div>
        </div>
      </div>
    </div>

    <footer class="bg-light text-center text-muted py-3 mt-5">
        <div class="container">
            <span>© 2025 Banco UTN – Todos los derechos reservados</span>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function validarYMostrarModal() {
            const cbu = document.getElementById("cbuDestino").value;
            const monto = document.getElementById("montoTransferencia").value;

            if (!cbu || !monto || parseFloat(monto) <= 0) {
                alert("Ingrese un CBU y un monto válido.");
                return;
            }

            fetch("ServletBuscarTitularCBU", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: "cbu=" + encodeURIComponent(cbu)
            })
            .then(response => response.json())
            .then(data => {
                const infoDiv = document.getElementById("infoTitular");
                const nombreSpan = document.getElementById("nombreTitular");

                if (data.error) {
                    infoDiv.classList.add("d-none");
                    alert("CBU no encontrado.");
                } else {
                    nombreSpan.textContent = data.nombre + " " + data.apellido;
                    infoDiv.classList.remove("d-none");

                    // Llenar modal
                    document.getElementById("modalNombreApellido").textContent = data.nombre + " " + data.apellido;
                    document.getElementById("modalDNI").textContent = data.dni || "No disponible";
                    document.getElementById("modalCBU").textContent = cbu;
                    document.getElementById("modalMonto").textContent = monto;

                    const modal = new bootstrap.Modal(document.getElementById('modalConfirmacion'));
                    modal.show();
                }
            })
            .catch(error => {
                console.error("Error al buscar el titular:", error);
                alert("Error al buscar el titular del CBU.");
            });
        }

        document.getElementById("btnConfirmarTransferencia").addEventListener("click", function (e) {
            e.preventDefault(); 
            document.getElementById("formTransferencia").submit();
        });
    </script>
</body>
</html>
