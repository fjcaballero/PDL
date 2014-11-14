/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package global.tabla;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Javichuck
 */
public class ControladorTS {
    
    private static Stack<TablaSimbolos> pilaTablas = new Stack<TablaSimbolos>();
    private static ArrayList<String> tablaReservadas = new ArrayList<String>(){{
        add("switch");
        add("case");
        add("break");
        add("return");
        add("if");
        add("function");
        add("prompt");
        add("document");
        add("write");
        add("var");
    }};
    
    public static void crearTS(){
        pilaTablas.push(new TablaSimbolos());
    }
    
    public static void eliminarTS(){
        pilaTablas.pop();
    }
    /* Metodo de prueba */
    public static int nElementos(){
        return pilaTablas.size();
    }
    public static boolean esReservada(String palabra){
        return tablaReservadas.contains(palabra);
    }
    public static int buscarEnTS(String id){
        return pilaTablas.firstElement().buscarTS(id);
    }
    public static int insertarEnTS(String id){
        return 0;//pilaTablas.firstElement().insertarTS(/*EntradaTS identificador*/, id);
    }
    
    
}
