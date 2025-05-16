package net.shy.sketches.flowfield;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

class Particle {
  PVector vel;
  PVector acc;
  PVector pos;
  PVector prevPos;
  PApplet app;
  float h = 0;

  Particle(PApplet app, float x, float y) {
    acc = new PVector(0, 0);
    vel = new PVector(0, 0);
    pos = new PVector(x, y);
    prevPos = pos.copy();
    this.app = app;
  }

  void follow(FlowField field) {
    PVector v = field.getVectorAt(pos.x, pos.y);
    acc.add(v.setHeading(v.heading() + ((float) field.randomnessSlider.getValue())));
  }

  void update() {
    vel.add(acc);
    vel.limit(3);
    pos.add(vel);
    float checkX = pos.x;
    float checkY = pos.y;
    pos.x = (pos.x > app.width ? 0 : (pos.x < 0 ? app.width : pos.x));
    pos.y = (pos.y > app.height ? 0 : (pos.y < 0 ? app.height : pos.y));
    if (pos.x != checkX || pos.y != checkY)
      prevPos = pos.copy();
    acc.mult(0);
  }

  void render(PGraphics g) {
    g.stroke(h, 100, 100);
    g.strokeWeight(1);
    g.line(prevPos.x, prevPos.y, pos.x, pos.y);
    prevPos = pos.copy();
    h = (h < 100 ? h + 1 : 0);
  }
}
