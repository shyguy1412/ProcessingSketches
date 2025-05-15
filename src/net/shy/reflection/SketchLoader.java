package net.shy.reflection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

public class SketchLoader {

  private static final String BASE_PACKAGE = SketchLoader.class
      .getPackageName()
      .substring(0, SketchLoader.class.getPackageName().lastIndexOf("."));
  private static final String SKETCH_PACKAGE = BASE_PACKAGE + ".sketches";

  public static Set<Class<?>> loadSketches() {
    InputStream stream = ClassLoader.getSystemClassLoader()
        .getResourceAsStream(SKETCH_PACKAGE.replaceAll("[.]", "/"));
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    return reader.lines()
        .filter(line -> line.endsWith(".class"))
        .map(line -> getClass(line))
        .collect(Collectors.toSet());
  }

  private static Class<?> getClass(String className) {
    try {
      return Class.forName(SKETCH_PACKAGE + "."
          + className.substring(0, className.lastIndexOf('.')));
    } catch (ClassNotFoundException e) {
      // handle the exception
    }
    return null;
  }
}