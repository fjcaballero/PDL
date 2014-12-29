package global.token;

import lexico.AnalizadorLexico;

/* 
 * = / += / -= / *= / /= / %= / 
 * Implementados: = / += / -= /
 */
public class OpAsig implements Token {
	private int codigo;

	public OpAsig(int cod){
		this.codigo = cod;
	}

	@Override
	public String aString() {
		return "<OpAsig," + codigo + ">";
	}

	@Override
	public String tipo() {
		String resultado = "";
		if(codigo==AnalizadorLexico.IGUAL)resultado="igual";
		else if(codigo==AnalizadorLexico.MASIGUAL)resultado="masIgual";
		else if(codigo==AnalizadorLexico.MENOSIGUAL)resultado="menosIgual";
		else{
			System.out.println("Error, se ha generado un token erróneo: OP.ASIGNACION + "+ codigo);
			return null;
		}
		return resultado;
	}

}
