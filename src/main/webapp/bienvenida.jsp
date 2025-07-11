<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entidades.Usuario" %>
<%
    Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Bienvenida</title>
    <link rel="icon" type="image/x-icon" href="img/banco.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body style="background-color: rgb(230, 240, 255);">

<div class="container mt-5 text-center">
    <div class="card p-4 shadow w-50 mx-auto">
        <h2 class="mb-3">¡Bienvenido, <%= user.getUsuario()%>!</h2>
        <p class="lead">Has iniciado sesión correctamente.</p>
        
      
        <form action="ServletClienteMovimiento" method="get">
            <input type="hidden" name="listar" value="1">
            <button class="btn btn-primary mt-3">Ir a mi panel</button>
        </form>
        
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>