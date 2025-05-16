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
    SScrollPanel sketchList;
    SCanvas sketchCanvas;

    public static void main(String[] args) {
        PApplet.main(SketchBrowser.class.getName());
    }

    @Override
    public void settings() {
        size(1000, 800);
        Set<Class<?>> sketches = SketchLoader.loadSketches();
        for (Class<?> sketch : sketches) {
            this.sketches.add(sketch);
        }
    }

    int sketchListWidth() {
        return 200;
    }

    @Override
    public void setup() {
        windowResizable(true);

        Sketch selectedSketch = sketches.getSelectedSketch();
        sketchList = new SScrollPanel(
                this,
                0,
                0,
                sketchListWidth(),
                height,
                sketchListWidth(),
                height);

        sketchList.setBorderWidth(0);
        sketchList.setBackgroundColor(28);

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
            button.setBackgroundColor(40);
            button.setLabelColor(255);
            button.setBorderColor(255);
            button.setHoverBackgroundColor(70);
            button.setHoverBorderColor(255);
            button.setHoverLabelColor(255);
            button.addClickListener(sketches);
            if (sketchClass.getSimpleName() == "MouseTest")
                button.dispatchClickEvent();
            sketchList.addComponent(button);
            index++;
        }

        sketchViewport = new SScrollPanel(this, sketchListWidth(), 0, width - sketchListWidth(),
                height, selectedSketch.width,
                selectedSketch.height);
        sketchCanvas = new SCanvas(this, sketchViewport.getWidth() / 2 - selectedSketch.width / 2,
                sketchViewport.getHeight() / 2 - selectedSketch.height / 2, selectedSketch.width,
                selectedSketch.height);
        sketchViewport.addComponent(sketchCanvas);

        sketchViewport.setBackgroundColor(40);
        sketchViewport.setBorderWidth(0);

    }

    @Override
    public void draw() {
        background(0);
        Sketch currentSketch = sketches.getSelectedSketch();
        sketchCanvas.g.beginDraw();
        sketchCanvas.g.image(currentSketch.get(), 0, 0);
        sketchCanvas.g.endDraw();
        
        sketchList.draw();
        sketchViewport.draw();

        fill(255);
        noStroke();
        textSize(50);
        text(nf(frameRate, 0, 2), width - 150, 50);
    }

    @Override
    public void mouseClicked() {
        // toggleSketch();
    }

    @Override
    public void windowResized() {
        Sketch selectedSketch = sketches.getSelectedSketch();

        sketchList.setWidth(sketchListWidth());
        sketchList.setActualWidth(sketchListWidth());
        sketchList.setHeight(height);

        sketchViewport.pos.x = sketchListWidth();
        sketchViewport.setWidth(width - sketchListWidth());
        sketchViewport.setHeight(height);
        sketchViewport.setActualWidth(selectedSketch.width);
        sketchViewport.setActualHeight(selectedSketch.height);

        sketchCanvas.pos.x = sketchViewport.getWidth() / 2 - selectedSketch.width / 2;
        sketchCanvas.pos.y = sketchViewport.getHeight() / 2 - selectedSketch.height / 2;
    }

    @Override
    public void postEvent(Event pe) {
        super.postEvent(pe);
        boolean isMouseEvent = MouseEvent.class.isAssignableFrom(pe.getClass());
        if (isMouseEvent) {
            MouseEvent me = (MouseEvent) pe;
            PVector localizedMouse = sketchCanvas.globalToLocal(me.getX(), me.getY());
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
