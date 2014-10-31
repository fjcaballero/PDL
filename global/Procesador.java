package global;

import lexico.AnalizadorLexico;

public class Procesador {

	public static void main(String[] args) {
		String fichero = args[1];
                Tablas tabla = new Tablas();
		AnalizadorLexico anLex = new AnalizadorLexico(fichero,tabla);
		//AnalizadorSintactico anSin = new AnalizadorSintactico();
	}

}
