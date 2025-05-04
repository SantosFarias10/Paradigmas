# Programacion Oriendatada a Objetos: C++

## Objetivo de diseño

* Proveer caracteristicas de orientacion a objetos en un lenguaje tipo C, sin renunciar a la eficiencia.

    - Retrocompatible con C.

    - Mejorando el chequeo de tipos estatico.

    - Con abstraccion de datos, objetos, clases.

    - Priorizando codigo compilado.

* Principio importante: Si no se usa una caracterisitca orientada a objetos, el codigo compilado deberia ser igual de eficiente que C sin orientacion a objetos.

## ¿Que tan bien salio?

* Muy popular.

* Dadas las restricciones y los objetivos, muy buen diseño.

* Pero un diseño muy complicado:

    - Muchas caracteristicas con interacciones complejas, dificiles de predecir a partir de los principios basicos.

    - La mayoria de usuarios serios usan solo un subconjunto del lenguaje, porque el lenguaje completo es complejo e impredecible.

    - Muchas propiedades dependientes de implementacion.

## Restricciones importantes

* C tiene un modelo de maquina especifico (no abstracto), porque tiene acceso al bajo nivel (por herencia de BCPL).

* No hay recoleccion de basura, por eficiencia, asi que hay que manejar la memoria de objetos explicitamente.

* Las variables locales se guardan en los activation record:

    - Los objetos se tratan como generalizaciones de `structs`.

    - Se los puede alojar en el stack y tratarlos como `L-valores`.

    - El programador puede acceder a la diferencia entre stack y heap.

## Añadidos no orientados a objetos

* Templates de funcion (programacion generica), en la STL.

* Pasaje por referencia.

* Sobrecarga definida por el usuario.

* Tipo booleano.

## Sistema de objetos de C++

* Clases.

* Objetos: Con consulta dinamica (*dynamic lookup*) de funciones virtuales.

* Herencia:

    - Simple y multiple.

    - Clases base publicas y privadas.

* Subtipado: Ligado al mecanismo de herencia.

* Encapsulacion.

### Buenas decisiones

* Niveles de visibilidad

    - **Public**: Visible en todos lados.

    - **Protected**: En las declaraciones de clase y sus subclases.

    - **Private**: Visible solamente en la clase donde se declara.

* Se permite herencia sin subtipado: Clases base privadas y protegidas.

### Areas problematicas

* Casts.

    - Irregular: A veces se fuerzan y a veces no.

* Sin garbage collection.

* Los objetos se alojan en el stack.

    - Mejor eficiencia, iteraccion con las excepciones.

    - Pero la asignacion funciona mal,posiblemente con punteros colgantes.

* Sobrecarga.

* Herencia multiple: Como se busca eficiencia, el comportamiento es complicado.

### Clase ejemplo: Punto

```c++
class Pt {
    public:
        Pt(int xv);                 <---|- Constructor sobrecargado
        Pt(Pt* pv);                 <---|

        int getX();                     Acceso publico de lectura a datos privados

        virtual void move(int dx);      Funcion virtual

    protected:
        void setX(int xv);              Acceso de escritura protegido

    private:
        int x;                          Datos privados
}
```

## Funciones virtuales

* Funciones virtuales

    - Se acceden a traves de un puntero en el objeto.

    - Se pueden redefinir en subclases derivadas.

    - La funcion exacta que se llama se determina dinamicamente.

* Las funciones no virtuales son funciones comunes: no se pueden redefinir pero se pueden sobrecargar.

* Las funciones son virtuales si se declaran explicitamente o se heredan como virtuaes, si no, son no-virtuales.

* Se paga overhead solo si se usan funciones virtuales.

### Ejemplo de clase derivada: Punto coloreado

```c++
class ColorPt: public Pt {                  La clase publica de base es el supertip
    public:                             <---|
        ColorPt(int xv, int cv);            |- Constructor sobrecargado
        ColorPt(Pt* pv, int cv);            |
        ColorPt(ColorPt* cp);           <---|

        int getColor();                     Funcion no-virtual

        virtual void move(int dx);      <---|- Funciones virtuales
        virtual void darken(int tint);  <---|

    protected:
        void setColor(int cv);              Acceso de escritura Protected

    private:
        int color; };                       Datos Private
```

## ¿Porque el lookup en C++ es mas simple?

* Smalltalk no tiene sistemas de tipo estatico

    - El codigo `p message:params` puede referirse a cualquier objeto.

    - Necesitamos encontrar un metodo que use el puntero del objeto.

    - Diferentes clases ponen los metodos en diferentes lugares en el diccionario de metodos.

* C++ le da al compilador una superclase.

    - El offset de los datos y los punteros a funciones son los mismos en la subclase y la superclase, se conocen en tiempo de compilacion.

    - El codigo `p->move(x)` compila al equivalente de `(*(p->vptr[0]))(p,x)` si `move` es la primera funcion en la vtable.

## El puntero "this"

* El codigo se compila de forma que la funcion miembro toma al objeto mismo como primer argumento.

    - Codigo: `int A:: f(int x) { ... g(i) ... }`.

    - Compilado: `int A:: f(A *this, int x) { ... this->g(i) ...; }`.

* El puntero "this" se puede usar en la funcion miembro, para devolver el puntero del objeto, pasar el puntero del objeto a otra funcion, etc.

* Igual al "self" de Smalltalk.

## Funciones NO virtuales

El codigo para funciones no virtuales se encuentra igual que para las funciones comunes.

* El compilador genera el codigo de la funcion y le asigna una direccion.

* La direccion se ubica en la tabla de simbolos.

* En el lugar de llamada, se obtiene la direccion de la tabla y se ubica en el codigo compilado.

* Pero en el caso de clases aplican algunas reglas especiales sobre alcance.

* La sobrecarga se resuelve en tiempo de compilacion, a diferencia de la consulta de una funcion virtual en tiempo de ejecucion.

## Reglas de alcance en C++

Calificadores de alcance: `::`, `->`, y `.`.

`class::member, ptr->member, objetc.member`.

* Global (objeto, funcion, enumerador, tipo): Nombre fuera de una funcion o clase no prefijado por `::` y no calificado.

* Alcance de clase: Nombre despues de `X::`, `ptr->` o `obj.`, se refiere a un miembro de la clase X o a la clase base de X, asumiendo `ptr` es un puntero a la clase X y `obj` es un objeto de la clase X.

## Subtipado

* Subtipado en principio: A es un subtipado de B (`A<:B`) si todo objeto A se puede usar en un contexto en el que se necesita B sin errores de tipo. Por ejemplo

```
Point:          int getX();
                void move(int);
ColorPoint:     int getX();
                int getColor();
                void move(int);
                void darken(int tint);
```

* En C++: **A es un subtipado de B** si la clase A tiene como clase base publica a B.

    - Esto es mas debil de lo que seria necesario.

## No hay tiapdo sin herencia

```
class Point() {
    public:
        int getX();
        void move(int);
    protected:  ...
    private:      ...
};

class ColorPoint {
    public:
        int getX();
        void move(int);
        int getColor();
        void darken(int);
    protected:  ...
    private:      ...
};
```

C++ No trata este `ColorPoint` como subtipado de Point, pero Smalltalk si lo haria.

### ¿Porque esta decision de deiseño?

* El codigo depende solo de la interfaz publica.

    - En principio, si la interfaz de ColorPoint contiene a la interfaz de Point los clientes podrian usar ColorPoint en lugar de Point (como en Smalltalk).

    - Pero el offset en la tabla de funciones virtuales puede ser distinta, y de esta forma perder eficiencia (como en Smalltalk).

* Si no funciona ligada a la herencia, el subtipado lleva a perdida de eficiencia.

* Tambien por Encapsulacion: el subtipado basado en herencia se preserva s hacemos modificaciones en la clase base.

## Clases abstractas

* Una clase abstracta es una clase sin implementacion completa.

* Se declara con `= 0`.

* Util porque puede tener clases derivadas.

    - Como el subtipado se sigue de la herencia en C++, se pueden usar las clases abstractas para construir jerarquias de subtipos.

* Establece la disposicion de la vtable (tabla de funciones virtuales).

## Problema: Choques de nombre (*name clashes*)

```c++
class A {
    public:
        void virtual f() { ... }
};

class B {
    public:
        void virtual f() { ... }
};

class C : public A, public B { ... };
...

    C* p;
    p->f();     //error
```

### Como resolver choques de nombre

* Resolucion implicita: Con reglas arbitrarias.

* Resolucion explicita: El programador debe resolver los conflictos explicitamente.`<- la que usa C++`.

* No permitida.

### Resolucion explicita de choques de nombres

* Reescribir la clase C para llamar a `A::f` explicitamente.

```c++
class C : public A, public B {
    public:
        void virtual f(){
            A::f();     // Llama A::f(), no B::f()
        }
}
```

* Elimina ambiguedad.

* Peserva la dependencia de A.

    - Los cambios a `A::f` cambiaran `C::f`.

## Resumen

* Objetos:

    - Creados por clases.

    - Contienen datos del miembro y un puntero a la clase.

* Clases: Tabla de funciones virtuales.

* Herencia: Clases base publicas y privadas, herencia multiple.

* Subtipado: Solo con clases base publicas.

* Encapsulacion:

    - Un miembro se puede declarar publico, privado o protegido.

    - La inicializacion de los objetos se puede forzar parcialmente.
