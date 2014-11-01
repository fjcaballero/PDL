package global.token;

public class TokenId implements Token {
	private int posicion;
	
	public TokenId(int pos){
		this.posicion = pos;
	}

	@Override
	public String aString() {
		return "<" + this.toString() + "," + posicion + ">";
	}

}
