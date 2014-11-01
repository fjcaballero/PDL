package global.token;

public class TokenEntero implements Token {
	private int valor;
	
	public TokenEntero(int valor){
		this.valor = valor;
	}
	@Override
	public String aString() {
		return "<" + this.toString() + "," + valor + ">";
	}

}
