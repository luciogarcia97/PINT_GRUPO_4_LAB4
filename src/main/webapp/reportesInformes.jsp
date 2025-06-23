<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Administrador - Reportes</title>
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
                    
                    <a href="prestamos.jsp" class="btn btn-light">
                        <i class="bi bi-cash-coin me-2"></i>
                        Autorizar Préstamos
                    </a>
                    
                    <a href="reportesInformes.jsp" class="btn btn-primary fw-bold">
                        <i class="bi bi-graph-up me-2"></i>
                        Informe/Reportes
                    </a>
                    
                </div>
            </div>
            
            <div class="col-10">
                
                <div class="d-flex justify-content-between align-items-center mb-4 pt-4">
                    <h3 class="text-white">
                        <i class="bi bi-graph-up me-2"></i>
                        Informes y Reportes
                    </h3>
                </div>
                
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">
                            <i class="bi bi-file-earmark-bar-graph me-2"></i>
                            Generar Reporte
                        </h5>
                    </div>
                    <div class="card-body bg-white">
                        
                        <form id="formReporte" onsubmit="generarReporte(event)">
                            <div class="row mb-4">
                                <div class="col-md-4">
                                    <label for="fechaDesde" class="form-label">Fecha Desde</label>
                                    <input type="date" class="form-control" id="fechaDesde" required>
                                </div>
                                <div class="col-md-4">
                                    <label for="fechaHasta" class="form-label">Fecha Hasta</label>
                                    <input type="date" class="form-control" id="fechaHasta" required>
                                </div>
                                <div class="col-md-4">
                                    <label for="tipoReporte" class="form-label">Tipo de Reporte</label>
                                    <select class="form-select" id="tipoReporte" required>
                                        <option value="" disabled selected>Seleccionar tipo de reporte</option>
                                        <option value="depositos">Suma de Depósitos</option>
                                        <option value="transferencias">Transferencias Realizadas</option>
                                        <option value="saldo">Saldo Diario Promedio</option>
                                        <option value="prestamos">Préstamos Otorgados</option>
                                        <option value="clientes">Clientes Activos</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="text-center">
                                <button type="submit" class="btn btn-success btn-lg">
                                    <i class="bi bi-download me-2"></i>
                                    Generar y Descargar Reporte
                                </button>
                                <button type="button" class="btn btn-outline-secondary btn-lg ms-2" onclick="limpiarFormulario()">
                                    <i class="bi bi-arrow-clockwise me-2"></i>
                                    Limpiar
                                </button>
                            </div>
                        </form>
                        
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
            event.preventDefault();
            
            const fechaDesde = document.getElementById('fechaDesde').value;
            const fechaHasta = document.getElementById('fechaHasta').value;
            const tipoReporte = document.getElementById('tipoReporte').value;
            
            if (new Date(fechaDesde) > new Date(fechaHasta)) {
                alert('La fecha "Desde" debe ser anterior a la fecha "Hasta"');
                return;
            }
            
            const tipoTexto = document.querySelector('#tipoReporte option:checked').text;
            const nombreArchivo = `Reporte_${tipoReporte}_${fechaDesde}_${fechaHasta}.pdf`;
            
            alert(`Reporte "${tipoTexto}" generado exitosamente.\nArchivo: ${nombreArchivo}\n\nEn la implementación final, aquí se descargaría el archivo PDF.`);
        }
        
        function limpiarFormulario() {
            document.getElementById('formReporte').reset();
        }
        
        });
    </script>

</body>
</html>