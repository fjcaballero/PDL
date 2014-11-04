package global;

import global.tabla.TablaSimbolos;

import java.util.Stack;

import lexico.AnalizadorLexico;

public class Procesador {

	public static void main(String[] args) {
		String fichero = args[1];
		TablaSimbolos TSGlobal = new TablaSimbolos();
		Stack<TablaSimbolos> pilaTS = new Stack<TablaSimbolos>();
		// Crear tabla de símbolos global en la pila
		pilaTS.push(new TablaSimbolos());
		AnalizadorLexico anLex = new AnalizadorLexico(fichero/*,pilaTS*/);
		//AnalizadorSintactico anSin = new AnalizadorSintactico();
		/*
		while(!(anLex.getCaracter().equals("$"))){

			 anSin.pedirToken(anLex);
			 
			 // anSin calcula el siguiente estado, actualizando sus variables
			 // y aplicando las anotaciones semánticas
			 
			 anLex.setTablaActual(anSin.getTablaActual());			 
			 
			
		}
		*/
	}

}
