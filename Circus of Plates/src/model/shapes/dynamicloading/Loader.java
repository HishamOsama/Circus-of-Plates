package model.shapes.dynamicloading;

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

	private final ArrayList<Constructor<Shape>> constructors;

	public Loader() {
		constructors = new ArrayList<>();
	}

	private String path;

	// loading any jar that includes Shape
	public ArrayList<Constructor<Shape>> invokeClassMethod() {
		try {
			final File directory = new File(path);
			final String[] directories = directory.list();
			for (final String jarName : directories) {
				final String jarPath = path + jarName;
				final JarFile jarFile = new JarFile(jarPath);
				final Enumeration<JarEntry> e = jarFile.entries();
				final URL[] urls = { new URL("jar:file:" + path + "!/") };
				final URLClassLoader cl = URLClassLoader.newInstance(urls);
				while (e.hasMoreElements()) {
					final JarEntry je = e.nextElement();
					if (je.isDirectory() || !je.getName().endsWith(".class")) {
						continue;
					}
					// -6 because of .class
					String className = je.getName().substring(0, je.getName().length() - 6);
					// System.out.println(className);
					className = className.replace('/', '.');
					// System.out.println(className);
					@SuppressWarnings("unchecked")
					final
					Class<Shape> c = (Class<Shape>) cl.loadClass(className);
					className = className.substring(className.lastIndexOf('.') + 1, className.length());
					final Constructor<Shape> constructor = c.getConstructor();
					constructors.add(constructor);
				}
				jarFile.close();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return constructors;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}
