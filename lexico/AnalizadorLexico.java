package lexico;

import global.tabla.*;
import global.token.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AnalizadorLexico {
	private ArrayList<Token> tokensLeidos;
	private FileReader fileReader;
	private BufferedReader bufferReader;
	private String caracter;
	private int lineaActual;
	private int columnaActual;
	
	// Constantes

	// Aritmeticos
	private final int SUMA = 1;
	private final int RESTA = 2;

	// Asignacion
	private final int IGUAL = 1;
	private final int MASIGUAL = 2;
	private final int MENOSIGUAL = 3;

	// Logicos
	private final int IGUALIGUAL = 1;
	private final int MENORIGUAL = 2;
	private final int MENOR = 3;


	public AnalizadorLexico(String ficheroALeer/*,Stack<TablaSimbolos> pilaTS*/){
		this.lineaActual = 0;
		this.columnaActual = 0;
		//this.tablas = pilaTS;
		//this.tablaActual = pilaTS.peek();
		this.tokensLeidos = new ArrayList<Token>();
		this.caracter = "";
		try {
			this.fileReader = new FileReader("\""+ficheroALeer+"\"");
			this.bufferReader = new BufferedReader(fileReader);
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
				else if(caracter.equals("=")){
					token = new OpAsig(IGUAL);
				}
				//------------------------------------------
				else if(caracter.equals("(")){
					token = new Simbolo("(");
					leido = true;
				}
				else if(caracter.equals(")")){
					token = new Simbolo(")");
					leido = true;
				}
				else if(caracter.equals("{")){
					token = new Simbolo("{");
					leido = true;
				}
				else if(caracter.equals("}")){
					token = new Simbolo("}");
					leido = true;
				}
				else if(caracter.equals(":")){
					token = new Simbolo(":");
					leido = true;
				}
				else if(caracter.equals(",")){
					token = new Simbolo(",");
					leido = true;
				}
				else if(caracter.equals(".")){
					token = new Simbolo(".");
					leido = true;
				}
				else if(caracter.equals("\r\n")){
					token = new Simbolo("sl");
					leido = true;
				}
				else if(caracter.equals("$")){
					token = new Simbolo("$");
					leido = true;
				}
				else{
					// Error
					System.out.println("Símbolo inesperado en línea" + lineaActual + "columna" + columnaActual);
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
				// generarToken(TokenEntero,valor) { token = new TokenEntero(valor) }
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
				// generarToken(TokenCadena,lexema) { token = new TokenCadena(lexema) }
				token = new Cadena(lexema);
				leido = true;
				break;
			case 7:
				if(caracter.equals("*")){
					estado = 8;
				}
				else{
					// Error
					System.out.println("Símbolo inesperado en línea" + lineaActual + "columna" + columnaActual);
					return null;
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
					estado = 14;
				}
				else{
					estado = 15;
				}
				break;
			case 14:
				token = new OpLog(IGUALIGUAL);
				leido = true;
				break;
			case 15:
				token = new OpAsig(IGUAL);
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
				token = new OpLog(MENORIGUAL);
				leido = true;
				break;
			case 18:
				token = new OpLog(MENOR);
				leido = true;
				break;
			default:
				// Error
				System.out.println("Símbolo inesperado en línea" + lineaActual + "columna" + columnaActual);
				return null;
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
			car = bufferReader.read();
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
		this.columnaActual++;
		if(caracter.equals("\n") || caracter.equals("\r") || caracter.equals("\r\n")){
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

}
