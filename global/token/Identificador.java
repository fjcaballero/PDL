package global.token;

public class Identificador implements Token {
	private int posicion;
	
	public Identificador(int pos){
		this.posicion = pos;
	}

	@Override
	public String aString() {
		return "<Id," + posicion + ">";
	}

}
