package model.shapes;

import java.awt.Color;
import java.io.File;

import model.shapes.util.ImageConstants;
import model.shapes.util.PlatesFactory;

public class YellowStar extends StarShape {

	static {
		PlatesFactory.getInstance().registerShape("YellowStar", YellowStar.class);
		System.out.println("Static Initializer Executed");
	}

	public YellowStar() {
		super();
		shapeColor = Color.YELLOW;
		for (final String extension : ImageConstants.RESERVED_IMAGE_EXTENSIONS) {
			try {
				imagePath = mainPath + File.separator + this.getClass().getSimpleName() + extension;
				loadImage();
				break;
			} catch (final Exception e) {
				continue;
			}
		}
	}

}
