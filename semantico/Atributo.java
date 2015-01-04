package semantico;

public class Atributo {
	private String simbolo;
	private String tipo;
	
	
	public Atributo(String simbolo, String tipo){
		this.simbolo = simbolo;
		this.tipo = tipo;
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
	
	
}
