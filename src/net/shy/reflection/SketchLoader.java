package net.shy.reflection;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.shy.Sketch;

public class SketchLoader {

  public static Set<Class<?>> loadSketches() {
    URL classURL = SketchLoader.class.getResource(SketchLoader.class.getSimpleName() + ".class");
    boolean isJar = classURL.getProtocol() == "jar";
    String basePath = classURL.getPath().replace(
        SketchLoader.class.getCanonicalName().replace(".", "/"), "")
        .replaceAll(".class$", "");

    List<Path> paths;
    try {
      if (isJar) {
        paths = SketchLoader.getPathsFromResourceJAR(".");
      } else {
        paths = SketchLoader.getAllPathsFromResource(".");
      }
    } catch (URISyntaxException | IOException e) {
      return null;
    }

    return paths.stream()
        .map(line -> line.toString())
        .filter(line -> line.endsWith(".class"))
        .map(line -> line.replace(basePath, ""))
        .map(line -> line.replace("/", "."))
        .map(line -> line.replaceAll(".class$", ""))
        .map(line -> getClass(line))
        .filter(c -> Sketch.class.isAssignableFrom(c) && c != Sketch.class)
        .collect(Collectors.toSet());
  }

  private static Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
    }
    return null;
  }

  private static List<Path> getAllPathsFromResource(String folder)
      throws URISyntaxException, IOException {

    ClassLoader classLoader = SketchLoader.class.getClassLoader();

    URL resource = classLoader.getResource(folder);

    // dun walk the root path, we will walk all the classes
    List<Path> collect = Files.walk(Paths.get(resource.toURI()))
        .filter(Files::isRegularFile)
        .collect(Collectors.toList());

    return collect;
  }

  // Get all paths from a folder that inside the JAR file
  private static List<Path> getPathsFromResourceJAR(String folder)
      throws URISyntaxException, IOException {

    List<Path> result;

    // get path of the current running JAR
    String jarPath = SketchLoader.class.getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .toURI()
        .getPath();

    // file walks JAR
    URI uri = URI.create("jar:file:" + jarPath);
    try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
      result = Files.walk(fs.getPath(folder))
          .filter(Files::isRegularFile)
          .collect(Collectors.toList());
    }

    return result;
  }
}