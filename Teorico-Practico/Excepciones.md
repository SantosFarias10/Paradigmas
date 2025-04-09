# Excepciones

## Excepciones: salidas estructurada

* **Terminar** una parte de la computacion

    - Saltar fuera de una construccion.

    - Pasar datos como parte del salto.

    - Retornar al lugar mas reciente donde tratar la excepcion.

    - En el proceso de retorno se pueden desalojar los active records inecesarios.

* Compuesta de **Dos Construcciones** linguisticas

    - Un **Manejador** de excepciones (*exception handler*), que captura (*catch*) la excepcion y la trata.

    - Sentencias o expresion que **Levanta** (*raise*) o tira (*throw*) la excepcion.

* Uso: normalmente para una condicion excepcional, pero no necesariamente.

## Diferencia con GO TO

* Solo se puede **salir** de una funcion, no saltar a cualquier parte del programa.
    
    - No es spaguetti, no se cruzan las ramas del arbol.

* Se pueden pasar **Datos** como parte del salto, para recuperarse de la excepcion o como informacion para el usuario, pero estos datos tienen **Alcance dinamico**: se obtiene del entorno en el que se ejecuta la funcion, no del entorno en el que se definio.
