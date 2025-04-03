## Paradigmas imperativos

* Un paradigma de programacion es una **configuracion frecuente** de caracteristicas de lenguajes de programacion.

* El paradigma imperativo es el mas antiguo y el que estuvo siempre mas pegado a la maquina.

* Tradicionalmente se ha opuesto al paradigma **funcional**, pero la mayor parte de lenguajes integran ideas de ambos paradigmas.

## Conceptos fundamentales

* Operacion basica: **asignacion**

    - Tiene efectos secundarios: cambia el estado de la maquina.

* Sentencias de **control** de flujo.

    - Condicionales y sin condicion, ramas, ciclos.

* Bloques, para obtener **referencias locales**.

* **Parametrizacion**.

## Elementos basicos

* [Declaraciones de variables](#declaraciones-de-variables) (normalmente tipadas).

* [Expresiones y sentencias de asignacion](#expresiones-y-sentencias-de-asignacion).

* [Sentencias de **control de flujo**](#sentencias-de-control-de-flujo) (normalmente estructuradas).

* [**Alcance léxico** y bloques, para poder tener variables con referencias locales](#alcance-léxico-y-bloques).

* [Declaraciones y definiciones de **procedimientos y funciones** (bloques parametrizados)](#declaraciones-y-definiciones-de-procedimientos-y-funciones).

## Declaraciones de variables

* Las declaraciones tipadas restringen los posibles valores de una variable en la ejecucion del programa.

    - Jerarquia de tipos *built-in* o personalizada.

    - Inicializacion.

* Uso de memoria: ¿Cuanto espacio de memoria reservar para cada tipo de variable?

### Ubicacion y valores de variables

* Al declarar una **variable** la estamos ligando a una **ubicacion en memoria** (global, en la pila o en el heap).

* **l-valor**: ubicacion en memoria (direccion de memoria)("puntero").

* **r-valor**: valor que se guarda en la ubicacion de memoria identificada por el l-valor.

* **identificador**: nombre de la variable en el texto del programa.

* Al crear una variable se crea una celda de referencia del tipo correcto (*alloc*)

* Diferentes identificadores (*labels*, nombres de variable) pueden referirse a una misma ubicación de memoria (*aliasing*). Y un mismo identificador puede referirse a dos variables diferentes según el alcance y tiempo de vida de cada una.

## Expresiones y sentencias de asignacion

### Variables y asignacion

* En la parte derecha de una asignacion esta el r-valor de la variable y en la parte izquierda esta su l-valor.

`x:= 1` significa "guardemos `1` como r-valor de la ubicacion señalada por el l-valor ligado a `x`".

`x:= x+1` significa "obtengamos el r-valor que encontrams en el l-valor ligado al identificador de variable `x`, sumesmole `1`, y guardemos el resultado como r-valor de la ubicacion señalada por el l-valor ligado a `x`".

* Una expresion que no tenga l-valor no puede aparecer en la parte izquierda de una asignacion.

* ¿Que expresiones no tienen l-valor? => `-1=x+1`, `++x++`

* El r-valor de un puntero es el l-valor de otra variable (el valor de un puntero es una direccion).

* Las constantes solo tienen r-valor.

* El nombre de una funcion solo tiene l-valor.

## Sentencias de control de flujo

### Flujo de control estructurado

* Se piensa como secuencial

    - Las instrucciones se ejecutan en el orden en el que estan escritas.
    
    - En algunos casos soporta ejecucion concurrente.

* Un programa es **estructurado** si el flujo de control es evidente en la estructura sintactica del texto del programa.

    - Util para poder razonar intuitivamente leyendo el texto del programa.

    - Se crean construcciones del lenguaje para patrones comunes de control: iteracion, seleccion, procedimientos/funciones...

## Alcance léxico y bloques

### Estilo moderno

* Construcciones estandar que estructuran los saltos

    - `if ... then ... else ... end`.

    - `while ... do ... end`

    - `for ... { ... }`

    - `case ...`

* Agrupan el codigo en bloques logicos.

* Se evitan saltos explicitos (excepto retorno de funcion).

* No se puede saltar al medio de un bloque o funcion.

### Manejo de memoria

* El **stack** tiene los datos sobre entrada y salida de bloques.

* El **heap** tiene datos de diferentes lifetime.

* El **puntero de entorno** (*environment*) apunta a la posicion actual en el stack

* Al entrar a un bloque: se añade un nuevo activation record al stack.

* Al salir de un bloque: se elimina el activation record mas reciente del stack.

### Alcance y lifetime

* **Alcance**: region del texto del programa donde una declaracion es visible (su bloque).

* **Lifetime**: periodo de tiempo en que una ubicacion de memoria es asignada a un programa (tiempo que existe ese r-valor).

* Una variable puede estar en su lifetime pero no tener alcance, porque una declaración con su misma etiqueta en un bloque más interno la tapa, produciendo un hueco de alcance (*hole in the scope()*).

## Declaraciones y definiciones de procedimientos y funciones

### Abstraccion procedural

* Un procedimiento es un **alcance parametrizado con nombre**.

    - El programador se puede abstraer de los detalles de implementacion, enfocandose en la interfaz.