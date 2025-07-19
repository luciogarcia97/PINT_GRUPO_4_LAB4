package excepciones;

import java.io.IOException;

public class CuilInvalido extends IOException {
	
	
	public CuilInvalido() {			
	}	
	
	@Override
	public String getMessage() {		
		return "CUIL invalido contiene Letras";
	}
	
	

}
