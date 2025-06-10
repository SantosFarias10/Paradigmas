package httpRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import java.io.Serializable;
/* 
 * Esta clase se encarga de realizar efectivamente el pedido de feed al servidor 
 * de noticias leer sobre como hacer una http request en java
 * https://www.baeldung.com/java-http-request
 */

/*
 * Usamos HttpClient que es la mejora? de HttpUrlConnection
 */

public class HttpRequester implements Serializable {
    
	private final HttpClient httpClient;

	/* Constructor */
    public HttpRequester() {
		this.httpClient = HttpClient.newBuilder()
									.connectTimeout(Duration.ofSeconds(5))
									.build();
	}

	public String getFeedRss(String urlFeed) {
		HttpRequest request = HttpRequest.newBuilder()
										 .uri(URI.create(urlFeed))
										 .header("User-Agent", "Java RSS Reader")
										 .GET()
										 .build();
		
		try {
			HttpResponse<String> response = httpClient.send(request,
															HttpResponse.BodyHandlers.ofString());
			
			if (response.statusCode() == 200) {
            	return response.body();
        	} else {
            	System.err.println("Error HTTP: " + response.statusCode());
            	return null;
        	}
		} catch (Exception e) {
			System.err.println("Error en la solicitud: " + e.getMessage());
			return null;
		}
	}

	/* No implementado */
	/* public String getFeedReedit(String urlFeed) {
		String feedReeditJson = null;
		return feedReeditJson;
	} */

	public static void main(String[] args) {
		HttpRequester requester = new HttpRequester();
		String testUrl = "https://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
		String feedContent = requester.getFeedRss(testUrl);

		if (feedContent != null) {
			System.out.println("Primeros 1024 caracteres de Feed (son muchisimos):\n");
			System.out.println(feedContent.substring(0, Math.min(1024, feedContent.length())) + "...");
		} else {
			System.out.println("Error no se pudo obtener el feed");
		}
	}
}
