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
		AnalizadorSintactico anSin = new AnalizadorSintactico("resources/"
				+ "Decision.dat","resources/GoTo.dat","resources/reglas.txt");
		int codSintactico = 1;
		while(!anLex.getCaracter().equals("$") && codSintactico==1){
			anLex.getLog().println("Leyendo nuevo token");
			Token sigToken = anLex.leerToken();
			System.out.println(sigToken.aString());
			codSintactico =  anSin.analizar(sigToken);
			anLex.getLog().println("-------------------");
		}
		anLex.getTokensLeidos().add(new Simbolo("$"));
		try {
			anLex.getFileReader().close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// Listar parse
		PrintWriter writerParse;
		try {
			writerParse = new PrintWriter("resources\\parse.txt", "UTF-8");
			System.out.println("Imprimiendo parse...");
			Iterator<Integer> itParse = anSin.getParse().iterator();
			String parse = "Ascendente";
			while(itParse.hasNext()){
				int regla = itParse.next();
				parse = parse + " " + regla;
				System.out.println(regla);
			}
			writerParse.println(parse);
			writerParse.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
