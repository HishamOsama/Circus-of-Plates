package model.shapes;

import java.awt.image.BufferedImage;
import java.util.Random;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.paint.Color;
import model.shapes.interfaces.Shape;

public class ShapesMovements extends ImageView {

    private Shape shape;
    private static final int delta = 5;
    private boolean move;
    private int delayTime;

    public ShapesMovements(Shape shape) {
        super(convertImage(shape.getImage()));
        super.setFitHeight(50);
        super.setFitWidth(50);
        super.setX(new Random().nextInt(1000));
        super.setY(-50);
        this.shape = shape;
        move = false;
        delayTime = shape.getDelayTime();

    }

    public Color getColor() {
        return shape.getColor();
    }

    public void move() {
        if (move){
            
            super.setY(super.getY() + delta);
        }
    }

    public void decreaseDelay() {
        if (delayTime <= 0) {
            move = true;
        }
        delayTime--;
    }

    private static Image convertImage(final BufferedImage image) {
        final Image imageF = SwingFXUtils.toFXImage(image, null);
        return imageF;
    }

}
