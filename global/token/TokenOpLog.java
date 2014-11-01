package global.token;

public class TokenOpLog implements Token {
	private int codigo;

	public TokenOpLog(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<" + this.toString() + "," + codigo + ">";
	}
}
