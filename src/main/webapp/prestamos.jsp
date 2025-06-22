<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <button class="btn btn-outline-dark">Cerrar Sesión</button>        
        </div>
    </nav>

    <div class="container text-center flex-grow-1"> 
        <div class="row">		                    
            
            <div class="col-2 d-flex flex-column align-items-center justify-content-start pt-4">
                <div class="d-grid gap-2 w-100">
                    
                    <a href="administrarClientes.jsp" class="btn btn-light">
                        <i class="bi bi-people me-2"></i>
                        Administrar Clientes
                    </a>
                    
                    <a href="administrarUsuarios.jsp" class="btn btn-light">
                        <i class="bi bi-person-gear me-2"></i>
                        Administrar Usuarios
                    </a>
                    
                    <a href="administrarCuentas.jsp" class="btn btn-light">
                        <i class="bi bi-credit-card me-2"></i>
                        Administrar Cuentas
                    </a>
                    
                    <a href="prestamos.jsp" class="btn btn-primary fw-bold">
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
                                    <tr>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>1</td>
                                        <td>15/05/2025</td>
                                        <td>12 meses</td>
                                        <td><strong>$30,000.00</strong></td>
                                        <td>$2,750.00</td>
                                        <td>12</td>
                                        <td>Pendiente</td>                   
                                        <td>
                                            <div class="d-flex gap-1">
                                                <button type="button" class="btn btn-sm btn-success" title="Aceptar Préstamo">
                                                    <i class="bi bi-check"></i>
                                                    Aprobar
                                                </button>
                                                <button type="button" class="btn btn-sm btn-danger" title="Rechazar Préstamo">
                                                    <i class="bi bi-x"></i>
                                                    Rechazar
                                                </button>
                                            </div>
                                        </td>                   
                                    </tr>
                                    <tr>
                                        <td>2</td>
                                        <td>2</td>
                                        <td>3</td>
                                        <td>01/06/2025</td>
                                        <td>6 meses</td>
                                        <td><strong>$15,000.00</strong></td>
                                        <td>$2,750.00</td>
                                        <td>6</td>
                                        <td>Pendiente</td>                   
                                        <td>
                                            <div class="d-flex gap-1">
                                                <button type="button" class="btn btn-sm btn-success" title="Aceptar Préstamo">
                                                    <i class="bi bi-check"></i>
                                                    Aprobar
                                                </button>
                                                <button type="button" class="btn btn-sm btn-danger" title="Rechazar Préstamo">
                                                    <i class="bi bi-x"></i>
                                                    Rechazar
                                                </button>
                                            </div>
                                        </td>                   
                                    </tr>
                                    <tr>
                                        <td>3</td>
                                        <td>1</td>
                                        <td>2</td>
                                        <td>28/05/2025</td>
                                        <td>24 meses</td>
                                        <td><strong>$50,000.00</strong></td>
                                        <td>$2,458.33</td>
                                        <td>24</td>
                                        <td>Aprobado</td>                   
                                        <td>
                                            <div class="d-flex gap-1">
                                                <button type="button" class="btn btn-sm btn-outline-secondary" disabled>
                                                    <i class="bi bi-check"></i>
                                                    Aprobado
                                                </button>
                                            </div>
                                        </td>                   
                                    </tr>
                                    <tr>
                                        <td>4</td>
                                        <td>2</td>
                                        <td>3</td>
                                        <td>10/05/2025</td>
                                        <td>18 meses</td>
                                        <td><strong>$25,000.00</strong></td>
                                        <td>$1,555.56</td>
                                        <td>18</td>
                                        <td>Rechazado</td>                   
                                        <td>
                                            <div class="d-flex gap-1">
                                                <button type="button" class="btn btn-sm btn-outline-secondary" disabled>
                                                    <i class="bi bi-x"></i>
                                                    Rechazado
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