package global.token;

/* 
 * + / - / * / / / % /
 * Implementados: + / - /
 */
public class OpArit implements Token {
	private int codigo;

	public OpArit(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<OpArit," + codigo + ">";
	}

}
