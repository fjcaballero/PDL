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
	private AnalizadorLexico anLex;
	private AnalizadorSintactico anSin;

	public Procesador(String fichero){
		anLex = new AnalizadorLexico(fichero);
		anSin = new AnalizadorSintactico(anLex,"resources/"
				+ "Decision.dat","resources/GoTo.dat","resources/reglas.txt");
	}
	
	public static void main(String[] args) {
		Procesador procesador = new Procesador(args[1]);
		// Crear tabla de símbolos global en la pila
		ControladorTS.crearTS("Global");
		int codSintactico = 1;
		while(codSintactico==1){
			codSintactico =  procesador.anSin.analizar();
		}
		try {
			procesador.anLex.getFileReader().close();
			procesador.listarTokens();
			procesador.listarParse();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * <i><b>listarParse()</b></i>
	 * <br>
	 * <br>
	 * Genera un fichero parse.txt y lista el conjunto de reglas aplicadas
	 */
	private void listarParse(){
		// Listar parse
		PrintWriter writerParse;
		try {
			writerParse = new PrintWriter("resources\\parse.txt", "UTF-8");
			// borrar linea
			System.out.println("Imprimiendo parse...");
			//
			Iterator<Integer> itParse = anSin.getParse().iterator();
			String parse = "Ascendente";
			while(itParse.hasNext()){
				int regla = itParse.next();
				parse = parse + " " + regla;
				// borrar linea
				System.out.println(regla);
				//
			}
			writerParse.println(parse);
			writerParse.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <i><b>listarTokens()</b></i>
	 * <br>
	 * <br>
	 * Genera un fichero tokens.txt y lista el conjunto de tokens reconocidos
	 */
	private void listarTokens(){
		// Listar tokens
		PrintWriter writer;
		try {
			writer = new PrintWriter("resources\\tokens.txt", "UTF-8");
			// borrar linea
			System.out.println("Imprimiendo lista de tokens...");
			//
			Iterator<Token> it = anLex.getTokensLeidos().iterator();
			while(it.hasNext()){
				Token token = it.next();
				// borrar linea
				System.out.println(token.aString());
				//
				writer.println(token.aString());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	

}
