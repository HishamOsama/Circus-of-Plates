package model.shapes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.Timer;

import controller.PlateFetching;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.players.PlayerIF;
import model.shapes.interfaces.Shape;

public class ShapesMovements extends ImageView implements Runnable {

    private Shape shape;
    private static final int delta = 2;
    private ImageView image;
    private Pane fx;
    private PlateFetching player1, player2;

    public ShapesMovements(Pane fx, PlayerIF player1, PlayerIF player2) {
        this.fx = fx;
        this.player1 = new PlateFetching(player1);
        this.player2 = new PlateFetching(player2);
    }

    public Color getColor() {
        return shape.getColor();
    }

    public void start(String name) {

        Thread mainPlateThread = new Thread("Main Plate Thread") {
            private int counter = 0;
            private PlatesPool platesPool = new PlatesPool();

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
                            boolean moving = true;
                            final Timer timer = new Timer(50, new ActionListener() {

                                private boolean isMoving = moving;

                                @Override
                                public void actionPerformed(final ActionEvent e) {

                                    if (isMoving) {
                                        image.setY(image.getY() + delta);
                          
                                        if (!player1.CheckMe(3*image.getX()/2.0, 2.0*image.getY())) {
                                            System.out.println("Yes1");
                                            isMoving = false;

                                        } else if (!player2.CheckMe(3*image.getX()/2.0, 2.0*image.getY())) {
                                            System.out.println("Yes2");
                                            isMoving = false;
                                        }
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
