package controller;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import model.shapes.ShapesMovements;
import model.shapes.interfaces.Shape;

public class PoolThread implements Runnable {

    private Thread thread;
    private ArrayList<ShapesMovements> shapes;
    private PlatesPool pool;
    private final static int maxNumberOfPlates = 4;
    private Pane pane;

    public PoolThread(Pane pane) {
        thread = new Thread(this, "Plates Thread");
        thread.setDaemon(true);
        shapes = new ArrayList<>();
        pool = new PlatesPool();
        this.pane = pane;
    }

    public void start() {
        getShapes();
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < maxNumberOfPlates; i++) {
                ShapesMovements image = shapes.get(i);
                image.decreaseDelay();
                image.move();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private void getShapes() {
        for (int i = 0; i < maxNumberOfPlates; i++) {
            Shape shape = pool.getPlate();
            // Convert to image view;
            ShapesMovements image = new ShapesMovements(shape);
            // shapes.add();
            shapes.add(image);
            // append to pane
            pane.getChildren().add(image);
        }
    }

}
