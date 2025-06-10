# XML PARSER
A program that fetchs URLS from json list and shows its content on screen in an 
almost-pretty way.

# Usage
Write links to fetch inside projectFolder/bin/config/subscriptions.json in the next way:
```json
[
    {	
	"url":"rss.example.link/%s.xml",
	"urlParams":["Interesting", "Categories"],
	"urlType":"rss"
    },
    {
	"_comment":"Included example",
	"url": "https://rss.nytimes.com/services/xml/rss/nyt/%s.xml",
	"urlParams": ["Business", "Technology"],
	"urlType": "rss",
	"download": "true"
    }
]
```
%s should go instead of any category.

# Build
### Requirements 
- JDK24 (May work with previous versions. Not tested)
- Make (Optional, can be used for testing individual classes)
## build.sh
A shell script file is with basic compilation and execution functionality.
Give executing permissions:
`chmod u+x build.sh`
Usage:
`./build.sh <compile|run|executable|count|clean>`
## Manually
Only basic functionality provided.
#### Compile: 
```Bash
javac -cp bin/json-20250107.jar -d bin \
  src/parser/GeneralParser.java \
  src/parser/SubscriptionParser.java \
  src/httpRequest/HttpRequester.java \
  src/subscription/Subscription.java \
  src/subscription/SingleSubscription.java \
  src/parser/RssParser.java \
  src/feed/Feed.java \
  src/feed/Article.java \
  src/namedEntity/NamedEntity.java \
  src/namedEntity/heuristic/Heuristic.java \
  src/namedEntity/heuristic/QuickHeuristic.java \
  src/namedEntity/heuristic/RandomHeuristic.java \
  src/FeedReaderMain.java
```
#### Run:
```Bash
java -cp bin:bin/json-20250107.jar FeedReaderMain`
```
#### Make executable (FeedReader.jar)
```Bash
echo "Main-Class: FeedReaderMain" > manifest
echo "Class-Path: bin/json-20250107.jar" >> manifest
jar cfvm FeedReaderMain.jar manifest -C bin .
rm manifest
```
#### Count Named Entities
Depends on executable file
`java -cp bin:bin/json-20250107.jar FeedReaderMain.jar -ne`
#### Clean
```Bash
  rm -rfdv \
    bin/parser \
    bin/subscription \
    bin/httpRequest \
    bin/feed \
    bin/namedEntity \
    bin/*.class \
    FeedReaderMain \
    FeedReaderMain.jar
    manifest
```
## Make
All make commands (except `clean`) compile the code. Is not necessary to execute `make [compile]` before each one.
#### Main functionality
- `$ make [compile]`:  Compiles everything and outputs everything to the `bin` folder.
- `$ make run`: Executes code. Reads subscriptions.jsons and leave.
- `$ make executable`:  Creates FeedReader.jar at project root.
- `$ make extraArgs`: Prints the number of NamedEntities.
- `$ make help`: Prints a short make list options.
- `$ make clean`: Removes files created during compilation. Leaves project directory clean
#### Test classes
Every command here test the class that follows "test".
- `$ make testSubscriptionParser`
- `$ make testHttpRequester`
- `$ make testSingleSubscription`
- `$ make testSubscription`
- `$ make testRSSParser`
- `$ make testArticle`
- `$ make testFeed`
- `$ make test`: Does all previous test together.
# Documentation
I leave this here because if I made it into a Docs directory no one would notice it :P
Please modularize your documentation into multiple files if you're documenting a bigger project.
## Project tree:
After cloning or `make clean`, the project tree looks like this, classes' methods are documented below.
```Ruby
ProjectRoot
├── bin
│   ├── config
│   │   └── subscriptions.json
│   └── json-20250107.jar
├── Makefile
├── README.md
└── src
    ├── feed
    │   ├── Article.java
    │   └── Feed.java
    ├── FeedReaderMain.java
    ├── httpRequest
    │   └── HttpRequester.java
    ├── namedEntity
    │   ├── heuristic
    │   │   ├── Heuristic.java
    │   │   ├── QuickHeuristic.java
    │   │   └── RandomHeuristic.java
    │   └── NamedEntity.java
    ├── parser
    │   ├── GeneralParser.java
    │   ├── RedditParser.java
    │   ├── RssParser.java
    │   └── SubscriptionParser.java
    └── subscription
        ├── Counter.java
        ├── SingleSubscription.java
        └── Subscription.java
```
If some class' import sounds interesting, looking for it into https://dev.java/ is recommended.
(Read oracle's documentation only if you'd like to burn your eyes)
## Src
This directory holds all classes that will be compiled
### FeedReaderMain.java
Main class. Instances other classes and prints its content.
## Feed
This directory contains classes that will be used for abstract websites' content
### Article.java
This class holds data for each "website".
An articule will be instanced for each UrlParam given.
#### Constructor
Initialize private variables with given parameters:
```Java
Article(String title, String text, Date publicationDate, String link)
```
#### Getters
```Java
String getTitle()
String getText()
Date getPublicationDate()
String getLink()
List<NamedEntity> getNamedEntityList()
```
#### Setters
```Java
void setTitle(String title)
void setText(String text)
void setPublicationDate(Date publicationDate)
void setLink(String link)
```
#### Interaction
```Java
// Returns a string containing Class' private variables along their content
String toString() 

// Returns NamedEntity if found arg given inside private List. Null otherwise
NamedEntity getNamedEntity(String namedEntity)

// Given Heuristic class-ification, counts aparitions and 
// sets private NamedEntityList with it.
void computeNamedEntities(Heuristic h)

// Prints Article's content with some decorations
void prettyPrint()
```
#### Used in
Feed.java
#### Comments
Date class is imported trough java.util.Date
### Feed.java
This class holds an ArticleList. A feed will be instanced for each Url given.
#### Constructor
```Java
Feed(String siteName)
```
#### Getters
```Java
String getSiteName()
List<Article> getArticleList()
Article getArticle(int i)       // Get article in position i
int getNumberOfArticles()       // Get ArticleList's length
```
#### Setters
```Java
void setSiteName(String siteName)
void setArticleList(List<Article> articleList)
```
#### Interaction
```Java
// Add an Article to the list
void addArticle(Article a)

// Returns String containing private variables "siteName" and "articleList"
// along their content
String toString()

// Returns a dictionary with each Article's counters for given Heuristic class.
Map<String, Integer> counter (Heuristic h)

// Given dictionary, print its content in a pretty-looking way
void prettyDictPrint(Map<String, Integer> dict)

// Calls each article's prettyPrint function
void prettyPrint()
```
## HtttpRequest
### HttpRequester.java
This class is used to get urls' content as a String with XML format.
Refer to https://www.baeldung.com/java-http-request for more information.
#### Constructor
```Java
HttpRequester()
```
#### Getters
```Java
// Recieves url and returns String with XML format
String getFeedRss(String urlFeed) 

```
#### Used by
FeedMain.java
## NamedEdentity (Herusitics)
### NamedEntity.java
Class that holds entity's data, such as its name, category and frequency with which it appears.
#### Constructors
```Java
NamedEntity(String name, String category, int frequency)
```
#### Getters
```Java
String getName()
String getCategory()
int getFrequency()
```
#### Setters
```Java
void setName(String name)
void setCategory(String name)
void setFrequency(int frequency)
void incFrequency()               // Increases frecuency by 1
```
#### Interaction
```Java
// Returns string with name and frecuency alongside their current values.
String toString() 

// Prints name and frecuency's values
void prettyPrint()
```
#### Used by
Article, Feed
### Heuristics
#### Heuristic.java
This class holds some words a classification between "Company", "Apellido" and "Country". 
##### Private variables
```Java
// Pair of strings in a <Name,Category> way
static Map<String, String> categoryMap
```
##### Getters
```Java
String getCategory(String entity) // Self-explanatory
```
##### Interaction
```Java
// For childrens to decide if a word is counted as entity or not
abstract boolean isEntity(String word)
```
##### Used by

#### QuickHeuristic.java
This class extends Heuristic and adds a contractions list.
##### Interaction
```Java
boolean isEntity(String word) // Checks only for this Heuristic
```
#### RandomHeuristic.java
This class extends Heuristic too.
When adding a new entity, it randomly chooses if add it as entity or not. 
##### Interaction
```Java
// True if entity is into this class' private positives list
// False otherwise
// If wasn't classified, add it into a list randomly
boolean isEntity(String word)
```
## Parser
### GeneralParser.java
Parsers' superclass
#### Constructor
```Java
GeneralParser()
```
#### Abstract Method
```Java
abstract T reader(String fileName); // T is for polymorphism
```
#### Used by
SubscriptionParser, RssParser
### RedditParser.java
Not implemented
### RssParser.java
This class is made for receiving an XML string, interpret its content, build a list of Articles based on it and return a Feed containing it.

#### Constructor
```Java
RssParser()
```
#### Interaction
```Java
Feed reader(String contentXML) 
```
#### Comments
For more info, refer to https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
#### Used by
FeedReaderMain
### SubscriptionParser.java
This class interprets a JSON file's content and parses it into a Subscription class.
(Subscription holds a list of SingleSubscription)
Refer to https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html for more
information.
#### Constructor
```Java
SubscriptionParser()
```
#### Interaction
```Java
Subscription reader(String fileName) // Receieves a JSON file's path
```
#### Used by
FeedReaderMain
## Subscription
### Counter.java
A single counter.
#### Interaction
```Java
void increment() // Increments private counter by 1
void printValue() // Prints counter value
```
### SingleSubscription.java
Class that will contain one JSON object's information like url and given parameters. It's the first abstraction layer.
#### Constructor
```Java
// Arguments like specified in subscriptions.json.
SingleSubscription(String url, List<String> urlParams, String urlType)
```
#### Getters
```Java
String getUrl()
String getUrlType
String getUrlParams(int i)   // Get by index, starting from 0.
List<String> getUrlParams()
int getUrlParamsSize()       // List length
```
#### Setters
```Java
void setUrl(String url)
void setUrlParams(String urlParam) // Adds one parameter at the end of the list
void setUrlType(String urlType)
```
#### Interaction
```Java
// Returns string containing private variables along their values.
String toString() 

// Just calls toString and prints it.
void prettyPrint() 
```
#### Used by
FeedReaderMain, SubscriptionParser, Subscription
### Subscription.java
Contains a SingleSubscription List. Offers some operators
#### Constructor
```Java
Subscription()
```
#### Getters
```Java
List<SingleSubscription> getSubscriptionsList()
SingleSubscription getSingleSubscription(int i) // 0 as starting index
```
#### Interaction
```Java
// Add subscription at the list's end
addSingleSubscription(SingleSubscription s)

// Returns array-like string listed subscriptions (e.g [a,b,c]), calling to 
// each SingleSubscription's toString method.
String toString()

// Calls toString and prints it.
void prettyPrint()
```
#### Used by
SubscriptionParser
