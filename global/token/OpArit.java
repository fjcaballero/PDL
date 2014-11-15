package global.token;

public class OpArit implements Token {
	private int codigo;
	private final int SUMA = 1;
	private final int RESTA = 2;

	public OpArit(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<OpArit," + codigo + ">";
	}

}
