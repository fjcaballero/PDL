package lexico;

import global.Token;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AnalizadorLexico {
	private ArrayList<Token> tokensLeidos;
	private FileReader fileReader;


	public AnalizadorLexico(String ficheroALeer){
		this.tokensLeidos = new ArrayList<Token>();
		try {
			this.fileReader = new FileReader("\""+ficheroALeer+"\"");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al leer el fichero");
			e.printStackTrace();
		}
	}

	/**
	 * Método que identifica un token del fichero
	 * @return Devuelve un token identificado del fichero
	 */
	public Token leerToken(){
		Token token = null;
		String lexema = "";
		int estado = 0;
		Caracter isChar = new Caracter(); //objeto para comprobar patrones
		boolean leido = false;
		String caracter;
		while(!leido){
			// Ejecutar automata
			switch(estado){
			case 0:
				//Se entra con un caracter leido
				caracter = leerCaracter();
				if(isChar.esLetra(caracter)){
					estado = 1;
					// Acciones semanticas
					lexema = lexema + caracter;
				}
				else if(isChar.esDigito(caracter)){
					estado = 3;
				}
				else if(caracter.equals("\"")){
					estado = 5;
				}
				else if(caracter.equals("/")){
					estado = 7;
				}
				else if(caracter.equals("+")){
					estado = 10;
				}
				else if(caracter.equals("=")){
					estado = 13;
				}
				else if(caracter.equals("<")){
					estado = 16;
				}
				else if(caracter.equals("(")){
					estado = 19;
				}
				else if(caracter.equals(")")){
					estado = 20;
				}
				else if(caracter.equals("{")){
					estado = 21;
				}
				else if(caracter.equals("}")){
					estado = 22;
				}
				else if(caracter.equals(":")){
					estado = 23;
				}
				else if(caracter.equals(",")){
					estado = 24;
				}
				else if(caracter.equals(".")){
					estado = 25;
				}
				else if(caracter.equals("\r\n")){
					estado = 26;
				}
				else if(caracter.equals("$")){
					estado = 27;
				}
				else{
					leido = true;
				}
				break;
			case 1:
				break;
			}
		}
		tokensLeidos.add(token);
		return token;
	}

	/**
	 * Método que lee un carácter como un String
	 * @return Devuelve el caracter leido como un String
	 * 
	 */
	private String leerCaracter(){
		int car = 0;
		char aux;
		String caracter = null;
		try {
			car = fileReader.read();
			if(car != -1){
				aux = (char) car;
				caracter = Character.toString(aux);
			}
			else{
				caracter = "$";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al leer el fichero");
			e.printStackTrace();
		}
		return caracter;
	}

}
