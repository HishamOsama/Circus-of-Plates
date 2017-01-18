package model.shapes;


import java.io.File;

import javafx.scene.paint.Color;
import model.shapes.util.ImageConstants;
import model.shapes.util.PlatesFactory;

public class RedStar extends StarShape {

	
	
	static {
        PlatesFactory.getInstance().registerShape("RedStar", RedStar.class);
        System.out.println("Static Initializer Executed");
    }

	public RedStar() {
		super();
		shapeColor = Color.RED;
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
