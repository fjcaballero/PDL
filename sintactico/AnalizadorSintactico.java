package sintactico;

import global.token.Token;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class AnalizadorSintactico {
	
	private Stack<String> pila;
	private ArrayList<String> tablaAccion;
	private ArrayList<String> tablaGoTo;
	private ArrayList<Regla> listaReglas;
	
	/* <AnalizadorSintactico>
	 * 
	 * Constructor del Analizador Sintactico
	 * 
	 * Recibe como parametros la ubicacion de los ficheros de la tabla de Accion y de GoTo
	 */
	public AnalizadorSintactico(String ficheroAccion, String ficheroGoTo){//Constructor
		
		//Inicializar pila de estados
		pila.push("0");
		
		//Leer Tabla Accion
		try {
			ObjectInputStream oisAccion = new ObjectInputStream(new FileInputStream(ficheroAccion));
			tablaAccion = (ArrayList<String>)oisAccion.readObject();
			oisAccion.close();
		} catch (Exception e) {
			System.out.println("Error al leer el fichero: "+ficheroAccion);
			e.printStackTrace();
		}
		
		//Leer Tabla GoTo
		try {
			ObjectInputStream oisGoTo = new ObjectInputStream(new FileInputStream(ficheroGoTo));
			tablaGoTo = (ArrayList<String>)oisGoTo.readObject();
			oisGoTo.close();
		} catch (Exception e) {
			System.out.println("Error al leer el fichero: "+ficheroGoTo);
			e.printStackTrace();
		}
		
		//Leer Fichero Reglas
		listaReglas = new ArrayList<Regla>();
		//Rellenar la lista en orden ascendente con las reglas del fichero
		
	}//Constructor
	
	
	/* <buscarTabla>
	 * 
	 * Metodo auxiliar para buscar elementos en las tablas de Accion o GoTo
	 * 
	 * Recibe como parametros:
	 *  - la fila a buscar <elemento1> (que simboliza el estado en ambas tablas)
	 *  - la columna a buscar <elemento2> (que simboliza el token en la tabla de Accion o el no terminal en la tabla de GoTo)
	 *  - la tabla en la que se debe buscar
	 * 
	 * Devuelve el elemento correspondiente de la tabla si existe y un String vacio en otro caso
	 */
	private String buscarTabla(String elemento1, String elemento2, ArrayList<String> tabla){//buscarTabla
		String resultado = "";
		String fila = "";
		String columna = "";
		boolean encontrado = false;
		Iterator<String> it = tabla.iterator();
		while (it.hasNext() && !encontrado){
			fila = it.next();
			columna = it.next();
			resultado = it.next();
			encontrado = fila.equals(elemento1) && columna.equals(elemento2.trim());
		}
		return resultado;
	}//buscarTabla
	
	
	/* <analizar>
	 * 
	 * Metodo que simula las iteraciones del automata del analizador sintactico
	 * 
	 * Recibe como parametro el token correspondiente de la sentencia que se esta analizando
	 * 
	 * * * * * Puede conllevar recursividad en algunas de sus iteraciones * * * * *
	 */
	public int analizar(Token token){//analizar
		int resultado = 1; //En proceso
		String estado = pila.peek();
		String accion = buscarTabla(estado,token.tipo(),tablaAccion);
		if(accion.charAt(0)=='d'){//Desplazar
			pila.push(accion.substring(1,accion.length()).trim());
		}
		else if(accion.charAt(0)=='r'){//Reducir
			int numRegla = Integer.valueOf(accion.substring(1,accion.length()).trim());
			Regla regla = listaReglas.get(numRegla-1);
			for(int i=0; i<regla.nElementosDer; i++){//Sacamos de la pila n estados
				pila.pop();
			}
			estado = buscarTabla(pila.peek(), regla.parteIzq, tablaGoTo);//Buscamos en la tabla GoTo el estado
			pila.push(estado);//Guardamos el estado en la cima de la pila
		}
		else if(accion.charAt(0)=='A'){//Aceptar
			resultado = 0;
		}
		else{
			//Error Sintactico
			resultado = -1;
		}
		return resultado;
	}//analizar
	
	/**
	 * cargarListaReglas
	 * 
	 * 
	 */
	public void cargarListaReglas(){
		try {
			FileInputStream ficheroReglas = new FileInputStream("resource/Reglas.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(ficheroReglas));
			String line;
			String[] linea;
			while ((line = br.readLine()) != null) {
				linea = line.split(",", 3);
				listaReglas.add(new Regla(Integer.parseInt(linea[0]),linea[1],Integer.parseInt(linea[2])));
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
