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

}
