package model.shapes;

import java.io.File;

import model.shapes.interfaces.Shape;

public abstract class StarShape extends Shape {

    public StarShape() {
    	// image path
    	super();
	}
    @Override
    public File getImage() {
        return image;
    }

}
