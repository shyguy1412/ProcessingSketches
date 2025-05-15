package net.shy;

import java.util.Set;
import java.util.Map.Entry;

import net.shy.reflection.SketchLoader;
import net.shy.simplegui.SButton;
import net.shy.simplegui.SCanvas;
import net.shy.simplegui.SScrollPanel;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.Event;
import processing.event.MouseEvent;

public class SketchBrowser extends PApplet {
    SketchCollection sketches = new SketchCollection();
    SScrollPanel sketchViewport;
    SCanvas sketchCanvas;

    public static void main(String[] args) {
        PApplet.main(SketchBrowser.class.getName());
    }

    @Override
    public void settings() {
        // size(300, 300);
        size(1000, 800);
        Set<Class<?>> sketches = SketchLoader.loadSketches();
        for (Class<?> sketch : sketches) {
            this.sketches.add(sketch);
        }
    }

    @Override
    public void setup() {
        // windowResizable(true);

        Sketch selectedSketch = sketches.getSelectedSketch();
        SScrollPanel sketchList = new SScrollPanel(
                this,
                0,
                0,
                width / 3,
                height,
                width / 3,
                1000);

        int index = 0;
        for (Entry<Class<?>, Sketch> sketchEntry : sketches) {
            Class<?> sketchClass = sketchEntry.getKey();
            int w = 100;
            int h = 50;
            float x = sketchList.getWidth() / 2 - w / 2;
            int margin = 10;
            float y = margin + index * (h + margin);
            SButton button = new SButton(this, x, y, w, h);
            button.setLabel(sketchClass.getSimpleName());
            button.addClickListener(sketches);
            if (sketchClass.getSimpleName() == "MouseTest")
                button.dispatchClickEvent();
            sketchList.addComponent(button);
            index++;
        }

        sketchViewport = new SScrollPanel(this, width / 3, 0, width - width / 3,
                height, selectedSketch.width,
                selectedSketch.height);
        sketchCanvas = new SCanvas(this, 0, 0, selectedSketch.width,
                selectedSketch.height);
        sketchViewport.addComponent(sketchCanvas);
    }

    @Override
    public void draw() {
        Sketch currentSketch = sketches.getSelectedSketch();
        sketchCanvas.g.beginDraw();
        sketchCanvas.g.image(currentSketch.get(), 0, 0);
        sketchCanvas.g.endDraw();
        background(0);
    }

    @Override
    public void mouseClicked() {
        // toggleSketch();
    }

    @Override
    public void windowResized() {
        println(width, height);
    }

    @Override
    public void postEvent(Event pe) {
        super.postEvent(pe);
        boolean isMouseEvent = MouseEvent.class.isAssignableFrom(pe.getClass());
        if (isMouseEvent) {
            MouseEvent me = (MouseEvent) pe;
            PVector localizedMouse = sketchViewport.globalToLocal(me.getX(), me.getY()).sub(sketchViewport.pos);
            pe = new MouseEvent(
                    me.getNative(),
                    me.getMillis(),
                    me.getAction(),
                    me.getModifiers(),
                    floor(localizedMouse.x),
                    floor(localizedMouse.y),
                    me.getButton(),
                    me.getCount());
        }
        sketches.getSelectedSketch().postEvent(pe);
    }
}
