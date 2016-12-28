package model.shapes.interfaces;

import java.awt.Color;
import java.io.File;

public abstract class Shape {

	protected String imagePath;
	protected File image;
	protected String mainPath = "";
	protected Color shapeColor;
	public Shape() {
		final File file = new File("/path/to/directory");
		final String[] directories = file.list();
		for (final String directory : directories) {
			if (directory.equals("ShapeImages")) {
				mainPath = file.getAbsolutePath() + directory;
			}
		}
	}
	abstract public File getImage();

	public Color getColor() {
		return shapeColor;
	}
}