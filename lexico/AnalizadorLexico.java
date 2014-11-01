package lexico;

import global.Tablas;
import global.token.Token;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AnalizadorLexico {
	private ArrayList<Token> tokensLeidos;
	private FileReader fileReader;
	private Tablas tabla;
	private String caracter;

	public AnalizadorLexico(String ficheroALeer,Tablas tabla){
		this.tabla = tabla;
		this.tokensLeidos = new ArrayList<Token>();
		caracter = "";
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
		Caracter isChar = new Caracter(); //objeto para comprobar patrones
		String lexema = "";
		int valor = 0;
		int estado = 0;
		boolean leido = false;
		
		// Se entra al automata (estado 0) con un caracter leido
		leerCaracter();
		
		while(!leido){
			// Ejecutar automata
			if(estado != 0){
				leerCaracter();
			}
			switch(estado){
			case 0:
				if (isChar.esDelimitador(caracter)){
					leerCaracter();
				}
				else if(isChar.esLetra(caracter)){
					estado = 1;
					// Acciones semanticas
					lexema += caracter;
				}
				else if(isChar.esDigito(caracter)){
					estado = 3;
					lexema += caracter;
				}
				else if(caracter.equals("\"")){
					estado = 5;
				}
				else if(caracter.equals("/")){
					estado = 7;
				}
				else if(caracter.equals("+")){
					estado = 10;
					lexema += caracter;
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
				if(isChar.esLetra(caracter)||isChar.esDigito(caracter)||caracter.equals("_")){
					lexema += caracter;
				}
				else{
					estado = 2;
				}
				break;
			case 2:
				/*
				 * pos = tsActual.buscar(lexema)
				 * if (pos < num_palabrasReservadas && pos > 0)
				 *  palabra reservada -> generarToken(TokenPalRes,pos) { token = new TokenPalRes(pos) }
				 * else
				 * 	identificador ->
				 * 		if (pos < 0)
				 * 			posTS = tsActual.insertar(lexema)
				 * 		else
				 * 			posTS = pos
				 * 		generarToken(TokenId,posTS) { token = new TokenId(posTS) }
				 */
				leido = true;
				break;
			case 3:
				if(isChar.esDigito(caracter)){
					lexema += caracter;
				}
				else{
					estado = 4;
				}
				break;
			case 4:
				valor = Integer.parseInt(lexema);
				// generarToken(TokenEntero,valor) { token = new TokenEntero(valor) }
				leido = true;
				break;
			case 5:
				if(!caracter.equals("\"")){
					lexema += caracter;
				}
				else{
					estado = 6;
				}
				break;
			case 6:
				// generarToken(TokenCadena,lexema) { token = new TokenCadena(lexema) }
				leido = true;
				break;
			case 7:
				if(caracter.equals("*")){
					estado = 8;
				}
				else{
					//ERROR
					//Ignorar el error: estado = 0;
				}
				break;
			case 8:
				if(caracter.equals("*")){
					estado = 9;
				}
				break;
			case 9:
				if(caracter.equals("/")){
					leerCaracter();
					estado = 0;
				}
				else{
					estado = 8;
				}
				break;
			case 10:
				if(caracter.equals("=")){
					lexema += caracter;
					estado = 12;
				}
				else{
					estado = 11;
				}
				break;
			case 11:
				// generarToken(TokenOpArit,cod) { token = new TokenOpArit( codigo del "+") }
				leido = true;
				break;
			case 12:
				// generarToken(TokenOpAsig,cod) { token = new TokenOpAsig( codigo del "+=") }
				leido = true;
				break;
			}
		}
		tokensLeidos.add(token);
		return token;
	}

	/**
	 * Método que lee un carácter como un String
	 */
	private void leerCaracter(){
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
		this.caracter = caracter;
	}

}
