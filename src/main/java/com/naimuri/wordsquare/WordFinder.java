package com.naimuri.wordsquare;

import static java.util.regex.Pattern.compile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;

public class WordFinder {
  
  private final Set<String> dictionary;
  private final WordVerifier verifier;

  public WordFinder(Set<String> dictionary) {

    this.dictionary = dictionary;
    this.verifier = new WordVerifier();
  }

  public String findWord(String regex, Map<Integer, List<String>> checkedWords, String availableCharacters, Integer wordIndex) throws NoSuchElementException {
    
    Predicate<String> predicate = compile(regex).asPredicate();
    
    if (!checkedWords.containsKey(wordIndex)) {
      checkedWords.put(wordIndex, new ArrayList<String>());
    }
    
    String match = dictionary.stream()
                             .filter(predicate)
                             .filter(word -> !checkedWords.get(wordIndex).contains(word))
                             .filter(word -> verifier.verify(word, availableCharacters, wordIndex))
                             .findFirst()
                             .get();
    
    checkedWords.get(wordIndex).add(match);
        
    return match;
  }

}
