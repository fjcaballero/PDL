package global.token;

/* 
 * == / != / < / > / <= / >= / 
 * Implementados: <= /
 */
public class OpRel implements Token {
	private int codigo;

	public OpRel(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<OpRel," + codigo + ">";
	}
}
