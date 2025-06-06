package net.shy.sketches.flowfield;

import net.shy.Sketch;
import processing.core.PGraphics;
import processing.core.PVector;

public class PerlinNoiseFlowField extends Sketch {
  FlowField flowfield;
  Particle[] particle;
  PGraphics g;

  int grid = 10;

  @Override
  public void settings() {
    size(1600, 800);
  }

  @Override
  public void setup() {
    // fullScreen();
    particle = new Particle[45000];
    for (int i = 0; i < particle.length; i++) {
      PVector startPos = new PVector(random(width), random(height));
      particle[i] = new Particle(this, startPos.x, startPos.y);
    }
    flowfield = new FlowField(this, floor(width / grid) + 1, floor(height / grid) + 1, grid);
    // flowfield.show();
    background(0);
    colorMode(HSB, 100);
    g = createGraphics(width, height);
    frameRate(31);
  }
  
  @Override
  public void draw() {
    g.beginDraw();
    g.colorMode(HSB, 100);
    g.background(0, 5);
    flowfield.update();
    // flowfield.render();

    for (Particle p : particle) {
      p.follow(flowfield);
      p.update();
      p.render(g);
    }
    g.endDraw();
    image(g, 0, 0);
  }

  // void mousePressed() {
  // if (mouseButton == RIGHT) {
  // PImage wallpaper = createImage(width, height, RGB);
  // wallpaper = get();
  // wallpaper.save("wallpaper.jpg");
  // exit();
  // } else if (mouseButton == LEFT) {
  // changeOverTime = 0;
  // }
  // }

}
