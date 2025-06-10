import parser.*;
import subscription.*;
import feed.Feed;
import feed.Article;
import httpRequest.HttpRequester;
import namedEntity.heuristic.QuickHeuristic;

public class FeedReaderMain {

	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
	}

	public static void main(String[] args) {
		System.out.println("*************  FeedReader version 1.2  *************");

		SubscriptionParser subParser = new SubscriptionParser();
		HttpRequester httpReq = new HttpRequester();
		RssParser rssParser = new RssParser();
		String contentXML;
		String url;
		Feed feed;

		Subscription subscriptions = subParser.reader("lib/config/subscriptions.json");
		Feed mainFeed = new Feed("Main Feed");

		if (args.length == 0) {
			for (SingleSubscription sub : subscriptions.getSubscriptionsList()) {
				for (int i = 0; i < sub.getUrlParamsSize(); i++) {
					url = sub.getFeedToRequest(i);
					contentXML = httpReq.getFeedRss(url);
					feed = rssParser.reader(contentXML);

					if (feed != null && feed.getArticleList() != null) {
						for (Article article : feed.getArticleList()) {
							mainFeed.addArticle(article);
						}
					}
				}
                mainFeed.prettyPrint();
			}

			/*
			 * [x] Leer el archivo de suscription por defecto
			 * [x] Llamar al httpRequester para obtener el feed del servidor
			 * [x] Llamar al Parser especifico para extrar los datos necesarios por
			 *      la aplicacion
			 * [x] Llamar al constructor de Feed
			 * [x] LLamar al prettyPrint del Feed para ver los articulos del feed en
			 * forma legible y amigable para el usuario
			 */

		} else if (args.length == 1 && args[0].equals("-ne")) {

			QuickHeuristic quickHeuristic = new QuickHeuristic();

			for (SingleSubscription sub : subscriptions.getSubscriptionsList()) {
				for (int i = 0; i < sub.getUrlParamsSize(); i++) {
					url = sub.getFeedToRequest(i);
					contentXML = httpReq.getFeedRss(url);
					feed = rssParser.reader(contentXML);

					if (feed != null && feed.getArticleList() != null) {
						for (Article article : feed.getArticleList()) {
							mainFeed.addArticle(article);
						}
					}
				}
				mainFeed.prettyDictPrint(mainFeed.counter(quickHeuristic));
			}

			/*
			 * Ponele que sea casi lo mismo que otro caso
			 * Leer el archivo de suscription por defecto;
			 * Llamar al httpRequester para obtener el feed del servidor
			 * Llamar al Parser especifico para extraer los datos necesarios por la aplicacion 
			 * Llamar al constructor de Feed
			 * Llamar a la heuristica para que compute las entidades nombradas de cada articulos del feed
			 * LLamar al prettyPrint de la tabla de entidades nombradas del feed.
			 */
			
		} else {
			printHelp();
		}
	}
}
