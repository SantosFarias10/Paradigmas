package parser;

/* 
* Importamos las clases necesarios de Java DOM (DOM es un modelo (Document 
* Object Model)) DocumentBuilderFactory es una API de fabrica para obtener el
* analizador para documentos xml mediante la creacion de Arboles. Tiene el 
* metodo "newDocumentBuilder" donde crea una instacia de la clase 
* "DocumentBuilder". Esta clase se utiliza para obtener entrada en forma de 
* stream/archivos/URLs.
*/

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import httpRequest.HttpRequester;

import feed.Article;
import feed.Feed;

//import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
/* 
 * Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 */

public class RssParser extends GeneralParser {

    private List<Article> articles;
    private String description;
    private String title;
    private Date pubDate;
    private String link;
    private Feed feed;

    /*
     * Deberiamos devolver una lista de todo lo que extramos ¿Feed? ya que en
     * Feed.java dice
     * "Esta clase modela la lista de articulos de un determinado feed"
     */
    public RssParser() {
        feed = new Feed(null);
    }

    @Override
    public Feed reader(String contentXML) {

        if (contentXML == null || contentXML.isEmpty()) {
            System.err.println("Contenido XML vacío o nulo");
            return null;
        }

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            /*
             * según la pagina del parse "It contains a method named parse()
             * which accepts a file or input stream as a parameter and returns a
             * DOM Document object" por lo que acepta como parametro "InputStream"
             */
            Document xmlDoc = docBuilder.parse(new ByteArrayInputStream(contentXML.getBytes()));

            /*
             * obtenemos los elementos raiz del xml y estandarizamos el árbol DOM
             * (eliminamos los "nodos fantasmas")
             */
            xmlDoc.getDocumentElement().normalize();

            Element element = xmlDoc.getDocumentElement();
            NodeList channel = element.getElementsByTagName("channel");
            NodeList nList = channel.item(0).getChildNodes();

            articles = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    NodeList titleNodes = eElement.getElementsByTagName("title");
                    if (titleNodes.getLength() > 0) {
                        title = titleNodes.item(0).getTextContent();
                    }

                    NodeList descriptionNodes = eElement.getElementsByTagName("description");
                    if (descriptionNodes.getLength() > 0) {
                        description = descriptionNodes.item(0).getTextContent();
                    }

                    NodeList pubDateNodes = eElement.getElementsByTagName("pubDate");
                    if (pubDateNodes.getLength() > 0) {
                        String dateStr = pubDateNodes.item(0).getTextContent();
                        try {
                            pubDate = dateFormat.parse(dateStr);
                        } catch (Exception e) {
                            System.err.println("Error al parsear la fecha: " + dateStr);
                            pubDate = null;
                        }
                    }

                    NodeList linkNodes = eElement.getElementsByTagName("link");
                    if (linkNodes.getLength() > 0) {
                        link = linkNodes.item(0).getTextContent();
                    }

                    if (title != null || description != null || pubDate != null || link != null) {
                        articles.add(new Article(title, description, pubDate, link));
                    }
                }
            }

            /*
             * El Feed debe tomar un "siteName"??????? idk y la lista de articulos que cree
             */
            this.feed.setArticleList(articles);
            return this.feed;

        } catch (ParserConfigurationException e) {
            System.err.println("Error de configuración del parser: " + e.getMessage());
            return null;
        } catch (SAXException e) {
            System.err.println("Error al parsear XML: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("Error de I/O: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        HttpRequester httpReq = new HttpRequester();
        RssParser rss = new RssParser();

        String url = "https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml";
        String contentXML = httpReq.getFeedRss(url);
        Feed feed = rss.reader(contentXML);
        feed.prettyPrint();

        QuickHeuristic q = new QuickHeuristic();
        Map<String, Integer> dict = feed.counter(q);
        feed.prettyDictPrint(dict);
    }
}
