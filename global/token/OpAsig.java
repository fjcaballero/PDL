package global.token;

public class OpAsig implements Token {
	private int codigo;

	public OpAsig(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<" + this.toString() + "," + codigo + ">";
	}

}
