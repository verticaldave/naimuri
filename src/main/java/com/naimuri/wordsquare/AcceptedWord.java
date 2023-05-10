package com.naimuri.wordsquare;

public class AcceptedWord {
  
  private final String availableCharacters;
  private final String word;
  
  public AcceptedWord(String availableCharacters, String word) {

    this.availableCharacters = availableCharacters;
    this.word = word;
  }

  public String getAvailableCharacters() {
    return availableCharacters;
  }

  public String getWord() {
    return word;
  }

}
