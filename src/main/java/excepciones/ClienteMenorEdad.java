package excepciones;

import java.io.IOException;

public class ClienteMenorEdad extends IOException {

	
	public ClienteMenorEdad() {			
	}	
	
	@Override
	public String getMessage() {		
		return "Menor de edad";
	}
	
}
