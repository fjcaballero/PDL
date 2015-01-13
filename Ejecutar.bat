@echo off
echo Indique el nombre del fichero que desea analizar
set /p var=
java -jar procesador.jar %var%
timeout /t 30 > nul