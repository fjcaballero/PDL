package global.tabla;

import java.util.HashMap;

public class TablaSimbolos {
	HashMap<String,EntradaTS> entradas;
	
	public TablaSimbolos(){
		//this.entradas.put("document", new EntradaPalRes("document"));
		//this.entradas.put("function", new EntradaPalRes("function"));
		//this.entradas.put("if", new EntradaPalRes("if"));
		//this.entradas.put("null", new EntradaPalRes("null"));
		//this.entradas.put("prompt", new EntradaPalRes("prompt"));
		//this.entradas.put("return", new EntradaPalRes("return"));
		//this.entradas.put("switch", new EntradaPalRes("switch"));
		//this.entradas.put("var", new EntradaPalRes("var"));
		//this.entradas.put("write", new EntradaPalRes("write"));
	}
    
    public int buscarTS(String lexema){
    	if(!entradas.isEmpty()){
    		entradas.get(lexema);
    	}
            return 0;
    }
    public int insertarTS(EntradaTS tipoEntrada, String lexema){
            return 0; 
    }
}
