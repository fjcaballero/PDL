package sintactico;

import global.token.Token;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import lexico.AnalizadorLexico;

public class AnalizadorSintactico {

	private Stack<String> pila;
	private Token tokenEntrada;
	private AnalizadorLexico anLex;
	private ArrayList<String> tAccion;
	private ArrayList<String> tGoTo;
	private HashMap<String,HashMap<String,String>> tablaAccion;
	private HashMap<String,HashMap<String,String>> tablaGoTo;
	private ArrayList<Regla> listaReglas;
	private ArrayList<Integer> parse;
	private boolean flagSL;


	/**<i><b>AnalizadorSintactico()</b></i>
	 * <br>
	 * <br>
	 * Constructor del Analizador Sintactico
	 * <br>
	 * <br>
	 * Recibe como parametros el analizador lexico y
	 * la ubicacion de los ficheros de la tabla de Accion y de GoTo
	 * @param anLex
	 * @param ficheroAccion
	 * @param ficheroGoTo
	 */
	public AnalizadorSintactico(AnalizadorLexico anLex, String ficheroAccion, String ficheroGoTo, String ficheroReglas){//Constructor
		this.anLex = anLex;
		this.tokenEntrada = anLex.leerToken();
		this.flagSL = false;
		this.tablaAccion = new HashMap<String,HashMap<String,String>>();
		this.tablaGoTo = new HashMap<String,HashMap<String,String>>();

		//Inicializar pila de estados
		pila = new Stack<String>();
		pila.push("0");

		//Inicializar parse
		parse = new ArrayList<Integer>();

		//Leer Tabla Accion
		try {
			ObjectInputStream oisAccion = new ObjectInputStream(new FileInputStream(ficheroAccion));
			tAccion = (ArrayList<String>)oisAccion.readObject();
			oisAccion.close();
			cargarTablaAccion();
		} catch (Exception e) {
			System.out.println("Error al leer el fichero: "+ficheroAccion);
			e.printStackTrace();
		}

		//Leer Tabla GoTo
		try {
			ObjectInputStream oisGoTo = new ObjectInputStream(new FileInputStream(ficheroGoTo));
			tGoTo = (ArrayList<String>)oisGoTo.readObject();
			cargarTablaGoTo();
			oisGoTo.close();
		} catch (Exception e) {
			System.out.println("Error al leer el fichero: "+ficheroGoTo);
			e.printStackTrace();
		}

		//Leer Fichero Reglas
		listaReglas = new ArrayList<Regla>();
		cargarListaReglas(ficheroReglas);

	}//Constructor

	/**
	 * <i><b>cargarTablaAccion()</b></i>
	 * <br>
	 * <br>
	 * Método que carga el contenido del fichero Decision.dat en un Hashmap
	 */
	public void cargarTablaAccion(){
		String estado = "";
		String accion = "";
		String token = "";
		Iterator<String> it = this.tAccion.iterator();
		while (it.hasNext()){
			estado = it.next();
			token = it.next();
			accion = it.next();
			if(tablaAccion.get(token) == null){
				tablaAccion.put(token, new HashMap<String,String>());
			}
			this.tablaAccion.get(token).put(estado, accion);
		}
	}

	/**
	 * <i><b>cargarTablaGoTo()</b></i>
	 * <br>
	 * <br>
	 * Método que carga el contenido del fichero GoTo.dat en un Hashmap
	 */
	public void cargarTablaGoTo(){
		String estado = "";
		String accion = "";
		String noterminal = "";
		Iterator<String> it = this.tGoTo.iterator();
		while (it.hasNext()){
			estado = it.next();
			noterminal = it.next();
			accion = it.next();
			if(tablaGoTo.get(noterminal) == null){
				tablaGoTo.put(noterminal, new HashMap<String,String>());
			}
			this.tablaGoTo.get(noterminal).put(estado, accion);
		}
	}

	/** 
	 * <i><b>buscarTabla</b></i
	 * <br>
	 * <br>
	 * Metodo auxiliar para buscar elementos en las tablas de Accion o GoTo
	 * <br>
	 * Recibe como parametros:
	 * <br>
	 * 	- la fila a buscar <elemento1> (que simboliza el estado en ambas tablas)
	 * <br>
	 * 	- la columna a buscar <elemento2> (que simboliza el token en la tabla de Accion o el no terminal en la tabla de GoTo)
	 * <br>
	 * 	- la tabla en la que se debe buscar
	 * <br>
	 * @param estado
	 * @param columna
	 * @param tabla
	 * @return Devuelve el elemento correspondiente de la tabla si existe y un String vacio en otro caso
	 */
	private String buscarTabla(String estado, String columna, HashMap<String,HashMap<String,String>> tabla){//buscarTabla
		if(tabla.get(columna) != null && tabla.get(columna).get(estado) != null){
			return tabla.get(columna).get(estado);
		}
		else
			return null;
	}//buscarTabla

	/** 
	 * <i><b>analizar()</b></i>
	 * <br>
	 * <br>
	 * Metodo que simula las iteraciones del automata del analizador sintactico
	 * <br>
	 * <br>
	 * Recibe como parametro el token correspondiente de la sentencia que se esta analizando
	 * @return 1 continuar, 0 aceptar, -1 error
	 */
	public int analizar(){
		int resultado = 1; //En proceso
		String estado = pila.peek();
		System.out.println("Cima de la pila: " + estado);
		System.out.println("Token: "+ tokenEntrada.aString());
		if(tokenEntrada.tipo().equals("sl")){
			flagSL = true;
		}
		Token entrada = null;
		String entradaTipo = "";
		String accion = buscarTabla(estado,tokenEntrada.tipo(),tablaAccion);
		if(accion != null){
			System.out.println("Accion: "+ accion);
			if(accion.substring(0,1).equals("d")){//Desplazar
				pila.push(accion.substring(1,accion.length()).trim());
				if(flagSL){
					while(flagSL && !entradaTipo.equals("$")){
						entrada = anLex.leerToken();
						entradaTipo = entrada.tipo();
						if(!entrada.tipo().equals("sl")){
							tokenEntrada = entrada;
							flagSL = false;
						}
					}
				}
				else
					tokenEntrada = anLex.leerToken();
			}
			else if(accion.substring(0,1).equals("r")){//Reducir
				int numRegla = Integer.valueOf(accion.substring(1,accion.length()).trim());
				parse.add(numRegla);//Agregamos el numero de regla al parse
				Regla regla = listaReglas.get(numRegla-1);
				if(pila.size() < regla.nElementosDer){
					resultado = -1;
				}
				else{
					for(int i=0; i<regla.nElementosDer; i++){//Sacamos de la pila n estados
						pila.pop();
					}
					estado = buscarTabla(pila.peek(), regla.parteIzq, tablaGoTo);//Buscamos en la tabla GoTo el estado
					System.out.println("GOTO: " + estado);
					pila.push(estado);//Guardamos el estado en la cima de la pila
				}
			}
			else if(accion.substring(0,1).equals("A")){//Aceptar
				resultado = 0;
				parse.add(1);
			}
		}		
		else{
			//Error Sintactico
			System.out.println("Error sintáctico en la línea: "+ this.anLex.getLineaActual());
			resultado = -1;
		}
		return resultado;
	}//analizar

	/**
	 * <b><i>cargarListaReglas(String ruta)</i></b>
	 * <br>
	 * <br>
	 * <code>public void cargarListaReglas(String ruta)</code>
	 * <br>
	 * <br>
	 * Carga la lista de reglas en un array list
	 */
	public void cargarListaReglas(String ruta){
		try {
			FileInputStream ficheroReglas = new FileInputStream(ruta);
			BufferedReader br = new BufferedReader(new InputStreamReader(ficheroReglas));
			String line;
			String[] linea;
			int i = 1;
			while ((line = br.readLine()) != null) {
				linea = line.split(",", 2);
				listaReglas.add(new Regla(i,linea[0].trim(),Integer.parseInt(linea[1].trim())));
				i++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//cargarListaReglas

	/**
	 * <b><i>getParse()</i></b>
	 * <br>
	 * <br>
	 * <code>public ArrayList<Integer> getPârse()</code>
	 * <br>
	 * <br>
	 * Devuelve la lista de reglas aplicadas en el analisis
	 * @return Un arraylist con las reglas utilizadas en el analisis
	 */
	public ArrayList<Integer> getParse() {
		return parse;
	}

}
