package model.shapes;

import java.io.File;

public interface Shape {

	public int getNumberOfPoints();

	public double[] getLengths();

	public double[] getAngles();

	public File getImage();

}