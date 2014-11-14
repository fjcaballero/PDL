package global.tabla;

import java.util.HashMap;

public class TablaSimbolos {
	HashMap<String,EntradaTS> entradas;
	
	public TablaSimbolos(){
	
	}
    
    public int buscarTS(String lexema){
        int pos = -1;
    	if(!entradas.isEmpty()){
    		entradas.get(lexema);
                // pos = ¿?
    	}
        return pos;
    }
    public int insertarTS(EntradaTS tipoEntrada, String lexema){
        int pos = -1;
        entradas.put(lexema, tipoEntrada);
        //pos = ¿?
        return pos; 
    }
}
