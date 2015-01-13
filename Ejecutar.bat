@echo off
echo Indique el nombre del fichero que desea analizar
set /p var=
java -jar pdl.jar Procesador %var%
timeout /t 30 > nul