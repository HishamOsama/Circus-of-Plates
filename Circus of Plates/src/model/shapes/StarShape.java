package model.shapes;

import java.io.File;

import model.shapes.interfaces.Shape;
import model.shapes.util.PlatesFactory;

public class StarShape implements Shape {

    static {
        PlatesFactory.getInstance().registerShape("Star", StarShape.class);
    }
    private File image;

    @Override
    public int getNumberOfPoints() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double[] getLengths() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double[] getAngles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public File getImage() {
        // TODO Auto-generated method stub
        return image;
    }

}
