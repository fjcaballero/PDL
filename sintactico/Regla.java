package sintactico;

import java.util.Stack;

import semantico.Atributo;

public class Regla {

	public int numero;
	public String parteIzq;
	public int nElementosDer;

	public Regla(int numero, String parteIzq, int nElementosDer){
		this.numero = numero;
		this.parteIzq = parteIzq;
		this.nElementosDer = nElementosDer;
	}

	public String toString(){
		return numero + "," + parteIzq + "," + nElementosDer;
	}
	
	public static String ejecutarAccion(int numRegla, Stack<Atributo> pilaSimbolos){
		String tipo = "-";
		Atributo programas, programa, bloque, funcion, sentencia, expresion;
		switch(numRegla){
			case 1://PROGRAMA0 -> PROGRAMAS
				//{ if (PROGRAMAS.tipo = "ok") then SIN ERRORES; else ERROR }
				programas = pilaSimbolos.peek();
				tipo = programas.getTipo();
				if(tipo.equals("ok"))System.out.println("El programa analizado no contiene errores");
				else System.out.println("El programa analizado contiene errores");
				break;
			case 2://PROGRAMAS -> SALTO PROGRAMA 
				//{ PROGRAMAS.tipo = PROGRAMA.tipo }
				programa = pilaSimbolos.peek();
				tipo = programa.getTipo();
				break;
			case 3://PROGRAMA -> BLOQUE
				//{ PROGRAMA.tipo = BLOQUE.tipo }
				bloque = pilaSimbolos.peek();
				tipo = bloque.getTipo();
				break;
			case 4://PROGRAMA -> FUNCION
				//{ PROGRAMA.tipo = FUNCION.tipo }
				funcion = pilaSimbolos.peek();
				tipo = funcion.getTipo();
				break;
			case 5://PROGRAMA -> BLOQUE  SALTO2  PROGRAMA
				//{ if(BLOQUE.tipo = "ok") then PROGRAMA.tipo = PROGRAMA1.tipo; else PROGRAMA.tipo = "error" }
				programa = pilaSimbolos.peek();
				bloque = pilaSimbolos.get(pilaSimbolos.size()-2);
				if(bloque.getTipo().equals("ok"))tipo = programa.getTipo();
				else tipo = "error";
				break;
			case 6://PROGRAMA -> FUNCION  SALTO2  PROGRAMA
				//{ if(FUNCION.tipo = "ok") then PROGRAMA.tipo = PROGRAMA1.tipo; else PROGRAMA.tipo = "error" }
				programa = pilaSimbolos.peek();
				funcion = pilaSimbolos.get(pilaSimbolos.size()-2);
				if(funcion.getTipo().equals("ok"))tipo = programa.getTipo();
				else tipo = "error";
				break;
			case 7://PROGRAMA -> lambda
				//{ PROGRAMA.tipo = "ok" }
				tipo = "ok";
				break;
			case 8://BLOQUE -> var  id 
				//{ BLOQUE.tipo = "ok" }
				tipo = "ok";
				break;
			case 9://BLOQUE -> SENTENCIA
				//{ BLOQUE.tipo = SENTENCIA.tipo }
				sentencia = pilaSimbolos.peek();
				tipo = sentencia.getTipo();
				break;
			case 10://BLOQUE -> if  abrePar  EXPRESION  cierraPar  SENTENCIA
				//{ if(EXPRESION.tipo = "entero/logico") then BLOQUE.tipo = SENTENCIA.tipo; else BLOQUE.tipo = "error" }
				sentencia = pilaSimbolos.peek();
				expresion = pilaSimbolos.get(pilaSimbolos.size()-2);
				if(expresion.getTipo().equals("entero/logico"))tipo = sentencia.getTipo();
				else tipo = "error";
				break;
			case 11://BLOQUE -> if  abrePar  EXPRESION  cierraPar  SALTO  abreLlave  SALTO2  CUERPO  cierraLlave
			case 12://BLOQUE -> switch  abrePar  EXPRESION  cierraPar  SALTO  abreLlave  SALTO2  CASE  cierraLlave
			case 13://CASE -> default  dosPuntos SALTO CUERPO
			case 14://CASE -> case  EXPRESION  dosPuntos  SALTO CUERPO  CASE2
			case 15://CASE2 -> CASE
			case 16://CASE2 -> break  SALTO2  CASE
			case 17://FUNCION -> function  id  abrePar  ARGUMENTOS  cierraPar SALTO abreLlave  SALTO2  CUERPO cierraLlave
			case 18://SENTENCIA -> id  igual  EXPRESION
			case 19://SENTENCIA -> id  masIgual  EXPRESION
			case 20://SENTENCIA -> id  menosIgual  EXPRESION
			case 21://SENTENCIA -> id  abrePar  LLAMADAFUN  cierraPar
			case 22://SENTENCIA -> document  punto  write  abrePar  EXPRESION  cierraPar
			case 23://SENTENCIA -> prompt  abrePar  id  cierraPar
			case 24://SENTENCIA -> return  RETURNVALUE
			case 25://CUERPO -> BLOQUE  SALTO2  CUERPO
			case 26://CUERPO -> lambda
			case 27://SALTO -> sl SALTO
			case 28://SALTO -> lambda
			case 29://SALTO2 -> sl SALTO
			case 30://RETURNVALUE -> EXPRESION 
			case 31://RETURNVALUE -> lambda
			case 32://LLAMADAFUN -> EXPRESION  LLAMADAFUN2  
			case 33://LLAMADAFUN -> lambda
			case 34://LLAMADAFUN2 -> coma  EXPRESION  LLAMADAFUN2 
			case 35://LLAMADAFUN2 -> lambda
			case 36://ARGUMENTOS -> id  ARGUMENTOS2 
			case 37://ARGUMENTOS -> lambda
			case 38://ARGUMENTOS2 -> coma  id  ARGUMENTOS2
			case 39://ARGUMENTOS2 -> lambda
			case 40://EXPRESION -> EXPRESION  menor  ARITMETICA 
			case 41://EXPRESION -> EXPRESION  menorIgual  ARITMETICA
			case 42://EXPRESION -> ARITMETICA  
			case 43://ARITMETICA -> ARITMETICA  mas  SIMPLE 
			case 44://ARITMETICA -> ARITMETICA  menos  SIMPLE 
			case 45://ARITMETICA -> SIMPLE 
			case 46://SIMPLE -> negacion  SIMPLE  
			case 47://SIMPLE -> abrePar  EXPRESION  cierraPar 
			case 48://SIMPLE -> id 
			case 49://SIMPLE -> num 
			case 50://SIMPLE -> cad  
			case 51://SIMPLE -> id  abrePar  LLAMADAFUN  cierraPar 
				
			default:
				System.out.println("Error al ejecutar Accion Semantica, numero de regla incorrecto");
				break;
	
		}
		return tipo;
	}
}
