<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="entidades.TipoCuenta" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Cuenta</title>
    <link rel="icon" type="image/x-icon" href="img/banco.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">       
    <link rel="stylesheet" href="style/indexStyle.css" />
</head>
<body>

    <form class="w-75 mx-auto mt-5" action="ServletCuenta" method="post">
        <div class="inicio">
            <h2 class="text-center">Agregar Cuenta</h2>

            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <div class="row">
                <div class="center row">
                    <div class="mb-3">
                        <label for="idCliente" class="form-label">ID del Cliente</label>
                        <input type="number" class="form-control" id="idCliente" name="idCliente" 
                               placeholder="Ingrese ID del cliente" required min="1">
                        <div class="form-text">
						    <i class="bi bi-info-circle me-1"></i>
						    Máximo 3 cuentas por cliente. Solo se pueden crear cuentas para clientes existentes.
						</div>
                    </div> 
                    
                    <div class="mb-3 col-md-6">
                        <label for="ddlTipoCuenta" class="form-label">Tipo de Cuenta</label>
                        <select id="ddlTipoCuenta" name="ddlTipoCuenta" class="form-control" required>
                            <option value="">Seleccione un tipo</option>
                            <% 
                                List<TipoCuenta> listaTipos = (List<TipoCuenta>) request.getAttribute("listaTipos");
                                if (listaTipos != null) {
                                    for (TipoCuenta tipo : listaTipos) {
                            %>
                                        <option value="<%= tipo.getId() %>"><%= tipo.getNombre() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    
                    <div class="mb-3 col-md-6">
                        <label class="form-label">Fecha Creación</label>
                        <input type="text" class="form-control" value="<%= java.time.LocalDate.now() %>" 
                               readonly style="background-color: #f8f9fa;">
                        <div class="form-text">Se asigna automáticamente la fecha actual</div>
                    </div>
                    
                    <div class="mb-3">
                        <div class="card">
                            <div class="card-body">
                                <h6 class="card-title">Información automática:</h6>
                                <ul class="list-unstyled mb-0">
                                    <li><strong>Número de cuenta:</strong> Se genera automáticamente</li>
                                    <li><strong>CBU:</strong> Se genera automáticamente</li>
                                    <li><strong>Saldo inicial:</strong> $10,000.00</li>
                                    <li><strong>Estado:</strong> Activa</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    
                    <div class="text-center mt-3">
                        <a href="ServletCuenta?listar=1" class="btn btn-secondary me-2">Cancelar</a>
                        <button type="submit" name="btnCrearCuenta" class="btn btn-primary">Crear Cuenta</button>
                    </div>
                </div>
            </div>
        </div>
    </form>     

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>