package subscription;

import java.util.ArrayList;
import java.util.List;

/* Porque lo agregamos? */
//import httpRequest.HttpRequester;

/* 
 * Esta clase abstrae el contenido de una sola subscripcion que ocurre en lista 
 * de subscripciones que figuran en el archivo de subscripcion(json) 
 */

public class SingleSubscription {
	
	private String url;
	private List<String> urlParams;
	private String urlType;
	//private HttpRequester httpRequester;
	
	public SingleSubscription(String url, List<String> urlParams, String urlType) {
		super();
		this.url = url;
		if (urlParams != null) {
			this.urlParams = urlParams;
		} else {
			this.urlParams = new ArrayList<>();
		}
		this.urlType = urlType;
		//this.httpRequester = new HttpRequester();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getUrlParams() {
		return urlParams;
	}

	public String getUrlParams(int i) {
		return this.urlParams.get(i);
	}

	public void setUrlParams(String urlParam) {
		this.urlParams.add(urlParam);
	}

	public int getUrlParamsSize() {
		return urlParams.size();
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	} 
	
	@Override
	public String toString() {
		return "{url=" + getUrl() + ", urlParams=" + getUrlParams().toString() + ", urlType=" + getUrlType() + "}";
	}
	
	public void prettyPrint(){
		System.out.println(this.toString());
	}
	
	public String getFeedToRequest(int i) {
		return this.getUrl().replace("%s",this.getUrlParams(i));
	}
	
	public static void main(String[] args) {
		System.out.println("SingleSubscriptionClass");
		SingleSubscription s = new SingleSubscription("https://rss.nytimes.com/services/xml/rss/nyt/%s.xml", null, "rss");
		s.setUrlParams("Business");
		s.setUrlParams("Technology");
		System.out.println(s.getFeedToRequest(0));
		System.out.println(s.getFeedToRequest(1));
		s.prettyPrint();
	}
}
