package net.shy.sketches;

import net.shy.Sketch;
import net.shy.simplegui.SButton;
import net.shy.simplegui.events.SClickListener;
import net.shy.simplegui.events.SClickable;

public class MouseTest extends Sketch implements SClickListener {
  SButton btn;

  @Override
  public void settings() {
    size(500, 500);
  }

  @Override
  public void setup() {
    btn = new SButton(this, width / 2 - 50, height / 2 - 25, 100, 50);
    btn.setLabel("CLICK ME");
    btn.addClickListener(this);
  }

  @Override
  public void draw() {
    btn.draw();
  }

  @Override
  public void mouseMoved() {
    // println(mouseX, mouseY);
  }

  @Override
  public void onClick(SClickable source) {
    println("CLICKED");
  }

}
