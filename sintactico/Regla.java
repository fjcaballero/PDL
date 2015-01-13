package sintactico;

import java.util.Stack;

import semantico.Atributo;
import global.tabla.ControladorTS;

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
		Atributo programas, programa, bloque, funcion, sentencia, expresion, cuerpo, caso, case2, id, llamadaFun, argumentos;
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
				//{ if(EXPRESION.tipo = "entero/logico") then BLOQUE.tipo = CUERPO.tipo; else BLOQUE.tipo = "error" }
				cuerpo = pilaSimbolos.get(pilaSimbolos.size()-1);
				expresion = pilaSimbolos.get(pilaSimbolos.size()-6);
				if(expresion.getTipo().equals("entero/logico"))tipo = cuerpo.getTipo();
				else tipo = "error";
				break;
			case 12://BLOQUE -> switch  abrePar  EXPRESION  cierraPar  SALTO  abreLlave  SALTO2  CASE  cierraLlave
				//{ if(EXPRESION.tipo = "entero/logico") then BLOQUE.tipo = CASE.tipo; else BLOQUE.tipo = "error" }
				caso = pilaSimbolos.get(pilaSimbolos.size()-1);
				expresion = pilaSimbolos.get(pilaSimbolos.size()-6);
				if(expresion.getTipo().equals("entero/logico"))tipo = caso.getTipo();
				else tipo = "error";
				break;
			case 13://CASE -> default  dosPuntos SALTO CUERPO
				//{ CASE.tipo = CUERPO.tipo }
				cuerpo = pilaSimbolos.peek();
				tipo = cuerpo.getTipo();
				break;
			case 14://CASE -> case  EXPRESION  dosPuntos  SALTO CUERPO  CASE2
				//{ if(EXPRESION.tipo = "entero/logico" && CUERPO.tipo = "ok" && CASE2.tipo="ok") then CASE.tipo = "ok"; else CASE.tipo = "error" }
				case2 = pilaSimbolos.peek();
				cuerpo = pilaSimbolos.get(pilaSimbolos.size()-1);
				expresion = pilaSimbolos.get(pilaSimbolos.size()-4);
				if(expresion.getTipo().equals("entero/logico") && cuerpo.getTipo().equals("ok") && case2.getTipo().equals("ok"))tipo = "ok";
				else tipo = "error";
				break;
			case 15://CASE2 -> CASE
				//{ CASE2.tipo = CASE.tipo }
				caso = pilaSimbolos.peek();
				tipo = caso.getTipo();
				break;
			case 16://CASE2 -> break  SALTO2  CASE
				//{ CASE2.tipo = CASE.tipo }
				caso = pilaSimbolos.peek();
				tipo = caso.getTipo();
				break;
			case 17://FUNCION -> function  id  abrePar  ARGUMENTOS  cierraPar SALTO abreLlave  SALTO2  CUERPO cierraLlave
				//{ if(ARGUMENTOS.tipo = "ok") then FUNCION.tipo = CUERPO.tipo; else FUNCION.tipo = "error"; Eliminar TS Local}
				cuerpo = pilaSimbolos.get(pilaSimbolos.size()-1);
				argumentos = pilaSimbolos.get(pilaSimbolos.size()-6);
				if(argumentos.getTipo().equals("ok"))tipo = cuerpo.getTipo();
				else tipo = "error";
				ControladorTS.eliminarTS();
				break;
			case 18://SENTENCIA -> id  igual  EXPRESION
				//{if(id.tipo!=funcion)then id.tipo = EXPRESION.tipo, SENTENCIA.tipo = "ok"; else SENTENCIA.tipo="error" }
				expresion = pilaSimbolos.peek();
				id = pilaSimbolos.get(pilaSimbolos.size()-2);
				if(!id.getLexema().equals("-") && (expresion.getTipo().equals("entero/logico") || expresion.getTipo().equals("cadena"))){
					String tipoID = ControladorTS.buscaTipoTS(id.getLexema());
					if(!tipoID.equals("funcion")){
						ControladorTS.insertaTipoTS(id.getLexema(), expresion.getTipo());
						tipo = "ok";
					}
					else tipo = "error";
				}
				else tipo = "error";
				break;
			case 19://SENTENCIA -> id  masIgual  EXPRESION
				//{ if (id.tipo = "entero/logico" && EXPRESION.tipo = "entero/logico") then SENTENCIA.tipo = "ok"; else SENTENCIA.tipo = "error" }
				expresion = pilaSimbolos.peek();
				id = pilaSimbolos.get(pilaSimbolos.size()-2);
				if(id.getTipo().equals("entero/logico") && expresion.getTipo().equals("entero/logico"))tipo = "ok";
				else tipo = "error";
				break;
			case 20://SENTENCIA -> id  menosIgual  EXPRESION
				//{ if (id.tipo = "entero/logico" && EXPRESION.tipo = "entero/logico") then SENTENCIA.tipo = "ok"; else SENTENCIA.tipo = "error" }
				expresion = pilaSimbolos.peek();
				id = pilaSimbolos.get(pilaSimbolos.size()-2);
				if(id.getTipo().equals("entero/logico") && expresion.getTipo().equals("entero/logico"))tipo = "ok";
				else tipo = "error";
				break;
			case 21://SENTENCIA -> id  abrePar  LLAMADAFUN  cierraPar
				//{ if(id.tipo = "funcion")then SENTENCIA.tipo = LLAMADAFUN.tipo; else SENTENCIA.tipo = "error" }
				llamadaFun = pilaSimbolos.get(pilaSimbolos.size()-1);
				id = pilaSimbolos.get(pilaSimbolos.size()-3);
				if(id.getTipo().equals("funcion"))tipo = llamadaFun.getTipo();
				else tipo = "error";
				break;
			case 22://SENTENCIA -> document  punto  write  abrePar  EXPRESION  cierraPar
				//{ if (EXPRESION.tipo != "error") then SENTENCIA.tipo = "ok"; else SENTENCIA.tipo="error" }
				expresion = pilaSimbolos.get(pilaSimbolos.size()-1);
				if(!expresion.getTipo().equals("error"))tipo = "ok";
				else tipo = "error";
				break;
			case 23://SENTENCIA -> prompt  abrePar  id  cierraPar
				//{ if (id.tipo!=funcion)then SENTENCIA.tipo = "ok"; else SENTENCIA.tipo = "error" }
				id = pilaSimbolos.get(pilaSimbolos.size()-1);
				if(!id.getTipo().equals("funcion"))tipo = "ok";
				else tipo = "error";
				break;
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
