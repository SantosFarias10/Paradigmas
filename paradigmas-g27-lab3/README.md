# Frameworks
## Computacion Distribuida con Apache Spark
### Integrantes Grupo 27
* Santino Ponchiardi
* Brandon Michel
* Luca Irrazabal
* Santos Farias
---
## Configuracion del Entorno y Ejecucion
### Requisitos
* git
* apache spark v3.5.1 ~ v3.5.5
* openjdk-11
* apache ant  
*Los requisitos son necesarios en las maquinas del futuro master y los futuros workers.*
### Compilacion
**Aclaracion:** aproveche que nos tiraron la pista de apache ant para el parcial anterior e implementamos toda la 
compilacion con esto. De aca la ausencia de un Makefile en el proyecto.
* ```ant run``` para ejecutar directamente el Feed Reader que imprime por consola los Feeds parseados.
* ```ant run-spark``` para ejecutar el Feed Reader pero con Spark paralelizando la descarga y parseo de los Feeds.
* ```ant jar``` compila ejecutable FeedReader.jar.
* ```ant jar-spark``` compila ejecutable FeedReader-Spark.jar. 
* ```ant namedEntity``` ejecuta Feed Reader con la flag -ne.
* ```ant quick``` ejecuta Feed Reader con la flag -ne y con Spark.
* ```ant clean``` limpia el directorio del proyecto.
* ```ant help``` para ver mas opciones.

Ahora, si se quiere hacer correr el cluster de Spark para el proyecto, primero se debe levantar un master y sus 
respectivos workers.
1. Pararse en el directorio de apache.  
    Ej:  
    ```cd /opt/apache-spark/sbin```
2. Crear el master. Y dirigirse a la url ```http://<IP-PRIVADA>:8080``` para monitorear lo que hace el master.  
    ```sudo ./start-master.sh start-master.sh --host <IP-PRIVADA> --port 7077```
3. Crear workers, tambien lo pueden hacer desde otras maquinas para dar sus recursos a disposicion del master.  
    ```sudo ./start-worker.sh spark://<IP-PRIVADA>:7077```  
    Siempore la IP-PRIVADA debe ser la del master.
4. Compilar el FeedReader-Spark.jar con ```ant jar-spark``` para mandarle la tarea al master. Se puede hacer desde 
    cualquier maquina, no necesaria mente desde el master. Y posicionarse en el directorio ```cd /opt/apache-spark/bin```  
    ```sudo ./spark-submit --class "SparkFeedReaderMain" --master "spark://<IP-PRIVADA>:7077" --deploy-mode cluster FeedReader-Spark.jar```
---
## Objetivos  
Trabajando con el codigo del laboratorio 2, pero extendiendo el lector automatico de feeds RSS para que soporte el 
procesamiento de grandes volumenes de datos, tanto en la etapa de descarga como en el calculo de entidades nombradas.
Utilizando Apache Spark, el framework mas utilizado actualmente para la computacion distribuida sobre grandes 
conjuntos de datos.

---
## Conceptos Importantes
1. Flujo de la Aplicacion:
    - La clase SubscriptionParser la usamos para leer el subscription.json que es donde se encuentran todas las url's 
        que usamos.
    - Se guardan todo los datos parseados del .json en un objeto de la clase Subscription que es una lista de objetos 
        de la clase SingleSubscription.
    - Luego analizamos SingleSubscripcion a SingleSubscripcion del objeto de Subscription extrayendo la url y el 
        contenido en formato XML.
    - Para despues en un objeto de la clase RssParser que parsea todo el contenido de XML a un objeto de la clase Feed.
    - La cual luego es puesta en pantalla/stdout con metodos de esa misma clase Feed.
    - Para luego en el caso de que se hayan pedido calcular las entidades nombradas, se analiza el objeto Feed con un 
        metodo que junto con un objeto de la clase Heuristic cuenta las entidades nombradas que tenga en cuenta la 
        heuristica usada. Y poniendola en pantalla/stdout.
2. Se decide usar Apache Spark en el proyecto para paralelizar la descarga y parseo de los feeds de las paginas dadas, 
    ya que no se dependen entre si. Tambien para paralelizar, por la misma razon, el calculo de las entidades nombradas
    de cada feed.
3. Las ventajas de Spark:
    - Mejoro la velocidad de la ejecucion del Feed Reader, pero solo si la maquina en la que se uso tenia al menos mas 
        de 2 nucleos. En nuestro caso dos de cuatro maquinas tenian 2 nucleos por lo que al principio fue complicado 
        inclusive darnos cuenta si estabamos usando spark bien.
    - A pesar de que no sabemos como se hace sin spark, este nos abstrae de todo los problemas de la paralelizacion de 
        un programa.
    - Realmente verlo funcionar, viendo como utilizaba los recursos de los workers que le dabamos desde otras maquinas 
        fue algo muy loco.
   Las desventajas de Spark:
    - No tuvimos le pudimos dedicarle el tiempo que nos hubiera gustado al proyecto, de ahi sus pecados.
    - Nos costo terminar de entender como spark dentro del codigo del programa. Especificamente se nos hizo muy facil 
        perdernos al no ver explicitamente en que momento se "paralelizaba" exactamente el codigo.
    - Basicamente es complejidad a la hora de la implementacion a cambio de rendimiento a la hora de ejecucion.
4. El control de flujo lo delegamos al criterio de Spark en el Feed Reader al obtener/descargar de la url los feeds, al
    parsear el contenido XML y al calcular las entidades nombradas. Dejando de controlar el hilo de ejecucion del 
    programa.
5. Considero que Spark tiene una integracion loose coupling por la tolerancia ante el fallo de alguno de los hilos de 
    ejecucion en su paralelizacion. Pero al mismo tiempo me hizo sentir el codigo original debia ser tight coupling, 
    osea muy interconectados entre ellos para que realemnte haga lo que necesitaba. De todas formas esto ultimo creo 
    y apuesto 100 pesos a que fue por el poco conocimiento de una herramienta tan amplia y compleja ante mis ojos que 
    es spark.
6. El uso de Spark me hizo cambiar toda la estructura de mi Feed Reader, pero vuelvo siempre a lo mismo, creo que fue 
    mas por el desconocimiento del framework mas que su complejidad para adaptarse a mi codigo. Principalmente tuve que
    modificar poco las clases y metodos, pero la logica de ejecucion del laboratorio 2 casi que se perdio por completo 
    a simple vista. A ver tampoco para exagerar no quedo absolutamente diferente, de todas formas si me hizo dar muchas
    vueltas para probar y al final eso me llevo a "mejorar" algunas partes del codigo original.
