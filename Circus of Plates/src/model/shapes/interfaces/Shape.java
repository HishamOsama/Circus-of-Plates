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
	protected boolean moving;

	public Shape() {
		final File file = new File(System.getProperty("user.dir")  + File.separator);
		final String[] directories = file.list();
		for (final String directory : directories) {
			if (directory.equals("ShapeImages")) {
				mainPath = file.getAbsolutePath() + File.separator + directory;
			}
		}
		moving = true;
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