package com.naimuri.wordsquare;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class WordSquare {
		
	public List<AcceptedWord> generate(String initString) {
	  
	  WordSquareConfig config = WordSquareConfig.init(initString);

    System.out.println(
        format("Configured a %1$d x %1$d word square with allowed characters of %2$s",
               config.getWordLength(), 
               config.getAllowedCharacters() 
        )
    );
	  
    WordFinder finder = new WordFinder(config.getDictionary());
    RegexBuilder regexBuilder = new RegexBuilder(config.getWordLength());
    UsedCharacterHandler disposal = new UsedCharacterHandler();

    String availableCharacters = config.getAllowedCharacters();
	  List<AcceptedWord> acceptedWords = new ArrayList<>();
	  Map<Integer, List<String>> checkedWords = new HashMap<>();
	  
	  Integer wordIndex = 0;
	  	  	      
    while (wordIndex < config.getWordLength()) {
      try {

        String regex = regexBuilder.buildSearchRegex(availableCharacters, acceptedWords, wordIndex);
        String foundWord = finder.findWord(regex, checkedWords, availableCharacters, wordIndex);

        acceptedWords.add(new AcceptedWord(availableCharacters, foundWord));
        availableCharacters = disposal.disposeUsedCharacters(foundWord, availableCharacters, wordIndex);

        wordIndex++;

      } catch (NoSuchElementException e) {
        if (wordIndex == 0) {
          System.out.println("Cannot solve. Exiting");
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
}
