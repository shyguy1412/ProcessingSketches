package net.shy.sketches.flowfield;

import processing.core.PApplet;
import processing.core.PVector;

class FlowField {
  PVector[] vector;
  PApplet app;
  int w, h;
  float zoff = 0;

  float stepsize = 0.1f;
  float changeOverTime = 0.01f;
  int grid;

  FlowField(PApplet app, int _w, int _h, int grid) {
    w = _w;
    h = _h;
    vector = new PVector[w * h];
    this.app = app;
    this.grid = grid;
  }

  void update() {
    float xoff = 0;
    for (int i = 0; i < w; i++) {
      float yoff = 0;
      for (int j = 0; j < h; j++) {
        int index = i + j * w;
        vector[index] = PVector.fromAngle(app.noise(xoff, yoff, zoff) * PApplet.TWO_PI * 2);
        vector[index].setMag(1);
        yoff += stepsize;
      }
      xoff += stepsize;
    }
    zoff += changeOverTime;
  }

  void render() {
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        int index = i + j * w;
        app.fill(255);
        app.pushMatrix();
        app.translate(i * grid, j * grid);
        app.rotate(vector[index].heading());
        app.line(0, 0, grid, 0);
        app.popMatrix();
      }
    }
  }

  PVector getVectorAt(float x, float y) {
    x = PApplet.floor(x / grid);
    y = PApplet.floor(y / grid);
    int index = PApplet.floor(x + y * w);
    return vector[index];
  }
}
