package sintactico;

import java.util.Stack;

import lexico.AnalizadorLexico;
import semantico.Atributo;
import global.ControladorErrores;
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

		Atributo programas, programa, bloque, funcion, sentencia, expresion, cuerpo, caso, case2, id, llamadaFun, llamadaFun21, argumentos,
		returnValue, argumentos2, argumentos21, aritmetica, simple;
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
				else {
					tipo = "error";
					ControladorErrores.addError("La condicion del if no es de tipo logico");
				}
				break;
			case 11://BLOQUE -> if  abrePar  EXPRESION  cierraPar  SALTO  abreLlave  SALTO2  CUERPO  cierraLlave
				//{ if(EXPRESION.tipo = "entero/logico") then BLOQUE.tipo = CUERPO.tipo; else BLOQUE.tipo = "error" }
				cuerpo = pilaSimbolos.get(pilaSimbolos.size()-1);
				expresion = pilaSimbolos.get(pilaSimbolos.size()-6);
				if(expresion.getTipo().equals("entero/logico"))tipo = cuerpo.getTipo();
				else {
					tipo = "error";
					ControladorErrores.addError("La condicion del if no es de tipo logico");
				}
				break;
			case 12://BLOQUE -> switch  abrePar  EXPRESION  cierraPar  SALTO  abreLlave  SALTO2  CASE  cierraLlave
				//{ if(EXPRESION.tipo = "entero/logico") then BLOQUE.tipo = CASE.tipo; else BLOQUE.tipo = "error" }
				caso = pilaSimbolos.get(pilaSimbolos.size()-1);
				expresion = pilaSimbolos.get(pilaSimbolos.size()-6);
				if(expresion.getTipo().equals("entero/logico"))tipo = caso.getTipo();
				else {
					tipo = "error";
					ControladorErrores.addError("La condicion del switch no es de tipo entero");
				}
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
				else {
					tipo = "error";
					if(!expresion.getTipo().equals("entero/logico"))ControladorErrores.addError("La expresion del case no es de tipo entero");
				}
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
				System.out.println("---------------------------------------------------------");
				System.out.println(pilaSimbolos.indexOf(pilaSimbolos.peek()));
				System.out.println(pilaSimbolos.size());
				System.out.println(id.getLexema());
				System.out.println(expresion.getTipo());
				System.out.println("---------------------------------------------------------");
				if(!id.getLexema().equals("-") && (expresion.getTipo().equals("entero/logico") || expresion.getTipo().equals("cadena"))){
					String tipoID = ControladorTS.buscaTipoTS(id.getLexema());
					if(!tipoID.equals("funcion")){
						ControladorTS.insertaTipoTS(id.getLexema(), expresion.getTipo());
						tipo = "ok";
					}
					else {
						ControladorErrores.addError("El identificador \""+ id.getLexema() +"\" esta declarado anteriormente como una funcion");
						tipo = "error";
					}
				}
				else tipo = "error";
				break;
			case 19://SENTENCIA -> id  masIgual  EXPRESION
				//{ if (id.tipo = "entero/logico" && EXPRESION.tipo = "entero/logico") then SENTENCIA.tipo = "ok"; else SENTENCIA.tipo = "error" }
				expresion = pilaSimbolos.peek();
				id = pilaSimbolos.get(pilaSimbolos.size()-2);
				if(id.getTipo().equals("entero/logico") && expresion.getTipo().equals("entero/logico"))tipo = "ok";
				else {
					tipo = "error";
					if(!id.getTipo().equals("entero/logico"))ControladorErrores.addError("El identificador \""+ id.getLexema() +"\" no es de tipo entero");
					else ControladorErrores.addError("La parte derecha de la asignacion no es de tipo entero");
				}
				break;
			case 20://SENTENCIA -> id  menosIgual  EXPRESION
				//{ if (id.tipo = "entero/logico" && EXPRESION.tipo = "entero/logico") then SENTENCIA.tipo = "ok"; else SENTENCIA.tipo = "error" }
				expresion = pilaSimbolos.peek();
				id = pilaSimbolos.get(pilaSimbolos.size()-2);
				if(id.getTipo().equals("entero/logico") && expresion.getTipo().equals("entero/logico"))tipo = "ok";
				else {
					tipo = "error";
					if(!id.getTipo().equals("entero/logico"))ControladorErrores.addError("El identificador \""+ id.getLexema() +"\" no es de tipo entero");
					else ControladorErrores.addError("La parte derecha de la asignacion no es de tipo entero");
				}
				break;
			case 21://SENTENCIA -> id  abrePar  LLAMADAFUN  cierraPar
				//{ if(id.tipo = "funcion")then SENTENCIA.tipo = LLAMADAFUN.tipo; else SENTENCIA.tipo = "error" }
				llamadaFun = pilaSimbolos.get(pilaSimbolos.size()-1);
				id = pilaSimbolos.get(pilaSimbolos.size()-3);
				if(id.getTipo().equals("funcion"))tipo = llamadaFun.getTipo();
				else {
					tipo = "error";
					ControladorErrores.addError("El identificador \""+ id.getLexema() +"\" no esta declarado como funcion");
				}
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
				else {
					tipo = "error";
					ControladorErrores.addError("El identificador \""+ id.getLexema() +"\" esta declarado como funcion");
				}
				break;
			case 24://SENTENCIA -> return  RETURNVALUE
				//{ if(RETURNVALUE.tipo == "entero/logico", "cadena", "vacio") then TSG.funcion.val_dev = RETURNVALUE.tipo, SENTENCIA.tipo = "ok"; else SENTENCIA.tipo = "error"}
				returnValue = pilaSimbolos.peek();
				if(returnValue.getTipo().equals("entero/logico") || returnValue.getTipo().equals("cadena") || returnValue.getTipo().equals("vacio")){
					ControladorTS.insertaTipoDevTS(ControladorTS.getFuncion(), returnValue.getTipo());
					tipo = "ok";
				}
				else tipo = "error";
				break;
				
			case 25://CUERPO -> BLOQUE  SALTO2  CUERPO
				//{ if (CUERPO1.tipo = "ok" && BLOQUE.tipo = "ok") then CUERPO.tipo = "ok"; else CUERPO.tipo = "error"}
				cuerpo = pilaSimbolos.peek();
				bloque = pilaSimbolos.get(pilaSimbolos.size()-3);
				if(cuerpo.getTipo().equals("ok") && bloque.getTipo().equals("ok")) tipo = "ok";
				else tipo = "error";
				break;
			case 26://CUERPO -> lambda
				//{ CUERPO.tipo = "ok" }
				tipo="ok";
				break;
			case 27://SALTO -> sl SALTO
				break;
			case 28://SALTO -> lambda
				break;
			case 29://SALTO2 -> sl SALTO
				break;
			case 30://RETURNVALUE -> EXPRESION 
				//{ RETURNVALUE.tipo = EXPRESION.tipo }
				expresion = pilaSimbolos.peek();
				tipo = expresion.getTipo();
				break;
			case 31://RETURNVALUE -> lambda
				//{ RETURNVALUE.tipo = "vacio" }
				tipo = "vacio";
				break;
			case 32://LLAMADAFUN -> EXPRESION  LLAMADAFUN2
				//{ if(EXPRESION.tipo = "ok" && LLAMADAFUN2.tipo="ok") then LLAMADAFUN.tipo = "ok"; else LLAMADAFUN.tipo = "error" }
				llamadaFun = pilaSimbolos.peek();
				expresion = pilaSimbolos.get(pilaSimbolos.size()-2);
				if(expresion.getTipo().equals("ok") && llamadaFun.getTipo().equals("ok")) tipo = "ok";
				else tipo = "error";
				break;
			case 33://LLAMADAFUN -> lambda
				//{ LLAMADAFUN.tipo = "ok" }
				tipo = "ok";
				break;
			case 34://LLAMADAFUN2 -> coma  EXPRESION  LLAMADAFUN2 
				//{ if(EXPRESION.tipo != "error","vacio")then LLAMADAFUN2.tipo = LLAMADAFUN21.tipo; else LLAMADAFUN2.tipo = "error"}
				llamadaFun21 = pilaSimbolos.peek();
				expresion = pilaSimbolos.get(pilaSimbolos.size()-2);
				if(!expresion.getTipo().equals("error") && !expresion.getTipo().equals("vacio"))tipo = llamadaFun21.getTipo();
				else tipo = "error";
				
				break;
			case 35://LLAMADAFUN2 -> lambda
				//{ LLAMADAFUN2.tipo = "ok" }
				tipo = "ok";
				break;
			case 36://ARGUMENTOS -> id  ARGUMENTOS2 
				//{ ARGUMENTOS.tipo = ARGUMENTOS2.tipo }
				argumentos2 = pilaSimbolos.peek();
				tipo = argumentos2.getTipo();
				break;
			case 37://ARGUMENTOS -> lambda
				//{ ARGUMENTOS.tipo = "ok" }
				tipo = "ok";
				break;
			case 38://ARGUMENTOS2 -> coma  id  ARGUMENTOS2
				//{ ARGUMENTOS2.tipo = ARGUMENTOS21.tipo }
				argumentos21 = pilaSimbolos.peek();
				tipo = argumentos21.getTipo();
				break;
			case 39://ARGUMENTOS2 -> lambda
				//{ ARGUMENTOS2.tipo = "ok" }
				tipo = "ok";
				break;
			case 40://EXPRESION -> EXPRESION  menor  ARITMETICA 
				//{ if(EXPRESION1.tipo = entero/logico && ARITMETICA.tipo = entero/logico) then EXPRESION.tipo = entero/logico else EXPRESION.tipo = "error" }
				expresion = pilaSimbolos.get(pilaSimbolos.size()-3);
				aritmetica = pilaSimbolos.peek();
				if(expresion.getTipo().equals("entero/logico") && aritmetica.getTipo().equals("entero/logico")) tipo = "entero/logico";
				else {
					tipo = "error";
					if(!expresion.getTipo().equals("entero/logico"))ControladorErrores.addError("La parte izquierda de la comparacion no es de tipo logico");
					else ControladorErrores.addError("La parte derecha de la comparacion no es de tipo logico");
					
				}
				break;
			case 41://EXPRESION -> EXPRESION  menorIgual  ARITMETICA
				//{ if(EXPRESION1.tipo = entero/logico && ARITMETICA.tipo = entero/logico) then EXPRESION.tipo = entero/logico else EXPRESION.tipo = "error" }
				expresion = pilaSimbolos.get(pilaSimbolos.size()-3);
				aritmetica = pilaSimbolos.peek();
				if(expresion.getTipo().equals("entero/logico") && aritmetica.getTipo().equals("entero/logico")) tipo = "entero/logico";
				else {
					tipo = "error";
					if(!expresion.getTipo().equals("entero/logico"))ControladorErrores.addError("La parte izquierda de la comparacion no es de tipo logico");
					else ControladorErrores.addError("La parte derecha de la comparacion no es de tipo logico");
					
				}
				break;
			case 42://EXPRESION -> ARITMETICA  
				//{ EXPRESION.tipo = ARITMETICA.tipo }
				aritmetica = pilaSimbolos.peek();
				tipo = aritmetica.getTipo();
				break;
			case 43://ARITMETICA -> ARITMETICA  mas  SIMPLE 
				//{ if(ARITMETICA1.tipo = entero/logico && SIMPLE.tipo = entero/logico) then ARITMETICA.tipo = entero/logico else ARITMETICA.tipo = "error" }
				aritmetica = pilaSimbolos.get(pilaSimbolos.size()-3);
				simple = pilaSimbolos.peek();
				if(aritmetica.getTipo().equals("entero/logico") && simple.getTipo().equals("entero/logico")) tipo = "entero/logico";
				else {
					tipo = "error";
					if(!aritmetica.getTipo().equals("entero/logico"))ControladorErrores.addError("La parte izquierda de la suma no es de tipo entero");
					else ControladorErrores.addError("La parte derecha de la suma no es de tipo entero");
				}
				break;
			case 44://ARITMETICA -> ARITMETICA  menos  SIMPLE 
				//{ if(ARITMETICA1.tipo = entero/logico && SIMPLE.tipo = entero/logico) then ARITMETICA.tipo = entero/logico else ARITMETICA.tipo = "error" }
				aritmetica = pilaSimbolos.get(pilaSimbolos.size()-3);
				simple = pilaSimbolos.peek();
				if(aritmetica.getTipo().equals("entero/logico") && simple.getTipo().equals("entero/logico")) tipo = "entero/logico";
				else {
					tipo = "error";
					if(!aritmetica.getTipo().equals("entero/logico"))ControladorErrores.addError("La parte izquierda de la resta no es de tipo entero");
					else ControladorErrores.addError("La parte derecha de la resta no es de tipo entero");
				}
				break;
			case 45://ARITMETICA -> SIMPLE 
				//{ ARITMETICA.tipo = SIMPLE.tipo }
				simple = pilaSimbolos.peek();
				tipo = simple.getTipo();
				break;
			case 46://SIMPLE -> negacion  SIMPLE  
				//{ if(SIMPLE1.tipo = entero/logico) then SIMPLE.tipo = entero/logico; else SIMPLE.tipo = "error" }
				simple = pilaSimbolos.peek();
				if(simple.getTipo().equals("entero/logico")) tipo = "entero/logico";
				else {
					tipo = "error";
					ControladorErrores.addError("El elemento que se quiere negar no es de tipo logico");
				}
				break;
			case 47://SIMPLE -> abrePar  EXPRESION  cierraPar
				//{ SIMPLE.tipo = EXPRESION.tipo }
				expresion = pilaSimbolos.get(pilaSimbolos.size()-2);
				tipo = expresion.getTipo();
				break;
			case 48://SIMPLE -> id 
				//{ SIMPLE.tipo = buscaTipoTS(id.ent) }
				id = pilaSimbolos.peek();
				tipo = ControladorTS.buscaTipoTS(id.getLexema());
				break;
			case 49://SIMPLE -> num 
				//{ SIMPLE.tipo = entero/logico }
				tipo = "entero/logico";
				break;
			case 50://SIMPLE -> cad  
				//{ SIMPLE.tipo = cadena }
				tipo = "cadena";
				break;
			case 51://SIMPLE -> id  abrePar  LLAMADAFUN  cierraPar 
				//{ if(LLAMADAFUN.tipo = "ok" && id.tipo = "funcion") then SIMPLE.tipo = id.tipoDevuelto; else SIMPLE.tipo = "error"}
				llamadaFun = pilaSimbolos.get(pilaSimbolos.size()-2);
				id = pilaSimbolos.get(pilaSimbolos.size()-4);
				if(llamadaFun.getTipo().equals("ok") && ControladorTS.buscaTipoTS(id.getLexema()).equals("funcion")){
					tipo = ControladorTS.buscaTipoDevTS(id.getLexema());
				}
				else {
					tipo = "error";
					if(llamadaFun.getTipo().equals("ok"))ControladorErrores.addError("El identificador \""+ id.getLexema() +"\" no esta declarado como funcion");
				}
				break;
			default:
				System.out.println("Error al ejecutar Accion Semantica, numero de regla incorrecto");
				break;
	
		}
		return tipo;
	}
}
