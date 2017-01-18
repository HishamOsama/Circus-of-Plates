package model.shapes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.shapes.interfaces.Shape;

public class ShapesMovements extends ImageView implements Runnable {

    private Shape shape;
    private static final int delta = 2;
    private ImageView image;
    private Pane fx;

    public ShapesMovements(Pane fx) {
        this.fx = fx;
    }

    public Color getColor() {
        return shape.getColor();
    }

    public void start(String name) {

        Thread mainPlateThread = new Thread("Main Plate Thread") {
            private int counter = 0;
            PlatesPool platesPool = new PlatesPool();

            @Override
            public void run() {
                while (true) {
                    Thread thread = new Thread("Plate" + counter) {
                        @Override
                        public void run() {
                            Shape shape = platesPool.getPlate();
                            ImageView image = convertImage(shape.getImage());
                            image.setFitHeight(50);
                            image.setFitWidth(50);
                            image.setX(50 + counter * 50);
                            image.setY(-50);
                            fx.getChildren().add(image);
                            final Timer timer = new Timer(1000, new ActionListener() {
                                @Override
                                public void actionPerformed(final ActionEvent e) {
                                    for (int i = 0; i < 30; i++) {
                                        image.setY(image.getY() + delta);
                                    }

                                }
                            });

                            timer.start();
                        }
                    };
                    thread.setDaemon(true);
                    Platform.runLater(thread);
                    counter = (counter + 1) % 1000;
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // break;
                }
            }
        };
        mainPlateThread.setDaemon(true);
        mainPlateThread.start();
    }

    public ImageView getImageView() {
        return image;
    }

    public void move() {
        image.setY(image.getY() + delta);
    }

    private ImageView convertImage(final BufferedImage image) {
        final Image imageF = SwingFXUtils.toFXImage(image, null);

        final ImageView dispaly = new ImageView(imageF);

        return dispaly;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}
