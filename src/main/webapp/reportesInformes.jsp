<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="entidades.Usuario" %>
<%
    // Verificar autenticación de admin
    Usuario adminLogueado = (Usuario) session.getAttribute("adminLogueado");
    if (adminLogueado == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Administrador - Reportes</title>
<link rel="icon" type="image/x-icon" href="img/banco.png">


<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/2.3.2/css/dataTables.bootstrap5.css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/2.3.2/js/dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/2.3.2/js/dataTables.bootstrap5.min.js"></script>

<style>
    #tablaReporte th {
        text-align: center !important;
        vertical-align: middle !important;
    }
</style>
</head>

<body
	style="background-color: rgb(104, 109, 250); min-height: 100vh; display: flex; flex-direction: column;">


	<nav class="navbar navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="#"> <i class="bi bi-bank2 me-2"></i>
				Administrador
			</a>
			<form action="ServletLogin" method="get" class="d-inline">
				<button class="btn btn-outline-dark" type="submit" name="btnCerrar">Cerrar
					Sesión</button>
			</form>
		</div>
	</nav>

	<div class="container text-center flex-grow-1">
		<div class="row">

			<div
				class="col-2 d-flex flex-column align-items-center justify-content-start pt-4">
				<div class="d-grid gap-2 w-100">

					<a href="ServletCliente?listar=1" class="btn btn-light"> <i
						class="bi bi-people me-2"></i> Administrar Clientes
					</a> <a href="ServletUsuario?listar=1" class="btn btn-light"> <i
						class="bi bi-person-gear me-2"></i> Administrar Usuarios
					</a> <a href="ServletCuenta?listar=1" class="btn btn-light"> <i
						class="bi bi-credit-card me-2"></i> Administrar Cuentas
					</a> <a href="ServletPrestamo?listar=1" class="btn btn-light"> <i
						class="bi bi-cash-coin me-2"></i> Autorizar Préstamos
					</a> <a href="reportesInformes.jsp" class="btn btn-primary fw-bold">
						<i class="bi bi-graph-up me-2"></i> Informe/Reportes
					</a>

				</div>
			</div>


			<div class="col-10">

				<div
					class="d-flex justify-content-between align-items-center mb-4 pt-4">
					<h3 class="text-white">
						<i class="bi bi-graph-up me-2"></i> Informes y Reportes
					</h3>
				</div>


				<div class="card shadow">
					<div class="card-header bg-primary text-white">
						<h5 class="mb-0">
							<i class="bi bi-file-earmark-bar-graph me-2"></i> Generar Reporte
						</h5>
					</div>
					<div class="card-body bg-white">

						<form id="formReporte" onsubmit="generarReporte(event)"
							method="post" action="ServletReporte">
							<div class="row mb-4">
								<div class="col-md-4">
									<label for="fechaDesde" class="form-label">Fecha Desde</label>
									<input type="date" class="form-control" id="fechaDesde"
										name="fechaDesde" required>
								</div>
								<div class="col-md-4">
									<label for="fechaHasta" class="form-label">Fecha Hasta</label>
									<input type="date" class="form-control" id="fechaHasta"
										name="fechaHasta" required>
								</div>
								<div class="col-md-4">
									<label for="tipoReporte" class="form-label">Tipo de
										Reporte</label> <select class="form-select" id="tipoReporte"
										name="txtReporte" required>
										<option value="" disabled selected>Seleccionar tipo
											de reporte</option>
										<option value="depositos">Suma de Depósitos</option>
										<option value="transferencias">Transferencias
											Realizadas</option>
										<option value="prestamos">Préstamos Otorgados</option>
										<option value="clientes">Clientes Activos</option>
									</select>
								</div>
							</div>

							<div class="text-center">
								<button type="submit" class="btn btn-success btn-lg"
									name="btnReporte">
									<i class="bi bi-search me-2"></i> Generar Reporte
								</button>
								<button type="button" name="btnLimpiar"
									class="btn btn-outline-secondary btn-lg ms-2"
									onclick="limpiarFormulario()">
									<i class="bi bi-arrow-clockwise me-2"></i> Limpiar
								</button>
							</div>

						</form>

						<!--  Sección para mostrar el resumen del reporte   -->
						<%
						    Object resultadoObj = request.getAttribute("resultado");
						    String tipoReporte = (String) request.getAttribute("tipoReporte");
						    String fechaInicio = (String) request.getAttribute("fechaInicio");
						    String fechaFin = (String) request.getAttribute("fechaFin");
						    
						    if (resultadoObj != null) {
						        String tipo = resultadoObj instanceof Double ? "Monto total" : "Cantidad total";
						        if ((resultadoObj instanceof Number) && ((Number)resultadoObj).doubleValue() == 0) {
						%>
							<div class="alert alert-warning mt-4">No hay resultados para
								mostrar en el rango seleccionado.</div>
						<%
						   } else {
						%>
							<hr>
							<div class="alert alert-success mt-4">
								<h5>
								<i class="bi bi-bar-chart-fill me-2"></i>
											<%= tipo %>:
								</h5>
								<h2 class="fw-bold">
									<% if (resultadoObj instanceof Double) { %>
										$<%= String.format("%,.2f", resultadoObj) %>
									<% } else { %>
										<%= resultadoObj %>
									<% } %>
								</h2>
								<% if (tipoReporte != null && fechaInicio != null && fechaFin != null) { %>
									<p class="mb-0">
										<strong>Período:</strong> 
										<%
											// Convertir formato de fecha de YYYY-MM-DD a DD/MM/YYYY
											if (fechaInicio != null && fechaFin != null) {
												String[] partesInicio = fechaInicio.split("-");
												String[] partesFin = fechaFin.split("-");
												if (partesInicio.length == 3 && partesFin.length == 3) {
													String fechaInicioFormateada = partesInicio[2] + "/" + partesInicio[1] + "/" + partesInicio[0];
													String fechaFinFormateada = partesFin[2] + "/" + partesFin[1] + "/" + partesFin[0];
													out.print(fechaInicioFormateada + " a " + fechaFinFormateada);
												} else {
													out.print(fechaInicio + " a " + fechaFin);
												}
											}
										%> | 
										<strong>Tipo:</strong> 
										<% 
											switch(tipoReporte) {
												case "depositos": out.print("Suma de Depósitos"); break;
												case "transferencias": out.print("Transferencias Realizadas"); break;
												case "prestamos": out.print("Préstamos Otorgados"); break;
												case "clientes": out.print("Clientes Activos"); break;
											}
										%>
									</p>
								<% } %>
							</div>
						<%
						        }
						    }
						%>
					</div>
				</div>

				<!-- Tabla de datos detallados -->
				<%
					List<Map<String, Object>> datosDetallados = (List<Map<String, Object>>) request.getAttribute("datosDetallados");
					if (datosDetallados != null && !datosDetallados.isEmpty()) {
				%>
				<div class="card shadow mt-4">
					<div class="card-header bg-info text-white">
						<h5 class="mb-0">
							<i class="bi bi-table me-2"></i> Detalle del Reporte
						</h5>
					</div>
					<div class="card-body bg-white">
						<div class="table-responsive">
							<table id="tablaReporte" class="table table-striped table-hover">
								<thead class="table-light text-center">
									<tr>
										<%
											if (!datosDetallados.isEmpty()) {
												Map<String, Object> primeraFila = datosDetallados.get(0);
												// Generar encabezados según el tipo de reporte
												if (tipoReporte != null && tipoReporte.equals("depositos")) {
													String[] ordenColumnas = {"Cliente", "Número de Cuenta", "Monto", "Detalle", "Fecha"};
													for (String col : ordenColumnas) {%>
														<th class="text-center"><%= col %></th>
												<%	}
												} else if (tipoReporte != null && tipoReporte.equals("transferencias")) {
													String[] ordenColumnas = {"Cliente", "Monto", "Número de Cuenta Destino", "Detalle", "Fecha"};
													for (String col : ordenColumnas) {%>
														<th class="text-center"><%= col %></th>
												<%	}
												} else if (tipoReporte != null && tipoReporte.equals("prestamos")) {
													String[] ordenColumnas = {"Cliente", "Número de Cuenta", "Monto Solicitado", "Cantidad Cuotas", "Cuota Mensual", "Fecha Aprobación"};
													for (String col : ordenColumnas) {%>
														<th class="text-center"><%= col %></th>
												<%	}
												} else if (tipoReporte != null && tipoReporte.equals("clientes")) {
													String[] ordenColumnas = {"Cliente", "Número de Cuenta", "Tipo de Cuenta", "DNI", "Saldo Actual", "Fecha Creación"};
													for (String col : ordenColumnas) {%>
														<th class="text-center"><%= col %></th>
												<%	}
												} else {
													// Para reportes no específicos, usar las claves del Map
													for (String columna : primeraFila.keySet()) {%>
														<th class="text-center"><%= columna %></th>
													<%}
												}
											}
										%>
									</tr>
								</thead>
								<tbody>
									<%
										// Iterar sobre cada fila de datos
										for (Map<String, Object> fila : datosDetallados) {
									%>
										<tr>
									<%
											// Procesar datos según el tipo de reporte
											if (tipoReporte != null && tipoReporte.equals("depositos")) {
												String[] ordenColumnas = {"Cliente", "Número de Cuenta", "Monto", "Detalle", "Fecha"};
												for (String col : ordenColumnas) {
													Object valor = fila.get(col);
													String valorFormateado = "";
													if (valor != null) {
														if (col.equals("Fecha")) {
															// Formatear fecha a dd/MM/yyyy
															SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
															valorFormateado = sdf.format(valor);
														} else if (col.equals("Monto")) {
															// Formatear moneda con $ y separadores de miles
															valorFormateado = "$" + String.format("%,.2f", valor);
														} else {
															valorFormateado = valor.toString();
														}
													}
									%>
											<td class="text-center"><%= valorFormateado %></td>
									<%
												}
											} else if (tipoReporte != null && tipoReporte.equals("transferencias")) {
												String[] ordenColumnas = {"Cliente", "Monto", "Número de Cuenta Destino", "Detalle", "Fecha"};
												for (String col : ordenColumnas) {
													Object valor = fila.get(col);
													String valorFormateado = "";
													if (valor != null) {
														if (col.equals("Fecha")) {
															SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
															valorFormateado = sdf.format(valor);
														} else if (col.equals("Monto")) {
															valorFormateado = "$" + String.format("%,.2f", valor);
														} else {
															valorFormateado = valor.toString();
														}
													}
									%>
											<td class="text-center"><%= valorFormateado %></td>
									<%
												}
											} else if (tipoReporte != null && tipoReporte.equals("prestamos")) {
												String[] ordenColumnas = {"Cliente", "Número de Cuenta", "Monto Solicitado", "Cantidad Cuotas", "Cuota Mensual", "Fecha Aprobación"};
												for (String col : ordenColumnas) {
													Object valor = fila.get(col);
													String valorFormateado = "";
													if (valor != null) {
														if (col.equals("Fecha Aprobación")) {
															SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
															valorFormateado = sdf.format(valor);
														} else if (col.equals("Monto Solicitado") || col.equals("Cuota Mensual")) {
															valorFormateado = "$" + String.format("%,.2f", valor);
														} else {
															valorFormateado = valor.toString();
														}
													}
									%>
											<td class="text-center"><%= valorFormateado %></td>
									<%
												}
											} else if (tipoReporte != null && tipoReporte.equals("clientes")) {
												String[] ordenColumnas = {"Cliente", "Número de Cuenta", "Tipo de Cuenta", "DNI", "Saldo Actual", "Fecha Creación"};
												for (String col : ordenColumnas) {
													Object valor = fila.get(col);
													String valorFormateado = "";
													if (valor != null) {
														if (col.equals("Fecha Creación")) {
															SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
															valorFormateado = sdf.format(valor);
														} else if (col.equals("Saldo Actual")) {
															valorFormateado = "$" + String.format("%,.2f", valor);
														} else {
															valorFormateado = valor.toString();
														}
													}
									%>
											<td class="text-center"><%= valorFormateado %></td>
									<%
												}
											} else {
												// Para los reportes no específicos, procesar todos los valores
												int index = 0;
												for (Object valor : fila.values()) {
													String valorFormateado = "";
													if (valor != null) {
														if (valor instanceof java.sql.Date || valor instanceof java.util.Date) {
															SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
															valorFormateado = sdf.format(valor);
														} else {
															valorFormateado = valor.toString();
														}
													}
									%>
											<td class="text-center"><%= valorFormateado %></td>
									<%
													index++;
												}
											}
									%>
										</tr>
									<%
										}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				<%
					}
				%>

			</div>
		</div>
	</div>

	<footer class="bg-light text-center text-muted py-3 mt-5">
		<div class="container">
			<span>© 2025 Banco UTN – Todos los derechos reservados</span>
		</div>
	</footer>

	<script>
	   // Función para validar fechas antes de enviar el formulario
	   function generarReporte(event) {
	        const fechaDesde = document.getElementById('fechaDesde').value;
	        const fechaHasta = document.getElementById('fechaHasta').value;

	        if (new Date(fechaDesde) > new Date(fechaHasta)) {
	            alert('La fecha "Desde" debe ser anterior a la fecha "Hasta"');
	            event.preventDefault();
	        }
	        
	    }

	    // Función para limpiar el formulario y recargar la página
	    function limpiarFormulario() {
	    	  document.getElementById('formReporte').reset();		   
			  window.location.href = 'reportesInformes.jsp'
	    }
	    
	    // Inicializar DataTable si existe la tabla con configuración en español
	    document.addEventListener("DOMContentLoaded", function () {
	        if (document.getElementById('tablaReporte')) {
	            new DataTable('#tablaReporte', {
	                ordering: false,
	                language: {
	                    search: "Buscar:",
	                    lengthMenu: "Mostrar _MENU_ registros por página",
	                    zeroRecords: "No se encontraron resultados",
	                    info: "Mostrando _START_ a _END_ de _TOTAL_ registros",
	                    infoEmpty: "Mostrando 0 a 0 de 0 registros",
	                    infoFiltered: "(filtrado de _MAX_ registros totales)",
	                    paginate: {
	                        first: "Primero",
	                        last: "Último",
	                        next: "Siguiente",
	                        previous: "Anterior"
	                    }
	                }
	            });
	        }
	    });
       
    </script>

</body>
</html>