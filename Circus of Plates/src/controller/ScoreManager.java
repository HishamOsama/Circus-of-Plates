package controller;

import controller.Enumrations.players;
import model.players.util.Observer;

public class ScoreManager implements Observer{

    private static ScoreManager manager;
    private static final int noOfPlayers = 2;

    private final int[] scores;

    private ScoreManager(){
        scores = new int[noOfPlayers];
        for(int i = 0 ; i < noOfPlayers ; i++){
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
            manager = new ScoreManager();
        }
        return manager;
    }

	@Override
	public void update(final int player) {
		scores[player]++;
	}

}
