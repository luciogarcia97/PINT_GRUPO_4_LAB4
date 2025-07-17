package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Cliente;
import entidades.Cuenta;
import entidades.TipoCuenta;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.MovimientoNegocio;
import negocio.TipoCuentaNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;
import negocioImpl.ClienteNegociolmpl;
import negocioImpl.MovimientoNegocioImpl;

@WebServlet("/ServletCuenta")
public class ServletCuenta extends HttpServlet {
private static final long serialVersionUID = 1L;
    
    private CuentaNegocio cuentaNegocio;
    private TipoCuentaNegocio tipoCuentaNegocio;
    private ClienteNegocio clienteNegocio;
    private MovimientoNegocio movimientoNegocio;
    
    public ServletCuenta() {
        super();
        this.cuentaNegocio = new CuentaNegocioImpl();
        this.tipoCuentaNegocio = new TipoCuentaNegocioImpl();
        this.clienteNegocio = new ClienteNegociolmpl();
        this.movimientoNegocio = new MovimientoNegocioImpl();
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

                if (!clienteNegocio.existeCliente(idCliente)) {
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
                	
                	// Obtenemos el ID de la cuenta recien creada
                    int idCuentaCreada = cuentaNegocio.obtenerUltimaIdCuenta();
                	
                	//Registramos el movimiento de alta de cuenta
                	boolean movimientoRegistrado = movimientoNegocio.registrarMovimientoAltaCuenta(idCuentaCreada, BigDecimal.valueOf(10000.00));
            	    
            	    if (!movimientoRegistrado) {
            	        System.out.println("Advertencia: No se pudo registrar el movimiento de alta de cuenta");
            	    }
            	    
            	    request.getSession().setAttribute("exito", "Cuenta agregada exitosamente");
                	
                	
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
        
        if (request.getParameter("btnModificarCuenta") != null) {
            try {
            	
                // Obtener datos del formulario
                int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
                int idCliente = Integer.parseInt(request.getParameter("txtIdCliente"));
                int idTipoCuenta = Integer.parseInt(request.getParameter("ddlTipoCuenta"));
                
                String numeroCuenta = request.getParameter("txtNumeroCuenta");
                String cbu = request.getParameter("txtCbu");
                
                BigDecimal saldo = new BigDecimal(request.getParameter("txtSaldo"));
                
                boolean activa = Boolean.parseBoolean(request.getParameter("txtActiva"));
                
                String fechaCreacionStr = request.getParameter("txtFechaCreacion");
                LocalDate fechaCreacion = null;
                
                try {
                    fechaCreacion = LocalDate.parse(fechaCreacionStr);
                    if (fechaCreacion.isAfter(LocalDate.now())) {
                        request.setAttribute("error", "La fecha de creación no puede ser futura.");
                        RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                        rd.forward(request, response);
                        return;
                    }
                } catch (DateTimeParseException e) {
                    request.setAttribute("error", "La fecha de creación es inválida.");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Verifica que la cuenta existe
                Cuenta cuentaExistente = cuentaNegocio.buscarPorID(idCuenta);
                if (cuentaExistente == null) {
                    request.setAttribute("error", "La cuenta no existe");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Verifica que el ID del cliente sea válido
                if (idCliente <= 0) {
                    request.setAttribute("error", "Debe especificar un ID de cliente válido");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Verifica que el cliente existe
                if (!clienteNegocio.existeCliente(idCliente)) {
                    request.setAttribute("error", "El cliente con ID " + idCliente + " no existe o está eliminado");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Verifica que el tipo de cuenta existe
                TipoCuenta tipoCuenta = tipoCuentaNegocio.obtenerTipoPorId(idTipoCuenta);
                if (tipoCuenta == null) {
                    request.setAttribute("error", "Tipo de cuenta no válido");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Verifica que los campos no estén vacíos
                if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
                    request.setAttribute("error", "El número de cuenta no puede estar vacío");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                // Verifica que el número de cuenta debe ser numérico, no debe ser 0 y no debe contener letras
                if (!numeroCuenta.matches("^[1-9][0-9]*$") ) {
                    request.setAttribute("error", "El número de cuenta debe ser un número positivo, sin letras ni ceros iniciales");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                // Verifica que CBU debe ser solo números y tener exactamente 22 dígitos
                if (cbu == null || !cbu.matches("^\\d{22}$")) {
                    request.setAttribute("error", "El CBU debe contener exactamente 22 dígitos numéricos");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Verifica que el saldo no sea negativo
                if (saldo.compareTo(BigDecimal.ZERO) < 0) {
                    request.setAttribute("error", "El saldo no puede ser negativo");
                    RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Verifica el límite de cuentas activas
                if (activa) {
                    if (!cuentaNegocio.puedeCrearCuenta(idCliente, idCuenta)) {
                        request.setAttribute("error", "No se puede asignar la cuenta al cliente " + idCliente + ": ya tiene el máximo de 3 cuentas activas");
                        RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                        rd.forward(request, response);
                        return;
                    }
                }
                
                // Verifica que el número de cuenta no esté duplicado (si cambió)
                if (!numeroCuenta.equals(cuentaExistente.getNumeroCuenta())) {
                    if (cuentaNegocio.existeNumeroCuenta(numeroCuenta)) {
                        request.setAttribute("error", "El número de cuenta ya existe en el sistema");
                        RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                        rd.forward(request, response);
                        return;
                    }
                }
                
                // Verifica que el CBU no esté duplicado (si cambió)
                if (!cbu.equals(cuentaExistente.getCbu())) {
                    if (cuentaNegocio.existeCBU(cbu)) {
                        request.setAttribute("error", "El CBU ya existe en el sistema");
                        RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
                        rd.forward(request, response);
                        return;
                    }
                }
                
                // Crea el objeto cuenta con los datos modificados
                Cuenta cuentaModificada = new Cuenta();
                cuentaModificada.setIdCuenta(idCuenta);
                cuentaModificada.setIdCliente(idCliente);
                cuentaModificada.setIdTipoCuenta(idTipoCuenta);
                cuentaModificada.setNumeroCuenta(numeroCuenta);
                cuentaModificada.setCbu(cbu);
                cuentaModificada.setSaldo(saldo);
                cuentaModificada.setActiva(activa);
                cuentaModificada.setFechaCreacion(fechaCreacion);
                
                // Modifica en la base de datos
                boolean resultado = cuentaNegocio.modificarCuenta(cuentaModificada);
                
                if (resultado) {
                    request.getSession().setAttribute("exito", "Cuenta modificada exitosamente");
                } else {
                    request.getSession().setAttribute("error", "Error al modificar la cuenta. Intente nuevamente.");
                }
                
                response.sendRedirect("ServletCuenta?listar=1");
                
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Datos numéricos inválidos");
                cargarListado(request, response);               
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error interno del sistema");
                cargarListado(request, response);                
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
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                
                Cuenta cuenta = cuentaNegocio.obtenerCuentaPorId(idCuenta);
                if (cuenta == null) {
                    request.setAttribute("error", "La cuenta no existe");
                    cargarListado(request, response);
                    return;
                }
                
                Cliente cliente = clienteNegocio.BuscarPorID(idCliente);
                if (cliente.getEliminado()) {
                	request.setAttribute("error", "Cliente fue dado de baja");
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
        List<TipoCuenta> listaTipos = tipoCuentaNegocio.obtenerTiposCuenta();
        request.setAttribute("listaTipos", listaTipos);
	        
        RequestDispatcher rd = request.getRequestDispatcher("/administrarCuentas.jsp");
        rd.forward(request, response);
    }

    private void cuentaCreada(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	        
        RequestDispatcher rd = request.getRequestDispatcher("/resultadoCuenta.jsp");
        rd.forward(request, response);
    }
}
