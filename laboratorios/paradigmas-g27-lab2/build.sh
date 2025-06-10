#!/usr/bin/env bash

#!/bin/bash

JAR_PATH="bin/json-20250107.jar"
SRC_DIR="src"
BIN_DIR="bin"

# Classes 
SRC_FILES=(
  src/parser/GeneralParser.java
  src/parser/SubscriptionParser.java
  src/httpRequest/HttpRequester.java
  src/subscription/Subscription.java
  src/subscription/SingleSubscription.java
  src/parser/RssParser.java
  src/feed/Feed.java
  src/feed/Article.java
  src/namedEntity/NamedEntity.java
  src/namedEntity/heuristic/Heuristic.java
  src/namedEntity/heuristic/QuickHeuristic.java
  src/namedEntity/heuristic/RandomHeuristic.java
  src/FeedReaderMain.java
)

# Main Class
MAIN_CLASS="FeedReaderMain"
JAR_NAME="FeedReaderMain.jar"

compile() {
  echo "Building..."
  javac -cp "$JAR_PATH" -d "$BIN_DIR" "${SRC_FILES[@]}"
  echo "*************       Compilación OK       *************"
}

run() {
  compile
  java -cp "$BIN_DIR:$JAR_PATH" "$MAIN_CLASS"
}

executable() {
  compile
  echo "Creating JAR..."
  echo "Main-Class: $MAIN_CLASS" > manifest
  echo "Class-Path: $JAR_PATH" >> manifest
  jar cfvm "$JAR_NAME" manifest -C "$BIN_DIR" .
  rm manifest
  echo "*************     JAR generated as: $JAR_NAME     *************"
}

count() {
  executable
  echo "Counting entities..."
  "java -cp bin:bin/json-20250107.jar FeedReaderMain.jar -ne"
}

clean() {
  echo "Cleaning compiled files..."
  rm -rfdv \
    $BIN_DIR/parser \
    $BIN_DIR/subscription \
    $BIN_DIR/httpRequest \
    $BIN_DIR/feed \
    $BIN_DIR/namedEntity \
    $BIN_DIR/*.class \
    FeedReaderMain \
    FeedReaderMain.jar
    manifest
  echo "*************         Clean finished!         *************"
}

# Usage
usage() {
  echo "Usage: $0 <compile|run|executable|count|clean>"
}

# Execute using given argument
case $1 in
  compile) 
    compile ;;
  run)
    run ;;
  executable)
    executable ;;
  count)
    count ;;
  clean)
    clean ;;
  *)
    usage ;;
esac

