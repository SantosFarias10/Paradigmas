package feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import namedEntity.heuristic.Heuristic;
import namedEntity.NamedEntity;
import java.util.Set;

import java.io.Serializable;
/* Esta clase modela la lista de articulos de un determinado feed */
public class Feed implements Serializable {
	
	String siteName;
	List<Article> articleList;
	
    private List<NamedEntity> feedNamedEntityList = new ArrayList<NamedEntity>();
	
    public Feed(String siteName) {
		super();
		this.siteName = siteName;
		this.articleList = new ArrayList<Article>();
	}

	public String getSiteName(){
		return siteName;
	}
	
	public void setSiteName(String siteName){
		this.siteName = siteName;
	}
	
	public List<Article> getArticleList(){
		return articleList;
	}
	
	public void setArticleList(List<Article> articleList){
		this.articleList = articleList;
	}
	
	public void addArticle(Article a){
		this.getArticleList().add(a);
	}
	
	public Article getArticle(int i){
		return this.getArticleList().get(i);
	}
	
	public int getNumberOfArticles(){
		return this.getArticleList().size();
	}

    public NamedEntity getFeedNamedEntity(String namedEntity){
		for (NamedEntity n: feedNamedEntityList){
			if (n.getName().compareTo(namedEntity) == 0){
				return n;
			}
		}
		return null;
	}
	
	@Override
	public String toString(){
		return "Feed [siteName=" + getSiteName() + ", articleList=" + getArticleList() + "]";
	}

    public void setFeedNamedEntityList(Heuristic h){
        List<Article> list = this.getArticleList();
        for (Article article: list) {
            article.computeNamedEntities(h);

            for (NamedEntity ne: article.getNamedEntityList()) {
               NamedEntity thisNe = this.getFeedNamedEntity(ne.getName());
               if (thisNe == null) {
                   this.feedNamedEntityList.add(ne);
               } else {
                   thisNe.setFrequency(thisNe.getFrequency() + ne.getFrequency());
               }

            }
        }
    }

    public List<NamedEntity> getFeedNamedEntityList() {
        return this.feedNamedEntityList;
    }

	public Map<String, Integer> counter (Heuristic h) {
		Map<String, Integer> dict = new HashMap<>(); 
        setFeedNamedEntityList(h);
		for (NamedEntity ne: this.feedNamedEntityList) {
            String category = ne.getCategory();
            Integer freq = ne.getFrequency();

			if(!dict.containsKey(category)) {
				dict.put(category, freq);
			} else {
				Integer temp = dict.get(category);
				dict.put(category, temp + freq);
			}
		}
	    return dict;
	}

    public void prettyDictPrint(Map<String, Integer> dict){
        Set<String> keys = dict.keySet();
        System.out.println("------------------------"); 
        for (String key: keys) {
            int ocr = dict.get(key);
            System.out.println("| " + key + " | " + ocr + " | ");
            System.out.println("------------------------");
        }
        for (NamedEntity named: this.feedNamedEntityList) {
            named.prettyPrint();
        }
        System.out.println("------------------------");
    }


	public void prettyPrint(){
		for (Article a: this.getArticleList()){
			a.prettyPrint();
		}	
	}
	
	public static void main(String[] args) {
		  Article a1 = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
			  	  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
			  	 	   new Date(),
			  	  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
			  	  );
		 
		  Article a2 = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
				  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
				  new Date(),
				  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
				  );
		  
		  Article a3 = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
				  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
				  new Date(),
				  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
				  );
		  
		  Feed f = new Feed("nytimes");
		  f.addArticle(a1);
		  f.addArticle(a2);
		  f.addArticle(a3);

		  f.prettyPrint();
		  
	}	
}
