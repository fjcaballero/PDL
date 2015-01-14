@echo off
echo Indique el nombre del fichero que desea analizar
set /p var=
java -jar procesador.jar %var%
echo Pulse cualquier tecla para salir
timeout /t 120 > nul