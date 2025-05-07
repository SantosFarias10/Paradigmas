# Programacion Orientada a Objetos (POO)

En el nucleo de la POO se encuentra el principio de **Encapsular** tanto datos como funcionalidad dentro de objetos. Los **Objetos** abarcan desde tipo de datos basicos como enteros hasta estructuras complejas. Los "lenguajes orientados a objetos" son aquellos que implementan cuatro caracteristicas: **Busqueda Dinamica**, **Abstraccion**, **Subtipad** y **Herencia**.

* **Busqueda Dinamica** permite la seleccion de metodos durante la ejecucion, fomentando interacciones flexibles entre objetos. Por ejemplo: un metodo llamado `add` puede realizar distintas funciones segun el tipo del objeto, sumar numeros o insertar valores en un conjunto.

* **Abstraccion** en la POO implica ocultar los detalles de implementacion mientras se expone una interfaz amigable para el user. Los objetos que encapsulan procesos complejos permiten a los clientes interactuar con interfaces simples sin requerir conociemiento detallado de los mecanismos internos (osea la implementacion). Por ejemplo: un objeto de manipulacion de figuras geometricas podria proporcionar facil acceso a sus metodos sin revelar detalles intrincados.

* **Subtipado** Mejora la reutilizacion de codigo al permitir que objetos de distintos tipos funcionen intercambiablemente, enriqueciendo la adaptabilidad. Cuando un subtipo deriva de un supertipo, las operaciones permitidas para el supertipo se extienden al subtipo, promoviendo un codigo facil mas flexible y apoyando el polimorfismo.

* **Herencia** permite que las clases deriven atributos y comportamientos de clases padre, refuerza la eficiencia del codigo al centrarse en nuevas funcionalidades en lugar de duplicar partes existentes. Si analizamos el mecanismo de herencia, desmostramos como las definiciones de clases nuevas pueden basarse en otras previas, lo cual permite conservar funcionalidades esenciales mientras se añaden nuevas propiedadas o metodos.

## Abstraccion de objetos vs. TADS

Los sistemas orientados a objetos permiten mayor flexibilidad mediante interfaces polimorficas, mientras mantienen la integridad estructural a traves de mecanismos de herencia. Destacamos los meritos del subtipado, mostrando como se pueden crear estructuras de datos heterogeneas de manera efectiva. Por ejemplo, una cola puede albergar distintos subtipos de cuenta, cumpliendo las funcionalidades necesarias sin requerir modificaciones fundamentales del sistema.

## Subtipado vs. herencia

El subtipado se refiere a relaciones de interfaz, mientras que la herencia esta relacionada con comportamientos de implementacion, siendo conceptos distintos aunque interconectados en los paradigmas orientados a objetos.


