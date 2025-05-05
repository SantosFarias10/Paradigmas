# Programacion Orientada a Objetos

## Objetivos

* Desarrollo de programas modulares

    - Refinamiento incremental.

    - Interfaz, especificacion e implementacion.

* Soporte de los lenguajes para la modularidad.

    - Abstraccion procedural.

    - Tipos abstractos de datos.

    - Paquetes y modulos.

    - Abstraccion generica (con parametros de tipo).

## Modularidad: Conceptos basicos

* Componentes:

    - **Unidad** de programa **Con sentido**: Funcion, estructura de datos, modulo, etc.

* Interfaz:

    - Tipos y operaciones definidos dentro de un componente que son **Visibles** fuera del componente.

* Especificacion:

    - **Comportamiento** esperado de un componente, expresado como una propiedad **Observable** a traves de la interfaz.

* Implementacion:

    - Estructura de datos y funciones **Dentro** del componente.

### Ejemplo: Componente funcion

* Componente

    - Funcion que calcula la raiz cuadrada.

* Interfaz

    - `float sqroot (float x)`.

* Especificacion

    - Si `x > 1`, entonces `sqroot(x) * sqroot(x) ≈ x`

* Implementacion

```
float sqroot (float x) {
    float y = x/2; float step = x/4; int i;

    for (i = 0; i < 20; i++){ if ((y*y) < x) y = y+step; else y = y-step; step = step/2; }

    return y;
}
```

### Ejemplo: Tipo de datos

* Componente

    - Cola de prioridad: Estructura de datos que devuelve elementos en orden de prioridad descendente.

* Interfaz

    - Tipo: `pq`

    - Operaciones:

        * empty:     `pq`.

        * insert:    `elt * pq -> pq`.

        * deletemax: `pq -> elt * pq`.

* Especificacion

    - **Insert**: Añade un elemento al conjunto de elementos guardados.

    - **Deletemax**: Devuelve el elemento maximo y compone el resto de elementos en una cola de prioridad.

* Algoritmo que usa la cola de prioridad (*heap sort*):

```
begin
    create empty pq s
    insert each element from array into s
    remove elts in decreasing order and
    place in array
end
```

## Herramientas de los lenguajes para la Abstraccion

### Tipos abstractos de datos (TADs)

* Idea 1: **Separar la interfaz de la implementacion**.

    - Ejemplo:

        * Los conjuntos tienen operaciones: `empty`, `insert`, `union`, `is_member?`, etc.

        * Los conjuntos se implementan como: `lista enlazada`, etc.

* Idea 2: **Usar comprobacion de tipos para forzar la separacion**.

    - El programa cliente solo tiene acceso a las operaciones de la interfaz.

    - La implementacion encapsulada en el constructor TAD.

### Modulos

* Construccion general para ocultar informacion.

    - Modulos (Modula), paquetes (Ada), estructura (ML).

* Interfaz: Conjunto de nombres y sus tipos.

* Implementacion:

    - Declaracion para cada entrada en la interfaz.

    - Declaraciones extra que estan ocultas.

### Abstracciones Genericas

Parametriza los modulos por tipos.

* Implementaciones generales, que se pueden instanciar de muchas formas: La misma implementacion para multiples tipos.

* Paquetes genericos en Ada, `templates` en C++ (especialmente las de la STL - *Standard Template Library*), fuctores en ML.

### Templates en C++

* Mecanismo de parametrizacion de tipos `template<class T>...` indica el parametro de tipo `T`.

    - C++ tiene templates de clases y de funcion.

* Se instancia en tiempo de ligado.

    - Se crea una copia del template generado para cada tipo.

    - ¿Por que duplicar codigo?

        * Tamaño de variables locales en el activation record.

        * Ligado a las operaciones del tipo instanciado.

* Por ejemplo: Funcion `swap` (sobrecarga y polimorfismo).

### Ejemplo de template

```
template <typename T>
void swap(T& x, T& y){
    T tmp = x; x = y; y = tmp;
}
```

### Diferencias entre ML y C++

* ML:

    - `swap` se compila a una funcion, y el `typechecker` (comprobador de tipos) determina como se puede usar.

* C++:

    - `swap` se compila a formato `linkeable`, y el `linker` duplica el codigo para cada tipo con el que se usa.

* ¿Por que la diferencia?

    - ML: La `x` local es un puntero a un valor en el heap, con el tamaño constante.

    - C++: La `x` local es un puntero a un valor en el stack, su tamaño depende del tipo.

## Propiedades importantes de la orientacion a objetos

### Objetos

* Un objeto consiste de:

    - Datos ocultos: Variables de la instancia (datos del miembro), posiblemente funciones ocultas.

    - Operaciones publicas: Metodos (funciones del miembro), puede tener variables publicas en algun mensaje.

### Lookup dinamico

* En programacion convencional, el significado de una operacion con los mismos operandos es siempre el mismo.

`operacion (operandos)`

* En programacion orientada a objetos: `object -> message (arguments)`. El codigo depende del objeto y el mensaje.

#### Sobrecarga vs. lookup dinamico

* En programcion convencional `add (x, y)` la funcion `add` tiene signidicado fijo.

* Para sumar dos numeros `x -> add (y)` tenemos un `add` distinto si `x` es entero, complejo, etc.

* Semejantes a la sobrecarga, con una diferencia critica: la sobrecarga se resuelve en tiempo de compilacion, mientras que el lookup dinamico se resuelve **en tiempo de ejecucion**.

### Encapsulacion

* El programador de un concepto tiene una vista detallada.

* El usuario de un concepto tiene una vista abstracta.

* La encapsulacion separa estas dos vistas, de forma que el codigo de cliente opera un conjunto fijo de operaciones que provee el implpementador de la abstraccion.

### Subtipado y herencia

* La interfaz es la vista externa de un objeto.

* El subtipado es una relacion entre interfaces.

* La implementacion es la representacion interna de un objeto.

* La herencia es una relacion entre implementaciones, de forma que nuevos objetos se pueden definir reusando implementaciones de otros objetos.

#### Interfaces de objetos

* Interfaz: Los mensajes que extiende de un objeto.

* Por ejemplo: `Punto`

    - `x-coord`: Devuelve la coordenada `x` de un punto.

    - `y-coord`: Devuelve la coordenada `y` de un punto.

    - `move`: Metodo para cambiar de ubicacion.

* La interfaz de un objeto es su tipo.

### Subtipado

* Si la interfaz A contiene todos los elementos de la interfaz B, entonces los objetos de tipo A tambien pueden usar como objetos de tipo B.

* Por ejemplo:

    * Punto: `x-coord`, `y-coord`, `move`.

    * Punto_Coloreado: `x-coord`, `y-coord`, `color`, `move`, `change-color`.

La interfaz de *Punto_Coloreado* contiene la de *Punto*, por lo tanto *Punto_Coloreado* es un subtipo de *Punto*.

#### Ejemplo

```
class Point
    private
        float x, y
    public
        point move (float dx, float dy);

class Colored_point
    private
        float x, y; color c
    public
        point move (float dx, float dy);
        point change_color (color newc);
```

* Subtipado: `Colored_point` se puede usar en lugar de `Point`: Propiedad que usa el **Cliente**.

#### Ejemplo

```
class Point
    private
        float x, y
    public
        point move (float dx, float dy);

class Colored_point from Point
    private
        color c
    public
        point change_color (color newc);
```

* Herencia: `Colored_point` se puede implementar reusando la implementacion de `Point`: Propiedad que usa el `implementador`.

## Estructura de un programa orientado a objetos

* Agrupar datos y funciones.

* Clase: Define el comportamiento de todos los objetos que son instancias de la clase.

* Subtipado: Organiza datos semejantes en clases relacionadas.

* Herencia: Evita reimplementar funciones ya definidas.

### Ejemplo: Biblioteca Geometrica

* Definimos el concepto general `forma`.

* implementamos dos formas: `circulo`, `rectangulo`.

* Funciones sobre formas: `centro`, `mover`, `rotar`, `imprimir`.

* Anticipar como podria evolucionar la biblioteca.

#### Formas

* La interfaz de cada `Forma` debe incluir `centro`, `mover`, `rotar`, `imprimir`.

* La diferentes formas se implementan distinto:

    - `Rectangulo`: Cuatro puntos que representan las esquinas.

    - `Circulo`: Punto central y radio.
