/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package global.tabla;

import java.util.ArrayList;
import java.util.Stack;

public class ControladorTS {
    
    private static Stack<TablaSimbolos> pilaTablas = new Stack<TablaSimbolos>();
    // pila para almacenar
    private static Stack<TablaSimbolos> pilaLocales = new Stack<TablaSimbolos>();
    private static ArrayList<String> tablaReservadas = new ArrayList<String>(){{
        add("switch");
        add("case");
        add("break");
        add("default");
        add("return");
        add("if");
        add("function");
        add("prompt");
        add("document");
        add("write");
        add("var");
    }};
    private static boolean flagDU = false;//True = Declaracion, False = Uso
    private static boolean flagVF = false;//True = Var, False = Function
    private static String funcionActual;
    
    public static void setFuncion(String funcion){
    	funcionActual = funcion;
    }
    
    public static String getFuncion(){
    	return funcionActual;
    }
    
    public static boolean getFlagDU(){
    	return flagDU;
    }
    
    public static void flagDeclaracion(){
    	flagDU = true;
    }
    public static void flagUso(){
    	flagDU = false;
    }
    
    public static boolean getFlagVF(){
    	return flagVF;
    }
    
    public static void flagVar(){
    	flagVF = true;
    }
    public static void flagFunction(){
    	flagVF = false;
    }
    
    
    public static boolean esReservada(String palabra){
        return tablaReservadas.contains(palabra);
    }
    
    public static void crearTS(String nombre){
        pilaTablas.push(new TablaSimbolos(nombre));
    }
    
    public static void eliminarTS(){
        pilaLocales.push(pilaTablas.pop());
    }
    
    public static String nombreTablaActual(){
    	return pilaTablas.peek().getNombreTabla();
    }
    
    public static int buscaIdTS(String id){
    	int pos = -1;
    	boolean found = false;
    	if(!pilaTablas.isEmpty()){
    		for(int i=0; i < pilaTablas.size() && !found; i++){
    			if(pilaTablas.get(i).buscarTS(id) > 0){
    				pos = pilaTablas.get(i).buscarTS(id);
    				found = true;
    			}
    		}
    	}
    	return pos;
    }
    
    /**
     * <i><b>buscaTipoTS()</b></i>
     * @param id
     * @return Devuelve el tipo asignado al identificador, o "-" en caso de que 
     * no este declarado o que no se le haya asignado tipo
     */
    public static String buscaTipoTS(String id){
    	String tipo = "-";
    	boolean found = false;
    	if(!pilaTablas.isEmpty()){
    		for(int i=0; i < pilaTablas.size() && !found; i++){
    			tipo = pilaTablas.get(i).buscaTipoTS(id);
    			if(!tipo.equals("-")){
    				found = true;
    			}
    		}
    	}
    	return tipo;
    }
    
    /**
     * 
     * @param id
     * @return Devuelve el desplazamiento asignado al identificador, o "-" en caso de que 
     * no este declarado o que no se le haya asignado desplazamiento
     */
    public static String buscaDespTS(String id){
    	String desp = "-";
    	boolean found = false;
    	if(!pilaTablas.isEmpty()){
    		for(int i=0; i < pilaTablas.size() && !found; i++){
    			desp = pilaTablas.get(i).buscaDespTS(id);
    			if(!desp.equals("-")){
    				found = true;
    			}
    		}
    	}
    	return desp;
    }
    
    public static int insertaIdTS(String id){
    	if(!pilaTablas.isEmpty()){
    		return pilaTablas.peek().insertarTS(id);
    	}
    	else{
    		return -1;
    	}
    }
    
    /**
     * <i><b>insertaTipoTS(String id, String tipo)</b></i>
     * <br>
     * Metodo que inserta el tipo especificado en la entrada de la tabla actual si la hubiera,
     * o en la tabla global en caso contrario. Si el identificador no esta declarado devuelve false.
     * @param id
     * @param tipo
     * @return Devuelve true si se ha insertado el tipo correctamente, y false en caso contrario
     */
    public static boolean insertaTipoTS(String id, String tipo){
    	boolean insertado = false;
    	if(!pilaTablas.isEmpty()){
    		if(!pilaTablas.isEmpty()){
    			for(int i=0; i < pilaTablas.size() && !insertado; i++){
    				insertado = pilaTablas.get(i).insertaTipoTS(id, tipo);
    			}
    		}
    	}
    	return insertado;
    }

	public static Stack<TablaSimbolos> getPilaTablas() {
		return pilaTablas;
	}

	public static Stack<TablaSimbolos> getPilaLocales() {
		return pilaLocales;
	}

	public static String getLexema(int pos, String nombreTabla){
		String resultado = "";
		boolean encontrado = false;
		TablaSimbolos tabla;
		for(int i=0; i < pilaTablas.size() && !encontrado; i++){
			tabla = pilaTablas.get(i);
			if(tabla.getNombreTabla().equals(nombreTabla)){
				resultado = tabla.buscaLexemaTS(pos);
				encontrado = true;
			}
		}
		return resultado;
	}
    
	public static void printTablas(){
		//Printear la tabla Global creando/sobreescribiendo el fichero tablasimbolos.txt
		pilaTablas.peek().printTabla(false);
		for(int i=pilaLocales.size(); i > 0 ; i--){
			pilaLocales.get(i-1).printTabla(true);
		}
	}
    
	public static String buscaTipoDevTS(String lexema){
		return pilaTablas.peek().buscaTipoDevTS(lexema);
	}
	
	public static void insertaTipoDevTS(String lexema, String tipo){
		pilaTablas.peek().insertaTipoDevTS(lexema, tipo);
	}
    
}
