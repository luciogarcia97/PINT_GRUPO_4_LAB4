<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
					</a> <a href="administrarCuentas.jsp" class="btn btn-light"> <i
						class="bi bi-credit-card me-2"></i> Administrar Cuentas
					</a> <a href="prestamos.jsp" class="btn btn-light"> <i
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
									<i class="bi bi-download me-2"></i> Generar y Descargar Reporte
								</button>
								<button type="button" name="btnLimpiar"
									class="btn btn-outline-secondary btn-lg ms-2"
									onclick="limpiarFormulario()">
									<i class="bi bi-arrow-clockwise me-2"></i> Limpiar
								</button>
							</div>

						</form>

							<%
							    Object resultadoObj = request.getAttribute("resultado");
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
									<h2 class="fw-bold"><%= resultadoObj %></h2>
								</div>
							<%
							        }
							    }
							%>
					</div>
				</div>

			</div>
		</div>
	</div>

	<footer class="bg-light text-center text-muted py-3 mt-auto">
		<div class="container">
			<span>© 2025 Banco UTN – Todos los derechos reservados</span>
		</div>
	</footer>

	<script>
	   function generarReporte(event) {
	        const fechaDesde = document.getElementById('fechaDesde').value;
	        const fechaHasta = document.getElementById('fechaHasta').value;

	        if (new Date(fechaDesde) > new Date(fechaHasta)) {
	            alert('La fecha "Desde" debe ser anterior a la fecha "Hasta"');
	            event.preventDefault(); // Solo se detiene si hay error
	        }
	        // Si está todo bien, el formulario se envía normalmente al servlet
	    }

	    function limpiarFormulario() {
	        document.getElementById('formReporte').reset();
	    }   
       
    </script>

</body>
</html>