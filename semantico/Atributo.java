package semantico;

public class Atributo {
	private String simbolo;
	private String tipo;
	private String lexema;
	
	public Atributo(String simbolo, String tipo, String lexema){
		this.simbolo = simbolo;
		this.tipo = tipo;
		this.lexema = lexema;
	}
	
	public Atributo(String simbolo, String tipo){
		this.simbolo = simbolo;
		this.tipo = tipo;
		this.lexema = "-";
	}


	public String getSimbolo() {
		return simbolo;
	}


	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public String getLexema() {
		return lexema;
	}


	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	
	
}
