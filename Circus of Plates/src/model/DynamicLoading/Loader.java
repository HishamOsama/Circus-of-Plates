package model.DynamicLoading;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import model.shapes.interfaces.Shape;

public class Loader {

    private ArrayList<Constructor<Shape>> constructors;

    public Loader() {
        constructors = new ArrayList<>();
    }

    private String path;

    // loading any jar that includes Shape
    public ArrayList<Constructor<Shape>> invokeClassMethod() {
        try {
            JarFile jarFile = new JarFile(path);
            Enumeration<JarEntry> e = jarFile.entries();
            URL[] urls = {new URL("jar:file:" + path + "!/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                // -6 because of .class
                String className = je.getName().substring(0, je.getName().length() - 6);
                // System.out.println(className);
                className = className.replace('/', '.');
                // System.out.println(className);
                @SuppressWarnings("unchecked")
                Class<Shape> c = (Class<Shape>) cl.loadClass(className);
                className = className.substring(className.lastIndexOf('.') + 1, className.length());
                Constructor<Shape> constructor = c.getConstructor();
                constructors.add(constructor);
            }
            jarFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return constructors;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
