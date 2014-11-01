package global.token;

public class TokenPalRes implements Token {
	private int codigo;
	
	public TokenPalRes(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<" + this.toString() + "," + codigo + ">";
	}
}
