package net.shy;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PSurface;
import processing.core.PSurfaceNone;

public class Sketch extends PApplet {

  PImage buffer;

  public void post() {
    buffer = g.get();
  }

  public PImage getBuffer() {
    return buffer;
  }

  @Override
  protected PSurface initSurface() {
    this.g = this.createPrimaryGraphics();
    this.surface = new PSurfaceNone(this.g);
    this.surface.initOffscreen(this);
    registerMethod("post", this);
    return this.surface;
  }

}
