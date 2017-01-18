package controller;

import java.awt.Color;
import java.util.Stack;

import controller.Enumrations.players;

/**
 *  General Comments :
 *              - The blue comments are used to documentation.
 *              - The green comments indicates about something want
 *                  to be add to the Class.
 *  ---------------------------------------------------------------
 *
 *  This class are used to control the stack of the players,
 *  and notify the ScoreManager to increase the score.
 *
 *
 * */

public class PlayersStack {

    /**
     *  Max number of similarity we want to increase the score.
     * */
    private final int similarity = 3;
    /**
     *  Stack that save the names of the shapes that should be distinct.
     * */
    private final Stack<Color> plates;
    /**
     *  Which player this object belongs to.
     * */
    private final players type;
    /**
     *  Score controller that should be modified if we found match.
     * */
    private final ScoreManager manager;

    public PlayersStack(final players type, final ScoreManager controller) {
        plates = new Stack<>();
        this.type = type;
        this.manager = controller;
    }
    /**
    *   Add the new shape to the stack ,then check if we found match
    *   or not.
    *       @param plate
    *                   the plate to put in the Stack.
    */
    public void addShape(final Color plate){
        plates.push(plate);
        checkStack();

    }
    /**
     *  This method is used to put the items we want to check about them
     *      in an array.
     *
     * */
    private void checkStack(){
        if(plates.size() >= similarity){
            final Color[] platesToCheck = new Color[similarity];
            for(int i = 0 ; i < similarity ; i++){
                platesToCheck[i] = plates.pop();
            }
            final boolean allSimilar = checkSimilarity(platesToCheck);
            if(allSimilar){
                updateScore();
            }
        }
    }
    /**
    * This method is used to see it the items in the array are similar
    *   or not
    *       @param platesToCheck
    *                       array we want to compare its items
    *       @return true -> the items are all similar
    *               false -> at least one item is not similar
    */
    private boolean checkSimilarity (final Color[] platesToCheck){
        boolean allSimilar = true;
        final Color comparator = platesToCheck[0];
        for(int i = similarity-1 ; i > 0 ; i--){
            if(!platesToCheck[i].equals(comparator)){
                allSimilar = false;
            }
            plates.push(platesToCheck[i]);
        }
        return allSimilar;
    }
    /**
     *  modify the Score manager to update the score of the current player.
     * */
    private void updateScore(){
         manager.updateScore(type);
    }
}
