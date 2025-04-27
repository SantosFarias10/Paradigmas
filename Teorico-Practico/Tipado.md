# Tipado

## ¿Que son y para que sirven un sistema de tipos?

### ¿Que es un tipo?

Un tipo es una **coleccion de valores** computables que comparten alguna propiedad estructural, por ejemplo los `int`, `string`, `int -> bool`, `(int -> int) -> bool`. La distincion entre conjuntos que son tipos y conjuntos que no lo son depende del lenguaje.

### ¿Para que sirven los tipos?

* Organzacion y documentacion de programas:

    - Se usan tipos distintos para usos distintos: Representan los conceptos del dominio del problema.

    - Indican el uso de los identificadores de variables: Se pueden chequear, a diferencia de los comentarios.

* Identificar y prevenir errores:

    - En tiempo de compilacion o de ejecucion, se puede prevenir calculos sin sentido como `3 + true - "Bill"`.

* Optimizacion del manejo de memoria (porque se puede calcular el tamaño en los datos).

### Sistemas de tipos

* **Jerarquia** de tipos: Los tipos se pueden organizar con relacion de subtipos.

* **Tipos Basicos** (o primitivos): Indivisibles (atomicos).

* **Tipos Compuestos**: Tienen diferentes partes.

* **Tipos definidos por usuarios** (vs. built-in).

    - En tiempo de compilacion o de ejecucion.

    - Problemas para detectar errores por los metodos tradicionales.

    - Si se definen en tiempo de ejecucion, no se puede hacer el arbol sintactico, ya que el analisis sintactico pasa a ser Turing completo.

### Operaciones con valores tipados

* Muchas operaciones se definen en funciones de tipos:

    - Enteros: `+ - / * < > ...`.

    - Booleanos: `^ v ¬ ...`.

* El conjunto de valores suele ser finito por su representacion binaria en la computadora:

    - Enteros de 32-bits en C: -2147483648 a 2147483647.

    - La suma y la resta pueden sobrepasar el rango finito, asi que a veces ` a + (b + c) != (a + b) + c`.

    - Excepciones: Tipo entero ilimitado en Haskell.

## Tipado

### Tipado estatico vs. dinamico

* El sistema de tipos impone restricciones en el uso de valores, por ejemplo solo valores numericos en la suma. No se puede expresar sintacticamente en una EBNF.

* **Tipado estatico**: Los tipos de las variables se fijan en tiempo de compilacion.

* **Tipado dinamico**: El tipo de una variable depende de su valor, y eso solo se puede determinar en tiempo de ejecucion.

### Tipado fuerte vs. debil

* **Tipado Fuerte**: Se detectan todos los errores de tipo, ya sea en tiempo de compilacion o de ejecucion. Las conversiones de tipo tienen que ser explicitas.

### Comprobacion en tiempo de compilacion o ejecucion

* Comprobacion de tipo (*type-checking*) en tiempo de compilacion (C, ML) o ejecucion (Perl, JS). 

* Ventajas y desventajas:

    - Ambos previenen errores de tipo.

    - En tiempo de ejecucion hace la ejecucion mas lenta.

    - En tiempo de compilacion restringe la flexibilidad del programa.

### Expresividad vs. seguridad

* En JS podemos escribir una funcion como `fuction f(x) { return x < 10 ? x : x(); }` en algunos casos producira un error de tipos, en otros no.

* El tipado estatico es siempre conservador:

```
if (big-hairy-boolean-expresion)
    then f(5);
    else f(10);
```

    - No puede decidir en tiempo de compilacion si habra un error en ejecucion, asi que no se puede definir la funcion arriba.

## Sistemas de tipos y tipos

### Tipos primitivos

* Booleanos.

* Enteros.

* Reales.

* Caracteres.

### Tipos construidos

* Enumeraciones.

* Tuplas.

* Registros (records).

* Listas.

* Arreglos.

* Arreglos Asociativos.

* Clases.

* Funciones.

### Subtipos

* Un **Subtipo** es un tipo con algunas restricciones en sus valores u operaciones.

* Algunos lenguajes permiten especificarlos directamente.

* Concepto basico para POO.

## Sobrecarga y polimorfismo

### Sobrecarga

* Sobrecargamos un operador o funcion cuando su significado varia dependiendo de los tipos de sus operandos o argumentos o resultados. Por ejemplo: 

    - Suma: Enteros y decimales, tambien concatenacion de string en Java.

    - Class PrintStream en Java: `print` y `println` definidos para boolean, char, int, long, float, double, char[], String, Object.

### Polimorfismo y generico

* Un operador o funcion es polimorfico si se puede aplicar a cualquier tipo relacionado. Permite reuso de codigo. 

    - Por ejemplo: Funciones genericas en C++.

        * La funcion hace exactamente lo mismo independientemente del tipo de los argumentos.

        * En cada uso, el compilador sustituye el tipo de los argumentos por los parametros de los tipo de la plantilla.

### Polimorfismo vs. sobrecarga

* Polimorfismo parametrico:

    - Un solo algoritmo puede tener diferentes tipos.

    - Las variables de tipo se reemplaza por cualquier tipo.

    - `f: t->t  =>  f: int->int, f: bool->bool, ...`

* Sobrecarga:

    - Un solo simbolo se refiere a mas de un algoritmo.

    - Cada algoritmo tiene diferentes tipos.

    - Se selecciona el algoritmo dependiendo del contexto de tipos.

    - Los tipos pueden ser arbitrariamente distintos.

## Inferencias de tipos

### Comprobacion vs. inferencias de tipos

* Comprobacion de tipo estandar

```
int f(int x) { return x+1; };
int g(int y) { return f(y+1)*2; };
```

    - Mirar el cuerpo de la funcion y usar los tipos declarados de los identificadores para comprobar.

* Inferencia de tipos:

```
int f(x) { return x+1; };
int g(y) { return f(y+1)*2; };
```

    - Mirar el codigo sin informacion de tipo y **Figurarse** que tipos se podrian haber declarado.


