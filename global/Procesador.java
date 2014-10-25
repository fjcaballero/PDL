package global;

import lexico.AnalizadorLexico;

public class Procesador {

	public static void main(String[] args) {
		String fichero = args[1];
		AnalizadorLexico anLex = new AnalizadorLexico(fichero);
		//AnalizadorSintactico anSin = new AnalizadorSintactico();
	}

}
