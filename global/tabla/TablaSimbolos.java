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
    
    public String buscaTipoTS(String lexema){
    	String tipo = "-";
    	if(!indice.isEmpty()){
    		if(indice.containsKey(lexema)){
    			tipo = entradas.get(lexema).getTipo();
    		}
    	}
    	return tipo;
    }

    public String buscaDespTS(String lexema){
        String desp = "-";
        if(!indice.isEmpty()){
            if(indice.containsKey(lexema)){
                desp = entradas.get(lexema).getDesp();
            }
        }
        return desp;
    }

    public int buscaNumParamTS(String lexema){
        int nparam = 0;
        if(!indice.isEmpty()){
            if(indice.containsKey(lexema)){
                nparam = entradas.get(lexema).getNumParam();
            }
        }
        return nparam;
    }

    public String buscaTipoDevTS(String lexema){
        String tipoDev = "-";
        if(!indice.isEmpty()){
            if(indice.containsKey(lexema)){
                tipoDev = entradas.get(lexema).getTipoDev();
            }
        }
        return tipoDev;
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
    
    public boolean insertaTipoTS(String lexema, String tipo){
    	boolean res = false;
    	if(!indice.isEmpty()){
    		if(indice.containsKey(lexema)){
    			entradas.get(lexema).setTipo(tipo);
    			res = true;
    		}
    	}
    	return res;
    }
    
    public boolean insertaDespTS(String lexema, String desp){
    	boolean res = false;
    	if(!indice.isEmpty()){
    		if(indice.containsKey(lexema)){
    			entradas.get(lexema).setDesp(desp);
    			res = true;
    		}
    	}
    	return res;
    }
    
    public boolean insertaNumParamTS(String lexema, int nparam){
    	boolean res = false;
    	if(!indice.isEmpty()){
    		if(indice.containsKey(lexema)){
    			entradas.get(lexema).setNumParam(nparam);
    			res = true;
    		}
    	}
    	return res;
    }
    
    public boolean insertaTipoDevTS(String lexema, String tipoDev){
    	boolean res = false;
    	if(!indice.isEmpty()){
    		if(indice.containsKey(lexema)){
    			entradas.get(lexema).setTipoDev(tipoDev);
    			res = true;
    		}
    	}
    	return res;
    }

	public String getNombreTabla() {
		return nombreTabla;
	}
	
	public void printTabla(){
		PrintWriter writer;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter("resources\\tabla_simbolos.txt", true)));
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
