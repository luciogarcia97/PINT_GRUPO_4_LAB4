package excepciones;

import java.io.IOException;

public class DniInvalido extends IOException {
	
	public DniInvalido() {			
	}	
	
	@Override
	public String getMessage() {		
		return "DNI invalido contiene Letras";
	}
	

}
