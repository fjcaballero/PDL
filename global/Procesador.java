package global;

import global.tabla.ControladorTS;
import global.tabla.TablaSimbolos;
import global.token.Simbolo;
import global.token.Token;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import sintactico.AnalizadorSintactico;
import lexico.AnalizadorLexico;

public class Procesador {

	public static void main(String[] args) {
		String fichero = args[1];
		// Crear tabla de símbolos global en la pila
		ControladorTS.crearTS("Global");
		AnalizadorLexico anLex = new AnalizadorLexico(fichero);
		while(!anLex.getCaracter().equals("$")){
			anLex.getLog().println("Leyendo nuevo token");
			anLex.leerToken();
			anLex.getLog().println("-------------------");
		}
		anLex.getTokensLeidos().add(new Simbolo("$"));
		try {
			anLex.getFileReader().close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		AnalizadorSintactico anSin = new AnalizadorSintactico("resources/Decision.dat","resources/GoTo.dat");
		int codSintactico = 1;
		while(!(anLex.getCaracter().equals("$")) && codSintactico==1){

			 // anSin calcula el siguiente estado, actualizando sus variables
			 // y aplicando las anotaciones semánticas
			
			 codSintactico =  anSin.analizar(anLex.leerToken());
			 
		}
		
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
