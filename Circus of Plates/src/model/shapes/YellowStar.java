package model.shapes;

import java.awt.Color;
import java.io.File;

import model.shapes.util.PlatesFactory;

public class YellowStar extends StarShape {

	static {
        PlatesFactory.getInstance().registerShape("YellowStar", YellowStar.class);
        System.out.println("Static Initializer Executed");
    }

	public YellowStar() {
		imagePath = mainPath + File.separator +
				this.getClass().getSimpleName();
		shapeColor = Color.YELLOW;
	}

}
