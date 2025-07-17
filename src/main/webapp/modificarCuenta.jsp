<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="entidades.Cuenta" %>
<%@ page import="entidades.TipoCuenta" %>
<%@ page import="negocio.CuentaNegocio" %>
<%@ page import="negocio.TipoCuentaNegocio" %>
<%@ page import="negocioImpl.CuentaNegocioImpl" %>
<%@ page import="negocioImpl.TipoCuentaNegocioImpl" %>
<%@ page import="java.util.List" %>
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
<html>
	<head>
	<meta charset="UTF-8">
    <title>Modificar Cuenta</title>
    <link rel="icon" type="image/x-icon" href="img/banco.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">       
		
	<link rel="stylesheet" href="style/indexStyle.css" /> 
	</head>
	<body>
	<%
    String idStr = request.getParameter("id");
    Cuenta cuenta = null;
    List<TipoCuenta> listaTipos = null;
    
    if (idStr != null) {
        int id = Integer.parseInt(idStr);
        CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
        TipoCuentaNegocio tipoCuentaNegocio = new TipoCuentaNegocioImpl();
        cuenta = cuentaNegocio.buscarPorID(id);
        listaTipos = tipoCuentaNegocio.obtenerTiposCuenta();
    }
    %>

     <form class="w-75 mx-auto mt-5" action="ServletCuenta" method="post">
	    <div class="inicio">
	        <h2 class="text-center">Modificar cuenta</h2>
	
	        <div class="row">
	            <div class="mb-3 col-md-6">
	                <input type="hidden" disabled name="idCuenta" class="form-control" id="idCuenta"
	                 value="<%= cuenta != null ? cuenta.getIdCuenta() : "" %>" required>
	            </div>
	            
	            <div class="mb-3 col-md-6">
	                <label for="idCliente" class="form-label">ID Cliente</label>
	                <input type="number" disabled name="txtIdCliente" class="form-control" id="idCliente"
	                 value="<%= cuenta != null ? cuenta.getIdCliente() : "" %>" required>
	            </div>
	            
	            <div class="mb-3 col-md-6">
	                <label for="tipoCuenta" class="form-label">Tipo de Cuenta</label>
	                <select id="tipoCuenta" name="ddlTipoCuenta" class="form-control" required>
	                    <% if (listaTipos != null) {
	                        for (TipoCuenta tipo : listaTipos) { %>
	                        <option value="<%= tipo.getId() %>" 
	                                <%= cuenta != null && cuenta.getIdTipoCuenta() == tipo.getId() ? "selected" : "" %>>
	                            <%= tipo.getNombre() %>
	                        </option>
	                        <% }
	                    } %>
	                </select>
	            </div>
	            
	            <div class="mb-3 col-md-6">
	                <label for="numeroCuenta" class="form-label">Número de Cuenta</label>
	                <input type="text" disabled name="txtNumeroCuenta" class="form-control" id="numeroCuenta"
	                 value="<%= cuenta != null ? cuenta.getNumeroCuenta() : "" %>" required>
	            </div>
	            
	            <div class="mb-3 col-md-6">
	                <label for="cbu" class="form-label">CBU</label>
	                <input type="text" name="txtCbu" class="form-control" id="cbu"
	                 value="<%= cuenta != null ? cuenta.getCbu() : "" %>" required>
	            </div>
	            
	            <div class="mb-3 col-md-6">
	                <label for="saldo" class="form-label">Saldo</label>
	                <input type="number" disabled step="0.01" name="txtSaldo" class="form-control" id="saldo"
	                 value="<%= cuenta != null ? cuenta.getSaldo() : "" %>" required>
	            </div>
	            
	            <div class="mb-3 col-md-6">
	                <label for="activa" class="form-label">Estado</label>
	                <select id="activa" name="txtActiva" class="form-control">
	                    <option value="true" <%= cuenta != null && cuenta.isActiva() ? "selected" : "" %>>Activa</option>
	                    <option value="false" <%= cuenta != null && !cuenta.isActiva() ? "selected" : "" %>>Inactiva</option>
	                </select>
	            </div>
	            
	            <div class="mb-3 col-md-6">
	                <label for="fechaCreacion" class="form-label">Fecha de Creación</label>
	                <input type="date" disabled name="txtFechaCreacion" class="form-control" id="fechaCreacion"
	                 value="<%= cuenta != null ? cuenta.getFechaCreacion() : "" %>"
	                 max="<%= java.time.LocalDate.now() %>" required>
	            </div>
        </div>   

        <div class="text-center mt-3">
            <a href="ServletCuenta?listar=1">Cancelar</a>
        </div>

        <div class="mt-3">
            <button type="submit" name="btnModificarCuenta" value="btnModificarCuenta" class="btn btn-primary w-100">Modificar</button>
        </div>
    </div>
</form>

	<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html> 