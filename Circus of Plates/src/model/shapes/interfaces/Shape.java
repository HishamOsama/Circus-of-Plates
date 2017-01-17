package model.shapes.interfaces;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Shape {

	protected String imagePath;
	protected File imageFile;
	protected BufferedImage image;
	protected String mainPath = "";
	protected Color shapeColor;

	public Shape() {
		final File file = new File("C:\\Users\\Hesham\\git\\circus-of-plates\\Circus of Plates\\");
		final String[] directories = file.list();
		for (final String directory : directories) {
			if (directory.equals("ShapeImages")) {
				mainPath = file.getAbsolutePath() + File.separator + directory;
			}
		}
	}

	public void loadImage() {
		imageFile = new File(imagePath);
		try {
			image = ImageIO.read(imageFile);
			System.out.println("Image Loaded Successfully");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public Color getColor() {
		return shapeColor;
	}

    public BufferedImage getImage() {
        return image;
    }
}