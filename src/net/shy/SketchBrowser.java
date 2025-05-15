package net.shy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

import net.shy.reflection.SketchLoader;
import processing.core.PApplet;
import processing.core.PImage;

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
