package global.token;

import lexico.AnalizadorLexico;

/* 
 * == / != / < / > / <= / >= / 
 * Implementados: <= / < /
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

	@Override
	public String tipo() {
		String resultado = "";
		if(codigo==AnalizadorLexico.MENOR)resultado="menor";
		else if(codigo==AnalizadorLexico.MENORIGUAL)resultado="menorIgual";
		else{
			System.out.println("Error, se ha generado un token erróneo: OP.RELACIONAL + "+ codigo);
			return null;
		}
		return resultado;
	}
}
