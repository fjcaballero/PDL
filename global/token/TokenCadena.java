package global.token;

public class TokenCadena implements Token {
	private String cadena;

	
	public TokenCadena(String cad){
		this.cadena = cad;
	}
	@Override
	public String aString() {
		return "<" + this.toString() + "," + "\"" + cadena + "\"" + ">";
	}

}
