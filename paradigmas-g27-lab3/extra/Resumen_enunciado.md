# Spark

Framework que utiliza una arquitectura maestro-trabajador donde la computadora “maestra” coordina el trabajo.

Este conjunto de computadoras forma lo que se llama un **cluster** de Spark. El flujo de ejecución es el siguiente:

1. El usuario envía una tarea o trabajo (job) al Master.
2. El Master divide el conjunto de datos en partes más pequeñas (particiones) y asigna cada parte a un Worker.
3. Cada Worker procesa su parte de los datos de forma paralela.    
4. Una vez que todos los Workers terminan, el Master recolecta los resultados parciales y los combina para producir el resultado final.

# Lab
Nosotros tenemos que averigüar cómo funciona la API de Spark.
[https://spark.apache.org/docs/latest/api/java/index.html](https://spark.apache.org/docs/latest/api/java/index.html)
Literalmente nos mandaron a leer
# Ejemplo de Spark
(Con shift+ruedita se desplaza horizontal)

Esto computa con Spark la cantidad de palabras que tiene un archivo de texto como [El Manifiesto Comunista](https://drive.google.com/file/d/1K6ShRmPxpk3k-Op8oWfYa0woEUM4vEy8/view?usp=sharing):

```Java
import scala.Tuple2;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class WordCounter {

  // Expresión regular para separar por espacios
  private static final Pattern SPACE = Pattern.compile(" ");
  public static void main(String[] args) throws Exception {

   // Verifica que se haya pasado al menos un argumento (el archivo de entrada)
   if (args.length < 1) {
       System.err.println("Usage: WordCounter <file>");
       System.exit(1);
   }

   // Crea una sesión de Spark en modo local (usa todos los núcleos de la máquina)
   SparkSession spark = SparkSession
           .builder()
           .appName("WordCounter")
           .master("local[*]") // Ejecuta en modo local usando todos los núcleos
           .getOrCreate();

   // Lee el archivo de texto especificado como RDD de líneas
   JavaRDD<String> lines = spark.read().textFile(args[0]).javaRDD();

   // Divide cada línea en palabras usando espacios como separador
   JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

   // Asocia a cada palabra el número 1 (pares <palabra, 1>)
   JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));

   // Suma los valores (conteos) asociados a la misma palabra (reduceByKey)
   JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);

   // Trae el resultado final a la máquina local como una lista de pares
   List<Tuple2<String, Integer>> output = counts.collect();

   // Imprime los resultados (palabra: cantidad de apariciones)
   for (Tuple2<?, ?> tuple : output) {
     System.out.println(tuple._1() + ": " + tuple._2());
   }

   // Cierra la sesión de Spark
   spark.stop();
  }
}
```

# Ejecutar Spark en modo local
Significa que Spark se ejecuta en tu compu, sin necesidad de un clúster distribuido.
- **Todos los procesos (driver y workers)** corren en **tu computadora**, como si fuera un mini-clúster de un solo nodo.
- **No se necesita levantar manualmente un master o worker.**
- Spark internamente simula la ejecución distribuida usando múltiples hilos o procesos.

Se compila y ejecuta como un proyecto de Java común, incluyendo los binarios de la librería de Spark. Este es un posible Makefile que pueden usar, adaptando los paths:

```shell
# Comment this line to run with default java
JAVA_PATH=/opt/homebrew/opt/openjdk@11/bin/

JAVAC=javac
JAVA=java
LIB_DIR=lib
# Directory where spark is installed
SPARK_FOLDER=/usr/local/spark-3.5.1-bin-hadoop3
# Extra libraries to include, replace with your own .jars
CLASSPATH=$(OUT_DIR):$(LIB_DIR)/json-20231013.jar:$(SPARK_FOLDER)/jars/*

SOURCES=$(shell find src -name "*.java")

all: build run

build:
	$(JAVA_PATH)$(JAVAC) -cp $(CLASSPATH) -d out $(SOURCES)

run:
	$(JAVA_PATH)$(JAVA) -cp $(CLASSPATH) SparkFeedFetcher

clean:
	rm -rf $(OUT_DIR)
```

## Montar un cluster de Spark

Ejecutarlo distribuido en múltiples máquinas. Spark se conecta al gestor del clúster (como YARN, Kubernetes, o Spark Standalone).

El procesamiento se distribuye automáticamente entre los nodos del clúster. Se puede simular un cluster aún dentro de una única computadora:

1. Dentro del directorio ${SPARK_FOLDER}/sbin, lanzar una instancia de master:   
    ./start-master.sh
2. Dentro del directorio ${SPARK_FOLDER}/sbin, lanzar una instancia de worker:  
    ./start-worker.sh spark://localhost:7077 -m 1G -c 1

Con los dos scripts de shell anteriores, se creó un cluster de Spark con un master y un worker. Si está todo ok, en la url [http://localhost:8080](http://localhot:8080) podemos observar el estado actual del cluster.
(Entrar a ese link en algún navegador, después de haber realizado los pasos anteriores)

Ahora estamos en condiciones de mandarle un trabajo a dicho cluster. Les queda como ejercicio investigar cómo realizar esta tarea.

# Qué entregar
## Makefile
Tiene que permitir ejecutar el programa con un solo comando
### [README.md](http://README.md)
Que responda las siguientes preguntas:
#### Título
Configuración del entorno y ejecución

Instrucciones para el usuario sobre cómo correr las dos partes del laboratorio con spark. Explicación del resultado que se espera luego de ejecutar cada parte.
#### Decisiones de diseño
Opcional. Cualquier cosa que quieran aclarar sobre la implementación del laboratorio
## Conceptos importantes
1. Describir el flujo de la aplicación:
- ¿Qué pasos sigue la aplicación desde la lectura del archivo feeds.json hasta la obtención de las entidades nombradas?
- ¿Cómo se reparten las tareas entre los distintos componentes del programa?
- ¿Por qué se decide usar Apache Spark para este proyecto?  
- ¿Qué necesidad concreta del problema resuelve?
2. Liste las principales ventajas y desventajas que encontró al utilizar Spark.
 - ¿Cómo se aplica el concepto de inversión de control en este laboratorio? Explique cómo y dónde se delega el control del flujo de ejecución.
- ¿Qué componentes deja de controlar el desarrollador directamente?

3. ¿Considera que Spark requiere que el codigo original tenga una integración tight vs loose coupling?
4. ¿El uso de Spark afectó la estructura de su código original?  
 - ¿Tuvieron que modificar significativamente clases, métodos o lógica de ejecución del laboratorio 2?

# Ejercicios adicionales

jaja no

## Ejemplo: Consola de Spark

Spark también puede ejecutarse mediante un shell interactivo (como ghci). Primero debemos lanzar dicho shell desde la carpeta ${SPARK_FOLDER}/bin:  
./spark-shell.sh spark://localhost:7077

Luego, leemos un archivo de texto “book.txt”  y computamos por ejemplo su cantidad líneas

```Bash
scala> val dataset = spark.read.textFile("/Users/ada/famaf/paradigmas25/el-manifiesto-comunista.txt")
dataset: org.apache.spark.sql.Dataset[String] = [value: string]

scala> dataset.count()
res0: Long = 439

scala> val dataset2 = dataset.filter(line => line.contains("Marx"))
dataset2: org.apache.spark.sql.Dataset[String] = [value: string]

scala> dataset2.count()
res1: Long = 1

scala> val dataset2 = dataset.filter(line => line.contains("proletario"))
dataset2: org.apache.spark.sql.Dataset[String] = [value: string]

scala> dataset2.count()
res2: Long = 18
```

## Posibles problemas

**Al instalar spark usando el link de la cátedra, funciona solo con java 11, pero tengo java 24**

Una opción es instalar java 11 y sobreescribir el binario de java solo para spark, de esta manera, la versión por defecto sigue siendo la más reciente. Luego se sobreescribe el JAVA_PATH para spark únicamente (versión para mac):

JAVA_HOME=/opt/homebrew/opt/openjdk@11 ./sbin/start-master.sh

En el enunciado hay un Makefile de ejemplo que pueden usar para compilar el laboratorio 3 con java 11

Corro alguno de los comandos como [start-master.sh](http://start-master.sh) o start_worker.sh pero no funciona

En la consola se imprime o el error, o el archivo donde se guardan los logs. Leer el archivo de logs para saber cuál es el problema.

**El master y el worker se inicializan correctamente en los puertos 8080 y 8081, pero el master no encuentra el worker**Spark, por defecto, toma el hostname del sistema (por ejemplo: Milagros-MacBook-Pro.local). Entonces la URL real del máster podría ser spark://Milagros-MacBook-Pro.local:7077 en lugar de localhost:7077

Para asegurarte, revisa el archivo de logs del proceso master y buscá una línea como Starting Spark master at \<url del master>:7077

Usá esa URL cuando inicies el worker. Recorda terminar el proceso primero con stop-worker.sh