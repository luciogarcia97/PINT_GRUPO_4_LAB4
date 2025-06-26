<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Cliente</title>
    <link rel="icon" type="image/x-icon" href="img/banco.png">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <style>
        .panel {
            display: none;
            background-color: white;
            padding: 20px;
            margin-top: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
        }
        .active-button {
            background-color: #0056b3;
            color: white;
        }
    </style>
</head>

<body style="background-color: rgb(104, 109, 250);">

<nav class="navbar navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <i class="bi bi-person-circle"></i> Usuario Cliente
        </a>        
        <form action="ServletLogin" method="get" class="d-inline">
            <button class="btn btn-outline-dark" type="submit" name="btnCerrar">Cerrar Sesión</button>
        </form>
    </div>
</nav>

<div class="container mt-4">
    <div class="d-flex justify-content-center gap-3">
        <button class="btn btn-primary" onclick="togglePanel('movimientos', this)">Ver Movimientos</button>
        <button class="btn btn-primary" onclick="togglePanel('transferencias', this)">Transferencias</button>
        <button class="btn btn-primary" onclick="togglePanel('prestamos', this)">Préstamos</button>
        <button class="btn btn-primary" onclick="togglePanel('pagoPrestamos', this)">Pago de prestamos</button>
        <button class="btn btn-primary" onclick="togglePanel('datos', this)">Mis Datos</button>
    </div>
    
    <div id="movimientos" class="panel">
    <h4>Movimientos</h4>
    
    <form action="">
    		
    		 <div class="mb-3">
                <label for="cuentaOrigen" class="form-label">Cuenta Origen</label>
               
                <select class="form-select" id="cuentaOrigen"  onchange="mostrarSaldo()">
                    <option selected disabled>Seleccione una cuenta</option>
                    <option value="1">Caja de Ahorro - 12345678</option>
                    <option value="2">Cuenta Corriente - 87654321</option>
                </select>
            </div>
            
             <div id="saldoVisor" class="alert alert-info d-none">
		        Saldo disponible: <strong id="saldoValor">$0.00</strong>
		    </div>
    
    
	        <div class="col-11">	
		      <div class="table-responsive">
		          <table id="example" class="table table-striped">
		            <thead>
		                <tr>
		                    <th >Tipo de movimiento</th>
		                    <th >Fecha</th>
		                    <th >Monto</th>                                    
		                </tr>
		            </thead>
		            <tbody>
		                <tr>
		                    <td>Desposito</td>
		                    <td>01/01/2025</td>
		                    <td>$10.000</td>            
		                    <td>
		                                        
		                </tr>
		                <tr>
		                    <td>Pago de prestamo</td>
		                    <td>05/03/2025</td>
		                    <td>$30.000</td>            
		                                       
		                </tr>	
		                </tbody>
		           
		        </table>
		      </div>	
		    </div>
	 </form>  
	    
    </div>
    
    <div id="transferencias" class="panel">
        <h4>Transferencias</h4>
        <form>
            <div class="mb-3">
                <label for="cuentaOrigen" class="form-label">Cuenta Origen</label>
                
                <select class="form-select" id="cuentaOrigen">
                    <option selected disabled>Seleccione una cuenta</option>
                    <option value="1">Caja de Ahorro - 12345678</option>
                    <option value="2">Cuenta Corriente - 87654321</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="cbuDestino" class="form-label">CBU Destino</label>
                <input type="text" class="form-control" id="cbuDestino" placeholder="Ingrese CBU">
            </div>
            <div class="mb-3">
                <label for="montoTransferencia" class="form-label">Monto</label>
                <input type="number" class="form-control" id="montoTransferencia" placeholder="$XXXX">
            </div>
            <button type="submit" class="btn btn-success">Realizar Transferencia</button>
        </form>
        <hr>
        <h5>Historial de Transferencias</h5>
        <div class="col-11">	
	      <div class="table-responsive">
	          <table id="example" class="table table-striped">
	            <thead>
	                <tr>
	                    <th >Fecha</th>
	                    <th >Monto</th>
	                    <th >Cuenta destino</th>                                    
	                </tr>
	            </thead>
	            <tbody>
	                <tr>
	                    <td>01/01/2025</td>
	                    <td>$15.000</td>
	                    <td>***4352</td>            
	                    <td>
	                                        
	                </tr>
	                <tr>
	                    <td>05/03/2025</td>
	                    <td>$30.000</td>
	                    <td>***6672</td>             
	                                       
	                </tr>	
	                </tbody>
	           
	        </table>
	      </div>	
	    </div>
    </div>
    
    <div id="prestamos" class="panel">
        <h4>Solicitar Préstamo</h4>
        <form>
            <div class="mb-3">
                <label for="cuentaPrestamo" class="form-label">Cuenta Destino</label>
                <select class="form-select" id="cuentaPrestamo">
                    <option selected disabled>Seleccione una cuenta</option>
                    <option value="1">Caja de Ahorro - 12345678</option>
                    <option value="2">Cuenta Corriente - 87654321</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="montoPrestamo" class="form-label">Monto solicitado</label>
                <input type="number" class="form-control" id="montoPrestamo">
            </div>
            <div class="mb-3">
                <label for="cuotasPrestamo" class="form-label">Cantidad de cuotas</label>
                <select class="form-select" id="cuotasPrestamo">
                    <option>3</option>
                    <option>6</option>
                    <option>12</option>
                </select>
            </div>
            <button type="submit" class="btn btn-success">Pedir Préstamo</button>
        </form>
    </div>

    <div id="pagoPrestamos" class="panel">
        <h4>Pago de prestamos</h4>
        <form>
            <div class="mb-3">
                <label for="cuotaSeleccion" class="form-label">Seleccione Cuota</label>
                <select class="form-select" id="cuotaSeleccion">
                    <option value="1">Cuota 1 - $2.500 - Vence: 10/06/2025</option>
                    <option value="2">Cuota 2 - $2.500 - Vence: 10/07/2025</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="cuentaPago" class="form-label">Cuenta de Pago</label>
                <select class="form-select" id="cuentaPago">
                    <option value="1">Caja de Ahorro - 12345678</option>
                    <option value="2">Cuenta Corriente - 87654321</option>
                </select>
            </div>
            <button type="submit" class="btn btn-success">Pagar Cuota</button>
        </form>
    </div>

    <div id="datos" style="background-color: rgb(104, 109, 250)" class="panel">
	    <div id="datosPersonales" class="mt-3 container bg-white p-4 rounded">
	        <h4>Datos Personales</h4>
	        <ul>
	            <li>Nombre: Lionel Messi</li>
	            <li>DNI: 12345678</li>
	            <li>Correo: messi10@gmail.com</li>
	            <li>Dirección: Miami 123</li> 
	        </ul>
	    </div> 
	    
	    <div id="datosCuenta" class="mt-3 container bg-white p-4 rounded"> 
	    <h4>Mis cuentas</h4>     
	        <table id="example" class="table table-striped">
	            <thead>
	                <tr>
	                    <th >Numero de cuenta</th>
	                    <th >Tipo de cuenta</th>
	                    <th >Dinero en cuenta</th>
	                    <th >CBU</th>                                    
	                </tr>
	            </thead>
	            <tbody>
	                <tr>
	                    <td>2089</td>
	                    <td>Caja de ahorro</td>
	                    <td>$100.000</td>
	                    <td>0000007100007364498275</td>            
	                    <td>                    
	                </tr>
	                <tr>
	                    <td>2302</td>
	                    <td>Cuenta corriente</td>
	                    <td>$342.000</td>
	                    <td>0000007100007375638986</td>            
	                    <td>                    
	                </tr>     	
	                </tbody>
	        </table>
	    </div> 
	    
    </div>
    
</div>

<script>
    let panelActual = null;

    function togglePanel(panelId, button) {
        const panel = document.getElementById(panelId);

        if (panelActual && panelActual !== panel) {
            panelActual.style.display = "none";
            const botones = document.querySelectorAll(".btn.btn-primary");
            botones.forEach(b => b.classList.remove("active-button"));
        }

        if (panel.style.display === "block") {
            panel.style.display = "none";
            button.classList.remove("active-button");
            panelActual = null;
        } else {
            panel.style.display = "block";
            button.classList.add("active-button");
            panelActual = panel;
        }
    }
    
    function mostrarSaldo() {
        const select = document.getElementById("cuentaOrigen");
        const visor = document.getElementById("saldoVisor");
        const saldoTexto = document.getElementById("saldoValor");

        const saldos = {
            1: "$150.000",
            2: "$75.500"
        };

        const seleccion = select.value;

        if (saldos[seleccion]) {
            saldoTexto.textContent = saldos[seleccion];
            visor.classList.remove("d-none");
        } else {
            visor.classList.add("d-none");
        }
    }    
    
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
