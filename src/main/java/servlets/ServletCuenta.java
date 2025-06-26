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
        
        if (request.getParameter("Param") != null) {
        	cargarFormularioRegistrar(request, response);
        }
        
	    if (request.getParameter("listar") != null) {
	    	cargarListado(request, response);
	    }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if (request.getParameter("btnCrearCuenta") != null) {
            try {
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                int idTipoCuenta = Integer.parseInt(request.getParameter("ddlTipoCuenta"));
                

                if (idCliente <= 0) {
                    request.setAttribute("error", "Debe especificar un ID de cliente válido");
                    cargarFormularioRegistrar(request, response);
                    return;
                }

                if (!cuentaNegocio.existeCliente(idCliente)) {
                    request.setAttribute("error", "El cliente con ID " + idCliente + " no existe o está eliminado");
                    cargarFormularioRegistrar(request, response);
                    return;
                }

                if (!cuentaNegocio.puedeCrearCuenta(idCliente)) {
                    request.setAttribute("error", "El cliente ya tiene el máximo de 3 cuentas permitidas");
                    cargarFormularioRegistrar(request, response);
                    return;
                }
                
                TipoCuenta tipoCuenta = tipoCuentaNegocio.obtenerTipoPorId(idTipoCuenta);
                if (tipoCuenta == null) {
                    request.setAttribute("error", "Tipo de cuenta no válido");
                    cargarFormularioRegistrar(request, response);
                    return;
                }
                
                String numeroCuenta = cuentaNegocio.generarNumeroCuenta();
                
                while (cuentaNegocio.existeNumeroCuenta(numeroCuenta)) {
                    numeroCuenta = cuentaNegocio.generarNumeroCuenta();
                }

                String cbu = cuentaNegocio.generarCBU(numeroCuenta);

                Cuenta nuevaCuenta = new Cuenta(idCliente, idTipoCuenta, numeroCuenta, cbu);
                nuevaCuenta.setFechaCreacion(LocalDate.now());
                                                
                boolean resultado = cuentaNegocio.insertarCuenta(nuevaCuenta);
                
                if (resultado) {
                    request.setAttribute("exito", "Cuenta creada exitosamente");
                    request.setAttribute("numeroCuenta", numeroCuenta);
                    request.setAttribute("cbu", cbu);
                    request.setAttribute("tipoCuenta", tipoCuenta.getNombre());
                    
                    cuentaCreada(request, response);
                } else {
                    request.setAttribute("error", "Error al crear la cuenta. Intente nuevamente.");
                    cargarFormularioRegistrar(request, response);
                }
                
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Datos numéricos inválidos");
                cargarFormularioRegistrar(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error interno del sistema");
                cargarFormularioRegistrar(request, response);
            }
        }
        
        if(request.getParameter("eliminar")!= null) {
            try {
                int idCuenta = Integer.parseInt(request.getParameter("idEliminar"));
                boolean resultado = cuentaNegocio.eliminarCuenta(idCuenta);

                if(resultado){
                    request.setAttribute("exito", "Cuenta eliminada exitosamente");
                } else {
                    request.setAttribute("error", "No se pudo eliminar la cuenta");
                }

                cargarListado(request, response);
                
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID de cuenta inválido");
                cargarListado(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error interno al eliminar la cuenta");
                cargarListado(request, response);
            }
        }
        
        if(request.getParameter("reactivar") != null) {
            try {
                int idCuenta = Integer.parseInt(request.getParameter("idReactivar"));
                
                Cuenta cuenta = cuentaNegocio.obtenerCuentaPorId(idCuenta);
                if (cuenta == null) {
                    request.setAttribute("error", "La cuenta no existe");
                    cargarListado(request, response);
                    return;
                }
                
                if (!cuentaNegocio.puedeCrearCuenta(cuenta.getIdCliente())) {
                    request.setAttribute("error", "No se puede reactivar: el cliente ya tiene 3 cuentas activas");
                    cargarListado(request, response);
                    return;
                }
                boolean resultado = cuentaNegocio.reactivarCuenta(idCuenta);

                if(resultado){
                	request.setAttribute("exito", "Cuenta reactivada correctamente");
                } else {
                    request.setAttribute("error", "No se pudo reactivar la cuenta");
                }
                
                cargarListado(request, response);
                
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID de cuenta inválido");
                cargarListado(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error interno al reactivar la cuenta");
                cargarListado(request, response);
            }
        }
    }
    
    private void cargarFormularioRegistrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TipoCuenta> listaTipos = tipoCuentaNegocio.obtenerTiposCuenta();
        request.setAttribute("listaTipos", listaTipos);
        
        RequestDispatcher rd = request.getRequestDispatcher("/registrarCuenta.jsp");
        rd.forward(request, response);
    }

    private void cargarListado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Cuenta> listaCuentas = cuentaNegocio.obtenerCuentas();
        request.setAttribute("listaCuentas", listaCuentas);
	        
        RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
        rd.forward(request, response);
    }

    private void cuentaCreada(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	        
        RequestDispatcher rd = request.getRequestDispatcher("/resultadoCuenta.jsp");
        rd.forward(request, response);
    }
}
