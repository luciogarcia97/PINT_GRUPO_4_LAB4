<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Administrador - Cuentas</title>
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
                Administrador - Cuentas
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
                    
                    <a href="ServletClientes?listar=1" class="btn btn-light">
                        <i class="bi bi-people me-2"></i>
                        Administrar Clientes
                    </a>
                    
                    <a href="ServletUsuario?listar=1" class="btn btn-light">
                        <i class="bi bi-person-gear me-2"></i>
                        Administrar Usuarios
                    </a>
                    
                    <a href="administrarCuentas.jsp" class="btn btn-primary fw-bold">
                        <i class="bi bi-credit-card me-2"></i>
                        Administrar Cuentas
                    </a>
                    
                    <a href="prestamos.jsp" class="btn btn-light">
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
                        <i class="bi bi-credit-card me-2"></i>
                        Administrar Cuentas
                    </h3>
                    <a href="ServletCuenta?Param=1" class="btn btn-success">
					    Registrar Cuenta
					</a>
                </div>
                
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">
                            <i class="bi bi-table me-2"></i>
                            Lista de Cuentas
                        </h5>
                    </div>
                    <div class="card-body bg-white">
                        <div class="table-responsive">
                            <table id="example" class="table table-striped table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>IdCuenta</th>
                                        <th>IdCliente</th>
                                        <th>Fecha Creación</th>
                                        <th>Tipo de Cuenta</th>
                                        <th>Número de Cuenta</th>
                                        <th>CBU</th>
                                        <th>Saldo</th> 
                                        <th>Activa</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>15/01/2025</td>
                                        <td>Caja de Ahorro</td>
                                        <td>12345678</td>
                                        <td>1234567890123456789012</td>
                                        <td><strong>$25,000.00</strong></td>
                                        <td>Sí</td>                   
                                        <td>
                                            <div class="d-flex gap-1">
                                                <button type="button" class="btn btn-sm btn-success" title="Activar cuenta" disabled>
                                                    <i class="bi bi-check-circle"></i>
                                                    Activar
                                                </button>                               
                                                <button type="button" class="btn btn-sm btn-danger" title="Desactivar cuenta">
                                                    <i class="bi bi-x-circle"></i>
                                                    Desactivar
                                                </button>
                                            </div>
                                        </td>                   
                                    </tr>
                                    <tr>
                                        <td>2</td>
                                        <td>1</td>
                                        <td>10/03/2025</td>
                                        <td>Cuenta Corriente</td>
                                        <td>87654321</td>
                                        <td>0987654321098765432109</td>
                                        <td><strong>$15,500.50</strong></td>
                                        <td>Sí</td>                   
                                        <td>
                                            <div class="d-flex gap-1">
                                                <button type="button" class="btn btn-sm btn-success" title="Activar cuenta" disabled>
                                                    <i class="bi bi-check-circle"></i>
                                                    Activar
                                                </button>                               
                                                <button type="button" class="btn btn-sm btn-danger" title="Desactivar cuenta">
                                                    <i class="bi bi-x-circle"></i>
                                                    Desactivar
                                                </button>
                                            </div>
                                        </td>                   
                                    </tr>
                                    <tr>
                                        <td>3</td>
                                        <td>2</td>
                                        <td>20/02/2025</td>
                                        <td>Caja de Ahorro</td>
                                        <td>11223344</td>
                                        <td>1122334455667788990011</td>
                                        <td><strong>$30,750.25</strong></td>
                                        <td>No</td>                   
                                        <td>
                                            <div class="d-flex gap-1">
                                                <button type="button" class="btn btn-sm btn-success" title="Activar cuenta">
                                                    <i class="bi bi-check-circle"></i>
                                                    Activar
                                                </button>                               
                                                <button type="button" class="btn btn-sm btn-danger" title="Desactivar cuenta" disabled>
                                                    <i class="bi bi-x-circle"></i>
                                                    Desactivar
                                                </button>
                                            </div>
                                        </td>                   
                                    </tr>
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