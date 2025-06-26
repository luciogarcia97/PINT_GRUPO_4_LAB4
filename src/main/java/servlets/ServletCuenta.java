package servlets;

import java.io.IOException;
import java.math.BigDecimal;
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
import daoImpl.CuentaDaoImpl;
import daoImpl.TipoCuentaDaoImpl;
import entidades.Cliente;
import entidades.Cuenta;
import entidades.TipoCuenta;
import negocio.CuentaNegocio;
import negocio.TipoCuentaNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;

@WebServlet("/ServletCuenta")
public class ServletCuenta extends HttpServlet {
private static final long serialVersionUID = 1L;
    
    private CuentaNegocioImpl cuentaNegocio;
    private TipoCuentaNegocioImpl tipoCuentaNegocio;
    
    public ServletCuenta() {
        super();
        this.cuentaNegocio = new CuentaNegocioImpl();
        this.tipoCuentaNegocio = new TipoCuentaNegocioImpl();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo de mensajes de éxito y error desde la sesión
        Object exito = request.getSession().getAttribute("exito");
        if (exito != null) {
            request.setAttribute("exito", exito);
            request.getSession().removeAttribute("exito");
        }
        Object error = request.getSession().getAttribute("error");
        if (error != null) {
            request.setAttribute("error", error);
            request.getSession().removeAttribute("error");
        }
        
        if (request.getParameter("Param") != null) {
        	cargarFormulario(request, response);
        }
        
        // Para listar todas las cuentas
	    if (request.getParameter("listar") != null) {
	    	
	        List<Cuenta> listaCuentas = cuentaNegocio.obtenerCuentas();
	        List<TipoCuenta> listaTipos = tipoCuentaNegocio.obtenerTiposCuenta();
	        
	        request.setAttribute("listaCuentas", listaCuentas);
	        request.setAttribute("listaTipos", listaTipos);
	        
	        RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
	        rd.forward(request, response);
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
                TipoCuenta tipoCuenta = tipoCuentaNegocio.obtenerTipoPorId(idTipoCuenta);
                if (tipoCuenta == null) {
                    request.setAttribute("error", "Tipo de cuenta no válido");
                    cargarFormulario(request, response);
                    return;
                }
                
                // Generar numero de cuenta unico
                String numeroCuenta = cuentaNegocio.generarNumeroCuenta();
                
                // Verificar que el numero generado sea unico
                while (cuentaNegocio.existeNumeroCuenta(numeroCuenta)) {
                    numeroCuenta = cuentaNegocio.generarNumeroCuenta();
                }
                
                // Generar CBU
                String cbu = cuentaNegocio.generarCBU(numeroCuenta);
                
                // Crear objeto cuenta
                Cuenta nuevaCuenta = new Cuenta(idCliente, idTipoCuenta, numeroCuenta, cbu);
                nuevaCuenta.setFechaCreacion(LocalDate.now());
                
                // Insertar en base de datos
                boolean resultado = cuentaNegocio.insertarCuenta(nuevaCuenta);
                
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
        
        if (request.getParameter("btnModificarCuenta") != null) {
            try {
                // Obtener datos del formulario
                int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
                int idCliente = Integer.parseInt(request.getParameter("txtIdCliente"));
                int idTipoCuenta = Integer.parseInt(request.getParameter("ddlTipoCuenta")) ;
                String numeroCuenta = request.getParameter("txtNumeroCuenta");
                String cbu = request.getParameter("txtCbu");
                BigDecimal saldo = new BigDecimal(request.getParameter("txtSaldo"));
                boolean activa = Boolean.parseBoolean(request.getParameter("txtActiva"));
                
                // Validar que la cuenta existe
                Cuenta cuentaExistente = cuentaNegocio.buscarPorID(idCuenta);
                if (cuentaExistente == null) {
                    request.setAttribute("error", "La cuenta no existe");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Validar que el tipo de cuenta existe
                TipoCuenta tipoCuenta = tipoCuentaNegocio.obtenerTipoPorId(idTipoCuenta);
                if (tipoCuenta == null) {
                    request.setAttribute("error", "Tipo de cuenta no válido");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Crear objeto cuenta con los datos modificados
                Cuenta cuentaModificada = new Cuenta();
                cuentaModificada.setIdCuenta(idCuenta);
                cuentaModificada.setIdCliente(idCliente);
                cuentaModificada.setIdTipoCuenta(idTipoCuenta);
                cuentaModificada.setNumeroCuenta(numeroCuenta);
                cuentaModificada.setCbu(cbu);
                cuentaModificada.setSaldo(saldo);
                cuentaModificada.setActiva(activa);
                cuentaModificada.setFechaCreacion(cuentaExistente.getFechaCreacion()); // Mantener fecha original
                
                // Modificar en base de datos
                boolean resultado = cuentaNegocio.modificarCuenta(cuentaModificada);
                
                if (resultado) {
                    request.getSession().setAttribute("exito", "Cuenta modificada exitosamente");
                } else {
                    request.getSession().setAttribute("error", "Error al modificar la cuenta. Intente nuevamente.");
                }
                
                response.sendRedirect("ServletCuenta?listar=1");
                
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Datos numéricos inválidos");
                RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error interno del sistema");
                RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                rd.forward(request, response);
            }
        }
        
        if(request.getParameter("eliminar")!= null) 
		{		
        	
			Boolean resultado = false;
			
			int idCuenta = Integer.parseInt(request.getParameter("idEliminar"));
			
			resultado = cuentaNegocio.eliminarCuenta(idCuenta);
			
			RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
	        rd.forward(request, response);
		}
    }
    
    private void cargarFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recargar tipos de cuenta para el desplegable
        List<TipoCuenta> listaTipos = tipoCuentaNegocio.obtenerTiposCuenta();
        request.setAttribute("listaTipos", listaTipos);
        
        RequestDispatcher rd = request.getRequestDispatcher("/registrarCuenta.jsp");
        rd.forward(request, response);
    }
}
