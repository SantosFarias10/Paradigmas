# Programacion Orientada a Objetos: Java

## Objetivos de diseño

* Portabilidad: A todo el internet: PC, Unix, Max.

* Confiabilidad: Evitar crashes y mensajes de error.

* Seguridad: Programadores Maliciosos.

* Simplicidad y familiaridad: Atractivo para programadores, mas sencillo que C++.

* Eficiencia: Importante pero secundaria.

## Decisiones de diseño generales

* Simplicidad:

    - Casi todo es un objeto.

    - Los objetos estan en el heap, y se acceden a traves de puntero.

    - No hay funciones, ni herencia multiple,ni go to, ni sobrecarga de operadores y pocas coerciones automaticas de tipo.

* Portabilidad: El interprete de bytecode esta en muchas plataformas.

* Confiabilidad y seguridad:

    - Codigo fuente tipado y lenguaje bytecode tipado.

    - Tipado en ejecucion.

    - Recoleccion de basura.

## El sistema Java

* El lenguaje de programacion Java.

* Compilador y sistema de ejecucion.

    - El programador compila el codigo.

    - El codigo compilado se transmite por la red.

    - El receptor lo ejecuta en el interprete (JVM).

    - Comprobaciones de seguridad antes y durante la ejecucion.

* Biblioteca, incluyendo graficos, seguridad, etc.

    - Una biblioteca extensa que hace facil adoptar Java para proyectos.

    - Interoperabilidad.

## Contenido

### Objetos en Java: Clases, encapsulacion, herencia.

#### Terminologia

* Clases, objeto - como en los otros lenguajes.

* Campo - miembro datos.

* Metodo - miembro funcion.

* Miembros estaticos - campos y metodos de la clase.

* This -self.

* Paquete - conjunto de clases en un mismo espacio de nombres (*namespace*).

* Metodo nativo - metodo escrito en otros lenguaje.

#### Objetos y clases

* Sintaxis semejantes a C++.

* Objeto:

    - Tiene campos y metodos.

    - alojado en el heap, no en la pila de ejecucion.

    - Se accede a traves de referencia (es la unica asignacion de puntero).

    - Con recoleccion de basura.

* Lookup dinamico.

    - Comportamiento semejante a otros lenguajes.

    - Tipado estatico => mas eficiente que Smalltalk.

    - Linkeado dinamico, interfaces => mas lento que C++.

##### Clase Punto

```java
class Point {
    private int x;
    protected void setX (int y) { x = y; }
    public int getX() { return x; }
    Point(int xval) { x = xval; } // constructor
};
```

#### Inicializacion de objetos

* Java garantiza la llamada al constructor para cada objeto.

    - Se aloja memoria.

    - Se llama al constructor para inicializar memoria.

* Los campos estaticos de la clase se inicializan en tiempo de carga.

#### Garbage Collection y Finalize

* Los objetos pasan por la recoleccion de basura.

    - No hay una instruccion *free* explicita.

    - Evita punteros colgantes.

* Problema: ¿Que pasa si un objeto ha abierto un archivo o tiene un lock?

* Solucion: Metodo *Finalize*, llamado por el garbage collection.

#### Encapsulacion y package

* Todos los campos y metodos pertenecen a una clase.

* Cada clase es parte de algun package.

    - Puede ser un package por defecto, sin nombre.

    - El archivo declara a que package pertenece el codigo.

#### Visibilidad y acceso

* Cuatro distinciones de visibilidad.

`public, private, protected, package`.

* Un metodo se puede referir a:

    - Los miembros privados de la clase a la que pertenece.

    - Miembros no privados de todas las clases del mismo package.

    - Miembros protected de superclases, en distintos packages.

    - Miembros public de clases en packages visibles, donde la visibilidad esta determinada por el sistema de archivos.

* Nombre calificados (o usando import)

    - `java.lang.String.substring()`.

    - `java.lang` = package.

    - `String` = clase.

    - `subtring()` = metodo.

#### Herencia

* Semejante a Smalltalk y C++.

* Las subclases heredan de las superclases.

    - Herencia simple unicamente - pero Java tiene interfaces.

* Algunas caracteristicas adicionales.

    - Clases y metodos final (no se pueden heredar).

##### Una subclase de ejemplo

```java
class ColorPoint extends Point {
    // Metodos y campos adicionales
    private Color c;
    protected void setC (Color d) { c = d; }
    public Color getC() { return c; }
    // Se define el constructor
    ColorPoint(int xval, Color cval) {
        super(xval);    // Llama al constructor de Point
        c = cval; }     // Inicializa el campo ColorPoint
}
```

#### Clase Object

* Todas las clases extienden otras clases

    - Si no se explicita otra clase, la superclase es Object.

* Metodos de una clase Object

    - `GetClass` - devuelve el objeto Class que representa la clase del objeto.

    - `ToString` - devuelve la representacion en string del objeto.

    - `equals` - equivalencia de objetos por defecto (no de punteros).

    - `hashCode`.

    - `Clone` - hace un duplicado de un objeto.

    - `wait, notify, notifyAll` - para concurrencia.

    - `finalize`.

#### Constructores y Super

* Java garantiza una llamada a constructor para cada objeto.

* La herencia tiene que preservar esta propiedad.

    - El constructor de subclase tiene que llamar al constructor de superclase.

        * Si el primer statement no es una llamada a super, el compilador inserta la llamada a super() automaticamente.

        * Si la superclase no tiene un constructor sin argumentos, causa un error de compilacion.

        * Expepcion: Si un constructor llama a otro, entonces el segundo constructor es el responsable de llamar a super. `ColorPoint() { ColorPoint(0,blue); }`. Se compila sin insertar la llamada a super.

#### Clases y metodos finales

* Restringen herencia

    - Las clases y metodos finales no se pueden redefinir, por ejemplo: `java.lang.String`.

* ¿Para que sirve?

    - Importante para seguridad.

        * El programador controla el comportamiento de todas las subclases, critico porque las subclases producen subtipos.

    - Si lo comparamos con virtual/no-virtual en C++, todo metodo es virtual hasta que se hace final.

### Sistema de tipos: Tipos primitivos, interfaces, arreglos, excepciones.

#### Tipos

* Dosclases generales de tipos:

    - **Tipos Primitivos que no son objetos**: enteros, booleanos.

    * **Tipos de referencia**: clases,interfaces, arrays.

* Chequeo estatico de tipo:

    - Toda expresion tiene tipo, determinado por sus partes.

    - Algunas conversiones automaticas, mucho casteos se comprueban en tiempo de ejecucion.

#### Subtipado

* Tipos primitivo.

    - Conversiones: `int->long`, `double->long`.

* Subtipado de clase semejante a C++.

    - Una subclase produce un subtipado.

* Interfaces

    - Clases complemente abstractas, sin implementacion.

    - Subtipado multiple: una interfaz puede tener multiples subtipos, que la implementacion, la extiende.

##### Subtipado en interfaces: Ejemplo

```java
interface Shape {
    public float center();
    public void rotate(float degrees);
}
interface Drawable {
    public void setColor(Color c);
    public void draw();
}
class Circle implements Shape, Drawable {
    // No hereda ninguna implementacion
    // Pero tiene que definir los metodos de Shape y Drawable
}
```

#### Propiedades de las interfaces

* Flexibilidad

    - Permite un grafo de subtipado, en lugar de un arbol.

    - Evita problemas con herencia multiple de implementaciones (como la herencia en diamante de C++).

* Coste:

    - No se conoce el offset en la tabla de consulta de metodos (*method lookup table*) en tiempo de compilacion.

    - Hay diferentes bytecode para consulta de metodos:

        * Uno cuando se conoce la clase.

        * Otro cuando solo se conoce la interfaz.

#### Expepciones

* Funcionalidad semejante a otros lenguajes

    - Construcciones para *throw* y *catch*.

    - Alcance dinamico.

* Algunas diferencias:

    - Una excepcion es un objeto de una clase excepcion.

    - Subtipado entre clases excepcion.

        * Se usa subtipado para matchear el tipo de una excepcion o pasarlo (semejante a ML).

    - El tipo de cada metodo incluye las expcepciones que puede lanzar, todas subclases de `Exception`.

* Si un metodo lanza una excepcion comprobada, la excepcion debe estar en el tipo del metodo.

#### Bloques try / finally

* Las excepciones se capturan en bloques `try`.

```java
try {
    statements
} catch (ex-type1 identifier1) {
    statements
} catch (ex-type2 identifier2) {
    statements
} finally {
    statements
}
```

#### ¿Porque nuevos tipos de expcepciones?

* Las expcepciones pueden contener datos:

    - La clase `Throwable` incluye un capo string para describir la causa de la excepcion.

    - Se pasan otros datos declarando campos o metodos adicionales.

* La jerarquia de subtipos se usa para capturar excepciones. `catch <exception-type> <identifier> { ... }`. Captura cualquier excepcion de cualquier subtipo y la liga al identificador.

### Genericos (añadidos en Java 1.5): Basicos, wildcards, ...

#### Programacion generica

* La clase Object es supertipo de todos los tipos objetos.

    - Esto permite polimorfismo en objetos, porque se pueden aplicar las operaciones de la clase `T` a toda subclase `S<:T`.

##### Ejemplo de construccion generica: Pila

* Se pueden hacer pilas para cualquier tipo de objeto, y las operaciones asociadas a pila funcionan para cualquier tipo.

* En C++ tendriamos la clase generica `stack`.

```
template <type t> class Stack {
    private: t data; Stack<t> * next;
    public: void    push (t * x) { ... }
                    t* pop (  ) { ... }
};
```

#### ¿Porque no se incorporan al principio?

* Muchas distintas propuestas.

* Los objetos basicos del lenguaje parecian cubiertos.

* Varios detalles que requieren esfuerzo:

    - Precisar exactamente las restricciones del tipado.

#### Los genericos de Java tienen comprobacion de tipo

* Una clase generica usa operaciones en un tipo de parametros: `PriorityQueue<T>...  if x.less(y) then ...`.

* Dos posibles soluciones:

    - C++: Linkear y fijarse si todas las operaciones se pueden resolver.

    - Java: Chequea tipos y compila los genericos sin linkear.

### Maquina virtual: Loader, verifier, linker, interprete | Bytecode para lookup de metodos.

#### Implementacion

* Compilador y maquina virtual:

    - El compilador produce bytecode.

    - La maquina virtual carga clases a demanda, verifica propiedades del bytecode e interpreta el bytecode.

* ¿Porque este diseño?

    - Ya se habian usado interpretes / compiladores de bytecode antes: Pascal, Smalltalk.

    - Minimiza la parte de la implementacion dependiente de maquina:

        * La optimizacion se hace en el bytecode.

        * Se mantiene muy simple el interprete de bytecode.

    - Para Java, tambien aporta portabilidad.

        * Se puede transmitir el bytecode por la red.

#### Areas de memoria de la JVM

* El programa en Java tiene uno o mas *threads*.

* Cada *thread* tiene su propio stack.

* Todos los *threads* comparten el heap.

#### Carga de clases

* El sistema de ejecucion carga las clases a medida que se necesitan

    - Cuando se referencia una clase, el sistema de carga busca el archivo de instrucciones de bytecode compiladas.

* El mecanismo de carga por defecto se puede sustituir definiendo otro objeto ClassLoader.

    - Se extiende la clase ClassLoader.

    - ClassLoader no implementa el metodo abstracto loadClass, si no que tiene metodos que pueden usarse para implementar loadClass.

    - Se pueden obtener bytecode de otra fuente.

#### Linker y verificador de la JVM

* Linker

    - Añade la clase o interfaz compiladas al sistema de ejecucion.

    - Crea los campos estaticos y los inicializa.

    - Resuelve nombres, reemplazandolos con referencias directas.

* Verificador.

    - Comprueba el bytecode de una clase o interfaz antes de que se cargue.

    - Lanza la excepcion VerifyError.

### Temas de seguridad.

#### Seguridad en Java

* Seguridad.

    - Evitar uso no autorizado de recursos computacionales.

* Seguridad en Java.

    - El codigo Java puede leer input de usuarios despistados o atacantes maliciosos.

    - El codigo Java se puede transmitir por la red.

Java esta diseñado para reducir riesgos de seguridad.

#### Mecanismos de seguridad

* Sandboxing (jugar en el arenero)

    - El programa se ejecuta en un entorno restringido.

    - Se aplica a:

        * Caracteristicas del loader, verificador, e interprete que restringen al programa.

        * Java Security Manager, un objeto especial que ejerce control de acceso.

* Firma de codigo.

    - Se usa principios criptograficos para establecer el origen de un archivo de clase.

    - La usa el security manager.

#### Java Sandbox

* Class loader

    - namespaces distintos para distintos class loaders.

    - Asocia un protection domain con cada clase.

* Test en tiempo de ejecucion del verifier y JVM.

    - No se permiten casteos sin comprobacion de tipo ni otros errores de tipo, no se permite array overflow.

    - Preserva los niveles de visibilidad private y protected.

* Security Manager.

    - Lo llaman las funciones para decidir si deben hacer lugar a un pedido.

    - Usa el protection domain asociado al codigo y politica de usuario.

#### Security Manager

* Las funciones de la biblioteca de Java llaman al security manager.

* Respuesta en tiempo de ejecicion.

    - Decide si el codigo que llama tiene permiso de la clase que llama.

        * Signer: Organiza que firmo el codigo antes de cargarlo.

        * Ubicacion: URL de donde vienen las clases.

    - Da permiso de acceso segun la politica del sistema.

#### Inspeccion del stack

* El permiso depende de:

    - Permiso del metodo que llama.

    - Permiso de todos los metodos por encima de el en el stack, hasta llegar a un metodo confiable.

#### Resumen

* Objetos:

    - Tienen campos y metodos.

    - Alojados en el heap, se acceden con punteros, con recoleccion de basura.

* Clases:

    - Public, Private, protected, Package (no exactamente como en C++).

    - Pueden tener miembros estaticos (propios de la clase).

    - Constructor y metodos finalize.

* Herencia:

    - Herencia simple.

    - Metodos y clases finales (no pueden tener hijas).

* Subtipado:

    - determinado por la jerarquia de herencia.

    - una clase puede implementar muchas interfaces.

* Virtual Machine:

    - Carga bytecode para clases en tiempo de ejecucion.

    - El verificador comprueba el bytecode.

    - El interprete tambien hace comprobaciones en tiempo de ejecucion.

        * Casteos.

        * Limites de arreglos.

* Portabilidad y seguridad.
