package com.kondratev.init.solution;

import java.io.*;

public class Main {
  public static void main(String[] args) {
    try (BufferedReader reader = new BufferedReader(new FileReader("inputText.txt"));
         BufferedWriter writer = new BufferedWriter(new FileWriter("outputText.txt"))) {
      String tempLine;
      LineExtractor lineExtractor;
      while ((tempLine = reader.readLine()) != null) {
        lineExtractor = new LineExtractor(tempLine);
        writer.write(lineExtractor.transform() + "\n");
      }
      System.out.println("All complete.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
