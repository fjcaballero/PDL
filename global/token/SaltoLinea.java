package global.token;

public class SaltoLinea implements Token {
	@Override
	public String aString() {
		return "<" + this.toString() + "," + "-" + ">";
	}

}
