<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidades.Usuario"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Administrador - Usuarios</title>
    <link rel="icon" type="image/x-icon" href="img/banco.png">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/2.3.2/css/dataTables.bootstrap5.css"> 
    
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.datatables.net/2.3.2/js/dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/2.3.2/js/dataTables.bootstrap5.min.js"></script>

    <!-- -->
</head>

<body style="background-color: rgb(104, 109, 250); min-height: 100vh; display: flex; flex-direction: column;">    

    <nav class="navbar navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <i class="bi bi-bank2 me-2"></i>        
                Administrador - Usuarios
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
                    
                    <a href="ServletUsuario?listar=1" class="btn btn-primary fw-bold">
                        <i class="bi bi-person-gear me-2"></i>
                        Administrar Usuarios
                    </a>
                    
                    <a href="ServletCuenta?listar=1" class="btn btn-light">
                        <i class="bi bi-credit-card me-2"></i>
                        Administrar Cuentas
                    </a>
                    
                    <a href="ServletPrestamo?listar=1" class="btn btn-light">
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
                        <i class="bi bi-person-gear me-2"></i>
                        Administrar Usuarios
                    </h3>                   
                </div>
                
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">
                            <i class="bi bi-table me-2"></i>
                            Lista de Usuarios
                        </h5>
                    </div>        
                  
                    
                    
                    <div class="card-body bg-white">
                        <div class="table-responsive">
                            <table id="example" class="table table-striped table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>IdUsuario</th>
                                        <th>IdCliente</th>
                                        <th>Usuario</th>
                                        <th>Tipo Usuario</th>
                                        <th>Eliminado</th>
                                        <th>Fecha de Creación</th> 
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                
                                	  <%
					                    	List<entidades.Usuario> lista = (List<entidades.Usuario>) request.getAttribute("listaUsuarios");
					                    
					                    	if (lista != null) {
					                        	for (Usuario usuario : lista) {                  
					                    
					                   %>                               
                                
                                    <tr>
                                        <td><%= usuario.getId_usuario() %></td>
                                        <td><%= usuario.getId_cliente() %></td>
                                        <td><%= usuario.getUsuario() %></td>
                                        <td><%= usuario.getTipo_usuario() %></td>
                                        <td><%= usuario.getEliminado() %></td>
                                        <td> <%= usuario.getFecha_creacion() %></td>                   
                                        <td>
                                            <div class="d-flex gap-1">                                            
                                               <a href="modificarUsuario.jsp?idUsuario=<%= usuario.getId_usuario()%>&idCliente=<%= usuario.getId_cliente()%>&fechaCreacion=<%= usuario.getFecha_creacion()%>&usuario=<%= usuario.getUsuario()%>" 
                                               class="btn btn-sm btn-outline-primary" title="Modificar Usuario">
                                               <i class="bi bi-pencil"></i>
                                               </a>

                                                <form action="ServletUsuario?eliminar=1" method="post">
                                                	<input type="hidden" name="idCliente" value="<%= usuario.getId_cliente()%>" />
                                                	<input type="hidden" name="idEliminar" value="<%= usuario.getId_usuario()%>" />
                                                	<button type="submit" class="btn btn-sm btn-outline-danger" title="Eliminar Usuario"
                                                		onclick="return confirm('¿Estás seguro de que deseas eliminar este usuario?')">
                                                    	<i class="bi bi-trash"></i>
                                                	</button>
                                                
                                                </form>
                                            </div>
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

    <footer class="bg-light text-center text-muted py-3 mt-auto">
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