package controller;

import java.util.ArrayList;
import java.util.Stack;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.players.AbstractPlayer;

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
    private final AbstractPlayer player;
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
    private final int index;
    private final int counter = 0;

    public PlayersStack(final AbstractPlayer player, final int index) {
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
    public void addShape(final Color plate, final ImageView image, final int indexOfStack) {
        plates.push(plate);
        images.add(image);
        checkStack();

    }

    public int getHeight(){
		return images.size();
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
                    final ImageView im = images.remove(images.size() - 1);
                    im.setVisible(false);
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
        if (thread == null) {
            thread = new Thread(this);
        }
        thread.setDaemon(true);
        thread.start();

    }

    private void drawStack(){
		final int[][] position = player.getPlayerPosition();

		int leftHight = 0;
		int RightHight = 0;

		for (int i = 0; i < images.size(); i++) {
			if (index == 0) {
				images.get(i).setX(position[0][0] + 110);
				images.get(i).setY(520 - (50 * leftHight));
				leftHight++;
			}
			else {
				images.get(i).setX(position[0][0] - 15);
				images.get(i).setY(555 - (50 * RightHight));
				RightHight++;
			}

		}
	}

    @Override
    public synchronized void run() {
        // TODO Auto-generated method stub
    	while (true) {
			drawStack();
		try {
			Thread.sleep(100);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    }
}
