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
    
    
    public static boolean esReservada(String palabra){
        return tablaReservadas.contains(palabra);
    }
    
    public static void crearTS(String nombre){
        pilaTablas.push(new TablaSimbolos(nombre));
    }
    
    public static void eliminarTS(){
        pilaTablas.pop();
    }
    
    public static String nombreTablaActual(){
    	return pilaTablas.firstElement().getNombreTabla();
    }
    
    public static int buscarEnTS(String id){
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
    public static int insertarEnTS(String id){
    	if(!pilaTablas.isEmpty()){
    		return pilaTablas.firstElement().insertarTS(id);
    	}
    	else{
    		return -1;
    	}
    }

	public static Stack<TablaSimbolos> getPilaTablas() {
		return pilaTablas;
	}
    
    
    
}
