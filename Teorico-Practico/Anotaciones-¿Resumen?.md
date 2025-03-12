# Sintaxis y Semantica

### ¿Qué veremos?

* Aprender conceptos fundamentales (Funcionales) de los lenguajes de programación.

* Aprender a identificar conceptos en lenguajes de programación.

* Evaluar el impacto de las deciciones de diseño en ñenguajes de programación para identificar cuando elegir el lenguaje mas adecuado a una situación.

## Sintaxis

* Un **Programa** es la descripción de un proceso dinámico
    
    - Lo que tiene que ver con la forma recibe el nombre de **Sintaxis**.

    - Lo que tiene que ver con el significado recibe el nombre de **Semántica**

* La **Implementación** de un lenguaje de programación debe transformar la sintaxis de un programa en instrucciones de máquina que se pueden ejecutar para que suceda la secuencia de acciones que se pretendía.

### Transformación de sintaxis a semántica

Un **lenguaje de programación** es un conjunto de abstracciones y empaquetamientos quizás sin correspondencia directa con la máquina.

* Es necesario **Traducir** lenguaje de programación a instrucciones de máquina, el **Compilador** es el que hace esa traducción (tambien se encarga de procesar la sintaxis de los lenguajes de programación).

* Un **Interprete** (Como el de python) puede combinar traducción y ejecución (incluye un compilador y la ejecución).

### ¿Qué es un Compilador?

Un programa que lee un programa escrito en lengujae origen y lo traduce a un programa equivalente (con el mismo significado) en un lenguaje destino.
<br/>Tenemos dos componentes:

1) Entender el programa (asegurarse de que es correcto).

2) Reescribir el programa.

* Normalmente, el lenguaje origen es de alto nivel y el destino es de bajo nivel.

### Fases de un compilador

* Análisis léxico.

* Análisis sintático.

* Análisis semántico.

* Generación de código intermedio (independiente de máquina).

* Optimización de código intermedio.

* Generación de código destino (dependiente de máquina).

* Optimización de código destino.

### Procesos de compilación

Programa origen, con macros ---> **Preprocesador** ---> Programa origen ---> **Compilador** ---> Programa en ensamblador destino ---> **Ensamblador** ---> Código máquina reubicable ---> **Linker** ---> Código máquina absoluto.

### Front-end y Back-end

* Front end análisis

Programa origen (cadena de caracteres) ---> [Scanner](#scanner-análisis-léxico) (análisis léxico) ---> Tokens ---> [Parser](#parser-análisis-sintático) (análisis sintático) ---> Árbol sintático ---> **Análisis semántico y generación de código intermedio** ---> árbol sintáctico abstracto.

* Back end síntesis

Árbol sintáctico abstracto ---> **Optimización de código independiente de máquina** ---> Formaintermedia modificada ---> **Generación de código destino** ---> código destino ---> **Optimización de código dependiente de máquina** ---> Código destino modificado.

### Scanner: Análisis léxico

Se divide un programa (secuencia de caracteres) en palabras (Tokens).

[Ejemplo scanner](../Imagenes/scanner.png)

### Parser: Análisis sintático

* Comprueba si la secuencia de tokens (palabras) conforma a la especificación gramatical del lenguaje y genera el árbol sintáctico.

* La especificación gramatical suele representarse con una gramática independiente de contexto (*Context free grammar*), que también le da forma al árbol sintáctico.

### Gramáticas independientes de contexto

* Se definen **Categorias de construcciones del lenguaje**, por ejemplo:

    - **Sentencias** (*Statements*)

    - **Expresiones** (*Expressions*)

    - **Declaraciones** (*Declarations*)

```
<statement>     ::= <for-statement> | <if-statement> | <assigment>
<for-statement>     ::= for(<expression>; <expression>; <expression>) <statement>
<assignment>    ::= <identifier> := <expression>
```

### Ejemplo: Micro Pascal

```
<Program>    ::= program <id> (<id> <More_ids>); <Block>.
<Block>    ::= <Variables> begin <Stmt> <More_Stmts> end
<More_ids>    ::= , <id> <More_ids>
    | ε
<Variables>    ::= var <id> <More_ids> : <Type> ; <More_Variables>
    | ε
<More_Variables>    ::= <id> <More_ids> : <Type> ; <More_Variables>
    | ε
<Stmt>    ::= <id> := <Exp>
    | if <Exp> then <Stmt> else <Stmt>
    | while <Exp> do <Stmt>
    | begin <Stmt> <More_Stmts> end
<Exp>    ::= <num>
    | <id>
    | <Exp> + <Exp>
    | <Exp> - <Exp>
```

### Ejemplo de análisis sintáctico

`id1 := id2 + id3 * 60`

[Ejemplo de análisis sintáctico](../Imagenes/ejemploAnalisisSintaticopng)

### Análisis Semántico

* El compilador trata de ver si un programa **Tiene sentido** analizando su árbol sintáctico.

* Un programa sin errores gramaticales no siempre es correcto, puede haber problemas de tipo

`pos = init + rate * 60`

* ¿Qué pasa si `pos` es una clave y `init` y `rate` sin enteros? 

    - El parser no puede encontrar este tipo de errores.

    - El análisis semántico encuentra este tipo de errores.

