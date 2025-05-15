package net.shy;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class SketchCollection {
  HashMap<Class<?>, Sketch> sketches = new HashMap<>();

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
    return sketch;
  }

}
