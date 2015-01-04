var mi_numero
var mi_cadena
document.write(mi_cadena)
/* Comentario */
function calcula (numero) {
	switch (numero)
{
	case 1: mi_numero += 3 + numero - (numero - 5)
	case 0: document.write ("El factorial de siempre es 1.\n") 
	break
	default:
		if (numero < 0)
		{
			prompt(mi_cadena)
		}
}

	return numero + mi_numero
}
function dias (m, a)	
{
	var maxdias
	switch (m)
	{
		case 1: case 3: case 5: case 7: case 8: case 10: case 12:
			maxdias = 31 
			break
		case 4: case 6: case 9: case 11:
			maxdias = 30 
			break
		case 2: maxdias = 29
			break
		default: maxdias = 0
	}
	return maxdias
}
function imprime (msg, f)	/* función que recibe dos argumentos */
{
	document.write (s)
	document.write (msg)
	document.write (f)
	document.write ("\n")	/* imprime un salto de línea */
	return	/* finaliza la ejecución de la función (en este caso, se podría omitir) */
}

s = "El factorial "

document.write (s)
document.write ("\nIntroduce un 'número'.")
prompt (num)	/* se lee un número del teclado y se guarda en la variable global num */
