package com.naimuri.wordsquare;

import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.function.Predicate;

public class WordSquareConfig {
  
  static final String INVALID_INPUT_UNPARSEABLE = "Value must begin with an integer grid size argument, followed by a space, followed by allowed characters";
  static final String INVALID_INPUT_CHARACTERS = "The number of allowed characters must be the square of the grid size and comprise only a-z lower case values";
  static final String DICTIONARY_NOT_FOUND = "Unable to open dictionary. Check location on the classpath and file permissions";
    
  private final Integer wordLength;
  private String allowedCharacters;
  private final Set<String> dictionary;
  
  public static final WordSquareConfig init(String initString) {
    
    return new WordSquareConfig(initString);
  }
    
  public WordSquareConfig(String initString) {
    
    Integer wordLength;
    String allowedCharacters;
    
    try {

      String[] inputs = initString.split(" ");
      wordLength = Integer.parseInt(inputs[0]);
      allowedCharacters = inputs[1];
      
      assert allowedCharacters.matches(format("^[a-z]{%d}$", round(pow(wordLength, 2)))) : INVALID_INPUT_CHARACTERS;

      this.wordLength = wordLength;
      this.allowedCharacters = sortAllowedCharacters(allowedCharacters);
      this.dictionary = getCandidateDictionaryEntries(wordLength, allowedCharacters);
      
    } catch (IOException e) {
      throw new RuntimeException(DICTIONARY_NOT_FOUND, e);
    } catch (AssertionError e) {
      throw new IllegalArgumentException(e.getMessage(), e.getCause());
    } catch (Exception e) {
      throw new IllegalArgumentException(INVALID_INPUT_UNPARSEABLE, e);
    }  
  }

  private String sortAllowedCharacters(String allowedCharacters) {
    
    return allowedCharacters.chars()
                            .mapToObj(c -> (char)c)
                            .map(Object::toString)
                            .sorted()
                            .collect(joining());
  }

  private Set<String> getCandidateDictionaryEntries(Integer wordLength, String allowedCharacters) throws IOException {

    Predicate<String> predicate = compile(format("^[%s]{%d}$", allowedCharacters, wordLength)).asPredicate();
    
    try (InputStream inputStream = getClass().getResourceAsStream("/dictionary.txt");
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      
          return reader.lines()
              .filter(predicate)
              .collect(toSet());
    }
    
  }

  public Integer getWordLength() {
    return wordLength;
  }

  public String getAllowedCharacters() {
    return allowedCharacters;
  }

  public Set<String> getDictionary() {
    return dictionary;
  } 
}
