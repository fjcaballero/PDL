package global.token;

import lexico.AnalizadorLexico;

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

	@Override
	public String tipo() {
		String resultado = "";
		if(codigo==AnalizadorLexico.SUMA)resultado="mas";
		else if(codigo==AnalizadorLexico.RESTA)resultado="menos";
		else{
			System.out.println("Error, se ha generado un token erróneo: OP.ARITMETICO + "+ codigo);
			return null;
		}
		return resultado;
	}

}
