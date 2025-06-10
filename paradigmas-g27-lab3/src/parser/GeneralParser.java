package parser;

//import feed.Feed;

/* 
 * Esta clase modela los atributos y metodos comunes a todos los distintos tipos
 * de parser existentes en la aplicacion
 */
public abstract class GeneralParser<T> {
    // public static Feed parse(String fileName) {
    //     Feed feed = null;
    //     RssParser p = new RssParser();
    //     feed = p.reader(fileName);
    //     return feed;
    // }
    /* 
     * Lo "definimos" como "abstract" ya que en GeneralParser solamente vamos a 
     * poner la "Especificacion" ya que en este archivo no la implementamos, 
     * sino que se implementa en las clases que heredan de la misma.
     * Nose en que momento ni porque le pusimos reader en vez de parser pero 
     * quedo asi.
     */
    public abstract T reader(String fileName);

    public GeneralParser() {};
}
