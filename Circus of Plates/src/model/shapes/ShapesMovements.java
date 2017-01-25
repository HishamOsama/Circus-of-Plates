package model.shapes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import controller.Paused;
import controller.PlateFetching.CheckResult;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.players.AbstractPlayer;
import model.shapes.interfaces.Shape;
import util.DimensionsConstants;

public class ShapesMovements extends ImageView implements Runnable {

    private Shape shape;
    private final int delta;
    private final Pane fx;
    private final AbstractPlayer player1, player2;
    //private final int speed;

    public ShapesMovements(final Pane fx, final AbstractPlayer player1, final AbstractPlayer player2) {
        this.fx = fx;
        this.player1 = player1;
        this.player2 = player2;
        delta = DimensionsConstants.delta;
        //this.speed = speed;
    }

    public Color getColor() {
        return shape.getColor();
    }

    public void start(final String name) {

        final Thread mainPlateThread = new Thread("Main Plate Thread") {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    private int counter = 0;
                    private final PlatesPool platesPool = new PlatesPool();
                    private int lastOrign = 0;

                    @Override
                    public void run() {
                        final Thread thread = new Thread() {
                            @Override
                            public void run() {
                                while (true) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!Paused.getState()) {

                                                final Shape shape = platesPool.getPlate();

                                                lastOrign =
                                                		DimensionsConstants.ALTERNATING_FACTOR - lastOrign;

                                                shape.setOrigin(lastOrign);

                                                final ImageView image = convertImage(shape.getImage());
                                                image.setFitHeight(DimensionsConstants.IMAGE_SIZE);
                                                image.setFitWidth(DimensionsConstants.IMAGE_SIZE);
                                                image.setX(lastOrign);
                                                image.setY(DimensionsConstants.INITIAL_Y);
                                                fx.getChildren().add(image);
                                                final boolean moving = true;
                                                //set timer speed to that indicated in level selection
                                                final Timer timer = new Timer(50, new ActionListener() {

                                                    private boolean isMoving = moving;

                                                    @Override
                                                    public void actionPerformed(final ActionEvent e) {
                                                        if (!Paused.getState()) {
                                                            if (isMoving) {
                                                                move(image, shape.getOrigin());
                                                                if (isOutOfSight(image)) {
                                                                	//image.setVisible(false);
                                                                	Platform.runLater(() -> fx.getChildren().remove(image));

                                                                	//platesPool.returnPlate(shape);
                                                                }
                                                                final CheckResult tmp = player1
                                                                        .check((int) image.getX(), (int) image.getY());
                                                                if (!tmp.getResult()) {
                                                                    isMoving = false;
                                                                    player1.receivePlate(tmp.getIndex(), shape, image);
                                                                }

                                                                final CheckResult tmp2 = player2
                                                                        .check((int) image.getX(), (int) image.getY());
                                                                if (!tmp2.getResult()) {
                                                                    isMoving = false;
                                                                    player2.receivePlate(tmp2.getIndex(), shape, image);
                                                                }

                                                            }
                                                        }

                                                    }
                                                });

                                                timer.start();
                                            }
                                        }



                                    });
                                    try {
                                        Thread.sleep(2000);
                                    } catch (final InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                        thread.setDaemon(true);
                        thread.start();
                        counter = (counter + 1) % 1000;
                    }
                });

            }

        };
        mainPlateThread.setDaemon(true);
        mainPlateThread.start();
    }

    public void move(final ImageView image, final int origin) {
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

    private boolean isOutOfSight(final ImageView image) {
    	if (image.getX() > DimensionsConstants.XBoundary || image.getX() < 0 - DimensionsConstants.IMAGE_SIZE) {
    		return true;
    	}
    	if (image.getY() > DimensionsConstants.YBoundary || image.getY() < 0 - DimensionsConstants.IMAGE_SIZE) {
    		return true;
    	}
    	return false;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}
