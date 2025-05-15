package net.shy.sketches;

import net.shy.Sketch;

public class HelloWorld extends Sketch {
  @Override
  public void settings() {
    size(500, 500);
  }

  @Override
  public void setup() {
  }

  @Override
  public void draw() {
    background(0);
    stroke(255);
    text("Hello World", width / 2, height / 2);
  }

}