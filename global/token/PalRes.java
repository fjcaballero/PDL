package global.token;

public class PalRes implements Token {
	private int codigo;
	
	public PalRes(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<" + this.toString() + "," + codigo + ">";
	}
}
