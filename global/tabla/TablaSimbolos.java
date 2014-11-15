package global.tabla;

import java.util.HashMap;

public class TablaSimbolos {
	private String nombreTabla;
	private static int n_entradas = 1;
	private HashMap<String,Integer> indice;
	private HashMap<Integer,EntradaTS> entradas;
	
	public TablaSimbolos(){
		this.indice = new HashMap<String, Integer>();
		this.entradas = new HashMap<Integer, EntradaTS>();
	}
    
	/**
	 * Busca un identificador en la tabla de símbolos.
	 * @param lexema
	 * @return Posición del identificador en la tabla. Devuelve -1 si no esta.
	 */
    public int buscarTS(String lexema){
        int pos = -1;
    	if(!indice.isEmpty()){
    		if(indice.containsKey(lexema)){
    			pos = indice.get(lexema);
    		}
    	}
        return pos;
    }
    /**
     * Inserta un identificador en la tabla de símbolos.
     * @param tipoEntrada
     * @param lexema
     * @return Posición del identificador en la tabla. Devuelve -1 si no se ha insertado.
     */
    public int insertarTS(EntradaTS tipoEntrada, String lexema){
        int pos = -1;
        if(!indice.containsKey(lexema)){
        	indice.put(lexema, n_entradas);
        	entradas.put(n_entradas, tipoEntrada);
        	pos = n_entradas;
            n_entradas++;
        }
        return pos; 
    }
}
