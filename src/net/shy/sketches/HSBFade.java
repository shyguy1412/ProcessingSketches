package net.shy.sketches;

import net.shy.Sketch;

public class HSBFade extends Sketch {
  @Override
  public void settings() {
    size(500, 500);
  }

  @Override
  public void setup() {
    colorMode(HSB, 360, 100, 100);
  }

  @Override
  public void draw() {
    background((frameCount*10)%360, 100, 100);
  }

}