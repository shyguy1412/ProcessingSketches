package net.shy;

import processing.core.PApplet;
import processing.core.PSurface;
import processing.core.PSurfaceNone;

public class Sketch extends PApplet {

  @Override
  protected PSurface initSurface() {
    this.g = this.createPrimaryGraphics();
    this.surface = new PSurfaceNone(this.g);
    this.surface.initOffscreen(this);
    return this.surface;
  }

}
