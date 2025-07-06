<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidades.Cliente"%>
<%@ page import="entidades.Usuario" %>
<%@ page import="entidades.Prestamo" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Administrador - Préstamos</title>
    <link rel="icon" type="image/x-icon" href="img/banco.png">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/2.3.2/css/dataTables.bootstrap5.css"> 
    
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.datatables.net/2.3.2/js/dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/2.3.2/js/dataTables.bootstrap5.min.js"></script>
</head>

<body style="background-color: rgb(104, 109, 250); min-height: 100vh; display: flex; flex-direction: column;">    

    <nav class="navbar navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <i class="bi bi-bank2 me-2"></i>        
                Administrador
            </a>
            <form action="ServletLogin" method="get" class="d-inline">
            	<button class="btn btn-outline-dark" type="submit" name="btnCerrar">Cerrar Sesión</button>
        	</form>       
        </div>
    </nav>

    <div class="container text-center flex-grow-1"> 
        <div class="row">		                    
            
            <div class="col-2 d-flex flex-column align-items-center justify-content-start pt-4">
                <div class="d-grid gap-2 w-100">
                    
                    <a href="ServletCliente?listar=1" class="btn btn-light">
                        <i class="bi bi-people me-2"></i>
                        Administrar Clientes
                    </a>
                    
                    <a href="ServletUsuario?listar=1" class="btn btn-light">
                        <i class="bi bi-person-gear me-2"></i>
                        Administrar Usuarios
                    </a>
                    
                    <a href="ServletCuenta?listar=1" class="btn btn-light">
                        <i class="bi bi-credit-card me-2"></i>
                        Administrar Cuentas
                    </a>
                    
                    <a href="ServletPrestamo?listar=1" class="btn btn-primary fw-bold">
                        <i class="bi bi-cash-coin me-2"></i>
                        Autorizar Préstamos
                    </a>
                    
                    <a href="reportesInformes.jsp" class="btn btn-light">
                        <i class="bi bi-graph-up me-2"></i>
                        Informe/Reportes
                    </a>
                    
                </div>
            </div>
            
            <div class="col-10">
                
                <div class="d-flex justify-content-between align-items-center mb-4 pt-4">
                    <h3 class="text-white">
                        <i class="bi bi-cash-coin me-2"></i>
                        Autorizar Préstamos
                    </h3>
                </div>
                
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">
                            <i class="bi bi-table me-2"></i>
                            Solicitudes de Préstamos
                        </h5>
                    </div>
                    <div class="card-body bg-white">
                        <div class="table-responsive">
                            <table id="example" class="table table-striped table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>IdPrestamo</th>
                                        <th>IdCliente</th>
                                        <th>IdCuenta</th>
                                        <th>Fecha de Solicitud</th>
                                        <th>Plazo</th>
                                        <th>Importe Solicitado</th>
                                        <th>Importe Cuotas</th>
                                        <th>N° Cuotas</th> 
                                        <th>Estado</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                               <tbody>
									<% List<entidades.Prestamo> listaPrestamos = (List<entidades.Prestamo>) request.getAttribute("listaPrestamos");
                                                        if (listaPrestamos != null) {
                                                        for (entidades.Prestamo p : listaPrestamos) {
                                                        %>
									<tr>
										<td><%= p.getId_prestamo() %></td>
										<td><%= p.getId_cliente() %></td>
										<td><%= p.getId_cuenta() %></td>
										<td><%= p.getFecha_solicitud() %></td>
										<td><%= p.getPlazo_pago_mes() %></td>
										<td><%= p.getImporte_solicitado() %></td>
										<td><%= p.getImporte_por_cuota() %></td>
										<td><%= p.getCantidad_cuotas() %></td>
										<td><%= p.getEstado() %></td>
										
									
										<td>
										<% if(!p.getEstado().equals("denegado") && !p.getEstado().equals("aceptado")){%>
										 <div class="d-flex gap-1">
										 <form   action="ServletPrestamo?denegar=1" method="post"
											class="d-inline">
										 <input type="hidden" name="idPrestamo"
											value="<%= p.getId_prestamo() %>" />														
										 <button type="submit" class="btn btn-sm btn-outline-danger"
											title="Denegar Prestamo" name="btnDenegarPrestamo">
											<i class="bi bi-trash"></i>
										 </button>
										 
										 </form>
										 
										  <form   action="ServletPrestamo?aceptar=1" method="post"
											class="d-inline">
										 <input type="hidden" name="idPrestamo"
											value="<%= p.getId_prestamo() %>" />														
										 <button type="submit" class="btn btn-sm btn-outline-success"
											title="Aceptar Prestamo" name="btnAceptarPrestamo">
											<i class="bi bi-arrow-through-heart-fill"></i>
										 </button>
										 
										 </form  >
										 
										 </div>
										 <%
										 } 
										 %>
									    </td>
									</tr>
									<% } } %>
								</tbody>
                            </table>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
    </div>

    <footer class="bg-light text-center text-muted py-3 mt-5">
        <div class="container">
            <span>© 2025 Banco UTN – Todos los derechos reservados</span>
        </div>
    </footer>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            new DataTable('#example');
        });
    </script>

</body>
</html>