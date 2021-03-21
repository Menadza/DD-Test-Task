package com.kondratev.init.solution;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of the text unpacker. The input is a string of text, if the string contains
 * an expression in the format: "a[b]", where a is a number from 0 to 2147483647, b is the Latin
 * alphabet characters to unpack or a similar expression (or all together), the expression
 * is unpacked according to the following rule: a*b.
 * Example: input string: "My1[First2[Cu]mber]s0[isRotten]", output string: "MyFirstCuCumbers".
 *
 * @author Denis Kondratev 21.03.2021
 */
public class LineExtractor {

  /**
   * String for unpacker, initialized via the constructor. Valid characters are Latin alphabet,
   * numbers 0-9, " ["and "]".
   */
  private String line;

  /**
   * Checks the string for validity and constructs unpacker with received {@link #line line}.
   *
   * @param line String to unpack
   */
  public LineExtractor(String line) {
    validation(line);
    this.line = line;
  }

  /**
   * Method for starting text unpacking.
   *
   * @return returns the result-unpacked String.
   */
  public String transform() {
    unpack();
    return line;
  }

  /**
   * Using regular expressions, the method selects matches with regEx from the {@link #line line}, then
   * replaces the main string with a new one, with the matches replaced by the manufactured
   * substrings, after which a new match search is performed and, if necessary, replacements.
   */
  private void unpack() {
    Pattern pattern = Pattern.compile("(\\d+)\\[([a-zA-Z]+)]");
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      StringBuilder replacementPiece = new StringBuilder();
      for (int i = 0; i < Integer.parseInt(matcher.group(1)); i++) {
        replacementPiece.append(matcher.group(2));
      }
      line = line.replace(matcher.group(), replacementPiece);
      matcher.reset(line);
    }
  }

  /**
   * The method runs a number of methods to check the validity of the string accepted by the constructor:
   * 1. Correct bracket sequence
   * 2. Character validity
   * 3. The presence of numbers in the text group
   *
   * @return true if the string is valid and false if the string is invalid
   */
  private boolean validation(String line) {
    if (isCorrectSymbols(line) && isNotNumberInText(line) && isCorrectBracketSeq(line)) {
      System.out.println("String is valid!");
    }
    return true;
  }

  /**
   * Checking the validity of characters in the received string.
   *
   * @param line init-string for unpacking
   * @return true if the string is valid and false if the string is invalid
   */
  private boolean isCorrectSymbols(String line) {
    if (line.matches("[0-9A-Za-z\\[\\],]+")) {
      return true;
    } else {
      throw new IllegalArgumentException("Invalid characters in the string");
    }
  }

  /**
   * Checking the bracket sequence
   *
   * @param line init-string for unpacking
   * @return true if the string is valid and false if the string is invalid
   */
  private boolean isCorrectBracketSeq(String line) {
    int bracketBalance = 0;
    for (int i = 0; i < line.length(); i++) {
      if (bracketBalance < 0) {
        throw new IllegalArgumentException("The bracket sequence in the string is broken");
      }
      if (line.charAt(i) == '[') {
        bracketBalance++;
      } else if (line.charAt(i) == ']') {
        bracketBalance--;
      }
    }
    if (bracketBalance == 0) {
      return true;
    } else {
      throw new IllegalArgumentException("The bracket sequence in the string is broken");
    }
  }

  /**
   * Сhecking for numbers in a text group
   *
   * @param line init-string for unpacking
   * @return true if the string is valid and false if the string is invalid
   */
  private boolean isNotNumberInText(String line) {
    Pattern pattern = Pattern.compile("\\d+[a-zA-Z]|\\d+$");
    Matcher matcher = pattern.matcher(line);
    if (matcher.find()) {
      throw new IllegalArgumentException("The presence of numbers in the text block is not allowed");
    } else {
      return !matcher.find();
    }
  }
}