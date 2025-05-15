package net.shy;

import net.shy.reflection.SketchLoader;
import processing.core.PApplet;

public class SketchBrowser extends PApplet {
    SketchCollection sketches = new SketchCollection();

    public static void main(String[] args) {
        PApplet.main(SketchBrowser.class.getName());
    }

    @Override
    public void settings() {
        System.out.println(SketchLoader.loadSketches());
    }

    @Override
    public void setup() {
        
    }

    @Override
    public void draw() {
        // PImage sketchBuffer = childSketches[currentSketch].get();
        // image(sketchBuffer, 0, 0);
    }

    @Override
    public void mouseClicked() {
        // toggleSketch();
    }
}
