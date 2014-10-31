package lexico;

import global.Tablas;
import global.Token;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AnalizadorLexico {
	private ArrayList<Token> tokensLeidos;
	private FileReader fileReader;
        private Tablas tabla;
        private boolean carLeido;
	private String caracter;

	public AnalizadorLexico(String ficheroALeer,Tablas tabla){
                this.tabla = tabla;
		this.tokensLeidos = new ArrayList<Token>();
                carLeido = false;
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
	 * M�todo que identifica un token del fichero
	 * @return Devuelve un token identificado del fichero
	 */
        
	public Token leerToken(){
		Token token = null;
		String lexema = "";
                int valor = 0;
		int estado = 0;
		Caracter isChar = new Caracter(); //objeto para comprobar patrones
		boolean leido = false;
		while(!leido){
			// Ejecutar automata
			switch(estado){
			case 0:
                                //Si ya se ha leido el caracter antes no es necesario volver a leerlo, sino se leerá.
                                if(!carLeido){
                                    caracter = leerCaracter();
                                }
                                carLeido = false;
                                
				if(isChar.esLetra(caracter)){
					estado = 1;
					// Acciones semanticas
					lexema = lexema + caracter;
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
                                caracter = leerCaracter();
                                if(isChar.esLetra(caracter)||isChar.esDigito(caracter)||caracter.equals("_")){
                                    lexema += caracter;
                                }
                                else{
                                    //el caracter que se ha leido se va a perder
                                    carLeido = true;
                                    estado = 2;
                                }
				break;
                        case 2:
                                if(tabla.esReservada(lexema)){
                                   //token = Preservada, lexema;
                                }
                                else{
                                    int posTS = tabla.estaTS(lexema);
                                    if(posTS == -1){
                                        posTS = tabla.añadirTS(lexema);
                                    }
                                    //token = id, posTS;   
                                }
                                leido = true;
                                break;
                        case 3:
                                caracter = leerCaracter();
                                if(isChar.esDigito(caracter)){
                                    lexema += caracter;
                                }
                                else{
                                    carLeido = true;
                                    estado = 4;
                                }
                                break;
                        case 4:
                                valor = Integer.parseInt(lexema);
                                //token = entero, valor;
                                leido = true;
                                break;
                        case 5:
                                caracter = leerCaracter();
                                if(!caracter.equals("\"")){
                                    lexema += caracter;
                                }
                                else{
                                    //el caracter no se pierde porque es un "
                                    estado = 6;
                                }
                                break;
                        case 6:
                                //token = cadena, lexema;
                                leido = true;
                                break;
                        case 7:
                                caracter = leerCaracter();
                                if(caracter.equals("*")){
                                    estado = 8;
                                }
                                else{
                                    //ERROR
                                    //Ignorar el error: estado = 0;
                                }
                                break;
                        case 8:
                                caracter = leerCaracter();
                                if(caracter.equals("*")){
                                    estado = 9;
                                }
                                break;
                        case 9:
                                caracter = leerCaracter();
                                if(caracter.equals("/")){
                                    estado = 0;
                                }
                                else{
                                    estado = 8;
                                }
                                break;
                        case 10:
                                caracter = leerCaracter();
                                if(caracter.equals("=")){
                                    lexema += caracter;
                                    estado = 12;
                                }
                                else{
                                    //el caracter que se ha leido se va a perder
                                    carLeido = true;
                                    estado = 11;
                                }
                                break;
                        case 11:
                                //token = opArtim, +;
                                leido = true;
                                break;
                        case 12:
                                //token = opAsign +=;
                                leido = true;
                                break;
			}
		}
		tokensLeidos.add(token);
		return token;
	}

	/**
	 * M�todo que lee un car�cter como un String
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
