import parser.*;
import subscription.*;
import feed.Feed;
import feed.Article;
import httpRequest.HttpRequester;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.NamedEntity;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Map;
import scala.Tuple2;
import java.util.Iterator;
import java.util.Collections;

public class SparkFeedReaderMain {
    
    private static void printHelp(){
    	System.out.println("Please, call this program in correct way: SparkFeedReader");
    }

    public static void main(String[] args) {
    	System.out.println("*************  FeedReader version 2.3 ft. Spark  *************");
        System.out.println(args);
        SparkSession mfspark = SparkSession.builder()
                                           .appName("FeedReader")
                                           .master("local[*]") // Eliminar para ejecutar en cluser
                                           .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(mfspark.sparkContext());
        /* Mira la verdad que hacerlo funcionar en mi maquina tiene toda la pinta de que va a hacerlo correr mas lerdo
         * que el FeedReader sin Spark, porque tiene dos nucleos, asi que el trabajo extra de "header" que meta spark 
         * estoy segurisimo que le saca la ventaja que pudo haber tenido si lo hubiera podido instalar en la compu 
         * grande.
         */
        /* Recordar: 
         * [X] Cerrar la sesion de spark con mfspark.stop() en alguna parte del final del main.
         * 
         * [X] Antes de la parte de paralelizacion, el enunciado recomienda guardar en disco los archivos.
         *
         * Se debe pararelizar dos partes:
         * [X] El de la lectura de la url. Osea un worker por cada feed.
         * [] Para cada articulo, cuando cuenta as entidades nombradas. Osea un worker por cada articulo.
         *
         */

        SubscriptionParser subParser = new SubscriptionParser();
        QuickHeuristic quickHeuristic = new QuickHeuristic();

		Feed mainFeed = new Feed("Main Feed Spark");
        
        if (args.length == 0) {
            Subscription result = subParser.reader("lib/config/subscriptions.json");
            List<SingleSubscription> subscriptions = result.getSubscriptionsList();
            JavaRDD<SingleSubscription> subscriptionsRDD = jsc.parallelize(subscriptions);
            
            subscriptionsRDD.foreach( subs -> {
                HttpRequester httpReq = new HttpRequester();
                RssParser rssParser = new RssParser();
                for (int parameter = 0 ; parameter < subs.getUrlParamsSize(); parameter++){
                    String url = subs.getFeedToRequest(parameter);
                    String contentXML = httpReq.getFeedRss(url);
                    Feed feed = rssParser.reader(contentXML);

                    if (feed != null && feed.getArticleList() != null && !feed.getArticleList().isEmpty()) {
                        synchronized (System.out) {
                            feed.prettyPrint();
                        }
                    }
                }
            });
            mfspark.stop();

		} else if (args.length >= 1 && (args[0].equals("-quick") || args[0].equals("-rand"))) {
            
            Subscription result = subParser.reader("lib/config/subscriptions.json");
            List<SingleSubscription> subscriptions = result.getSubscriptionsList();
            JavaRDD<SingleSubscription> subscriptionsRDD = jsc.parallelize(subscriptions);
            
            JavaRDD<Article> articlesRDD = subscriptionsRDD.flatMap( subs -> {
                List<Article> articles = new ArrayList<>();
                HttpRequester httpReq = new HttpRequester();
                RssParser rssParser = new RssParser();
                for (int parameter = 0 ; parameter < subs.getUrlParamsSize(); parameter++){
                    String url = subs.getFeedToRequest(parameter);
                    String contentXML = httpReq.getFeedRss(url);
                    Feed feed = rssParser.reader(contentXML);

                    if (feed != null && feed.getArticleList() != null && !feed.getArticleList().isEmpty()) {
                        for (Article article : feed.getArticleList()) {
                            articles.add(article);
                        }
                    }

                }
                return articles.iterator();
            });

            List<Article> allArticles = articlesRDD.collect();
            allArticles.forEach(mainFeed::addArticle);
                


            if (args[0].equals("-quick")) {
                JavaRDD<Feed> feedRDD = jsc.parallelize(Collections.singletonList(mainFeed));
                JavaRDD<NamedEntity> namedEntitiesRDD = feedRDD.flatMap(feed -> {
                    feed.setFeedNamedEntityList(quickHeuristic); // Esto ahora ocurrirá en un worker
                    return feed.getFeedNamedEntityList().iterator();
                });
                //* feedRDD.foreach(feed -> feed.prettyPrint());
                // mainFeed.setFeedNamedEntityList(quickHeuristic);
                // JavaRDD<NamedEntity> namedEntities = jsc.parallelize(mainFeed.getFeedNamedEntityList());
                JavaPairRDD<String, Integer> words = namedEntitiesRDD.mapToPair(entity -> new Tuple2<>(entity.getName(), entity.getFrequency()));
                // JavaPairRDD<String, Integer> words = feedMain.getFeedNamedEntityList().flatMap(namedEntity -> 
                //         new Tuple2<>(namedentity.getname(), namedEntity.getFrequency())); 
                JavaPairRDD<String, Integer> counts = words.reduceByKey((i1, i2) -> i1 + i2);
                List<Tuple2<String, Integer>> output = counts.collect();
                for (Tuple2<?, ?> tuple : output) {
                            System.out.println(tuple._1() + ": " + tuple._2());
                }
                // //

                mfspark.stop();
            }
        } else {
			printHelp();
        }
                // else if (args[0].equals("-rand"))
            //     RandomHeuristic randHeuristic = new RandomHeuristic();
            //     mainFeed.prettyDictPrint(mainFeed.counter(randHeuristic));
            //
	}
}

