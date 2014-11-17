package lexico;

import global.tabla.*;
import global.token.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AnalizadorLexico {
	private ArrayList<Token> tokensLeidos;
	private FileReader fileReader;
	private BufferedReader bufferReader;
	private String caracter;
	private int lineaActual;
	private int columnaActual;
	private boolean flagSL;
	private PrintWriter log;

	// Constantes

	// Aritmeticos
	private final int SUMA = 1;
	private final int RESTA = 2;

	// Asignacion
	private final int IGUAL = 1;
	private final int MASIGUAL = 2;
	private final int MENOSIGUAL = 3;

	// Logicos
	private final int NEGACION = 1;
        
	// Relacion
	private final int MENORIGUAL = 2;
	private final int MENOR = 3;

	public AnalizadorLexico(String ficheroALeer){
		this.lineaActual = 0;
		this.columnaActual = 0;
		this.tokensLeidos = new ArrayList<Token>();
		this.caracter = "";
		this.flagSL = false;
		try {
			String filePath = new File("").getAbsolutePath();
			filePath = filePath.concat("\\resources\\" + ficheroALeer);
			System.out.println(filePath);
			this.fileReader = new FileReader(filePath);
			this.bufferReader = new BufferedReader(fileReader);
			// Se entra al automata (estado 0) con un caracter leido
			leerCaracter();
		} catch (FileNotFoundException e) {
			System.out.println("Error al leer el fichero");
			e.printStackTrace();
		}
		try {
			this.log = new PrintWriter("lexico\\log_lexico.txt","UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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

		while(!leido){
			log.println("Lexema: "+lexema+"<");
			// Ejecutar automata
			if(estado != 0 
					&& estado != 2
					&& estado != 4){
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
				else if(caracter.equals("-")){
					estado = 13;
					lexema += caracter;
				}
				else if(caracter.equals("<")){
					estado = 16;
					lexema += caracter;
				}
				//------------------------------------------
				else if(caracter.equals("(")){
					token = new Simbolo("(");
					lexema = "(";
					leido = true;
					log.println("Lexema: "+lexema+"<");
					leerCaracter();
				}
				else if(caracter.equals(")")){
					token = new Simbolo(")");
					lexema = ")";
					leido = true;
					log.println("Lexema: "+lexema+"<");
					leerCaracter();
				}
				else if(caracter.equals("{")){
					token = new Simbolo("{");
					lexema = "{";
					leido = true;
					log.println("Lexema: "+lexema+"<");
					leerCaracter();
				}
				else if(caracter.equals("}")){
					token = new Simbolo("}");
					lexema = "}";
					leido = true;
					log.println("Lexema: "+lexema+"<");
					leerCaracter();
				}
				else if(caracter.equals(":")){
					token = new Simbolo(":");
					lexema = ":";
					leido = true;
					log.println("Lexema: "+lexema+"<");
					leerCaracter();
				}
				else if(caracter.equals(",")){
					token = new Simbolo(",");
					lexema = ",";
					leido = true;
					log.println("Lexema: "+lexema+"<");
					leerCaracter();
				}
				else if(caracter.equals(".")){
					token = new Simbolo(".");
					lexema = ".";
					leido = true;
					log.println("Lexema: "+lexema+"<");
					leerCaracter();
				}
				else if(caracter.equals("sl")){
					token = new Simbolo("sl");
					lexema = "sl";
					leido = true;
					log.println("Lexema: "+lexema+"<");
					leerCaracter();
				}
				else if(caracter.equals("flagSL")){
					leerCaracter();
				}
				else if(caracter.equals("$")){
					token = new Simbolo("$");
					lexema = "$";
					leido = true;
					log.println("Lexema: "+lexema+"<");
				}
                                else if(caracter.equals("=")){
					token = new OpAsig(IGUAL);
					lexema = "=";
					leido = true;
					log.println("Lexema: "+lexema+"<");
					leerCaracter();
				}
                                else if(caracter.equals("!")){
					token = new OpLog(NEGACION);
					lexema = "!";
					leido = true;
					log.println("Lexema: "+lexema+"<");
					leerCaracter();
				}
				else{
					// Error
					System.out.println("Símbolo inesperado en la línea " + lineaActual + ", columna " + columnaActual);
					log.println("Símbolo inesperado en línea " + lineaActual + ", columna " + columnaActual);
					leerCaracter();
					leido = true;
					return null;
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
				/* Si es palabra reservada se genera el token PReservada, lexema */
				if(ControladorTS.esReservada(lexema)){
					token = new PalRes(lexema);
				}
				/* Si es un id se busca en la TS y si no esta se añade
				 * y se genera el token Id, posTS
				 */
				else{
					int pos = ControladorTS.buscarEnTS(lexema);
					if(pos < 0){
						pos = ControladorTS.insertarEnTS(lexema);
					}
					token = new Identificador(pos);
				}
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
				token = new Entero(valor);
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
				token = new Cadena(lexema);
				leido = true;
				break;
			case 7:
				if(caracter.equals("*")){
					estado = 8;
				}
				else{
					// Error
					System.out.println("Símbolo inesperado en la línea " + lineaActual + ", columna " + columnaActual);
					log.println("Símbolo inesperado en línea " + lineaActual + ", columna " + columnaActual);
					leerCaracter();
					token = null;
					leido = true;
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
				token = new OpArit(SUMA);
				leido = true;
				break;
			case 12:
				token = new OpAsig(MASIGUAL);
				leido = true;
				break;
			case 13:
				if(caracter.equals("=")){
					lexema+="=";
					estado = 15;
				}
				else{
					estado = 14;
				}
				break;
			case 14:
				token = new OpArit(RESTA);
				leido = true;
				break;
			case 15:
				token = new OpAsig(MENOSIGUAL);
				leido = true;
				break;
			case 16:
				if(caracter.equals("=")){
					lexema+="=";
					estado = 17;
				}
				else{
					estado = 18;
				}
				break;
			case 17:
				token = new OpRel(MENORIGUAL);
				leido = true;
				break;
			case 18:
				token = new OpRel(MENOR);
				leido = true;
				break;
			default:
				// Error
				System.out.println("Símbolo inesperado en la línea " + lineaActual + ", columna " + columnaActual);
				log.println("Símbolo inesperado en línea " + lineaActual + ", columna " + columnaActual);
				leerCaracter();
				leido = true;
				token = null;
			}
		}
		if(token != null){
			tokensLeidos.add(token);
			log.println("Token leido:" + token.aString());
		}
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
			car = this.bufferReader.read();
			if(car == 13){
				this.flagSL = true;
				caracter = "flagSL";
			}
			else if (car == 10 && flagSL){
				caracter = "sl";
				flagSL = false;
			}
			else if(car != -1){
				aux = (char) car;
				caracter = Character.toString(aux);
			}
			else{
				caracter = "$";
				log.println("Token leido:"+ new Simbolo("$").aString());
				log.close();
			}
		} catch (IOException e) {
			System.out.println("Error al leer el fichero");
			e.printStackTrace();
		}
		this.columnaActual++;
		if(caracter.equals("sl")){
			lineaActual++;
			columnaActual = 0;
		}
		this.caracter = caracter;
	}

	public ArrayList<Token> getTokensLeidos() {
		return tokensLeidos;
	}

	public int getLineaActual() {
		return lineaActual;
	}

	public int getColumnaActual() {
		return columnaActual;
	}

	public String getCaracter() {
		return caracter;
	}

	public BufferedReader getBufferReader() {
		return bufferReader;
	}

	public FileReader getFileReader() {
		return fileReader;
	}
	public PrintWriter getLog() {
		return log;
	}

}
