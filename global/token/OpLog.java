package global.token;

/* 
 * && / || / ! /
 * Implementados: ! /
 */
public class OpLog implements Token {
	private int codigo;

	public OpLog(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<OpLog," + codigo + ">";
	}
}
