package com.naimuri.wordsquare;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.util.List;

public class RegexBuilder {
  
  private final Integer wordLength;
    
  public RegexBuilder(Integer wordLength) {
    super();
    this.wordLength = wordLength;
  }

  public String buildSearchRegex(String availableCharacters, List<AcceptedWord> acceptedWords, Integer wordIndex) {

    return format(
        "^%s[%s]{%d}$",
        acceptedWords.stream()
                     .map(e -> e.getWord().substring(wordIndex, wordIndex +1))
                     .collect(joining()),
        availableCharacters, 
        wordLength - wordIndex
    );
  }
}
