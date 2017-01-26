package model.shapes.dynamicloading;

import java.io.File;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.net.URLClassLoader;
import java.net.URL;
import java.util.Enumeration;
import java.lang.ClassLoader;

public class Tests extends ClassLoader {

    public static void main(String[] args) throws Exception {

        File file = new File(System.getProperty("user.dir") + File.separator + "HelloWorld.jar");

        URLClassLoader clazzLoader = URLClassLoader.newInstance(new URL[] {file.toURI().toURL()});

        JarFile jarFile = new JarFile(file);
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry element = entries.nextElement();
            if (element.getName().endsWith(".class")) {
                try {
                    Class c = clazzLoader.loadClass(element.getName().replaceAll(".class", "").replaceAll("/", "."));
                    c.newInstance(); // this proves that class is loaded
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Class cls = Class.forName("HelloWorld", true, clazzLoader);
        cls.newInstance();
        Tests p = new Tests();
        p.checkIfLoaded();

    }

    public void checkIfLoaded() {
        System.out.println("coming in");
        if (findLoadedClass("HelloWorld") != null) {
            System.out.println("Yepee, HelloWorld class is loaded !");
        }
    }

}