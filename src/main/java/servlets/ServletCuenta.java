package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CuentaDao;
import dao.TipoCuentaDao;
import entidades.Cuenta;
import entidades.TipoCuenta;

@WebServlet("/ServletCuenta")
public class ServletCuenta extends HttpServlet {
private static final long serialVersionUID = 1L;
    
    private CuentaDao cuentaDao;
    private TipoCuentaDao tipoCuentaDao;
    
    public ServletCuenta() {
        super();
        this.cuentaDao = new CuentaDao();
        this.tipoCuentaDao = new TipoCuentaDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if (request.getParameter("Param") != null) {
        	cargarFormulario(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if (request.getParameter("btnCrearCuenta") != null) {
            try {
                // Obtener datos del formulario
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                int idTipoCuenta = Integer.parseInt(request.getParameter("ddlTipoCuenta"));
                
                // Validar que el cliente existe (por ahora solo validamos que no sea 0 porque no tengo la clase cliente)
                // TODO : Cuando este la clase cliente debemos validar que el cliente exista
                if (idCliente <= 0) {
                    request.setAttribute("error", "Debe especificar un cliente válido");
                    cargarFormulario(request, response);
                    return;
                }
                
                // Validar que el tipo de cuenta existe
                TipoCuenta tipoCuenta = tipoCuentaDao.obtenerTipoPorId(idTipoCuenta);
                if (tipoCuenta == null) {
                    request.setAttribute("error", "Tipo de cuenta no válido");
                    cargarFormulario(request, response);
                    return;
                }
                
                // Generar numero de cuenta unico
                String numeroCuenta = cuentaDao.generarNumeroCuenta();
                
                // Verificar que el numero generado sea unico
                while (cuentaDao.existeNumeroCuenta(numeroCuenta)) {
                    numeroCuenta = cuentaDao.generarNumeroCuenta();
                }
                
                // Generar CBU
                String cbu = cuentaDao.generarCBU(numeroCuenta);
                
                // Crear objeto cuenta
                Cuenta nuevaCuenta = new Cuenta(idCliente, idTipoCuenta, numeroCuenta, cbu);
                nuevaCuenta.setFechaCreacion(LocalDate.now());
                
                // Insertar en base de datos
                boolean resultado = cuentaDao.insertarCuenta(nuevaCuenta);
                
                if (resultado) {
                    // Formulario registrado - mostramos los datos registrados
                    request.setAttribute("exito", "Cuenta creada exitosamente");
                    request.setAttribute("numeroCuenta", numeroCuenta);
                    request.setAttribute("cbu", cbu);
                    request.setAttribute("tipoCuenta", tipoCuenta.getNombre());
                    
                    RequestDispatcher rd = request.getRequestDispatcher("/resultadoCuenta.jsp");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("error", "Error al crear la cuenta. Intente nuevamente.");
                    cargarFormulario(request, response);
                }
                
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Datos numéricos inválidos");
                cargarFormulario(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error interno del sistema");
                cargarFormulario(request, response);
            }
        }
    }
    
    private void cargarFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recargar tipos de cuenta para el desplegable
        List<TipoCuenta> listaTipos = tipoCuentaDao.obtenerTiposCuenta();
        request.setAttribute("listaTipos", listaTipos);
        
        RequestDispatcher rd = request.getRequestDispatcher("/registrarCuenta.jsp");
        rd.forward(request, response);
    }
}
