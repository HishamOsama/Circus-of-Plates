package controller;

import java.util.ArrayList;
import java.util.Stack;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.players.PlayerIF;

/**
 * General Comments : - The blue comments are used to documentation. - The green
 * comments indicates about something want to be add to the Class.
 * ---------------------------------------------------------------
 *
 * This class are used to control the stack of the players, and notify the
 * ScoreManager to increase the score.
 *
 *
 */

public class PlayersStack implements Runnable {

    /**
     * Max number of similarity we want to increase the score.
     */
    private final int similarity = 3;
    /**
     * Stack that save the names of the shapes that should be distinct.
     */
    private final Stack<Color> plates;
    /**
     * Array List that save the names of the shapes that should be distinct.
     */
    private final ArrayList<ImageView> images;
    /**
     * Which player this object belongs to.
     */
    private final PlayerIF player;
    /**
     * Score controller that should be modified if we found match.
     */
    // private final ScoreManager manager;
    /**
     * Thread for stack
     */
    private Thread thread, bigThread;
    /**
     * to determin Which stack
     */
    private int index;
    private int counter = 0;

    public PlayersStack(final PlayerIF player, final int index) {
        plates = new Stack<>();
        images = new ArrayList<>();
        this.player = player;
        // this.manager = controller;
        this.index = index;
    }

    /**
     * Add the new shape to the stack ,then check if we found match or not.
     * 
     * @param plate
     *            the plate to put in the Stack.
     */
    public void addShape(final Color plate, ImageView image, int indexOfStack) {
        plates.push(plate);
        images.add(image);
        checkStack();

    }

    /**
     * This method is used to put the items we want to check about them in an
     * array.
     *
     */
    private void checkStack() {
        if (plates.size() >= similarity) {
            final Color[] platesToCheck = new Color[similarity];
            for (int i = 0; i < similarity; i++) {
                platesToCheck[i] = plates.pop();
            }
            final boolean allSimilar = checkSimilarity(platesToCheck);
            if (allSimilar) {
                for (int i = 0; i < similarity; i++) {
                    plates.pop();
                    images.remove(images.size() - 1);
                }
                updateScore();
            }
        }
    }

    /**
     * This method is used to see it the items in the array are similar or not
     * 
     * @param platesToCheck
     *            array we want to compare its items
     * @return true -> the items are all similar false -> at least one item is
     *         not similar
     */
    private boolean checkSimilarity(final Color[] platesToCheck) {
        boolean allSimilar = true;
        final Color comparator = platesToCheck[0];
        for (int i = similarity - 1; i >= 0; i--) {
            if (!platesToCheck[i].equals(comparator)) {
                allSimilar = false;
            }
            plates.push(platesToCheck[i]);
        }
        return allSimilar;
    }

    /**
     * modify the Score manager to update the score of the current player.
     */
    private void updateScore() {
        // manager.updateScore(type);
    }

    public void start() {
        bigThread = new Thread() {
            public void run() {
                if (thread == null) {
                    thread = new Thread(this);
                    thread.setDaemon(true);
                    System.out.println("In PlayersStacks #"+(counter++)+" Action...");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                Platform.runLater(thread);
                thread.setDaemon(true);
                Platform.runLater(thread);
            }

        };
        bigThread.setDaemon(true);
        bigThread.start();

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            float[][] position = player.getStacksCenters();
            for (int i = 0; i < images.size(); i++) {
                images.get(i).setX(position[index][0]);
                images.get(i).setY(position[index][1] - 50 * i);
            }
        }

    }
}
