Proyecto Procesadores de Lenguajes 2014/2015
==
Este repositorio contiene una versión actualizada de los ficheros para construir un procesador de lenguajes para el lenguaje *javascript*

Configuración del repositorio:

    Establish remote:

    git remote add upstream http://github.com/jorgegvalencia/pdl_project_2014-2015

    Fetch any changes to it:

    git fetch upstream

    Checkout the local master branch of your fork:

    git checkout master

    Merge changes from the remote into your master branch:

    git merge upstream/master

Para trabajar en Eclipse o Netbeans asegurarse de que se tiene el fichero .gitignore dentro del directorio del proyecto con el siguiente contenido:

	.classpath
	.project
	#.gitignore
	*.class

Esto es para evitar que el repositorio remoto contenga ficheros distintos a los ficheros fuente o que contenga ficheros que generen problemas con los workspace de los IDE.

IMPORTANTE: Añadir al .gitignore el nombre de cualquier fichero distinto de los directorios del proyecto (global, lexico...) y del README.md que se encuentren en el directorio pdl_project_2014-2015

Por ejemplo:

Si se tiene un fichero llamado prueba.txt en el directorio el contenido del .gitignore tendrá que ser:

	.classpath
	.project
	#.gitignore
	*.class
	prueba.txt

En el caso de que al crear el proyecto en Eclipse o Netbeans se creen ficheros .gitignore dentro de los subdirectorios del proyecto, descomentar la linea #.gitignore:

	.classpath
	.project
	.gitignore
	*.class

En cualquier caso, si se hacen los commit desde la aplicación de escritorio de Github, asegurarse de que los ficheros que NO hay que subir al repositorio remoto esten desmarcados (caja de la izquierda sin el tick), si es que aparece alguno.

