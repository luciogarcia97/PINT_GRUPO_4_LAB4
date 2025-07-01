package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Cuenta;
import entidades.Movimiento;
import entidades.Usuario;
import negocioImpl.MovimientoNegocioImpl;
import negocioImpl.CuentaNegocioImpl;

@WebServlet("/ServletMovimiento")
public class ServletMovimiento extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private MovimientoNegocioImpl movimientoNegocio;
    private CuentaNegocioImpl cuentaNegocio;
    
    public ServletMovimiento() {
        super();
        this.movimientoNegocio = new MovimientoNegocioImpl();
        this.cuentaNegocio = new CuentaNegocioImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        if (request.getParameter("consultar") != null) {
            consultarMovimientos(request, response, usuario);
            return;
        }
        
        try {
            List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(usuario.getId_cliente());
            request.setAttribute("cuentasCliente", cuentasCliente);
            
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
            rd.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar las cuentas: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
            rd.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            response.sendRedirect("Login.jsp");
            return;
        }
    }
    
    private void cargarCuentasCliente(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException {
        
        try {
            System.out.println("Cargando cuentas para cliente ID: " + usuario.getId_cliente());
            
            List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(usuario.getId_cliente());
            
            System.out.println("Cuentas encontradas: " + (cuentasCliente != null ? cuentasCliente.size() : 0));
            
            request.setAttribute("cuentasCliente", cuentasCliente);
            
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
            rd.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar las cuentas: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
            rd.forward(request, response);
        }
    }
    
    private void consultarMovimientos(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException {
        
        try {
            int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
            
            Cuenta cuenta = cuentaNegocio.buscarPorID(idCuenta);
            if (cuenta == null || !cuenta.isActiva()) {
                request.setAttribute("error", "La cuenta seleccionada no existe o está inactiva.");
                RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
                rd.forward(request, response);
                return;
            }
            
            if (cuenta.getIdCliente() != usuario.getId_cliente()) {
                request.setAttribute("error", "No tiene permisos para consultar esta cuenta.");
                RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
                rd.forward(request, response);
                return;
            }
            
            List<Movimiento> movimientos = movimientoNegocio.obtenerMovimientosPorCuenta(idCuenta);
            List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(usuario.getId_cliente());
            
            request.setAttribute("movimientos", movimientos);
            request.setAttribute("cuentasCliente", cuentasCliente);
            request.setAttribute("cuentaSeleccionada", cuenta);
            
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
            rd.forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID de cuenta inválido.");
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error interno del sistema.");
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
            rd.forward(request, response);
        }
    }
    
}