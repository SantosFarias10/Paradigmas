# Programacion Orientada a Objetos (POO)

Paradigma que ha influido significativamente en el diseño de software. Se abordan aspectos clave como los principios del diseño orientado a objetos y las caracteristicas unicas de diversos lenguajes que facilitan un desarrollo de software eficiente.

En el nucleo de la POO se encuentra el principio de **Encapsular** tanto datos como funcionalidad dentro de objetos. Estos **Objetos** abarcan desde tipos de datos basicos como enteros hasta estructuras complejas, interactuando mediante llamadas a mensajes definidas. A medida que la POO se volvio predominante, se aclaro el termino "Lenguaje Orienta a Objetos", definido como aquel que implementa cuatro caracteristicas fundamentales: **Busqueda Dinamica**, **Abstraccion**, **Subtipado** y **Herencia**.

Se presenta un enfoque sistematico para el diseño orientado a objetos, siguiendo el metodo de *Grady Booch*,  que incluye pasos como identificar objetos relevantes dentro de un contexto especifico, aclarar sus comportamientos previstos, mapear relaciones entre objetos e implementar el diseño dentro de un marco de programacion. La naturaleza iterativa de este proceso de diseño apoya la evolucion de sistemas complejos con el tiempo, estableciendo una sinergia entre la funcion y el refinamiento de datos que supera los enfoques de programacion estructurada anteriores.

La **Busqueda Dinamica** permite la seleccion de metodos durante la ejecucion, fomentando interacciones flexibles entre objetos. Como los metodos se invocan segund la clase real del objeto en lugar de su tipo declarado, diferentes clases pueden exhibir comportamientos variados ante mensajes indenticos-una manifestacion del polimorfismo. Por ejemplo, un metodo llamado `add` puede realizar distintas funciones segun el tipo del objeto: sumar numeros o insertar valores en una coleccion.

La **Abstraccion** en la POO implica ocultar los detalles de implementacion mientras se expone una interfaz amigable para el usuario. Los objetos que encapsulan procesos complejos permiten a los clientes interactuar con interfaces simplificadas sin requerir conocimiento detallado de los mecanismos internos. Por ejemplo, un objeto de manipulacion de figuras geometricas podria proporcionar facil acceso a sus metodos sin revelar detalles intrincados.

El **Subtipado** mejora la reutilizacion de codigo al permitir que objetos de distintos tipos funciones intercambiablemente, enriqueciendo la adaptibilidad. Cuando un subtipo deriva de un supertipo, las operaciones permitidas para el supertipo se extienden al subtipo, promoviendo un codigo mas flexible y apoyando el polimorfismo. La **Herencia**, que permite que las clases deriven atributos y comportamientos de clases padre, refuerza la eficiencia del codigo al centrarse en nuevas funcionalidades en lugar de duplicar partes existentes.

Se examinan los mecanismos de busqueda de objetos, que, aunque varian entre lenguajes, dependen fundamentalmente de la asignacion de informacion de metodos en tiempo de ejecucion basada en el tipo del objeto, permitiendo una gestion fluida de los mensajes. La *Multidispatch* (despacho multiples), que favorece la seleccion de metodos basada en los tipos de todos los argumentos, se contrrasta con los sitemas de *single dispatch* (despacho simple) donde solo importa el tipo del objeto receptor, mejorando el polimorfismo y la versatilidad-concepto prevalente en sistemas como *CLOS*.

Profundizando en la **Abstraccion**, se aclaran las diferencias entre la abstraccion de objetos y los tipos abstractos de datos (TADs) tradicionales, siendo que los sitemas orientados a objetos permiten mayor flexibilidad mediante interfaces polimorficas, mientras mantienen la integridad estructural a traves de mecanismos de herencias. Se destacan los meritos del subtipad, mostrando como se pueden crear estructuras de datos heterogeneas de manera efectiva. Una cola, por ejemplo, puede albergar distintos subtipos de cuenta, cumpliendo las funcionalidades necesarias sin requerir modificaciones fundamentales del sistema.

Se analiza las mecanicas de **Herencia**, desmostrando como las definiciones de clases nuevas pueden basarse en otras previas, lo cual permite conservar funcionalidades esenciales mientras se añaden nuevas propiedades o metodos. Esto sienta las bases para una reutilizacion efectiva del codigo y garantiza que las modificaciones en clases padre se propaguen automaticamente en la jerarquia, simplificando el mantenimiento y la adaptibilidad.

Ademas, distinguimos entre objetos y *closures* (clausuras xd), señalando que, si bien ambos ofrecen funcionalidades similares, difieren en contexto operativos e implementaciones. La POO depende fundamentalmente de mecanismos como subtipado y herencia, los cuales tipicamente estan ausentes en las *closures*.

La importancia de diferenciar subtipado de herencia: El subtipado se refiere a relaciones de interfaz, mientras que la herencia esta relacionada con comportamientos de implementacion, siendo conceptos distintos aunque interconectados en los paradigmas orientados a objetos.

Un contraste fundamental entre la programacion orientada a objetos y las estructuras de programacion funcional tradicional. A diferencia de la programacion funcional, que separa los datos de la funcionalidad, la POO fusiona ambos, facilitando interacciones especificas de metodos con los datos, lo cual fortalece la robustez y la adaptibilidad a lo largo del tiempo.

Se introducen los patrones de diseño como paradigmas significativos dentro del diseño de software, reconocidos como soluciones reutilizables para problemas recurrentes encontrados durante el desarrollo.

El contexto historico de la POO se remonta a la aparicion de **Simula**, que paso de ser una herramienta centrada en simulaciones a convertirse en un lenguaje de programacion completo, incluyendo en lenguajes posteriores como **Smalltalk**. La evolucion de **Smalltalk** se destaca por su enfasis en el paso de mensajes, el diseño centrado en el usuario y la representacion coherente de objetos, avanzando significativamente el paradigma de la POO.

Ademas, los principios de **Herencia en Smalltalk**, donde sublases como `ColoredPoint` extienden funcionalidades de clases padre como `Point`. Esta versabilidad se evidencia cuando `ColoredPoint` hereda todos los atributos de `Point` mientras implementa nuevas caracteristicas. Se expora la **Sobrescritura** de metodos, particularmente como el metodo `draw` es adaptado en `ColoredPoint` para integrar su atributo de color, alineandose con el sistema de busqueda dinamica de metodos de Smalltalk, el cual identifica la configuracion correcta del metodo durante la ejecucion, dando lugar a comportamientos polimorficos.

Las diferencias en la visibilidad de objetos destacan el diseño de Smalltalk, donde los metodos publicos coexisten con variables de instancia protegidas, implicando un acceso limitado a ciertos atributos para maximizar la eficiencia de la abstraccion. La flexibilidad general de Smalltalk incluye restricciones para el manejo de booleanos y detalles operativos sobre palabras clave definidas como **self** y **super**, garantizando resoluciones dinamicas de metodos durante las interacciones entre objetos.

Crucialmente, introducimos **C++**, constratando su modelo de gestion de objetos con Smalltalk al combinar tipado estatico con caracteristicas dinamicas para un procesamiento mas eficiente. C++ enfatiza el uso de clases, introduce constructores y destructores para la gestion de memoria, y resalta como las resoluciones de tipos en tiempo de compilacion optimizan las llamadas a metodos frente a la busqueda dinamica tipica de Smalltalk.

Herencia y las funciones virtuales en C++, detallando como los mecanismos virtuales facilitan el despacho dinamico. Cada clase con metodos virtuales posee una tabla virtual (*vtable*) que vincula llamadas dinamicas con los tipos reales de objetos. A medida que las funciones miembro invocan procesos heredados, el compilador optimiza la resolucion de llamadas, mejorando el rendimiento y gestionando ambiguedades potenciales en funciones sobrecargadas.

Los principios de subtipado en C++ establecen que si una clase A es subclase de B, entonces instancias de A pueden usarse donde se espera una de B, manteniendo un vinculo fuerte con los principios de herencia. La Sobrescritura de metodos en sublcases debe cumplir con que los tipos de retorno sean subtipos del tipo de retorno en la clase padre, promoviendo la integridad polimorfica incluso dentro de contextos sobrecargados.

Al pasar a Java, se evidencia su enfoque en la portabilidad, la seguridad y la confiabilidad, lo que lo hace especialmente adecuado para aplicaciones web y de red. Las caracteristicas resaltan los objetivos de diseño de Java: la portabilidad a traves de la maquina virtual de Java (JVM) permite compatibilidad con diversos hardware, la confiabilidad minimiza errores, las funciones de seguridad protegen contra operaciones inseguras y el enlace dinamico facilita la programacion modular sin costos iniciales elevados.

Java enfatiza la facilidad de uso mediante distinciones de acceso (publico, protegido, privado), respetando los estandares de encapsulacion. La estructuras de los objetos funcioina de manera similar a C++, garantizando una inicializacion adecuada mediante constructores y aclarando las distinciones entre la sobrecarga y la Sobrescritura de metodos segun la arquitectura de Java.

La diferencia de las clases abstractas en Java, donde los metodos abstractos deben ser implementados por subclase. Junto con las interfaces, que promueven la versabilidad al permitir multiples implementacion, Java resulta eficaz para organizar la estructura e introducir polimorfismo controlado debido a su restruccion de una sola superclas.

La conversion de tipos en tiempo de ejecucion garantiza la seguridad de tipos en Java mediante castings cuidadosos para evitar errores, mientras que los arreglos presentan caracteristicas de covarianza que pueden ocacionar desafios como el problema de la covarianza de arreglos ante incompatibilidad de tipos.

La jerarquia de excepciones y los mecanismos de manejos en Java permiten una gestion robusta de errores definidos por el usuario. La clase base `Throwable` encapsula diversas excepciones, permitiendo su encadenamiento a traves de manejadores y controlando eficazmente el flujo del programa.

La arquitectura del sistema Java se centra en la JVM, que gestiona los archivos de clase mediante un cargador de clases, valida sistemas de tipos con un verificador de *bitecode*, y se ejecuta codigo compilado a traves de un interprete dedicado. Las tacticas de gestion de memoria, incluido el garabe collector, juegan un papel fundamental en el mantenimiento de la integridad del sistema durante la ejecucion de programas.

Las caracteristicas de seguridad de Java brindan proteccion contra el acceso no autorizado al codigo mediante entornos restringidos (*sandboxing*), firmas de codigo y salvaguardas contra desbordamiento de bufer. Estas previenen vulnerabilidades comunes presentes en lenguajes como C, mejorando la resiliencia general de las aplicaciones.

En resumen, el papel fundamental de los principios de la POO en el desarrollo moderno de software, revelando la evolucion inherente y los conceptos fundacionales que presisten en diversos lenguajes de programacion. C++ muestra complejidad frente al rendimiento, mientras que Java enfatiza la confiabilidad y la seguridad, destacando como ambos lenguajes continuan desarrollando los principios de encapsulacion, herencia y polimorfismo para apoyar practicas de programacion resilientes.

---

## Parcial 2 del 8 de Mayo de 2025

### 1

* **Smalltalk** permite reemplazo en caliente. Una de sus principales caracteristicas es que pueden modificar clases, metodos y objetos durante la ejecucion del programa.

* C++ tiene menos *Overhead* que Smalltalk, porque las llamadas a metodos virtuales se resuelven usando tablas de metodos (*vtable*) que son mas eficientes que el sistema de envio de mensajes de Smalltalk, que implica buscar un diccionario de metodos.

* *Override* es cuando una subclase proporciona una nueva implementacion de un metodo que ya esta definido en su superclase. Tanto C++ como Smalltalk permiten *overriding*.

### 2

La palabra `super()` en Python permite acceder a metodos de la clase padre desde una subclase. En este caso, se usa para llamar al constructor de la clase `Persona` dentro de `Estudiante`. Asi, `self.nombre` se inicializa correctamente usando la logica de superclase. Evita repetir codigo y permite reutilizar comportamientos de la clase base. Tambien se puede usar para llamar a otros metodos heredados.

### 3

El primer programa da error porque intenta acceder directamente a un atributo `private`, lo cual no esta permitido fuera de la clase. Java usa el modificador `private` para proteger los datos y forzar el uso de metodos de acceso.

El segundo programa usa metodos `getNombre()` y `setNombre()`, que si son publicos. Esto permite acceder y modificar el atributo sin violar el encapsulamiento.

### 4

* Una **Funcion Virtual** es una funcion que puede ser sobrescrita (*override*) por una clase hija y se resuelve en tiempo de ejecucion (**Polimorfismo Dinamico**). Se usan principalmente en lenguajes como C++, donde necesitas indicar explicitamente que una funcion puede ser redefinida con la palabra clave `virtual`.

* En **Ruby**, todos los metodos son por defecto virtuales, osea, pueden ser sobrescritos por subclases.

* Una funcion o metodo "**Final**" es un metodo que no puede ser sobrescito por una subclase.

* En Ruby sigue la cadena de herencia (lineal), busca el metodo en la clase actual y luego en sus ancestros, respetando la jerarquia. En este caso,  por eso `PinguinoPinguino` usa el metodo `desplazamiento` de `Pinguino` y no de `Ave`.

* El principio de sustitucion de Liskov dice que un objeto de una subclase debe poder ser usado como si fuera de su superclase sin afectar la correccion del programa. Aqui no se viola dicho principio: el metodo `desplazamiento` es redefinido, pero no cambia la interfaz ni rompe el uso esperado.

---
