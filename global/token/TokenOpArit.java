package global.token;

public class TokenOpArit implements Token {
	private int codigo;

	public TokenOpArit(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<" + this.toString() + "," + codigo + ">";
	}

}
