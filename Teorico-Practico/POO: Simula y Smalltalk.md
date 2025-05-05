# Programacion Orientada a Objetos: Simula t Smalltalk

## Simula

### Objetos en Simula

* **Clase**: Un procedimiento que devuelve un puntero al activation record en el que se ejecuta.

* **Objeto**: Activation record que se genera al llamar a una clase.

* **Acceder a un objeto**: Acceder cualquier variable o procedimiento local.

* **Manejo de memoria**: Recoleccion de basura.

### Como se representan los objetos

Un objeto se representa con un activation record con un access link para encontrar las variables globales con alcance estatico.

### Ejemplo: Circulo y lineas

* Problema: Encontrar centro y radio del Circulo que pasa por los puntos `p`, `q` y `r`.

* Solucion:

    - Dibujar circulos que se intersectan.

    - Dibujar lineas a traves de la interseccion de los ciruclos.

    - La interseccion de las lineas es el centro del circulo que se busca.

#### Modelar esto en Simula

* Los puntos, lineas y circulos son objetos.

* Operaciones asociados a los objetos:

```
punto:

equality(anotherPoint): boolean
distance(anotherPoint): real

linea:

parallelto(anotherLine): boolean
meets(anotherLine): REF(Point)

ciruclo:

intersects(anotherCircle): REF(Line)
```

### Clase punto en Simula

```
class Point(x,y); real x,y;
    begin
        boolean procedure equals(p); ref(Point) p;
            if p =/= none then
                equals:= abs(x - p.x) + abs(y - p.y) < 0.00001
        real procedure distance(p); ref(Point) p;
            if p == none then error else
                distance:= sqrt((x - p.x)**2 + (y - p.y)**2);
end ***Point***

p:- new Point(1.0, 2.5);
q:- new Point(2.0, 3.5);
if p.distance(q) > 2 then ...
```

* El argumento `p` es un puntero a `Point`.

* Un `ptr` no inicializado tiene el valor `none`.

* Al ultimo es la asignacion de los punteros.

### Clase linea en Simula

```
class Line(a, b, c); real a, b, c;              <--- Variables locales
    begin
        boolean procedure parallelto(l); ref(Line) l;       <---|
            if l =/= none then parallelto:= ...                 |
        ref(Point) procedure meets(l); ref(Line) l;             |- Procedimiento
            begin real t;                                       |
                if l =/= none and ~parallelto(l) then ...       |
            end;                                            <---|
        real d; d:= sqrt(a**2 + b**2);                      <---|
        if d == 0.0 then error else                             |
            begin                                               |
                d:= 1/d;                                        |- Inicializacion
                a:= a*d; b:= b*d; c:= c*d;                      |
            end;                                            <---|
end ***Line***
```

### Clases derivadas en Simula

* Cuando se declara una clase se le puede prefijar el nombre de otra clase.

* Un objeto de una clase "prefijada" es la concatenacion de objetos de cada clase del prefijo.

### Subtipado

* El tipo de un objeto es su clase.

* El tipo de una subclase se trata como un subtipo del tipo asociado con la superclase.

* Ejemplo:

    - class A(...); ...

    - A class B(...); ...

    - ref (A) a:- new A(...)

    - ref (B) b:- new B(...)

    - a:= b (legal ya que B es una subclase de A).

    - b:= a (Tambien legal, pero hay que comprobarlo en tiempo de ejecucion).

### Que no tiene Simula

* Encapsulamiento: Se pueden acceder a todos los datos y funciones.

* Sin mecanismos self/super (a diferencia de Smalltalk), pero se puede usar la expresion `this <class>` para referirse al objeto en si mismo.

* Sin variables de clases, pero con variables globales.

* Sin excepsiones.

### Resumen

* Una clase es un procedimiento que devuelve un puntero a un activation record, el codigo de inicializacion se ejecuta siempre como cuerpo del procedimiento.

* Un objeto es una clausura creada por una clase.

* Sin Encapsulamiento.

* Subtipado mediante jerarquia de clases.

* Herencia por prefijado de clases.

## Smalltalk

* Extiende y desarrolla la metafora de objetos: 

    - Algunas ideas de Simula, pero muy distinto.

    - Todo es un objeto, incluso una clase (como en Lisp "todo es una lista").

    - Todas las operaciones son mensajes a objetos.

    - Muy flexible y poderoso: si un objeto que recibe un mensaje que no entiende, trata de inferir que puede hacer.

### Terminologia Smalltalk

* Objeto: Instancia de una clase.

* Clase: Define el comportamiento de sus objetos.

* Subclase: Clase definida como modificaciones incrementales a una superclase.

* Selector: Nombre de un mensaje.

* Mensaje: Selector con valores para sus parametros.

* Metodo: Codigo que usa una clase para responder a un mensaje.

* Variable de instancia: Datos guardados en un objeto.

### Tipos de objetos

* Cada objeto tiene una interfaz

    - Interfaz = Metodos de instancia declarados en la clase.

    - En una forma de tipo: solo los nombres de los metodos, nada sobre los argumentos.

* Uso de objetos con tipo: Cuando se envia un mensaje a un objeto. La expresion anda si el mensaje esta en la interfaz.

### Encapsulamiento en Smalltalk

* Los metodos son publicos.

* Las variables de instancia estan ocultas.

    - Invisibles para otros objetos...

    - ...Pero las pueden manipular los metodos de subclase.

        * Esto limita la forma de establecer invariantes. Por ejemplo: Una superclase mantiene una lista ordenada de mensajes con algun selector, por ejemplo, insert.

        * Una subclase puede acceder esta lista directamente y reordenarla.

### Subtipado

* Relacion entre interfaces.

    - Supongamos que la expresion tiene sentido.

    - Substituimos `p` por `q` si la interfaz de `q` contiene a la interfaz de `p`.

* Subtipado:

    - Si la interfaz es un superconjunto, entonces es un subtipo.

    - A veces llamado "conformidad".

### Subtipado y herencia

* El subtipado es implicito.

    - No es parte del leguaje.

    - Es un aspecto importante de como se construyen los sistemas.

* La herencia es explicita.

    - Se usa para implementar sistemas.

    - No fuerza la relacion a subtipado.

### Flexibilidad de Smalltalk

* Expresividad: ¿Se pueden definir las construcciones del lenguaje en el lenguaje mismo?

    - Lisp cond: Lisp permite formas especiales definidas por el usuario.

    - ML datatype: Suficiente para definir listas polimorficas, equivalentes al tipo lista built-in.

    - ML overloading: No esta disponible para el programador.

* Smalltalk es expresivo en este sentido.

    - Muchas construcciones primitivas en otros lenguajes se pueden definir en Smalltalk.

### Booleanos y Bloques Smalltalk

* El valor Booleano es un objeto con `ifTrue: ifFalse:`.

    - Clase `boolean` con subclases `True` y `False`.

    - `True ifTrue:B1 ifFalse:B2` ejecuta `B1`.

    - `False ifTrue:B1 ifFalse:B2` ejecuta `B2`.

Expresion de ejemplo:

`i < j ifTrue: [i add 1] ifFalse: [j subtract 1]`.

* `i < j` es expresion booleana, produce un objeto booleano.

* Los argumentos son bloques, objetos que ejecutan metodos.

* Los booleanos y los bloques son muy comunes:

    - Hay una optimizacion para booleanos.

    - Sintaxis especial para bloques.

###  Test de Ingallls

* Test para saber si algo es "orientado a objetos"

    - Se puede definir nuevos tipo de entero, poner tus nuevos enteros en rectangulos (que ya son parte del sistema de ventanas), pedirle al sistema que oscurezca un rectangulo, y que todo funcione?

    - Smalltalk pasa, C++ falla.

### Operaciones de enteros en Smalltalk

* Expresiones de enteros: `x plus: 1 times: 3 plus: (y plus 1) print`

* Propiedades

    - Todas las operaciones se ejecutan enviando mensajes.

    - Si `x` es de algun nuevo tipo de entero, la expresion tiene sentido siempre que `x` tenga los metodos `plus`, `times`, `print`.

En realidad el compilador tiene algunas optimizaciones hardcodeadas, pero se revierte a esto si `x` no es un entero built-in.

### Costes y beneficios del "verdadero OO"

* Porque sirve el test de Ingallls?

    - Asegura que todo es un objeto.

    - Facilita la extencion de los programas.

* Cual es el coste de implementacion?

    - Cada operacion sobre enteros requiere una llamada a metodos (a no ser que haya optimizaciones de compilador).

### Resumen

* Clase: Crea objetos que comparten metodos. Punteros a template, diccionarios, clase madre.

* Objetos: Creados por una clase, contienen variables de instancia.

* Encapsulacion: Los metodos son publicos, las variables de instancia son ocultas.

* Subtipado: implicito, sin sistemas de tipo estatico.

* Herencia: Subclase, self, super.
