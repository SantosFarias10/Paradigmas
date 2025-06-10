JAR_PATH = ./bin/json-20250107.jar
SRC_DIR  = ./src
LIB_DIR  = ./bin

GENPARSER_DIR = $(SRC_DIR)/parser/GeneralParser.java
RSSPARSER_DIR = $(SRC_DIR)/parser/RssParser.java
SUBPARSER_DIR = $(SRC_DIR)/parser/SubscriptionParser.java
HTTPREQUE_DIR = $(SRC_DIR)/httpRequest/HttpRequester.java
SUBSCRIPT_DIR = $(SRC_DIR)/subscription/Subscription.java
SINGLRSUB_DIR = $(SRC_DIR)/subscription/SingleSubscription.java
FEED_DIR 	  = $(SRC_DIR)/feed/Feed.java
ARTICLE_DIR   = $(SRC_DIR)/feed/Article.java
NAMEENTIT_DIR = $(SRC_DIR)/namedEntity/NamedEntity.java
HEURISTI1_DIR = $(SRC_DIR)/namedEntity/heuristic/Heuristic.java
HEURISTI2_DIR = $(SRC_DIR)/namedEntity/heuristic/QuickHeuristic.java
HEURISTI3_DIR = $(SRC_DIR)/namedEntity/heuristic/RandomHeuristic.java
FEEDREADE_DIR = $(SRC_DIR)/FeedReaderMain.java

RSSPARSER_MAIN_CLASS = parser.RssParser
SUBPARSER_MAIN_CLASS = parser.SubscriptionParser
HTTPREQUE_MAIN_CLASS = httpRequest.HttpRequester
SINGLESUB_MAIN_CLASS = subscription.SingleSubscription
SUBSCRIPT_MAIN_CLASS = subscription.Subscription
ARTICLE_MAIN_CLASS 	 = feed.Article
FEED_MAIN_CLASS 	 = feed.Feed
FEEDREADE_MAIN_CLASS = FeedReaderMain

# Ir agregando los PATH de cada archivo que se quiere compilar
compile:
	@javac -cp $(JAR_PATH) -d $(LIB_DIR) $(GENPARSER_DIR) $(SUBPARSER_DIR) \
		$(HTTPREQUE_DIR) $(SUBSCRIPT_DIR) $(SINGLRSUB_DIR) $(RSSPARSER_DIR)  \
		$(FEED_DIR) $(ARTICLE_DIR) $(NAMEENTIT_DIR) $(HEURISTI1_DIR) \
		$(HEURISTI2_DIR) $(HEURISTI3_DIR) $(FEEDREADE_DIR)
	@echo "*************      Compilacion ok      *************"

run: compile
	@echo "*************       Feed Reader        *************"
	@java -cp $(LIB_DIR):$(JAR_PATH) $(FEEDREADE_MAIN_CLASS)
	@echo "*************     End Feed Reader      *************"

executable: compile
	@echo "*************      Creando el JAR      *************"
	@touch manifest
	@echo "Main-Class: $(FEEDREADE_MAIN_CLASS)" > manifest
	@echo "Class-Path: $(JAR_PATH) " >> manifest
	@jar cfvm FeedReader manifest -C $(LIB_DIR) .
	@rm manifest
	@echo "************* JAR FeedReader creado ok *************"
	@echo "El Jar ser ejecuta con java -jar FeedReader"
	@echo "O con el argumento para la heuristica java -jar FeedReader -ne"

help:
	@echo "<make help>"
	@echo "no me vas a creer si te digo que te tira el help del Makefile"
	@echo "<make>"
	@echo "compila el codigo"
	@echo "<make run>"
	@echo "ejecuta el FeedReader pelado"
	@echo "<make extrArgs>"
	@echo "ejecuta el FeedReader con el argumento -ne"
	@echo "<make executable>"
	@echo "crea el ejecutable jar para poder hacer java -jar FeedReader -ne ó sin el -ne"
	@echo "<make test>"
	@echo "ejecuta todos los test que hicimos, tambien se pueden llamar individualmente"
	@echo "<make clean>"
	@echo "te hace una bomba fork que te llena el directorio principal de archivos con tu direccion ip"

testSubscriptionParser: compile
	@echo "************* Test Subscription Parser *************"
	@java -cp $(LIB_DIR):$(JAR_PATH) $(SUBPARSER_MAIN_CLASS)
	@echo "*************        Test end          *************"

testHttpRequester: compile
	@echo "*************   Test Http Requester    *************"
	@java -cp $(LIB_DIR):$(JAR_PATH) $(HTTPREQUE_MAIN_CLASS)
	@echo "*************        Test end          *************"

testSingleSubscription: compile
	@echo "************* Test Single Subscription *************"
	@java -cp $(LIB_DIR):$(JAR_PATH) $(SINGLESUB_MAIN_CLASS)
	@echo "*************        Test end          *************"

testSubscription: compile
	@echo "*************    Test Subscription     *************"
	@java -cp $(LIB_DIR):$(JAR_PATH) $(SUBSCRIPT_MAIN_CLASS)
	@echo "*************        Test end          *************"

testRSSParser: compile
	@echo "*************     Test RSS Parser      *************"
	@java -cp $(LIB_DIR):$(JAR_PATH) $(RSSPARSER_MAIN_CLASS)
	@echo "*************        Test end          *************"

testArticle: compile
	@echo "*************      Test Article        *************"
	@java -cp $(LIB_DIR):$(JAR_PATH) $(ARTICLE_MAIN_CLASS)
	@echo "*************        Test end          *************"

testFeed: compile
	@echo "*************       Test Feed          *************"
	@java -cp $(LIB_DIR):$(JAR_PATH) $(FEED_MAIN_CLASS)
	@echo "*************        Test end          *************"

extrArgs: executable
	@echo "*************       Test Main Args     *************"
	@echo "Quick Heuristic"
	@java -jar FeedReader -quick
	@echo "Random Heuristic"
	@java -jar FeedReader -rand
	@echo "*************        Test end          *************"

test: compile testSubscriptionParser testHttpRequester testSingleSubscription \
	  testSubscription testRSSParser testArticle testFeed extrArgs
	@echo "************* Test Completo Sin Error  *************"

clean:
	@rm -rfdv $(LIB_DIR)/parser $(LIB_DIR)/subscription $(LIB_DIR)/httpRequest \
	$(LIB_DIR)/feed $(LIB_DIR)/namedEntity $(LIB_DIR)/*.class FeedReader manifest
	@echo "*************         Clean ok         *************"

.PHONY: compile help test clean run executable testSubscriptionParser \
		testHttpRequester testSingleSubscription testSubscription testRSSParser \
		testArticle testFeed extrArgs
