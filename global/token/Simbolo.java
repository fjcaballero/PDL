package global.token;

public class Simbolo implements Token {
	private String lexema;
	
	public Simbolo(String simbolo){
		this.lexema = simbolo;
	}
	@Override
	public String aString() {
		return "<" + lexema + ",->";
	}
	@Override
	public String tipo() {
		String resultado = "";
		// ( ) { } : . , sl $
		if(lexema.equals("("))resultado="abrePar";
		else if(lexema.equals(")"))resultado="cierraPar";
		else if(lexema.equals("{"))resultado="abreLlave";
		else if(lexema.equals("}"))resultado="cierraLlave";
		else if(lexema.equals(":"))resultado="dosPuntos";
		else if(lexema.equals("."))resultado="punto";
		else if(lexema.equals(","))resultado="coma";
		else if(lexema.equals("sl"))resultado="sl";
		else if(lexema.equals("$"))resultado="$";
		else{
			System.out.println("Error, se ha generado un token erróneo: SIMBOLO + "+ lexema);
			return null;
		}
		return resultado;
	}

}
