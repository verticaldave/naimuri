# Wordsquare

## Compiling 
This is a standard maven project which compiles and runs using Java 11

## Packaging
In order to run the application from the command line the project must first be packaged into an executable jar using `mvn package`. I used maven 3.6.1

## Running
Once packaged navigate to the target directory of the project and run `java -jar word-square-0.0.1-SNAPSHOT-spring-boot.jar` followed by the input string, e.g. 

`java -jar word-square-0.0.1-SNAPSHOT-spring-boot.jar 7 aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy`