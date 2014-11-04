package global.token;

public class OpLog implements Token {
	private int codigo;

	public OpLog(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<" + this.toString() + "," + codigo + ">";
	}
}
