package global;

import global.tabla.TablaSimbolos;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
		while(anLex.getCaracter() != null){
			anLex.leerToken();
		}
		//AnalizadorSintactico anSin = new AnalizadorSintactico();
		/*
		while(!(anLex.getCaracter().equals("$"))){

			 anSin.pedirToken(anLex);
			 
			 // anSin calcula el siguiente estado, actualizando sus variables
			 // y aplicando las anotaciones semánticas
			 
			 anLex.setTablaActual(anSin.getTablaActual());			 
		}
		
		*/
		PrintWriter writer;
		try {
			writer = new PrintWriter("tokens.txt", "UTF-8");
			System.out.println("Imprimiendo lista de tokens...");
			for(int i = 0; i < anLex.getTokensLeidos().size(); i++){
				System.out.println(anLex.getTokensLeidos().get(i).aString());
				writer.println(anLex.getTokensLeidos().get(i).aString());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
