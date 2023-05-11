# Wordsquare

## Compiling 
This is a standard Maven project which compiles and runs using Java 11

## Packaging
In order to run the application from the command line the project must first be packaged into an executable jar using `mvn package`. I used maven 3.6.1

## Running
Once packaged navigate to the target directory of the project and run `java -jar word-square-0.0.1-SNAPSHOT-spring-boot.jar` followed by the input string, e.g. 

`java -jar word-square-0.0.1-SNAPSHOT-spring-boot.jar 7 aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy`

## Execution & Configuration Details

`Application.java` is the main entry point into the application, which immediately invokes the `WordSquare.generate` method passing the grid configuration arguments as a String. This method in turn passes the configuration String to the WordSquareConfig class for verification and fail fast (We don't want to try and plough ahead with an obviously invalid configuration). `WordSquareConfig` carries out a few sanity checks on the input parameters and makes sure the dictionary can be loaded into memory. It then filters the dictionary entries so that only words of the correct length and containing the allowed characters from the configuration String remain; this will speed up future dictionary searches due to the vastly reduced number of entries. If the configuration completes without error `WordSquare.generate` cracks on with the task in hand.

A small but important note is `WordSquareConfig` doesn't just sanity check the allowed characters from the input, it also sorts them alphabetically. This is an important point, as any given configuration String may not have characters of the same value grouped together. The ordering ensures that when a word is matched, it can be verified correctly and the characters it spent from the available characters can be more easily removed prior to searching for the next word  

## The algorithm

The core of the algorithm is a while loop based on the index of the word currently being searched (in a 5x5 grid, index 0 is the first word and index 4 is the last). This approach was chosen because it is necessary for one to explicitly increment, decrement or do nothing to the index value to manage the progression of the loop. This makes it easier to back up one step and change the last found word if we can't get a match for the one we're currently trying to find.

Starting at index 0 (the first word) the following steps are followed

**Step 1. The first word (index 0)**

- Create a regular expression with a character class containing all the allowed characters from the input String and an occurrence (length) of the grid size
- Use the regex to find matches in the dictionary. If there are no matches the search method will throw an exception and it's immediately game over
- Given a match, it is verified that there are enough characters in the configuration String to render the word both horizontally and vertically and that we haven't previously matched that word
- Once verified the first match is stored alongside the characters that were available to search for it. It is also stored as a previously matched index 0 word. This will become important later
- Remove the used characters from the available characters

**Step 2. The second word (n = index 1)**

- Create a regular expression where the n -1 character of the word is the character at index n of the word matched in the prior step and the character class comprises all the remaining allowed characters matched in the prior step 
- Use the regex to find matches in the dictionary.
- Given a match, it is verified that there are enough characters in the configuration String to render the word both horizontally and vertically and that we haven't previously matched that word
- Once verified it is stored alongside the characters that were available to search for it. It is also stored as a previously matched index 1 word 
- Remove the used characters from the available characters
- If there are no matches the search method will throw an exception and we go back to step 1 (but with a history of previously matched words)


**further words (index n)**

- Create a regular expression where the *0..n - 1* characters of the string are the nth character of all previously matched words and the remaining character class comprises the remaining allowed characters after previous steps
- If nothing matched got to step n - 1