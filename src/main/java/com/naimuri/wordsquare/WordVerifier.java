package com.naimuri.wordsquare;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.repeat;

public class WordVerifier {
  
public Boolean verify(String word, String availableCharacters, Integer wordIndex) {
    
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
