package model.shapes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import controller.PlateFetching;
import controller.PlateFetching.CheckResult;
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
    private Pane fx;
    private PlayerIF player1, player2;
    private int counter2=0;

    public ShapesMovements(Pane fx, PlayerIF player1, PlayerIF player2) {
        this.fx = fx;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Color getColor() {
        return shape.getColor();
    }

    public void start(String name) {
        Thread bigger = new Thread() {
            public void run() {
                Thread mainPlateThread = new Thread("Main Plate Thread") {
                    private int counter = 0;
                    private PlatesPool platesPool = new PlatesPool();
                    private int lastOrign = 0;
                    

                    @Override
                    public void run() {
                        while (true) {

                            Thread thread = new Thread("Plate" + counter) {

                                @Override
                                public void run() {
                                    Shape shape = platesPool.getPlate();

                                    lastOrign = 1000 - lastOrign;

                                    shape.setOrigin(lastOrign);

                                    ImageView image = convertImage(shape.getImage());
                                    image.setFitHeight(50);
                                    image.setFitWidth(50);
                                    image.setX(lastOrign);
                                    image.setY(60);
                                    fx.getChildren().add(image);
                                    //System.out.println("In ShapesMovement #"+(counter2++)+" Action...");
                                    boolean moving = true;
                                    final Timer timer = new Timer(50, new ActionListener() {

                                        private boolean isMoving = moving;

                                        @Override
                                        public void actionPerformed(final ActionEvent e) {

                                            if (isMoving) {
                                                move(image, shape.getOrigin());
                                                CheckResult tmp = player1.check((int) image.getX(), (int) image.getY());
                                                if (!tmp.getResult()) {
                                                    isMoving = false;
                                                    player1.receivePlate(tmp.getIndex(), shape, image);
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
            };
        };
        bigger.setDaemon(true);
        bigger.start();
    }

    public void move(ImageView image, int origin) {
        if (origin == 1000) {
            if (image.getX() < 600) {
                image.setY(delta * delta + image.getY());
            }
            image.setX(image.getX() - delta);
        } else {
            if (image.getX() > 400) {
                image.setY(delta * delta + image.getY());
            }
            image.setX(image.getX() + delta);
        }
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
