package com.naimuri.wordsquare;

public class Application {

  public static void main(String[] args) {
    new WordSquare().generate(String.format("%s %s", args[0], args[1]));
  }

}
