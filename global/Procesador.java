package global;

import global.tabla.TablaSimbolos;
import global.token.Token;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
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
		while(!anLex.getCaracter().equals("$")){
			anLex.leerToken();
		}
		try {
			anLex.getFileReader().close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		
		// Listar tokens
		PrintWriter writer;
		try {
			writer = new PrintWriter("resources\\tokens.txt", "UTF-8");
			System.out.println("Imprimiendo lista de tokens...");
			Iterator<Token> it = anLex.getTokensLeidos().iterator();
			while(it.hasNext()){
				Token token = it.next();
				System.out.println(token.aString());
				writer.println(token.aString());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
