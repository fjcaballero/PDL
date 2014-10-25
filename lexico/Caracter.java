package lexico;

import java.util.regex.*;

public class Caracter {
	private Pattern letra = Pattern.compile("[a-zA-z]");
	private Pattern digito = Pattern.compile("[0-9]");
	
	public Caracter(){
		
	}
	
	public boolean esLetra(String caracter){
		Matcher isLetter = this.letra.matcher(caracter);
		return isLetter.matches();
	}
	
	public boolean esDigito(String caracter){
		Matcher isDigit = this.digito.matcher(caracter);
		return isDigit.matches();
	}
}
