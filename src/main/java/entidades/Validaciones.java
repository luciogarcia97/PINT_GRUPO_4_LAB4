package entidades;

import java.time.LocalDate;
import java.time.Period;

import excepciones.ClienteMenorEdad;
import excepciones.CuilInvalido;
import excepciones.DniInvalido;


public class Validaciones {
	
	public static void verificarDniInvalido(String dni) throws DniInvalido {				
			
			for(int i = 0 ; i < dni.length() ; i++) {
				
				if(!Character.isDigit(dni.charAt(i))) { 		
					
					DniInvalido invalido = new DniInvalido(); 
						throw invalido;	
				}	
			}
			
		}
	
	public static void verificarCuilInvalido(String dni) throws CuilInvalido {				
		
		for(int i = 0 ; i < dni.length() ; i++) {
			
			if(!Character.isDigit(dni.charAt(i))) { 		
				
				CuilInvalido invalido = new CuilInvalido(); 
					throw invalido;	
			}	
		}
		
	}	
	
	 public static void esMenorDeEdad(LocalDate fechaNacimiento) throws ClienteMenorEdad {
	        LocalDate hoy = LocalDate.now();
	        Period edad = Period.between(fechaNacimiento, hoy);
	        
	        if(edad.getYears() < 18) {
	        	ClienteMenorEdad clienteMenorEdad = new ClienteMenorEdad();
	        	throw clienteMenorEdad;	        	
	        }	        
	        
	    }

}
