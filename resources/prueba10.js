/* Prueba con errores, condicion erronea */

var ent
var cad
var log

ent = 1
cad = "hola mundo"
log = ent < 2
if(log){
    document.write(cad)
}
if(cad){
    document.write("Error")
}