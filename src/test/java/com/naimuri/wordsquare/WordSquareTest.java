package com.naimuri.wordsquare;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@SuppressWarnings("unchecked")
public class WordSquareTest {
  
  private WordSquare wordSquare;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setup() {
    wordSquare = new WordSquare();
  }

  @Test  
  public void test_4_x_4_grid() {

    List<AcceptedWord> result = wordSquare.generate("4 oonnnmmeeeedccaa");
     
    assertThat(result ,contains(
        hasProperty("word",equalTo("moan")),
        hasProperty("word",equalTo("once")),
        hasProperty("word",equalTo("acme")),
        hasProperty("word",equalTo("need"))
    ));
  }

  @Test  
  public void test_5_x_5_grid() {
	  
	  List<AcceptedWord> result = wordSquare.generate("5 aaaeeeefhhmoonssrrrrttttw");

    assertThat(result ,contains(
        hasProperty("word",equalTo("feast")),
        hasProperty("word",equalTo("earth")),
        hasProperty("word",equalTo("armor")),
        hasProperty("word", equalTo("stone")),
        hasProperty("word",equalTo("threw"))
    )); 
  }
	
  @Test  
  public void test_5_x_5_grid2() {

    List<AcceptedWord> result = wordSquare.generate("5 aabbeeeeeeeehmosrrrruttvv");
	  
    assertThat(result ,contains(
        hasProperty("word",equalTo("heart")),
        hasProperty("word",equalTo("ember")),
        hasProperty("word",equalTo("above")),
        hasProperty("word",equalTo("revue")),
        hasProperty("word",equalTo("trees"))
    ));
  }

  @Test  
  public void test_7_x_7_grid() {

    List<AcceptedWord> result = wordSquare.generate("7 aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy");

    assertThat(result ,contains(
        hasProperty("word",equalTo("bravado")),
        hasProperty("word",equalTo("renamed")),
        hasProperty("word",equalTo("analogy")),
        hasProperty("word",equalTo("valuers")),
        hasProperty("word",equalTo("amoebas")),
        hasProperty("word",equalTo("degrade")),
        hasProperty("word",equalTo("odyssey"))
    ));
  }
}
