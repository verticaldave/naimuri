package com.naimuri.wordsquare;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WordSquareConfigTest {

  private static final String SORTED_CHARACTERS = "aaaeeeefhhmnoorrrrssttttw";
  private static final Integer WORD_LENGTH = 5;
  private static final String VALID_CONFIG = WORD_LENGTH + " " + SORTED_CHARACTERS;
  
	@Rule
 	public ExpectedException expectedException = ExpectedException.none();
  
	@Test  
	public void test_dictionary_entries() throws IOException, URISyntaxException {

    WordSquareConfig config = new WordSquareConfig("7 aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy");
	 
    assertThat(config.getDictionary(), hasItems(
            "ardours",
            "duellos",
            "drubber",
            "alumnae",
            "bubbled"
    ));
  }
  
  @Test
  public void test_validate_valid_init_string() {
    
    WordSquareConfig config = new WordSquareConfig(VALID_CONFIG);
    
    assertThat(config.getWordLength(), equalTo(WORD_LENGTH));
    assertThat(config.getAllowedCharacters(), equalTo(SORTED_CHARACTERS));
  }
  
  @Test
  public void test_validate_sorted_init_string() {
    
    WordSquareConfig config = new WordSquareConfig("5 tweearrreefhahaonssmorttt");
    
    assertThat(config.getWordLength(), equalTo(WORD_LENGTH));
    assertThat(config.getAllowedCharacters(), equalTo(SORTED_CHARACTERS));
  }
  
	@Test
 	public void test_validate_malformed_init_string() {
 		
	  expectedException.expect(IllegalArgumentException.class);
 		expectedException.expectMessage(WordSquareConfig.INVALID_INPUT_UNPARSEABLE);
 		new WordSquareConfig(VALID_CONFIG.replace(" ", ""));
 	}
	
	@Test
  public void test_validate_invalid_allowed_characters_init_string() {
    
	  expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage(WordSquareConfig.INVALID_INPUT_CHARACTERS);
    new WordSquareConfig(VALID_CONFIG.replace("e", "6"));
  }

}
