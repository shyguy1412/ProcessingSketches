package net.shy;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.shy.simplegui.SButton;
import net.shy.simplegui.events.SClickListener;
import net.shy.simplegui.events.SClickable;
import processing.core.PApplet;

public class SketchCollection implements SClickListener, Iterable<Entry<Class<?>, Sketch>> {
  HashMap<Class<?>, Sketch> sketches = new HashMap<>();
  Class<?> selected = null;

  private static Sketch instanceSketch(Class<?> sketchClass) {
    Sketch sketch;
    try {
      sketch = (Sketch) sketchClass.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      return null;
    }
    Sketch.runSketch(new String[] { sketchClass.getName() }, sketch);
    sketch.getSurface().pauseThread();
    return sketch;
  }

  public Sketch add(Class<?> sketchClass) {
    if (sketches.containsKey(sketchClass))
      return sketches.get(sketchClass);
    Sketch sketch = instanceSketch(sketchClass);
    sketches.put(sketchClass, sketch);
    if (selected != null)
      return sketch;
    selected = sketchClass;
    sketch.getSurface().resumeThread();
    return sketch;
  }

  @Override
  public void onClick(SClickable arg0) {
    String label = ((SButton) arg0).getLabel();
    Class<?> targetClass = null;
    for (Class<?> sketchClass : sketches.keySet()) {
      if (sketchClass.getSimpleName() == label) {
        targetClass = sketchClass;
        break;
      }
    }
    if (targetClass == null)
      return;

    getSelectedSketch().getSurface().pauseThread();
    selected = targetClass;
    getSelectedSketch().getSurface().resumeThread();
    PApplet.println(targetClass);
  }

  @Override
  public Iterator<Entry<Class<?>, Sketch>> iterator() {
    return this.sketches.entrySet().iterator();
  }

  public Sketch getSelectedSketch() {
    if (selected == null)
      return null;
    return sketches.get(selected);
  }

}
