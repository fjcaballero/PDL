package sintactico;

public class Regla {

	public int numero;
	public String parteIzq;
	public int nElementosDer;

	public Regla(int numero, String parteIzq, int nElementosDer){
		this.numero = numero;
		this.parteIzq = parteIzq;
		this.nElementosDer = nElementosDer;
	}

	public String toString(){
		return numero + "," + parteIzq + "," + nElementosDer;
	}
}
