<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cuenta Creada</title>
    <link rel="icon" type="image/x-icon" href="img/banco.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">       
    <link rel="stylesheet" href="style/indexStyle.css" />
</head>
<body>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                
                <div class="alert alert-success text-center" role="alert">
                    <h4 class="alert-heading">¡Cuenta creada exitosamente!</h4>
                    <p class="mb-0"><%= request.getAttribute("exito") %></p>
                </div>

                <div class="card shadow">
                    <div class="card-header bg-primary text-white text-center">
                        <h5 class="mb-0">Detalles de la Nueva Cuenta</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <h6 class="text-muted">Número de Cuenta:</h6>
                                <p class="fs-5 fw-bold text-primary"><%= request.getAttribute("numeroCuenta") %></p>
                            </div>
                            <div class="col-md-6">
                                <h6 class="text-muted">Tipo de Cuenta:</h6>
                                <p class="fs-5"><%= request.getAttribute("tipoCuenta") %></p>
                            </div>
                        </div>
                        
                        <div class="row mt-3">
                            <div class="col-12">
                                <h6 class="text-muted">CBU (Clave Bancaria Uniforme):</h6>
                                <p class="fs-6 fw-bold text-success font-monospace"><%= request.getAttribute("cbu") %></p>
                            </div>
                        </div>
                        
                        <div class="row mt-3">
                            <div class="col-md-6">
                                <h6 class="text-muted">Saldo Inicial:</h6>
                                <p class="fs-5 text-success fw-bold">$10,000.00</p>
                            </div>
                            <div class="col-md-6">
                                <h6 class="text-muted">Estado:</h6>
                                <span class="badge bg-success fs-6">Activa</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="text-center mt-4">
                    <a href="ServletCuenta?listar=1" class="btn btn-primary me-2">
                        <i class="bi bi-arrow-left"></i> Volver a Administrar Cuentas
                    </a>
                    <a href="ServletCuenta?Param=1" class="btn btn-success">
                        <i class="bi bi-plus"></i> Crear Otra Cuenta
                    </a>
                </div>

            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>