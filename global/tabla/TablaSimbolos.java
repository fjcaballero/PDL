package global.tabla;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class TablaSimbolos {
	private String nombreTabla;
	private int n_entradas = 1;
	private HashMap<String,Integer> indice;
	private HashMap<Integer,EntradaTS> entradas;
	
	public TablaSimbolos(String nombre){
		this.nombreTabla = nombre;
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
    public int insertarTS(String lexema){
        int pos = -1;
        if(!indice.containsKey(lexema)){
        	indice.put(lexema, n_entradas);
        	entradas.put(n_entradas, new EntradaTS(n_entradas));
        	pos = n_entradas;
            n_entradas++;
        }
        return pos; 
    }

	public String getNombreTabla() {
		return nombreTabla;
	}
	
	public void printTabla(){
		PrintWriter writer;
		try {
			if(!this.nombreTabla.equals("Global")){
				writer = new PrintWriter(new BufferedWriter(new FileWriter("resources\\tabla_simbolos.txt", true)));
			}
			else{
				writer = new PrintWriter("resources\\tabla_simbolos.txt","UTF-8");
			}
			writer.println("Tabla de símbolos: " +this.nombreTabla);
			writer.println("+ POS +     LEXEMA     +     TIPO      +  DESP   +    NUMPARAM    +    TIPODEV");
			writer.println("+-----+----------------+---------------+---------+----------------+-----------------");
			Iterator<Entry<Integer, EntradaTS>> it = entradas.entrySet().iterator();
			while(it.hasNext()){
				Entry<Integer,EntradaTS> entrada = it.next();
				writer.println(entrada.getValue().aString());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}
