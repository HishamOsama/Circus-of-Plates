package model.shapes;

import java.awt.Color;
import java.io.File;

import model.shapes.util.PlatesFactory;

public class RedStar extends StarShape {

	static {
        PlatesFactory.getInstance().registerShape("RedStar", RedStar.class);
        System.out.println("Static Initializer Executed");
    }

	public RedStar() {
		imagePath = mainPath + File.separator +
				this.getClass().getSimpleName();
		shapeColor = Color.RED;
	}

}
