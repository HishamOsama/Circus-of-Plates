package controller;

import controller.Enumrations.players;

public class ScoreManager {

    private static ScoreManager manager;

    private final int[] scores;

    private ScoreManager(final int numberOfPlayers){
        scores = new int[numberOfPlayers];
        for(int i = 0 ; i < numberOfPlayers ; i++){
            scores[i]=0;
        }
    }

    public void updateScore(final players type){
        scores[type.ordinal()]++;
    }

    public int getScore(final players type){
        return scores[type.ordinal()];
    }

    public static ScoreManager getInstance(final int numberOfPlayers){
        if(manager == null){
            manager = new ScoreManager(numberOfPlayers);
        }
        return manager;
    }

}
