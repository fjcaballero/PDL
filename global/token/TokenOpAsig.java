package global.token;

public class TokenOpAsig implements Token {
	private int codigo;

	public TokenOpAsig(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<" + this.toString() + "," + codigo + ">";
	}

}
