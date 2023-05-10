package com.naimuri.wordsquare;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.repeat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;

public class WordSquare {
		
	public List<AcceptedWord> generate(String initString) {
	  
	  WordSquareConfig config = WordSquareConfig.init(initString);

    System.out.println(
        format("Configured a %1$d x %1$d word square with allowed characters of %2$s",
               config.getWordLength(), 
               config.getAllowedCharacters() 
        )
    );
	  
	  String availableCharacters = config.getAllowedCharacters();
	  List<AcceptedWord> acceptedWords = new ArrayList<>();
	  Map<Integer, List<String>> checkedWords = new HashMap<>();
	  Integer wordIndex = 0;
	  	  	      
    while (wordIndex < config.getWordLength()) {
      try {

        String regex = buildSearchRegex(availableCharacters, config.getWordLength(), acceptedWords, wordIndex);
        String foundWord = findWord(config.getDictionary(), regex, checkedWords, availableCharacters, wordIndex);

        acceptedWords.add(new AcceptedWord(availableCharacters, foundWord));
        availableCharacters = removeUsedCharactersFromAvailable(foundWord, availableCharacters, wordIndex);

        wordIndex++;

      } catch (NoSuchElementException e) {
        if (wordIndex == 0) {
          break;
        } else {
          availableCharacters = acceptedWords.remove(acceptedWords.size() - 1).getAvailableCharacters();
          checkedWords.get(wordIndex).clear();
          wordIndex--;
          continue;
        }
      }
    }
  	
  	acceptedWords.stream().forEach(e -> System.out.println(e.getWord()));
  	
  	return acceptedWords;
	}

  private String buildSearchRegex(String availableCharacters, Integer wordLength, List<AcceptedWord> acceptedWords, Integer wordIndex) {

    return format(
        "^%s[%s]{%d}$",
        acceptedWords.stream()
                     .map(e -> e.getWord().substring(wordIndex, wordIndex +1))
                     .collect(joining()),
        availableCharacters, 
        wordLength - wordIndex
    );
  }
	
	private String findWord(Set<String> dictionary, String regex, Map<Integer, List<String>> checkedWords, String availableCharacters, Integer wordIndex) throws NoSuchElementException {
	  
	  Predicate<String> predicate = compile(regex).asPredicate();
	  
	  if (!checkedWords.containsKey(wordIndex)) {
      checkedWords.put(wordIndex, new ArrayList<String>());
    }
	  
	  String match = dictionary.stream()
	                           .filter(predicate)
	                           .filter(word -> !checkedWords.get(wordIndex).contains(word))
	                           .filter(word -> verifyWordAgainstAvailableCharacters(word, availableCharacters, wordIndex))
	                           .findFirst()
	                           .get();
	  
	  checkedWords.get(wordIndex).add(match);
	  	  
	  return match;
	}
	
	private String removeUsedCharactersFromAvailable(String word, String availableCharacters, Integer wordIndex) {
	  
	  char[] charsToRemove = word.toCharArray();
	  
	  for (int i = wordIndex; i < charsToRemove.length; i++) { 
	    String match;
	    if (i == wordIndex) {
	      match = valueOf(charsToRemove[i]);
	    } else { 
	      match = repeat(valueOf(charsToRemove[i]), 2);
	    }
	    
	    availableCharacters = availableCharacters.replaceFirst(match, "");
	  }
	  	  
	  return availableCharacters;
	}
	
	private Boolean verifyWordAgainstAvailableCharacters(String word, String availableCharacters, Integer wordIndex) {
	  
	  char[] charsToValidate = word.toCharArray();
	  
	  for (int i = wordIndex; i < charsToValidate.length; i++) { 
      String match;
      if (i == wordIndex) {
        match = valueOf(charsToValidate[i]);
      } else { 
        match = repeat(valueOf(charsToValidate[i]), 2);
      }

      if (availableCharacters.contains(match)) {
        availableCharacters = availableCharacters.replaceFirst(match, "");
      } else {
        return false;
      }
    }   
	              
	  return true;
	}
	
}
