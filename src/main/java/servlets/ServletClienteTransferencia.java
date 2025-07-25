package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
import entidades.Movimiento;
import entidades.TipoCuenta;
import entidades.Transferencia;
import entidades.Usuario;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.TipoCuentaNegocio;
import negocio.TransferenciaNegocio;
import negocio.UsuarioNegocio;
import negocio.MovimientoNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;
import negocioImpl.TransferenciaNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;
import negocioImpl.ClienteNegociolmpl;
import negocioImpl.MovimientoNegocioImpl;

@WebServlet("/ServletClienteTransferencia")
public class ServletClienteTransferencia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ClienteNegocio clienteNegocio;
	private CuentaNegocio cuentaNegocio;
	private TipoCuentaNegocio tipoCuentaNegocio;
	private UsuarioNegocio usuarioNegocio;
	private MovimientoNegocio movimientoNegocio;
	private TransferenciaNegocio transferenciaNegocio;

	public ServletClienteTransferencia() {
		super();
		this.clienteNegocio = new ClienteNegociolmpl();
		this.cuentaNegocio = new CuentaNegocioImpl();
		this.tipoCuentaNegocio = new TipoCuentaNegocioImpl();
		this.usuarioNegocio = new UsuarioNegocioImpl();
		this.movimientoNegocio = new MovimientoNegocioImpl();
		this.transferenciaNegocio = new TransferenciaNegocioImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
		if (usuarioLogueado == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		request.getSession().setAttribute("usuarioLogueado", usuarioLogueado);

		Cliente cliente = clienteNegocio.BuscarPorID(usuarioLogueado.getId_cliente());
		request.setAttribute("cliente", cliente);

		List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(cliente.getIdCliente());
		request.setAttribute("cuentasCliente", cuentasCliente);

		if (cuentasCliente != null && !cuentasCliente.isEmpty()) {

			List<Transferencia> historialTransferencias = transferenciaNegocio
					.mostrar_historial_transferencia(cliente.getIdCliente());
			request.setAttribute("historial", historialTransferencias);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteTransferencias.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
		if (usuarioLogueado == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		request.getSession().setAttribute("usuarioLogueado", usuarioLogueado);

		if (request.getParameter("btnTransferencia") != null) {

			int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
			String cbu = request.getParameter("txtCbu").toString();
			BigDecimal cero = new BigDecimal("0.00");
			Cliente cliente = clienteNegocio.BuscarPorID(usuarioLogueado.getId_cliente());

			String txtMonto = request.getParameter("txtMonto");
			BigDecimal monto = new BigDecimal("0.00");

			if (txtMonto == null || txtMonto.trim().isEmpty()) {
				// Valida si es null o un string vacio
				request.setAttribute("cliente", cliente);

				// Cargar las cuentas del cliente para el desplegable
				List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(cliente.getIdCliente());
				request.setAttribute("cuentasCliente", cuentasCliente);

				request.setAttribute("montoInvalido", 1);

				RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteTransferencias.jsp");
				dispatcher.forward(request, response);

				return;
			} else {
				monto = new BigDecimal(txtMonto);
			}
			

			// Valida si la cuenta tiene el dinero suficiente
			if (cuentaNegocio.tieneSaldoSuficiente(idCuenta, monto) == false || monto.compareTo(cero) <= 0) {

				request.setAttribute("cliente", cliente);

				// Cargar las cuentas del cliente para el desplegable
				List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(cliente.getIdCliente());
				request.setAttribute("cuentasCliente", cuentasCliente);

				request.setAttribute("saldoInsuficiente", 1);

				RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteTransferencias.jsp");
				dispatcher.forward(request, response);

				return;
			}

			if (cuentaNegocio.existeCBU(cbu) == false) {

				request.setAttribute("cliente", cliente);

				// Cargar las cuentas del cliente para el desplegable
				List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(cliente.getIdCliente());
				request.setAttribute("cuentasCliente", cuentasCliente);

				request.setAttribute("cbuInexistente", 1);

				RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteTransferencias.jsp");
				dispatcher.forward(request, response);

				return;
			}

			// Revisa si la cuenta no esta dada de baja o no le permite transferir			
			Cuenta cuentaDestino = cuentaNegocio.buscarIdConCbu(cbu);
			if (cuentaDestino.isActiva() == false) {

				request.setAttribute("cliente", cliente);

				// Cargar las cuentas del cliente para el desplegable
				List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(cliente.getIdCliente());
				request.setAttribute("cuentasCliente", cuentasCliente);

				request.setAttribute("cbuInexistente", 1);

				RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteTransferencias.jsp");
				dispatcher.forward(request, response);

				return;
			}
			
			//invalida la transferencia al mismo cbu
			String cbuOrigen = request.getParameter("cbuOrigen");			
			Cuenta cuentaCbuDestino = cuentaNegocio.buscarIdConCbu(cbu);				
				
			if(cuentaCbuDestino.getCbu().equals(cbuOrigen)) {
					request.setAttribute("cliente", cliente);
					
					List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(cliente.getIdCliente());
					request.setAttribute("cuentasCliente", cuentasCliente);

					request.setAttribute("mismoCbu", 1);

					RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteTransferencias.jsp");
					dispatcher.forward(request, response);
					return;					
				}				
			
			// Resta el monto al saldo de la cuenta origen y lo suma en la cuenta destino a
			// la que le pertenezca ese CBU:
			Cuenta cuentaOrigen = cuentaNegocio.buscarPorID(idCuenta);
			boolean movimientoRegistrado = false;
			boolean transferenciaRegistrada = false;
			Boolean debitoResultado = false;
			BigDecimal saldoFinal = cuentaOrigen.getSaldo().subtract(monto);
			debitoResultado = cuentaNegocio.modificarSaldo(cuentaOrigen.getIdCuenta(), saldoFinal);

			Boolean acreditacionResultado = false;
			saldoFinal = cuentaDestino.getSaldo().add(monto);
			acreditacionResultado = cuentaNegocio.modificarSaldo(cuentaDestino.getIdCuenta(), saldoFinal);

			// Si se registro todo correctamente, registro el movimiento
			if (acreditacionResultado && debitoResultado) {
				String detalle = "CBU: " + cuentaDestino.getCbu();
				movimientoRegistrado = movimientoNegocio.registrarMovimientoTransferencia(cuentaOrigen.getIdCuenta(),
						cuentaDestino.getIdCuenta(), monto, detalle);

				if (!movimientoRegistrado) {
					System.out.println("Advertencia: No se pudieron registrar los movimientos de transferencia");
				}
			}
			Movimiento movimiento = new Movimiento();
			movimiento = transferenciaNegocio.obtener_movimiento_idCuentaOrigen(cuentaOrigen.getIdCuenta());
			transferenciaRegistrada = transferenciaNegocio.registrar_transferencia(idCuenta,
					cuentaDestino.getIdCuenta(), movimiento.getIdMovimiento());

			List<Transferencia> historialTransferencias = new ArrayList<Transferencia>();
			historialTransferencias = transferenciaNegocio.mostrar_historial_transferencia(cuentaOrigen.getIdCliente());
			request.setAttribute("historial", historialTransferencias);

			// Recargar los datos y mostrar la página de transferencias
			request.setAttribute("cliente", cliente);

			// Cargar las cuentas del cliente para el desplegable
			List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(cliente.getIdCliente());
			request.setAttribute("cuentasCliente", cuentasCliente);

			request.setAttribute("exito", "Transferencia realizada con éxito");
			RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteTransferencias.jsp");
			dispatcher.forward(request, response);

		}

	}

}
