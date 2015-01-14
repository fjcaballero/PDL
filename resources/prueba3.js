/* Prueba para comprobar las sentencias if y switch-case */
var maxdias
prompt(m)
m = 2
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
document.write(maxdias)