package com.naimuri.wordsquare;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.repeat;

public class UsedCharacterHandler {
  
  public String disposeUsedCharacters(String word, String availableCharacters, Integer wordIndex) {
    
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
}
